package com.biel.qmsgatherCgVn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpWaterDropAsWsa;
import com.biel.qmsgatherCgVn.service.DfUpWaterDropAsWsaService;
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
@RequestMapping("/vn/dfUpWaterDropAsWsa")
@CrossOrigin
@Api(tags = "vn水滴角Wsa 接口")
public class DfUpWaterDropAsWsaController {

    @Autowired
    private DfUpWaterDropAsWsaService dfUpWaterDropAsWsaService;


    @PostMapping("/uploadDfUpWaterDropAsWsa")
    @ApiOperation(value = "vn水滴角Wsa上传接口")
    public Result uploadDfUpWaterDropAsWsa(@RequestBody List<DfUpWaterDropAsWsa> dfUpWaterDropAsWsaList){

        for (DfUpWaterDropAsWsa dfUpWaterDropAsWsa : dfUpWaterDropAsWsaList) {
            String batchFromDate = DateUtil.getBatchFromDate(dfUpWaterDropAsWsa.getDate());
            dfUpWaterDropAsWsa.setBatchId(batchFromDate);
        }

        boolean save = dfUpWaterDropAsWsaService.saveBatch(dfUpWaterDropAsWsaList);
        if (save){
            return new Result(200, "vn水滴角Wsa上传成功");
        }
        return new Result(500, "vn水滴角Wsa上传失败");
    }

    @GetMapping("/findDfUpWaterDropAsWsa")
    @ApiOperation(value = "vn水滴角Wsa 查询接口")
    public R findDfUpWaterDropAsWsa(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpWaterDropAsWsa> dfUpWaterDropAsWsaQueryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpWaterDropAsWsaQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpWaterDropAsWsaQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpWaterDropAsWsaQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpWaterDropAsWsaQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpWaterDropAsWsaQueryWrapper.between("date", startTestDate, endTestDate);
        }
        dfUpWaterDropAsWsaQueryWrapper.orderByDesc("date");

        // 执行分页查询
        IPage<DfUpWaterDropAsWsa> pageResult = dfUpWaterDropAsWsaService.page(
                new Page<>(page, limit),
                dfUpWaterDropAsWsaQueryWrapper
        );

        return R.ok(pageResult);
    }
}
