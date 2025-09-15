package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpWireFrameInkClimbing;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mapper.DfUpWireFrameInkClimbingMapper;
import com.biel.qmsgatherCgVn.service.DfUpWireFrameInkClimbingService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author dafenqi
* @description 针对表【df_up_wire_frame_ink_climbing(线框油墨爬高)】的数据库操作Service实现
* @createDate 2025-07-24 15:46:00
*/
@Service
public class DfUpWireFrameInkClimbingServiceImpl extends ServiceImpl<DfUpWireFrameInkClimbingMapper, DfUpWireFrameInkClimbing>
    implements DfUpWireFrameInkClimbingService {

    @Autowired
    private DfUpWireFrameInkClimbingMapper dfUpWireFrameInkClimbingMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    private static final int MQ_BATCH_SIZE = 200;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) { // 自动识别 xls/xlsx，并确保资源关闭
            Sheet sheet = workbook.getSheetAt(0);

            // 表头在第一行（索引0）
            Row headerRow = sheet.getRow(0);
            validateHeader(headerRow);

            int startRow = 4; // 从第5行开始（索引4）
            List<DfUpWireFrameInkClimbing> mqBatch = new ArrayList<>(MQ_BATCH_SIZE);

            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (ExcelSheetUtils.isRowEmpty(row)) continue;

                // 首列为时间，只解析一次
                Date parsedDate = ExcelCellParsers.getDateCellValue(row.getCell(0));
                if (parsedDate == null) continue;

                DfUpWireFrameInkClimbing entity = new DfUpWireFrameInkClimbing();
                int i = 0;

                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);

                entity.setDate(parsedDate);
                i++; // 日期列已读取，推进列指针

                entity.setLongSide1(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setLongSide2(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setGroove(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setShortGroove(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));
                entity.setShortSide(ExcelCellParsers.getDoubleCellValue(row.getCell(i++)));

                entity.setMachineCode(ExcelCellParsers.getStringCellValue(row.getCell(i++)));
                entity.setState(ExcelCellParsers.getStringCellValue(row.getCell(i++)));

                entity.setUploadName(uploadName);
                entity.setCreateTime(new Date());

                // 持久化
                dfUpWireFrameInkClimbingMapper.insert(entity);

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

    // 表头校验（校验第2~6列为：长边1、长边2、凹槽、凹槽短边、短边）
    private void validateHeader(Row headerRow) {
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头不存在或格式错误：未找到表头行（应在第1行）");
        }
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 1, "长边1");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 2, "长边2");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 3, "凹槽");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 4, "凹槽短边");
        ExcelHeaderValidator.assertSingleHeaderFuzzy(headerRow, 5, "短边");
    }

    // 发布事件封装
    private void publishBatch(List<DfUpWireFrameInkClimbing> batch) {
        if (batch == null || batch.isEmpty()) return;
        publisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(batch), DfUpWireFrameInkClimbing.class));
    }
}




