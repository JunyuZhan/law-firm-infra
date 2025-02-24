package com.lawfirm.model.base.enums;

/**
 * 状态枚举
 */
public enum StatusEnum implements BaseEnum<Integer> {
    /**
     * 启用
     */
    ENABLED(0, "启用"),

    /**
     * 禁用
     */
    DISABLED(1, "禁用");

    private final Integer value;
    private final String description;

    StatusEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static StatusEnum valueOf(Integer value) {
        for (StatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
} 