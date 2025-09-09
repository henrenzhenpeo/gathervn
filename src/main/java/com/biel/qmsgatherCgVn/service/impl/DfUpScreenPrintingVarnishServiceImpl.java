package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintingVarnishService;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintingVarnishMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress; // 新增：用于校验合并单元格
// 新增：POI Zip 安全设置
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author dafenqi
* @description 针对表【df_up_screen_printing_varnish(丝印光油)】的数据库操作Service实现
* @createDate 2025-07-24 16:34:35
*/
@Service
public class DfUpScreenPrintingVarnishServiceImpl extends ServiceImpl<DfUpScreenPrintingVarnishMapper, DfUpScreenPrintingVarnish>
    implements DfUpScreenPrintingVarnishService{

    @Autowired
    private DfUpScreenPrintingVarnishMapper dfUpScreenPrintingVarnishMapper;
    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception {
        // 使用抽取后的工具类进行一次性配置
        PoiZipSecurity.configure();

        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        // 新增：表头校验（表头在第3行，即索引2；从第2列开始校验）
        validateHeader(sheet);

        int startRow = 8; // 从第9行开始（索引是8）
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpScreenPrintingVarnish entity = new DfUpScreenPrintingVarnish();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            entity.setDate(getDateCellValue(row.getCell(i++)));
            entity.setOnePointy2(getDoubleCellValue(row.getCell(i++)));
            entity.setTwoPointy1(getDoubleCellValue(row.getCell(i++)));
            entity.setThreePoint(getDoubleCellValue(row.getCell(i++)));
            entity.setGroove(getDoubleCellValue(row.getCell(i++)));

            entity.setFourPointx(getDoubleCellValue(row.getCell(i++)));
            entity.setFivePointy1(getDoubleCellValue(row.getCell(i++)));
            entity.setSixPointy2(getDoubleCellValue(row.getCell(i++)));

            entity.setSevenPoint(getDoubleCellValue(row.getCell(i++)));
            entity.setEightPointx(getDoubleCellValue(row.getCell(i++)));
            entity.setTwoCodeWindow1(getDoubleCellValue(row.getCell(i++)));

            entity.setTwoCodeWindow2(getDoubleCellValue(row.getCell(i++)));
            entity.setLightOilTopReference1(getDoubleCellValue(row.getCell(i++)));

            entity.setLightOilTopReference2(getDoubleCellValue(row.getCell(i++)));
            entity.setTwoCodeCenter1(getDoubleCellValue(row.getCell(i++)));

            entity.setTwoCodeCenter2(getDoubleCellValue(row.getCell(i++)));
            entity.setTwoCodeTopCenter1(getDoubleCellValue(row.getCell(i++)));
            entity.setTwoCodeTopCenter2(getDoubleCellValue(row.getCell(i++)));

            entity.setDebugMachinex(getDoubleCellValue(row.getCell(i++)));
            entity.setDebugMachiney1(getDoubleCellValue(row.getCell(i++)));
            entity.setDebugMachiney2(getDoubleCellValue(row.getCell(i++)));

            entity.setMachineCode(getStringCellValue(row.getCell(i++)));
            entity.setState(getStringCellValue(row.getCell(i++)));

            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            // 保存
            dfUpScreenPrintingVarnishMapper.insert(entity);
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
     * 表头默认在第3行（索引2），从第2列开始校验：
     *  1: 1点Y2（精确）
     *  2: 2点Y1（精确）
     *  3-5: 合并，包含“3点，凹槽，四点X”（模糊）
     *  6: 5点Y1（精确）
     *  7: 6点Y2（精确）
     *  8-9: 合并，包含“7点，8点X”（模糊）
     *  9-10: 均包含“2D码到视窗”（模糊）——注意第10列同时在合并区域内
     *  11-12: 合并，包含“光油上边到基准C”（模糊）
     *  13-14: 合并，包含“光油内边到玻璃中心距离”（模糊）
     *  15-16: 合并，包含“上端到玻中心”（模糊）
     */
    private void validateHeader(Sheet sheet) {
        int headerRowIndex = 2; // 第三行
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头校验失败：未找到第3行表头（索引2）");
        }

        // 单列（精确匹配）
        assertSingleHeaderFuzzy(headerRow, 1, "1点");
        assertSingleHeaderFuzzy(headerRow, 2, "2点");
        assertSingleHeaderFuzzy(headerRow, 6, "5点");
        assertSingleHeaderFuzzy(headerRow, 7, "6点");

        // 合并块（模糊匹配）
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 3, 5, "3点，凹槽，四点");
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 8, 9, "7点，8点");

        // 两列均需模糊匹配“2D码到视窗”
        assertSingleHeaderFuzzy(headerRow, 10,  "2D码到视窗");
        assertSingleHeaderFuzzy(headerRow, 11, "2D码到视窗");

        // 后续合并块
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 12, 13, "光油上边到基准C");
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 14, 15, "光油内边到玻璃中心距离");
        assertMergedHeaderFuzzy(sheet, headerRowIndex, 16, 17, "上端到玻中心");
    }

    private void assertSingleHeaderExact(Row headerRow, int colIndex, String expected) {
        String actual = normalizeCellString(headerRow.getCell(colIndex));
        if (!equalsIgnoreCaseSafe(actual, expected)) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (colIndex + 1) + "列应为【" + expected + "】, 实际为【" + (actual == null ? "空" : actual) + "】");
        }
    }

    private void assertSingleHeaderFuzzy(Row headerRow, int colIndex, String expectedContains) {
        String actual = normalizeCellString(headerRow.getCell(colIndex));
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

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b);
    }
}




