package com.biel.qmsgatherCgVn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 电膜AS coating
 * @TableName df_up_water_drop_as_coating
 */
@TableName(value ="df_up_water_drop_as_coating")
@Data
public class DfUpWaterDropAsCoating {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 工序
     */
    private String process;

    /**
     * 工厂
     */
    private String factory;

    /**
     * 模型
     */
    private String model;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 测试区域
     */
    private String testRegion;

    /**
     * 测试项目
     */
    private String testProject;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 
     */
    private Integer no;

    /**
     * 测试时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date testTime;

    /**
     * 班别
     */
    private String classShift;

    /**
     * 机台号
     */
    private String machineCode;

    /**
     * 锅数
     */
    private Integer potNumber;

    /**
     * 
     */
    private Integer beforeP1;

    /**
     * 
     */
    private Integer beforeP2;

    /**
     * 
     */
    private Integer beforeP3;

    /**
     * 
     */
    private Integer beforeP4;

    /**
     * 
     */
    private Integer beforeMin;

    /**
     * 
     */
    private Integer afterP1;

    /**
     * 
     */
    private Integer afterP2;

    /**
     * 
     */
    private Integer afterP3;

    /**
     * 
     */
    private Integer afterMin;

    /**
     * 批次号
     */
    private String batchId;

    /**
     * 上传人
     */
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
        DfUpWaterDropAsCoating other = (DfUpWaterDropAsCoating) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProcess() == null ? other.getProcess() == null : this.getProcess().equals(other.getProcess()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getStage() == null ? other.getStage() == null : this.getStage().equals(other.getStage()))
            && (this.getTestRegion() == null ? other.getTestRegion() == null : this.getTestRegion().equals(other.getTestRegion()))
            && (this.getTestProject() == null ? other.getTestProject() == null : this.getTestProject().equals(other.getTestProject()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getNo() == null ? other.getNo() == null : this.getNo().equals(other.getNo()))
            && (this.getTestTime() == null ? other.getTestTime() == null : this.getTestTime().equals(other.getTestTime()))
            && (this.getClassShift() == null ? other.getClassShift() == null : this.getClassShift().equals(other.getClassShift()))
            && (this.getMachineCode() == null ? other.getMachineCode() == null : this.getMachineCode().equals(other.getMachineCode()))
            && (this.getPotNumber() == null ? other.getPotNumber() == null : this.getPotNumber().equals(other.getPotNumber()))
            && (this.getBeforeP1() == null ? other.getBeforeP1() == null : this.getBeforeP1().equals(other.getBeforeP1()))
            && (this.getBeforeP2() == null ? other.getBeforeP2() == null : this.getBeforeP2().equals(other.getBeforeP2()))
            && (this.getBeforeP3() == null ? other.getBeforeP3() == null : this.getBeforeP3().equals(other.getBeforeP3()))
            && (this.getBeforeP4() == null ? other.getBeforeP4() == null : this.getBeforeP4().equals(other.getBeforeP4()))
            && (this.getBeforeMin() == null ? other.getBeforeMin() == null : this.getBeforeMin().equals(other.getBeforeMin()))
            && (this.getAfterP1() == null ? other.getAfterP1() == null : this.getAfterP1().equals(other.getAfterP1()))
            && (this.getAfterP2() == null ? other.getAfterP2() == null : this.getAfterP2().equals(other.getAfterP2()))
            && (this.getAfterP3() == null ? other.getAfterP3() == null : this.getAfterP3().equals(other.getAfterP3()))
            && (this.getAfterMin() == null ? other.getAfterMin() == null : this.getAfterMin().equals(other.getAfterMin()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
            && (this.getUploadName() == null ? other.getUploadName() == null : this.getUploadName().equals(other.getUploadName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProcess() == null) ? 0 : getProcess().hashCode());
        result = prime * result + ((getFactory() == null) ? 0 : getFactory().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getStage() == null) ? 0 : getStage().hashCode());
        result = prime * result + ((getTestRegion() == null) ? 0 : getTestRegion().hashCode());
        result = prime * result + ((getTestProject() == null) ? 0 : getTestProject().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getNo() == null) ? 0 : getNo().hashCode());
        result = prime * result + ((getTestTime() == null) ? 0 : getTestTime().hashCode());
        result = prime * result + ((getClassShift() == null) ? 0 : getClassShift().hashCode());
        result = prime * result + ((getMachineCode() == null) ? 0 : getMachineCode().hashCode());
        result = prime * result + ((getPotNumber() == null) ? 0 : getPotNumber().hashCode());
        result = prime * result + ((getBeforeP1() == null) ? 0 : getBeforeP1().hashCode());
        result = prime * result + ((getBeforeP2() == null) ? 0 : getBeforeP2().hashCode());
        result = prime * result + ((getBeforeP3() == null) ? 0 : getBeforeP3().hashCode());
        result = prime * result + ((getBeforeP4() == null) ? 0 : getBeforeP4().hashCode());
        result = prime * result + ((getBeforeMin() == null) ? 0 : getBeforeMin().hashCode());
        result = prime * result + ((getAfterP1() == null) ? 0 : getAfterP1().hashCode());
        result = prime * result + ((getAfterP2() == null) ? 0 : getAfterP2().hashCode());
        result = prime * result + ((getAfterP3() == null) ? 0 : getAfterP3().hashCode());
        result = prime * result + ((getAfterMin() == null) ? 0 : getAfterMin().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
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
        sb.append(", process=").append(process);
        sb.append(", factory=").append(factory);
        sb.append(", model=").append(model);
        sb.append(", stage=").append(stage);
        sb.append(", testRegion=").append(testRegion);
        sb.append(", testProject=").append(testProject);
        sb.append(", date=").append(date);
        sb.append(", no=").append(no);
        sb.append(", testTime=").append(testTime);
        sb.append(", classShift=").append(classShift);
        sb.append(", machineCode=").append(machineCode);
        sb.append(", potNumber=").append(potNumber);
        sb.append(", beforeP1=").append(beforeP1);
        sb.append(", beforeP2=").append(beforeP2);
        sb.append(", beforeP3=").append(beforeP3);
        sb.append(", beforeP4=").append(beforeP4);
        sb.append(", beforeMin=").append(beforeMin);
        sb.append(", afterP1=").append(afterP1);
        sb.append(", afterP2=").append(afterP2);
        sb.append(", afterP3=").append(afterP3);
        sb.append(", afterMin=").append(afterMin);
        sb.append(", batchId=").append(batchId);
        sb.append(", uploadName=").append(uploadName);
        sb.append("]");
        return sb.toString();
    }
}