package com.biel.qmsgatherCgVn.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/27 14:44
 */
@Data
public class DfUpBottomGapChamferVo {
    /**
     * 时间日期
     */
    @Excel(name = "时间日期", orderNum = "1", format = "yyyy-MM-dd HH:mm:ss", width = 25)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 上长边底倒角1
     */
    @Excel(name = "上长边底倒角1",orderNum = "2")
    private Double upperLongSideBottomChamfer1;

    /**
     * 上长边底倒角2
     */
    @Excel(name = "上长边底倒角2", orderNum = "3")
    private Double upperLongSideBottomChamfer2;

    /**
     * 上长边底倒角3
     */
    @Excel(name = "上长边底倒角3", orderNum = "4")
    private Double upperLongSideBottomChamfer3;

    /**
     * 右短边底倒角1
     */
    @Excel(name = "右短边底倒角1", orderNum = "5")
    private Double rightShortSideBottomChamfer1;

    /**
     * 右短边底倒角3
     */
    @Excel(name = "右短边底倒角3", orderNum = "6")
    private Double rightShortSideBottomChamfer3;

    /**
     * 凹槽底倒角
     */
    @Excel(name = "凹槽底倒角2", orderNum = "7")
    private Double grooveBottomChamfer2;

    /**
     * 下长边底倒角1
     */
    @Excel(name = "下长边底倒角1",orderNum = "8")
    private Double lowerLongSideBottomChamfer1;

    /**
     * 下长边底倒角2
     */
    @Excel(name = "下长边底倒角2",orderNum = "9")
    private Double lowerLongSideBottomChamfer2;

    /**
     * 下长边底倒角3
     */
    @Excel(name = "下长边底倒角3",orderNum = "10")
    private Double lowerLongSideBottomChamfer3;

    /**
     * 左短边底倒角1
     */
    @Excel(name = "左短边底倒角1",orderNum = "11")
    private Double leftShortSideBottomChamfer1;

    /**
     * 左短边底倒角2
     */
    @Excel(name = "左短边底倒角2",orderNum = "12")
    private Double leftShortSideBottomChamfer2;

    /**
     * 左短边底倒角3
     */
    @Excel(name = "左短边底倒角3",orderNum = "13")
    private Double leftShortSideBottomChamfer3;

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
