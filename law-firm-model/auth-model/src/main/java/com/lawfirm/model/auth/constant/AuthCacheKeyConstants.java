package com.lawfirm.model.auth.constant;

/**
 * 认证模块缓存键常量
 */
public interface AuthCacheKeyConstants {
    
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
     * 用户组相关缓存键前缀
     */
    String USER_GROUP = PREFIX + "user:group:";
    
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
     * 部门权限缓存键
     */
    String DEPARTMENT_PERMISSIONS = DEPARTMENT + "permissions:";
    
    /**
     * 用户组权限缓存键
     */
    String USER_GROUP_PERMISSIONS = USER_GROUP + "permissions:";
    
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