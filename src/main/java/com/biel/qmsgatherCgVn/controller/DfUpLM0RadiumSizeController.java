package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpFileMaterialSize;
import com.biel.qmsgatherCgVn.domain.DfUpLM0RadiumSize;
import com.biel.qmsgatherCgVn.domain.vo.DfUpLM0RadiumSizeVo;
import com.biel.qmsgatherCgVn.domain.vo.DfUpRadiumCodeSizeVo;
import com.biel.qmsgatherCgVn.service.DfUpLM0RadiumSizeService;
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
 * 2D镭码,镭码尺寸接口
 *
 * @Author mr.feng
 * @DATE 2025/9/25 17:10
 */
@RestController
@RequestMapping("/vn/dfUpLM0RadiumSize")
@CrossOrigin
@Api(tags = "vn LM0-镭码尺寸 接口")
public class DfUpLM0RadiumSizeController {

    @Autowired
    private DfUpLM0RadiumSizeService dfUpLM0RadiumSizeService;

    @ApiOperation(value = "vn LM0-镭码尺寸导入接口")
    @PostMapping("/importDfUpLM0RadiumSize")
    public R importExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam("factory") String factory,
                         @RequestParam("model") String model,
                         @RequestParam("process") String process,
                         @RequestParam("test_project") String testProject,
                         @RequestParam("upload_name") String uploadName,
                         @RequestParam("batch_id") String batchId) {
        try {
            dfUpLM0RadiumSizeService.importExcel(file, factory, model, process, testProject,uploadName,batchId);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入失败：" + e.getMessage());
        }
    }

    @GetMapping("/findDfUpFileMaterialSize")
    @ApiOperation(value = "vn LM0-镭码尺寸查询接口")
    public R findDfUpLM0RadiumSize(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpLM0RadiumSize> dfUpLM0RadiumSizeQueryWrapper = new QueryWrapper<>();

        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpLM0RadiumSizeQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpLM0RadiumSizeQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpLM0RadiumSizeQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpLM0RadiumSizeQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpLM0RadiumSizeQueryWrapper.between("date", startTestDate, endTestDate);
        }

        dfUpLM0RadiumSizeQueryWrapper.orderByDesc("date");

        // 执行分页查询
        IPage<DfUpLM0RadiumSize> pageResult = dfUpLM0RadiumSizeService.page(
                new Page<>(page, limit),
                dfUpLM0RadiumSizeQueryWrapper
        );

        return R.ok(pageResult);
    }


    @GetMapping("/exportDfUpFileMaterialSize")
    @ApiOperation(value = "导出vn LM0-镭码尺寸 Excel")
    public void exportDfUpFileMaterialSize(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String testProject,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletResponse response) throws IOException {

        // 条件查询构造器
        QueryWrapper<DfUpLM0RadiumSize> queryWrapper = new QueryWrapper<>();
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
        List<DfUpLM0RadiumSize> dfUpLM0RadiumSizeList = dfUpLM0RadiumSizeService.list(queryWrapper);

        // 映射为 VO 列表（只保留 VO 字段）
        List<DfUpLM0RadiumSizeVo> exportList = dfUpLM0RadiumSizeList.stream().map(e -> {
            DfUpLM0RadiumSizeVo v = new DfUpLM0RadiumSizeVo();
            v.setModel(e.getModel());
            v.setDate(e.getDate());
            v.setExternalLong(e.getExternalLong());
            v.setExternalWidth(e.getExternalWidth());
            v.setCodeLength(e.getCodeLength());
            v.setCodeWidth(e.getCodeWidth());
            v.setCodeToView(e.getCodeToView());
            v.setCodeToCenterX(e.getCodeToCenterX());
            v.setCodeToCenterY(e.getCodeToCenterY());
            v.setAppleUpDownLength(e.getAppleUpDownLength());
            v.setAppleWidth(e.getAppleWidth());
            v.setLeafDownToUpHorizontalDistance(e.getLeafDownToUpHorizontalDistance());
            v.setLeafDownToAppleLogoBodyDown(e.getLeafDownToAppleLogoBodyDown());
            v.setAppleBottomToQrCode(e.getAppleBottomToQrCode());
            v.setMachineCode(e.getMachineCode());
            v.setAppleLogoBodyUpToDown(e.getAppleLogoBodyUpToDown());
            v.setState(e.getState());
            v.setUploadName(e.getUploadName());
            v.setBatchId(e.getBatchId());
            return v;
        }).collect(Collectors.toList());

        // 导出 Excel
        ExportParams exportParams = new ExportParams("LM02D镭码尺寸记录", "2D镭码尺寸");
// 修正：使用VO类和转换后的VO列表
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, DfUpLM0RadiumSizeVo.class, exportList);
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("LM02D镭码尺寸导出记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出文件
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
