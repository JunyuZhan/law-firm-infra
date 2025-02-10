package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件标签枚举
 */
@Getter
public enum CaseTagEnum implements BaseEnum<String> {
    
    // 案件特征标签
    PRECEDENT("典型案例", TagGroup.FEATURE, true),
    COMPLEX("复杂案件", TagGroup.FEATURE, true),
    GROUP_ACTION("群体性案件", TagGroup.FEATURE, true),
    SENSITIVE("敏感案件", TagGroup.FEATURE, true),
    MAJOR_IMPACT("重大影响", TagGroup.FEATURE, true),
    RESEARCH_VALUE("研究价值", TagGroup.FEATURE, false),
    
    // 处理状态标签
    URGENT_HANDLE("紧急处理", TagGroup.STATUS, true),
    NEEDS_ATTENTION("需要关注", TagGroup.STATUS, true),
    DELAYED("进度延误", TagGroup.STATUS, true),
    DIFFICULT("疑难问题", TagGroup.STATUS, true),
    ON_HOLD("暂时搁置", TagGroup.STATUS, false),
    
    // 客户关系标签
    VIP_CLIENT("重要客户", TagGroup.CLIENT, true),
    REPEAT_CLIENT("老客户", TagGroup.CLIENT, false),
    STRATEGIC_CLIENT("战略客户", TagGroup.CLIENT, true),
    GOVERNMENT_CLIENT("政府客户", TagGroup.CLIENT, true),
    
    // 业务发展标签
    NEW_FIELD("新领域案件", TagGroup.BUSINESS, false),
    BENCHMARK("标杆案件", TagGroup.BUSINESS, true),
    HIGH_PROFIT("高收益", TagGroup.BUSINESS, false),
    BUSINESS_OPPORTUNITY("商机相关", TagGroup.BUSINESS, false),
    
    // 风险控制标签
    HIGH_RISK("高风险", TagGroup.RISK, true),
    CONFLICT_POTENTIAL("潜在冲突", TagGroup.RISK, true),
    COMPLIANCE_ISSUE("合规问题", TagGroup.RISK, true),
    REPUTATION_RISK("声誉风险", TagGroup.RISK, true),
    
    // 团队管理标签
    TEAM_COLLABORATION("团队协作", TagGroup.TEAM, false),
    TRAINING_CASE("培训案例", TagGroup.TEAM, false),
    RESOURCE_INTENSIVE("资源密集", TagGroup.TEAM, true),
    EXPERT_REQUIRED("需要专家", TagGroup.TEAM, true);

    private final String description;
    private final TagGroup tagGroup;
    private final boolean needsSpecialAttention;  // 是否需要特别关注

    CaseTagEnum(String description, TagGroup tagGroup, boolean needsSpecialAttention) {
        this.description = description;
        this.tagGroup = tagGroup;
        this.needsSpecialAttention = needsSpecialAttention;
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
     * 获取标签分组
     * @return 标签分组
     */
    public TagGroup getTagGroup() {
        return this.tagGroup;
    }

    /**
     * 判断是否需要特别关注
     * @return 是否需要特别关注
     */
    public boolean needsSpecialAttention() {
        return this.needsSpecialAttention;
    }

    /**
     * 判断是否为案件特征标签
     * @return 是否案件特征标签
     */
    public boolean isFeatureTag() {
        return this.tagGroup == TagGroup.FEATURE;
    }

    /**
     * 判断是否为状态标签
     * @return 是否状态标签
     */
    public boolean isStatusTag() {
        return this.tagGroup == TagGroup.STATUS;
    }

    /**
     * 判断是否为客户关系标签
     * @return 是否客户关系标签
     */
    public boolean isClientTag() {
        return this.tagGroup == TagGroup.CLIENT;
    }

    /**
     * 判断是否为业务发展标签
     * @return 是否业务发展标签
     */
    public boolean isBusinessTag() {
        return this.tagGroup == TagGroup.BUSINESS;
    }

    /**
     * 判断是否为风险控制标签
     * @return 是否风险控制标签
     */
    public boolean isRiskTag() {
        return this.tagGroup == TagGroup.RISK;
    }

    /**
     * 判断是否为团队管理标签
     * @return 是否团队管理标签
     */
    public boolean isTeamTag() {
        return this.tagGroup == TagGroup.TEAM;
    }

    /**
     * 获取建议的案件优先级
     * @return 建议的优先级
     */
    public CasePriorityEnum getSuggestedPriority() {
        if (this == URGENT_HANDLE) {
            return CasePriorityEnum.URGENT;
        }
        if (this == VIP_CLIENT || this == STRATEGIC_CLIENT || this == HIGH_RISK) {
            return CasePriorityEnum.HIGH;
        }
        if (this == NEEDS_ATTENTION || this == DIFFICULT) {
            return CasePriorityEnum.MEDIUM;
        }
        return CasePriorityEnum.NORMAL;
    }

    /**
     * 获取标签显示文本
     * @return 完整的标签显示文本
     */
    public String getTagDisplay() {
        if (this.needsSpecialAttention) {
            return this.description + "[!]";
        }
        return this.description;
    }

    /**
     * 标签分组
     */
    public enum TagGroup {
        FEATURE("案件特征"),
        STATUS("处理状态"),
        CLIENT("客户关系"),
        BUSINESS("业务发展"),
        RISK("风险控制"),
        TEAM("团队管理");

        private final String description;

        TagGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 