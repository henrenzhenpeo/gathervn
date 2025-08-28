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
 * @DATE 2025/8/27 16:01
 */
@Data
@TableName(value = "df_up_chamferHypotenuse")
public class DfUpChamferHypotenuse {

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
     * 短边右上
     */
    @Excel(name = "短边右上1")
    private double shortUr1;

    /**
     * 短边右下
     */
    @Excel(name = "短边右下4")
    private double shortLr4;

    /**
     * 短边左上
     */
    @Excel(name = "短边左上8")
    private double shortUl8;

    /**
     * 短边左下
     */
    @Excel(name = "短边左下5")
    private double shortLl5;

    /**
     * 长边右上
     */
    @Excel(name = "长边右上2")
    private double longUr2;

    /**
     * 长边右下
     */
    @Excel(name = "长边右下3")
    private double longLr3;

    /**
     * 长边左上
     */
    @Excel(name = "长边左上7")
    private double longUl7;

    /**
     * 长边左下
     */
    @Excel(name = "长边左下6")
    private double longLl6;

    /**
     * 平均值
     */
    @Excel(name = "平均值")
    private double avg;

    /**
     * 1-4差值
     */
    @Excel(name = "1-4差值")
    private double std1to4;

    /**
     * 2-7差值
     */
    @Excel(name = "2-7差值")
    private double std2to7;

    /**
     * 3-6差值
     */
    @Excel(name = "3-6差值")
    private double std3to6;

    /**
     * 5-8差值
     */
    @Excel(name = "5-8差值")
    private double std5to8;

    /**
     * 班别
     */
    @Excel(name = "班别")
    private String classes;
}
