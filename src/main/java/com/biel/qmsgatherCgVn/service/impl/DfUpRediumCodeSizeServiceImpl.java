package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.domain.DfUpRadiumCodeSize;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mapper.DfUpRediumCodeSizeMapper;
import com.biel.qmsgatherCgVn.service.DfUpRediumCodeSizeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.*;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/26 13:38
 */
@Service
@Slf4j
public class DfUpRediumCodeSizeServiceImpl extends ServiceImpl<DfUpRediumCodeSizeMapper, DfUpRadiumCodeSize> implements DfUpRediumCodeSizeService {

    @Autowired
    private DfUpRediumCodeSizeMapper dfUpRediumCodeSizeMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final int MQ_BATCH_SIZE = 200;
    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId,String createTime) throws Exception {
        InputStream is = null;
        Workbook workbook = null;
        boolean isHandedOver = false;
        try {
            is = file.getInputStream();
            workbook = WorkbookFactory.create(is); // ✅ 自动识别 xls/xlsx
            isHandedOver = true;

            Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet
            // 表头校验：镭码尺寸要求包含“二维码长”
            validateExcelHeader(sheet, "二维码长");

            Date createTimeDate = parseCreateTime(createTime);

            int startRow = 11; // 从第12行开始（索引是11）
            List<DfUpRadiumCodeSize> mqBatch = new ArrayList<>(MQ_BATCH_SIZE);
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);

                if (row == null) continue; // 不跳过非空的行
                Cell dateCell = row.getCell(0);
                if (dateCell == null || getDateCellValue(dateCell) == null) continue;
                DfUpRadiumCodeSize entity = new DfUpRadiumCodeSize();
                int i = 0;

                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);

                Date recordDate = getDateCellValue(row.getCell(i++));
                entity.setDate(recordDate);
                entity.setQrCodeLength(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setQrCodeWidth(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setBarcodeToglass(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setXWhitePlateToGlassCenter(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setLeftDistance(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setRightDistance(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setMachineCode(getStringCellValue(row.getCell(i++)));
                entity.setState(getStringCellValue(row.getCell(i++)));
                entity.setTestNumber(getIntegerCellValue(row.getCell(i++)));
                entity.setRemark(getStringCellValue(row.getCell(i++)));

                entity.setClasses(determineShift(recordDate));
                entity.setUploadName(uploadName);
                entity.setCreateTime(createTimeDate);

                dfUpRediumCodeSizeMapper.insert(entity);

                // MQ 批量事件发布（解耦合为事件）
                mqBatch.add(entity);
                if (mqBatch.size() >= MQ_BATCH_SIZE) {
                    eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpRadiumCodeSize.class));
                    mqBatch.clear();
                }
            }
            if (!mqBatch.isEmpty()) {
                eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpRadiumCodeSize.class));
                mqBatch.clear();
            }
        }finally {
            IOUtils.closeQuietly(workbook);
            if (!isHandedOver) {
                IOUtils.closeQuietly(is);
            }
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

    private Double getDoubleCellValue(Cell cell) {
        if (cell == null) return null;
        try {
            return cell.getCellType() == CellType.NUMERIC ? cell.getNumericCellValue()
                    : Double.parseDouble(cell.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        return cell.toString().trim();
    }

    private Date getDateCellValue(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String val = cell.getStringCellValue().trim();
                if (val.isEmpty()) return null;

                // 清理格式：将 2025/6/20 8:30:35 -> 2025-06-20 08:30:35
                val = val.replace("/", "-");

                // 解析格式：支持不补零格式的时间（注意单个数字）
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s");
                return sdf.parse(val);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 可记录日志
        }

        return null;
    }

    private Date parseCreateTime(String val) {
        if (val == null) return null;
        val = val.trim();
        if (val.isEmpty()) return null;

        try {
            // 兼容常见分隔符和格式
            val = val.replace("/", "-").replace("T", " ");
            // 处理 "yyyy-MM-dd,HH:mm:ss" 之类
            if (val.contains(",")) {
                val = val.replace(",", " ");
            }

            SimpleDateFormat[] formats = new SimpleDateFormat[] {
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                    new SimpleDateFormat("yyyy-M-d H:m:s")
            };

            for (SimpleDateFormat sdf : formats) {
                try {
                    return sdf.parse(val);
                } catch (Exception ignore) { }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
