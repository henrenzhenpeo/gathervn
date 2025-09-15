package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpBaige;
import com.biel.qmsgatherCgVn.service.DfUpBaigeService;
import com.biel.qmsgatherCgVn.util.DateUtil;
import com.biel.qmsgatherCgVn.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/vn/dfUpInkDensity")
@CrossOrigin
@Api(tags = "vn 百格 接口")
public class DfUpBaigeController {

    @Autowired
    private DfUpBaigeService dfUpBaigeService;


    @PostMapping("/uploadBaige")
    @ApiOperation(value = "vn 百格上传接口")
    public Result uploadBaige(@RequestBody List<DfUpBaige> dfUpBaigeList){
        for (DfUpBaige dfUpBaige : dfUpBaigeList) {
            String batchFromDate = DateUtil.getBatchFromDate(dfUpBaige.getDate());
            dfUpBaige.setBatchId(batchFromDate);
        }

        boolean save = dfUpBaigeService.saveBatch(dfUpBaigeList);
        if (save){
            return new Result(200, "vn 百格上传成功");
        }
        return new Result(500, "vn 百格上传失败");
    }

    @GetMapping("/findDfUpBaige")
    @ApiOperation(value = "vn 百格查询接口")
    public R findDfUpBaige(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "project", required = false) String project,
            @RequestParam(value = "stage", required = false) String stage,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        // 创建查询条件
        QueryWrapper<DfUpBaige> dfUpBaigeQueryWrapper = new QueryWrapper<>();

        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpBaigeQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpBaigeQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(project)) {
            dfUpBaigeQueryWrapper.eq("project", project);
        }
        if (StringUtils.isNotEmpty(stage)) {
            dfUpBaigeQueryWrapper.eq("stage", stage);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpBaigeQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpBaige> pageResult = dfUpBaigeService.page(
                new Page<>(page, limit),
                dfUpBaigeQueryWrapper
        );

        return R.ok(pageResult);
    }


    @GetMapping("/getBaigeById/{id}")
    @ApiOperation(value = "根据 ID 查询百格数据")
    public R getBaigeById(@PathVariable Long id) {
        DfUpBaige baige = dfUpBaigeService.getById(id);
        if (baige != null) {
            return R.ok(baige);
        }
        return R.failed( "未找到对应数据");
    }

    @PutMapping("/updateBaige")
    @ApiOperation(value = "更新百格数据")
    public R updateBaige(@RequestBody DfUpBaige dfUpBaige) {
        boolean updated = dfUpBaigeService.updateById(dfUpBaige);
        if (updated) {
            return R.ok("更新成功");
        }
        return R.failed("更新失败");
    }

    @DeleteMapping("/deleteBaigeBatch")
    @ApiOperation(value = "批量删除百格数据")
    public R deleteBaigeBatch(@RequestBody List<Long> ids) {
        boolean removed = dfUpBaigeService.removeByIds(ids);
        if (removed) {
            return R.ok("批量删除成功");
        }
        return R.failed( "批量删除失败");
    }


    @GetMapping("/exportBaige")
    @ApiOperation(value = "导出百格上传记录 Excel")
    public void exportBaige(

            @RequestParam(required = false) String process,
            @RequestParam(required = false) String project,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletResponse response) throws IOException {

        // 条件查询构造器
        QueryWrapper<DfUpBaige> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            queryWrapper.between("date", startTime, endTime);
        }
        if (StringUtils.isNotBlank(process)) {
            queryWrapper.eq("process", process);
        }
        if (StringUtils.isNotBlank(project)) {
            queryWrapper.like("project", project);
        }
        queryWrapper.orderByDesc("date");

        // 查询数据
        List<DfUpBaige> baigeList = dfUpBaigeService.list(queryWrapper);

        // 导出 Excel
        ExportParams exportParams = new ExportParams("百格上传记录", "百格记录");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, DfUpBaige.class, baigeList);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("百格导出记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出文件
        workbook.write(response.getOutputStream());
        workbook.close();
    }





}
