package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.mapper.DfUpChamferHypotenuseMapper;
import com.biel.qmsgatherCgVn.service.DfUpChamferHypotenuseService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biel.qmsgatherCgVn.event.DataImportedEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.*;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/27 16:02
 */
@Service
public class DfUpChamferHypotenuseServiceImpl extends ServiceImpl<DfUpChamferHypotenuseMapper, DfUpChamferHypotenuse> implements DfUpChamferHypotenuseService {

    @Autowired
    private DfUpChamferHypotenuseMapper dfUpChamferHypotenuseMapper;

    // 改为事件发布器，解耦合MQ
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    private static final int MQ_BATCH_SIZE = 200;
    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId,String createTime) throws Exception {

        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet
        // 表头校验：斜边倒角要求包含“短边右上1”
        validateExcelHeader(sheet, "平均值");

        //Date createTimeDate = parseCreateTime(createTime);

        int startRow = 11; // 从第12行开始（索引是11）
        List<DfUpChamferHypotenuse> mqBatch = new ArrayList<>(MQ_BATCH_SIZE);
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpChamferHypotenuse entity = new DfUpChamferHypotenuse();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            Date recordDate = getDateCellValue(row.getCell(i++));
            entity.setDate(recordDate);
            // 基础测量值：保留3位小数
            entity.setShortUr1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongUr2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongLr3(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setShortLr4(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongLl6(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongUl7(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setShortUl8(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setShortLl5(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

            entity.setAvg(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 4));
            entity.setStd1to4(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setStd2to7(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setStd3to6(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setStd5to8(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

            entity.setMachineCode(getStringCellValue(row.getCell(i++)));
            entity.setState(getStringCellValue(row.getCell(i++)));
            entity.setTestNumber(getIntegerCellValue(row.getCell(i++)));
            entity.setRemark(getStringCellValue(row.getCell(i++)));

            entity.setClasses(determineShift(recordDate));
            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            dfUpChamferHypotenuseMapper.insert(entity);

            // 批量事件发布（由监听器统一发送到MQ）
            mqBatch.add(entity);
            if (mqBatch.size() >= MQ_BATCH_SIZE) {
                eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpChamferHypotenuse.class));
                mqBatch.clear();
            }
        }

        if (!mqBatch.isEmpty()) {
            eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpChamferHypotenuse.class));
            mqBatch.clear();
        }

    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return true;
            }
        }
        return false;
    }

    /**
     * 保留指定小数位数
     */
    private double roundToDecimalPlaces(double value, int decimalPlaces) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return 0.0;
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
    }

    // 表头通用校验（扫描前20行*每行前50列）
    private void validateExcelHeader(Sheet sheet, String requiredHeader) {
        if (!containsHeaderKeyword(sheet, requiredHeader)) {
            throw new RuntimeException("导入的excel文件错误");
        }
    }

    private boolean containsHeaderKeyword(Sheet sheet, String keyword) {
        DataFormatter formatter = new DataFormatter();
        int maxRowsToScan = Math.min(20, sheet.getLastRowNum() + 1);
        for (int r = 0; r < maxRowsToScan; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            short lastCellNum = row.getLastCellNum();
            if (lastCellNum < 0) continue;
            for (int c = 0; c < lastCellNum && c < 50; c++) {
                Cell cell = row.getCell(c);
                if (cell == null) continue;
                String text = formatter.formatCellValue(cell).trim();
                if (!text.isEmpty() && text.contains(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }
}
