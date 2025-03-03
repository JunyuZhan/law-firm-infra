package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 职能类型枚举
 */
public enum FunctionTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 人事
     */
    HR(1, "人事"),

    /**
     * 财务
     */
    FINANCE(2, "财务"),

    /**
     * 行政
     */
    ADMIN(3, "行政"),

    /**
     * IT
     */
    IT(4, "IT"),

    /**
     * 其他
     */
    OTHER(5, "其他");

    private final Integer value;
    private final String description;

    FunctionTypeEnum(Integer value, String description) {
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

    public static FunctionTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (FunctionTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 