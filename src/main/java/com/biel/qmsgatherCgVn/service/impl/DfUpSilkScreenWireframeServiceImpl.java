package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm;
import com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe;
import com.biel.qmsgatherCgVn.service.DfUpSilkScreenWireframeService;
import com.biel.qmsgatherCgVn.mapper.DfUpSilkScreenWireframeMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress; // 新增：用于检测表头合并单元格
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet
        // 公式求值，确保公式单元格能返回正确的数值结果（包含 0）
        workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

        // 新增：表头校验（表头在第2行，从第2列开始）
        validateHeader(sheet);

        int startRow = 8; // 从第9行开始（索引是8）
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpSilkScreenWireframe entity = new DfUpSilkScreenWireframe();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            entity.setDate(getDateCellValue(row.getCell(i++)));
            entity.setOnePointy2(getDoubleCellValue(row.getCell(i++)));
            entity.setTwoPointy1(getDoubleCellValue(row.getCell(i++)));
            entity.setThreePointx(getDoubleCellValue(row.getCell(i++)));
            entity.setFourPointx(getDoubleCellValue(row.getCell(i++)));

//            entity.setFourPointx(getDoubleCellValue(row.getCell(i++)));
            entity.setFivePointy1(getDoubleCellValue(row.getCell(i++)));
            entity.setSixPointy2(getDoubleCellValue(row.getCell(i++)));

            entity.setSevenPointx(getDoubleCellValue(row.getCell(i++)));
            entity.setEightPointx(getDoubleCellValue(row.getCell(i++)));
            entity.setR1(getDoubleCellValue(row.getCell(i++)));

            entity.setR2(getDoubleCellValue(row.getCell(i++)));
            entity.setR3(getDoubleCellValue(row.getCell(i++)));

            entity.setR4(getDoubleCellValue(row.getCell(i++)));
            entity.setTwoRadiumCode(getDoubleCellValue(row.getCell(i++)));

            entity.setDebugMachinex(getDoubleCellValue(row.getCell(i++)));
            entity.setDebugMachiney1(getDoubleCellValue(row.getCell(i++)));
            entity.setDebugMachiney2(getDoubleCellValue(row.getCell(i++)));

            entity.setMachineCode(getStringCellValue(row.getCell(i++)));
            entity.setState(getStringCellValue(row.getCell(i++)));

            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            // 保存
            dfUpSilkScreenWireframeMapper.insert(entity);
        }

        workbook.close();
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
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING: {
                    String s = cell.getStringCellValue();
                    if (s == null) return null;
                    s = s.trim();
                    // 常见“空值”占位
                    if (s.isEmpty() || "-".equals(s)) return null;
                    // 去千分位逗号
                    s = s.replace(",", "");
                    return Double.parseDouble(s);
                }
                case FORMULA:
                    // 优先取缓存数值（evaluateAll 后通常有效；结果为 0 也能取到 0.0）
                    try {
                        return cell.getNumericCellValue();
                    } catch (IllegalStateException ex) {
                        // 不是数值则尝试按字符串解析
                        String fs = null;
                        try { fs = cell.getStringCellValue(); } catch (Exception ignore) {}
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

    /**
     * 表头默认在第2行（索引1），从第2列开始校验：
     *  - 第2-9列（索引1-8）：合并单元格，标题需包含“位置八点”
     *  - 第10-13列（索引9-12）：合并单元格，标题需包含“四个R角”
     *  - 第14列（索引13）：标题需包含“2D镭码位置”
     *  - 第15-17列（索引14-16）：合并单元格，标题需包含“丝印调机专用”
     */
    private void validateHeader(Sheet sheet) {
        int headerRowIndex = 1; // 第二行
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头校验失败：未找到第2行表头（索引1）");
        }

        // 2-9列 合并：位置八点
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 1, 8, "位置八点");

        // 10-13列 合并：四个R角
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 9, 12, "四个R角");

        // 14列：2D镭码位置（单列）
        assertSingleHeaderFuzzy(headerRow, 13, "2D镭码位置");

        // 15-17列 合并：丝印调机专用
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 14, 16, "丝印调机专用");
    }

    private void assertSingleHeaderFuzzy(Row headerRow, int colIndex, String expectedContains) {
        Cell cell = headerRow.getCell(colIndex);
        String actual = normalizeCellString(cell);
        if (!containsIgnoreCase(actual, expectedContains)) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (colIndex + 1) + "列应包含【" + expectedContains + "】, 实际为【" + (actual == null ? "空" : actual) + "】");
        }
    }

    private void assertMergedHeaderFuzzy(Sheet sheet, int rowIndex, int startCol, int endCol, String expectedContains) {
        CellRangeAddress region = findMergedRegion(sheet, rowIndex, startCol, endCol);
        if (region == null) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (startCol + 1) + "-" + (endCol + 1) + "列应为合并单元格且包含【" + expectedContains + "】, 但未检测到对应的合并区域");
        }
        Row firstRow = sheet.getRow(region.getFirstRow());
        Cell firstCell = firstRow != null ? firstRow.getCell(region.getFirstColumn()) : null;
        String actual = normalizeCellString(firstCell);
        if (!containsIgnoreCase(actual, expectedContains)) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (startCol + 1) + "-" + (endCol + 1) + "列合并单元格应包含【" + expectedContains + "】, 实际为【" + (actual == null ? "空" : actual) + "】");
        }
    }

    private CellRangeAddress findMergedRegion(Sheet sheet, int rowIndex, int startCol, int endCol) {
        for (CellRangeAddress region : sheet.getMergedRegions()) {
            boolean rowInRange = rowIndex >= region.getFirstRow() && rowIndex <= region.getLastRow();
            boolean colMatch = region.getFirstColumn() == startCol && region.getLastColumn() == endCol;
            if (rowInRange && colMatch) {
                return region;
            }
        }
        return null;
    }

    private String normalizeCellString(Cell cell) {
        if (cell == null) return null;
        String s = cell.toString();
        return s == null ? null : s.trim();
    }

    private boolean containsIgnoreCase(String src, String needle) {
        if (src == null || needle == null) return false;
        return src.toLowerCase().contains(needle.toLowerCase());
    }
}




