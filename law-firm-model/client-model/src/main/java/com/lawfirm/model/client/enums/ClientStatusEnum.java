package com.lawfirm.model.client.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ClientStatusEnum implements BaseEnum<String> {
    
    ACTIVE("ACTIVE", "活跃"),
    INACTIVE("INACTIVE", "不活跃"),
    POTENTIAL("POTENTIAL", "潜在"),
    LOST("LOST", "流失"),
    BLACKLIST("BLACKLIST", "黑名单");

    private final String value;
    private final String description;

    ClientStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 