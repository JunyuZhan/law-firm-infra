package com.lawfirm.model.auth.constant;

/**
 * 认证模块SQL常量类
 * 集中管理认证相关SQL查询语句，提高可维护性和安全性
 */
public class AuthSqlConstants {
    
    /**
     * 用户相关SQL常量
     */
    public static class User {
        /**
         * 删除用户角色关联
         */
        public static final String DELETE_USER_ROLES = 
                "DELETE FROM auth_user_role WHERE user_id = #{userId}";
                
        /**
         * 批量插入用户角色关联
         */
        public static final String INSERT_USER_ROLES = 
                "<script>" +
                "INSERT INTO auth_user_role (user_id, role_id) VALUES " +
                "<foreach collection='roleIds' item='roleId' separator=','>" +
                "(#{userId}, #{roleId})" +
                "</foreach>" +
                "</script>";
                
        /**
         * 查询用户角色ID列表
         */
        public static final String SELECT_USER_ROLE_IDS = 
                "SELECT role_id FROM auth_user_role WHERE user_id = #{userId}";
                
        /**
         * 根据用户名查询用户
         */
        public static final String SELECT_BY_USERNAME = 
                "SELECT * FROM auth_user WHERE username = #{username} AND deleted = 0";
                
        /**
         * 根据手机号查询用户
         */
        public static final String SELECT_BY_PHONE = 
                "SELECT * FROM auth_user WHERE mobile = #{phone} AND deleted = 0";
                
        /**
         * 根据邮箱查询用户
         */
        public static final String SELECT_BY_EMAIL = 
                "SELECT * FROM auth_user WHERE email = #{email} AND deleted = 0";
    }
    
    /**
     * 角色相关SQL常量
     */
    public static class Role {
        /**
         * 根据角色编码查询角色
         */
        public static final String SELECT_BY_CODE = 
                "SELECT * FROM auth_role WHERE code = #{code} AND deleted = 0";
                
        /**
         * 分页查询角色列表
         */
        public static final String SELECT_PAGE = 
                "<script>SELECT * FROM auth_role WHERE deleted = 0 " +
                "<if test='name != null and name != \"\"'>AND name LIKE CONCAT('%', #{name}, '%')</if> " +
                "ORDER BY create_time DESC " +
                "LIMIT #{pageNum}, #{pageSize}" +
                "</script>";
                
        /**
         * 查询角色总数
         */
        public static final String SELECT_COUNT = 
                "<script>SELECT COUNT(*) FROM auth_role WHERE deleted = 0 " +
                "<if test='name != null and name != \"\"'>AND name LIKE CONCAT('%', #{name}, '%')</if>" +
                "</script>";
                
        /**
         * 查询所有角色
         */
        public static final String SELECT_ALL = 
                "SELECT * FROM auth_role WHERE deleted = 0 ORDER BY sort";
                
        /**
         * 查询用户的所有角色
         */
        public static final String SELECT_ROLES_BY_USER_ID = 
                "SELECT r.* FROM auth_role r " +
                "INNER JOIN auth_user_role ur ON r.id = ur.role_id " +
                "WHERE ur.user_id = #{userId} AND r.deleted = 0";
    }
    
    /**
     * 角色权限关联相关SQL常量
     */
    public static class RolePermission {
        /**
         * 通过角色ID查询角色权限关联
         */
        public static final String SELECT_BY_ROLE_ID = 
                "SELECT * FROM auth_role_permission WHERE role_id = #{roleId}";
                
        /**
         * 通过权限ID查询角色权限关联
         */
        public static final String SELECT_BY_PERMISSION_ID = 
                "SELECT * FROM auth_role_permission WHERE permission_id = #{permissionId}";
                
        /**
         * 批量新增角色权限关联
         */
        public static final String INSERT_BATCH = 
                "<script>" +
                "INSERT INTO auth_role_permission (role_id, permission_id) VALUES " +
                "<foreach collection='rolePermissions' item='item' separator=','>" +
                "(#{item.roleId}, #{item.permissionId})" +
                "</foreach>" +
                "</script>";
                
        /**
         * 删除角色权限关联
         */
        public static final String DELETE = 
                "DELETE FROM auth_role_permission WHERE role_id = #{roleId} AND permission_id = #{permissionId}";
                
        /**
         * 通过角色ID删除角色权限关联
         */
        public static final String DELETE_BY_ROLE_ID = 
                "DELETE FROM auth_role_permission WHERE role_id = #{roleId}";
                
        /**
         * 通过权限ID删除角色权限关联
         */
        public static final String DELETE_BY_PERMISSION_ID = 
                "DELETE FROM auth_role_permission WHERE permission_id = #{permissionId}";
                
        /**
         * 查询是否存在角色权限关联
         */
        public static final String EXISTS = 
                "SELECT COUNT(1) FROM auth_role_permission WHERE role_id = #{roleId} AND permission_id = #{permissionId}";
                
        /**
         * 获取角色的所有权限ID
         */
        public static final String SELECT_PERMISSION_IDS_BY_ROLE_ID = 
                "SELECT permission_id FROM auth_role_permission WHERE role_id = #{roleId}";
    }
    
    /**
     * 权限相关SQL常量
     */
    public static class Permission {
        /**
         * 查询用户权限编码列表
         */
        public static final String SELECT_EMPLOYEE_PERMISSION_CODES = 
                "SELECT DISTINCT p.code FROM auth_permission p " +
                "INNER JOIN auth_role_permission rp ON p.id = rp.permission_id " +
                "INNER JOIN auth_user_role ur ON rp.role_id = ur.role_id " +
                "WHERE ur.user_id = #{userId}";
                
        /**
         * 查询用户菜单列表
         */
        public static final String SELECT_MENUS_BY_USER_ID = 
                "SELECT DISTINCT p.* FROM auth_permission p " +
                "INNER JOIN auth_role_permission rp ON p.id = rp.permission_id " +
                "INNER JOIN auth_user_role ur ON rp.role_id = ur.role_id " +
                "WHERE ur.user_id = #{userId} AND p.type = 0 " +
                "ORDER BY p.sort";
    }
    
    /**
     * 登录历史相关SQL常量
     */
    public static class LoginHistory {
        /**
         * 根据用户ID查询登录历史
         */
        public static final String SELECT_BY_USER_ID = 
                "SELECT * FROM auth_login_history WHERE user_id = #{userId} AND deleted = 0 ORDER BY login_time DESC";
                
        /**
         * 根据IP地址查询登录历史
         */
        public static final String SELECT_BY_IP_ADDRESS = 
                "SELECT * FROM auth_login_history WHERE ip_address = #{ipAddress} AND deleted = 0 ORDER BY login_time DESC";
                
        /**
         * 分页查询登录历史
         */
        public static final String SELECT_PAGE = 
                "<script>SELECT * FROM auth_login_history " +
                "<where>" +
                "  deleted = 0" +
                "  <if test='userId != null'>AND user_id = #{userId}</if>" +
                "  <if test='loginType != null'>AND login_type = #{loginType}</if>" +
                "  <if test='status != null'>AND status = #{status}</if>" +
                "  <if test='startTime != null'>AND login_time &gt;= #{startTime}</if>" +
                "  <if test='endTime != null'>AND login_time &lt;= #{endTime}</if>" +
                "</where>" +
                "ORDER BY login_time DESC " +
                "<if test='pageNum != null and pageSize != null'>LIMIT #{pageNum}, #{pageSize}</if>" +
                "</script>";
                
        /**
         * 查询登录历史总数
         */
        public static final String SELECT_COUNT = 
                "<script>SELECT COUNT(*) FROM auth_login_history " +
                "<where>" +
                "  deleted = 0" +
                "  <if test='userId != null'>AND user_id = #{userId}</if>" +
                "  <if test='loginType != null'>AND login_type = #{loginType}</if>" +
                "  <if test='status != null'>AND status = #{status}</if>" +
                "  <if test='startTime != null'>AND login_time &gt;= #{startTime}</if>" +
                "  <if test='endTime != null'>AND login_time &lt;= #{endTime}</if>" +
                "</where>" +
                "</script>";
                
        /**
         * 根据用户ID删除登录历史
         */
        public static final String DELETE_BY_USER_ID = 
                "UPDATE auth_login_history SET deleted = 1 WHERE user_id = #{userId}";
                
        /**
         * 删除指定日期之前的登录历史
         */
        public static final String DELETE_BEFORE_DATE = 
                "UPDATE auth_login_history SET deleted = 1 WHERE login_time < #{date}";
                
        /**
         * 查询用户最后一次登录记录
         */
        public static final String SELECT_LAST_LOGIN_BY_USER_ID = 
                "SELECT * FROM auth_login_history WHERE user_id = #{userId} AND deleted = 0 ORDER BY login_time DESC LIMIT 1";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private AuthSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 