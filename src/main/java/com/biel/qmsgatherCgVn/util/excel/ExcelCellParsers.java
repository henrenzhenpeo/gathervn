package com.biel.qmsgatherCgVn.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 通用的 Excel 单元格解析工具
 */
public final class ExcelCellParsers {
    //private ExcelCellParsers() {}




    /**
     * 解析数值：支持 NUMERIC、STRING（去空格、千分位、短横线“-”）以及 FORMULA。
     * 任何解析异常均返回 null。
     */
    public static Double getDoubleCellValue(Cell cell) {
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING: {
                    String s = cell.getStringCellValue();
                    if (s == null) return null;
                    s = s.trim();
                    if (s.isEmpty() || "-".equals(s)) return null;
                    s = s.replace(",", "");
                    return Double.parseDouble(s);
                }
                case FORMULA:
                    try {
                        return cell.getNumericCellValue();
                    } catch (IllegalStateException ex) {
                        String fs = null;
                        try {
                            fs = cell.getStringCellValue();
                        } catch (Exception ignore) {}
                        if (fs == null) fs = cell.toString();
                        if (fs == null) return null;
                        fs = fs.trim();
                        if (fs.isEmpty() || "-".equals(fs)) return null;
                        fs = fs.replace(",", "");
                        return Double.parseDouble(fs);
                    }
                case BLANK:
                case BOOLEAN:
                case ERROR:
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
 * 解析日期：支持 Excel 日期数值，以及字符串日期（/ 替换为 -，格式 yyyy-M-d H:m:s）
 * 解析失败返回 null。
 */
public static Date getDateCellValue(Cell cell) {
    if (cell == null) return null;

    // Excel 数值型且为日期格式：直接取日期
    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
        return cell.getDateCellValue();
    }

    // 严格字符串解析：仅支持
    // 1) yyyy/M/d,HH:mm:ss（示例：2025/6/20,8:30:35）
    // 2) yyyy/M/d HH:mm:ss（示例：2025/6/20 8:30:35）
    // 3) yyyy/M/d,HH:mm（示例：2025/6/20,8:30）- 新增支持
    // 4) yyyy/M/d HH:mm（示例：2025/6/20 8:30）- 新增支持
    if (cell.getCellType() == CellType.STRING) {
        String val = cell.getStringCellValue();
        if (val != null) val = val.trim();

        if (val == null || val.isEmpty()) {
            throw new IllegalArgumentException(
                "日期字符串为空。仅支持的格式：yyyy/M/d,HH:mm:ss 或 yyyy/M/d HH:mm:ss（其中月份与日期可为1或2位数字）。"
            );
        }

        String[] patterns = {
            "yyyy/M/d,HH:mm:ss",   // 逗号，无空格，含秒
            "yyyy/M/d HH:mm:ss",   // 空格，含秒
            "yyyy/M/d,HH:mm",      // 逗号，无空格，不含秒
            "yyyy/M/d HH:mm"       // 空格，不含秒
        };

        for (String p : patterns) {
            SimpleDateFormat sdf = new SimpleDateFormat(p);
            sdf.setLenient(false);
            java.text.ParsePosition pos = new java.text.ParsePosition(0);
            Date parsed = sdf.parse(val, pos);
            if (parsed != null && pos.getIndex() == val.length()) {
                return parsed;
            }
        }

        throw new IllegalArgumentException(
            "无效的日期格式：\"" + val + "\"。仅支持：yyyy/M/d,HH:mm:ss 或 yyyy/M/d HH:mm:ss（其中月份与日期可为1或2位数字）。"
        );
    }

    throw new IllegalArgumentException("不支持的单元格类型用于日期解析：" + cell.getCellType());
}


    /**
     * 标准化 Cell 的字符串：toString 并 trim；null 安全。
     */
    public static String normalizeCellString(Cell cell) {
        if (cell == null) return null;
        String s = cell.toString();
        return s == null ? null : s.trim();
    }

    // 仍保留：用于读取字符串字段（如 MachineCode、State）
    /**
     * 读取字符串单元格值：使用 Cell.toString() 并去除首尾空白；null 安全。
     * 如需更复杂行为（例如数字单元格转字符串、保留格式等），可在此统一增强。
     */
    public static String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        return cell.toString().trim();
    }

    /**
     * 读取字符串单元格值（支持合并单元格）
     */
    public static String getStringCellValue(Cell cell, Map<String, String> mergedCellValues, int rowNum, int colNum) {
        // 先检查是否属于合并区域
        String key = rowNum + "_" + colNum;
        if (mergedCellValues.containsKey(key)) {
            return mergedCellValues.get(key); // 返回合并区域的首行值
        }
        // 非合并区域，正常读取
        return getStringCellValue(cell);
    }

    public static Integer getIntegerCellValue(Cell cell) {
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

    public static String determineShift(Date date) {
        if (date == null) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // 7点到19点是A班次，19点到次日7点是B班次
        if (hour >= 7 && hour < 19) {
            return "A班";
        } else {
            return "B班";
        }
    }

    /**
     * 保留指定小数位数
     */
    public double roundToDecimalPlaces(double value, int decimalPlaces) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return 0.0;
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
    }
}