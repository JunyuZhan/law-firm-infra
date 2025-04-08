package com.lawfirm.personnel.constant;

/**
 * 人事模块常量定义
 */
public final class PersonnelConstants {
    
    private PersonnelConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * API路径前缀
     */
    public static final String API_PREFIX = "/api/v1/personnel";
    
    /**
     * 员工API路径前缀
     */
    public static final String API_EMPLOYEE_PREFIX = "/api/v1/employees";
    
    /**
     * 组织API路径前缀
     */
    public static final String API_ORGANIZATION_PREFIX = "/api/v1/organizations";
    
    /**
     * 职位API路径前缀
     */
    public static final String API_POSITION_PREFIX = "/api/v1/positions";
} 