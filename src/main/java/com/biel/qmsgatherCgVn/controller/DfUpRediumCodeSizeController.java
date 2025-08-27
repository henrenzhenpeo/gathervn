package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpRadiumCodeSize;
import com.biel.qmsgatherCgVn.domain.vo.DfUpRadiumCodeSizeVo;
import com.biel.qmsgatherCgVn.service.DfUpRediumCodeSizeService;
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
 * @DATE 2025/8/26 13:37
 */
@RestController
@RequestMapping("/vn/RediumCodeSize")
@CrossOrigin
@Api(tags = "vn 镭码尺寸 接口")
public class DfUpRediumCodeSizeController {

    @Autowired
    private DfUpRediumCodeSizeService dfUpRediumCodeSizeService;

    @ApiOperation(value = "vn 镭码尺寸导入接口")
    @PostMapping("/importDfUpRediumCodeSize")
    public R importExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam("factory") String factory,
                         @RequestParam("model") String model,
                         @RequestParam("process") String process,
                         @RequestParam("test_project") String testProject,
                         @RequestParam("upload_name") String uploadName,
                         @RequestParam("batch_id") String batchId){
        try {
            dfUpRediumCodeSizeService.importExcel(file, factory, model, process, testProject,uploadName,batchId);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入失败：" + e.getMessage());
        }
    }


    @GetMapping("/findDfUpRediumCodeSize")
    @ApiOperation(value = "vn 镭码尺寸查询接口")
    public R findDfUpRediumCodeSize(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpRadiumCodeSize> dfUpRadiumCodeSizeQueryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpRadiumCodeSizeQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpRadiumCodeSizeQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpRadiumCodeSizeQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpRadiumCodeSizeQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpRadiumCodeSizeQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpRadiumCodeSize> pageResult = dfUpRediumCodeSizeService.page(
                new Page<>(page, limit),
                dfUpRadiumCodeSizeQueryWrapper
        );
        return R.ok(pageResult);
    }

    @GetMapping("/exportDfUpRediumCodeSize")
    @ApiOperation(value = "导出镭码尺寸 Excel")
    public void exportDfUpRediumCodeSize(
            @RequestParam(required = false) String factory,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String testProject,
            @RequestParam(required = false) String startTestDate,
            @RequestParam(required = false) String endTestDate,
            HttpServletResponse response) throws IOException {

        QueryWrapper<DfUpRadiumCodeSize> queryWrapper = new QueryWrapper<>();
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

        List<DfUpRadiumCodeSize> dataList = dfUpRediumCodeSizeService.list(queryWrapper);

        // 映射为 VO 列表（只保留 VO 字段）
        List<DfUpRadiumCodeSizeVo> exportList = dataList.stream().map(e -> {
            DfUpRadiumCodeSizeVo v = new DfUpRadiumCodeSizeVo();
            v.setDate(e.getDate());
            v.setQrCodeLength(e.getQrCodeLength());
            v.setQrCodeWidth(e.getQrCodeWidth());
            v.setBarcodeToglass(e.getBarcodeToglass());
            v.setXWhitePlateToGlassCenter(e.getXWhitePlateToGlassCenter());
            v.setLeftDistance(e.getLeftDistance());
            v.setRightDistance(e.getRightDistance());
            v.setMachineCode(e.getMachineCode());
            v.setState(e.getState());
            v.setTestNumber(e.getTestNumber());
            v.setRemark(e.getRemark());
            return v;
        }).collect(Collectors.toList());

        ExportParams params = new ExportParams("镭码尺寸记录", "镭码尺寸");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DfUpRadiumCodeSizeVo.class, exportList);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("镭码尺寸记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
