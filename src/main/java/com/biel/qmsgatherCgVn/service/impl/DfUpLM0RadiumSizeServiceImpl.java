package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpFileMaterialSize;
import com.biel.qmsgatherCgVn.domain.DfUpLM0RadiumSize;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mapper.DfUpFileMaterialSizeMapper;
import com.biel.qmsgatherCgVn.mapper.DfUpLM0RadiumSizeMapper;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintingbmMapper;
import com.biel.qmsgatherCgVn.service.DfUpFileMaterialSizeService;
import com.biel.qmsgatherCgVn.service.DfUpLM0RadiumSizeService;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers;
import com.biel.qmsgatherCgVn.util.excel.ExcelHeaderValidator;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.*;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/9/25 17:15
 */
@Service
public class DfUpLM0RadiumSizeServiceImpl extends ServiceImpl<DfUpLM0RadiumSizeMapper, DfUpLM0RadiumSize> implements DfUpLM0RadiumSizeService {


    @Autowired
    private DfUpLM0RadiumSizeMapper dfUpLM0RadiumSizeMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher; // 新增：事件发布器

    private static final int MQ_BATCH_SIZE = 200;     // 新增：批量大小与其他模块保持一致
    private static final int DB_BATCH_SIZE = 200;

    @Override
    @Transactional
    public void importExcel(MultipartFile file, String factory, String model, String process,
                            String testProject, String uploadName, String batchId) throws Exception {

        PoiZipSecurity.configure();

        try (InputStream is = file.getInputStream();
             Workbook workbook = StreamingReader.builder()
                     .rowCacheSize(100)    // 内存中缓存行数
                     .bufferSize(4096)     // 流缓冲区
                     .open(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            // 使用迭代器校验表头
            Iterator<Row> rowIterator = sheet.rowIterator();
            if (!rowIterator.hasNext()) {
                throw new RuntimeException("Excel sheet is empty");
            }

            // 跳过前1行（如果你的表头在第二行，可以跳1行）
            rowIterator.next();

            // 第二行作为表头
            if (!rowIterator.hasNext()) {
                throw new RuntimeException("Excel sheet has no second row for header");
            }
            Row headerRow = rowIterator.next();
            validateHeader(headerRow); // 改成传入 Row 对象的表头校验方法

            List<DfUpLM0RadiumSize> dbBatch = new ArrayList<>(DB_BATCH_SIZE);
            List<DfUpLM0RadiumSize> mqBatch = new ArrayList<>(MQ_BATCH_SIZE);

            int rowIndex = 2; // 从第三行开始是数据
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                rowIndex++;
                if (rowIndex <= 5) continue; // 前5行说明行/表头可跳过

                Date parsedDate = ExcelCellParsers.getDateCellValue(row.getCell(0));
                if (parsedDate == null) continue;

                DfUpLM0RadiumSize entity = new DfUpLM0RadiumSize();
                int i = 0;

                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);

                entity.setDate(parsedDate);
                i++;

                entity.setExternalLong(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setExternalWidth(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setCodeLength(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setCodeWidth(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setCodeToView(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setCodeToCenterX(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setCodeToCenterY(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setAppleUpDownLength(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setAppleWidth(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setLeafDownToUpHorizontalDistance(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setAppleLogoBodyUpToDown(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setLeafDownToAppleLogoBodyDown(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
                entity.setAppleBottomToQrCode(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

                entity.setMachineCode(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));

                entity.setClasses(determineShift(parsedDate));
                entity.setUploadName(uploadName);
                entity.setCreateTime(new Date());

                dbBatch.add(entity);
                mqBatch.add(entity);

                // MQ 批量发布
                if (mqBatch.size() >= MQ_BATCH_SIZE) {
                    eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpLM0RadiumSize.class));
                    mqBatch.clear();
                }

                // 数据库批量写入
                if (dbBatch.size() >= DB_BATCH_SIZE) {
                    this.saveBatch(dbBatch, DB_BATCH_SIZE); // MyBatis-Plus 批量写入
                    dbBatch.clear();
                }
            }

            // 收尾处理
            if (!dbBatch.isEmpty()) {
                this.saveBatch(dbBatch, DB_BATCH_SIZE);
            }
            if (!mqBatch.isEmpty()) {
                eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpLM0RadiumSize.class));
            }
        }
    }


    // 删除了本地的 getStringCellValue 私有方法，统一改为使用 ExcelCellParsers.getStringCellValue
    private void validateHeader(Row headerRow) {
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头校验失败：headerRow为空");
        }

        for (int i = 1; i <= 16; i++) { // 第2列到第17列
            Cell cell = headerRow.getCell(i);
            String cellValue = ExcelCellParsers.getStringCellValue(cell); // 流式单元格安全获取
            switch (i) {
                case 1:
                    assertContains(cellValue, "外形长");
                    break;
                case 2:
                    assertContains(cellValue, "外形宽");
                    break;
                case 3:
                    assertContains(cellValue, "码长");
                    break;
                case 4:
                    assertContains(cellValue, "码宽");
                    break;
                case 5:
                    assertContains(cellValue, "码到视窗");
                    break;
                case 6:
                    assertContains(cellValue, "码到中心X");
                    break;
                case 7:
                    assertContains(cellValue, "码到中心Y");
                    break;
                case 8:
                    assertContains(cellValue, "苹果上下长度");
                    break;
                case 9:
                    assertContains(cellValue, "苹果宽");
                    break;
                case 10:
                    assertContains(cellValue, "叶子下端到上端水平距离");
                    break;
                case 11:
                    assertContains(cellValue, "苹果logo主体");
                    break;
                case 12:
                    assertContains(cellValue, "叶子下端到苹果logo");
                    break;
                case 13:
                    assertContains(cellValue, "苹果底");
                    break;
/*                case 14:
                    assertContains(cellValue, "苹果底到二维码");
                    break;
                case 15:
                    assertContains(cellValue, "机台号");
                    break;
                case 16:
                    assertContains(cellValue, "状态");
                    break;*/
                default:
                    break;
            }
        }
    }

    private void assertContains(String actual, String expected) {
        if (actual == null || !actual.contains(expected)) {
            throw new IllegalArgumentException("Excel表头校验失败：应包含【" + expected + "】, 实际为【" + actual + "】");
        }
    }


}
