package com.lawfirm.common.security.token;

import com.lawfirm.common.security.authentication.Authentication;
import jakarta.servlet.http.HttpServletRequest;

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
    
    /**
     * 从请求中获取令牌
     *
     * @param request HTTP请求
     * @return 令牌字符串，如果不存在则返回null
     */
    String getToken(HttpServletRequest request);
    
    /**
     * 移除令牌
     *
     * @param token 令牌字符串
     */
    void removeToken(String token);
    
    /**
     * 创建令牌
     *
     * @param username 用户名
     * @return 令牌字符串
     */
    String createToken(String username);
    
    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌
     */
    String refreshToken(String refreshToken);
    
    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌字符串
     * @return 用户名
     */
    String getUsernameFromToken(String token);
} 