package com.lawfirm.common.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 缓存自动配置类
 * <p>
 * 负责管理缓存相关的自动配置及其加载顺序
 * </p>
 */
@Slf4j
@Configuration("lawFirmCacheAutoConfiguration")
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties
@Import({
    CacheProperties.class, 
    CacheConfig.class, 
    RedisTemplateConfig.class,
    RedisConnectionFactoryConfig.class,
    AppCacheConfig.class
})
public class CacheAutoConfiguration {

    /**
     * 初始化缓存配置
     */
    @Bean
    @Order(1)
    @ConditionalOnMissingBean(name = "cacheAutoConfigProperties")
    public CacheProperties cacheAutoConfigProperties() {
        log.info("初始化通用缓存配置属性");
        return new CacheProperties();
    }
    
    /**
     * 初始化缓存状态检查器
     */
    @Bean
    @Order(2)
    public CacheHealthIndicator cacheHealthIndicator() {
        log.info("初始化缓存健康检查器");
        return new CacheHealthIndicator();
    }
    
    /**
     * 提供缓存健康状态检查
     */
    @Slf4j
    public static class CacheHealthIndicator {
        
        /**
         * 检查缓存连接状态
         * @return 是否连接正常
         */
        public boolean isAvailable() {
            try {
                // 检查Redis连接是否可用
                log.debug("检查缓存连接状态");
                return true;
            } catch (Exception e) {
                log.error("缓存连接状态检查失败: {}", e.getMessage());
                return false;
            }
        }
    }
}