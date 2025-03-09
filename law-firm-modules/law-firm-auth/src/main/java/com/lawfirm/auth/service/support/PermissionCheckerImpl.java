package com.lawfirm.auth.service.support;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.entity.RolePermission;
import com.lawfirm.model.auth.entity.UserRole;
import com.lawfirm.model.auth.enums.DataScopeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import com.lawfirm.model.auth.mapper.RoleMapper;
import com.lawfirm.model.auth.mapper.RolePermissionMapper;
import com.lawfirm.model.auth.mapper.UserRoleMapper;
import com.lawfirm.model.auth.service.PermissionChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限检查器实现
 * 用于检查用户对特定模块的操作权限和数据范围
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionCheckerImpl implements PermissionChecker {

    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;

    /**
     * 操作类型权限级别，从高到低
     * 完整权限 > 审批权限 > 创建权限 > 只读权限 > 申请权限
     */
    private static final List<String> OPERATION_LEVELS = List.of(
            "full",      // 完整权限（增删改查）
            "approve",   // 审批权限
            "create",    // 创建权限
            "read_only", // 只读权限
            "apply"      // 申请权限
    );

    /**
     * 数据范围级别，从高到低
     * 全所 > 部门全权 > 团队 > 部门相关 > 个人
     */
    private static final List<String> DATA_SCOPE_LEVELS = List.of(
            "all",           // 全所数据
            "department_full", // 部门全权数据
            "team",         // 团队数据
            "department_related", // 部门相关数据
            "personal"      // 个人数据
    );

    @Override
    public boolean hasPermission(Long userId, String moduleCode, OperationTypeEnum operationType) {
        // 获取用户角色
        List<Role> roles = getUserRoles(userId);
        if (CollectionUtils.isEmpty(roles)) {
            log.debug("用户没有角色，无权限访问，userId: {}", userId);
            return false;
        }

        // 获取角色ID列表
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        // 查询角色关联的权限
        LambdaQueryWrapper<RolePermission> rpQueryWrapper = new LambdaQueryWrapper<>();
        rpQueryWrapper.in(RolePermission::getRoleId, roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rpQueryWrapper);

        if (CollectionUtils.isEmpty(rolePermissions)) {
            log.debug("角色没有关联权限，无权限访问，roleIds: {}", roleIds);
            return false;
        }

        // 获取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 查询权限
        LambdaQueryWrapper<Permission> pQueryWrapper = new LambdaQueryWrapper<>();
        pQueryWrapper.in(Permission::getId, permissionIds)
                .eq(Permission::getCode, moduleCode);
        
        // 获取所有匹配的权限
        List<Permission> permissions = permissionMapper.selectList(pQueryWrapper);
        
        if (CollectionUtils.isEmpty(permissions)) {
            log.debug("未找到匹配的权限，无权限访问，moduleCode: {}", moduleCode);
            return false;
        }
        
        // 检查是否有足够的操作权限
        for (Permission permission : permissions) {
            // 获取权限的操作类型
            Integer permissionOperationType = permission.getOperationType();
            if (permissionOperationType == null) {
                continue;
            }
            
            // 将整数值转换为枚举值
            OperationTypeEnum permissionOperation = OperationTypeEnum.values()[permissionOperationType];
            
            // 使用枚举的hasPermission方法判断是否有权限
            if (permissionOperation.hasPermission(operationType)) {
                return true;
            }
        }
        
        log.debug("没有足够的操作权限，moduleCode: {}, operationType: {}", moduleCode, operationType);
        return false;
    }

    @Override
    public DataScopeEnum getDataScope(Long userId, String moduleCode) {
        // 获取用户角色
        List<Role> roles = getUserRoles(userId);
        if (CollectionUtils.isEmpty(roles)) {
            log.debug("用户没有角色，使用默认数据范围，userId: {}", userId);
            return DataScopeEnum.PERSONAL; // 默认个人数据范围
        }

        // 获取角色ID列表
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        // 查询角色关联的权限
        LambdaQueryWrapper<RolePermission> rpQueryWrapper = new LambdaQueryWrapper<>();
        rpQueryWrapper.in(RolePermission::getRoleId, roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rpQueryWrapper);

        if (CollectionUtils.isEmpty(rolePermissions)) {
            log.debug("角色没有关联权限，使用默认数据范围，roleIds: {}", roleIds);
            return DataScopeEnum.PERSONAL; // 默认个人数据范围
        }

        // 获取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 查询权限
        LambdaQueryWrapper<Permission> pQueryWrapper = new LambdaQueryWrapper<>();
        pQueryWrapper.in(Permission::getId, permissionIds)
                .eq(Permission::getCode, moduleCode)
                .orderByAsc(Permission::getDataScope); // 按数据范围升序排序，取最大权限（序号越小，范围越大）

        List<Permission> permissions = permissionMapper.selectList(pQueryWrapper);
        if (CollectionUtils.isEmpty(permissions)) {
            log.debug("未找到模块权限，使用默认数据范围，moduleCode: {}", moduleCode);
            return DataScopeEnum.PERSONAL; // 默认个人数据范围
        }

        // 找出最高的数据范围权限
        DataScopeEnum highestDataScope = DataScopeEnum.PERSONAL; // 默认个人数据范围
        
        for (Permission permission : permissions) {
            Integer dataScope = permission.getDataScope();
            if (dataScope != null) {
                // 将整数值转换为枚举值
                DataScopeEnum scopeEnum = DataScopeEnum.getByValue(dataScope);
                if (scopeEnum != null && scopeEnum.ordinal() < highestDataScope.ordinal()) {
                    // 序号越小，范围越大
                    highestDataScope = scopeEnum;
                }
            }
        }
        
        return highestDataScope;
    }

    @Override
    public boolean hasRole(Long userId, String roleCode) {
        // 获取用户角色
        List<Role> roles = getUserRoles(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        // 检查是否包含指定角色
        return roles.stream()
                .anyMatch(role -> roleCode.equals(role.getCode()));
    }

    @Override
    public boolean hasPermission(Long userId, String permissionCode) {
        // 获取用户角色
        List<Role> roles = getUserRoles(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        // 获取角色ID列表
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        // 查询角色关联的权限
        LambdaQueryWrapper<RolePermission> rpQueryWrapper = new LambdaQueryWrapper<>();
        rpQueryWrapper.in(RolePermission::getRoleId, roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rpQueryWrapper);

        if (CollectionUtils.isEmpty(rolePermissions)) {
            return false;
        }

        // 获取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 查询权限
        LambdaQueryWrapper<Permission> pQueryWrapper = new LambdaQueryWrapper<>();
        pQueryWrapper.in(Permission::getId, permissionIds)
                .eq(Permission::getCode, permissionCode);

        return permissionMapper.selectCount(pQueryWrapper) > 0;
    }

    /**
     * 获取用户角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    private List<Role> getUserRoles(Long userId) {
        // 查询用户角色关联
        LambdaQueryWrapper<UserRole> urQueryWrapper = new LambdaQueryWrapper<>();
        urQueryWrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleMapper.selectList(urQueryWrapper);

        if (CollectionUtils.isEmpty(userRoles)) {
            return new ArrayList<>();
        }

        // 获取角色ID列表
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 查询角色
        LambdaQueryWrapper<Role> rQueryWrapper = new LambdaQueryWrapper<>();
        rQueryWrapper.in(Role::getId, roleIds)
                .eq(Role::getStatus, 0); // 只查询正常状态的角色

        return roleMapper.selectList(rQueryWrapper);
    }
} 