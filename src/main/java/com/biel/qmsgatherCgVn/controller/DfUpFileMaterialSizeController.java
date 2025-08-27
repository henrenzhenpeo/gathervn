package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpBaige;
import com.biel.qmsgatherCgVn.domain.DfUpFileMaterialSize;
import com.biel.qmsgatherCgVn.domain.DfUpInkDensity;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm;
import com.biel.qmsgatherCgVn.service.DfUpFileMaterialSizeService;
import com.biel.qmsgatherCgVn.util.DateUtil;
import com.biel.qmsgatherCgVn.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/vn/dfUpFileMaterialSize")
@CrossOrigin
@Api(tags = "vn 膜材尺寸接口")
public class DfUpFileMaterialSizeController {

    @Autowired
    private DfUpFileMaterialSizeService dfUpFileMaterialSizeService;


    @ApiOperation(value = "vn 膜材尺寸导入接口")
    @PostMapping("/importDfUpFileMaterialSize")
    public R importExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam("factory") String factory,
                         @RequestParam("model") String model,
                         @RequestParam("process") String process,
                         @RequestParam("test_project") String testProject,
                         @RequestParam("upload_name") String uploadName,
                         @RequestParam("batch_id") String batchId) {
        try {
            dfUpFileMaterialSizeService.importExcel(file, factory, model, process, testProject,uploadName,batchId);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入失败：" + e.getMessage());
        }
    }

    @GetMapping("/findDfUpFileMaterialSize")
    @ApiOperation(value = "vn 膜材尺寸查询接口")
    public R findDfUpFileMaterialSize(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpFileMaterialSize> dfUpFileMaterialSizeQueryWrapper = new QueryWrapper<>();

        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpFileMaterialSizeQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpFileMaterialSizeQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpFileMaterialSizeQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpFileMaterialSizeQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpFileMaterialSizeQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpFileMaterialSize> pageResult = dfUpFileMaterialSizeService.page(
                new Page<>(page, limit),
                dfUpFileMaterialSizeQueryWrapper
        );

        return R.ok(pageResult);
    }


    @GetMapping("/exportDfUpFileMaterialSize")
    @ApiOperation(value = "导出vn 膜材尺寸 Excel")
    public void exportDfUpFileMaterialSize(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String testProject,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletResponse response) throws IOException {

        // 条件查询构造器
        QueryWrapper<DfUpFileMaterialSize> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            queryWrapper.between("date", startTime, endTime);
        }
        if (StringUtils.isNotBlank(model)) {
            queryWrapper.eq("model", model);
        }
        if (StringUtils.isNotBlank(process)) {
            queryWrapper.eq("process", process);
        }
        if (StringUtils.isNotBlank(testProject)) {
            queryWrapper.like("test_project", testProject);
        }
        queryWrapper.orderByDesc("date");

        // 查询数据
        List<DfUpFileMaterialSize> dfUpFileMaterialSizeList = dfUpFileMaterialSizeService.list(queryWrapper);

        // 导出 Excel
        ExportParams exportParams = new ExportParams("膜材尺寸记录", "膜材尺寸");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, DfUpFileMaterialSize.class, dfUpFileMaterialSizeList);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("膜材尺寸导出记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出文件
        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
