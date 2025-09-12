package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mapper.DfUpSilkScreenWireframeMapper;
import com.biel.qmsgatherCgVn.service.DfUpSilkScreenWireframeService;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers;
import com.biel.qmsgatherCgVn.util.excel.ExcelHeaderValidator;
import com.biel.qmsgatherCgVn.util.excel.ExcelSheetUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author dafenqi
* @description 针对表【df_up_silk_screen_wireframe(丝印线框)】的数据库操作Service实现
* @createDate 2025-07-25 11:35:16
*/
@Service
public class DfUpSilkScreenWireframeServiceImpl extends ServiceImpl<DfUpSilkScreenWireframeMapper, DfUpSilkScreenWireframe>
    implements DfUpSilkScreenWireframeService{

    @Autowired
    private DfUpSilkScreenWireframeMapper dfUpSilkScreenWireframeMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    private static final int MQ_BATCH_SIZE = 200;

    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) { // 自动识别 xls/xlsx，异常也会关闭
            Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

            // 如仍需支持公式求值，可保留以下一行
            workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

            // 表头校验（表头在第2行，从第2列开始）
            validateHeader(sheet);

            int startRow = 8; // 从第9行开始（索引是8）
            List<DfUpSilkScreenWireframe> mqBatch = new ArrayList<>(MQ_BATCH_SIZE);
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (ExcelSheetUtils.isRowEmpty(row)) continue;

                // 首列日期只解析一次
                Date parsedDate = ExcelCellParsers.getDateCellValue(row.getCell(0));
                if (parsedDate == null) continue;

                DfUpSilkScreenWireframe entity = new DfUpSilkScreenWireframe();
                int i = 0;

                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);

                entity.setDate(parsedDate);
                i++; // 日期列已读取，推进列指针

                entity.setOnePointy2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setTwoPointy1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setThreePointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setFourPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));

                entity.setFivePointy1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setSixPointy2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));

                entity.setSevenPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setEightPointx(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));

                entity.setR1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setR2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setR3(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setR4(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));

                entity.setTwoRadiumCode(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));

                entity.setDebugMachinex(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setDebugMachiney1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setDebugMachiney2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));

                entity.setMachineCode(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));

                entity.setUploadName(uploadName);
                entity.setCreateTime(new Date());

                // 保存
                dfUpSilkScreenWireframeMapper.insert(entity);

                // 批量事件发布（由监听器统一发送到MQ）
                mqBatch.add(entity);
                if (mqBatch.size() >= MQ_BATCH_SIZE) {
                    publishBatch(mqBatch);
                    mqBatch.clear();
                }
            }

            // 收尾发布剩余
            if (!mqBatch.isEmpty()) {
                publishBatch(mqBatch);
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

        // 2-9列 合并：位置八点
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 1, 8, "位置八点");

        // 10-13列 合并：四个R角
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 9, 12, "四个R角");

        // 14列：2D镭码位置（单列）
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 13, "2D镭码位置");

        // 15-17列 合并：丝印调机专用
        ExcelHeaderValidator.assertMergedHeaderFuzzy(sheet, headerRowIndex, 14, 16, "丝印调机专用");
    }
    // 新增：发布事件的封装（复制一份避免引用外部可变集合）
    private void publishBatch(List<DfUpSilkScreenWireframe> batch) {
        if (batch == null || batch.isEmpty()) return;
        publisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(batch), DfUpSilkScreenWireframe.class));
    }
}




