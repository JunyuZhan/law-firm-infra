package com.lawfirm.model.auth.constant;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 认证模块统一常量定义
 * 整合了原有的SecurityConstants、AuthCacheKeyConstants、AuthErrorCode和AuthApiConstants
 */
public interface AuthConstants extends BaseConstants {
    
    /**
     * 数据表常量
     */
    interface Table {
        /**
         * 用户表名
         */
        String USER = "auth_user";
        
        /**
         * 角色表名
         */
        String ROLE = "auth_role";
        
        /**
         * 权限表名
         */
        String PERMISSION = "auth_permission";
        
        /**
         * 用户角色关联表名
         */
        String USER_ROLE = "auth_user_role";
        
        /**
         * 角色权限关联表名
         */
        String ROLE_PERMISSION = "auth_role_permission";
        
        /**
         * 登录历史表名
         */
        String LOGIN_HISTORY = "auth_login_history";
    }
    
    /**
     * 字段常量
     */
    interface Field {
        /**
         * 用户ID字段
         */
        String USER_ID = "user_id";
        
        /**
         * 角色ID字段
         */
        String ROLE_ID = "role_id";
        
        /**
         * 权限ID字段
         */
        String PERMISSION_ID = "permission_id";
        
        /**
         * 用户名字段
         */
        String USERNAME = "username";
        
        /**
         * 密码字段
         */
        String PASSWORD = "password";
        
        /**
         * 状态字段
         */
        String STATUS = "status";
        
        /**
         * 角色编码字段
         */
        String ROLE_CODE = "role_code";
        
        /**
         * 权限编码字段
         */
        String PERMISSION_CODE = "permission_code";
    }
    
    /**
     * 用户状态常量
     */
    interface UserStatus {
        /**
         * 启用
         */
        int ENABLED = 1;
        
        /**
         * 禁用
         */
        int DISABLED = 0;
        
        /**
         * 锁定
         */
        int LOCKED = 2;
    }
    
    /**
     * 安全相关常量
     */
    interface Security {
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
    
    /**
     * 缓存键常量
     */
    interface CacheKey {
        /**
         * 缓存键前缀
         */
        String PREFIX = "law:firm:auth:";
        
        /**
         * 用户相关缓存键前缀
         */
        String USER = PREFIX + "user:";
        
        /**
         * 角色相关缓存键前缀
         */
        String ROLE = PREFIX + "role:";
        
        /**
         * 权限相关缓存键前缀
         */
        String PERMISSION = PREFIX + "permission:";
        
        /**
         * 部门相关缓存键前缀
         */
        String DEPARTMENT = PREFIX + "department:";
        
        /**
         * 登录相关缓存键前缀
         */
        String LOGIN = PREFIX + "login:";
        
        /**
         * Token相关缓存键前缀
         */
        String TOKEN = PREFIX + "token:";
        
        /**
         * 用户信息缓存键
         */
        String USER_INFO = USER + "info:";
        
        /**
         * 用户权限缓存键
         */
        String USER_PERMISSIONS = USER + "permissions:";
        
        /**
         * 用户角色缓存键
         */
        String USER_ROLES = USER + "roles:";
        
        /**
         * 角色权限缓存键
         */
        String ROLE_PERMISSIONS = ROLE + "permissions:";
        
        /**
         * 验证码缓存键
         */
        String CAPTCHA = LOGIN + "captcha:";
        
        /**
         * 登录失败次数缓存键
         */
        String LOGIN_FAIL_COUNT = LOGIN + "fail:count:";
        
        /**
         * 登录锁定缓存键
         */
        String LOGIN_LOCK = LOGIN + "lock:";
        
        /**
         * Token黑名单缓存键
         */
        String TOKEN_BLACKLIST = TOKEN + "blacklist:";
        
        /**
         * 刷新Token缓存键
         */
        String REFRESH_TOKEN = TOKEN + "refresh:";
    }
    
    /**
     * 错误码常量
     */
    interface ErrorCode {
        /**
         * 认证相关错误码基础值
         */
        int AUTH_ERROR_BASE = 10000;
        
        /**
         * 用户名或密码错误
         */
        int INVALID_CREDENTIALS = AUTH_ERROR_BASE + 1;
        
        /**
         * 账号已锁定
         */
        int ACCOUNT_LOCKED = AUTH_ERROR_BASE + 2;
        
        /**
         * 账号已禁用
         */
        int ACCOUNT_DISABLED = AUTH_ERROR_BASE + 3;
        
        /**
         * 账号已过期
         */
        int ACCOUNT_EXPIRED = AUTH_ERROR_BASE + 4;
        
        /**
         * 密码已过期
         */
        int PASSWORD_EXPIRED = AUTH_ERROR_BASE + 5;
        
        /**
         * 无效的Token
         */
        int INVALID_TOKEN = AUTH_ERROR_BASE + 6;
        
        /**
         * Token已过期
         */
        int TOKEN_EXPIRED = AUTH_ERROR_BASE + 7;
        
        /**
         * 无效的刷新Token
         */
        int INVALID_REFRESH_TOKEN = AUTH_ERROR_BASE + 8;
        
        /**
         * 权限不足
         */
        int INSUFFICIENT_PERMISSIONS = AUTH_ERROR_BASE + 9;
        
        /**
         * 验证码错误
         */
        int INVALID_CAPTCHA = AUTH_ERROR_BASE + 10;
        
        /**
         * 验证码已过期
         */
        int CAPTCHA_EXPIRED = AUTH_ERROR_BASE + 11;
        
        /**
         * 用户不存在
         */
        int USER_NOT_FOUND = AUTH_ERROR_BASE + 12;
        
        /**
         * 角色不存在
         */
        int ROLE_NOT_FOUND = AUTH_ERROR_BASE + 13;
        
        /**
         * 权限不存在
         */
        int PERMISSION_NOT_FOUND = AUTH_ERROR_BASE + 14;
        
        /**
         * 用户名已存在
         */
        int USERNAME_ALREADY_EXISTS = AUTH_ERROR_BASE + 15;
        
        /**
         * 角色编码已存在
         */
        int ROLE_CODE_ALREADY_EXISTS = AUTH_ERROR_BASE + 16;
        
        /**
         * 权限编码已存在
         */
        int PERMISSION_CODE_ALREADY_EXISTS = AUTH_ERROR_BASE + 17;
    }
    
    /**
     * API 路径常量
     */
    interface Api {
        /**
         * API版本前缀
         */
        String PREFIX = "/api/v1";
        
        /**
         * 认证API前缀
         */
        String AUTH = PREFIX + "/auth";
        
        /**
         * 用户API前缀
         */
        String USER = PREFIX + "/users";
        
        /**
         * 角色API前缀
         */
        String ROLE = PREFIX + "/roles";
        
        /**
         * 权限API前缀
         */
        String PERMISSION = PREFIX + "/permissions";
        
        /**
         * 登录接口路径
         */
        String LOGIN = AUTH + "/login";
        
        /**
         * 登出接口路径
         */
        String LOGOUT = AUTH + "/logout";
        
        /**
         * 刷新Token接口路径
         */
        String REFRESH_TOKEN = AUTH + "/refresh";
        
        /**
         * 获取验证码接口路径
         */
        String CAPTCHA = AUTH + "/captcha";
        
        /**
         * 重置密码接口路径
         */
        String RESET_PASSWORD = AUTH + "/password/reset";
    }
    
    /**
     * 权限相关常量
     */
    interface Permission {
        /**
         * 系统管理权限前缀
         */
        String SYSTEM = "system:";
        
        /**
         * 用户管理权限前缀
         */
        String USER = SYSTEM + "user:";
        
        /**
         * 角色管理权限前缀
         */
        String ROLE = SYSTEM + "role:";
        
        /**
         * 权限管理权限前缀
         */
        String PERMISSION = SYSTEM + "permission:";
        
        /**
         * 查看用户权限
         */
        String USER_VIEW = USER + "view";
        
        /**
         * 创建用户权限
         */
        String USER_CREATE = USER + "create";
        
        /**
         * 修改用户权限
         */
        String USER_UPDATE = USER + "update";
        
        /**
         * 删除用户权限
         */
        String USER_DELETE = USER + "delete";
        
        /**
         * 查看角色权限
         */
        String ROLE_VIEW = ROLE + "view";
        
        /**
         * 创建角色权限
         */
        String ROLE_CREATE = ROLE + "create";
        
        /**
         * 修改角色权限
         */
        String ROLE_UPDATE = ROLE + "update";
        
        /**
         * 删除角色权限
         */
        String ROLE_DELETE = ROLE + "delete";
    }
} 