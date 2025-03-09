package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 中心类型枚举
 * 用于描述组织中各类功能中心
 */
public enum CenterTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 人力资源中心
     */
    HR(1, "人力资源中心"),

    /**
     * 财务中心
     */
    FINANCE(2, "财务中心"),

    /**
     * 行政中心
     */
    ADMIN(3, "行政中心"),

    /**
     * IT中心
     */
    IT(4, "IT中心"),
    
    /**
     * 其他中心
     */
    OTHER(5, "其他中心");

    private final Integer value;
    private final String description;

    CenterTypeEnum(Integer value, String description) {
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

    public static CenterTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CenterTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 