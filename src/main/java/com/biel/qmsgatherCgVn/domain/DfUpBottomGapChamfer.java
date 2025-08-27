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
 * @DATE 2025/8/26 23:07
 */
@Data
@TableName(value = "df_up_bottom_gap_chamfer")
public class DfUpBottomGapChamfer {

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
      * 上长边底倒角1
     */
    @Excel(name = "上长边底倒角1")
    private Double upperLongSideBottomChamfer1;

    /**
     * 上长边底倒角2
     */
    @Excel(name = "上长边底倒角2")
    private Double upperLongSideBottomChamfer2;

    /**
     * 上长边底倒角3
     */
    @Excel(name = "上长边底倒角3")
    private Double upperLongSideBottomChamfer3;

    /**
     * 右短边底倒角1
     */
    @Excel(name = "右短边底倒角1")
    private Double rightShortSideBottomChamfer1;

    /**
     * 右短边底倒角3
     */
    @Excel(name = "右短边底倒角3")
    private Double rightShortSideBottomChamfer3;

    /**
     * 凹槽底倒角
     */
    @Excel(name = "凹槽底倒角2")
    private Double grooveBottomChamfer2;

    /**
     * 下长边底倒角1
     */
    @Excel(name = "下长边底倒角1")
    private Double lowerLongSideBottomChamfer1;

    /**
     * 下长边底倒角2
     */
    @Excel(name = "下长边底倒角2")
    private Double lowerLongSideBottomChamfer2;

    /**
     * 下长边底倒角3
     */
    @Excel(name = "下长边底倒角3")
    private Double lowerLongSideBottomChamfer3;

    /**
     * 左短边底倒角1
     */
    @Excel(name = "左短边底倒角1")
    private Double leftShortSideBottomChamfer1;

    /**
     * 左短边底倒角2
     */
    @Excel(name = "左短边底倒角2")
    private Double leftShortSideBottomChamfer2;

    /**
     * 左短边底倒角3
     */
    @Excel(name = "左短边底倒角3")
    private Double leftShortSideBottomChamfer3;

    /**
     * 班次
     */
    @Excel(name = "班次")
    private String classes;
}
