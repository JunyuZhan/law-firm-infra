package com.lawfirm.model.organization.constant;

/**
 * 组织模块SQL常量类
 * 集中管理组织相关SQL查询语句，提高可维护性和安全性
 */
public class OrganizationSqlConstants {
    
    /**
     * 部门相关SQL常量
     */
    public static class Department {
        /**
         * 根据部门编码查询
         */
        public static final String SELECT_BY_CODE = 
                "SELECT * FROM org_department WHERE code = #{code} AND deleted = 0";
                
        /**
         * 查询子部门列表
         */
        public static final String SELECT_BY_PARENT_ID = 
                "SELECT * FROM org_department WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY sort";
                
        /**
         * 根据部门类型查询
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM org_department WHERE dept_type = #{type} AND deleted = 0";
                
        /**
         * 根据律所ID查询部门列表
         */
        public static final String SELECT_BY_FIRM_ID = 
                "SELECT * FROM org_department WHERE firm_id = #{firmId} AND deleted = 0";
    }
    
    /**
     * 团队相关SQL常量
     */
    public static class Team {
        /**
         * 根据团队编码查询
         */
        public static final String SELECT_BY_CODE = 
                "SELECT * FROM org_team WHERE code = #{code} AND deleted = 0";
                
        /**
         * 根据部门ID查询团队列表
         */
        public static final String SELECT_BY_DEPARTMENT_ID = 
                "SELECT * FROM org_team WHERE department_id = #{departmentId} AND deleted = 0";
                
        /**
         * 根据团队类型查询
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM org_team WHERE team_type = #{type} AND deleted = 0";
                
        /**
         * 根据负责人ID查询团队列表
         */
        public static final String SELECT_BY_LEADER_ID = 
                "SELECT * FROM org_team WHERE leader_id = #{leaderId} AND deleted = 0";
                
        /**
         * 根据成员ID查询所属团队列表
         */
        public static final String SELECT_BY_MEMBER_ID = 
                "SELECT t.* FROM org_team t " +
                "INNER JOIN org_team_member m ON t.id = m.team_id " +
                "WHERE m.member_id = #{memberId} AND t.deleted = 0";
    }
    
    /**
     * 职位相关SQL常量
     */
    public static class Position {
        /**
         * 根据部门ID查询职位列表
         */
        public static final String FIND_BY_DEPARTMENT_ID = 
                "SELECT * FROM org_position WHERE department_id = #{departmentId} AND deleted = 0";
                
        /**
         * 根据职位类型查询职位列表
         */
        public static final String FIND_BY_TYPE = 
                "SELECT * FROM org_position WHERE position_type = #{type} AND deleted = 0";
                
        /**
         * 查询指定部门下的职位数量
         */
        public static final String COUNT_BY_DEPARTMENT_ID = 
                "SELECT COUNT(*) FROM org_position WHERE department_id = #{departmentId} AND deleted = 0";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private OrganizationSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 