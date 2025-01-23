package com.lawfirm.model.system.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ConfigStatusEnum implements BaseEnum<String> {
    
    ENABLED("ENABLED", "启用"),
    DISABLED("DISABLED", "禁用"),
    DEPRECATED("DEPRECATED", "已废弃"),
    TESTING("TESTING", "测试中");

    private final String value;
    private final String description;

    ConfigStatusEnum(String value, String description) {
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