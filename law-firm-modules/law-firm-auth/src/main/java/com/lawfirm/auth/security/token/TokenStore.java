package com.lawfirm.auth.security.token;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TokenStore {
    private static final String TOKEN_PREFIX = "token:";
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String REFRESH_TOKEN_MAPPING = "refresh_token_mapping:";
    private static final long TOKEN_VALIDITY = 24 * 60 * 60; // 24 hours

    private final RedisTemplate<String, String> redisTemplate;

    public TokenStore(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeToken(String username, String token) {
        String key = TOKEN_PREFIX + username;
        redisTemplate.opsForValue().set(key, token, TOKEN_VALIDITY, TimeUnit.SECONDS);
    }

    public String getToken(String username) {
        String key = TOKEN_PREFIX + username;
        return redisTemplate.opsForValue().get(key);
    }

    public boolean isTokenValid(String username, String token) {
        String storedToken = getToken(username);
        return token.equals(storedToken);
    }

    public void removeToken(String username) {
        String key = TOKEN_PREFIX + username;
        redisTemplate.delete(key);
    }
    
    /**
     * 存储刷新令牌
     *
     * @param username 用户名
     * @param refreshToken 刷新令牌
     * @param expiration 过期时间
     * @param timeUnit 时间单位
     */
    public void storeRefreshToken(String username, String refreshToken, long expiration, TimeUnit timeUnit) {
        String refreshKey = REFRESH_TOKEN_PREFIX + username;
        String mappingKey = REFRESH_TOKEN_MAPPING + refreshToken;
        
        // 存储刷新令牌
        redisTemplate.opsForValue().set(refreshKey, refreshToken, expiration, timeUnit);
        // 存储映射关系，用于根据刷新令牌查找用户名
        redisTemplate.opsForValue().set(mappingKey, username, expiration, timeUnit);
    }
    
    /**
     * 获取刷新令牌
     *
     * @param username 用户名
     * @return 刷新令牌
     */
    public String getRefreshToken(String username) {
        String key = REFRESH_TOKEN_PREFIX + username;
        return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 根据刷新令牌获取用户名
     *
     * @param refreshToken 刷新令牌
     * @return 用户名
     */
    public String getUsernameByRefreshToken(String refreshToken) {
        String key = REFRESH_TOKEN_MAPPING + refreshToken;
        return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 验证刷新令牌
     *
     * @param username 用户名
     * @param refreshToken 刷新令牌
     * @return 是否有效
     */
    public boolean isRefreshTokenValid(String username, String refreshToken) {
        String storedRefreshToken = getRefreshToken(username);
        return refreshToken.equals(storedRefreshToken);
    }
    
    /**
     * 移除刷新令牌
     *
     * @param username 用户名
     */
    public void removeRefreshToken(String username) {
        String refreshKey = REFRESH_TOKEN_PREFIX + username;
        String refreshToken = redisTemplate.opsForValue().get(refreshKey);
        
        if (refreshToken != null) {
            String mappingKey = REFRESH_TOKEN_MAPPING + refreshToken;
            redisTemplate.delete(mappingKey);
        }
        
        redisTemplate.delete(refreshKey);
    }
    
    /**
     * 移除特定的刷新令牌
     *
     * @param username 用户名
     * @param refreshToken 刷新令牌
     */
    public void removeRefreshToken(String username, String refreshToken) {
        String mappingKey = REFRESH_TOKEN_MAPPING + refreshToken;
        redisTemplate.delete(mappingKey);
        
        // 如果当前存储的刷新令牌与传入的刷新令牌相同，则同时移除用户名对应的刷新令牌
        String refreshKey = REFRESH_TOKEN_PREFIX + username;
        String storedRefreshToken = redisTemplate.opsForValue().get(refreshKey);
        if (refreshToken.equals(storedRefreshToken)) {
            redisTemplate.delete(refreshKey);
        }
    }
} 