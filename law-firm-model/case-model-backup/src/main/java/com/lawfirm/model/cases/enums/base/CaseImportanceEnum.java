package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件重要性枚举
 */
public enum CaseImportanceEnum implements BaseEnum<Integer> {
    /**
     * 普通
     */
    NORMAL(0, "普通"),

    /**
     * 重要
     */
    IMPORTANT(1, "重要"),

    /**
     * 非常重要
     */
    VERY_IMPORTANT(2, "非常重要"),

    /**
     * 特别重要
     */
    CRITICAL(3, "特别重要");

    private final Integer value;
    private final String description;

    CaseImportanceEnum(Integer value, String description) {
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

    public static CaseImportanceEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseImportanceEnum importance : values()) {
            if (importance.value.equals(value)) {
                return importance;
            }
        }
        return null;
    }
} 