package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件重要程度枚举
 */
@Getter
public enum CaseImportanceEnum implements BaseEnum<String> {
    
    CRITICAL("重大案件", 5, true),
    SIGNIFICANT("重要案件", 4, true),
    MAJOR("较大案件", 3, false),
    NORMAL("普通案件", 2, false),
    MINOR("小案件", 1, false);

    private final String description;
    private final Integer level;
    private final boolean specialAttention;

    CaseImportanceEnum(String description, Integer level, boolean specialAttention) {
        this.description = description;
        this.level = level;
        this.specialAttention = specialAttention;
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
     * 获取重要程度等级
     * @return 重要程度对应的等级
     */
    public Integer getLevel() {
        return this.level;
    }
    
    /**
     * 判断是否为重要案件
     * @return 是否重要
     */
    public boolean isImportant() {
        return this.level >= 4;
    }
    
    /**
     * 判断是否为普通案件
     * @return 是否普通
     */
    public boolean isNormal() {
        return this.level <= 2;
    }

    /**
     * 判断是否需要特别关注
     * @return 是否需要特别关注
     */
    public boolean needsSpecialAttention() {
        return this.specialAttention;
    }

    /**
     * 获取建议的案件优先级
     * @return 建议的优先级
     */
    public CasePriorityEnum getSuggestedPriority() {
        switch (this) {
            case CRITICAL:
                return CasePriorityEnum.URGENT;
            case SIGNIFICANT:
                return CasePriorityEnum.HIGH;
            case MAJOR:
                return CasePriorityEnum.MEDIUM;
            case NORMAL:
                return CasePriorityEnum.NORMAL;
            case MINOR:
                return CasePriorityEnum.LOW;
            default:
                return CasePriorityEnum.NORMAL;
        }
    }

    /**
     * 比较重要程度
     * @param other 其他重要程度
     * @return 比较结果
     */
    public int compareByLevel(CaseImportanceEnum other) {
        return this.level.compareTo(other.level);
    }

    /**
     * 判断是否比指定重要程度更高
     * @param other 其他重要程度
     * @return 是否更重要
     */
    public boolean isMoreImportantThan(CaseImportanceEnum other) {
        return this.level > other.level;
    }

    /**
     * 获取重要程度描述
     * @return 完整的重要程度描述
     */
    public String getFullDescription() {
        if (needsSpecialAttention()) {
            return this.description + "(需特别关注)";
        }
        return this.description;
    }
} 