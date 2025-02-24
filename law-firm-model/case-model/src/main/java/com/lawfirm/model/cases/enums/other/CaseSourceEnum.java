package com.lawfirm.model.cases.enums.other;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件来源枚举
 */
@Getter
public enum CaseSourceEnum implements BaseEnum<Integer> {

    /**
     * 客户直接委托
     */
    DIRECT_CLIENT(1, "客户直接委托"),

    /**
     * 法院指定
     */
    COURT_APPOINTED(2, "法院指定"),

    /**
     * 律协指派
     */
    BAR_ASSOCIATION_ASSIGNED(3, "律协指派"),

    /**
     * 同行推荐
     */
    PEER_REFERRAL(4, "同行推荐"),

    /**
     * 老客户介绍
     */
    CLIENT_REFERRAL(5, "老客户介绍"),

    /**
     * 政府部门推荐
     */
    GOVERNMENT_REFERRAL(6, "政府部门推荐"),

    /**
     * 网络咨询
     */
    ONLINE_CONSULTATION(7, "网络咨询"),

    /**
     * 电话咨询
     */
    PHONE_CONSULTATION(8, "电话咨询"),

    /**
     * 法律援助
     */
    LEGAL_AID(9, "法律援助"),

    /**
     * 其他来源
     */
    OTHER(99, "其他来源");

    private final Integer value;
    private final String description;

    CaseSourceEnum(Integer value, String description) {
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
    public static CaseSourceEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseSourceEnum source : values()) {
            if (source.value.equals(value)) {
                return source;
            }
        }
        return null;
    }

    /**
     * 是否需要特殊处理
     */
    public boolean needSpecialHandling() {
        return this == COURT_APPOINTED || this == LEGAL_AID || 
               this == BAR_ASSOCIATION_ASSIGNED;
    }

    /**
     * 是否需要回访
     */
    public boolean needFollowUp() {
        return this == DIRECT_CLIENT || this == PEER_REFERRAL || 
               this == CLIENT_REFERRAL || this == ONLINE_CONSULTATION || 
               this == PHONE_CONSULTATION;
    }

    /**
     * 是否需要确认委托
     */
    public boolean needEngagementConfirmation() {
        return this == ONLINE_CONSULTATION || this == PHONE_CONSULTATION;
    }

    /**
     * 是否需要费用减免评估
     */
    public boolean needFeeReductionAssessment() {
        return this == LEGAL_AID || this == COURT_APPOINTED;
    }

    /**
     * 是否需要报告处理进度
     */
    public boolean needProgressReport() {
        return this == COURT_APPOINTED || this == BAR_ASSOCIATION_ASSIGNED || 
               this == GOVERNMENT_REFERRAL || this == LEGAL_AID;
    }

    /**
     * 获取建议回访时间（天）
     */
    public int getSuggestedFollowUpDays() {
        switch (this) {
            case DIRECT_CLIENT:
            case PEER_REFERRAL:
            case CLIENT_REFERRAL:
                return 7;
            case ONLINE_CONSULTATION:
            case PHONE_CONSULTATION:
                return 3;
            case GOVERNMENT_REFERRAL:
                return 5;
            default:
                return 0;
        }
    }

    /**
     * 获取建议响应时间（小时）
     */
    public int getSuggestedResponseHours() {
        switch (this) {
            case COURT_APPOINTED:
            case LEGAL_AID:
                return 24;
            case ONLINE_CONSULTATION:
            case PHONE_CONSULTATION:
                return 4;
            case DIRECT_CLIENT:
            case PEER_REFERRAL:
            case CLIENT_REFERRAL:
                return 8;
            default:
                return 48;
        }
    }

    /**
     * 是否需要特殊资质
     */
    public boolean needSpecialQualification() {
        return this == COURT_APPOINTED || this == LEGAL_AID;
    }
} 