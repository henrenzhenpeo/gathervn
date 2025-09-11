package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintingVarnishService;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintingVarnishMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers;
import com.biel.qmsgatherCgVn.util.excel.ExcelHeaderValidator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher; // 新增
import com.biel.qmsgatherCgVn.event.DataImportedEvent;    // 新增
import java.util.ArrayList;                               // 新增
import java.util.List;                                    // 新增
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author dafenqi
 * @description 针对表【df_up_screen_printing_varnish(丝印光油)】的数据库操作Service实现
 * @createDate 2025-07-24 16:34:35
 */
@Service
public class DfUpScreenPrintingVarnishServiceImpl extends ServiceImpl<DfUpScreenPrintingVarnishMapper, DfUpScreenPrintingVarnish>
        implements DfUpScreenPrintingVarnishService {

    @Autowired
    private DfUpScreenPrintingVarnishMapper dfUpScreenPrintingVarnishMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher; // 新增：事件发布器（解耦发送）

    private static final int MQ_BATCH_SIZE = 200; // 新增：与其他模块一致
    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) { // ✅ 自动识别 xls/xlsx，异常也会关闭
            Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet
    
            // 表头校验：表头在第3行（索引2），从第2列开始校验
            validateHeader(sheet);
    
            // 新增：批量事件缓存
            List<DfUpScreenPrintingVarnish> mqBatch = new ArrayList<>(MQ_BATCH_SIZE);
    
            int startRow = 8; // 从第9行开始（索引是8）
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
    
                if (com.biel.qmsgatherCgVn.util.excel.ExcelSheetUtils.isRowEmpty(row)) continue; // 跳过空行
                // 首列日期只解析一次并缓存
                Date parsedDate = com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.getDateCellValue(row.getCell(0));
                if (parsedDate == null) continue;
    
                DfUpScreenPrintingVarnish entity = new DfUpScreenPrintingVarnish();
                int i = 0;
    
                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);
    
                entity.setDate(parsedDate); // 使用缓存结果，避免重复解析
                i++; // 手动推进列索引，保持与后续列的对齐
    
                entity.setOnePointy2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoPointy1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setThreePoint(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setGroove(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setFourPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setFivePointy1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setSixPointy2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setSevenPoint(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setEightPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoCodeWindow1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoCodeWindow2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setLightOilTopReference1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setLightOilTopReference2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoCodeCenter1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoCodeCenter2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoCodeTopCenter1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoCodeTopCenter2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setDebugMachinex(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setDebugMachiney1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setDebugMachiney2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setMachineCode(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
    
                entity.setUploadName(uploadName);
                entity.setCreateTime(new Date());
    
                // 保存
                dfUpScreenPrintingVarnishMapper.insert(entity);
    
                // 新增：批量事件发布（由监听器统一发送到MQ）
                mqBatch.add(entity);
                if (mqBatch.size() >= MQ_BATCH_SIZE) {
                    eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintingVarnish.class));
                    mqBatch.clear();
                }
            }
    
            // 新增：收尾发布
            if (!mqBatch.isEmpty()) {
                eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintingVarnish.class));
                mqBatch.clear();
            }
        }
    }

    private void validateHeader(Sheet sheet) {
        int headerRowIndex = 2; // 第三行
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头校验失败：未找到第3行表头（索引2）");
        }

        // 单列（模糊匹配）
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 1, "1点");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 2, "2点");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 6, "5点");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 7, "6点");

        // 合并块（模糊匹配）
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 3, 5, "3点");
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 8, 9, "7点");

        // 两列均需模糊匹配“2D码到视窗”
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 10, "2D码到视窗");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 11, "2D码到视窗");

        // 后续合并块
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 12, 13, "光油上边到基准C");
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 14, 15, "光油内边到玻璃中心距离");
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 16, 17, "上端到玻中心");
    }

}




