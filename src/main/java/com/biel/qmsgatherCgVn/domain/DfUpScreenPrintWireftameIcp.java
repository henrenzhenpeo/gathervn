package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 丝印线框icp
 * @TableName df_up_screen_print_wireftame_icp
 */
@TableName(value ="df_up_screen_print_wireftame_icp")
@Data
public class DfUpScreenPrintWireftameIcp {
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
     * 
     */
    @Excel(name = "左上R角")
    private Double r1;

    /**
     * 
     */
    @Excel(name = "右上R角")
    private Double r2;

    /**
     * 
     */
    @Excel(name = "左下R角")
    private Double r3;

    /**
     * 
     */
    @Excel(name = "右下R角")
    private Double r4;

    /**
     * 上长边
     */
    @Excel(name = "上长边左")
    private Double upperLongSide1;

    /**
     * 上长边2
     */
    @Excel(name = "上长边中")
    private Double upperLongSide2;

    /**
     * 上长边3
     */
    @Excel(name = "上长边下")
    private Double upperLongSide3;

    /**
     * 下长边1
     */
    @Excel(name = "下长边左")
    private Double lowerLongSide1;

    /**
     * 下长边
     */
    @Excel(name = "中长边")
    private Double lowerLongSide2;

    /**
     * 下长边
     */
    @Excel(name = "右长边")
    private Double lowerLongSide3;

    /**
     * 凹槽短边1
     */
    @Excel(name = "凹槽短边1")
    private Double shortDegeGroove1;

    /**
     * 凹槽短边2
     */
    @Excel(name = "凹槽短边2")
    private Double shortDegeGroove2;

    /**
     * 无凹槽短边
     */
    @Excel(name = "凹槽短边3")
    private Double noShortDegeGroove1;

    /**
     * 无凹槽短边2
     */
    @Excel(name = "凹槽短边4")
    private Double noShortDegeGroove2;

    /**
     * 凹槽
     */
    @Excel(name = "凹槽")
    private Double degeGroove;

    /**
     * 外形长
     */
    @Excel(name = "外形长1")
    private Double longAppearance1;
    /**
     * 外形长2
     */
    @Excel(name = "外形长2")
    private Double longAppearance2;

    /**
     * 外形宽2
     */
    @Excel(name = "外形宽1")
    private Double exteriorWidth;

    /**
     * 外形宽2
     */
    @Excel(name = "外形宽2")
    private Double exteriorWidth2;

    /**
     * 
     */
    @Excel(name = "max")
    private Double maxValue;

    /**
     * 机台号
     */
    @Excel(name = "机台号")
    private String machineCode;

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
     * 上传人
     */
    @Excel(name = "上传人")
    private String uploadName;

    /**
     * 上传时间
     */
    @Excel(name = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadCreate;

}