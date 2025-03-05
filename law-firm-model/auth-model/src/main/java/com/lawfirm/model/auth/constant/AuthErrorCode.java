package com.lawfirm.model.auth.constant;

/**
 * 认证模块错误码常量
 */
public interface AuthErrorCode {
    
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
    
    /**
     * 多因素认证失败
     */
    int MFA_VERIFICATION_FAILED = AUTH_ERROR_BASE + 18;
    
    /**
     * 租户不存在
     */
    int TENANT_NOT_FOUND = AUTH_ERROR_BASE + 19;
    
    /**
     * 租户已禁用
     */
    int TENANT_DISABLED = AUTH_ERROR_BASE + 20;
} 