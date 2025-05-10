package com.lawfirm.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 监控模块缓存配置
 * 
 * 职责：
 * 1. 提供监控系统专用的缓存管理器
 * 2. 不干扰基础缓存设施
 */
@Slf4j
@Configuration("monitorCacheConfig")
@EnableCaching
@Order(30) // 在应用缓存配置之后加载
public class MonitorCacheConfig {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 创建监控专用缓存管理器
     * 使用内存缓存，不依赖Redis
     */
    @Bean(name = "monitorCacheManager")
    @ConditionalOnMissingBean(name = "monitorCacheManager")
    public CacheManager monitorCacheManager() {
        log.info("初始化监控模块缓存管理器");
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("monitorCache")
        ));
        return cacheManager;
    }
    
    /**
     * 诊断Mapper Bean名称，在应用上下文刷新后执行
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("=== 检查所有Mapper Bean名称 ===");
        String[] mapperNames = applicationContext.getBeanNamesForAnnotation(org.apache.ibatis.annotations.Mapper.class);
        for (String name : mapperNames) {
            log.info("发现Mapper Bean: {}", name);
        }
    }
} 