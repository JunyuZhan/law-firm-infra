package com.lawfirm.common.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缓存模块Redis配置主类
 * 
 * 负责整合所有Redis相关配置，是缓存模块的Redis配置入口。
 * 此配置类通过@Import导入其他配置类，维护了Redis基础设施的完整性。
 */
@Configuration("commonRedisConfig")
@Import({
    RedisTemplateConfig.class,
    RedisConnectionFactoryConfig.class
})
public class RedisConfig {
    // 此类作为配置聚合器，不需要其他实现
} 