package com.lawfirm.model.client.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ClientLevelEnum implements BaseEnum<String> {
    
    NORMAL("NORMAL", "普通客户"),
    VIP("VIP", "VIP客户"),
    SVIP("SVIP", "SVIP客户"),
    STRATEGIC("STRATEGIC", "战略客户");

    private final String value;
    private final String description;

    ClientLevelEnum(String value, String description) {
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