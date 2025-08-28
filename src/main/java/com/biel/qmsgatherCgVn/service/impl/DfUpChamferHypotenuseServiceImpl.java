package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.mapper.DfUpChamferHypotenuseMapper;
import com.biel.qmsgatherCgVn.service.DfUpChamferHypotenuseService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/27 16:02
 */
@Service
public class DfUpChamferHypotenuseServiceImpl extends ServiceImpl<DfUpChamferHypotenuseMapper, DfUpChamferHypotenuse> implements DfUpChamferHypotenuseService {

    @Autowired
    private DfUpChamferHypotenuseMapper dfUpChamferHypotenuseMapper;
    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId) throws Exception {

        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        int startRow = 11; // 从第12行开始（索引是11）
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpChamferHypotenuse entity = new DfUpChamferHypotenuse();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            Date recordDate = getDateCellValue(row.getCell(i++));
            entity.setDate(recordDate);
            // 基础测量值：保留3位小数
            entity.setShortUr1(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongUr2(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongLr3(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setShortLr4(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setShortLl5(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongLl6(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setLongUl7(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setShortUl8(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            // 平均值：保留4位小数
            entity.setAvg(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 4));
            entity.setStd1to4(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setStd2to7(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setStd3to6(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));
            entity.setStd5to8(roundToDecimalPlaces(getDoubleCellValue(row.getCell(i++)), 3));

            entity.setMachineCode(getStringCellValue(row.getCell(i++)));
            entity.setState(getStringCellValue(row.getCell(i++)));
            entity.setTestNumber(getIntegerCellValue(row.getCell(i++)));
            entity.setRemark(getStringCellValue(row.getCell(i++)));

            entity.setClasses(determineShift(recordDate));
            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            dfUpChamferHypotenuseMapper.insert(entity);
        }
    }

    private String determineShift(Date date) {
        if (date == null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // 8点到20点是A班次，20点到次日8点是B班次
        if (hour >= 7 && hour < 19) {
            return "A";
        } else {
            return "B";
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
    if (cell == null) return 0.0;

    try {
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                String stringValue = cell.getStringCellValue().trim();
                if (stringValue.isEmpty()) {
                    return 0.0;
                }
                // 直接使用 Double.parseDouble，它能自动识别负数
                return Double.parseDouble(stringValue);
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        return cell.getNumericCellValue();
                    case STRING:
                        String formulaStrValue = cell.getStringCellValue().trim();
                        if (formulaStrValue.isEmpty()) {
                            return 0.0;
                        }
                        return Double.parseDouble(formulaStrValue);
                    default:
                        return 0.0;
                }
            default:
                return 0.0;
        }
    } catch (NumberFormatException e) {
        // 处理数字格式异常，返回默认值而不是让程序崩溃
        return 0.0;
    } catch (Exception e) {
        // 捕获其他可能的异常
        return 0.0;
    }
}


    public Integer getIntegerCellValue(Cell cell) {
        if (cell == null) {
            //System.out.println("单元格为空");
            return 0;
        }

        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    int value = (int) cell.getNumericCellValue();
                    //System.out.println("读取到数值: " + value);
                    return value;
                case STRING:
                    String strValue = cell.getStringCellValue().trim();
                    //System.out.println("读取到字符串: '" + strValue + "'");
                    if (strValue.isEmpty()) {
                        return 0;
                    }
                    return Integer.parseInt(strValue);
                case FORMULA:
                    // 处理公式单元格 - 获取公式计算后的值
                    //System.out.println("检测到公式单元格");
                    switch (cell.getCachedFormulaResultType()) {
                        case NUMERIC:
                            int formulaValue = (int) cell.getNumericCellValue();
                            //System.out.println("公式计算结果: " + formulaValue);
                            return formulaValue;
                        case STRING:
                            String formulaStrValue = cell.getStringCellValue().trim();
                            //System.out.println("公式字符串结果: '" + formulaStrValue + "'");
                            if (formulaStrValue.isEmpty()) {
                                return 0;
                            }
                            return Integer.parseInt(formulaStrValue);
                        default:
                            //System.out.println("公式结果类型不支持: " + cell.getCachedFormulaResultType());
                            return 0;
                    }
                default:
                    //System.out.println("不支持的单元格类型: " + cell.getCellType());
                    return 0;
            }
        } catch (Exception e) {
            //System.out.println("解析单元格异常: " + e.getMessage());
            return 0;
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

                // 处理多种可能的日期格式
                // 格式1: 2025-08-19,20:54 -> 2025-08-19 20:54:00
                if (val.contains(",")) {
                    // 替换逗号为时间分隔符
                    val = val.replace(",", " ");
                    // 如果没有秒数，则添加默认秒数
                    if (val.split(":").length == 2) {
                        val += ":00";
                    }
                }

                // 尝试多种日期格式进行解析
                SimpleDateFormat[] formats = {
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                        new SimpleDateFormat("yyyy-M-d H:m:s")
                };

                for (SimpleDateFormat sdf : formats) {
                    try {
                        return sdf.parse(val);
                    } catch (Exception e) {
                        // 继续尝试下一个格式
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 可记录日志
        }

        return null;

    }
    
    /**
     * 保留指定小数位数
     */
    private double roundToDecimalPlaces(double value, int decimalPlaces) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return 0.0;
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
    }
}
