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
 * 膜材尺寸
 * @TableName df_up_file_material_size
 */
@TableName(value ="df_up_file_material_size")
@Data
public class DfUpFileMaterialSize {
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
     * 型号
     */
    @Excel(name = "型号")
    private String model;

    /**
     * 工序
     */
    @Excel(name = "工序")
    private String process;

    /**
     * 测试项
     */
    @Excel(name = "测试项")
    private String testProject;

    /**
     * 膜长1
     */
    @Excel(name = "膜长1")
    private String membraneLength1;

    /**
     * 膜长2
     */
    @Excel(name = "膜长2")
    private String membraneLength2;

    /**
     * 膜宽
     */
    @Excel(name = "膜宽")
    private String membraneWidth1;

    /**
     * 膜宽2
     */
    @Excel(name = "膜宽2")
    private String membraneWidth2;

    /**
     * ar孔直径
     */
    @Excel(name = "ar孔直径")
    private String arHoleDiameter;

    /**
     * 真圆度1
     */
    @Excel(name = "真圆度1")
    private String trueRoundness1;

    /**
     * AR孔中心到膜顶上边距y
     */
    @Excel(name = "AR孔中心到膜顶上边距y")
    private String arHoleCenterTopy;

    /**
     * AR孔中心到膜顶上边距x
     */
    @Excel(name = "AR孔中心到膜顶上边距x")
    private String arHoleCenterTopx;

    /**
     * 方孔长
     */
    @Excel(name = "方孔长")
    private String squareHoleLength;

    /**
     * 方孔宽
     */
    @Excel(name = "方孔宽")
    private String squareHoleWidth;

    /**
     * 方孔中心到膜顶上边距Y
     */
    @Excel(name = "方孔中心到膜顶上边距Y")
    private String squareHoleTopy;

    /**
     * 方孔中心到膜顶上边距x
     */
    @Excel(name = "方孔中心到膜顶上边距x")
    private String squareHoleTopx;

    /**
     * 长孔直径（上）
     */
    @Excel(name = "长孔直径（上）")
    private String lengthHoleDiameterTop;

    /**
     * 真圆度2
     */
    @Excel(name = "真圆度2")
    private String trueRoundness2;

    /**
     * 长孔直径（下）
     */
    @Excel(name = "长孔直径（下）")
    private String lengthHoleDiameterLower;

    /**
     * 真圆度3
     */
    @Excel(name = "真圆度3")
    private String trueRoundness3;

    /**
     * 长孔长
     */
    @Excel(name = "长孔长")
    private String lengthHoleLength;

    /**
     * 长孔宽
     */
    @Excel(name = "长孔宽")
    private String longHoleWidth;

    /**
     * 长孔上圆心到长孔下圆心距y
     */
    @Excel(name = "长孔上圆心到长孔下圆心距y")
    private String longHoleTopLongHoleLowery;

    /**
     * 长孔中心到膜顶上边距Y
     */
    @Excel(name = "长孔中心到膜顶上边距Y")
    private String longHoleCenterTopy;

    /**
     * 长孔中心到膜顶上边距x
     */
    @Excel(name = "长孔中心到膜顶上边距x")
    private String longHoleCenterTopx;

    /**
     * 小圆直径
     */
    @Excel(name = "小圆直径")
    private String smallCircleDiameter;

    /**
     * 真圆度4
     */
    @Excel(name = "真圆度4")
    private String trueRoundness4;

    /**
     * 小圆孔中心到方孔中心距X
     */
    @Excel(name = "小圆孔中心到方孔中心距X")
    private String smallHoleCenterSquareHolex;

    /**
     * 小圆孔中心到膜顶上边距Y
     */
    @Excel(name = "小圆孔中心到膜顶上边距Y")
    private String smallHoleCenterTopy;

    /**
     * 二维码口宽
     */
    @Excel(name = "二维码口宽")
    private String qrCodeWidth;

    /**
     * 二维码口高
     */
    @Excel(name = "二维码口高")
    private String qrCodeHigh;

    /**
     * 二维码口到膜中心距X
     */
    @Excel(name = "二维码口到膜中心距X")
    private String qrCodeMaterialCenterx;

    /**
     * 二维码口到膜中心距y
     */
    @Excel(name = "二维码口到膜中心距y")
    private String qrCodeMaterialCentery;

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
        DfUpFileMaterialSize other = (DfUpFileMaterialSize) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getFactory() == null ? other.getFactory() == null : this.getFactory().equals(other.getFactory()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getProcess() == null ? other.getProcess() == null : this.getProcess().equals(other.getProcess()))
            && (this.getTestProject() == null ? other.getTestProject() == null : this.getTestProject().equals(other.getTestProject()))
            && (this.getMembraneLength1() == null ? other.getMembraneLength1() == null : this.getMembraneLength1().equals(other.getMembraneLength1()))
            && (this.getMembraneLength2() == null ? other.getMembraneLength2() == null : this.getMembraneLength2().equals(other.getMembraneLength2()))
            && (this.getMembraneWidth1() == null ? other.getMembraneWidth1() == null : this.getMembraneWidth1().equals(other.getMembraneWidth1()))
            && (this.getMembraneWidth2() == null ? other.getMembraneWidth2() == null : this.getMembraneWidth2().equals(other.getMembraneWidth2()))
            && (this.getArHoleDiameter() == null ? other.getArHoleDiameter() == null : this.getArHoleDiameter().equals(other.getArHoleDiameter()))
            && (this.getTrueRoundness1() == null ? other.getTrueRoundness1() == null : this.getTrueRoundness1().equals(other.getTrueRoundness1()))
            && (this.getArHoleCenterTopy() == null ? other.getArHoleCenterTopy() == null : this.getArHoleCenterTopy().equals(other.getArHoleCenterTopy()))
            && (this.getArHoleCenterTopx() == null ? other.getArHoleCenterTopx() == null : this.getArHoleCenterTopx().equals(other.getArHoleCenterTopx()))
            && (this.getSquareHoleLength() == null ? other.getSquareHoleLength() == null : this.getSquareHoleLength().equals(other.getSquareHoleLength()))
            && (this.getSquareHoleWidth() == null ? other.getSquareHoleWidth() == null : this.getSquareHoleWidth().equals(other.getSquareHoleWidth()))
            && (this.getSquareHoleTopy() == null ? other.getSquareHoleTopy() == null : this.getSquareHoleTopy().equals(other.getSquareHoleTopy()))
            && (this.getSquareHoleTopx() == null ? other.getSquareHoleTopx() == null : this.getSquareHoleTopx().equals(other.getSquareHoleTopx()))
            && (this.getLengthHoleDiameterTop() == null ? other.getLengthHoleDiameterTop() == null : this.getLengthHoleDiameterTop().equals(other.getLengthHoleDiameterTop()))
            && (this.getTrueRoundness2() == null ? other.getTrueRoundness2() == null : this.getTrueRoundness2().equals(other.getTrueRoundness2()))
            && (this.getLengthHoleDiameterLower() == null ? other.getLengthHoleDiameterLower() == null : this.getLengthHoleDiameterLower().equals(other.getLengthHoleDiameterLower()))
            && (this.getTrueRoundness3() == null ? other.getTrueRoundness3() == null : this.getTrueRoundness3().equals(other.getTrueRoundness3()))
            && (this.getLengthHoleLength() == null ? other.getLengthHoleLength() == null : this.getLengthHoleLength().equals(other.getLengthHoleLength()))
            && (this.getLongHoleWidth() == null ? other.getLongHoleWidth() == null : this.getLongHoleWidth().equals(other.getLongHoleWidth()))
            && (this.getLongHoleTopLongHoleLowery() == null ? other.getLongHoleTopLongHoleLowery() == null : this.getLongHoleTopLongHoleLowery().equals(other.getLongHoleTopLongHoleLowery()))
            && (this.getLongHoleCenterTopy() == null ? other.getLongHoleCenterTopy() == null : this.getLongHoleCenterTopy().equals(other.getLongHoleCenterTopy()))
            && (this.getLongHoleCenterTopx() == null ? other.getLongHoleCenterTopx() == null : this.getLongHoleCenterTopx().equals(other.getLongHoleCenterTopx()))
            && (this.getSmallCircleDiameter() == null ? other.getSmallCircleDiameter() == null : this.getSmallCircleDiameter().equals(other.getSmallCircleDiameter()))
            && (this.getTrueRoundness4() == null ? other.getTrueRoundness4() == null : this.getTrueRoundness4().equals(other.getTrueRoundness4()))
            && (this.getSmallHoleCenterSquareHolex() == null ? other.getSmallHoleCenterSquareHolex() == null : this.getSmallHoleCenterSquareHolex().equals(other.getSmallHoleCenterSquareHolex()))
            && (this.getSmallHoleCenterTopy() == null ? other.getSmallHoleCenterTopy() == null : this.getSmallHoleCenterTopy().equals(other.getSmallHoleCenterTopy()))
            && (this.getQrCodeWidth() == null ? other.getQrCodeWidth() == null : this.getQrCodeWidth().equals(other.getQrCodeWidth()))
            && (this.getQrCodeHigh() == null ? other.getQrCodeHigh() == null : this.getQrCodeHigh().equals(other.getQrCodeHigh()))
            && (this.getQrCodeMaterialCenterx() == null ? other.getQrCodeMaterialCenterx() == null : this.getQrCodeMaterialCenterx().equals(other.getQrCodeMaterialCenterx()))
            && (this.getQrCodeMaterialCentery() == null ? other.getQrCodeMaterialCentery() == null : this.getQrCodeMaterialCentery().equals(other.getQrCodeMaterialCentery()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUploadName() == null ? other.getUploadName() == null : this.getUploadName().equals(other.getUploadName()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()));
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
        result = prime * result + ((getMembraneLength1() == null) ? 0 : getMembraneLength1().hashCode());
        result = prime * result + ((getMembraneLength2() == null) ? 0 : getMembraneLength2().hashCode());
        result = prime * result + ((getMembraneWidth1() == null) ? 0 : getMembraneWidth1().hashCode());
        result = prime * result + ((getMembraneWidth2() == null) ? 0 : getMembraneWidth2().hashCode());
        result = prime * result + ((getArHoleDiameter() == null) ? 0 : getArHoleDiameter().hashCode());
        result = prime * result + ((getTrueRoundness1() == null) ? 0 : getTrueRoundness1().hashCode());
        result = prime * result + ((getArHoleCenterTopy() == null) ? 0 : getArHoleCenterTopy().hashCode());
        result = prime * result + ((getArHoleCenterTopx() == null) ? 0 : getArHoleCenterTopx().hashCode());
        result = prime * result + ((getSquareHoleLength() == null) ? 0 : getSquareHoleLength().hashCode());
        result = prime * result + ((getSquareHoleWidth() == null) ? 0 : getSquareHoleWidth().hashCode());
        result = prime * result + ((getSquareHoleTopy() == null) ? 0 : getSquareHoleTopy().hashCode());
        result = prime * result + ((getSquareHoleTopx() == null) ? 0 : getSquareHoleTopx().hashCode());
        result = prime * result + ((getLengthHoleDiameterTop() == null) ? 0 : getLengthHoleDiameterTop().hashCode());
        result = prime * result + ((getTrueRoundness2() == null) ? 0 : getTrueRoundness2().hashCode());
        result = prime * result + ((getLengthHoleDiameterLower() == null) ? 0 : getLengthHoleDiameterLower().hashCode());
        result = prime * result + ((getTrueRoundness3() == null) ? 0 : getTrueRoundness3().hashCode());
        result = prime * result + ((getLengthHoleLength() == null) ? 0 : getLengthHoleLength().hashCode());
        result = prime * result + ((getLongHoleWidth() == null) ? 0 : getLongHoleWidth().hashCode());
        result = prime * result + ((getLongHoleTopLongHoleLowery() == null) ? 0 : getLongHoleTopLongHoleLowery().hashCode());
        result = prime * result + ((getLongHoleCenterTopy() == null) ? 0 : getLongHoleCenterTopy().hashCode());
        result = prime * result + ((getLongHoleCenterTopx() == null) ? 0 : getLongHoleCenterTopx().hashCode());
        result = prime * result + ((getSmallCircleDiameter() == null) ? 0 : getSmallCircleDiameter().hashCode());
        result = prime * result + ((getTrueRoundness4() == null) ? 0 : getTrueRoundness4().hashCode());
        result = prime * result + ((getSmallHoleCenterSquareHolex() == null) ? 0 : getSmallHoleCenterSquareHolex().hashCode());
        result = prime * result + ((getSmallHoleCenterTopy() == null) ? 0 : getSmallHoleCenterTopy().hashCode());
        result = prime * result + ((getQrCodeWidth() == null) ? 0 : getQrCodeWidth().hashCode());
        result = prime * result + ((getQrCodeHigh() == null) ? 0 : getQrCodeHigh().hashCode());
        result = prime * result + ((getQrCodeMaterialCenterx() == null) ? 0 : getQrCodeMaterialCenterx().hashCode());
        result = prime * result + ((getQrCodeMaterialCentery() == null) ? 0 : getQrCodeMaterialCentery().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
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
        sb.append(", date=").append(date);
        sb.append(", factory=").append(factory);
        sb.append(", model=").append(model);
        sb.append(", process=").append(process);
        sb.append(", testProject=").append(testProject);
        sb.append(", membraneLength1=").append(membraneLength1);
        sb.append(", membraneLength2=").append(membraneLength2);
        sb.append(", membraneWidth1=").append(membraneWidth1);
        sb.append(", membraneWidth2=").append(membraneWidth2);
        sb.append(", arHoleDiameter=").append(arHoleDiameter);
        sb.append(", trueRoundness1=").append(trueRoundness1);
        sb.append(", arHoleCenterTopy=").append(arHoleCenterTopy);
        sb.append(", arHoleCenterTopx=").append(arHoleCenterTopx);
        sb.append(", squareHoleLength=").append(squareHoleLength);
        sb.append(", squareHoleWidth=").append(squareHoleWidth);
        sb.append(", squareHoleTopy=").append(squareHoleTopy);
        sb.append(", squareHoleTopx=").append(squareHoleTopx);
        sb.append(", lengthHoleDiameterTop=").append(lengthHoleDiameterTop);
        sb.append(", trueRoundness2=").append(trueRoundness2);
        sb.append(", lengthHoleDiameterLower=").append(lengthHoleDiameterLower);
        sb.append(", trueRoundness3=").append(trueRoundness3);
        sb.append(", lengthHoleLength=").append(lengthHoleLength);
        sb.append(", longHoleWidth=").append(longHoleWidth);
        sb.append(", longHoleTopLongHoleLowery=").append(longHoleTopLongHoleLowery);
        sb.append(", longHoleCenterTopy=").append(longHoleCenterTopy);
        sb.append(", longHoleCenterTopx=").append(longHoleCenterTopx);
        sb.append(", smallCircleDiameter=").append(smallCircleDiameter);
        sb.append(", trueRoundness4=").append(trueRoundness4);
        sb.append(", smallHoleCenterSquareHolex=").append(smallHoleCenterSquareHolex);
        sb.append(", smallHoleCenterTopy=").append(smallHoleCenterTopy);
        sb.append(", qrCodeWidth=").append(qrCodeWidth);
        sb.append(", qrCodeHigh=").append(qrCodeHigh);
        sb.append(", qrCodeMaterialCenterx=").append(qrCodeMaterialCenterx);
        sb.append(", qrCodeMaterialCentery=").append(qrCodeMaterialCentery);
        sb.append(", createTime=").append(createTime);
        sb.append(", uploadName=").append(uploadName);
        sb.append(", batchId=").append(batchId);
        sb.append("]");
        return sb.toString();
    }
}