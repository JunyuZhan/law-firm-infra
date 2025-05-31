package com.lawfirm.auth.security.evaluator;

import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.auth.service.BusinessPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 业务权限评估器
 * <p>
 * 实现Spring Security的PermissionEvaluator接口，用于处理@PreAuthorize注解中的hasPermission表达式。
 * 主要用于财务模块等需要细粒度权限控制的业务场景。
 * </p>
 * 
 * <p>支持的权限表达式格式：</p>
 * <ul>
 *   <li>hasPermission('resource', 'operation') - 检查用户对指定资源的操作权限</li>
 *   <li>hasPermission(targetId, 'targetType', 'operation') - 检查用户对特定目标对象的操作权限</li>
 * </ul>
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
@Slf4j
@Component("businessPermissionEvaluator")
@RequiredArgsConstructor
public class BusinessPermissionEvaluator implements PermissionEvaluator {

    private final BusinessPermissionService businessPermissionService;
    private final SecurityContext securityContext;

    /**
     * 检查用户对指定资源的操作权限
     * 
     * @param authentication 认证信息
     * @param targetDomainObject 目标资源（通常是资源类型字符串）
     * @param permission 权限操作（如：create、update、delete、view等）
     * @return 是否有权限
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || permission == null) {
            log.debug("权限检查参数为空，拒绝访问");
            return false;
        }

        try {
            Long userId = getCurrentUserId(authentication);
            if (userId == null) {
                log.debug("无法获取当前用户ID，拒绝访问");
                return false;
            }

            String resourceType = targetDomainObject.toString();
            String operation = permission.toString();
            
            log.debug("检查用户[{}]对资源[{}]的[{}]权限", userId, resourceType, operation);
            
            // 使用BusinessPermissionService进行权限检查
            boolean hasPermission = businessPermissionService.checkPermission(userId, resourceType, null, operation);
            
            log.debug("用户[{}]对资源[{}]的[{}]权限检查结果: {}", userId, resourceType, operation, hasPermission);
            return hasPermission;
            
        } catch (Exception e) {
            log.error("权限检查过程中发生异常", e);
            return false;
        }
    }

    /**
     * 检查用户对特定目标对象的操作权限
     * 
     * @param authentication 认证信息
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型
     * @param permission 权限操作
     * @return 是否有权限
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || targetId == null || targetType == null || permission == null) {
            log.debug("权限检查参数为空，拒绝访问");
            return false;
        }

        try {
            Long userId = getCurrentUserId(authentication);
            if (userId == null) {
                log.debug("无法获取当前用户ID，拒绝访问");
                return false;
            }

            Long businessId = null;
            if (targetId instanceof Number) {
                businessId = ((Number) targetId).longValue();
            } else {
                try {
                    businessId = Long.parseLong(targetId.toString());
                } catch (NumberFormatException e) {
                    log.warn("无法解析目标ID[{}]为数字", targetId);
                    return false;
                }
            }

            String operation = permission.toString();
            
            log.debug("检查用户[{}]对目标[{}:{}]的[{}]权限", userId, targetType, businessId, operation);
            
            // 使用BusinessPermissionService进行权限检查
            boolean hasPermission = businessPermissionService.checkPermission(userId, targetType, businessId, operation);
            
            log.debug("用户[{}]对目标[{}:{}]的[{}]权限检查结果: {}", userId, targetType, businessId, operation, hasPermission);
            return hasPermission;
            
        } catch (Exception e) {
            log.error("权限检查过程中发生异常", e);
            return false;
        }
    }

    /**
     * 从认证信息中获取当前用户ID
     * 
     * @param authentication 认证信息
     * @return 用户ID
     */
    private Long getCurrentUserId(Authentication authentication) {
        try {
            // 优先从SecurityContext获取用户ID
            Long userId = securityContext.getCurrentUserId();
            if (userId != null) {
                return userId;
            }
            
            // 如果SecurityContext中没有，尝试从Authentication中获取
            Object principal = authentication.getPrincipal();
            if (principal instanceof Number) {
                return ((Number) principal).longValue();
            } else if (principal instanceof String) {
                try {
                    return Long.parseLong((String) principal);
                } catch (NumberFormatException e) {
                    log.debug("无法将principal[{}]解析为用户ID", principal);
                }
            }
            
            log.debug("无法从认证信息中获取用户ID，principal类型: {}", 
                    principal != null ? principal.getClass().getSimpleName() : "null");
            return null;
            
        } catch (Exception e) {
            log.error("获取当前用户ID时发生异常", e);
            return null;
        }
    }
}