package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 律所类型枚举
 */
public enum FirmTypeEnum implements BaseEnum<Integer> {
    /**
     * 合伙制律所
     */
    PARTNERSHIP(1, "合伙制律所"),

    /**
     * 个人所
     */
    INDIVIDUAL(2, "个人所"),

    /**
     * 公司制律所
     */
    CORPORATION(3, "公司制律所");

    private final Integer value;
    private final String description;

    FirmTypeEnum(Integer value, String description) {
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

    public static FirmTypeEnum valueOf(Integer value) {
        for (FirmTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 