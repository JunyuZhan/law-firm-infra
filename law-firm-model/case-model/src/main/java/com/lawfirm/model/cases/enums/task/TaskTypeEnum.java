package com.lawfirm.model.cases.enums.task;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 任务类型枚举
 */
@Getter
public enum TaskTypeEnum implements BaseEnum<Integer> {

    /**
     * 文书起草
     */
    DOCUMENT_DRAFTING(1, "文书起草"),

    /**
     * 文书审核
     */
    DOCUMENT_REVIEW(2, "文书审核"),

    /**
     * 证据收集
     */
    EVIDENCE_COLLECTION(3, "证据收集"),

    /**
     * 证据整理
     */
    EVIDENCE_ORGANIZATION(4, "证据整理"),

    /**
     * 法律研究
     */
    LEGAL_RESEARCH(5, "法律研究"),

    /**
     * 案情分析
     */
    CASE_ANALYSIS(6, "案情分析"),

    /**
     * 开庭准备
     */
    HEARING_PREPARATION(7, "开庭准备"),

    /**
     * 客户沟通
     */
    CLIENT_COMMUNICATION(8, "客户沟通"),

    /**
     * 材料送达
     */
    DOCUMENT_SERVICE(9, "材料送达"),

    /**
     * 费用收取
     */
    FEE_COLLECTION(10, "费用收取"),

    /**
     * 档案整理
     */
    FILE_ORGANIZATION(11, "档案整理"),

    /**
     * 进度汇报
     */
    PROGRESS_REPORT(12, "进度汇报"),

    /**
     * 现场调查
     */
    SITE_INVESTIGATION(13, "现场调查"),

    /**
     * 谈判准备
     */
    NEGOTIATION_PREPARATION(14, "谈判准备"),

    /**
     * 团队协调
     */
    TEAM_COORDINATION(15, "团队协调"),

    /**
     * 其他任务
     */
    OTHER(99, "其他任务");

    private final Integer value;
    private final String description;

    TaskTypeEnum(Integer value, String description) {
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
    public static TaskTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (TaskTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否需要审核
     */
    public boolean needReview() {
        return this == DOCUMENT_DRAFTING || this == EVIDENCE_ORGANIZATION || 
               this == CASE_ANALYSIS || this == LEGAL_RESEARCH;
    }

    /**
     * 是否需要客户确认
     */
    public boolean needClientConfirmation() {
        return this == FEE_COLLECTION || this == CLIENT_COMMUNICATION;
    }

    /**
     * 是否需要团队协作
     */
    public boolean needTeamwork() {
        return this == HEARING_PREPARATION || this == SITE_INVESTIGATION || 
               this == NEGOTIATION_PREPARATION || this == TEAM_COORDINATION;
    }

    /**
     * 是否需要文档记录
     */
    public boolean needDocumentation() {
        return this != OTHER && this != TEAM_COORDINATION;
    }

    /**
     * 是否需要定期跟进
     */
    public boolean needRegularFollowUp() {
        return this == CLIENT_COMMUNICATION || this == FEE_COLLECTION || 
               this == PROGRESS_REPORT;
    }

    /**
     * 获取建议完成时间（小时）
     */
    public int getSuggestedCompletionHours() {
        switch (this) {
            case DOCUMENT_DRAFTING:
            case LEGAL_RESEARCH:
            case CASE_ANALYSIS:
                return 24;
            case EVIDENCE_COLLECTION:
            case EVIDENCE_ORGANIZATION:
            case HEARING_PREPARATION:
                return 48;
            case DOCUMENT_REVIEW:
            case CLIENT_COMMUNICATION:
            case PROGRESS_REPORT:
                return 8;
            case DOCUMENT_SERVICE:
            case FEE_COLLECTION:
            case FILE_ORGANIZATION:
                return 4;
            default:
                return 16;
        }
    }

    /**
     * 获取建议参与人数
     */
    public int getSuggestedParticipants() {
        switch (this) {
            case HEARING_PREPARATION:
            case SITE_INVESTIGATION:
            case NEGOTIATION_PREPARATION:
                return 3;
            case EVIDENCE_COLLECTION:
            case TEAM_COORDINATION:
                return 2;
            default:
                return 1;
        }
    }
} 