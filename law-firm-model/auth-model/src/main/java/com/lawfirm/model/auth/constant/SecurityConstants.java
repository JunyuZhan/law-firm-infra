package com.lawfirm.model.auth.constant;

/**
 * 安全相关常量定义
 */
public interface SecurityConstants {
    
    /**
     * 认证请求头
     */
    String AUTHORIZATION_HEADER = "Authorization";
    
    /**
     * Token前缀
     */
    String TOKEN_PREFIX = "Bearer ";
    
    /**
     * 用户ID的请求头
     */
    String USER_ID_HEADER = "X-User-Id";
    
    /**
     * 用户名的请求头
     */
    String USERNAME_HEADER = "X-Username";
    
    /**
     * 租户ID的请求头
     */
    String TENANT_ID_HEADER = "X-Tenant-Id";
    
    /**
     * JWT密钥
     */
    String JWT_SECRET_KEY = "${law.firm.security.jwt.secret:defaultSecretKey}";
    
    /**
     * JWT过期时间（毫秒）
     */
    long JWT_EXPIRATION = 86400000; // 默认24小时
    
    /**
     * 刷新Token过期时间（毫秒）
     */
    long REFRESH_TOKEN_EXPIRATION = 604800000; // 默认7天
    
    /**
     * 登录路径
     */
    String LOGIN_PATH = "/auth/login";
    
    /**
     * 登出路径
     */
    String LOGOUT_PATH = "/auth/logout";
    
    /**
     * 刷新Token路径
     */
    String REFRESH_TOKEN_PATH = "/auth/refresh";
    
    /**
     * 验证码路径
     */
    String CAPTCHA_PATH = "/auth/captcha";
    
    /**
     * 公共路径前缀
     */
    String[] PUBLIC_PATHS = {
            "/auth/login",
            "/auth/refresh",
            "/auth/captcha",
            "/auth/password/reset/**",
            "/doc.html",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/actuator/**"
    };
    
    /**
     * 最大登录失败次数
     */
    int MAX_LOGIN_FAIL_TIMES = 5;
    
    /**
     * 登录失败锁定时间（分钟）
     */
    int LOGIN_LOCK_MINUTES = 30;
    
    /**
     * 验证码有效期（分钟）
     */
    int CAPTCHA_EXPIRE_MINUTES = 5;
    
    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "Law@123456";
} 