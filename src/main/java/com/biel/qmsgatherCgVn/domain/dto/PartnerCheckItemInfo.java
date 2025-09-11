package com.biel.qmsgatherCgVn.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PartnerCheckItemInfo {

    @JsonProperty("CheckValue")
    private Double checkValue;

    @JsonProperty("ItemName")
    private String itemName;

    public PartnerCheckItemInfo(String itemName, Double checkValue) {
        this.itemName = itemName;
        this.checkValue = checkValue;
    }
}