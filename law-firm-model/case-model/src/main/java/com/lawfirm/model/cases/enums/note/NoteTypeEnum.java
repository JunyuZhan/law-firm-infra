package com.lawfirm.model.cases.enums.note;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 笔记类型枚举
 */
@Getter
public enum NoteTypeEnum implements BaseEnum<Integer> {

    /**
     * 一般笔记
     */
    GENERAL(1, "一般笔记"),

    /**
     * 会议记录
     */
    MEETING_MINUTES(2, "会议记录"),

    /**
     * 客户沟通记录
     */
    CLIENT_COMMUNICATION(3, "客户沟通记录"),

    /**
     * 法院记录
     */
    COURT_RECORD(4, "法院记录"),

    /**
     * 证据分析
     */
    EVIDENCE_ANALYSIS(5, "证据分析"),

    /**
     * 法律研究
     */
    LEGAL_RESEARCH(6, "法律研究"),

    /**
     * 案件策略
     */
    CASE_STRATEGY(7, "案件策略"),

    /**
     * 谈判记录
     */
    NEGOTIATION_RECORD(8, "谈判记录"),

    /**
     * 调解记录
     */
    MEDIATION_RECORD(9, "调解记录"),

    /**
     * 仲裁记录
     */
    ARBITRATION_RECORD(10, "仲裁记录"),

    /**
     * 费用记录
     */
    EXPENSE_RECORD(11, "费用记录"),

    /**
     * 时间记录
     */
    TIME_RECORD(12, "时间记录"),

    /**
     * 其他
     */
    OTHER(99, "其他");

    private final Integer value;
    private final String description;

    NoteTypeEnum(Integer value, String description) {
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
    public static NoteTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (NoteTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否是会议相关记录
     */
    public boolean isMeetingRelated() {
        return this == MEETING_MINUTES;
    }

    /**
     * 是否是客户相关记录
     */
    public boolean isClientRelated() {
        return this == CLIENT_COMMUNICATION;
    }

    /**
     * 是否是法院相关记录
     */
    public boolean isCourtRelated() {
        return this == COURT_RECORD;
    }

    /**
     * 是否是证据相关记录
     */
    public boolean isEvidenceRelated() {
        return this == EVIDENCE_ANALYSIS;
    }

    /**
     * 是否是策略相关记录
     */
    public boolean isStrategyRelated() {
        return this == CASE_STRATEGY || this == LEGAL_RESEARCH;
    }

    /**
     * 是否是解决争议相关记录
     */
    public boolean isDisputeResolutionRelated() {
        return this == NEGOTIATION_RECORD || this == MEDIATION_RECORD || this == ARBITRATION_RECORD;
    }

    /**
     * 是否是费用相关记录
     */
    public boolean isExpenseRelated() {
        return this == EXPENSE_RECORD;
    }

    /**
     * 是否是时间相关记录
     */
    public boolean isTimeRelated() {
        return this == TIME_RECORD;
    }

    /**
     * 获取建议的可见性级别
     */
    public int getSuggestedVisibilityValue() {
        switch (this) {
            case CLIENT_COMMUNICATION:
            case COURT_RECORD:
            case NEGOTIATION_RECORD:
            case MEDIATION_RECORD:
            case ARBITRATION_RECORD:
                return 2; // TEAM
            case CASE_STRATEGY:
            case LEGAL_RESEARCH:
            case EVIDENCE_ANALYSIS:
                return 4; // SPECIFIC_USERS
            case EXPENSE_RECORD:
            case TIME_RECORD:
                return 1; // PRIVATE
            default:
                return 2; // TEAM
        }
    }
} 