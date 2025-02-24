package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件办理方式枚举
 */
public enum CaseHandleTypeEnum implements BaseEnum<Integer> {

    /**
     * 全程代理
     */
    FULL_REPRESENTATION(1, "全程代理"),

    /**
     * 专项代理
     */
    SPECIAL_REPRESENTATION(2, "专项代理"),

    /**
     * 阶段代理
     */
    STAGE_REPRESENTATION(3, "阶段代理"),

    /**
     * 法律顾问
     */
    LEGAL_COUNSEL(4, "法律顾问"),

    /**
     * 法律咨询
     */
    LEGAL_CONSULTATION(5, "法律咨询"),

    /**
     * 文书代写
     */
    DOCUMENT_WRITING(6, "文书代写"),

    /**
     * 案件指导
     */
    CASE_GUIDANCE(7, "案件指导");

    private final Integer value;
    private final String description;

    CaseHandleTypeEnum(Integer value, String description) {
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
    public static CaseHandleTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseHandleTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否需要委托合同
     */
    public boolean needPowerOfAttorney() {
        return this == FULL_REPRESENTATION || this == SPECIAL_REPRESENTATION || 
               this == STAGE_REPRESENTATION;
    }

    /**
     * 是否需要详细工作计划
     */
    public boolean needDetailedPlan() {
        return this == FULL_REPRESENTATION || this == SPECIAL_REPRESENTATION || 
               this == STAGE_REPRESENTATION || this == LEGAL_COUNSEL;
    }

    /**
     * 是否需要出庭
     */
    public boolean needCourtAppearance() {
        return this == FULL_REPRESENTATION || this == STAGE_REPRESENTATION;
    }

    /**
     * 是否需要团队协作
     */
    public boolean needTeamwork() {
        return this == FULL_REPRESENTATION || this == SPECIAL_REPRESENTATION;
    }

    /**
     * 获取建议工作时长（小时）
     */
    public int getSuggestedWorkHours() {
        switch (this) {
            case FULL_REPRESENTATION:
                return 100;
            case SPECIAL_REPRESENTATION:
                return 50;
            case STAGE_REPRESENTATION:
                return 30;
            case LEGAL_COUNSEL:
                return 20;
            case LEGAL_CONSULTATION:
                return 2;
            case DOCUMENT_WRITING:
                return 8;
            case CASE_GUIDANCE:
                return 5;
            default:
                return 0;
        }
    }
} 