package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件标签枚举
 */
public enum CaseTagEnum implements BaseEnum<Integer> {

    /**
     * 重大案件
     */
    MAJOR_CASE(1, "重大案件"),

    /**
     * 敏感案件
     */
    SENSITIVE_CASE(2, "敏感案件"),

    /**
     * 群体性案件
     */
    GROUP_CASE(3, "群体性案件"),

    /**
     * 典型案例
     */
    TYPICAL_CASE(4, "典型案例"),

    /**
     * 疑难案件
     */
    DIFFICULT_CASE(5, "疑难案件"),

    /**
     * 跨境案件
     */
    CROSS_BORDER_CASE(6, "跨境案件"),

    /**
     * 示范案件
     */
    MODEL_CASE(7, "示范案件"),

    /**
     * 专业案件
     */
    PROFESSIONAL_CASE(8, "专业案件"),

    /**
     * 公益案件
     */
    PUBLIC_INTEREST_CASE(9, "公益案件"),

    /**
     * 创新案件
     */
    INNOVATIVE_CASE(10, "创新案件");

    private final Integer value;
    private final String description;

    CaseTagEnum(Integer value, String description) {
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
    public static CaseTagEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseTagEnum tag : values()) {
            if (tag.value.equals(value)) {
                return tag;
            }
        }
        return null;
    }

    /**
     * 是否需要特别关注
     */
    public boolean needSpecialAttention() {
        return this == MAJOR_CASE || this == SENSITIVE_CASE || 
               this == GROUP_CASE;
    }

    /**
     * 是否需要专家咨询
     */
    public boolean needExpertConsultation() {
        return this == DIFFICULT_CASE || this == PROFESSIONAL_CASE || 
               this == INNOVATIVE_CASE;
    }

    /**
     * 是否需要风险评估
     */
    public boolean needRiskAssessment() {
        return this == SENSITIVE_CASE || this == GROUP_CASE || 
               this == CROSS_BORDER_CASE;
    }

    /**
     * 是否需要媒体关注
     */
    public boolean needMediaAttention() {
        return this == MAJOR_CASE || this == TYPICAL_CASE || 
               this == PUBLIC_INTEREST_CASE;
    }

    /**
     * 是否需要定期报告
     */
    public boolean needRegularReport() {
        return this == MAJOR_CASE || this == SENSITIVE_CASE || 
               this == GROUP_CASE || this == MODEL_CASE;
    }

    /**
     * 获取建议汇报周期（天）
     */
    public int getSuggestedReportCycle() {
        switch (this) {
            case MAJOR_CASE:
            case SENSITIVE_CASE:
                return 7;
            case GROUP_CASE:
            case MODEL_CASE:
                return 15;
            case DIFFICULT_CASE:
            case CROSS_BORDER_CASE:
                return 30;
            default:
                return 0;
        }
    }

    /**
     * 是否需要知识库归档
     */
    public boolean needKnowledgeArchive() {
        return this == TYPICAL_CASE || this == MODEL_CASE || 
               this == PROFESSIONAL_CASE || this == INNOVATIVE_CASE;
    }

    /**
     * 是否需要团队会议
     */
    public boolean needTeamMeeting() {
        return this == MAJOR_CASE || this == DIFFICULT_CASE || 
               this == CROSS_BORDER_CASE || this == INNOVATIVE_CASE;
    }

    /**
     * 获取建议会议周期（天）
     */
    public int getSuggestedMeetingCycle() {
        switch (this) {
            case MAJOR_CASE:
            case DIFFICULT_CASE:
                return 7;
            case CROSS_BORDER_CASE:
            case INNOVATIVE_CASE:
                return 15;
            default:
                return 0;
        }
    }
} 