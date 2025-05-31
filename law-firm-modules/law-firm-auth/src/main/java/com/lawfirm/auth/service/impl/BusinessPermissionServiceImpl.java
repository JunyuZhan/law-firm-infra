package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.auth.dto.permission.PermissionApprovalDTO;
import com.lawfirm.model.auth.dto.permission.PermissionRequestDTO;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.entity.TeamPermission;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import com.lawfirm.model.auth.service.BusinessPermissionService;
import com.lawfirm.model.auth.service.PermissionRequestService;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.service.RoleService;
import com.lawfirm.model.auth.service.TeamPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务权限服务实现类
 */
@Slf4j
@Service("businessPermissionService")
@RequiredArgsConstructor
public class BusinessPermissionServiceImpl implements BusinessPermissionService {

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final TeamPermissionService teamPermissionService;
    private final PermissionRequestService permissionRequestService;
    private final PermissionMapper permissionMapper;
    
    @Override
    @Cacheable(value = "userPermissions", key = "#userId + ':' + #businessType + ':' + #businessId + ':' + #operation", 
               cacheManager = "permissionCacheManager", unless = "#result == false")
    public boolean checkPermission(Long userId, String businessType, Long businessId, String operation) {
        log.debug("检查用户权限: userId={}, businessType={}, businessId={}, operation={}", 
                 userId, businessType, businessId, operation);
        
        // 1. 检查用户角色权限
        if (checkRolePermission(userId, businessType, operation)) {
            log.debug("用户{}通过角色权限检查", userId);
            return true;
        }
        
        // 2. 检查用户团队权限
        if (checkTeamPermission(userId, businessType, businessId, operation)) {
            log.debug("用户{}通过团队权限检查", userId);
            return true;
        }
        
        // 3. 检查用户临时权限
        String permissionCode = buildPermissionCode(businessType, operation);
        boolean hasTemporary = permissionRequestService.hasTemporaryPermission(userId, permissionCode, businessType, businessId);
        if (hasTemporary) {
            log.debug("用户{}通过临时权限检查", userId);
        } else {
            log.debug("用户{}权限检查失败", userId);
        }
        return hasTemporary;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long requestTemporaryPermission(Long userId, String businessType, Long businessId, 
                                          String permissionCode, String reason) {
        PermissionRequestDTO dto = new PermissionRequestDTO();
        dto.setBusinessType(businessType);
        dto.setBusinessId(businessId);
        dto.setPermissionCode(permissionCode);
        dto.setReason(reason);
        
        return permissionRequestService.createRequest(userId, dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approvePermissionRequest(Long approverId, Long requestId, boolean approved, String remark) {
        PermissionApprovalDTO dto = new PermissionApprovalDTO();
        dto.setRequestId(requestId);
        dto.setApproved(approved);
        dto.setRemark(remark);
        
        return permissionRequestService.approveRequest(approverId, dto);
    }

    @Override
    public boolean checkDataScope(Long userId, int dataScope, String businessType, Long businessId) {
        // 获取用户在这个业务对象上的最大数据范围
        int userDataScope = getUserDataScope(userId, businessType, businessId);
        
        // 数据范围值越小，权限越大（0=全所，1=团队，2=个人）
        return userDataScope <= dataScope;
    }

    @Override
    @Cacheable(value = "dataScopes", key = "#userId + ':' + #businessType + ':' + #businessId", 
               cacheManager = "permissionCacheManager")
    public int getUserDataScope(Long userId, String businessType, Long businessId) {
        log.debug("获取用户数据范围: userId={}, businessType={}, businessId={}", 
                 userId, businessType, businessId);
        
        // 1. 先检查用户是否有全所范围的权限
        if (hasGlobalScope(userId)) {
            log.debug("用户{}具有全所数据范围权限", userId);
            return Permission.DATA_SCOPE_GLOBAL;
        }
        
        // 2. 再检查用户是否有团队范围的权限
        if (hasTeamScope(userId, businessType, businessId)) {
            log.debug("用户{}具有团队数据范围权限", userId);
            return Permission.DATA_SCOPE_TEAM;
        }
        
        // 3. 默认只有个人范围
        log.debug("用户{}只有个人数据范围权限", userId);
        return Permission.DATA_SCOPE_PERSONAL;
    }
    
    /**
     * 检查用户角色权限
     */
    @Cacheable(value = "rolePermissions", key = "#userId + ':' + #businessType + ':' + #operation", 
               cacheManager = "permissionCacheManager")
    private boolean checkRolePermission(Long userId, String businessType, String operation) {
        String permissionCode = buildPermissionCode(businessType, operation);
        List<String> userPermissions = permissionService.listPermissionCodesByUserId(userId);
        boolean hasPermission = userPermissions.contains(permissionCode);
        log.debug("用户{}角色权限检查结果: permissionCode={}, hasPermission={}", 
                 userId, permissionCode, hasPermission);
        return hasPermission;
    }
    
    /**
     * 检查用户团队权限
     */
    private boolean checkTeamPermission(Long userId, String businessType, Long businessId, String operation) {
        // 这里需要结合具体业务实现
        // 例如，检查用户是否是该业务对象所属团队的成员，以及团队是否有相应权限
        return false;
    }
    
    /**
     * 检查用户是否有全所范围的权限
     */
    private boolean hasGlobalScope(Long userId) {
        // 实现逻辑：检查用户角色是否包含系统管理员、律所主任等具有全所数据权限的角色
        return roleService.hasAdminRole(userId);
    }
    
    /**
     * 检查用户是否有团队范围的权限
     */
    private boolean hasTeamScope(Long userId, String businessType, Long businessId) {
        // 实现逻辑：检查用户是否是该业务对象所属团队的负责人或成员
        return false;
    }
    
    /**
     * 构建权限编码
     */
    private String buildPermissionCode(String businessType, String operation) {
        return businessType.toLowerCase() + ":" + operation.toLowerCase();
    }

    @Override
    public boolean checkUserHasTeamPermission(Long userId, Long teamId, String resourceType) {
        if (userId == null || teamId == null || resourceType == null) {
            return false;
        }
        
        // 1. 首先检查用户是否是管理员，管理员拥有所有权限
        if (roleService.hasAdminRole(userId)) {
            return true;
        }
        
        // 2. 使用TeamPermissionService的hasUserTeamResourcePermission方法检查
        return teamPermissionService.hasUserTeamResourcePermission(teamId, userId, resourceType);
    }

    @Override
    public boolean checkUserHasTeamPermission(Long userId, Long teamId, List<String> resourceTypes) {
        if (userId == null || teamId == null || resourceTypes == null || resourceTypes.isEmpty()) {
            return false;
        }
        
        // 1. 首先检查用户是否是管理员，管理员拥有所有权限
        if (roleService.hasAdminRole(userId)) {
            return true;
        }
        
        // 2. 检查用户在该团队中是否具有任一资源类型的权限
        for (String resourceType : resourceTypes) {
            if (teamPermissionService.hasUserTeamResourcePermission(teamId, userId, resourceType)) {
                return true;
            }
        }
        
        return false;
    }
}