package com.lawfirm.core.workflow.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * 工作流缓存配置
 * 
 * @author JunyuZhan
 */
@Configuration("workflowCacheConfig")
@EnableCaching
@ConditionalOnProperty(prefix = "lawfirm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class CacheConfig {
    
    /**
     * 流程缓存名称
     */
    public static final String PROCESS_CACHE = "process";
    
    /**
     * 任务缓存名称
     */
    public static final String TASK_CACHE = "task";
    
    /**
     * 表单缓存名称
     */
    public static final String FORM_CACHE = "form";
    
    /**
     * 流程模板缓存名称
     */
    public static final String PROCESS_TEMPLATE_CACHE = "process_template";
    
    /**
     * 缓存管理器
     * 
     * @return 缓存管理器
     */
    @Bean("workflowCacheManager")
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(Arrays.asList(
                PROCESS_CACHE,
                TASK_CACHE,
                FORM_CACHE,
                PROCESS_TEMPLATE_CACHE
        ));
        return cacheManager;
    }
} 
