package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件复杂度枚举
 */
public enum CaseComplexityEnum implements BaseEnum<Integer> {

    /**
     * 极其复杂
     */
    EXTREMELY_COMPLEX(1, "极其复杂"),

    /**
     * 复杂
     */
    COMPLEX(2, "复杂"),

    /**
     * 中等复杂度
     */
    MODERATE(3, "中等复杂度"),

    /**
     * 简单
     */
    SIMPLE(4, "简单"),

    /**
     * 非常简单
     */
    VERY_SIMPLE(5, "非常简单");

    private final Integer value;
    private final String description;

    CaseComplexityEnum(Integer value, String description) {
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

    /**
     * 根据值获取枚举
     */
    public static CaseComplexityEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseComplexityEnum complexity : values()) {
            if (complexity.value.equals(value)) {
                return complexity;
            }
        }
        return null;
    }

    /**
     * 是否是高复杂度
     */
    public boolean isHighComplexity() {
        return this == EXTREMELY_COMPLEX || this == COMPLEX;
    }

    /**
     * 是否是低复杂度
     */
    public boolean isLowComplexity() {
        return this == SIMPLE || this == VERY_SIMPLE;
    }

    /**
     * 获取复杂度等级（数字越小复杂度越高）
     */
    public int getLevel() {
        return value;
    }

    /**
     * 比较复杂度
     * @return 大于0表示当前复杂度更低，小于0表示当前复杂度更高，等于0表示相同
     */
    public int compareByComplexity(CaseComplexityEnum other) {
        if (other == null) {
            return -1;
        }
        return this.value.compareTo(other.value);
    }

    /**
     * 是否需要高级律师处理
     */
    public boolean needSeniorLawyer() {
        return this == EXTREMELY_COMPLEX;
    }

    /**
     * 获取建议团队规模
     */
    public int getSuggestedTeamSize() {
        switch (this) {
            case EXTREMELY_COMPLEX:
                return 5;
            case COMPLEX:
                return 4;
            case MODERATE:
                return 3;
            case SIMPLE:
                return 2;
            case VERY_SIMPLE:
                return 1;
            default:
                return 2;
        }
    }

    /**
     * 获取建议评审级别
     * @return 0-无需评审 1-普通评审 2-严格评审
     */
    public int getSuggestedReviewLevel() {
        switch (this) {
            case EXTREMELY_COMPLEX:
            case COMPLEX:
                return 2;
            case MODERATE:
                return 1;
            case SIMPLE:
            case VERY_SIMPLE:
                return 0;
            default:
                return 1;
        }
    }
} 