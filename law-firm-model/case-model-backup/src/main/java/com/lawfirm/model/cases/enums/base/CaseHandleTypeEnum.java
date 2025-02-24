package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件办理方式枚举
 */
public enum CaseHandleTypeEnum implements BaseEnum<Integer> {
    /**
     * 独立办理
     */
    INDEPENDENT(0, "独立办理"),

    /**
     * 团队办理
     */
    TEAM(1, "团队办理"),

    /**
     * 协作办理
     */
    COLLABORATION(2, "协作办理"),

    /**
     * 指导办理
     */
    GUIDANCE(3, "指导办理"),

    /**
     * 督办
     */
    SUPERVISION(4, "督办");

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
} 