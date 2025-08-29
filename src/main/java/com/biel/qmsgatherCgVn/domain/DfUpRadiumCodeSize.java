package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/26 10:49
 */
@Data
@TableName(value = "df_up_redium_code_size")
public class DfUpRadiumCodeSize {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 时间日期
     */
    @Excel(name = "时间日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 工厂
     */
    @Excel(name = "工厂")
    private String factory;

    /**
     * 模型
     */
    @Excel(name = "模型")
    private String model;

    /**
     * 工序
     */
    @Excel(name = "工序")
    private String process;

    /**
     * 测试项目
     */
    @Excel(name = "测试项目")
    private String testProject;
    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchId;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 上传人
     */
    @Excel(name = "上传人")
    private String uploadName;

    /**
     * 机台号
     */
    @Excel(name = "机台号")
    private String machineCode;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String state;

    /**
     * 测试次数
     */
    @Excel(name = "测量次数")
    private Integer testNumber;

    /**
     * 二维码长
     */
    @Excel(name = "二维码长")
    private double qrCodeLength;

    /**
     * 二维码宽
     */
    @Excel(name = "二维码宽")
    private double qrCodeWidth;

    /**
     * 镭码下边到玻璃边距
     */
    @Excel(name = "镭码下边到玻璃边距")
    private double barcodeToglass;

    /**
     * x白片镭码到玻璃中心
     */
    @Excel(name = "x白片镭码到玻璃中心")
    private double xWhitePlateToGlassCenter;

    /**
     * 左边距
     */
    @Excel(name = "左边距")
    private double leftDistance;

    /**
     * 右边距
     */
    @Excel(name = "右边距")
    private double rightDistance;

    /**
     * 班次
     */
    @Excel(name = "班次")
    private String classes;

}
