package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 丝印BM2
 * @TableName df_up_screen_printingbm
 */
@TableName(value ="df_up_screen_printingbm")
@Data
public class DfUpScreenPrintingbm {
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
     * 项目
     */
    @Excel(name = "项目")
    private String project;

    /**
     * 测试项目
     */
    @Excel(name = "测试项目")
    private String testProject;

    /**
     * 1点y2
     */
    @Excel(name = "1点y2")
    private Double onePointy2;

    /**
     * 2点y1
     */
    @Excel(name = "2点y1")
    private Double twoPointy1;

    /**
     * 3点x
     */
    @Excel(name = "3点x")
    private Double threePointx;

    /**
     * 4点x
     */
    @Excel(name = "4点x")
    private Double fourPointx;

    /**
     * 5点y1
     */
    @Excel(name = "5点y1")
    private Double fivePointy1;

    /**
     * 6点y2
     */
    @Excel(name = "6点y2")
    private Double sixPointy2;

    /**
     * 7点x
     */
    @Excel(name = "7点x")
    private Double sevenPointx;

    /**
     * 8点x
     */
    @Excel(name = "8点x")
    private Double eightPointx;

    /**
     * 视窗长1
     */
    @Excel(name = "视窗长1")
    private Double windowLength1;

    /**
     * 视窗长2
     */
    @Excel(name = "视窗长2")
    private Double windowLength2;

    /**
     * 视窗宽1
     */
    @Excel(name = "视窗宽1")
    private Double windowWide1;

    /**
     * 视窗宽2
     */
    @Excel(name = "视窗宽2")
    private Double windowWide2;

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
     * 调机x
     */
    @Excel(name = "调机x")
    private Double debugMachinex;

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
     * 创建时间
     */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DfUpScreenPrintingbm other = (DfUpScreenPrintingbm) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getProcess() == null ? other.getProcess() == null : this.getProcess().equals(other.getProcess()))
            && (this.getProject() == null ? other.getProject() == null : this.getProject().equals(other.getProject()))
            && (this.getTestProject() == null ? other.getTestProject() == null : this.getTestProject().equals(other.getTestProject()))
            && (this.getOnePointy2() == null ? other.getOnePointy2() == null : this.getOnePointy2().equals(other.getOnePointy2()))
            && (this.getTwoPointy1() == null ? other.getTwoPointy1() == null : this.getTwoPointy1().equals(other.getTwoPointy1()))
            && (this.getThreePointx() == null ? other.getThreePointx() == null : this.getThreePointx().equals(other.getThreePointx()))
            && (this.getFourPointx() == null ? other.getFourPointx() == null : this.getFourPointx().equals(other.getFourPointx()))
            && (this.getFivePointy1() == null ? other.getFivePointy1() == null : this.getFivePointy1().equals(other.getFivePointy1()))
            && (this.getSixPointy2() == null ? other.getSixPointy2() == null : this.getSixPointy2().equals(other.getSixPointy2()))
            && (this.getSevenPointx() == null ? other.getSevenPointx() == null : this.getSevenPointx().equals(other.getSevenPointx()))
            && (this.getEightPointx() == null ? other.getEightPointx() == null : this.getEightPointx().equals(other.getEightPointx()))
            && (this.getWindowLength1() == null ? other.getWindowLength1() == null : this.getWindowLength1().equals(other.getWindowLength1()))
            && (this.getWindowLength2() == null ? other.getWindowLength2() == null : this.getWindowLength2().equals(other.getWindowLength2()))
            && (this.getWindowWide1() == null ? other.getWindowWide1() == null : this.getWindowWide1().equals(other.getWindowWide1()))
            && (this.getWindowWide2() == null ? other.getWindowWide2() == null : this.getWindowWide2().equals(other.getWindowWide2()))
            && (this.getDebugMachiney1() == null ? other.getDebugMachiney1() == null : this.getDebugMachiney1().equals(other.getDebugMachiney1()))
            && (this.getDebugMachiney2() == null ? other.getDebugMachiney2() == null : this.getDebugMachiney2().equals(other.getDebugMachiney2()))
            && (this.getDebugMachinex() == null ? other.getDebugMachinex() == null : this.getDebugMachinex().equals(other.getDebugMachinex()))
            && (this.getMachineCode() == null ? other.getMachineCode() == null : this.getMachineCode().equals(other.getMachineCode()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
            && (this.getUploadName() == null ? other.getUploadName() == null : this.getUploadName().equals(other.getUploadName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getFactory() == null) ? 0 : getFactory().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getProcess() == null) ? 0 : getProcess().hashCode());
        result = prime * result + ((getProject() == null) ? 0 : getProject().hashCode());
        result = prime * result + ((getTestProject() == null) ? 0 : getTestProject().hashCode());
        result = prime * result + ((getOnePointy2() == null) ? 0 : getOnePointy2().hashCode());
        result = prime * result + ((getTwoPointy1() == null) ? 0 : getTwoPointy1().hashCode());
        result = prime * result + ((getThreePointx() == null) ? 0 : getThreePointx().hashCode());
        result = prime * result + ((getFourPointx() == null) ? 0 : getFourPointx().hashCode());
        result = prime * result + ((getFivePointy1() == null) ? 0 : getFivePointy1().hashCode());
        result = prime * result + ((getSixPointy2() == null) ? 0 : getSixPointy2().hashCode());
        result = prime * result + ((getSevenPointx() == null) ? 0 : getSevenPointx().hashCode());
        result = prime * result + ((getEightPointx() == null) ? 0 : getEightPointx().hashCode());
        result = prime * result + ((getWindowLength1() == null) ? 0 : getWindowLength1().hashCode());
        result = prime * result + ((getWindowLength2() == null) ? 0 : getWindowLength2().hashCode());
        result = prime * result + ((getWindowWide1() == null) ? 0 : getWindowWide1().hashCode());
        result = prime * result + ((getWindowWide2() == null) ? 0 : getWindowWide2().hashCode());
        result = prime * result + ((getDebugMachiney1() == null) ? 0 : getDebugMachiney1().hashCode());
        result = prime * result + ((getDebugMachiney2() == null) ? 0 : getDebugMachiney2().hashCode());
        result = prime * result + ((getDebugMachinex() == null) ? 0 : getDebugMachinex().hashCode());
        result = prime * result + ((getMachineCode() == null) ? 0 : getMachineCode().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
        result = prime * result + ((getUploadName() == null) ? 0 : getUploadName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", factory=").append(factory);
        sb.append(", model=").append(model);
        sb.append(", process=").append(process);
        sb.append(", project=").append(project);
        sb.append(", testProject=").append(testProject);
        sb.append(", onePointy2=").append(onePointy2);
        sb.append(", twoPointy1=").append(twoPointy1);
        sb.append(", threePointx=").append(threePointx);
        sb.append(", fourPointx=").append(fourPointx);
        sb.append(", fivePointy1=").append(fivePointy1);
        sb.append(", sixPointy2=").append(sixPointy2);
        sb.append(", sevenPointx=").append(sevenPointx);
        sb.append(", eightPointx=").append(eightPointx);
        sb.append(", windowLength1=").append(windowLength1);
        sb.append(", windowLength2=").append(windowLength2);
        sb.append(", windowWide1=").append(windowWide1);
        sb.append(", windowWide2=").append(windowWide2);
        sb.append(", debugMachiney1=").append(debugMachiney1);
        sb.append(", debugMachiney2=").append(debugMachiney2);
        sb.append(", debugMachinex=").append(debugMachinex);
        sb.append(", machineCode=").append(machineCode);
        sb.append(", state=").append(state);
        sb.append(", batchId=").append(batchId);
        sb.append(", uploadName=").append(uploadName);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}