package com.lawfirm.auth.service.impl;

import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.auth.service.SingleLoginService;
import com.lawfirm.common.core.constant.CacheConstants;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 单点登录服务实现类
 */
@Service
@RequiredArgsConstructor
public class SingleLoginServiceImpl implements SingleLoginService {

    private final RedisService redisService;
    private static final String USER_TOKEN_KEY = "user:token:";
    private static final String TOKEN_USER_KEY = "token:user:";
    private static final long TOKEN_EXPIRE_TIME = 24; // token过期时间（小时）

    @Override
    public void recordLoginInfo(String token, LoginUser user) {
        String username = user.getUsername();
        
        // 如果开启了单点登录，先清除该用户的其他登录
        String oldToken = getCurrentToken(username);
        if (oldToken != null && !oldToken.equals(token)) {
            clearLoginInfo(oldToken, username);
        }
        
        // 记录新的登录信息
        redisService.setCacheObject(USER_TOKEN_KEY + username, token, TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
        redisService.setCacheObject(TOKEN_USER_KEY + token, user, TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
    }

    @Override
    public void clearLoginInfo(String token, String username) {
        redisService.deleteObject(USER_TOKEN_KEY + username);
        redisService.deleteObject(TOKEN_USER_KEY + token);
    }

    @Override
    public long getLoginCount(String username) {
        String token = getCurrentToken(username);
        return token != null ? 1 : 0;
    }

    @Override
    public String getCurrentToken(String username) {
        return redisService.getCacheObject(USER_TOKEN_KEY + username);
    }

    @Override
    public boolean isValidToken(String username, String token) {
        String currentToken = getCurrentToken(username);
        return token != null && token.equals(currentToken);
    }
} 