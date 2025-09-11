package com.biel.qmsgatherCgVn.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String val = cell.getStringCellValue().trim();
                if (val.isEmpty()) return null;
                val = val.replace("/", "-");
                val = val.replace(",", " ");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s");
                return sdf.parse(val);
            }
        } catch (Exception ignore) {
        }
        return null;
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
}