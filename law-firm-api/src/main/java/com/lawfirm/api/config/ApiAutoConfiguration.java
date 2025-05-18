package com.lawfirm.api.config;

import com.lawfirm.api.aspect.IdempotentAspect;
import com.lawfirm.api.aspect.RepeatSubmitPreventionAspect;
import com.lawfirm.api.controller.ApiGatewayController;
import com.lawfirm.api.service.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * API自动配置类
 * 确保API相关功能被正确加载
 */
@Configuration("apiAutoConfiguration")
@EnableConfigurationProperties
@Slf4j
@Import({
    RateLimitConfig.class,
    ApiVersionConfig.class
})
public class ApiAutoConfiguration {

    private final RedissonClient redissonClient;

    /**
     * 初始化方法，打印配置信息
     */
    public ApiAutoConfiguration(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        log.info("初始化API自动配置");
    }
    
    /**
     * 配置幂等性切面
     * 仅在幂等性功能启用时创建
     */
    @Bean
    @ConditionalOnProperty(name = "law-firm.api.idempotent.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(IdempotentAspect.class)
    @ConditionalOnBean(RedissonClient.class)
    public IdempotentAspect idempotentAspect() {
        log.info("初始化幂等性切面");
        return new IdempotentAspect(redissonClient);
    }
    
    /**
     * 配置防重复提交切面
     * 仅在防重复提交功能启用时创建
     */
    @Bean
    @ConditionalOnProperty(name = "law-firm.api.repeat-submit.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(RepeatSubmitPreventionAspect.class)
    @ConditionalOnBean(RedissonClient.class)
    public RepeatSubmitPreventionAspect repeatSubmitPreventionAspect() {
        log.info("初始化防重复提交切面");
        return new RepeatSubmitPreventionAspect(redissonClient);
    }
    
    /**
     * 配置分布式锁服务
     * 仅在分布式锁功能启用时创建
     */
    @Bean
    @ConditionalOnProperty(name = "law-firm.api.distributed-lock.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(DistributedLockService.class)
    @ConditionalOnBean(RedissonClient.class)
    public DistributedLockService distributedLockService() {
        log.info("初始化分布式锁服务");
        return new DistributedLockService(redissonClient);
    }
    
    /**
     * 配置API网关控制器
     */
    @Bean
    @ConditionalOnMissingBean(ApiGatewayController.class)
    public ApiGatewayController apiGatewayController() {
        log.info("初始化API网关控制器");
        return new ApiGatewayController();
    }
} 