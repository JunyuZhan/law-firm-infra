package com.lawfirm.model.cases.enums.team;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 团队成员角色枚举
 */
@Getter
public enum TeamMemberRoleEnum implements BaseEnum<Integer> {

    /**
     * 主办律师
     */
    LEAD_LAWYER(1, "主办律师"),

    /**
     * 协办律师
     */
    ASSOCIATE_LAWYER(2, "协办律师"),

    /**
     * 实习律师
     */
    TRAINEE_LAWYER(3, "实习律师"),

    /**
     * 律师助理
     */
    LEGAL_ASSISTANT(4, "律师助理"),

    /**
     * 合伙人督办
     */
    SUPERVISING_PARTNER(5, "合伙人督办"),

    /**
     * 专家顾问
     */
    EXPERT_CONSULTANT(6, "专家顾问"),

    /**
     * 文书助理
     */
    DOCUMENT_ASSISTANT(7, "文书助理"),

    /**
     * 行政助理
     */
    ADMIN_ASSISTANT(8, "行政助理"),

    /**
     * 翻译
     */
    TRANSLATOR(9, "翻译"),

    /**
     * 调查员
     */
    INVESTIGATOR(10, "调查员");

    private final Integer value;
    private final String description;

    TeamMemberRoleEnum(Integer value, String description) {
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
    public static TeamMemberRoleEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (TeamMemberRoleEnum role : values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        return null;
    }

    /**
     * 是否是律师角色
     */
    public boolean isLawyer() {
        return this == LEAD_LAWYER || this == ASSOCIATE_LAWYER || 
               this == TRAINEE_LAWYER;
    }

    /**
     * 是否是主要角色
     */
    public boolean isPrimaryRole() {
        return this == LEAD_LAWYER || this == SUPERVISING_PARTNER;
    }

    /**
     * 是否可以分配任务
     */
    public boolean canAssignTasks() {
        return this == LEAD_LAWYER || this == SUPERVISING_PARTNER || 
               this == ASSOCIATE_LAWYER;
    }

    /**
     * 是否可以审核文件
     */
    public boolean canReviewDocuments() {
        return this == LEAD_LAWYER || this == SUPERVISING_PARTNER || 
               this == ASSOCIATE_LAWYER;
    }

    /**
     * 是否需要监督
     */
    public boolean needSupervision() {
        return this == TRAINEE_LAWYER || this == LEGAL_ASSISTANT || 
               this == DOCUMENT_ASSISTANT;
    }

    /**
     * 是否可以独立办案
     */
    public boolean canHandleCaseIndependently() {
        return this == LEAD_LAWYER || this == ASSOCIATE_LAWYER;
    }

    /**
     * 是否可以接触敏感信息
     */
    public boolean canAccessSensitiveInfo() {
        return this == LEAD_LAWYER || this == SUPERVISING_PARTNER || 
               this == ASSOCIATE_LAWYER;
    }

    /**
     * 是否可以与客户直接沟通
     */
    public boolean canCommunicateWithClient() {
        return this == LEAD_LAWYER || this == SUPERVISING_PARTNER || 
               this == ASSOCIATE_LAWYER;
    }

    /**
     * 获取建议工作量上限（每周小时）
     */
    public int getSuggestedMaxWorkHoursPerWeek() {
        switch (this) {
            case LEAD_LAWYER:
            case ASSOCIATE_LAWYER:
                return 45;
            case TRAINEE_LAWYER:
            case LEGAL_ASSISTANT:
                return 40;
            case SUPERVISING_PARTNER:
                return 20;
            case EXPERT_CONSULTANT:
                return 15;
            default:
                return 40;
        }
    }

    /**
     * 获取建议同时处理的案件数量上限
     */
    public int getSuggestedMaxConcurrentCases() {
        switch (this) {
            case LEAD_LAWYER:
                return 8;
            case ASSOCIATE_LAWYER:
                return 6;
            case TRAINEE_LAWYER:
                return 3;
            case LEGAL_ASSISTANT:
                return 10;
            case SUPERVISING_PARTNER:
                return 15;
            default:
                return 5;
        }
    }
} 