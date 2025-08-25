package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * tx-pvd 溢镀
 * @TableName df_up_tx_pvd_overflow_plating
 */
@TableName(value ="df_up_tx_pvd_overflow_plating")
@Data
public class DfUpTxPvdOverflowPlating {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 日期
     */
    @Excel(name = "日期")
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
     * 方孔高
     */
    @Excel(name = "方孔高")
    private String squareHoleHeight;

    /**
     * 方孔宽
     */
    @Excel(name = "方孔宽")
    private String squareHoleWidth;

    /**
     * 长孔中心到BM0左边
     */
    @Excel(name = "长孔中心到BM0左边")
    private String lengthHoleCenterBmoLeft;

    /**
     * 长孔中心到BM0下边
     */
    @Excel(name = "长孔中心到BM0下边")
    private String lengthHoleCenterBmoLower;

    /**
     * 长孔中心到方孔中心
     */
    @Excel(name = "长孔中心到方孔中心")
    private String longHoleCenterSquareHoleCenter;

    /**
     * 长孔中心到小孔中心
     */
    @Excel(name = "长孔中心到小孔中心")
    private String longHoleCenterSmallHoleCenter;

    /**
     * 长孔直径上圆
     */
    @Excel(name = "长孔直径上圆")
    private String longHoleTopRound;

    /**
     * 长孔直径下圆
     */
    @Excel(name = "长孔直径下圆")
    private String longHoleLowerRound;

    /**
     * 长孔宽度
     */
    @Excel(name = "长孔宽度")
    private String longHoleWidth;

    /**
     * 长孔上圆中心到下圆中心
     */
    @Excel(name = "长孔上圆中心到下圆中心")
    private String longHoleTopCenterLowerCenter;

    /**
     * 长孔中心到BM0右边
     */
    @Excel(name = "长孔中心到BM0右边")
    private String lengthHoleCenterBmoRight;

    /**
     * 长孔中心到BM0底边
     */
    @Excel(name = "长孔中心到BM0底边")
    private String lengthHoleCenterBmoBottom;

    /**
     * 小圆直径
     */
    @Excel(name = "小圆直径")
    private String smallCircleDiameter;

    /**
     * 小圆中心到BM0右边
     */
    @Excel(name = "小圆中心到BM0右边")
    private String smallCircleCenterBm0Right;

    /**
     * 小圆中心到BM0下边
     */
    @Excel(name = "小圆中心到BM0下边")
    private String smallCircleCenterBm0Lower;

    /**
     * ar孔上
     */
    @Excel(name = "ar孔上")
    private String arHoleTop;

    /**
     * ar孔左
     */
    @Excel(name = "ar孔左")
    private String arHoleLeft;

    /**
     * ar孔下
     */
    @Excel(name = "ar孔下")
    private String arHoleLower;

    /**
     * ar孔右
     */
    @Excel(name = "ar孔右")
    private String arHoleRight;

    /**
     * ar孔镀膜直径
     */
    @Excel(name = "ar孔镀膜直径")
    private String arHoleCoatingDiameter;

    /**
     * ar孔直径
     */
    @Excel(name = "ar孔直径")
    private String arHoleDiameter;

    /**
     * 判断结果
     */
    @Excel(name = "判断结果")
    private String result;

    /**
     * 工厂型号
     */
    @Excel(name = "工厂型号")
    private String factoryModel;

    /**
     * 机台锅数
     */
    @Excel(name = "机台锅数")
    private String machinePotNumber;

    /**
     * 班别
     */
    @Excel(name = "班别")
    private String whiteNightShift;

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
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchId;


}