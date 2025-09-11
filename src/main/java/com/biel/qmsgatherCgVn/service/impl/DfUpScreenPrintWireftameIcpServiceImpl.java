package com.biel.qmsgatherCgVn.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintWireftameIcpService;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintWireftameIcpMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;  // ← 新增：用于读取合并单元格区域
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author dafenqi
* @description 针对表【df_up_screen_print_wireftame_icp(丝印线框icp)】的数据库操作Service实现
* @createDate 2025-07-22 16:36:25
*/
@Service
public class DfUpScreenPrintWireftameIcpServiceImpl extends ServiceImpl<DfUpScreenPrintWireftameIcpMapper, DfUpScreenPrintWireftameIcp>
    implements DfUpScreenPrintWireftameIcpService {

    @Autowired
    private DfUpScreenPrintWireftameIcpMapper mapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final int MQ_BATCH_SIZE = 200;

    @Override
    public void importExcel(MultipartFile file,String factory, String model,String process,String testProject,String uploadName,String batchId) throws Exception {
        PoiZipSecurity.configure();
        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        // 新增：表头校验（表头在第2行，即索引1）
        validateHeader(sheet);

        int startRow = 5; // 从第9行开始（索引是8）
        List<DfUpScreenPrintWireftameIcp> mqBatch = new ArrayList<>();
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpScreenPrintWireftameIcp entity = new DfUpScreenPrintWireftameIcp();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            entity.setDate(getDateCellValue(row.getCell(i++)));
            entity.setR1(getDoubleCellValue(row.getCell(i++)));
            entity.setR2(getDoubleCellValue(row.getCell(i++)));
            entity.setR3(getDoubleCellValue(row.getCell(i++)));
            entity.setR4(getDoubleCellValue(row.getCell(i++)));

            entity.setUpperLongSide1(getDoubleCellValue(row.getCell(i++)));
            entity.setUpperLongSide2(getDoubleCellValue(row.getCell(i++)));
            entity.setUpperLongSide3(getDoubleCellValue(row.getCell(i++)));

            entity.setLowerLongSide1(getDoubleCellValue(row.getCell(i++)));
            entity.setLowerLongSide2(getDoubleCellValue(row.getCell(i++)));
            entity.setLowerLongSide3(getDoubleCellValue(row.getCell(i++)));

            entity.setShortDegeGroove1(getDoubleCellValue(row.getCell(i++)));
            entity.setShortDegeGroove2(getDoubleCellValue(row.getCell(i++)));

            entity.setNoShortDegeGroove1(getDoubleCellValue(row.getCell(i++)));
            entity.setNoShortDegeGroove2(getDoubleCellValue(row.getCell(i++)));

            entity.setDegeGroove(getDoubleCellValue(row.getCell(i++)));

            entity.setLongAppearance1(getDoubleCellValue(row.getCell(i++)));
            entity.setLongAppearance2(getDoubleCellValue(row.getCell(i++)));
            entity.setExteriorWidth(getDoubleCellValue(row.getCell(i++)));
            entity.setExteriorWidth2(getDoubleCellValue(row.getCell(i++)));

            entity.setMaxValue(getDoubleCellValue(row.getCell(i++)));

            entity.setMachineCode(getStringCellValue(row.getCell(i++)));
            entity.setState(getStringCellValue(row.getCell(i++)));

            entity.setUploadName(uploadName);
            entity.setUploadCreate(new Date());

            // 保存
            mapper.insert(entity);

            // 批量事件发布（由监听器统一发送到MQ）
            mqBatch.add(entity);
            if (mqBatch.size() >= MQ_BATCH_SIZE) {
                eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintWireftameIcp.class));
                mqBatch.clear();
            }
        }
        if (!mqBatch.isEmpty()) {
            eventPublisher.publishEvent(new DataImportedEvent<>(new ArrayList<>(mqBatch), DfUpScreenPrintWireftameIcp.class));
            mqBatch.clear();
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
                return cell.getDateCellValue(); // 原生日期格式
            } else if (cell.getCellType() == CellType.STRING) {
                String val = cell.getStringCellValue().trim();

                if (val.contains(",")) {
                    val = val.replace(",", " "); // 把 2025/05/23,07:56 → 2025/05/23 07:56
                }

                // 兼容 / 和 - 两种日期分隔符
                val = val.replace("/", "-");

                // SimpleDateFormat 不支持自动识别多个格式，可尝试多个解析
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return sdf.parse(val);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 也可记录日志
        }

        return null;
    }

    private void validateHeader(Sheet sheet) {
        int headerRowIndex = 1; // 第二行
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            throw new IllegalArgumentException("Excel表头校验失败：未找到第2行表头（索引1）");
        }

        // 单列校验
        assertSingleHeader(headerRow, 0, "时间");
        assertSingleHeader(headerRow, 1, "左上R角");
        assertSingleHeader(headerRow, 2, "右上R角");
        assertSingleHeader(headerRow, 3, "左下R角");
        assertSingleHeader(headerRow, 4, "右下R角");
        assertSingleHeader(headerRow, 5, "上长边左");
        assertSingleHeader(headerRow, 6, "上长边中");
        assertSingleHeader(headerRow, 7, "上长边右");
        assertSingleHeader(headerRow, 8, "下长边左");
        assertSingleHeader(headerRow, 9, "中长边");
        assertSingleHeader(headerRow, 10, "右长边");
        assertSingleHeader(headerRow, 11, "凹槽短边1");
        assertSingleHeader(headerRow, 12, "凹槽短边2");
        assertSingleHeader(headerRow, 13, "凹槽短边3");
        assertSingleHeader(headerRow, 14, "凹槽短边4");
        assertSingleHeader(headerRow, 15, "凹槽");
        assertSingleHeader(headerRow, 16, "外形长1");
        assertSingleHeader(headerRow, 17, "外形长2");
        assertSingleHeader(headerRow, 18, "外形宽1");
        assertSingleHeader(headerRow, 19, "外形宽2");
        assertSingleHeader(headerRow, 20, "max");
    }

    private void assertSingleHeader(Row headerRow, int colIndex, String expected) {
        Cell cell = headerRow.getCell(colIndex);
        String actual = normalizeCellString(cell);
        if (!equalsIgnoreCaseSafe(actual, expected)) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (colIndex + 1) + "列应为【" + expected + "】, 实际为【" + (actual == null ? "空" : actual) + "】");
        }
    }

    private void assertMergedHeader(Sheet sheet, int rowIndex, int startCol, int endCol, String expected) {
        CellRangeAddress region = findMergedRegion(sheet, rowIndex, startCol, endCol);
        if (region == null) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (startCol + 1) + "-" + (endCol + 1) + "列应为合并单元格【" + expected + "】, 但未检测到对应的合并区域");
        }
        Row firstRow = sheet.getRow(region.getFirstRow());
        Cell firstCell = firstRow != null ? firstRow.getCell(region.getFirstColumn()) : null;
        String actual = normalizeCellString(firstCell);
        if (!equalsIgnoreCaseSafe(actual, expected)) {
            throw new IllegalArgumentException("Excel表头校验失败：第" + (startCol + 1) + "-" + (endCol + 1) + "列合并单元格应为【" + expected + "】, 实际为【" + (actual == null ? "空" : actual) + "】");
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

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b);
    }

}




