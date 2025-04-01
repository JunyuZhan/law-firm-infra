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
     * API文档和Swagger相关公开路径
     */
    public static final String[] API_DOC_PATHS = {
            "/doc.html", "/doc.html/**", "/doc/**",
            "/swagger-ui.html", "/swagger-ui/**", "/swagger-ui",
            "/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs-ext/**",
            "/swagger-resources/**", "/swagger-resources",
            "/swagger-config/**", "/swagger-config",
            "/webjars/**", "/webjars",
            "/knife4j/**", "/knife4j",
            "/api-docs/**", "/api-docs",
            "/v2/api-docs/**", "/v2/api-docs",
            "/configuration/ui", "/configuration/security",
            "/favicon.ico", "/markdown/**",
            "/swagger/**", "/swagger-ui/index.html",
            "/all", "/business", "/system", // API分组路径
            "/**/swagger-config", "/**/api-docs/**", // 嵌套路径
            "/**/swagger-ui/**", "/**/v3/api-docs/**" // 确保包含上下文路径的情况
            // 注意：根路径 / 和 /api/ 通常在具体配置中单独处理或通过视图控制器映射
    };

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
} 