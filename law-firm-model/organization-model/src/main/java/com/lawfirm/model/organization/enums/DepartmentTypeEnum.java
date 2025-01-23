package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum DepartmentTypeEnum implements BaseEnum<String> {
    
    MANAGEMENT("MANAGEMENT", "管理部门"),
    BUSINESS("BUSINESS", "业务部门"),
    SUPPORT("SUPPORT", "支持部门"),
    FINANCE("FINANCE", "财务部门"),
    HR("HR", "人力资源部门"),
    IT("IT", "信息技术部门"),
    LEGAL("LEGAL", "法务部门"),
    MARKETING("MARKETING", "市场部门"),
    ADMIN("ADMIN", "行政部门");

    private final String value;
    private final String description;

    DepartmentTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 