package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.PersonnelConstants;

/**
 * 律师职级枚举
 */
public enum LawyerLevelEnum implements BaseEnum<Integer> {
    
    /**
     * 实习律师
     */
    INTERN(PersonnelConstants.LawyerLevel.INTERN, "实习律师"),

    /**
     * 初级律师
     */
    JUNIOR(PersonnelConstants.LawyerLevel.JUNIOR, "初级律师"),

    /**
     * 中级律师
     */
    MIDDLE(PersonnelConstants.LawyerLevel.MIDDLE, "中级律师"),

    /**
     * 高级律师
     */
    SENIOR(PersonnelConstants.LawyerLevel.SENIOR, "高级律师"),

    /**
     * 资深律师
     */
    EXPERT(PersonnelConstants.LawyerLevel.EXPERT, "资深律师"),

    /**
     * 合伙人
     */
    PARTNER(PersonnelConstants.LawyerLevel.PARTNER, "合伙人");

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