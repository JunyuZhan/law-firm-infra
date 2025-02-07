package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 部门类型枚举
 */
@Getter
public enum DepartmentTypeEnum implements BaseEnum<String> {
    
    FUNCTIONAL("FUNCTIONAL", "职能部门"),
    BUSINESS("BUSINESS", "业务部门"),
    SUPPORT("SUPPORT", "支持部门"),
    PROJECT("PROJECT", "项目部门"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    DepartmentTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 