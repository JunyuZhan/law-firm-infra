package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件类型枚举
 */
public enum CaseTypeEnum implements BaseEnum<Integer> {
    /**
     * 民事案件
     */
    CIVIL(0, "民事案件"),

    /**
     * 刑事案件
     */
    CRIMINAL(1, "刑事案件"),

    /**
     * 行政案件
     */
    ADMINISTRATIVE(2, "行政案件"),

    /**
     * 非诉讼案件
     */
    NON_LITIGATION(3, "非诉讼案件"),

    /**
     * 仲裁案件
     */
    ARBITRATION(4, "仲裁案件"),

    /**
     * 执行案件
     */
    ENFORCEMENT(5, "执行案件");

    private final Integer value;
    private final String description;

    CaseTypeEnum(Integer value, String description) {
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

    public static CaseTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 