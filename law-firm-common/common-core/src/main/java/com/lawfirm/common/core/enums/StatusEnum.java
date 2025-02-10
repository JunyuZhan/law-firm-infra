package com.lawfirm.common.core.enums;

import lombok.Getter;

/**
 * 通用状态枚举
 */
@Getter
public enum StatusEnum {
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
} 