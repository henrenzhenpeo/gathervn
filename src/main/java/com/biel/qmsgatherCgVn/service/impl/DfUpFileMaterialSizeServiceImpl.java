package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpFileMaterialSize;
import com.biel.qmsgatherCgVn.service.DfUpFileMaterialSizeService;
import com.biel.qmsgatherCgVn.mapper.DfUpFileMaterialSizeMapper;
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
* @description 针对表【df_up_file_material_size(膜材尺寸)】的数据库操作Service实现
* @createDate 2025-07-28 14:16:14
*/
@Service
public class DfUpFileMaterialSizeServiceImpl extends ServiceImpl<DfUpFileMaterialSizeMapper, DfUpFileMaterialSize>
    implements DfUpFileMaterialSizeService{

    @Autowired
    private DfUpFileMaterialSizeMapper dfUpFileMaterialSizeMapper;

    @Override
    public void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName,String batchId) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream()); // ✅ 自动识别 xls/xlsx

        Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

        int startRow = 7; // 从第9行开始（索引是8）
        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);

            if (row == null) continue; // 不跳过非空的行
            Cell dateCell = row.getCell(0);
            if (dateCell == null || getDateCellValue(dateCell) == null) continue;
            DfUpFileMaterialSize entity = new DfUpFileMaterialSize();
            int i = 0;

            entity.setFactory(factory);
            entity.setModel(model);
            entity.setProcess(process);
            entity.setTestProject(testProject);
            entity.setBatchId(batchId);

            entity.setDate(getDateCellValue(row.getCell(i++)));
            entity.setMembraneLength1(getStringCellValue(row.getCell(i++)));
            entity.setMembraneLength2(getStringCellValue(row.getCell(i++)));
            entity.setMembraneWidth1(getStringCellValue(row.getCell(i++)));
            entity.setMembraneWidth2(getStringCellValue(row.getCell(i++)));
            entity.setArHoleDiameter(getStringCellValue(row.getCell(i++)));
            entity.setTrueRoundness1(getStringCellValue(row.getCell(i++)));
            entity.setArHoleCenterTopy(getStringCellValue(row.getCell(i++)));
            entity.setArHoleCenterTopx(getStringCellValue(row.getCell(i++)));
            entity.setSquareHoleLength(getStringCellValue(row.getCell(i++)));
            entity.setSquareHoleWidth(getStringCellValue(row.getCell(i++)));
            entity.setSquareHoleTopy(getStringCellValue(row.getCell(i++)));
            entity.setSquareHoleTopx(getStringCellValue(row.getCell(i++)));
            entity.setLengthHoleDiameterTop(getStringCellValue(row.getCell(i++)));
            entity.setTrueRoundness2(getStringCellValue(row.getCell(i++)));
            entity.setLengthHoleDiameterLower(getStringCellValue(row.getCell(i++)));
            entity.setTrueRoundness3(getStringCellValue(row.getCell(i++)));
            entity.setLengthHoleLength(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleWidth(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleTopLongHoleLowery(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleCenterTopy(getStringCellValue(row.getCell(i++)));
            entity.setLongHoleCenterTopx(getStringCellValue(row.getCell(i++)));
            entity.setSmallCircleDiameter(getStringCellValue(row.getCell(i++)));
            entity.setTrueRoundness4(getStringCellValue(row.getCell(i++)));
            entity.setSmallHoleCenterSquareHolex(getStringCellValue(row.getCell(i++)));
            entity.setSmallHoleCenterTopy(getStringCellValue(row.getCell(i++)));
            entity.setQrCodeWidth(getStringCellValue(row.getCell(i++)));
            entity.setQrCodeHigh(getStringCellValue(row.getCell(i++)));
            entity.setQrCodeMaterialCenterx(getStringCellValue(row.getCell(i++)));
            entity.setQrCodeMaterialCentery(getStringCellValue(row.getCell(i++)));


            entity.setUploadName(uploadName);
            entity.setCreateTime(new Date());

            // 保存
            dfUpFileMaterialSizeMapper.insert(entity);
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




