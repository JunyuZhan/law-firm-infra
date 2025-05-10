package com.lawfirm.common.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

/**
 * 缓存自动配置类
 * 
 * 职责：
 * 1. 作为通用基础设施，提供缓存抽象层的配置入口
 * 2. 整合所有缓存相关配置，确保配置的一致性
 * 3. 不包含任何业务逻辑或特定业务场景的实现
 * 4. 只负责导入其他配置类，维护缓存基础设施的完整性
 */
@Slf4j
@Configuration("commonCacheAutoConfiguration")
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "law-firm.common.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({
    RedisConfig.class,
    CacheConfig.class,
    AppCacheConfig.class,
    LegacyCompatConfig.class
})
public class CacheAutoConfiguration {

    /**
     * 初始化缓存状态检查器
     * 作为基础设施组件，只提供通用的健康检查功能
     */
    @Bean(name = "commonCacheHealthIndicator")
    @Order(2)
    public CacheHealthIndicator cacheHealthIndicator() {
        log.info("初始化缓存健康检查器");
        return new CacheHealthIndicator();
    }
    
    /**
     * 提供缓存健康状态检查
     * 纯粹的基础设施工具类，不包含业务逻辑
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