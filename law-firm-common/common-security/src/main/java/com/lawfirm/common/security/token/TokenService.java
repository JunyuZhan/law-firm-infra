package com.lawfirm.common.security.token;

import com.lawfirm.common.security.authentication.Authentication;

/**
 * Token服务接口
 * 用于处理认证令牌的生成、验证和解析
 */
public interface TokenService {
    
    /**
     * 生成令牌
     * @param authentication 认证信息
     * @return 令牌字符串
     */
    String generateToken(Authentication authentication);
    
    /**
     * 验证令牌有效性
     * @param token 令牌字符串
     * @return 是否有效
     */
    boolean validateToken(String token);
    
    /**
     * 从令牌中解析认证信息
     * @param token 令牌字符串
     * @return 认证信息
     */
    Authentication getAuthenticationFromToken(String token);
} 