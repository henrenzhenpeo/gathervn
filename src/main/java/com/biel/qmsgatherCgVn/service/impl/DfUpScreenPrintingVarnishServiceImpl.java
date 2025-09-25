package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintingVarnishService;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintingVarnishMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers;
import com.biel.qmsgatherCgVn.util.excel.ExcelHeaderValidator;
import com.biel.qmsgatherCgVn.util.excel.ExcelSheetUtils;
import com.github.pjfanning.xlsx.StreamingReader;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext; // 新增
import org.springframework.context.ApplicationEventPublisher; // 新增
import com.biel.qmsgatherCgVn.event.DataImportedEvent;    // 新增

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;                               // 新增
import java.util.List;                                    // 新增
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;

import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.getDoubleCellValue;
import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.roundToDecimalPlaces;

/**
 * @author dafenqi
 * @description 针对表【df_up_screen_printing_varnish(丝印光油)】的数据库操作Service实现
 * @createDate 2025-07-24 16:34:35
 */
@Service
@Slf4j
public class DfUpScreenPrintingVarnishServiceImpl extends ServiceImpl<DfUpScreenPrintingVarnishMapper, DfUpScreenPrintingVarnish>
        implements DfUpScreenPrintingVarnishService {

    @Autowired
    private DfUpScreenPrintingVarnishMapper dfUpScreenPrintingVarnishMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ApplicationContext applicationContext; // 新增

    private static final int MQ_BATCH_SIZE = 200;
    private static final int DB_BATCH_SIZE = 1000;

    @Override
    public void importExcel(MultipartFile file,
                            String factory,
                            String model,
                            String process,
                            String testProject,
                            String uploadName,
                            String batchId) throws Exception {

        PoiZipSecurity.configure();

        // 先把 MultipartFile 转存到临时文件（避免 getInputStream() 多次调用问题）
        File tempFile = File.createTempFile("import-", ".xlsx");
        file.transferTo(tempFile);

        try {
            // 第一步：普通方式读取表头，支持合并单元格校验
            try (InputStream headerIs = new FileInputStream(tempFile);
                 Workbook headerWb = WorkbookFactory.create(headerIs)) {
                Sheet headerSheet = headerWb.getSheetAt(0);
                validateHeader(headerSheet);  // ✅ 这里用原来的 validateHeader，不用改
            }

            // 第二步：流式读取数据部分
            try (InputStream dataIs = new FileInputStream(tempFile);
                 Workbook workbook = StreamingReader.builder()
                         .rowCacheSize(500)
                         .bufferSize(4096)
                         .open(dataIs)) {

                Sheet sheet = workbook.getSheetAt(0);

                int rowIndex = 0;
                int startRow = 9; // 从第9行开始读数据（索引8）
                List<DfUpScreenPrintingVarnish> dbBatch = new ArrayList<>(DB_BATCH_SIZE);
                Date importTime = new Date();

                for (Row row : sheet) {
                    rowIndex++;
                    if (rowIndex < startRow) {
                        continue;
                    }
                    if (ExcelSheetUtils.isRowEmpty(row)) {
                        continue;
                    }

                    try {
                        Date parsedDate = ExcelCellParsers.getDateCellValue(row.getCell(0));
                        if (parsedDate == null) {
                            continue;
                        }

                        DfUpScreenPrintingVarnish entity = new DfUpScreenPrintingVarnish();
                        int i = 0;

                        entity.setFactory(factory);
                        entity.setModel(model);
                        entity.setProcess(process);
                        entity.setTestProject(testProject);
                        entity.setBatchId(batchId);

                        entity.setDate(parsedDate);
                        i++;

                        entity.setOnePointy2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setTwoPointy1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setThreePoint(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setGroove(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setFourPointx(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setFivePointy1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setSixPointy2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setSevenPoint(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setEightPointx(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setTwoCodeWindow1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setTwoCodeWindow2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setLightOilTopReference1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setLightOilTopReference2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setTwoCodeCenter1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setTwoCodeCenter2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setTwoCodeTopCenter1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setTwoCodeTopCenter2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                        entity.setDebugMachinex(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 4));
                        entity.setDebugMachiney1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 4));
                        entity.setDebugMachiney2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 4));
                        entity.setRemark(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                        entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));

                        entity.setUploadName(uploadName);
                        entity.setCreateTime(importTime);

                        dbBatch.add(entity);
                        if (dbBatch.size() >= DB_BATCH_SIZE) {
                            getSelf().saveBatchWithNewTx(dbBatch);
                            dbBatch.clear();
                        }

                    } catch (Exception e) {
                        log.error("第 {} 行解析失败，跳过该行，原因：{}", rowIndex, e.getMessage(), e);
                        continue;
                    }
                }

                if (!dbBatch.isEmpty()) {
                    getSelf().saveBatchWithNewTx(dbBatch);
                    dbBatch.clear();
                }
            }

        } finally {
            // 删除临时文件，避免磁盘泄露
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * 获取当前类的代理对象，确保事务注解生效
     */
    private DfUpScreenPrintingVarnishServiceImpl getSelf() {
        return applicationContext.getBean(DfUpScreenPrintingVarnishServiceImpl.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveBatchWithNewTx(List<DfUpScreenPrintingVarnish> batch) {
        try {
            this.saveBatch(batch);
            log.info("保存数据库批次成功：{} 条", batch.size());

            // 在事务提交前创建事件的副本
            List<DfUpScreenPrintingVarnish> eventBatch = new ArrayList<>(batch);

            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    List<List<DfUpScreenPrintingVarnish>> mqChunks = Lists.partition(eventBatch, MQ_BATCH_SIZE);
                    for (List<DfUpScreenPrintingVarnish> chunk : mqChunks) {
                        eventPublisher.publishEvent(
                                new DataImportedEvent<>(new ArrayList<>(chunk), DfUpScreenPrintingVarnish.class)
                        );
                    }
                }
            });

        } catch (Exception e) {
            log.error("保存数据库批次失败，批次大小：{}，错误：{}", batch.size(), e.getMessage(), e);
            throw e;
        }
    }

    private void validateHeader(Sheet sheet) {
        int headerRowIndex = 2; // 第三行（索引2）
        Row headerRow = null;

        for (Row row : sheet) {
            if (row.getRowNum() == headerRowIndex) {
                headerRow = row;
                break;
            }
            if (row.getRowNum() > headerRowIndex) {
                break; // 已经过头，不用再遍历
            }
        }

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


