package com.lawfirm.auth.service.impl;

import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.auth.repository.UserRepository;
import com.lawfirm.auth.service.AuthService;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_KEY_PREFIX = "auth:token:";
    private static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000; // 24小时
    private static final SecretKey JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginUser login(String username, String password) {
        // 从数据库查询用户
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException("用户不存在"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        if (!user.getEnabled()) {
            throw new BusinessException("账户已禁用");
        }

        // 生成token
        String token = generateToken(user);

        // 创建登录用户对象
        LoginUser loginUser = new LoginUser(user, new HashSet<>());
        loginUser.setUserId(user.getId());
        loginUser.setToken(token);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);

        // 保存到Redis
        redisTemplate.opsForValue().set(
            TOKEN_KEY_PREFIX + token,
            loginUser,
            TOKEN_EXPIRE_TIME,
            TimeUnit.MILLISECONDS
        );

        return loginUser;
    }

    @Override
    public void logout(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            token = token.substring(TOKEN_PREFIX.length());
        }
        
        // 从Redis中删除token
        redisTemplate.delete(TOKEN_KEY_PREFIX + token);
    }

    @Override
    public String refreshToken(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            token = token.substring(TOKEN_PREFIX.length());
        }

        // 从Redis中获取登录用户信息
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + token);
        if (loginUser == null) {
            throw new BusinessException("令牌已过期");
        }

        // 生成新token
        String newToken = generateToken(loginUser.getUser());

        // 更新Redis
        loginUser.setToken(newToken);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);

        redisTemplate.delete(TOKEN_KEY_PREFIX + token);
        redisTemplate.opsForValue().set(
            TOKEN_KEY_PREFIX + newToken,
            loginUser,
            TOKEN_EXPIRE_TIME,
            TimeUnit.MILLISECONDS
        );

        return TOKEN_PREFIX + newToken;
    }

    @Override
    public LoginUser verifyToken(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            token = token.substring(TOKEN_PREFIX.length());
        }

        // 从Redis中获取登录用户信息
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + token);
        if (loginUser == null) {
            throw new BusinessException("令牌已过期");
        }

        // 验证token是否过期
        if (System.currentTimeMillis() > loginUser.getExpireTime()) {
            redisTemplate.delete(TOKEN_KEY_PREFIX + token);
            throw new BusinessException("令牌已过期");
        }

        return loginUser;
    }

    /**
     * 生成JWT token
     */
    private String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(JWT_KEY)
                .compact();
    }
} 