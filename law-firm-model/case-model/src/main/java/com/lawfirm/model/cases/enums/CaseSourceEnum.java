package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件来源枚举
 */
@Getter
public enum CaseSourceEnum implements BaseEnum<String> {
    
    DIRECT_CLIENT("直接委托", SourceGroup.DIRECT),
    REFERRAL_LAWYER("律师推荐", SourceGroup.REFERRAL),
    REFERRAL_CLIENT("客户推荐", SourceGroup.REFERRAL),
    COURT_ASSIGNED("法院指派", SourceGroup.ASSIGNED),
    LEGAL_AID("法律援助", SourceGroup.ASSIGNED),
    GOVERNMENT_ASSIGNED("政府指派", SourceGroup.ASSIGNED),
    ONLINE_CONSULTATION("在线咨询", SourceGroup.ONLINE),
    MARKETING("市场营销", SourceGroup.MARKETING),
    REPEAT_CLIENT("老客户", SourceGroup.DIRECT),
    PARTNER_REFERRAL("合作伙伴推荐", SourceGroup.REFERRAL),
    MEDIA_PUBLICITY("媒体宣传", SourceGroup.MARKETING),
    SEMINAR("研讨会", SourceGroup.MARKETING),
    WEBSITE("网站", SourceGroup.ONLINE),
    SOCIAL_MEDIA("社交媒体", SourceGroup.ONLINE),
    OTHER("其他来源", SourceGroup.OTHER);

    private final String description;
    private final SourceGroup sourceGroup;

    CaseSourceEnum(String description, SourceGroup sourceGroup) {
        this.description = description;
        this.sourceGroup = sourceGroup;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
    
    /**
     * 获取来源分组
     * @return 来源分组
     */
    public SourceGroup getSourceGroup() {
        return this.sourceGroup;
    }
    
    /**
     * 判断是否为推荐来源
     * @return 是否推荐来源
     */
    public boolean isReferral() {
        return this.sourceGroup == SourceGroup.REFERRAL;
    }
    
    /**
     * 判断是否为指派来源
     * @return 是否指派来源
     */
    public boolean isAssigned() {
        return this.sourceGroup == SourceGroup.ASSIGNED;
    }
    
    /**
     * 判断是否为营销来源
     * @return 是否营销来源
     */
    public boolean isMarketing() {
        return this.sourceGroup == SourceGroup.MARKETING;
    }

    /**
     * 判断是否为直接来源
     * @return 是否直接来源
     */
    public boolean isDirect() {
        return this.sourceGroup == SourceGroup.DIRECT;
    }

    /**
     * 判断是否为在线来源
     * @return 是否在线来源
     */
    public boolean isOnline() {
        return this.sourceGroup == SourceGroup.ONLINE;
    }

    /**
     * 判断是否需要支付推荐费
     * @return 是否需要支付推荐费
     */
    public boolean needsReferralFee() {
        return this == REFERRAL_LAWYER || this == PARTNER_REFERRAL;
    }

    /**
     * 判断是否为付费获取的案源
     * @return 是否付费获取
     */
    public boolean isPaidSource() {
        return this.sourceGroup == SourceGroup.MARKETING || needsReferralFee();
    }

    /**
     * 获取建议的案件优先级
     * @return 建议的优先级
     */
    public CasePriorityEnum getSuggestedPriority() {
        if (this.sourceGroup == SourceGroup.ASSIGNED) {
            return CasePriorityEnum.HIGH;
        }
        if (this == REPEAT_CLIENT) {
            return CasePriorityEnum.HIGH;
        }
        return CasePriorityEnum.NORMAL;
    }

    /**
     * 案件来源分组
     */
    public enum SourceGroup {
        DIRECT("直接来源"),
        REFERRAL("推荐来源"),
        ASSIGNED("指派来源"),
        MARKETING("营销来源"),
        ONLINE("在线来源"),
        OTHER("其他来源");

        private final String description;

        SourceGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 