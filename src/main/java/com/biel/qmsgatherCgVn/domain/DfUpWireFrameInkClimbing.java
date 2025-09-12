package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 线框油墨爬高
 * @TableName df_up_wire_frame_ink_climbing
 */
@TableName(value ="df_up_wire_frame_ink_climbing")
@Data
public class DfUpWireFrameInkClimbing {
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
     * 测试项目
     */
    @Excel(name = "测试项目")
    private String testProject;

    /**
     * 长边1
     */
    @Excel(name = "长边1")
    private Double longSide1;

    /**
     * 长边2
     */
    @Excel(name = "长边2")
    private Double longSide2;

    /**
     * aoc
     */
    @Excel(name = "凹槽")
    private Double groove;

    /**
     * 凹槽短边
     */
    @Excel(name = "凹槽短边")
    private Double shortGroove;

    /**
     * 短边
     */
    @Excel(name = "短边")
    private Double shortSide;

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
     * 创建时间
     */
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 上传人
     */
    @Excel(name = "上传人")
    private String uploadName;

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
        DfUpWireFrameInkClimbing other = (DfUpWireFrameInkClimbing) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getProcess() == null ? other.getProcess() == null : this.getProcess().equals(other.getProcess()))
            && (this.getTestProject() == null ? other.getTestProject() == null : this.getTestProject().equals(other.getTestProject()))
            && (this.getLongSide1() == null ? other.getLongSide1() == null : this.getLongSide1().equals(other.getLongSide1()))
            && (this.getLongSide2() == null ? other.getLongSide2() == null : this.getLongSide2().equals(other.getLongSide2()))
            && (this.getGroove() == null ? other.getGroove() == null : this.getGroove().equals(other.getGroove()))
            && (this.getShortGroove() == null ? other.getShortGroove() == null : this.getShortGroove().equals(other.getShortGroove()))
            && (this.getShortSide() == null ? other.getShortSide() == null : this.getShortSide().equals(other.getShortSide()))
            && (this.getMachineCode() == null ? other.getMachineCode() == null : this.getMachineCode().equals(other.getMachineCode()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUploadName() == null ? other.getUploadName() == null : this.getUploadName().equals(other.getUploadName()));
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
        result = prime * result + ((getTestProject() == null) ? 0 : getTestProject().hashCode());
        result = prime * result + ((getLongSide1() == null) ? 0 : getLongSide1().hashCode());
        result = prime * result + ((getLongSide2() == null) ? 0 : getLongSide2().hashCode());
        result = prime * result + ((getGroove() == null) ? 0 : getGroove().hashCode());
        result = prime * result + ((getShortGroove() == null) ? 0 : getShortGroove().hashCode());
        result = prime * result + ((getShortSide() == null) ? 0 : getShortSide().hashCode());
        result = prime * result + ((getMachineCode() == null) ? 0 : getMachineCode().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUploadName() == null) ? 0 : getUploadName().hashCode());
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
        sb.append(", testProject=").append(testProject);
        sb.append(", longSide1=").append(longSide1);
        sb.append(", longSide2=").append(longSide2);
        sb.append(", groove=").append(groove);
        sb.append(", shortGroove=").append(shortGroove);
        sb.append(", shortSide=").append(shortSide);
        sb.append(", machineCode=").append(machineCode);
        sb.append(", state=").append(state);
        sb.append(", batchId=").append(batchId);
        sb.append(", createTime=").append(createTime);
        sb.append(", uploadName=").append(uploadName);
        sb.append("]");
        return sb.toString();
    }
}