package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintingbmService;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintingbmMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import org.apache.poi.ss.usermodel.Cell;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers;
import com.biel.qmsgatherCgVn.util.excel.ExcelHeaderValidator;



/**
* @author dafenqi
* @description 针对表【df_up_screen_printingbm(丝印BM2)】的数据库操作Service实现
* @createDate 2025-07-25 11:35:11
*/
@Service
public class DfUpScreenPrintingbmServiceImpl extends ServiceImpl<DfUpScreenPrintingbmMapper, DfUpScreenPrintingbm>
    implements DfUpScreenPrintingbmService{

    @Autowired
    private DfUpScreenPrintingbmMapper dfUpScreenPrintingbmMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher; // 新增：事件发布器

    private static final int MQ_BATCH_SIZE = 200;     // 新增：批量大小与其他模块保持一致

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) { // ✅ 自动识别 xls/xlsx，确保异常也能关闭
            Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet
    
            // 表头校验：表头在第2行（索引1），从第2列开始校验
            validateHeader(sheet);
    
            // 新增：批量事件缓存
            List<DfUpScreenPrintingbm> mqBatch = new ArrayList<>(MQ_BATCH_SIZE);
    
            int startRow = 8; // 从第9行开始（索引是8）
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
    
                if (row == null) continue; // 跳过空行
                Cell dateCell = row.getCell(0);
                Date parsedDate = ExcelCellParsers.getDateCellValue(dateCell);
                if (parsedDate == null) continue;
    
                DfUpScreenPrintingbm entity = new DfUpScreenPrintingbm();
                int i = 0;
    
                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);
    
                entity.setDate(parsedDate); // 避免重复解析
                i++; // 手动推进列索引，保持与后续 i++ 顺序一致
    
                entity.setOnePointy2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoPointy1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setThreePointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setFourPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
    
                // entity.setFourPointx(getDoubleCellValue(row.getCell(i++)));
                entity.setFivePointy1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setSixPointy2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
    
                entity.setSevenPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setEightPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setWindowLength1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
    
                entity.setWindowLength2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setWindowWide1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
    
                entity.setWindowWide2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
    
                entity.setDebugMachinex(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setDebugMachiney1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setDebugMachiney2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
    
                entity.setMachineCode(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
    
                entity.setUploadName(uploadName);
                entity.setCreateTime(new Date());
    
                dfUpScreenPrintingbmMapper.insert(entity);
    
                // 新增：批量事件发布（由监听器统一发送到MQ）
                mqBatch.add(entity);
                if (mqBatch.size() >= MQ_BATCH_SIZE) {
                    eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintingbm.class));
                    mqBatch.clear();
                }
            }
    
            // 新增：收尾发布
            if (!mqBatch.isEmpty()) {
                eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintingbm.class));
                mqBatch.clear();
            }
        }
    }


    // 删除了本地的 getStringCellValue 私有方法，统一改为使用 ExcelCellParsers.getStringCellValue
    private void validateHeader(Sheet sheet) {
        int headerRowIndex = 1; // 第二行
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头校验失败：未找到第2行表头（索引1）");
        }

        // 2-9列 合并：位置八点
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 1, 8, "位置八点");

        // 10-13列 单列模糊匹配
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 9,  "视窗长1");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 10, "视窗长2");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 11, "视窗宽1");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 12, "视窗宽2");

        // 14-16列 合并：丝印调机专用
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 13, 15, "丝印调机专用");
    }
}







