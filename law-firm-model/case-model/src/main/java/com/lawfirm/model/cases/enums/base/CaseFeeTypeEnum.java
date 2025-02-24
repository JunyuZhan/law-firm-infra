package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件收费类型枚举
 */
public enum CaseFeeTypeEnum implements BaseEnum<Integer> {

    /**
     * 固定收费
     */
    FIXED(1, "固定收费"),

    /**
     * 计时收费
     */
    TIME_BASED(2, "计时收费"),

    /**
     * 风险收费
     */
    CONTINGENCY(3, "风险收费"),

    /**
     * 混合收费
     */
    HYBRID(4, "混合收费"),

    /**
     * 阶段收费
     */
    STAGE_BASED(5, "阶段收费"),

    /**
     * 年度收费
     */
    ANNUAL(6, "年度收费"),

    /**
     * 免费
     */
    FREE(7, "免费");

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

    /**
     * 根据值获取枚举
     */
    public static CaseFeeTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseFeeTypeEnum feeType : values()) {
            if (feeType.value.equals(value)) {
                return feeType;
            }
        }
        return null;
    }

    /**
     * 是否需要合同
     */
    public boolean needContract() {
        return this != FREE;
    }

    /**
     * 是否需要时间记录
     */
    public boolean needTimeTracking() {
        return this == TIME_BASED || this == HYBRID;
    }

    /**
     * 是否需要结果记录
     */
    public boolean needResultTracking() {
        return this == CONTINGENCY || this == HYBRID;
    }

    /**
     * 是否需要阶段记录
     */
    public boolean needStageTracking() {
        return this == STAGE_BASED;
    }

    /**
     * 是否需要标的额
     */
    public boolean needClaimAmount() {
        return this == CONTINGENCY || this == ANNUAL;
    }

    /**
     * 是否需要预付款
     */
    public boolean needAdvancePayment() {
        return this == FIXED || this == TIME_BASED || this == HYBRID || 
               this == STAGE_BASED || this == ANNUAL;
    }

    /**
     * 获取建议预付款比例（百分比）
     */
    public int getSuggestedAdvancePaymentPercentage() {
        switch (this) {
            case FIXED:
                return 50;
            case TIME_BASED:
                return 30;
            case HYBRID:
                return 40;
            case STAGE_BASED:
                return 30;
            case ANNUAL:
                return 20;
            default:
                return 0;
        }
    }

    /**
     * 获取建议收费周期（天）
     */
    public int getSuggestedBillingCycle() {
        switch (this) {
            case TIME_BASED:
                return 30;
            case STAGE_BASED:
                return 90;
            case HYBRID:
                return 45;
            case ANNUAL:
                return 365;
            default:
                return 0;
        }
    }

    /**
     * 是否允许调整收费
     */
    public boolean allowFeeAdjustment() {
        return this == TIME_BASED || this == HYBRID || this == STAGE_BASED || this == ANNUAL;
    }

    /**
     * 是否需要工作量确认
     */
    public boolean needWorkloadConfirmation() {
        return this == TIME_BASED || this == HYBRID;
    }
} 