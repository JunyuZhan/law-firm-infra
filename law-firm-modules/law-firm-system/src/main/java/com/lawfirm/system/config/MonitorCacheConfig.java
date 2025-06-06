package com.lawfirm.system.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * 监控模块缓存配置
 * 专门为系统监控功能提供缓存支持，确保监控数据快速访问
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
    @Bean(name = "systemMonitorCacheManager")
    @ConditionalOnMissingBean(name = "systemMonitorCacheManager")
    public CacheManager systemMonitorCacheManager() {
        log.info("✅ 初始化系统监控模块专用缓存管理器");
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("systemMonitorCache"),
                new ConcurrentMapCache("performanceMetricsCache"),
                new ConcurrentMapCache("healthCheckCache")
        ));
        log.info("系统监控缓存管理器初始化完成，包含3个缓存区域");
        return cacheManager;
    }
    
    /**
     * 诊断Mapper Bean名称，在应用上下文刷新后执行
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("=== 🔍 系统启动完成 - Bean状态检查 ===");
        
        // 检查Mapper Bean
        String[] mapperNames = applicationContext.getBeanNamesForAnnotation(org.apache.ibatis.annotations.Mapper.class);
        log.info("📊 已注册Mapper Bean总数: {}", mapperNames.length);
        
        // 检查重复的Mapper Bean（这里只记录，不会影响功能）
        boolean hasStorageMappers = Arrays.stream(mapperNames)
                .anyMatch(name -> name.toLowerCase().contains("storage"));
        
        if (hasStorageMappers) {
            log.info("🗂️ 检测到存储相关Mapper Bean（正常情况）");
        }
        
        // 检查缓存管理器
        String[] cacheManagers = applicationContext.getBeanNamesForType(CacheManager.class);
        log.info("💾 已注册缓存管理器数量: {}", cacheManagers.length);
        
        // 检查调度器
        String[] taskSchedulers = applicationContext.getBeanNamesForType(
                org.springframework.scheduling.TaskScheduler.class);
        log.info("⏰ 已注册任务调度器数量: {}", taskSchedulers.length);
        
        log.info("=== ✅ Bean状态检查完成 - 系统正常运行 ===");
    }
} 