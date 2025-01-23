package com.lawfirm.model.base.enums;

import lombok.Getter;

@Getter
public enum StatusEnum implements BaseEnum<String> {
    
    ENABLED("ENABLED", "启用"),
    DISABLED("DISABLED", "禁用"),
    DELETED("DELETED", "已删除"),
    LOCKED("LOCKED", "已锁定"),
    EXPIRED("EXPIRED", "已过期");

    private final String value;
    private final String description;

    StatusEnum(String value, String description) {
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