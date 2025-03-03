package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.EmployeeConstant;

/**
 * 员工状态枚举
 */
public enum EmployeeStatusEnum implements BaseEnum<Integer> {
    
    /**
     * 试用期
     */
    PROBATION(EmployeeConstant.Status.PROBATION, "试用期"),

    /**
     * 正式
     */
    REGULAR(EmployeeConstant.Status.REGULAR, "正式");

    private final Integer value;
    private final String description;

    EmployeeStatusEnum(Integer value, String description) {
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

    public static EmployeeStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (EmployeeStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
} 