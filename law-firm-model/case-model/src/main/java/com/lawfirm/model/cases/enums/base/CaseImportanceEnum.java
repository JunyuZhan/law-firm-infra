package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件重要性枚举
 */
public enum CaseImportanceEnum implements BaseEnum<Integer> {

    /**
     * 特别重要
     */
    CRITICAL(1, "特别重要"),

    /**
     * 重要
     */
    IMPORTANT(2, "重要"),

    /**
     * 一般重要
     */
    MODERATE(3, "一般重要"),

    /**
     * 普通
     */
    NORMAL(4, "普通"),

    /**
     * 较不重要
     */
    LESS_IMPORTANT(5, "较不重要");

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

    /**
     * 根据值获取枚举
     */
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

    /**
     * 是否是高重要性
     */
    public boolean isHighImportance() {
        return this == CRITICAL || this == IMPORTANT;
    }

    /**
     * 是否是低重要性
     */
    public boolean isLowImportance() {
        return this == LESS_IMPORTANT;
    }

    /**
     * 获取重要性等级（数字越小重要性越高）
     */
    public int getLevel() {
        return value;
    }

    /**
     * 比较重要性
     * @return 大于0表示当前重要性更低，小于0表示当前重要性更高，等于0表示相同
     */
    public int compareByImportance(CaseImportanceEnum other) {
        if (other == null) {
            return -1;
        }
        return this.value.compareTo(other.value);
    }

    /**
     * 是否需要合伙人审核
     */
    public boolean needPartnerReview() {
        return this == CRITICAL;
    }

    /**
     * 是否需要定期汇报
     */
    public boolean needRegularReport() {
        return this == CRITICAL || this == IMPORTANT;
    }

    /**
     * 获取建议汇报周期（天）
     */
    public int getSuggestedReportCycle() {
        switch (this) {
            case CRITICAL:
                return 3;
            case IMPORTANT:
                return 7;
            case MODERATE:
                return 15;
            case NORMAL:
                return 30;
            case LESS_IMPORTANT:
                return 45;
            default:
                return 30;
        }
    }

    /**
     * 获取建议审核级别
     * @return 0-无需审核 1-普通审核 2-严格审核
     */
    public int getSuggestedReviewLevel() {
        switch (this) {
            case CRITICAL:
                return 2;
            case IMPORTANT:
                return 1;
            default:
                return 0;
        }
    }
} 