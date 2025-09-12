package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 丝印线框
 * @TableName df_up_silk_screen_wireframe
 */
@TableName(value ="df_up_silk_screen_wireframe")
@Data
public class DfUpSilkScreenWireframe {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 时间日期
     */
    @Excel(name = "时间日期", exportFormat = "yyyy-MM-dd HH:mm:ss")
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
     * r角1
     */
    @Excel(name = "r角1")
    private Double r1;

    /**
     * r角2
     */
    @Excel(name = "r角2")
    private Double r2;

    /**
     * r角3
     */
    @Excel(name = "r角3")
    private Double r3;

    /**
     * r角4
     */
    @Excel(name = "r角4")
    private Double r4;

    /**
     * 2d镭码
     */
    @Excel(name = "2d镭码")
    private Double twoRadiumCode;

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
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
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
        DfUpSilkScreenWireframe other = (DfUpSilkScreenWireframe) that;
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
            && (this.getR1() == null ? other.getR1() == null : this.getR1().equals(other.getR1()))
            && (this.getR2() == null ? other.getR2() == null : this.getR2().equals(other.getR2()))
            && (this.getR3() == null ? other.getR3() == null : this.getR3().equals(other.getR3()))
            && (this.getR4() == null ? other.getR4() == null : this.getR4().equals(other.getR4()))
            && (this.getTwoRadiumCode() == null ? other.getTwoRadiumCode() == null : this.getTwoRadiumCode().equals(other.getTwoRadiumCode()))
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
        result = prime * result + ((getR1() == null) ? 0 : getR1().hashCode());
        result = prime * result + ((getR2() == null) ? 0 : getR2().hashCode());
        result = prime * result + ((getR3() == null) ? 0 : getR3().hashCode());
        result = prime * result + ((getR4() == null) ? 0 : getR4().hashCode());
        result = prime * result + ((getTwoRadiumCode() == null) ? 0 : getTwoRadiumCode().hashCode());
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
        sb.append(", r1=").append(r1);
        sb.append(", r2=").append(r2);
        sb.append(", r3=").append(r3);
        sb.append(", r4=").append(r4);
        sb.append(", twoRadiumCode=").append(twoRadiumCode);
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