package com.biel.qmsgatherCgVn.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PartnerCheckMessage {

    @JsonProperty("CheckDevCode")
    private String checkDevCode; // 按要求置为 null

    @JsonProperty("CheckItemInfos")
    private List<PartnerCheckItemInfo> checkItemInfos;

    @JsonProperty("CheckTime")
    private String checkTime;    // yyyy-MM-dd HH:mm:ss

    @JsonProperty("CheckType")
    private String checkType;    // 映射 remark

    @JsonProperty("ItemName")
    private String itemName;     // 映射 testProject

    @JsonProperty("MachineCode")
    private String machineCode;  // 映射 machineCode

    @JsonProperty("ProcessNO")
    private String processNO;    // 映射 process
}