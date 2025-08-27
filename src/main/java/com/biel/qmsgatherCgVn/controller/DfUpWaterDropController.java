package com.biel.qmsgatherCgVn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpBaige;
import com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe;
import com.biel.qmsgatherCgVn.domain.DfUpWaterDrop;
import com.biel.qmsgatherCgVn.service.DfUpWaterDropService;
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
@RequestMapping("/vn/dfUpWaterDrop")
@CrossOrigin
@Api(tags = "vn 水滴角接口")
public class DfUpWaterDropController {

    @Autowired
    private DfUpWaterDropService dfUpWaterDropService;


    @PostMapping("/uploadDfUpWaterDrop")
    @ApiOperation(value = "vn 水滴角上传接口")
    public Result uploadDfUpWaterDrop(@RequestBody List<DfUpWaterDrop> dfUpWaterDrops){
        for (DfUpWaterDrop dfUpWaterDrop : dfUpWaterDrops) {
            String batchFromDate = DateUtil.getBatchFromDate(dfUpWaterDrop.getDate());
            dfUpWaterDrop.setBatchId(batchFromDate);
        }

        boolean save = dfUpWaterDropService.saveBatch(dfUpWaterDrops);
        if (save){
            return new Result(200, "vn 水滴角上传成功");
        }
        return new Result(500, "vn 水滴角上传失败");
    }


    @GetMapping("/findDfUpWaterDrop")
    @ApiOperation(value = "vn 水滴角 查询接口")
    public R findDfUpWaterDrop(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpWaterDrop> dfUpWaterDropQueryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpWaterDropQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpWaterDropQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpWaterDropQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpWaterDropQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpWaterDropQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpWaterDrop> pageResult = dfUpWaterDropService.page(
                new Page<>(page, limit),
                dfUpWaterDropQueryWrapper
        );

        return R.ok(pageResult);
    }


}
