package com.biel.qmsgatherCgVn.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 通用 Excel 表头校验：支持单列/合并列的模糊包含断言
 */
public final class ExcelHeaderValidator {
    private ExcelHeaderValidator() {}

    public static void assertSingleHeaderFuzzy(Row headerRow, int colIndex, String expectedContains) {
        Cell cell = headerRow.getCell(colIndex);
        String actual = ExcelCellParsers.normalizeCellString(cell);
        if (!containsIgnoreCase(actual, expectedContains)) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (colIndex + 1) + "列应包含【" + expectedContains + "】, 实际为【" + (actual == null ? "空" : actual) + "】");
        }
    }

    public static void assertMergedHeaderFuzzy(Sheet sheet, int rowIndex, int startCol, int endCol, String expectedContains) {
        CellRangeAddress region = ExcelSheetUtils.findMergedRegion(sheet, rowIndex, startCol, endCol);
        if (region == null) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (startCol + 1) + "-" + (endCol + 1) + "列应为合并单元格且包含【" + expectedContains + "】, 但未检测到对应的合并区域");
        }
        Row firstRow = sheet.getRow(region.getFirstRow());
        Cell firstCell = firstRow != null ? firstRow.getCell(region.getFirstColumn()) : null;
        String actual = ExcelCellParsers.normalizeCellString(firstCell);
        if (!containsIgnoreCase(actual, expectedContains)) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (startCol + 1) + "-" + (endCol + 1) + "列合并单元格应包含【" + expectedContains + "】, 实际为【" + (actual == null ? "空" : actual) + "】");
        }
    }

    private static boolean containsIgnoreCase(String src, String needle) {
        if (src == null || needle == null) return false;
        return src.toLowerCase().contains(needle.toLowerCase());
    }
}