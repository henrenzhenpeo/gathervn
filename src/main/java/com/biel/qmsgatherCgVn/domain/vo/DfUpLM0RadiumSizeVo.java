package com.biel.qmsgatherCgVn.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/9/25 19:11
 */
@Data
public class DfUpLM0RadiumSizeVo {
    /**
     * 时间日期
     */
    @Excel(name = "测试时间", orderNum = "1", format = "yyyy-MM-dd HH:mm:ss", width = 25)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 外形长
     */
    @Excel(name = "外形长", orderNum = "2")
    private double externalLong;

    /**
     * 外形宽
     */
    @Excel(name = "外形宽", orderNum = "3")
    private double externalWidth;

    /**
     * 码长
     */
    @Excel(name = "码长", orderNum = "4")
    private double codeLength;

    /**
     * 码宽
     */
    @Excel(name = "码宽", orderNum = "5")
    private double codeWidth;

    /**
     * 码到视窗
     */
    @Excel(name = "码到视窗", orderNum = "6")
    private double codeToView;

    /**
     * 码到中心X
     */
    @Excel(name = "码到中心X", orderNum = "7")
    private double codeToCenterX;

    /**
     * 码到中心Y
     */
    @Excel(name = "码到中心Y", orderNum = "8")
    private double codeToCenterY;

    /**
     * 苹果上下长度
     */
    @Excel(name = "苹果上下长度", orderNum = "9")
    private double appleUpDownLength;

    /**
     * 苹果宽
     */
    @Excel(name = "苹果宽", orderNum = "10")
    private double appleWidth;

    /**
     * 叶子下端到上端水平距离
     */
    @Excel(name = "叶子下端到上端水平距离", orderNum = "11")
    private double leafDownToUpHorizontalDistance;

    /**
     * 苹果logo主体上端到低端
     */
    @Excel(name = "苹果logo主体上端到低端", orderNum = "12")
    private double appleLogoBodyUpToDown;

    /**
     * 叶子下端到苹果logo主体下端距离
     */
    @Excel(name = "叶子下端到苹果logo主体下端距离", orderNum = "13")
    private double leafDownToAppleLogoBodyDown;

    /**
     * 苹果底到二维码
     */
    @Excel(name = "苹果底到二维码", orderNum = "14")
    private double appleBottomToQrCode;

    /**
     * 上传人
     */
    @Excel(name = "上传人", orderNum = "18")
    private String uploadName;

    /**
     * 批次号
     */
    @Excel(name = "批次号", orderNum = "17")
    private String batchId;

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

}
