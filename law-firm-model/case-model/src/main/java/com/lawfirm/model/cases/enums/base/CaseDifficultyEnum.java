package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件难度枚举
 * 
 * 注意：此枚举是系统中表示案件难易程度的唯一标准。
 * 难度主要考虑案件处理的技术难度、法律问题复杂程度、证据收集难度等综合因素。
 * 
 * 此枚举整合了原有的案件难度和复杂度的概念，避免概念重复和维护困难。
 * 原 CaseComplexityEnum 已被移除，所有相关代码应使用此枚举。
 */
public enum CaseDifficultyEnum implements BaseEnum<Integer> {

    /**
     * 特别困难
     * 案件涉及复杂法律问题，需要高级专业知识，证据收集极其困难，可能涉及多个法律领域
     */
    EXTREMELY_DIFFICULT(1, "特别困难"),

    /**
     * 困难
     * 案件涉及较复杂法律问题，需要专业知识，证据收集困难
     */
    DIFFICULT(2, "困难"),

    /**
     * 中等难度
     * 案件涉及常见法律问题，需要一般专业知识，证据收集难度适中
     */
    MODERATE(3, "中等难度"),

    /**
     * 容易
     * 案件涉及简单法律问题，证据明确，处理流程清晰
     */
    EASY(4, "容易"),

    /**
     * 非常容易
     * 案件涉及基础法律问题，证据充分，处理流程标准化
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
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 根据数值获取枚举实例
     *
     * @param value 枚举数值
     * @return 对应的枚举实例，如果未找到则返回null
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
     * 判断是否为高难度案件
     *
     * @return 如果是高难度案件（特别困难或困难）返回true，否则返回false
     */
    public boolean isHighDifficulty() {
        return this == EXTREMELY_DIFFICULT || this == DIFFICULT;
    }

    /**
     * 判断是否为低难度案件
     *
     * @return 如果是低难度案件（容易或非常容易）返回true，否则返回false
     */
    public boolean isLowDifficulty() {
        return this == EASY || this == VERY_EASY;
    }

    /**
     * 获取难度级别（数值越小难度越高）
     *
     * @return 难度级别
     */
    public int getLevel() {
        return this.value;
    }

    /**
     * 比较难度
     *
     * @param other 另一个难度枚举
     * @return 正数表示当前难度低于other，负数表示当前难度高于other，0表示相等
     */
    public int compareByDifficulty(CaseDifficultyEnum other) {
        if (other == null) {
            return -1; // 如果other为null，认为当前难度更高
        }
        return this.value - other.value;
    }

    /**
     * 获取建议的团队规模
     *
     * @return 建议的团队人数
     */
    public int getSuggestedTeamSize() {
        switch (this) {
            case EXTREMELY_DIFFICULT:
                return 5;
            case DIFFICULT:
                return 3;
            case MODERATE:
                return 2;
            case EASY:
            case VERY_EASY:
                return 1;
            default:
                return 1;
        }
    }

    /**
     * 获取建议的审核级别
     *
     * @return 建议的审核级别（1-最高级别，5-最低级别）
     */
    public int getSuggestedReviewLevel() {
        return this.value;
    }
    
    /**
     * 获取建议的处理时间（天）
     *
     * @return 建议的处理时间
     */
    public int getSuggestedProcessingDays() {
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
     * 是否需要高级律师处理
     *
     * @return 如果需要高级律师处理返回true，否则返回false
     */
    public boolean needSeniorLawyer() {
        return this == EXTREMELY_DIFFICULT;
    }
    
    /**
     * 获取建议的评审方式
     * 
     * @return 建议的评审方式描述
     */
    public String getSuggestedReviewMethod() {
        switch (this) {
            case EXTREMELY_DIFFICULT:
                return "部门主管和高级律师共同评审";
            case DIFFICULT:
                return "高级律师评审";
            case MODERATE:
                return "团队负责人评审";
            case EASY:
            case VERY_EASY:
                return "简易评审";
            default:
                return "团队负责人评审";
        }
    }
    
    /**
     * 获取建议的收费系数
     * 
     * @return 收费系数（相对于标准收费的倍数）
     */
    public double getSuggestedFeeCoefficient() {
        switch (this) {
            case EXTREMELY_DIFFICULT:
                return 1.5;
            case DIFFICULT:
                return 1.2;
            case MODERATE:
                return 1.0;
            case EASY:
                return 0.8;
            case VERY_EASY:
                return 0.6;
            default:
                return 1.0;
        }
    }
} 