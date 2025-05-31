package com.lawfirm.auth.controller;

import com.lawfirm.auth.constant.AuthApiConstants;
import com.lawfirm.auth.service.PermissionCacheService;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.constant.PermissionConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限管理控制器
 * <p>
 * 提供权限验证、缓存管理等功能的API接口。
 * 只有在启用权限管理功能时才会注册此控制器。
 * </p>
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(AuthApiConstants.Api.PERMISSION_MANAGEMENT)
@RequiredArgsConstructor
@Tag(name = "权限管理", description = "权限验证和缓存管理API")
@ConditionalOnProperty(name = "law-firm.permission.management.enabled", havingValue = "true")
public class PermissionManagementController {

    private final PermissionCacheService permissionCacheService;

    /**
     * 清理指定用户的权限缓存
     */
    @PostMapping("/cache/clear/user/{userId}")
    @Operation(summary = "清理用户权限缓存", description = "清理指定用户的所有权限相关缓存")
    @PreAuthorize("hasAuthority('" + PermissionConstants.SYS_PERMISSION_CACHE_CLEAR + "')")
    public CommonResult<Void> clearUserCache(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId) {
        
        log.info("管理员请求清理用户{}的权限缓存", userId);
        
        try {
            permissionCacheService.clearUserPermissionCache(userId);
            return CommonResult.success("用户权限缓存清理成功");
        } catch (Exception e) {
            log.error("清理用户权限缓存失败", e);
            return CommonResult.error("清理用户权限缓存失败: " + e.getMessage());
        }
    }

    /**
     * 清理指定角色的权限缓存
     */
    @PostMapping("/cache/clear/role/{roleId}")
    @Operation(summary = "清理角色权限缓存", description = "清理指定角色的所有权限相关缓存")
    @PreAuthorize("hasAuthority('" + PermissionConstants.SYS_PERMISSION_CACHE_CLEAR + "')")
    public CommonResult<Void> clearRoleCache(
            @Parameter(description = "角色ID", required = true)
            @PathVariable Long roleId) {
        
        log.info("管理员请求清理角色{}的权限缓存", roleId);
        
        try {
            permissionCacheService.clearRolePermissionCache(roleId);
            return CommonResult.success("角色权限缓存清理成功");
        } catch (Exception e) {
            log.error("清理角色权限缓存失败", e);
            return CommonResult.error("清理角色权限缓存失败: " + e.getMessage());
        }
    }

    /**
     * 清理指定团队的权限缓存
     */
    @PostMapping("/cache/clear/team/{teamId}")
    @Operation(summary = "清理团队权限缓存", description = "清理指定团队的所有权限相关缓存")
    @PreAuthorize("hasAuthority('" + PermissionConstants.SYS_PERMISSION_CACHE_CLEAR + "')")
    public CommonResult<Void> clearTeamCache(
            @Parameter(description = "团队ID", required = true)
            @PathVariable Long teamId) {
        
        log.info("管理员请求清理团队{}的权限缓存", teamId);
        
        try {
            permissionCacheService.clearTeamPermissionCache(teamId);
            return CommonResult.success("团队权限缓存清理成功");
        } catch (Exception e) {
            log.error("清理团队权限缓存失败", e);
            return CommonResult.error("清理团队权限缓存失败: " + e.getMessage());
        }
    }

    /**
     * 清理指定业务类型的权限缓存
     */
    @PostMapping("/cache/clear/business/{businessType}")
    @Operation(summary = "清理业务权限缓存", description = "清理指定业务类型的所有权限相关缓存")
    @PreAuthorize("hasAuthority('" + PermissionConstants.SYS_PERMISSION_CACHE_CLEAR + "')")
    public CommonResult<Void> clearBusinessCache(
            @Parameter(description = "业务类型", required = true)
            @PathVariable String businessType) {
        
        log.info("管理员请求清理业务类型{}的权限缓存", businessType);
        
        try {
            permissionCacheService.clearBusinessPermissionCache(businessType);
            return CommonResult.success("业务权限缓存清理成功");
        } catch (Exception e) {
            log.error("清理业务权限缓存失败", e);
            return CommonResult.error("清理业务权限缓存失败: " + e.getMessage());
        }
    }

    /**
     * 清理所有权限缓存
     */
    @PostMapping("/cache/clear/all")
    @Operation(summary = "清理所有权限缓存", description = "清理系统中所有的权限相关缓存")
    @PreAuthorize("hasAuthority('" + PermissionConstants.SYS_PERMISSION_CACHE_CLEAR + "')")
    public CommonResult<Void> clearAllCache() {
        
        log.info("管理员请求清理所有权限缓存");
        
        try {
            permissionCacheService.clearAllPermissionCache();
            return CommonResult.success("所有权限缓存清理成功");
        } catch (Exception e) {
            log.error("清理所有权限缓存失败", e);
            return CommonResult.error("清理所有权限缓存失败: " + e.getMessage());
        }
    }

    /**
     * 预热用户权限缓存
     */
    @PostMapping("/cache/warmup/user/{userId}")
    @Operation(summary = "预热用户权限缓存", description = "为指定用户预加载权限缓存")
    @PreAuthorize("hasAuthority('" + PermissionConstants.SYS_PERMISSION_CACHE_MANAGE + "')")
    public CommonResult<Void> warmUpUserCache(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId) {
        
        log.info("管理员请求为用户{}预热权限缓存", userId);
        
        try {
            permissionCacheService.warmUpUserPermissionCache(userId);
            return CommonResult.success("用户权限缓存预热成功");
        } catch (Exception e) {
            log.error("预热用户权限缓存失败", e);
            return CommonResult.error("预热用户权限缓存失败: " + e.getMessage());
        }
    }

    /**
     * 获取权限缓存统计信息
     */
    @GetMapping("/cache/statistics")
    @Operation(summary = "获取缓存统计", description = "获取权限缓存的统计信息")
    @PreAuthorize("hasAuthority('" + PermissionConstants.SYS_PERMISSION_VIEW + "')")
    public CommonResult<String> getCacheStatistics() {
        
        try {
            String statistics = permissionCacheService.getCacheStatistics();
            return CommonResult.success(statistics, "获取缓存统计成功");
        } catch (Exception e) {
            log.error("获取缓存统计失败", e);
            return CommonResult.error("获取缓存统计失败: " + e.getMessage());
        }
    }

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    @Operation(summary = "权限系统健康检查", description = "检查权限系统的运行状态")
    public CommonResult<Map<String, Object>> healthCheck() {
        try {
            // 简单的健康检查逻辑
            String statistics = permissionCacheService.getCacheStatistics();
            
            Map<String, Object> data = new HashMap<>();
            data.put("status", "UP");
            data.put("timestamp", System.currentTimeMillis());
            data.put("cacheInfo", statistics);
            
            return CommonResult.success(data, "权限系统运行正常");
        } catch (Exception e) {
            log.error("权限系统健康检查失败", e);
            return CommonResult.error("权限系统异常: " + e.getMessage());
        }
    }
}