package com.lawfirm.model.auth.constant;

/**
 * 认证模块API路径常量
 */
public interface AuthApiConstants {
    
    /**
     * API版本前缀
     */
    String API_PREFIX = "/api/v1";
    
    /**
     * 认证API前缀
     */
    String AUTH_PREFIX = API_PREFIX + "/auth";
    
    /**
     * 用户API前缀
     */
    String USER_PREFIX = API_PREFIX + "/users";
    
    /**
     * 角色API前缀
     */
    String ROLE_PREFIX = API_PREFIX + "/roles";
    
    /**
     * 权限API前缀
     */
    String PERMISSION_PREFIX = API_PREFIX + "/permissions";
    
    /**
     * 部门API前缀
     */
    String DEPARTMENT_PREFIX = API_PREFIX + "/departments";
    
    /**
     * 职位API前缀
     */
    String POSITION_PREFIX = API_PREFIX + "/positions";
    
    /**
     * 用户组API前缀
     */
    String USER_GROUP_PREFIX = API_PREFIX + "/user-groups";
    
    /**
     * 登录历史API前缀
     */
    String LOGIN_HISTORY_PREFIX = API_PREFIX + "/login-history";
    
    /**
     * 登录接口路径
     */
    String LOGIN_PATH = AUTH_PREFIX + "/login";
    
    /**
     * 登出接口路径
     */
    String LOGOUT_PATH = AUTH_PREFIX + "/logout";
    
    /**
     * 刷新Token接口路径
     */
    String REFRESH_TOKEN_PATH = AUTH_PREFIX + "/refresh";
    
    /**
     * 获取验证码接口路径
     */
    String CAPTCHA_PATH = AUTH_PREFIX + "/captcha";
    
    /**
     * 重置密码接口路径
     */
    String RESET_PASSWORD_PATH = AUTH_PREFIX + "/password/reset";
} 