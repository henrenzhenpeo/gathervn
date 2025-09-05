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
}




