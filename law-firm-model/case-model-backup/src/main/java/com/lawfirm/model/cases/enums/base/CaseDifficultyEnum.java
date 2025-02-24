package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件难度枚举
 */
public enum CaseDifficultyEnum implements BaseEnum<Integer> {
    /**
     * 简单
     */
    SIMPLE(0, "简单"),

    /**
     * 普通
     */
    NORMAL(1, "普通"),

    /**
     * 较难
     */
    DIFFICULT(2, "较难"),

    /**
     * 复杂
     */
    COMPLEX(3, "复杂"),

    /**
     * 疑难
     */
    COMPLICATED(4, "疑难");

    private final Integer value;
    private final String description;

    CaseDifficultyEnum(Integer value, String description) {
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

    public static CaseDifficultyEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseDifficultyEnum difficulty : values()) {
            if (difficulty.value.equals(value)) {
                return difficulty;
            }
        }
        return null;
    }
} 