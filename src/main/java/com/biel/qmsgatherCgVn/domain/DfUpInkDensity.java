package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 油墨密度-od
 * @TableName df_up_ink_density
 */
@TableName(value ="df_up_ink_density")
@Data
public class DfUpInkDensity {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 过程
     */
    @Excel(name = "过程")
    private String process;

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
     * 时间
     */
    @Excel(name = "时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 编号no
     */
    @Excel(name = "编号no")
    private Integer noValue;

    /**
     * 测量值
     */
    @Excel(name = "测量值")
    private Double testValue;

    /**
     * 判定
     */
    @Excel(name = "判定")
    private String result;

    /**
     * 上传时间
     */
    @Excel(name = "上传时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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
        DfUpInkDensity other = (DfUpInkDensity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProcess() == null ? other.getProcess() == null : this.getProcess().equals(other.getProcess()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getStage() == null ? other.getStage() == null : this.getStage().equals(other.getStage()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getNoValue() == null ? other.getNoValue() == null : this.getNoValue().equals(other.getNoValue()))
            && (this.getTestValue() == null ? other.getTestValue() == null : this.getTestValue().equals(other.getTestValue()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
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
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getNoValue() == null) ? 0 : getNoValue().hashCode());
        result = prime * result + ((getTestValue() == null) ? 0 : getTestValue().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
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
        sb.append(", date=").append(date);
        sb.append(", noValue=").append(noValue);
        sb.append(", testValue=").append(testValue);
        sb.append(", result=").append(result);
        sb.append(", createTime=").append(createTime);
        sb.append(", batchId=").append(batchId);
        sb.append(", uploadName=").append(uploadName);
        sb.append("]");
        return sb.toString();
    }
}