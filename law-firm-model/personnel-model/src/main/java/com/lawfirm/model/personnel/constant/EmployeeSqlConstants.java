package com.lawfirm.model.personnel.constant;

/**
 * 员工模块SQL常量类
 * 集中管理员工相关SQL查询语句，提高可维护性
 */
public class EmployeeSqlConstants {

    /**
     * 根据用户名（工号）查询员工
     */
    public static final String SELECT_BY_USERNAME = 
            "SELECT * FROM employee WHERE work_number = #{username}";
    
    /**
     * 根据手机号查询员工
     */
    public static final String SELECT_BY_PHONE = 
            "SELECT * FROM employee WHERE mobile = #{phone}";
    
    /**
     * 根据律师执业证号查询员工
     */
    public static final String SELECT_BY_LICENSE_NUMBER = 
            "SELECT * FROM employee WHERE license_number = #{licenseNumber} AND employee_type = 1";
    
    /**
     * 根据部门ID和员工类型查询
     */
    public static final String SELECT_BY_DEPT_AND_TYPE = 
            "SELECT * FROM employee WHERE department_id = #{departmentId} AND employee_type = #{employeeType.value}";
    
    /**
     * 根据职位ID和员工类型查询
     */
    public static final String SELECT_BY_POSITION_AND_TYPE = 
            "SELECT * FROM employee WHERE position_id = #{positionId} AND employee_type = #{employeeType.value}";
            
    /**
     * 根据工号查询员工
     */
    public static final String SELECT_BY_WORK_NUMBER = 
            "SELECT * FROM employee WHERE work_number = #{workNumber}";
            
    /**
     * 根据邮箱查询员工
     */
    public static final String SELECT_BY_EMAIL = 
            "SELECT * FROM employee WHERE email = #{email}";
            
    /**
     * 根据专业领域查询律师列表
     */
    public static final String SELECT_BY_PRACTICE_AREA = 
            "SELECT * FROM employee WHERE practice_areas LIKE CONCAT('%', #{practiceArea}, '%') AND employee_type = 1";
            
    /**
     * 根据员工类型查询员工列表
     */
    public static final String SELECT_BY_EMPLOYEE_TYPE = 
            "SELECT * FROM employee WHERE employee_type = #{employeeType.value}";
            
    /**
     * 根据律师职级查询律师列表
     */
    public static final String SELECT_BY_LAWYER_LEVEL = 
            "SELECT * FROM employee WHERE lawyer_level = #{lawyerLevel} AND employee_type = 1";
            
    /**
     * 根据行政职能类型查询行政人员列表
     */
    public static final String SELECT_BY_FUNCTION_TYPE = 
            "SELECT * FROM employee WHERE function_type = #{functionType} AND employee_type = 2";
            
    /**
     * 人事合同相关SQL常量
     */
    public static class Contract {
        /**
         * 根据员工ID查询合同
         */
        public static final String SELECT_BY_EMPLOYEE_ID = 
                "SELECT * FROM personnel_contract WHERE employee_id = #{employeeId} ORDER BY sign_date DESC";
                
        /**
         * 查询即将到期的合同
         */
        public static final String SELECT_EXPIRING_CONTRACTS = 
                "SELECT * FROM personnel_contract WHERE end_date <= DATE_ADD(CURDATE(), INTERVAL #{days} DAY) " +
                "AND end_date >= CURDATE() AND contract_status = 1";
    }
    
    /**
     * 员工联系人相关SQL常量
     */
    public static class Contact {
        /**
         * 根据员工ID查询联系人
         */
        public static final String SELECT_BY_EMPLOYEE_ID = 
                "SELECT * FROM personnel_contact WHERE employee_id = #{employeeId}";
                
        /**
         * 查询员工的紧急联系人
         */
        public static final String SELECT_EMERGENCY_CONTACT = 
                "SELECT * FROM personnel_contact WHERE employee_id = #{employeeId} AND contact_type = 1";
    }
    
    /**
     * 组织关系相关SQL常量
     */
    public static class OrganizationRelation {
        /**
         * 根据员工ID查询组织关系
         */
        public static final String SELECT_BY_EMPLOYEE_ID = 
                "SELECT * FROM employee_organization_relation WHERE employee_id = #{employeeId}";
                
        /**
         * 根据组织ID查询员工
         */
        public static final String SELECT_BY_ORGANIZATION_ID = 
                "SELECT e.* FROM employee e " +
                "INNER JOIN employee_organization_relation r ON e.id = r.employee_id " +
                "WHERE r.organization_id = #{organizationId}";
    }
    
    /**
     * 职位历史相关SQL常量
     */
    public static class PositionHistory {
        /**
         * 根据员工ID查询职位历史
         */
        public static final String SELECT_BY_EMPLOYEE_ID = 
                "SELECT * FROM employee_position_history WHERE employee_id = #{employeeId} ORDER BY start_date DESC";
                
        /**
         * 查询员工当前职位
         */
        public static final String SELECT_CURRENT_POSITION = 
                "SELECT * FROM employee_position_history WHERE employee_id = #{employeeId} AND end_date IS NULL";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private EmployeeSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 