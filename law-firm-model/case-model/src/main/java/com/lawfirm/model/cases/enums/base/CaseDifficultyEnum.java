package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件难度枚举
 */
public enum CaseDifficultyEnum implements BaseEnum<Integer> {

    /**
     * 特别困难
     */
    EXTREMELY_DIFFICULT(1, "特别困难"),

    /**
     * 困难
     */
    DIFFICULT(2, "困难"),

    /**
     * 中等难度
     */
    MODERATE(3, "中等难度"),

    /**
     * 容易
     */
    EASY(4, "容易"),

    /**
     * 非常容易
     */
    VERY_EASY(5, "非常容易");

    private final Integer value;
    private final String description;

    CaseDifficultyEnum(Integer value, String description) {
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
    public static CaseDifficultyEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseDifficultyEnum difficulty : values()) {
            if (difficulty.value.equals(value)) {
                return difficulty;
            }
        }
        return null;
    }

    /**
     * 是否是高难度
     */
    public boolean isHighDifficulty() {
        return this == EXTREMELY_DIFFICULT || this == DIFFICULT;
    }

    /**
     * 是否是低难度
     */
    public boolean isLowDifficulty() {
        return this == EASY || this == VERY_EASY;
    }

    /**
     * 获取难度等级（数字越小难度越高）
     */
    public int getLevel() {
        return value;
    }

    /**
     * 比较难度
     * @return 大于0表示当前难度更低，小于0表示当前难度更高，等于0表示相同
     */
    public int compareByDifficulty(CaseDifficultyEnum other) {
        if (other == null) {
            return -1;
        }
        return this.value.compareTo(other.value);
    }

    /**
     * 是否需要资深律师
     */
    public boolean needSeniorLawyer() {
        return this == EXTREMELY_DIFFICULT;
    }

    /**
     * 获取建议团队规模
     */
    public int getSuggestedTeamSize() {
        switch (this) {
            case EXTREMELY_DIFFICULT:
                return 5;
            case DIFFICULT:
                return 4;
            case MODERATE:
                return 3;
            case EASY:
                return 2;
            case VERY_EASY:
                return 1;
            default:
                return 2;
        }
    }

    /**
     * 获取建议准备时间（天）
     */
    public int getSuggestedPreparationDays() {
        switch (this) {
            case EXTREMELY_DIFFICULT:
                return 30;
            case DIFFICULT:
                return 20;
            case MODERATE:
                return 15;
            case EASY:
                return 10;
            case VERY_EASY:
                return 5;
            default:
                return 15;
        }
    }

    /**
     * 获取建议评审级别
     * @return 0-无需评审 1-普通评审 2-严格评审
     */
    public int getSuggestedReviewLevel() {
        switch (this) {
            case EXTREMELY_DIFFICULT:
            case DIFFICULT:
                return 2;
            case MODERATE:
                return 1;
            case EASY:
            case VERY_EASY:
                return 0;
            default:
                return 1;
        }
    }
} 