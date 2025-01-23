package com.lawfirm.core.workflow.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 工作流缓存配置
 */
@Configuration
@EnableCaching
public class WorkflowCacheConfig {

    /**
     * 缓存管理器
     */
    @Bean
    public CacheManager workflowCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        // 流程定义缓存配置
        cacheManager.registerCustomCache("processDefinitions",
                Caffeine.newBuilder()
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .maximumSize(1000)
                        .build());
        
        // 任务查询缓存配置
        cacheManager.registerCustomCache("taskQueries",
                Caffeine.newBuilder()
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .maximumSize(10000)
                        .build());
        
        // 流程实例缓存配置
        cacheManager.registerCustomCache("processInstances",
                Caffeine.newBuilder()
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .maximumSize(5000)
                        .build());
                        
        return cacheManager;
    }
    
    /**
     * 自定义缓存键生成器
     */
    @Bean
    public WorkflowCacheKeyGenerator workflowCacheKeyGenerator() {
        return new WorkflowCacheKeyGenerator();
    }
} 