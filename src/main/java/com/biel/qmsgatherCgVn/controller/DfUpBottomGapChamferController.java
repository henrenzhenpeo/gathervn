package com.biel.qmsgatherCgVn.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.biel.qmsgatherCgVn.service.DfUpBottomGapChamferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/26 23:05
 */
@RestController
@RequestMapping("/vn/RediumCodeSize")
@CrossOrigin
@Api(tags = "vn 底倒角 接口")
public class DfUpBottomGapChamferController {

    @Autowired
    private DfUpBottomGapChamferService dfUpBottomGapChamferService;

    @ApiOperation(value = "vn 底倒角导入接口")
    @PostMapping("/importDfUpRediumCodeSize")
    public R importExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam("factory") String factory,
                         @RequestParam("model") String model,
                         @RequestParam("process") String process,
                         @RequestParam("test_project") String testProject,
                         @RequestParam("upload_name") String uploadName,
                         @RequestParam("batch_id") String batchId){
        try {
            dfUpBottomGapChamferService.importExcel(file, factory, model, process, testProject,uploadName,batchId);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入失败：" + e.getMessage());
        }
    }
}
