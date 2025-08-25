package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe;
import com.biel.qmsgatherCgVn.domain.DfUpTxPvdOverflowPlating;
import com.biel.qmsgatherCgVn.service.DfUpTxPvdOverflowPlatingService;
import com.biel.qmsgatherCgVn.mapper.DfUpTxPvdOverflowPlatingMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author dafenqi
* @description 针对表【df_up_tx_pvd_overflow_plating(tx-pvd 溢镀)】的数据库操作Service实现
* @createDate 2025-07-28 14:14:39
*/
@Service
public class DfUpTxPvdOverflowPlatingServiceImpl extends ServiceImpl<DfUpTxPvdOverflowPlatingMapper, DfUpTxPvdOverflowPlating>
    implements DfUpTxPvdOverflowPlatingService{

    @Autowired
    private DfUpTxPvdOverflowPlatingMapper dfUpTxPvdOverflowPlatingMapper;

    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName,String batchId) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        int startRow = 8; // 从第9行开始（索引是8）
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpTxPvdOverflowPlating entity = new DfUpTxPvdOverflowPlating();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            entity.setDate(getDateCellValue(row.getCell(i++)));
            entity.setSquareHoleHeight(getStringCellValue(row.getCell(i++)));
            entity.setSquareHoleWidth(getStringCellValue(row.getCell(i++)));
            entity.setLengthHoleCenterBmoLeft(getStringCellValue(row.getCell(i++)));
            entity.setLengthHoleCenterBmoLower(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleCenterSquareHoleCenter(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleCenterSmallHoleCenter(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleTopRound(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleLowerRound(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleWidth(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleTopCenterLowerCenter(getStringCellValue(row.getCell(i++)));
            entity.setLengthHoleCenterBmoRight(getStringCellValue(row.getCell(i++)));
            entity.setLengthHoleCenterBmoBottom(getStringCellValue(row.getCell(i++)));
            entity.setSmallCircleDiameter(getStringCellValue(row.getCell(i++)));
            entity.setSmallCircleCenterBm0Right(getStringCellValue(row.getCell(i++)));
            entity.setSmallCircleCenterBm0Lower(getStringCellValue(row.getCell(i++)));
            entity.setArHoleTop(getStringCellValue(row.getCell(i++)));
            entity.setArHoleLeft(getStringCellValue(row.getCell(i++)));
            entity.setArHoleLower(getStringCellValue(row.getCell(i++)));
            entity.setArHoleRight(getStringCellValue(row.getCell(i++)));
            entity.setArHoleCoatingDiameter(getStringCellValue(row.getCell(i++)));
            entity.setArHoleDiameter(getStringCellValue(row.getCell(i++)));
            entity.setResult(getStringCellValue(row.getCell(i++)));

            entity.setFactoryModel(getMergedCellValue(sheet, row, i++));
            entity.setMachinePotNumber(getMergedCellValue(sheet, row, i++));
            entity.setWhiteNightShift(getMergedCellValue(sheet, row, i++));


            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            // 保存
            dfUpTxPvdOverflowPlatingMapper.insert(entity);
        }

        workbook.close();
    }


    private String getMergedCellValue(Sheet sheet, Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell != null && cell.getCellType() != CellType.BLANK) {
            return getStringCellValue(cell);
        }

        // 如果该单元格为空，检查是否在合并单元格区域
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.isInRange(row.getRowNum(), columnIndex)) {
                Row firstRow = sheet.getRow(range.getFirstRow());
                if (firstRow != null) {
                    Cell firstCell = firstRow.getCell(range.getFirstColumn());
                    return getStringCellValue(firstCell);
                }
            }
        }

        return ""; // 如果没有值，返回空字符串
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
}




