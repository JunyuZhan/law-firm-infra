package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 部门类型枚举
 */
public enum DepartmentTypeEnum implements BaseEnum<Integer> {
    /**
     * 业务部门
     */
    BUSINESS(1, "业务部门"),

    /**
     * 行政部门
     */
    ADMINISTRATIVE(2, "行政部门"),

    /**
     * 财务部门
     */
    FINANCE(3, "财务部门"),

    /**
     * 人事部门
     */
    HR(4, "人事部门"),

    /**
     * IT部门
     */
    IT(5, "IT部门"),
    
    /**
     * 主任办公室
     */
    DIRECTOR_OFFICE(6, "主任办公室");

    private final Integer value;
    private final String description;

    DepartmentTypeEnum(Integer value, String description) {
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

    public static DepartmentTypeEnum valueOf(Integer value) {
        for (DepartmentTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 