package com.lawfirm.common.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * Redis连接工厂配置
 * <p>
 * 覆盖默认的Redis连接工厂配置，解决空密码导致的AUTH命令问题
 * </p>
 */
@Slf4j
@Configuration("commonRedisConnectionFactoryConfig")
@ConditionalOnProperty(prefix = "spring.data.redis", name = "host")
public class RedisConnectionFactoryConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.password:}")
    private String password;

    @Value("${spring.data.redis.database:0}")
    private int database;

    /**
     * 解析超时配置，支持多种格式：
     * - 纯数字（毫秒）: 2000
     * - 带单位的字符串: 10s, 500ms
     */
    @Value("${spring.data.redis.timeout:2000}")
    private String timeoutString;

    /**
     * 创建Redis连接工厂，正确处理空密码情况
     * 明确命名为commonRedisConnectionFactory，避免与Spring Boot自动配置冲突
     */
    @Bean("commonRedisConnectionFactory")
    @Primary
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory commonRedisConnectionFactory() {
        log.info("初始化Redis连接工厂，连接到 {}:{}", host, port);
        
        // 创建Redis标准配置
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setDatabase(database);

        // 只在密码非空时设置密码
        if (StringUtils.hasText(password) && !password.trim().isEmpty()) {
            log.info("Redis连接使用密码认证");
            redisConfig.setPassword(RedisPassword.of(password));
        } else {
            log.info("Redis连接不使用密码认证");
            // 显式设置为空密码
            redisConfig.setPassword(RedisPassword.none());
        }

        // 解析超时配置
        Duration timeout = parseTimeout(timeoutString);
        log.info("Redis连接超时设置为：{}", timeout);

        // 创建Lettuce客户端配置
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(timeout)
                .build();

        // 创建并返回连接工厂
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfig);
        // 确保连接工厂完全初始化
        factory.afterPropertiesSet();
        log.info("Redis连接工厂创建完成");
        
        return factory;
    }
    
    /**
     * 解析超时字符串为Duration
     * 支持纯数字（视为毫秒）和带单位的格式（如10s, 500ms）
     */
    private Duration parseTimeout(String timeoutString) {
        try {
            // 尝试解析为数字（毫秒）
            return Duration.ofMillis(Long.parseLong(timeoutString));
        } catch (NumberFormatException e) {
            // 如果不是纯数字，尝试按照标准格式解析
            if (timeoutString.endsWith("ms")) {
                return Duration.ofMillis(Long.parseLong(timeoutString.replace("ms", "")));
            } else if (timeoutString.endsWith("s")) {
                return Duration.ofSeconds(Long.parseLong(timeoutString.replace("s", "")));
            } else if (timeoutString.endsWith("m")) {
                return Duration.ofMinutes(Long.parseLong(timeoutString.replace("m", "")));
            } else {
                // 默认解析为毫秒
                return Duration.ofMillis(2000);
            }
        }
    }
} 