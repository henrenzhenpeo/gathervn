package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * SSB 3D机
 *
 * @Author mr.feng
 * @DATE 2025/8/28 15:38
 */
@Data
@TableName(value = "df_up_three_d_machine")
public class DfUpSSBThreeDMachine {

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
     * 外形长1
     */
    @Excel(name = "外形长1")
    private double externalLong1;

    /**
     * 外形长2
     */
    @Excel(name = "外形长2")
    private double externalLong2;

    /**
     * 外形宽1
     */
    @Excel(name = "外形宽1")
    private double externalWidth1;

    /**
     * 外形宽2
     */
    @Excel(name = "外形宽2")
    private double externalWidth2;

    /**
     * 外形宽3
     */
    @Excel(name = "外形宽3")
    private double externalWidth3;

    /**
     * 切角角度
     */
    @Excel(name = "切角角度")
    private double cutAngle;

    /**
     * 切角长
     */
    @Excel(name = "切角长")
    private double cutAngleLong;

    /**
     * 切角宽
     */
    @Excel(name = "切角宽")
    private double cutAngleWidth;

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
     * 白片镭码下边到玻璃距
     */
    @Excel(name = "白片镭码下边到玻璃距")
    private double whitePlateToglass;

    /**
     * 白片镭码到玻璃中心
     */
    @Excel(name = "白片镭码到玻璃中心")
    private double whitePlateToGlassCenter;

    /**
     * 班别
     */
    @Excel(name = "班别")
    private String classes;
}
