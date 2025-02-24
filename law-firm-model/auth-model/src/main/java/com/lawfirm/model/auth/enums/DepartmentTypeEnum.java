package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 部门类型枚举
 */
@Getter
public enum DepartmentTypeEnum {
    
    /**
     * 公司
     */
    COMPANY(0, "公司"),
    
    /**
     * 部门
     */
    DEPARTMENT(1, "部门"),
    
    /**
     * 团队
     */
    TEAM(2, "团队");
    
    private final Integer code;
    private final String desc;
    
    DepartmentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 