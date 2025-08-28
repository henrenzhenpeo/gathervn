package com.biel.qmsgatherCgVn.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class DfUpChamferHypotenuseVo {
    /**
     * 时间日期
     */
    @Excel(name = "时间日期", orderNum = "1", format = "yyyy-MM-dd HH:mm:ss", width = 25)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 短边右上
     */
    @Excel(name = "短边右上1", orderNum = "2")
    private double shortUr1;

    /**
     * 短边右下
     */
    @Excel(name = "短边右下4", orderNum = "5")
    private double shortLr4;

    /**
     * 短边左上
     */
    @Excel(name = "短边左上9")
    private double shortUl8;

    /**
     * 短边左下
     */
    @Excel(name = "短边左下5", orderNum = "6")
    private double shortLl5;

    /**
     * 长边右上
     */
    @Excel(name = "长边右上2", orderNum = "3")
    private double longUr2;

    /**
     * 长边右下
     */
    @Excel(name = "长边右下3", orderNum = "4")
    private double longLr3;

    /**
     * 长边左上
     */
    @Excel(name = "长边左上7", orderNum = "8")
    private double longUl7;

    /**
     * 长边左下
     */
    @Excel(name = "长边左下6", orderNum = "7")
    private double longLl6;

    /**
     * 平均值
     */
    @Excel(name = "平均值", orderNum = "10")
    private double avg;

    /**
     * 1-4差值
     */
    @Excel(name = "1-4差值", orderNum = "11")
    private double std1to4;

    /**
     * 2-7差值
     */
    @Excel(name = "2-7差值", orderNum = "12")
    private double std2to7;

    /**
     * 3-6差值
     */
    @Excel(name = "3-6差值", orderNum = "13")
    private double std3to6;

    /**
     * 5-8差值
     */
    @Excel(name = "5-8差值", orderNum = "14")
    private double std5to8;

    /**
     * 机台号
     */
    @Excel(name = "机台号", orderNum = "15")
    private String machineCode;

    /**
     * 状态
     */
    @Excel(name = "状态", orderNum = "16")
    private String state;

    /**
     * 测试次数
     */
    @Excel(name = "测量次数", orderNum = "17")
    private Integer testNumber;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "18")
    private String remark;

}
