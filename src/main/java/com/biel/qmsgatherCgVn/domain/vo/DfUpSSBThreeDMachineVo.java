package com.biel.qmsgatherCgVn.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/28 15:38
 */
@Data
public class DfUpSSBThreeDMachineVo {
    /**
     * 时间日期
     */
    @Excel(name = "时间日期", orderNum = "1", format = "yyyy-MM-dd HH:mm:ss", width = 25)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 外形长1
     */
    @Excel(name = "外形长1",orderNum = "2")
    private double externalLong1;

    /**
     * 外形长2
     */
    @Excel(name = "外形长2", orderNum = "3")
    private double externalLong2;

    /**
     * 外形宽1
     */
    @Excel(name = "外形宽1", orderNum = "4")
    private double externalWidth1;

    /**
     * 外形宽2
     */
    @Excel(name = "外形宽2", orderNum = "5")
    private double externalWidth2;

    /**
     * 外形宽3
     */
    @Excel(name = "外形宽3", orderNum = "6")
    private double externalWidth3;

    /**
     * 切角角度
     */
    @Excel(name = "切角角度", orderNum = "7")
    private double cutAngle;

    /**
     * 切角长
     */
    @Excel(name = "切角长", orderNum = "8")
    private double cutAngleLong;

    /**
     * 切角宽
     */
    @Excel(name = "切角宽", orderNum = "9")
    private double cutAngleWidth;

    /**
     * 二维码长
     */
    @Excel(name = "二维码长", orderNum = "10")
    private double qrCodeLength;

    /**
     * 二维码宽
     */
    @Excel(name = "二维码宽", orderNum = "11")
    private double qrCodeWidth;

    /**
     * 白片镭码下边到玻璃距
     */
    @Excel(name = "白片镭码下边到玻璃距", orderNum = "12")
    private double whitePlateToglass;

    /**
     * 白片镭码到玻璃中心
     */
    @Excel(name = "白片镭码到玻璃中心", orderNum = "13")
    private double whitePlateToGlassCenter;

    /**
     * 机台号
     */
    @Excel(name = "机台号", orderNum = "14")
    private String machineCode;

    /**
     * 状态
     */
    @Excel(name = "状态", orderNum = "15")
    private String state;

    /**
     * 测试次数
     */
    @Excel(name = "测量次数", orderNum = "16")
    private Integer testNumber;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "17")
    private String remark;

    /**
     * 上传人
     */
    @Excel(name = "上传人", orderNum = "18")
    private String uploadName;

    /**
     * 批次号
     */
    @Excel(name = "批次号", orderNum = "19")
    private String batchId;
}
