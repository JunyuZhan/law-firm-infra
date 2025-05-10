package com.lawfirm.auth.config;

import com.lawfirm.auth.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 认证模块自动配置类
 * <p>
 * 统一导入所有认证相关配置
 * </p>
 */
@Configuration
@ConditionalOnWebApplication
@Import({
    SecurityConfig.class,
})
@ComponentScan(basePackages = "com.lawfirm.auth")
public class AuthAutoConfiguration {

    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    public AuthAutoConfiguration(
            JwtTokenProvider tokenProvider,
            @Qualifier("cacheRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.tokenProvider = tokenProvider;
        this.redisTemplate = redisTemplate;
    }

    // AuthServiceImpl已通过@Service注解被组件扫描注册，无需在此重复定义
} 