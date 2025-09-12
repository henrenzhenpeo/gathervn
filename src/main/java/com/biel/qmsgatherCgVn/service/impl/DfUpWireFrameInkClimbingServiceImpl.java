package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.domain.DfUpWireFrameInkClimbing;
import com.biel.qmsgatherCgVn.service.DfUpWireFrameInkClimbingService;
import com.biel.qmsgatherCgVn.mapper.DfUpWireFrameInkClimbingMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.ApplicationEventPublisher; // 新增
import com.biel.qmsgatherCgVn.event.DataImportedEvent;    // 新增
import java.util.ArrayList;                                // 新增
import java.util.List;                                     // 新增

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author dafenqi
* @description 针对表【df_up_wire_frame_ink_climbing(线框油墨爬高)】的数据库操作Service实现
* @createDate 2025-07-24 15:46:00
*/
@Service
public class DfUpWireFrameInkClimbingServiceImpl extends ServiceImpl<DfUpWireFrameInkClimbingMapper, DfUpWireFrameInkClimbing>
    implements DfUpWireFrameInkClimbingService{

    @Autowired
    private DfUpWireFrameInkClimbingMapper dfUpWireFrameInkClimbingMapper;

    @Autowired
    private ApplicationEventPublisher publisher; // 新增

    private static final int MQ_BATCH_SIZE = 200; // 新增，与现有模块一致

    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        int startRow = 4; // 从第5行开始（索引是4）

        // 调整：表头在第一行（索引0）
        Row headerRow = sheet.getRow(0);
        validateHeader(headerRow);

        List<DfUpWireFrameInkClimbing> mqBatch = new ArrayList<>(MQ_BATCH_SIZE); // 新增

        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行

            DfUpWireFrameInkClimbing entity = new DfUpWireFrameInkClimbing();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            entity.setDate(getDateCellValue(row.getCell(i++)));
            entity.setLongSide1(getDoubleCellValue(row.getCell(i++)));
            entity.setLongSide2(getDoubleCellValue(row.getCell(i++)));
            entity.setGroove(getDoubleCellValue(row.getCell(i++)));
            entity.setShortGroove(getDoubleCellValue(row.getCell(i++)));

            entity.setShortSide(getDoubleCellValue(row.getCell(i++)));

            entity.setMachineCode(getStringCellValue(row.getCell(i++)));
            entity.setState(getStringCellValue(row.getCell(i++)));

            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            // 保存
            dfUpWireFrameInkClimbingMapper.insert(entity);

            // 新增：加入 MQ 批次并按阈值发布
            mqBatch.add(entity);
            if (mqBatch.size() >= MQ_BATCH_SIZE) {
                publishBatch(mqBatch);
                mqBatch.clear();
            }
        }

        // 新增：收尾发布剩余
        if (!mqBatch.isEmpty()) {
            publishBatch(mqBatch);
            mqBatch.clear();
        }

        workbook.close();
    }

    // 表头校验（校验第2~6列为：长边1、长边2、凹槽、凹槽短边、短边）
    private void validateHeader(Row headerRow) {
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头不存在或格式错误：未找到表头行（应在第1行）");
        }
        String[] expected = {"长边1", "长边2", "凹槽", "凹槽短边", "短边"};
        for (int i = 0; i < expected.length; i++) {
            Cell cell = headerRow.getCell(i + 1); // 从第2列起（索引1）
            String actual = (cell == null) ? "" : cell.toString().trim();
            if (!expected[i].equals(actual)) {
                throw new IllegalArgumentException(String.format(
                    "Excel表头不匹配",
                    i + 2, expected[i], actual
                ));
            }
        }
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
                val = val.replace(" ", "");

                // 解析格式：支持不补零格式的时间（注意单个数字）
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s");
                return sdf.parse(val);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 可记录日志
        }

        return null;
    }

    // 新增：发布事件封装
    private void publishBatch(List<DfUpWireFrameInkClimbing> batch) {
        if (batch == null || batch.isEmpty()) return;
        publisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(batch), DfUpWireFrameInkClimbing.class));
    }
}




