package com.lawfirm.common.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Configuration("redisConnectionFactoryConfig")
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
     */
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
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

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(timeout)
                .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }
    
    /**
     * 解析超时字符串为Duration
     * 支持纯数字（视为毫秒）和带单位的格式（如10s, 500ms）
     */
    private Duration parseTimeout(String timeoutStr) {
        if (timeoutStr == null || timeoutStr.trim().isEmpty()) {
            return Duration.ofMillis(2000); // 默认2秒
        }
        
        // 处理纯数字（毫秒）
        if (timeoutStr.matches("^\\d+$")) {
            try {
                long millis = Long.parseLong(timeoutStr);
                return Duration.ofMillis(millis);
            } catch (NumberFormatException e) {
                log.warn("无法解析超时值：{}，使用默认值2000ms", timeoutStr, e);
                return Duration.ofMillis(2000);
            }
        }
        
        // 处理带单位的格式
        try {
            if (timeoutStr.endsWith("ms")) {
                String value = timeoutStr.substring(0, timeoutStr.length() - 2);
                return Duration.ofMillis(Long.parseLong(value));
            } else if (timeoutStr.endsWith("s")) {
                String value = timeoutStr.substring(0, timeoutStr.length() - 1);
                return Duration.ofSeconds(Long.parseLong(value));
            } else if (timeoutStr.endsWith("m")) {
                String value = timeoutStr.substring(0, timeoutStr.length() - 1);
                return Duration.ofMinutes(Long.parseLong(value));
            } else if (timeoutStr.endsWith("h")) {
                String value = timeoutStr.substring(0, timeoutStr.length() - 1);
                return Duration.ofHours(Long.parseLong(value));
            } else {
                log.warn("未知的时间单位格式：{}，使用默认值2000ms", timeoutStr);
                return Duration.ofMillis(2000);
            }
        } catch (Exception e) {
            log.warn("解析带单位的超时值失败：{}，使用默认值2000ms", timeoutStr, e);
            return Duration.ofMillis(2000);
        }
    }
} 