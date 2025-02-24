package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件结果枚举
 */
public enum CaseResultEnum implements BaseEnum<Integer> {

    /**
     * 胜诉
     */
    WIN(1, "胜诉"),

    /**
     * 部分胜诉
     */
    PARTIAL_WIN(2, "部分胜诉"),

    /**
     * 败诉
     */
    LOSE(3, "败诉"),

    /**
     * 调解结案
     */
    MEDIATION(4, "调解结案"),

    /**
     * 和解结案
     */
    SETTLEMENT(5, "和解结案"),

    /**
     * 撤诉
     */
    WITHDRAWAL(6, "撤诉"),

    /**
     * 驳回起诉
     */
    DISMISS(7, "驳回起诉"),

    /**
     * 终止审理
     */
    TERMINATE(8, "终止审理"),

    /**
     * 移送管辖
     */
    TRANSFER_JURISDICTION(9, "移送管辖"),

    /**
     * 其他结果
     */
    OTHER(10, "其他结果");

    private final Integer value;
    private final String description;

    CaseResultEnum(Integer value, String description) {
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
    public static CaseResultEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseResultEnum result : values()) {
            if (result.value.equals(value)) {
                return result;
            }
        }
        return null;
    }

    /**
     * 是否是积极结果
     */
    public boolean isPositiveResult() {
        return this == WIN || this == PARTIAL_WIN || this == MEDIATION || 
               this == SETTLEMENT;
    }

    /**
     * 是否是消极结果
     */
    public boolean isNegativeResult() {
        return this == LOSE || this == DISMISS;
    }

    /**
     * 是否是中立结果
     */
    public boolean isNeutralResult() {
        return this == WITHDRAWAL || this == TERMINATE || 
               this == TRANSFER_JURISDICTION || this == OTHER;
    }

    /**
     * 是否需要上诉分析
     */
    public boolean needAppealAnalysis() {
        return this == LOSE || this == PARTIAL_WIN || this == DISMISS;
    }

    /**
     * 是否需要执行准备
     */
    public boolean needExecutionPreparation() {
        return this == WIN || this == PARTIAL_WIN || this == MEDIATION || 
               this == SETTLEMENT;
    }

    /**
     * 是否需要结案报告
     */
    public boolean needClosingReport() {
        return this != TRANSFER_JURISDICTION;
    }

    /**
     * 是否需要费用结算
     */
    public boolean needFeeSettlement() {
        return this != TRANSFER_JURISDICTION && this != TERMINATE;
    }

    /**
     * 获取建议总结时间（天）
     */
    public int getSuggestedSummaryDays() {
        switch (this) {
            case WIN:
            case LOSE:
                return 7;
            case PARTIAL_WIN:
                return 10;
            case MEDIATION:
            case SETTLEMENT:
                return 5;
            case WITHDRAWAL:
            case DISMISS:
                return 3;
            case TERMINATE:
            case TRANSFER_JURISDICTION:
                return 2;
            default:
                return 5;
        }
    }

    /**
     * 是否需要客户回访
     */
    public boolean needClientFollowUp() {
        return this == WIN || this == PARTIAL_WIN || this == LOSE || 
               this == MEDIATION || this == SETTLEMENT;
    }
} 