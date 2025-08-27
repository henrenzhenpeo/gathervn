package com.biel.qmsgatherCgVn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 电膜AS/WSA
 * @TableName df_up_water_drop_as_wsa
 */
@TableName(value ="df_up_water_drop_as_wsa")
@Data
public class DfUpWaterDropAsWsa {
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
    private String clssShift;

    /**
     * 机台号
     */
    private String machineCode;

    /**
     * 锅数
     */
    private String potNumber;

    /**
     * 
     */
    private Integer aaa;

    /**
     * 
     */
    private Integer aathetaa;

    /**
     * 
     */
    private Integer aathetar;

    /**
     * 
     */
    private Integer aba;

    /**
     * 
     */
    private Integer abthetaa;

    /**
     * 
     */
    private Integer abthetar;

    /**
     * 
     */
    private Integer aca;

    /**
     * 
     */
    private Integer acthetaa;

    /**
     * 
     */
    private Integer acthetar;

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
        DfUpWaterDropAsWsa other = (DfUpWaterDropAsWsa) that;
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
            && (this.getClssShift() == null ? other.getClssShift() == null : this.getClssShift().equals(other.getClssShift()))
            && (this.getMachineCode() == null ? other.getMachineCode() == null : this.getMachineCode().equals(other.getMachineCode()))
            && (this.getPotNumber() == null ? other.getPotNumber() == null : this.getPotNumber().equals(other.getPotNumber()))
            && (this.getAaa() == null ? other.getAaa() == null : this.getAaa().equals(other.getAaa()))
            && (this.getAathetaa() == null ? other.getAathetaa() == null : this.getAathetaa().equals(other.getAathetaa()))
            && (this.getAathetar() == null ? other.getAathetar() == null : this.getAathetar().equals(other.getAathetar()))
            && (this.getAba() == null ? other.getAba() == null : this.getAba().equals(other.getAba()))
            && (this.getAbthetaa() == null ? other.getAbthetaa() == null : this.getAbthetaa().equals(other.getAbthetaa()))
            && (this.getAbthetar() == null ? other.getAbthetar() == null : this.getAbthetar().equals(other.getAbthetar()))
            && (this.getAca() == null ? other.getAca() == null : this.getAca().equals(other.getAca()))
            && (this.getActhetaa() == null ? other.getActhetaa() == null : this.getActhetaa().equals(other.getActhetaa()))
            && (this.getActhetar() == null ? other.getActhetar() == null : this.getActhetar().equals(other.getActhetar()))
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
        result = prime * result + ((getClssShift() == null) ? 0 : getClssShift().hashCode());
        result = prime * result + ((getMachineCode() == null) ? 0 : getMachineCode().hashCode());
        result = prime * result + ((getPotNumber() == null) ? 0 : getPotNumber().hashCode());
        result = prime * result + ((getAaa() == null) ? 0 : getAaa().hashCode());
        result = prime * result + ((getAathetaa() == null) ? 0 : getAathetaa().hashCode());
        result = prime * result + ((getAathetar() == null) ? 0 : getAathetar().hashCode());
        result = prime * result + ((getAba() == null) ? 0 : getAba().hashCode());
        result = prime * result + ((getAbthetaa() == null) ? 0 : getAbthetaa().hashCode());
        result = prime * result + ((getAbthetar() == null) ? 0 : getAbthetar().hashCode());
        result = prime * result + ((getAca() == null) ? 0 : getAca().hashCode());
        result = prime * result + ((getActhetaa() == null) ? 0 : getActhetaa().hashCode());
        result = prime * result + ((getActhetar() == null) ? 0 : getActhetar().hashCode());
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
        sb.append(", clssShift=").append(clssShift);
        sb.append(", machineCode=").append(machineCode);
        sb.append(", potNumber=").append(potNumber);
        sb.append(", aaa=").append(aaa);
        sb.append(", aathetaa=").append(aathetaa);
        sb.append(", aathetar=").append(aathetar);
        sb.append(", aba=").append(aba);
        sb.append(", abthetaa=").append(abthetaa);
        sb.append(", abthetar=").append(abthetar);
        sb.append(", aca=").append(aca);
        sb.append(", acthetaa=").append(acthetaa);
        sb.append(", acthetar=").append(acthetar);
        sb.append(", batchId=").append(batchId);
        sb.append(", uploadName=").append(uploadName);
        sb.append("]");
        return sb.toString();
    }
}