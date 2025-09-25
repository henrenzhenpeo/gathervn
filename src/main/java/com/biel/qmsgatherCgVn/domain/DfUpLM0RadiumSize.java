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
 * @DATE 2025/9/25 17:19
 */
@Data
@TableName(value = "df_up_lm0_radium_size")
public class DfUpLM0RadiumSize {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 时间日期
     */
    @Excel(name = "时间日期", format = "yyyy-MM-dd HH:mm:ss")
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
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
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
     * 外形长
     */
    @Excel(name = "外形长")
    private double externalLong;

    /**
     * 外形宽
     */
    @Excel(name = "外形宽")
    private double externalWidth;

    /**
     * 码长
     */
    @Excel(name = "码长")
    private double codeLength;

    /**
     * 码宽
     */
    @Excel(name = "码宽")
    private double codeWidth;

    /**
     * 码到视窗
     */
    @Excel(name = "码到视窗")
    private double codeToView;

    /**
     * 码到中心X
     */
    @Excel(name = "码到中心X")
    private double codeToCenterX;

    /**
     * 码到中心Y
     */
    @Excel(name = "码到中心Y")
    private double codeToCenterY;

    /**
     * 苹果上下长度
     */
    @Excel(name = "苹果上下长度")
    private double appleUpDownLength;

    /**
     * 苹果宽
     */
    @Excel(name = "苹果宽")
    private double appleWidth;

    /**
     * 叶子下端到上端水平距离
     */
    @Excel(name = "叶子下端到上端水平距离")
    private double leafDownToUpHorizontalDistance;

    /**
     * 苹果logo主体上端到低端
     */
    @Excel(name = "苹果logo主体上端到低端")
    private double appleLogoBodyUpToDown;

    /**
     * 叶子下端到苹果logo主体下端距离
     */
    @Excel(name = "叶子下端到苹果logo主体下端距离")
    private double leafDownToAppleLogoBodyDown;

    /**
     * 苹果底到二维码
     */
    @Excel(name = "苹果底到二维码")
    private double appleBottomToQrCode;

    /**
     * 班次
     */
    @Excel(name = "班次")
    private String classes;
}
