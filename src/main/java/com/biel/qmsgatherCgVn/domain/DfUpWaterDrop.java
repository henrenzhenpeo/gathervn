package com.biel.qmsgatherCgVn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 水滴角上传
 * @TableName df_up_water_drop
 */
@TableName(value ="df_up_water_drop")
@Data
public class DfUpWaterDrop {
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
     * 型号
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
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 编号
     */
    private Integer no;

    /**
     * 测试时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date testTime;

    /**
     * 测试班别（A，B）
     */
    private String testClass;

    /**
     * 机台号
     */
    private String machineCode;

    /**
     *
     */
    private Integer m4;

    /**
     *
     */
    private Integer m6;

    /**
     *
     */
    private Integer l2;

    /**
     *
     */
    private Integer l5;

    /**
     *
     */
    private Integer m1;

    /**
     *
     */
    private Integer m3;

    /**
     *
     */
    private String result;

    /**
     *
     */
    private String dayinbiTest;

    /**
     *
     */
    private String status;

    /**
     * 批次号
     */
    private String batchId;

    /**
     *
     */
    private String uploadName;

    /**
     *
     */
    private String uploadTime;

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
        DfUpWaterDrop other = (DfUpWaterDrop) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getProcess() == null ? other.getProcess() == null : this.getProcess().equals(other.getProcess()))
                && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
                && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
                && (this.getStage() == null ? other.getStage() == null : this.getStage().equals(other.getStage()))
                && (this.getTestRegion() == null ? other.getTestRegion() == null : this.getTestRegion().equals(other.getTestRegion()))
                && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
                && (this.getNo() == null ? other.getNo() == null : this.getNo().equals(other.getNo()))
                && (this.getTestTime() == null ? other.getTestTime() == null : this.getTestTime().equals(other.getTestTime()))
                && (this.getTestClass() == null ? other.getTestClass() == null : this.getTestClass().equals(other.getTestClass()))
                && (this.getMachineCode() == null ? other.getMachineCode() == null : this.getMachineCode().equals(other.getMachineCode()))
                && (this.getM4() == null ? other.getM4() == null : this.getM4().equals(other.getM4()))
                && (this.getM6() == null ? other.getM6() == null : this.getM6().equals(other.getM6()))
                && (this.getL2() == null ? other.getL2() == null : this.getL2().equals(other.getL2()))
                && (this.getL5() == null ? other.getL5() == null : this.getL5().equals(other.getL5()))
                && (this.getM1() == null ? other.getM1() == null : this.getM1().equals(other.getM1()))
                && (this.getM3() == null ? other.getM3() == null : this.getM3().equals(other.getM3()))
                && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
                && (this.getDayinbiTest() == null ? other.getDayinbiTest() == null : this.getDayinbiTest().equals(other.getDayinbiTest()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
                && (this.getUploadName() == null ? other.getUploadName() == null : this.getUploadName().equals(other.getUploadName()))
                && (this.getUploadTime() == null ? other.getUploadTime() == null : this.getUploadTime().equals(other.getUploadTime()));
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
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getNo() == null) ? 0 : getNo().hashCode());
        result = prime * result + ((getTestTime() == null) ? 0 : getTestTime().hashCode());
        result = prime * result + ((getTestClass() == null) ? 0 : getTestClass().hashCode());
        result = prime * result + ((getMachineCode() == null) ? 0 : getMachineCode().hashCode());
        result = prime * result + ((getM4() == null) ? 0 : getM4().hashCode());
        result = prime * result + ((getM6() == null) ? 0 : getM6().hashCode());
        result = prime * result + ((getL2() == null) ? 0 : getL2().hashCode());
        result = prime * result + ((getL5() == null) ? 0 : getL5().hashCode());
        result = prime * result + ((getM1() == null) ? 0 : getM1().hashCode());
        result = prime * result + ((getM3() == null) ? 0 : getM3().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getDayinbiTest() == null) ? 0 : getDayinbiTest().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
        result = prime * result + ((getUploadName() == null) ? 0 : getUploadName().hashCode());
        result = prime * result + ((getUploadTime() == null) ? 0 : getUploadTime().hashCode());
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
        sb.append(", date=").append(date);
        sb.append(", no=").append(no);
        sb.append(", testTime=").append(testTime);
        sb.append(", testClass=").append(testClass);
        sb.append(", machineCode=").append(machineCode);
        sb.append(", m4=").append(m4);
        sb.append(", m6=").append(m6);
        sb.append(", l2=").append(l2);
        sb.append(", l5=").append(l5);
        sb.append(", m1=").append(m1);
        sb.append(", m3=").append(m3);
        sb.append(", result=").append(result);
        sb.append(", dayinbiTest=").append(dayinbiTest);
        sb.append(", status=").append(status);
        sb.append(", batchId=").append(batchId);
        sb.append(", uploadName=").append(uploadName);
        sb.append(", uploadTime=").append(uploadTime);
        sb.append("]");
        return sb.toString();
    }
}