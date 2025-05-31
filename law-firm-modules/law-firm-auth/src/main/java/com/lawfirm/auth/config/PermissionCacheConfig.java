package com.lawfirm.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

/**
 * 权限缓存配置类
 * <p>
 * 为权限检查提供缓存支持，提高系统性能。
 * 可以通过配置 law-firm.permission.cache.enabled=true 来启用权限缓存。
 * </p>
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(name = "law-firm.permission.cache.enabled", havingValue = "true", matchIfMissing = true)
public class PermissionCacheConfig {

    /**
     * 权限缓存管理器
     * <p>
     * 使用ConcurrentMapCacheManager作为默认缓存实现，
     * 生产环境建议使用Redis等分布式缓存。
     * </p>
     */
    @Bean("permissionCacheManager")
    public CacheManager permissionCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        
        // 预定义缓存名称
        cacheManager.setCacheNames(Arrays.asList(
            "userPermissions",      // 用户权限缓存
            "rolePermissions",      // 角色权限缓存
            "teamPermissions",      // 团队权限缓存
            "dataScopes",           // 数据范围缓存
            "permissionMatrix",     // 权限矩阵缓存
            "temporaryPermissions"  // 临时权限缓存
        ));
        
        // 允许运行时创建缓存
        cacheManager.setAllowNullValues(false);
        
        return cacheManager;
    }
}