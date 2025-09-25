package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpSSBThreeDMachine;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mapper.DfUpSSBThreeDMachineMapper;
import com.biel.qmsgatherCgVn.service.DfUpSSBThreeDMachineService;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.biel.qmsgatherCgVn.util.excel.ExcelCellParsers.*;

/**
 * Excel导入服务实现（处理合并单元格）
 *
 * @Author mr.feng
 * @DATE 2025/8/28 15:39
 */
@Service
public class DfUpSSBThreeDMachineServiceImpl extends ServiceImpl<DfUpSSBThreeDMachineMapper, DfUpSSBThreeDMachine> implements DfUpSSBThreeDMachineService {

    @Autowired
    private DfUpSSBThreeDMachineMapper dfUpSSBThreeDMachineMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    private static final int MQ_BATCH_SIZE = 200;
    // 新增：数据库批量写入大小（可根据 DB 性能调整）
    private static final int DB_BATCH_SIZE = 1000;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId,String createTime) throws Exception {

        InputStream is = null;
        Workbook workbook = null;
        try {
            is = file.getInputStream();
            workbook = WorkbookFactory.create(is); // 自动识别 xls/xlsx

            Sheet sheet = workbook.getSheetAt(0);
            // 去掉全量公式评估，避免大表卡顿
            // workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

            // 关键步骤1：收集所有合并区域的信息（行范围、列、值）
            Map<String, String> mergedCellValues = getMergedCellValues(sheet);

            int startRow = 11;
            List<DfUpSSBThreeDMachine> mqBatch = new ArrayList<>();
            // 新增：数据库批量列表
            List<DfUpSSBThreeDMachine> dbBatch = new ArrayList<>(DB_BATCH_SIZE);
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                Cell dateCell = row.getCell(0);
                if (dateCell == null || getDateCellValue(dateCell, mergedCellValues, r, 0) == null) continue;

                DfUpSSBThreeDMachine entity = new DfUpSSBThreeDMachine();
                int i = 0;

                entity.setFactory(factory);
                entity.setModel(model);
                entity.setProcess(process);
                entity.setTestProject(testProject);
                entity.setBatchId(batchId);

                // 关键步骤2：读取单元格时传入行号列号，检查是否属于合并区域
                Date recordDate = getDateCellValue(row.getCell(i), mergedCellValues, r, i++);
                entity.setDate(recordDate);
                entity.setExternalLong1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setExternalLong2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setExternalWidth1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setExternalWidth2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setExternalWidth3(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setCutAngle(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setCutAngleLong(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setCutAngleWidth(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setQrCodeLength(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setQrCodeWidth(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setWhitePlateToglass(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));
                entity.setWhitePlateToGlassCenter(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i), mergedCellValues, r, i++), 3));

                entity.setMachineCode(getStringCellValue(row.getCell(i), mergedCellValues, r, i++));
                entity.setState(getStringCellValue(row.getCell(i), mergedCellValues, r, i++));
                entity.setTestNumber(getIntegerCellValue(row.getCell(i), mergedCellValues, r, i++));
                entity.setRemark(getStringCellValue(row.getCell(i), mergedCellValues, r, i++));

                entity.setClasses(determineShift(recordDate));
                entity.setUploadName(uploadName);
                entity.setCreateTime(new Date());

                // 原来是：dfUpSSBThreeDMachineMapper.insert(entity);
                dbBatch.add(entity);
                if (dbBatch.size() >= DB_BATCH_SIZE) {
                    this.saveBatch(dbBatch, DB_BATCH_SIZE);
                    dbBatch.clear();
                }

                // 批量事件发布（由监听器统一发送到MQ）
                mqBatch.add(entity);
                if (mqBatch.size() >= MQ_BATCH_SIZE) {
                    publisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpSSBThreeDMachine.class));
                    mqBatch.clear();
                }
            }
            if (!mqBatch.isEmpty()) {
                publisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpSSBThreeDMachine.class));
                mqBatch.clear();
            }
            // 刷新尾批次
            if (!dbBatch.isEmpty()) {
                this.saveBatch(dbBatch, DB_BATCH_SIZE);
                dbBatch.clear();
            }
        } finally {
            // 语义更清晰：workbook 非空 → 只关 workbook；否则（创建失败）→ 只关 is
            if (workbook != null) {
                IOUtils.closeQuietly(workbook); // 会连带关闭底层输入资源
            } else {
                IOUtils.closeQuietly(is);
            }
        }
    }

    /**
     * 收集所有合并区域的信息，存储格式："行号_列号" -> 合并区域的首行值
     */
    private Map<String, String> getMergedCellValues(Sheet sheet) {
        Map<String, String> mergedValues = new HashMap<>();
        // 获取所有合并区域
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();

        for (CellRangeAddress region : mergedRegions) {
            int firstRow = region.getFirstRow();
            int lastRow = region.getLastRow();
            int firstCol = region.getFirstColumn();
            int lastCol = region.getLastColumn();

            Row firstRowObj = sheet.getRow(firstRow);
            if (firstRowObj == null) continue;
            Cell firstCell = firstRowObj.getCell(firstCol);
            // 空安全：首格可能为空
            String value = (firstCell == null) ? null : getStringCellValue(firstCell);

            for (int r = firstRow; r <= lastRow; r++) {
                for (int c = firstCol; c <= lastCol; c++) {
                    mergedValues.put(r + "_" + c, value);
                }
            }
        }
        return mergedValues;
    }

    /**
     * 读取字符串单元格值（支持合并单元格）
     */
/*
    private String getStringCellValue(Cell cell, Map<String, String> mergedCellValues, int rowNum, int colNum) {
        // 先检查是否属于合并区域
        String key = rowNum + "_" + colNum;
        if (mergedCellValues.containsKey(key)) {
            return mergedCellValues.get(key); // 返回合并区域的首行值
        }
        // 非合并区域，正常读取
        return getStringCellValue(cell);
    }
*/

    /**
     * 读取日期单元格值（支持合并单元格）
     */
    private Date getDateCellValue(Cell cell, Map<String, String> mergedCellValues, int rowNum, int colNum) {
        // 先检查是否属于合并区域
        String key = rowNum + "_" + colNum;
        if (mergedCellValues.containsKey(key)) {
            String mergedValue = mergedCellValues.get(key);
            if (mergedValue == null || mergedValue.isEmpty()) return null;
            // 解析合并区域的字符串值为日期
            return parseDateFromString(mergedValue);
        }
        // 非合并区域，正常读取
        return getDateCellValue(cell);
    }

    /**
     * 读取Double单元格值（支持合并单元格）
     */
    private Double getDoubleCellValue(Cell cell, Map<String, String> mergedCellValues, int rowNum, int colNum) {
        // 先检查是否属于合并区域
        String key = rowNum + "_" + colNum;
        if (mergedCellValues.containsKey(key)) {
            String mergedValue = mergedCellValues.get(key);
            if (mergedValue == null || mergedValue.isEmpty()) return null;
            try {
                return Double.parseDouble(mergedValue);
            } catch (Exception e) {
                return null;
            }
        }
        // 非合并区域，正常读取
        return getDoubleCellValue(cell);
    }

    /**
     * 读取Integer单元格值（支持合并单元格）
     */
    private Integer getIntegerCellValue(Cell cell, Map<String, String> mergedCellValues, int rowNum, int colNum) {
        // 先检查是否属于合并区域
        String key = rowNum + "_" + colNum;
        if (mergedCellValues.containsKey(key)) {
            String mergedValue = mergedCellValues.get(key);
            if (mergedValue == null || mergedValue.isEmpty()) return 0;
            try {
                return Integer.parseInt(mergedValue);
            } catch (Exception e) {
                return 0;
            }
        }
        // 非合并区域，正常读取
        return getIntegerCellValue(cell);
    }

    // 以下为原有方法（略有调整）
/*    private String determineShift(Date date) {
        if (date == null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 7 && hour < 19) {
            return "A";
        } else {
            return "B";
        }
    }*/

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false; // 修正逻辑：有非空单元格则行非空
            }
        }
        return true;
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

    public Integer getIntegerCellValue(Cell cell) {
        if (cell == null) {
            return 0;
        }

        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (int) cell.getNumericCellValue();
                case STRING:
                    String strValue = cell.getStringCellValue().trim();
                    return strValue.isEmpty() ? 0 : Integer.parseInt(strValue);
                case FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case NUMERIC:
                            return (int) cell.getNumericCellValue();
                        case STRING:
                            String formulaStrValue = cell.getStringCellValue().trim();
                            return formulaStrValue.isEmpty() ? 0 : Integer.parseInt(formulaStrValue);
                        default:
                            return 0;
                    }
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

/*    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            // 处理数字类型的字符串（如"1#"可能被误判为数字）
            return String.valueOf(cell.getNumericCellValue()).trim();
        }
        return cell.toString().trim();
    }*/

    private Date getDateCellValue(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                return parseDateFromString(cell.getStringCellValue().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析字符串为日期（提取通用逻辑）
     */
    private Date parseDateFromString(String val) {
        if (val == null) return null;
        val = val.trim();
        if (val.isEmpty()) return null;

        val = val.replace("/", "-");
        if (val.contains(",")) {
            val = val.replace(",", " ");
            if (val.split(":").length == 2) {
                val += ":00";
            }
        }

        SimpleDateFormat[] formats = {
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                new SimpleDateFormat("yyyy-M-d H:m:s"),
                new SimpleDateFormat("yyyy-M-d H:m")
        };

        for (SimpleDateFormat sdf : formats) {
            try {
                return sdf.parse(val);
            } catch (Exception e) {
                // 继续尝试下一个格式
            }
        }
        return null;
    }

}
