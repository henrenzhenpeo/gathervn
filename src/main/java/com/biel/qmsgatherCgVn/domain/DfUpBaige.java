package com.biel.qmsgatherCgVn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 百格测试
 * @TableName df_up_baige
 */
@TableName(value ="df_up_baige")
@Data
public class DfUpBaige {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 工序
     */
    @Excel(name = "工序")
    private String process;

    /**
     * 工厂
     */
    @Excel(name = "工厂")
    private String factory;

    /**
     * 项目
     */
    @Excel(name = "项目")
    private String project;

    /**
     * 阶段
     */
    @Excel(name = "阶段")
    private String stage;

    /**
     * 日期
     */
    @Excel(name = "日期",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private Integer no;

    /**
     * 出炉时间
     */
    @Excel(name = "出炉时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;

    /**
     * 考炉号
     */
    @Excel(name = "考炉号")
    private String examFurnaceNumber;

    /**
     * 测试时间
     */
    @Excel(name = "测试时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date testTime;

    /**
     * 
     */
    @Excel(name = "sample1")
    private String sample1;

    /**
     * 
     */
    @Excel(name = "sample2")
    private String sample2;

    /**
     * 
     */
    @Excel(name = "sample3")
    private String sample3;

    /**
     * 
     */
    @Excel(name = "sample4")
    private String sample4;

    /**
     * 
     */
    @Excel(name = "sample5")
    private String sample5;

    /**
     * 测试结果
     */
    @Excel(name = "测试结果")
    private String testResult;

    /**
     * 取出时间
     */
    @Excel(name = "取出时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date popTime;

    /**
     * mek浸泡测试数量
     */
    @Excel(name = "mek浸泡测试数量")
    private String mekSoakTestNumber;

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
        DfUpBaige other = (DfUpBaige) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProcess() == null ? other.getProcess() == null : this.getProcess().equals(other.getProcess()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getProject() == null ? other.getProject() == null : this.getProject().equals(other.getProject()))
            && (this.getStage() == null ? other.getStage() == null : this.getStage().equals(other.getStage()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getNo() == null ? other.getNo() == null : this.getNo().equals(other.getNo()))
            && (this.getReleaseTime() == null ? other.getReleaseTime() == null : this.getReleaseTime().equals(other.getReleaseTime()))
            && (this.getExamFurnaceNumber() == null ? other.getExamFurnaceNumber() == null : this.getExamFurnaceNumber().equals(other.getExamFurnaceNumber()))
            && (this.getTestTime() == null ? other.getTestTime() == null : this.getTestTime().equals(other.getTestTime()))
            && (this.getSample1() == null ? other.getSample1() == null : this.getSample1().equals(other.getSample1()))
            && (this.getSample2() == null ? other.getSample2() == null : this.getSample2().equals(other.getSample2()))
            && (this.getSample3() == null ? other.getSample3() == null : this.getSample3().equals(other.getSample3()))
            && (this.getSample4() == null ? other.getSample4() == null : this.getSample4().equals(other.getSample4()))
            && (this.getSample5() == null ? other.getSample5() == null : this.getSample5().equals(other.getSample5()))
            && (this.getTestResult() == null ? other.getTestResult() == null : this.getTestResult().equals(other.getTestResult()))
            && (this.getPopTime() == null ? other.getPopTime() == null : this.getPopTime().equals(other.getPopTime()))
            && (this.getMekSoakTestNumber() == null ? other.getMekSoakTestNumber() == null : this.getMekSoakTestNumber().equals(other.getMekSoakTestNumber()))
            && (this.getUploadName() == null ? other.getUploadName() == null : this.getUploadName().equals(other.getUploadName()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProcess() == null) ? 0 : getProcess().hashCode());
        result = prime * result + ((getFactory() == null) ? 0 : getFactory().hashCode());
        result = prime * result + ((getProject() == null) ? 0 : getProject().hashCode());
        result = prime * result + ((getStage() == null) ? 0 : getStage().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getNo() == null) ? 0 : getNo().hashCode());
        result = prime * result + ((getReleaseTime() == null) ? 0 : getReleaseTime().hashCode());
        result = prime * result + ((getExamFurnaceNumber() == null) ? 0 : getExamFurnaceNumber().hashCode());
        result = prime * result + ((getTestTime() == null) ? 0 : getTestTime().hashCode());
        result = prime * result + ((getSample1() == null) ? 0 : getSample1().hashCode());
        result = prime * result + ((getSample2() == null) ? 0 : getSample2().hashCode());
        result = prime * result + ((getSample3() == null) ? 0 : getSample3().hashCode());
        result = prime * result + ((getSample4() == null) ? 0 : getSample4().hashCode());
        result = prime * result + ((getSample5() == null) ? 0 : getSample5().hashCode());
        result = prime * result + ((getTestResult() == null) ? 0 : getTestResult().hashCode());
        result = prime * result + ((getPopTime() == null) ? 0 : getPopTime().hashCode());
        result = prime * result + ((getMekSoakTestNumber() == null) ? 0 : getMekSoakTestNumber().hashCode());
        result = prime * result + ((getUploadName() == null) ? 0 : getUploadName().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
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
        sb.append(", project=").append(project);
        sb.append(", stage=").append(stage);
        sb.append(", date=").append(date);
        sb.append(", no=").append(no);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", examFurnaceNumber=").append(examFurnaceNumber);
        sb.append(", testTime=").append(testTime);
        sb.append(", sample1=").append(sample1);
        sb.append(", sample2=").append(sample2);
        sb.append(", sample3=").append(sample3);
        sb.append(", sample4=").append(sample4);
        sb.append(", sample5=").append(sample5);
        sb.append(", testResult=").append(testResult);
        sb.append(", popTime=").append(popTime);
        sb.append(", mekSoakTestNumber=").append(mekSoakTestNumber);
        sb.append(", uploadName=").append(uploadName);
        sb.append(", batchId=").append(batchId);
        sb.append("]");
        return sb.toString();
    }
}