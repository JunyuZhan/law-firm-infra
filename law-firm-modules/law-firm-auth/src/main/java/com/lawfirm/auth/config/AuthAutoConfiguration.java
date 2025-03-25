package com.lawfirm.auth.config;

import com.lawfirm.auth.security.provider.JwtTokenProvider;

import com.lawfirm.auth.service.impl.AuthServiceImpl;
import com.lawfirm.model.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * 认证模块自动配置类
 */
@Configuration
@ComponentScan(basePackages = "com.lawfirm.auth")
@RequiredArgsConstructor
public class AuthAutoConfiguration {

    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建AuthService实例
     * 通过懒加载模式避免循环依赖
     */
    @Bean
    public AuthService authService(@Qualifier("authAuthenticationManager") AuthenticationManager authenticationManager) {
        return new AuthServiceImpl(authenticationManager, tokenProvider, redisTemplate);
    }
} 