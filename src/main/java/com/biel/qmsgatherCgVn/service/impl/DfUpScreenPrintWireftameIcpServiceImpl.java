package com.biel.qmsgatherCgVn.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintWireftameIcpService;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintWireftameIcpMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
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

    @Override
    public void importExcel(MultipartFile file,String factory, String model,String process,String testProject,String uploadName,String batchId) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        int startRow = 8; // 从第9行开始（索引是8）
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

}




