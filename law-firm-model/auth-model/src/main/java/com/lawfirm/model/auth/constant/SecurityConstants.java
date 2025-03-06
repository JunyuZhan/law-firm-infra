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
     * JWT密钥配置键
     */
    String JWT_SECRET_KEY_CONFIG = "${law.firm.security.jwt.secret}";
    
    /**
     * JWT过期时间配置键（毫秒）
     */
    String JWT_EXPIRATION_CONFIG = "${law.firm.security.jwt.expiration:86400000}";
    
    /**
     * 刷新Token过期时间配置键（毫秒）
     */
    String REFRESH_TOKEN_EXPIRATION_CONFIG = "${law.firm.security.jwt.refresh-expiration:604800000}";
    
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
     * 最大登录失败次数配置键
     */
    String MAX_LOGIN_FAIL_TIMES_CONFIG = "${law.firm.security.login.max-fail-times:5}";
    
    /**
     * 登录失败锁定时间配置键（分钟）
     */
    String LOGIN_LOCK_MINUTES_CONFIG = "${law.firm.security.login.lock-minutes:30}";
    
    /**
     * 验证码有效期配置键（分钟）
     */
    String CAPTCHA_EXPIRE_MINUTES_CONFIG = "${law.firm.security.captcha.expire-minutes:5}";
    
    /**
     * 密码最小长度配置键
     */
    String PASSWORD_MIN_LENGTH_CONFIG = "${law.firm.security.password.min-length:8}";
    
    /**
     * 密码是否需要包含数字配置键
     */
    String PASSWORD_REQUIRE_DIGIT_CONFIG = "${law.firm.security.password.require-digit:true}";
    
    /**
     * 密码是否需要包含小写字母配置键
     */
    String PASSWORD_REQUIRE_LOWERCASE_CONFIG = "${law.firm.security.password.require-lowercase:true}";
    
    /**
     * 密码是否需要包含大写字母配置键
     */
    String PASSWORD_REQUIRE_UPPERCASE_CONFIG = "${law.firm.security.password.require-uppercase:true}";
    
    /**
     * 密码是否需要包含特殊字符配置键
     */
    String PASSWORD_REQUIRE_SPECIAL_CHAR_CONFIG = "${law.firm.security.password.require-special-char:true}";
} 