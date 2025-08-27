package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpInkDensity;
import com.biel.qmsgatherCgVn.domain.DfUpResistance;
import com.biel.qmsgatherCgVn.service.DfUpInkDensityService;
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
@Api(tags = "vn 油墨密度-od 接口")
public class DfUpInkDensityController {
    @Autowired
    DfUpInkDensityService dfUpInkDensityService;


    @PostMapping("/uploadInkDensity")
    @ApiOperation(value = "vn 油墨密度-od上传接口")
    public Result uploadInkDensity(@RequestBody List<DfUpInkDensity> dfUpInkDensityList){
        for (DfUpInkDensity dfUpInkDensity : dfUpInkDensityList) {
            String batchFromDate = DateUtil.getBatchFromDate(dfUpInkDensity.getDate());
            dfUpInkDensity.setBatchId(batchFromDate);
        }

        boolean save = dfUpInkDensityService.saveBatch(dfUpInkDensityList);
        if (save){
            return new Result(200, "vn 油墨密度-od上传成功");
        }
        return new Result(500, "vn 油墨密度-od上传失败");
    }

    @GetMapping("/findDfUpInkDensity")
    @ApiOperation(value = "vn 油墨密度-od查询接口")
    public R findDfUpInkDensity(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "product_model", required = false) String productModel,
            @RequestParam(value = "stage", required = false) String stage,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        // 创建查询条件
        QueryWrapper<DfUpInkDensity> dfUpInkDensityQueryWrapper = new QueryWrapper<>();

        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpInkDensityQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpInkDensityQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(productModel)) {
            dfUpInkDensityQueryWrapper.eq("model", productModel);
        }
        if (StringUtils.isNotEmpty(stage)) {
            dfUpInkDensityQueryWrapper.eq("stage", stage);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpInkDensityQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpInkDensity> pageResult = dfUpInkDensityService.page(
                new Page<>(page, limit),
                dfUpInkDensityQueryWrapper
        );

        return R.ok(pageResult);
    }

    @GetMapping("/getInkDensityById/{id}")
    @ApiOperation(value = "根据 ID 查询油墨密度数据")
    public R getInkDensityById(@PathVariable Long id) {
        DfUpInkDensity inkDensity = dfUpInkDensityService.getById(id);
        if (inkDensity != null) {
            return R.ok(inkDensity);
        }
        return R.failed("未找到对应数据");
    }

    @PutMapping("/updateInkDensity")
    @ApiOperation(value = "更新油墨密度数据")
    public R updateInkDensity(@RequestBody DfUpInkDensity dfUpInkDensity) {
        boolean updated = dfUpInkDensityService.updateById(dfUpInkDensity);
        if (updated) {
            return R.ok("更新成功");
        }
        return R.failed("更新失败");
    }

    @DeleteMapping("/deleteInkDensityBatch")
    @ApiOperation(value = "批量删除油墨密度数据")
    public R deleteInkDensityBatch(@RequestBody List<Long> ids) {
        boolean removed = dfUpInkDensityService.removeByIds(ids);
        if (removed) {
            return R.ok("批量删除成功");
        }
        return R.failed("批量删除失败");
    }

    @GetMapping("/exportInkDensity")
    @ApiOperation(value = "导出油墨密度记录 Excel")
    public void exportInkDensity(
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletResponse response) throws IOException {

        QueryWrapper<DfUpInkDensity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            queryWrapper.between("create_time", startTime, endTime);
        }
        if (StringUtils.isNotBlank(process)) {
            queryWrapper.like("process", process);
        }
        if (StringUtils.isNotBlank(model)) {
            queryWrapper.like("model", model);
        }
        queryWrapper.orderByDesc("create_time");

        List<DfUpInkDensity> dataList = dfUpInkDensityService.list(queryWrapper);

        ExportParams params = new ExportParams("油墨密度记录", "油墨密度");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DfUpInkDensity.class, dataList);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("油墨密度记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
