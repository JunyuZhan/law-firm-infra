package com.lawfirm.auth.service.impl;

import com.lawfirm.auth.service.PermissionCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * 权限缓存服务实现类
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
@Slf4j
@Service("permissionCacheService")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.permission.cache.enabled", havingValue = "true", matchIfMissing = true)
public class PermissionCacheServiceImpl implements PermissionCacheService {

    private final CacheManager permissionCacheManager;

    @Override
    public void clearUserPermissionCache(Long userId) {
        if (userId == null) {
            log.warn("用户ID为空，无法清理权限缓存");
            return;
        }
        
        log.info("开始清理用户{}的权限缓存", userId);
        
        // 清理用户权限缓存
        clearCacheByKeyPattern("userPermissions", userId.toString());
        
        // 清理角色权限缓存
        clearCacheByKeyPattern("rolePermissions", userId.toString());
        
        // 清理数据范围缓存
        clearCacheByKeyPattern("dataScopes", userId.toString());
        
        // 清理临时权限缓存
        clearCacheByKeyPattern("temporaryPermissions", userId.toString());
        
        log.info("用户{}的权限缓存清理完成", userId);
    }

    @Override
    public void clearRolePermissionCache(Long roleId) {
        if (roleId == null) {
            log.warn("角色ID为空，无法清理权限缓存");
            return;
        }
        
        log.info("开始清理角色{}的权限缓存", roleId);
        
        // 角色权限变更会影响所有拥有该角色的用户，需要清理所有相关缓存
        clearAllPermissionCache();
        
        log.info("角色{}的权限缓存清理完成", roleId);
    }

    @Override
    public void clearTeamPermissionCache(Long teamId) {
        if (teamId == null) {
            log.warn("团队ID为空，无法清理权限缓存");
            return;
        }
        
        log.info("开始清理团队{}的权限缓存", teamId);
        
        // 清理团队权限缓存
        clearCacheByKeyPattern("teamPermissions", teamId.toString());
        
        // 团队权限变更可能影响数据范围，清理相关缓存
        Cache dataScopesCache = permissionCacheManager.getCache("dataScopes");
        if (dataScopesCache != null) {
            dataScopesCache.clear();
            log.debug("已清理数据范围缓存");
        }
        
        log.info("团队{}的权限缓存清理完成", teamId);
    }

    @Override
    public void clearBusinessPermissionCache(String businessType) {
        if (businessType == null || businessType.trim().isEmpty()) {
            log.warn("业务类型为空，无法清理权限缓存");
            return;
        }
        
        log.info("开始清理业务类型{}的权限缓存", businessType);
        
        // 清理与该业务类型相关的所有缓存
        clearCacheByKeyPattern("userPermissions", businessType);
        clearCacheByKeyPattern("rolePermissions", businessType);
        clearCacheByKeyPattern("teamPermissions", businessType);
        clearCacheByKeyPattern("dataScopes", businessType);
        
        log.info("业务类型{}的权限缓存清理完成", businessType);
    }

    @Override
    public void clearAllPermissionCache() {
        log.info("开始清理所有权限缓存");
        
        String[] cacheNames = {
            "userPermissions", 
            "rolePermissions", 
            "teamPermissions", 
            "dataScopes", 
            "permissionMatrix", 
            "temporaryPermissions"
        };
        
        for (String cacheName : cacheNames) {
            Cache cache = permissionCacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
                log.debug("已清理缓存: {}", cacheName);
            }
        }
        
        log.info("所有权限缓存清理完成");
    }

    @Override
    public void warmUpUserPermissionCache(Long userId) {
        if (userId == null) {
            log.warn("用户ID为空，无法预热权限缓存");
            return;
        }
        
        log.info("开始为用户{}预热权限缓存", userId);
        
        // 这里可以预加载用户的常用权限
        // 具体实现需要根据业务需求来定制
        
        log.info("用户{}的权限缓存预热完成", userId);
    }

    @Override
    public String getCacheStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("权限缓存统计信息:\n");
        
        String[] cacheNames = {
            "userPermissions", 
            "rolePermissions", 
            "teamPermissions", 
            "dataScopes", 
            "permissionMatrix", 
            "temporaryPermissions"
        };
        
        for (String cacheName : cacheNames) {
            Cache cache = permissionCacheManager.getCache(cacheName);
            if (cache != null) {
                Object nativeCache = cache.getNativeCache();
                if (nativeCache instanceof ConcurrentMap) {
                    ConcurrentMap<?, ?> map = (ConcurrentMap<?, ?>) nativeCache;
                    stats.append(String.format("- %s: %d 条记录\n", cacheName, map.size()));
                } else {
                    stats.append(String.format("- %s: 缓存类型不支持统计\n", cacheName));
                }
            } else {
                stats.append(String.format("- %s: 缓存不存在\n", cacheName));
            }
        }
        
        return stats.toString();
    }

    /**
     * 根据键模式清理缓存
     * 
     * @param cacheName 缓存名称
     * @param keyPattern 键模式
     */
    private void clearCacheByKeyPattern(String cacheName, String keyPattern) {
        Cache cache = permissionCacheManager.getCache(cacheName);
        if (cache == null) {
            log.debug("缓存{}不存在，跳过清理", cacheName);
            return;
        }
        
        Object nativeCache = cache.getNativeCache();
        if (nativeCache instanceof ConcurrentMap) {
            ConcurrentMap<Object, Object> map = (ConcurrentMap<Object, Object>) nativeCache;
            
            // 找到所有匹配的键并删除
            map.keySet().removeIf(key -> {
                String keyStr = key.toString();
                boolean matches = keyStr.contains(keyPattern);
                if (matches) {
                    log.debug("清理缓存项: {} -> {}", cacheName, keyStr);
                }
                return matches;
            });
        } else {
            // 如果不是ConcurrentMap，直接清空整个缓存
            cache.clear();
            log.debug("清理整个缓存: {}", cacheName);
        }
    }
}