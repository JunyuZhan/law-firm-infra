package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.PersonnelConstants;

/**
 * 员工状态枚举
 */
public enum EmployeeStatusEnum implements BaseEnum<Integer> {
    
    /**
     * 试用期
     */
    PROBATION(PersonnelConstants.Status.PROBATION, "试用期"),

    /**
     * 正式
     */
    REGULAR(PersonnelConstants.Status.ON_JOB, "正式"),
    
    /**
     * 离职
     */
    RESIGNED(PersonnelConstants.Status.RESIGNED, "离职"),
    
    /**
     * 休假
     */
    ON_LEAVE(PersonnelConstants.Status.ON_LEAVE, "休假"),
    
    /**
     * 停职
     */
    SUSPENDED(PersonnelConstants.Status.SUSPENDED, "停职");

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
    
    /**
     * 获取状态编码
     */
    public Integer getCode() {
        return this.value;
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