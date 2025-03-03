package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.LawyerConstant;

/**
 * 律师职级枚举
 */
public enum LawyerLevelEnum implements BaseEnum<Integer> {
    
    /**
     * 实习律师
     */
    INTERN(LawyerConstant.Level.INTERN, "实习律师"),

    /**
     * 初级律师
     */
    JUNIOR(LawyerConstant.Level.JUNIOR, "初级律师"),

    /**
     * 中级律师
     */
    MIDDLE(LawyerConstant.Level.MIDDLE, "中级律师"),

    /**
     * 高级律师
     */
    SENIOR(LawyerConstant.Level.SENIOR, "高级律师"),

    /**
     * 资深律师
     */
    EXPERT(LawyerConstant.Level.EXPERT, "资深律师"),

    /**
     * 合伙人
     */
    PARTNER(LawyerConstant.Level.PARTNER, "合伙人");

    private final Integer value;
    private final String description;

    LawyerLevelEnum(Integer value, String description) {
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

    public static LawyerLevelEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (LawyerLevelEnum level : values()) {
            if (level.value.equals(value)) {
                return level;
            }
        }
        return null;
    }
} 