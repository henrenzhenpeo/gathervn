package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.domain.vo.DfUpBottomGapChamferVo;
import com.biel.qmsgatherCgVn.service.DfUpBottomGapChamferService;
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
 * @DATE 2025/8/26 23:05
 */
@RestController
@RequestMapping("/vn/BottomGapChamfer")
@CrossOrigin
@Api(tags = "vn 底倒角 接口")
public class DfUpBottomGapChamferController {

    @Autowired
    private DfUpBottomGapChamferService dfUpBottomGapChamferService;

    @ApiOperation(value = "vn 底倒角导入接口")
    @PostMapping("/importBottomGapChamfer")
    public R importExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam("factory") String factory,
                         @RequestParam("model") String model,
                         @RequestParam("process") String process,
                         @RequestParam("test_project") String testProject,
                         @RequestParam("upload_name") String uploadName,
                         @RequestParam("batch_id") String batchId,
                         @RequestParam("create_time") String createTime){
        try {
            dfUpBottomGapChamferService.importExcel(file, factory, model, process, testProject,uploadName,batchId,createTime);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("底倒角导入失败：" + e.getMessage());
        }
    }

    @GetMapping("/findBottomGapChamfer")
    @ApiOperation(value = "vn 底倒角查询接口")
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
        QueryWrapper<DfUpBottomGapChamfer> dfUpBottomGapChamferQueryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpBottomGapChamferQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpBottomGapChamferQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpBottomGapChamferQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpBottomGapChamferQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpBottomGapChamferQueryWrapper.between("date", startTestDate, endTestDate);
        }

        dfUpBottomGapChamferQueryWrapper.orderByDesc("date");

        // 执行分页查询
        IPage<DfUpBottomGapChamfer> pageResult = dfUpBottomGapChamferService.page(
                new Page<>(page, limit),
                dfUpBottomGapChamferQueryWrapper
        );
        return R.ok(pageResult);
    }

    @GetMapping("/exportBottomGapChamfer")
    @ApiOperation(value = "导出底倒角 Excel")
    public void exportBottomGapChamfer(
            @RequestParam(required = false) String factory,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String testProject,
            @RequestParam(required = false) String startTestDate,
            @RequestParam(required = false) String endTestDate,
            HttpServletResponse response) throws IOException {

        QueryWrapper<DfUpBottomGapChamfer> queryWrapper = new QueryWrapper<>();
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

        List<DfUpBottomGapChamfer> dataList = dfUpBottomGapChamferService.list(queryWrapper);

        // 映射为 VO 列表（只保留 VO 字段）
        List<DfUpBottomGapChamferVo> exportList = dataList.stream().map(e -> {
            DfUpBottomGapChamferVo v = new DfUpBottomGapChamferVo();
            v.setDate(e.getDate());
            v.setUpperLongSideBottomChamfer1(e.getUpperLongSideBottomChamfer1());
            v.setUpperLongSideBottomChamfer2(e.getUpperLongSideBottomChamfer2());
            v.setUpperLongSideBottomChamfer3(e.getUpperLongSideBottomChamfer3());
            v.setRightShortSideBottomChamfer1(e.getRightShortSideBottomChamfer1());
            v.setRightShortSideBottomChamfer3(e.getRightShortSideBottomChamfer3());
            v.setGrooveBottomChamfer2(e.getGrooveBottomChamfer2());
            v.setLowerLongSideBottomChamfer1(e.getLowerLongSideBottomChamfer1());
            v.setLowerLongSideBottomChamfer2(e.getLowerLongSideBottomChamfer2());
            v.setLowerLongSideBottomChamfer3(e.getLowerLongSideBottomChamfer3());
            v.setLeftShortSideBottomChamfer1(e.getLeftShortSideBottomChamfer1());
            v.setLeftShortSideBottomChamfer2(e.getLeftShortSideBottomChamfer2());
            v.setLeftShortSideBottomChamfer3(e.getLeftShortSideBottomChamfer3());
            v.setMachineCode(e.getMachineCode());
            v.setState(e.getState());
            v.setTestNumber(e.getTestNumber());
            v.setRemark(e.getRemark());
            v.setBatchId(e.getBatchId());
            v.setUploadName(e.getUploadName());
            return v;
        }).collect(Collectors.toList());

        ExportParams params = new ExportParams("底倒角记录", "底倒角尺寸");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DfUpBottomGapChamferVo.class, exportList);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("底倒角记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
