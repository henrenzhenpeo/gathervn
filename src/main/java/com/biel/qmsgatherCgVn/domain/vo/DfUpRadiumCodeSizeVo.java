package com.biel.qmsgatherCgVn.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/26 17:56
 */
@Data
public class DfUpRadiumCodeSizeVo {
    /**
     * 时间日期
     */
    @Excel(name = "时间日期", orderNum = "1", format = "yyyy-MM-dd HH:mm:ss", width = 25)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
    /**
     * 二维码长
     */
    @Excel(name = "二维码长", orderNum = "2")
    private double qrCodeLength;

    /**
     * 二维码宽
     */
    @Excel(name = "二维码宽", orderNum = "3")
    private double qrCodeWidth;

    /**
     * 玻璃下边到 X 片源中心
     */
    @Excel(name = "镭码下边到玻璃边距", orderNum = "4")
    private double barcodeToglass;

    /**
     * 玻璃上边到 X 片源中心
     */
    @Excel(name = "x白片镭码到玻璃中心", orderNum = "5")
    private double xWhitePlateToGlassCenter;

    /**
     * 左边距
     */
    @Excel(name = "左边距", orderNum = "6")
    private double leftDistance;

    /**
     * 右边距
     */
    @Excel(name = "右边距", orderNum = "7")
    private double rightDistance;
    /**
     * 机台号
     */
    @Excel(name = "机台号", orderNum = "8")
    private String machineCode;

    /**
     * 状态
     */
    @Excel(name = "状态", orderNum = "9")
    private String state;

    /**
     * 测试次数
     */
    @Excel(name = "测量次数", orderNum = "10")
    private Integer testNumber;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "11")
    private String remark;

    /**
     * 上传人
     */
    @Excel(name = "上传人", orderNum = "12")
    private String uploadName;

    /**
     * 批次号
     */
    @Excel(name = "批次号", orderNum = "13")
    private String batchId;

}
