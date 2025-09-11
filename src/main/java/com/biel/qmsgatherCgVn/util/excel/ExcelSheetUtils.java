package com.biel.qmsgatherCgVn.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel Sheet 层工具
 */
public final class ExcelSheetUtils {
    private ExcelSheetUtils() {}

    /**
     * 判断整行是否为空：所有单元格均为 BLANK 或 toString 后为空白 才认为是空行。
     */
    public static boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (Cell cell : row) {
            if (cell == null) continue;
            if (cell.getCellType() == CellType.BLANK) continue;
            String s = cell.toString();
            if (s != null && !s.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 查找给定行区间与列区间完全匹配的合并区域。
     */
    public static CellRangeAddress findMergedRegion(Sheet sheet, int rowIndex, int startCol, int endCol) {
        for (CellRangeAddress region : sheet.getMergedRegions()) {
            boolean rowInRange = rowIndex >= region.getFirstRow() && rowIndex <= region.getLastRow();
            boolean colMatch = region.getFirstColumn() == startCol && region.getLastColumn() == endCol;
            if (rowInRange && colMatch) {
                return region;
            }
        }
        return null;
    }
}