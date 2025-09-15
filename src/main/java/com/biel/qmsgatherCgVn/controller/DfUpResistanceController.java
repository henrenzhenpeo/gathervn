package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpResistance;
import com.biel.qmsgatherCgVn.service.DfUpResistanceService;
import com.biel.qmsgatherCgVn.util.DateUtil;
import com.biel.qmsgatherCgVn.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/vn/dfUpResistance")
@CrossOrigin
@Api(tags = "vn 电阻接口")
public class DfUpResistanceController {
    @Autowired
    private DfUpResistanceService dfUpResistanceService;

    @PostMapping("/uploadResistance")
    @ApiOperation(value = "vn 电阻上传接口")
    public Result uploadResistance(@RequestBody List<DfUpResistance> dfUpResistanceList){
        for (DfUpResistance dfUpResistance : dfUpResistanceList) {
            String batchFromDate = DateUtil.getBatchFromDate(dfUpResistance.getTestTime());
            dfUpResistance.setBatchId(batchFromDate);
        }

        boolean save = dfUpResistanceService.saveBatch(dfUpResistanceList);
        if (save){
            return new Result(200, "vn 电阻上传成功");
        }
        return new Result(500, "vn 电阻上传失败");
    }


    @GetMapping("/findDfUpResistance")
    @ApiOperation(value = "vn 电阻查询接口")
    public R findDfUpResistance(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "product_model", required = false) String productModel,
            @RequestParam(value = "stage", required = false) String stage,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        // 创建查询条件
        QueryWrapper<DfUpResistance> dfUpResistanceQueryWrapper = new QueryWrapper<>();

        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpResistanceQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpResistanceQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(productModel)) {
            dfUpResistanceQueryWrapper.eq("model", productModel);
        }
        if (StringUtils.isNotEmpty(stage)) {
            dfUpResistanceQueryWrapper.eq("stage", stage);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpResistanceQueryWrapper.between("test_time", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpResistance> pageResult = dfUpResistanceService.page(
                new Page<>(page, limit),
                dfUpResistanceQueryWrapper
        );

        return R.ok(pageResult);
    }


    @GetMapping("/getResistanceById/{id}")
    @ApiOperation(value = "根据 ID 查询电阻数据")
    public R getResistanceById(@PathVariable Long id) {
        DfUpResistance resistance = dfUpResistanceService.getById(id);
        if (resistance != null) {
            return R.ok("查询成功");
        }
        return R.failed("未找到对应数据");
    }

    @PutMapping("/updateResistance")
    @ApiOperation(value = "更新电阻数据")
    public R updateResistance(@RequestBody DfUpResistance dfUpResistance) {
        boolean updated = dfUpResistanceService.updateById(dfUpResistance);
        if (updated) {
            return R.ok("更新成功");
        }
        return R.failed("更新失败");
    }

    @GetMapping("/exportResistance")
    @ApiOperation(value = "导出电阻记录 Excel")
    public void exportResistance(
            @RequestParam(required = false) String productModel,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletResponse response) throws IOException {

        QueryWrapper<DfUpResistance> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            queryWrapper.between("test_time", startTime, endTime);
        }
        if (StringUtils.isNotBlank(productModel)) {
            queryWrapper.like("model", productModel);
        }
        queryWrapper.orderByDesc("test_time");

        List<DfUpResistance> dataList = dfUpResistanceService.list(queryWrapper);

        ExportParams params = new ExportParams("电阻记录", "电阻");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DfUpResistance.class, dataList);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("电阻记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
