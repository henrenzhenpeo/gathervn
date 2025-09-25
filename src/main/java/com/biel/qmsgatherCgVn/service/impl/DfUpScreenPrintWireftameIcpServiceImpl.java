package com.biel.qmsgatherCgVn.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintWireftameIcpMapper;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintWireftameIcpService;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers;
import com.biel.qmsgatherCgVn.util.excel.ExcelHeaderValidator;
import com.biel.qmsgatherCgVn.util.excel.ExcelSheetUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.getDoubleCellValue;
import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.roundToDecimalPlaces;

/**
* @author dafenqi
* @description 针对表【df_up_screen_print_wireftame_icp(丝印线框icp)】的数据库操作Service实现
* @createDate 2025-07-22 16:36:25
*/
@Service
public class DfUpScreenPrintWireftameIcpServiceImpl extends ServiceImpl<DfUpScreenPrintWireftameIcpMapper, DfUpScreenPrintWireftameIcp>
    implements DfUpScreenPrintWireftameIcpService {

    @Autowired
    private DfUpScreenPrintWireftameIcpMapper mapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final int MQ_BATCH_SIZE = 200;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) { // 自动关闭资源
            Sheet sheet = workbook.getSheetAt(0);

            // 表头校验
            validateHeader(sheet);

            int startRow = 5; // 从第6行开始（索引5）
            List<DfUpScreenPrintWireftameIcp> mqBatch = new ArrayList<>();
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (ExcelSheetUtils.isRowEmpty(row)) continue;

                // 首列日期仅解析一次
                Date parsedDate = ExcelCellParsers.getDateCellValue(row.getCell(0));
                if (parsedDate == null) continue;

                DfUpScreenPrintWireftameIcp entity = new DfUpScreenPrintWireftameIcp();
                int i = 0;

                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);

                entity.setDate(parsedDate);
                i++; // 日期列已读取，推进列指针


                entity.setR1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setR2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setR3(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setR4(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setUpperLongSide1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setUpperLongSide2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setUpperLongSide3(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setLowerLongSide1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setLowerLongSide2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setLowerLongSide3(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setShortDegeGroove1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setShortDegeGroove2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setNoShortDegeGroove1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setNoShortDegeGroove2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setDegeGroove(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setLongAppearance1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setLongAppearance2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setExteriorWidth(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setExteriorWidth2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setMaxValue(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setMachineCode(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));

                entity.setUploadName(uploadName);
                entity.setUploadCreate(new Date());

                mapper.insert(entity);

                // 批量事件发布（由监听器统一发送到MQ）
                mqBatch.add(entity);
                if (mqBatch.size() >= MQ_BATCH_SIZE) {
                    eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintWireftameIcp.class));
                    mqBatch.clear();
                }
            }
            if (!mqBatch.isEmpty()) {
                eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintWireftameIcp.class));
                mqBatch.clear();
            }
        }
    }
    private void validateHeader(Sheet sheet) {
        int headerRowIndex = 1; // 第二行
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头校验失败：未找到第2行表头（索引1）");
        }

        // 单列校验（使用模糊匹配以兼容轻微差异）
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 0, "时间");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 1, "左上R角");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 2, "右上R角");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 3, "左下R角");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 4, "右下R角");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 5, "上长边左");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 6, "上长边中");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 7, "上长边右");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 8, "下长边左");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 9, "中长边");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 10, "右长边");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 11, "凹槽短边1");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 12, "凹槽短边2");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 13, "凹槽短边3");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 14, "凹槽短边4");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 15, "凹槽");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 16, "外形长1");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 17, "外形长2");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 18, "外形宽1");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 19, "外形宽2");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 20, "max");
    }

}




