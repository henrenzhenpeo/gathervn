package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 丝印光油
 * @TableName df_up_screen_printing_varnish
 */
@TableName(value ="df_up_screen_printing_varnish")
@Data
public class DfUpScreenPrintingVarnish {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 时间
     */
    @Excel(name = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 工厂
     */
    @Excel(name = "工厂")
    private String factory;

    /**
     * 型号
     */
    @Excel(name = "型号")
    private String model;

    /**
     * 工序
     */
    @Excel(name = "工序")
    private String process;

    /**
     * 测量项目
     */
    @Excel(name = "测量项目")
    private String testProject;

    /**
     * 1点Y2
     */
    @Excel(name = "1点Y2")
    private Double onePointy2;

    /**
     * 2点Y1
     */
    @Excel(name = "2点Y1")
    private Double twoPointy1;

    /**
     * 3点
     */
    @Excel(name = "3点")
    private Double threePoint;

    /**
     * aoc
     */
    @Excel(name = "aoc")
    private Double groove;

    /**
     * 四点x
     */
    @Excel(name = "四点x")
    private Double fourPointx;

    /**
     * 5点Y1
     */
    @Excel(name = "5点Y1")
    private Double fivePointy1;

    /**
     * 6点Y2
     */
    @Excel(name = "6点Y2")
    private Double sixPointy2;

    /**
     * 7点
     */
    @Excel(name = "7点")
    private Double sevenPoint;

    /**
     * 8点x
     */
    @Excel(name = "8点x")
    private Double eightPointx;

    /**
     * 2D码到视窗1
     */
    @Excel(name = "2D码到视窗1")
    private Double twoCodeWindow1;

    /**
     * 2D码到视窗2
     */
    @Excel(name = "2D码到视窗2")
    private Double twoCodeWindow2;

    /**
     * 光油上边到基准1
     */
    @Excel(name = "光油上边到基准1")
    private Double lightOilTopReference1;

    /**
     * 光油上边到基准2
     */
    @Excel(name = "光油上边到基准2")
    private Double lightOilTopReference2;

    /**
     * 2D码到玻中心1
     */
    @Excel(name = "2D码到玻中心1")
    private Double twoCodeCenter1;

    /**
     * 2D码到玻中心2
     */
    @Excel(name = "2D码到玻中心2")
    private Double twoCodeCenter2;

    /**
     * 2D码上到玻中心1
     */
    @Excel(name = "2D码上到玻中心1")
    private Double twoCodeTopCenter1;

    /**
     * 2D码上到玻中心2
     */
    @Excel(name = "2D码上到玻中心2")
    private Double twoCodeTopCenter2;

    /**
     * 调机x
     */
    @Excel(name = "调机x")
    private Double debugMachinex;

    /**
     * 调机y1
     */
    @Excel(name = "调机y1")
    private Double debugMachiney1;

    /**
     * 调机y2
     */
    @Excel(name = "调机y2")
    private Double debugMachiney2;

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


}