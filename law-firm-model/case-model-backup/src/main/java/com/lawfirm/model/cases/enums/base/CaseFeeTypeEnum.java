package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件收费类型枚举
 */
public enum CaseFeeTypeEnum implements BaseEnum<Integer> {
    /**
     * 固定收费
     */
    FIXED(0, "固定收费"),

    /**
     * 计时收费
     */
    HOURLY(1, "计时收费"),

    /**
     * 风险收费
     */
    CONTINGENCY(2, "风险收费"),

    /**
     * 混合收费
     */
    HYBRID(3, "混合收费"),

    /**
     * 免费
     */
    FREE(4, "免费");

    private final Integer value;
    private final String description;

    CaseFeeTypeEnum(Integer value, String description) {
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

    public static CaseFeeTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseFeeTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 