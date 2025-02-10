package com.lawfirm.model.client.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ClientSourceEnum implements BaseEnum<String> {
    
    REFERRAL("REFERRAL", "转介绍"),
    ONLINE("ONLINE", "线上获客"),
    OFFLINE("OFFLINE", "线下获客"),
    PARTNER("PARTNER", "合作伙伴"),
    MARKETING("MARKETING", "市场营销"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    ClientSourceEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 