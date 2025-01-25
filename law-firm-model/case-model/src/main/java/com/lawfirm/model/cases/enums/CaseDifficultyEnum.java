package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件难度等级枚举
 */
@Getter
public enum CaseDifficultyEnum implements BaseEnum<String>, Comparable<CaseDifficultyEnum> {
    
    VERY_HARD("特别复杂", 100, 2.0),
    HARD("复杂", 80, 1.5),
    MEDIUM("中等", 60, 1.2),
    EASY("简单", 40, 1.0),
    VERY_EASY("非常简单", 20, 0.8);

    private final String description;
    private final Integer score;
    private final Double coefficient;

    CaseDifficultyEnum(String description, Integer score, Double coefficient) {
        this.description = description;
        this.score = score;
        this.coefficient = coefficient;
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
     * 获取难度分值
     * @return 难度对应的分值
     */
    public Integer getScore() {
        return this.score;
    }

    /**
     * 获取难度系数
     * @return 难度系数
     */
    public Double getCoefficient() {
        return this.coefficient;
    }

    /**
     * 根据分值获取难度等级
     * @param score 分值
     * @return 难度等级
     */
    public static CaseDifficultyEnum getByScore(Integer score) {
        if (score >= 90) return VERY_HARD;
        if (score >= 70) return HARD;
        if (score >= 50) return MEDIUM;
        if (score >= 30) return EASY;
        return VERY_EASY;
    }

    /**
     * 计算难度加权费用
     * @param baseFee 基础费用
     * @return 加权后的费用
     */
    public Double calculateWeightedFee(Double baseFee) {
        return baseFee * this.coefficient;
    }

    /**
     * 比较难度等级
     * @param other 其他难度等级
     * @return 比较结果
     */
    @Override
    public int compareTo(CaseDifficultyEnum other) {
        return this.score.compareTo(other.score);
    }

    /**
     * 判断是否高于指定难度
     * @param other 其他难度等级
     * @return 是否更高难度
     */
    public boolean isHarderThan(CaseDifficultyEnum other) {
        return this.score > other.score;
    }

    /**
     * 判断是否为高难度案件
     * @return 是否高难度
     */
    public boolean isHighDifficulty() {
        return this == VERY_HARD || this == HARD;
    }
} 