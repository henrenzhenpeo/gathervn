package com.biel.qmsgatherCgVn.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.domain.vo.DfUpChamferHypotenuseVo;
import com.biel.qmsgatherCgVn.service.DfUpChamferHypotenuseService;
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
 * @DATE 2025/8/27 15:59
 */
@RestController
@RequestMapping("/vn/ChamferHypotenuse")
@CrossOrigin
@Api(tags = "vn 斜边倒角 接口")
public class DfUpChamferHypotenuseController {

    @Autowired
    private DfUpChamferHypotenuseService dfUpChamferHypotenuseService;

    @ApiOperation(value = "vn 斜边倒角导入接口")
    @PostMapping("/importChamferHypotenuse")
    public R importExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam("factory") String factory,
                         @RequestParam("model") String model,
                         @RequestParam("process") String process,
                         @RequestParam("test_project") String testProject,
                         @RequestParam("upload_name") String uploadName,
                         @RequestParam("batch_id") String batchId,
                         @RequestParam("create_time") String createTime){
        try {
            dfUpChamferHypotenuseService.importExcel(file, factory, model, process, testProject,uploadName,batchId,createTime);
            return R.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入失败：" + e.getMessage());
        }
    }

    @GetMapping("/findChamferHypotenuse")
    @ApiOperation(value = "vn 斜边倒角查询接口")
    public R findDfUpChamferHypotenuse(
            @RequestParam(value = "factory", required = false) String factory,
            @RequestParam(value = "process", required = false) String process,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "test_project", required = false) String testProject,
            @RequestParam(value = "startTestDate", required = false) String startTestDate,
            @RequestParam(value = "endTestDate", required = false) String endTestDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 创建查询条件
        QueryWrapper<DfUpChamferHypotenuse> dfUpChamferHypotenuseQueryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.isNotEmpty(process)) {
            dfUpChamferHypotenuseQueryWrapper.eq("process", process);
        }
        if (StringUtils.isNotEmpty(factory)) {
            dfUpChamferHypotenuseQueryWrapper.eq("factory", factory);
        }
        if (StringUtils.isNotEmpty(model)) {
            dfUpChamferHypotenuseQueryWrapper.eq("model", model);
        }
        if (StringUtils.isNotEmpty(testProject)) {
            dfUpChamferHypotenuseQueryWrapper.eq("test_project", testProject);
        }

        if (StringUtils.isNotEmpty(startTestDate) && StringUtils.isNotEmpty(endTestDate)) {
            dfUpChamferHypotenuseQueryWrapper.between("date", startTestDate, endTestDate);
        }

        // 执行分页查询
        IPage<DfUpChamferHypotenuse> pageResult = dfUpChamferHypotenuseService.page(
                new Page<>(page, limit),
                dfUpChamferHypotenuseQueryWrapper
        );
        return R.ok(pageResult);
    }

    @GetMapping("/exportChamferHypotenuse")
    @ApiOperation(value = "导出斜边倒角尺寸 Excel")
    public void exportDfUpChamferHypotenuse(
            @RequestParam(required = false) String factory,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String process,
            @RequestParam(required = false) String testProject,
            @RequestParam(required = false) String startTestDate,
            @RequestParam(required = false) String endTestDate,
            HttpServletResponse response) throws IOException {

        QueryWrapper<DfUpChamferHypotenuse> queryWrapper = new QueryWrapper<>();
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

        List<DfUpChamferHypotenuse> dataList = dfUpChamferHypotenuseService.list(queryWrapper);

        // 映射为 VO 列表（只保留 VO 字段）
        List<DfUpChamferHypotenuseVo> exportList = dataList.stream().map(e -> {
            DfUpChamferHypotenuseVo v = new DfUpChamferHypotenuseVo();
            v.setDate(e.getDate());
            v.setShortUr1(e.getShortUr1());
            v.setShortLr4(e.getShortLr4());
            v.setShortUl8(e.getShortUl8());
            v.setShortLl5(e.getShortLl5());
            v.setLongUr2(e.getLongUr2());
            v.setLongLr3(e.getLongLr3());
            v.setLongUl7(e.getLongUl7());
            v.setLongLl6(e.getLongLl6());
            v.setAvg(e.getAvg());
            v.setStd1to4(e.getStd1to4());
            v.setStd2to7(e.getStd2to7());
            v.setStd3to6(e.getStd3to6());
            v.setStd5to8(e.getStd5to8());
            v.setMachineCode(e.getMachineCode());
            v.setState(e.getState());
            v.setTestNumber(e.getTestNumber());
            v.setRemark(e.getRemark());
            v.setBatchId(e.getBatchId());
            v.setUploadName(e.getUploadName());
            return v;
        }).collect(Collectors.toList());

        ExportParams params = new ExportParams("斜边倒角记录", "斜边倒角尺寸");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DfUpChamferHypotenuseVo.class, exportList);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("斜边倒角尺寸记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
