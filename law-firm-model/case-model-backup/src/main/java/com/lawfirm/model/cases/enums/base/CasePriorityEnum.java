package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件优先级枚举
 */
public enum CasePriorityEnum implements BaseEnum<Integer> {
    /**
     * 低
     */
    LOW(0, "低"),

    /**
     * 普通
     */
    NORMAL(1, "普通"),

    /**
     * 高
     */
    HIGH(2, "高"),

    /**
     * 紧急
     */
    URGENT(3, "紧急"),

    /**
     * 特急
     */
    CRITICAL(4, "特急");

    private final Integer value;
    private final String description;

    CasePriorityEnum(Integer value, String description) {
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

    public static CasePriorityEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CasePriorityEnum priority : values()) {
            if (priority.value.equals(value)) {
                return priority;
            }
        }
        return null;
    }
} 