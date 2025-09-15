package com.biel.qmsgatherCgVn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpWaterDropAsCoating;
import com.biel.qmsgatherCgVn.service.DfUpWaterDropAsCoatingService;
import com.biel.qmsgatherCgVn.util.DateUtil;
import com.biel.qmsgatherCgVn.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vn/dfUpWaterDropAsCoating")
@CrossOrigin
@Api(tags = "vn 水滴角 Coating 接口")
public class DfUpWaterDropAsCoatingController {

    @Autowired
    private DfUpWaterDropAsCoatingService dfUpWaterDropAsCoatingService;

    @PostMapping("/uploadDfUpWaterDropAsCoating")
    @ApiOperation(value = "vn水滴角Coating上传接口")
    public Result uploadDfUpWaterDropAsCoating(@RequestBody List<DfUpWaterDropAsCoating> dfUpWaterDropAsCoatings){

        for (DfUpWaterDropAsCoating dfUpWaterDropAsCoating : dfUpWaterDropAsCoatings) {
            String batchFromDate = DateUtil.getBatchFromDate(dfUpWaterDropAsCoating.getDate());
            dfUpWaterDropAsCoating.setBatchId(batchFromDate);
        }

        boolean save = dfUpWaterDropAsCoatingService.saveBatch(dfUpWaterDropAsCoatings);
        if (save){
            return new Result(200, "vn水滴角Coating上传成功");
        }
        return new Result(500, "vn水滴角Coating上传失败");
    }

    @GetMapping("/findDfUpWaterDropAsCoating")
    @ApiOperation(value = "vn水滴角Coating 查询接口")
    public R findDfUpWaterDropAsCoating(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpWaterDropAsCoating> dfUpWaterDropAsCoatingQueryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpWaterDropAsCoatingQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpWaterDropAsCoatingQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpWaterDropAsCoatingQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpWaterDropAsCoatingQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpWaterDropAsCoatingQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpWaterDropAsCoating> pageResult = dfUpWaterDropAsCoatingService.page(
                new Page<>(page, limit),
                dfUpWaterDropAsCoatingQueryWrapper
        );

        return R.ok(pageResult);
    }


}
