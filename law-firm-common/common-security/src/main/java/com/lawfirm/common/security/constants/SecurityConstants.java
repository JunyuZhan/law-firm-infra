package com.lawfirm.common.security.constants;

/**
 * 安全相关常量
 */
public class SecurityConstants {

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌头部
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 用户ID字段
     */
    public static final String USER_ID_KEY = "userId";

    /**
     * 用户名字段
     */
    public static final String USERNAME_KEY = "username";

    /**
     * 用户类型字段
     */
    public static final String USER_TYPE_KEY = "userType";

    /**
     * 权限分隔符
     */
    public static final String PERMISSION_DELIMITER = ":";

    /**
     * 超级管理员角色标识
     */
    public static final String SUPER_ADMIN_ROLE = "superAdmin";

    /**
     * 公开资源路径
     */
    public static final String[] PUBLIC_RESOURCE_PATHS = {
            // 静态资源
            "/favicon.ico",
            "/static/**",
            // 公共API
            "/api/public/**",
            // 基础路径
            "/", "/error/**",
            // API文档相关路径 - 确保所有环境都可以访问
            "/doc.html",
            "/v3/api-docs/**", 
            "/swagger-ui/**", 
            "/swagger-ui.html", 
            "/webjars/**", 
            "/swagger-resources/**", 
            "/knife4j/**", 
            "/v3/api-docs.yaml",
            // 健康检查
            "/actuator/**",
            "/health/**"
    };

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
} 