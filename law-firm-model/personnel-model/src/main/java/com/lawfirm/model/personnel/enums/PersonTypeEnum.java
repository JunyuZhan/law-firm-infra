package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.PersonnelConstants;

/**
 * 人员类型枚举
 */
public enum PersonTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 律师
     */
    LAWYER(1, "律师"),

    /**
     * 行政人员
     */
    STAFF(2, "行政人员");

    private final Integer value;
    private final String description;

    PersonTypeEnum(Integer value, String description) {
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

    public static PersonTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (PersonTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 