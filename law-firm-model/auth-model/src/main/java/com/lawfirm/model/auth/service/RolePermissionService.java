package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.auth.entity.RolePermission;

import java.util.List;

/**
 * 角色-权限关联服务接口
 */
public interface RolePermissionService extends IService<RolePermission> {
    
    /**
     * 通过角色ID查询角色权限关联
     * 
     * @param roleId 角色ID
     * @return 角色权限关联列表
     */
    List<RolePermission> getByRoleId(Long roleId);
    
    /**
     * 通过权限ID查询角色权限关联
     * 
     * @param permissionId 权限ID
     * @return 角色权限关联列表
     */
    List<RolePermission> getByPermissionId(Long permissionId);
    
    /**
     * 创建角色权限关联
     * 
     * @param rolePermission 角色权限关联实体
     * @return 是否创建成功
     */
    boolean createRolePermission(RolePermission rolePermission);
    
    /**
     * 批量创建角色权限关联
     * 
     * @param rolePermissions 角色权限关联实体列表
     * @return 是否创建成功
     */
    boolean batchCreateRolePermission(List<RolePermission> rolePermissions);
    
    /**
     * 删除角色权限关联
     * 
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 是否删除成功
     */
    boolean deleteRolePermission(Long roleId, Long permissionId);
    
    /**
     * 删除角色的所有权限关联
     * 
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    boolean deleteByRoleId(Long roleId);
    
    /**
     * 删除权限的所有角色关联
     * 
     * @param permissionId 权限ID
     * @return 是否删除成功
     */
    boolean deleteByPermissionId(Long permissionId);
    
    /**
     * 检查角色是否拥有指定权限
     * 
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 是否拥有权限
     */
    boolean hasPermission(Long roleId, Long permissionId);
    
    /**
     * 获取角色的所有权限ID
     * 
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getPermissionIdsByRoleId(Long roleId);
    
    /**
     * 分配角色权限
     * 
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 是否分配成功
     */
    boolean assignPermissions(Long roleId, List<Long> permissionIds);
} 