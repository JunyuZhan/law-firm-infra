package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 部门类型枚举
 */
@Getter
public enum DepartmentTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 管理部门
     */
    MANAGEMENT(0, "管理部门"),
    
    /**
     * 业务部门
     */
    BUSINESS(1, "业务部门"),
    
    /**
     * 诉讼部门
     */
    LITIGATION(2, "诉讼部门"),
    
    /**
     * 非诉讼部门
     */
    NON_LITIGATION(3, "非诉讼部门"),
    
    /**
     * 行政部门
     */
    ADMINISTRATIVE(4, "行政部门"),
    
    /**
     * 财务部门
     */
    FINANCE(5, "财务部门"),
    
    /**
     * 人力资源部门
     */
    HR(6, "人力资源部门"),
    
    /**
     * IT部门
     */
    IT(7, "IT部门"),
    
    /**
     * 分所
     */
    BRANCH(8, "分所");

    private final Integer code;
    private final String desc;
    
    DepartmentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }
} 