package com.lawfirm.model.auth.constant;

/**
 * 认证模块缓存键常量
 */
public interface AuthCacheKeyConstants {
    
    /**
     * 缓存键前缀
     */
    String AUTH_CACHE_PREFIX = "law:firm:auth:";
    
    /**
     * 用户信息缓存键
     */
    String USER_INFO = AUTH_CACHE_PREFIX + "user:info:";
    
    /**
     * 用户权限缓存键
     */
    String USER_PERMISSIONS = AUTH_CACHE_PREFIX + "user:permissions:";
    
    /**
     * 用户角色缓存键
     */
    String USER_ROLES = AUTH_CACHE_PREFIX + "user:roles:";
    
    /**
     * 角色权限缓存键
     */
    String ROLE_PERMISSIONS = AUTH_CACHE_PREFIX + "role:permissions:";
    
    /**
     * 部门权限缓存键
     */
    String DEPARTMENT_PERMISSIONS = AUTH_CACHE_PREFIX + "department:permissions:";
    
    /**
     * 职位权限缓存键
     */
    String POSITION_PERMISSIONS = AUTH_CACHE_PREFIX + "position:permissions:";
    
    /**
     * 用户组权限缓存键
     */
    String USER_GROUP_PERMISSIONS = AUTH_CACHE_PREFIX + "user:group:permissions:";
    
    /**
     * 验证码缓存键
     */
    String CAPTCHA = AUTH_CACHE_PREFIX + "captcha:";
    
    /**
     * 登录失败次数缓存键
     */
    String LOGIN_FAIL_COUNT = AUTH_CACHE_PREFIX + "login:fail:count:";
    
    /**
     * 登录锁定缓存键
     */
    String LOGIN_LOCK = AUTH_CACHE_PREFIX + "login:lock:";
    
    /**
     * Token黑名单缓存键
     */
    String TOKEN_BLACKLIST = AUTH_CACHE_PREFIX + "token:blacklist:";
    
    /**
     * 刷新Token缓存键
     */
    String REFRESH_TOKEN = AUTH_CACHE_PREFIX + "refresh:token:";
} 