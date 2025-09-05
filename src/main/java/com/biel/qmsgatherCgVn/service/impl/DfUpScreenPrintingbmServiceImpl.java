package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintingbmService;
import com.biel.qmsgatherCgVn.mapper.DfUpScreenPrintingbmMapper;
import com.biel.qmsgatherCgVn.util.PoiZipSecurity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
* @author dafenqi
* @description 针对表【df_up_screen_printingbm(丝印BM2)】的数据库操作Service实现
* @createDate 2025-07-25 11:35:11
*/
@Service
public class DfUpScreenPrintingbmServiceImpl extends ServiceImpl<DfUpScreenPrintingbmMapper, DfUpScreenPrintingbm>
    implements DfUpScreenPrintingbmService{

    @Autowired
    private DfUpScreenPrintingbmMapper dfUpScreenPrintingbmMapper;

    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception {
        PoiZipSecurity.configure();
        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        int startRow = 8; // 从第9行开始（索引是8）
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpScreenPrintingbm entity = new DfUpScreenPrintingbm();
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
            entity.setWindowLength1(getDoubleCellValue(row.getCell(i++)));

            entity.setWindowLength2(getDoubleCellValue(row.getCell(i++)));
            entity.setWindowWide1(getDoubleCellValue(row.getCell(i++)));

            entity.setWindowWide2(getDoubleCellValue(row.getCell(i++)));



            entity.setDebugMachinex(getDoubleCellValue(row.getCell(i++)));
            entity.setDebugMachiney1(getDoubleCellValue(row.getCell(i++)));
            entity.setDebugMachiney2(getDoubleCellValue(row.getCell(i++)));

            entity.setMachineCode(getStringCellValue(row.getCell(i++)));
            entity.setState(getStringCellValue(row.getCell(i++)));

            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            // 保存
            dfUpScreenPrintingbmMapper.insert(entity);
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
}




