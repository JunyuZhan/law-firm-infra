package com.lawfirm.auth.constant;

/**
 * 认证模块API常量定义
 * 业务实现层使用的API路径常量，与模型层常量区分开
 * 
 * 设计原则：
 * 1. 仅包含控制器层使用的API路径常量
 * 2. 与数据模型层的AuthConstants区分职责
 * 3. 遵循其他业务模块的常量组织模式
 */
public final class AuthApiConstants {
    
    private AuthApiConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
    
    /**
     * API路径常量
     */
    public static final class Api {
        /**
         * API版本前缀
         */
        public static final String PREFIX = "/api/v1";
        
        /**
         * 认证API前缀
         */
        public static final String AUTH = PREFIX + "/auth";
        
        /**
         * 用户API前缀
         */
        public static final String USER = PREFIX + "/users";
        
        /**
         * 角色API前缀
         */
        public static final String ROLE = PREFIX + "/roles";
        
        /**
         * 权限API前缀
         */
        public static final String PERMISSION = PREFIX + "/permissions";
        
        /**
         * 登录接口路径
         */
        public static final String LOGIN = AUTH + "/login";
        
        /**
         * 登出接口路径
         */
        public static final String LOGOUT = AUTH + "/logout";
        
        /**
         * 刷新Token接口路径
         */
        public static final String REFRESH_TOKEN = AUTH + "/refresh";
        
        /**
         * 获取验证码接口路径
         */
        public static final String CAPTCHA = AUTH + "/captcha";
        
        /**
         * 重置密码接口路径
         */
        public static final String RESET_PASSWORD = AUTH + "/password/reset";
    }
    
    /**
     * 控制器相关常量
     */
    public static final class Controller {
        /**
         * 响应消息
         */
        public static final String RESPONSE_SUCCESS = "操作成功";
        public static final String RESPONSE_LOGIN_SUCCESS = "登录成功";
        public static final String RESPONSE_LOGOUT_SUCCESS = "登出成功";
        public static final String RESPONSE_REFRESH_SUCCESS = "刷新令牌成功";
        public static final String RESPONSE_CAPTCHA_SUCCESS = "获取验证码成功";
        public static final String RESPONSE_PASSWORD_RESET_SUCCESS = "密码重置成功";
        
        /**
         * 请求参数
         */
        public static final String PARAM_USERNAME = "username";
        public static final String PARAM_PASSWORD = "password";
        public static final String PARAM_CAPTCHA = "captcha";
        public static final String PARAM_CAPTCHA_KEY = "captchaKey";
        public static final String PARAM_REFRESH_TOKEN = "refreshToken";
    }
    
    /**
     * 集成相关常量
     */
    public static final class Integration {
        /**
         * 消息模块集成
         */
        public static final String MESSAGE_TYPE_LOGIN = "user_login";
        public static final String MESSAGE_TYPE_LOGOUT = "user_logout";
        public static final String MESSAGE_TYPE_PASSWORD_CHANGE = "password_change";
        
        /**
         * 审计模块集成
         */
        public static final String AUDIT_TYPE_AUTH = "auth";
        public static final String AUDIT_TYPE_USER = "user";
        public static final String AUDIT_TYPE_ROLE = "role";
        public static final String AUDIT_TYPE_PERMISSION = "permission";
    }
} 