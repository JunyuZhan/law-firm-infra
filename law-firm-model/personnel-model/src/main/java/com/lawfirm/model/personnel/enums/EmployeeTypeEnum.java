package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 员工类型枚举
 */
public enum EmployeeTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 律师
     */
    LAWYER(1, "律师"),

    /**
     * 行政人员
     */
    STAFF(2, "行政人员"),
    
    /**
     * 实习生
     */
    INTERN(3, "实习生"),
    
    /**
     * 顾问
     */
    CONSULTANT(4, "顾问"),
    
    /**
     * 其他
     */
    OTHER(99, "其他");

    private final Integer value;
    private final String description;

    EmployeeTypeEnum(Integer value, String description) {
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

    public static EmployeeTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (EmployeeTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 