package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件进展枚举
 */
public enum CaseProgressEnum implements BaseEnum<Integer> {
    /**
     * 立案准备
     */
    PREPARATION(0, "立案准备"),

    /**
     * 立案审查
     */
    FILING_REVIEW(1, "立案审查"),

    /**
     * 已立案
     */
    FILED(2, "已立案"),

    /**
     * 庭前准备
     */
    PRE_TRIAL(3, "庭前准备"),

    /**
     * 庭审中
     */
    COURT_HEARING(4, "庭审中"),

    /**
     * 调解中
     */
    MEDIATION(5, "调解中"),

    /**
     * 判决阶段
     */
    JUDGMENT(6, "判决阶段"),

    /**
     * 执行阶段
     */
    ENFORCEMENT(7, "执行阶段"),

    /**
     * 结案
     */
    CLOSED(8, "结案");

    private final Integer value;
    private final String description;

    CaseProgressEnum(Integer value, String description) {
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

    public static CaseProgressEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseProgressEnum progress : values()) {
            if (progress.value.equals(value)) {
                return progress;
            }
        }
        return null;
    }
} 