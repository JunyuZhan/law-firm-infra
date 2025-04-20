package com.lawfirm.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.NamedCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

/**
 * API模块缓存解析器配置
 * <p>
 * 确保monitorCache能被系统监控服务正确找到
 * </p>
 */
@Slf4j
@Configuration
@EnableCaching
public class ApiCacheResolverConfig {

    /**
     * 明确使用指定的缓存管理器，避免多个@Primary缓存管理器冲突
     */
    @Autowired
    @Qualifier("monitorCacheManager")
    private CacheManager monitorCacheManager;
    
    /**
     * 创建默认缓存解析器
     * <p>
     * 确保所有缓存注解操作能够找到正确的缓存
     * </p>
     */
    @Bean
    @Primary
    public CacheResolver cacheResolver() {
        log.info("创建API层默认缓存解析器");
        NamedCacheResolver resolver = new NamedCacheResolver();
        resolver.setCacheManager(monitorCacheManager);
        return resolver;
    }
    
    /**
     * 专门为系统监控创建的缓存解析器
     * <p>
     * 显式指定monitorCache确保监控服务能找到它
     * </p>
     */
    @Bean(name = "monitorCacheResolver") 
    public CacheResolver monitorCacheResolver() {
        log.info("创建监控专用缓存解析器，强制指定monitorCache");
        NamedCacheResolver resolver = new NamedCacheResolver();
        resolver.setCacheManager(monitorCacheManager);
        resolver.setCacheNames(Arrays.asList("monitorCache"));
        return resolver;
    }
} 