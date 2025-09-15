package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.service.DfUpScreenPrintWireftameIcpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/vn/dfUpScreenPrintWireftameIcp")
@CrossOrigin
@Api(tags = "vn 丝印线框ICP接口")
public class DfUpScreenPrintWireftameIcpController {

    @Autowired
    private DfUpScreenPrintWireftameIcpService dfUpScreenPrintWireftameIcpService;

    @ApiOperation(value = "vn 丝印线框ICP导入接口")
    @PostMapping("/importDfUpScreenPrintWireftameIcp")
    public R importExcel(@RequestParam("file") MultipartFile file,
                              @RequestParam("factory") String factory,
                              @RequestParam("model") String model,
                              @RequestParam("process") String process,
                              @RequestParam("test_project") String testProject,
                              @RequestParam("upload_name") String uploadName,
                              @RequestParam("batch_id") String batchId) {
        try {
            dfUpScreenPrintWireftameIcpService.importExcel(file, factory, model, process, testProject,uploadName,batchId);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入失败：" + e.getMessage());
        }
    }


    @GetMapping("/findDfUpScreenPrintWireftameIcp")
    @ApiOperation(value = "vn 丝印线框ICP查询接口")
    public R findDfUpScreenPrintWireftameIcp(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        // 创建查询条件
        QueryWrapper<DfUpScreenPrintWireftameIcp> dfUpScreenPrintWireftameIcpQueryWrapper = new QueryWrapper<>();

        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpScreenPrintWireftameIcpQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpScreenPrintWireftameIcpQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpScreenPrintWireftameIcpQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpScreenPrintWireftameIcpQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpScreenPrintWireftameIcpQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpScreenPrintWireftameIcp> pageResult = dfUpScreenPrintWireftameIcpService.page(
                new Page<>(page, limit),
                dfUpScreenPrintWireftameIcpQueryWrapper
        );

        return R.ok(pageResult);
    }


    @GetMapping("/exportDfUpScreenPrintWireftameIcp")
    @ApiOperation(value = "导出丝印线框ICP Excel")
    public void exportDfUpScreenPrintWireftameIcp(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String testProject,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletResponse response) throws IOException {

        QueryWrapper<DfUpScreenPrintWireftameIcp> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            queryWrapper.between("date", startTime, endTime);
        }
        if (StringUtils.isNotBlank(model)) {
            queryWrapper.like("model", model);
        }
        if (StringUtils.isNotBlank(process)) {
            queryWrapper.like("process", process);
        }
        if (StringUtils.isNotBlank(testProject)) {
            queryWrapper.like("test_project", testProject);
        }

        queryWrapper.orderByDesc("date");

        List<DfUpScreenPrintWireftameIcp> dfUpScreenPrintWireftameIcpList = dfUpScreenPrintWireftameIcpService.list(queryWrapper);

        ExportParams params = new ExportParams("导出丝印线框ICP记录", "丝印线框ICP");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DfUpScreenPrintWireftameIcp.class, dfUpScreenPrintWireftameIcpList);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("导出丝印线框ICP记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }



}
