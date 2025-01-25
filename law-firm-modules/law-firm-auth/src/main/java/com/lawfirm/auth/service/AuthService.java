package com.lawfirm.auth.service;

import com.lawfirm.auth.model.LoginUser;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 登录
     */
    LoginUser login(String username, String password);
    
    /**
     * 登出
     */
    void logout(String token);
    
    /**
     * 刷新令牌
     */
    String refreshToken(String token);
    
    /**
     * 验证令牌
     */
    LoginUser verifyToken(String token);
} 