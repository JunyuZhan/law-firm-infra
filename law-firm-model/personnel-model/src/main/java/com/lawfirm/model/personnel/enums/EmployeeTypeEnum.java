package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.EmployeeConstant;

/**
 * 员工类型枚举
 */
public enum EmployeeTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 全职
     */
    FULL_TIME(EmployeeConstant.Type.FULL_TIME, "全职"),

    /**
     * 兼职
     */
    PART_TIME(EmployeeConstant.Type.PART_TIME, "兼职"),

    /**
     * 实习
     */
    INTERN(EmployeeConstant.Type.INTERN, "实习"),

    /**
     * 外包
     */
    OUTSOURCED(EmployeeConstant.Type.OUTSOURCED, "外包");

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