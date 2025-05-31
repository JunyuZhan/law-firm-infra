package com.lawfirm.auth.config;

import java.util.Arrays;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 权限矩阵缓存配置
 * 配置权限矩阵相关的缓存管理
 *
 * @author System
 */
@Configuration
@EnableCaching
public class PermissionMatrixCacheConfig {

    /**
     * 权限矩阵缓存管理器
     * 使用内存缓存，适合权限矩阵这种相对稳定的数据
     */
    @Bean("permissionMatrixCacheManager")
    public CacheManager permissionMatrixCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        
        // 预创建权限矩阵相关的缓存
        cacheManager.setCacheNames(Arrays.asList(
            "permissionMatrix",           // 完整权限矩阵缓存
            "userPermissionMatrix",      // 用户权限矩阵缓存
            "rolePermissionMatrix",      // 角色权限矩阵缓存
            "userDataScope",             // 用户数据范围缓存
            "userAccessibleModules",     // 用户可访问模块缓存
            "moduleOperations"           // 模块操作缓存
        ));
        
        // 允许运行时创建新的缓存
        cacheManager.setAllowNullValues(false);
        
        return cacheManager;
    }
}