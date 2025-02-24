package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.base.service.BaseService;

import java.util.List;

/**
 * 权限服务接口
 */
public interface PermissionService extends BaseService<Permission> {
    
    /**
     * 创建权限
     *
     * @param dto 权限DTO
     * @return 权限ID
     */
    Long createPermission(BaseDTO dto);
    
    /**
     * 更新权限
     *
     * @param id 权限ID
     * @param dto 权限DTO
     */
    void updatePermission(Long id, BaseDTO dto);
    
    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    void deletePermission(Long id);
    
    /**
     * 批量删除权限
     *
     * @param ids 权限ID列表
     */
    void deletePermissions(List<Long> ids);
    
    /**
     * 获取权限详情
     *
     * @param id 权限ID
     * @return 权限视图对象
     */
    PermissionVO getPermissionById(Long id);
    
    /**
     * 分页查询权限
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    Page<PermissionVO> pagePermissions(BaseDTO dto);
    
    /**
     * 获取权限树
     *
     * @param dto 查询条件
     * @return 权限树
     */
    List<PermissionVO> getPermissionTree(BaseDTO dto);
    
    /**
     * 根据编码获取权限
     *
     * @param code 权限编码
     * @return 权限实体
     */
    Permission getByCode(String code);
    
    /**
     * 更新权限状态
     *
     * @param id 权限ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);
    
    /**
     * 获取角色权限列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<PermissionVO> getRolePermissions(Long roleId);
    
    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<PermissionVO> getUserPermissions(Long userId);
    
    /**
     * 获取用户菜单树
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<PermissionVO> getUserMenuTree(Long userId);
} 