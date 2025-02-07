package com.lawfirm.model.client.enums;

import lombok.Getter;

/**
 * 客户状态枚举
 */
@Getter
public enum ClientStatusEnum {

    ENABLED("ENABLED", "启用"),
    DISABLED("DISABLED", "禁用");

    private final String value;
    private final String description;

    ClientStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 