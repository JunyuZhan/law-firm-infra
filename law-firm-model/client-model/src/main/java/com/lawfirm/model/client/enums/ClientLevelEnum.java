package com.lawfirm.model.client.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ClientLevelEnum implements BaseEnum<String> {
    
    VIP("VIP", "VIP客户"),
    PREMIUM("PREMIUM", "高级客户"),
    REGULAR("REGULAR", "普通客户"),
    BASIC("BASIC", "基础客户");

    private final String value;
    private final String description;

    ClientLevelEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 