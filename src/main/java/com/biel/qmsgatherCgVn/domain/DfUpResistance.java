package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 电阻
 * @TableName df_up_resistance
 */
@TableName(value ="df_up_resistance")
@Data
public class DfUpResistance {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 工厂
     */
    @Excel(name = "工厂")
    private String factory;

    /**
     * 型号
     */
    @Excel(name = "型号")
    private String model;

    /**
     * 阶段
     */
    @Excel(name = "阶段")
    private String stage;

    /**
     * 点位
     */
    @Excel(name = "点位")
    private Integer pointNo;

    /**
     * 测试时间
     */
    @Excel(name = "测试时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date testTime;

    /**
     * sd09-10
     */
    @Excel(name = "sd09-10")
    private Integer sd;

    /**
     * fd1-3
     */
    @Excel(name = "fd1-3")
    private Integer fd;

    /**
     * bc1-2
     */
    @Excel(name = "bc1-2")
    private Integer bc;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

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
     * 上传时间
     */
    @Excel(name = "上传时间",format = "yyyy-MM-dd HH:mm:ss")
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
        DfUpResistance other = (DfUpResistance) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getStage() == null ? other.getStage() == null : this.getStage().equals(other.getStage()))
            && (this.getPointNo() == null ? other.getPointNo() == null : this.getPointNo().equals(other.getPointNo()))
            && (this.getTestTime() == null ? other.getTestTime() == null : this.getTestTime().equals(other.getTestTime()))
            && (this.getSd() == null ? other.getSd() == null : this.getSd().equals(other.getSd()))
            && (this.getFd() == null ? other.getFd() == null : this.getFd().equals(other.getFd()))
            && (this.getBc() == null ? other.getBc() == null : this.getBc().equals(other.getBc()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
            && (this.getUploadName() == null ? other.getUploadName() == null : this.getUploadName().equals(other.getUploadName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFactory() == null) ? 0 : getFactory().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getStage() == null) ? 0 : getStage().hashCode());
        result = prime * result + ((getPointNo() == null) ? 0 : getPointNo().hashCode());
        result = prime * result + ((getTestTime() == null) ? 0 : getTestTime().hashCode());
        result = prime * result + ((getSd() == null) ? 0 : getSd().hashCode());
        result = prime * result + ((getFd() == null) ? 0 : getFd().hashCode());
        result = prime * result + ((getBc() == null) ? 0 : getBc().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
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
        sb.append(", factory=").append(factory);
        sb.append(", model=").append(model);
        sb.append(", stage=").append(stage);
        sb.append(", pointNo=").append(pointNo);
        sb.append(", testTime=").append(testTime);
        sb.append(", sd=").append(sd);
        sb.append(", fd=").append(fd);
        sb.append(", bc=").append(bc);
        sb.append(", remark=").append(remark);
        sb.append(", batchId=").append(batchId);
        sb.append(", uploadName=").append(uploadName);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}