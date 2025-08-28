package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpSSBThreeDMachine;
import com.biel.qmsgatherCgVn.domain.vo.DfUpChamferHypotenuseVo;
import com.biel.qmsgatherCgVn.domain.vo.DfUpSSBThreeDMachineVo;
import com.biel.qmsgatherCgVn.service.DfUpSSBThreeDMachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/28 15:38
 */
@RestController
@RequestMapping("/vn/SSBThreeDMachine")
@CrossOrigin
@Api(tags = "vn SSB3D机 接口")
public class DfUpSSBThreeDMachineController {

    @Autowired
    private DfUpSSBThreeDMachineService dfUpSSBThreeDMachineService;

    @ApiOperation(value = "vn SSB3D机导入接口")
    @PostMapping("/importSSBThreeDMachine")
    public R importExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam("factory") String factory,
                         @RequestParam("model") String model,
                         @RequestParam("process") String process,
                         @RequestParam("test_project") String testProject,
                         @RequestParam("upload_name") String uploadName,
                         @RequestParam("batch_id") String batchId){
        try {
            dfUpSSBThreeDMachineService.importExcel(file, factory, model, process, testProject,uploadName,batchId);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入失败：" + e.getMessage());
        }
    }


    @GetMapping("/findSSBThreeDMachine")
    @ApiOperation(value = "vn SSB3D机查询接口")
    public R findDfUpSSBThreeDMachine(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpSSBThreeDMachine> dfUpSSBThreeDMachineQueryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpSSBThreeDMachineQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpSSBThreeDMachineQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpSSBThreeDMachineQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpSSBThreeDMachineQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpSSBThreeDMachineQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpSSBThreeDMachine> pageResult = dfUpSSBThreeDMachineService.page(
                new Page<>(page, limit),
                dfUpSSBThreeDMachineQueryWrapper
        );
        return R.ok(pageResult);
    }

    @GetMapping("/exportSSBThreeDMachine")
    @ApiOperation(value = "SSB3D机导出 Excel")
    public void exportDfUpSSBThreeDMachine(
            @RequestParam(required = false) String factory,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String testProject,
            @RequestParam(required = false) String startTestDate,
            @RequestParam(required = false) String endTestDate,
            HttpServletResponse response) throws IOException {

        QueryWrapper<DfUpSSBThreeDMachine> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            queryWrapper.between("date", startTestDate, endTestDate);  // 相应修改变量名
        }
        if (StringUtils.isNotBlank(model)) {
            queryWrapper.like("model", model);
        }
        if (StringUtils.isNotEmpty(factory)) {
            queryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotBlank(process)) {
            queryWrapper.like("process", process);
        }
        if (StringUtils.isNotBlank(testProject)) {
            queryWrapper.like("test_project", testProject);
        }

        queryWrapper.orderByDesc("date");

        List<DfUpSSBThreeDMachine> dataList = dfUpSSBThreeDMachineService.list(queryWrapper);

        // 映射为 VO 列表（只保留 VO 字段）
        List<DfUpSSBThreeDMachineVo> exportList = dataList.stream().map(e -> {
            DfUpSSBThreeDMachineVo v = new DfUpSSBThreeDMachineVo();
            v.setDate(e.getDate());
            v.setExternalLong1(e.getExternalLong1());
            v.setExternalLong2(e.getExternalLong2());
            v.setExternalWidth1(e.getExternalWidth1());
            v.setExternalWidth2(e.getExternalWidth2());
            v.setExternalWidth3(e.getExternalWidth3());
            v.setCutAngle(e.getCutAngle());
            v.setCutAngleLong(e.getCutAngleLong());
            v.setCutAngleWidth(e.getCutAngleWidth());
            v.setQrCodeLength(e.getQrCodeLength());
            v.setQrCodeWidth(e.getQrCodeWidth());
            v.setWhitePlateToglass(e.getWhitePlateToglass());
            v.setWhitePlateToGlassCenter(e.getWhitePlateToGlassCenter());
            v.setMachineCode(e.getMachineCode());
            v.setState(e.getState());
            v.setTestNumber(e.getTestNumber());
            v.setRemark(e.getRemark());
            return v;
        }).collect(Collectors.toList());

        ExportParams params = new ExportParams("SSB3D机记录", "SSB3D机尺寸");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DfUpSSBThreeDMachineVo.class, exportList);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("SSB3D机尺寸记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
