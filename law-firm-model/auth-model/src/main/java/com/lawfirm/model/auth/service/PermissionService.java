package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.auth.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.auth.vo.RouterVO;
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
     * @param createDTO 创建参数
     * @return 权限ID
     */
    Long createPermission(PermissionCreateDTO createDTO);
    
    /**
     * 更新权限
     *
     * @param id        权限ID
     * @param updateDTO 更新参数
     */
    void updatePermission(Long id, PermissionUpdateDTO updateDTO);
    
    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    void deletePermission(Long id);
    
    /**
     * 获取权限详情
     *
     * @param id 权限ID
     * @return 权限视图对象
     */
    PermissionVO getPermissionById(Long id);
    
    /**
     * 获取所有权限列表
     *
     * @return 权限列表
     */
    List<PermissionVO> listAllPermissions();
    
    /**
     * 获取权限树结构
     *
     * @return 权限树列表
     */
    List<PermissionVO> getPermissionTree();
    
    /**
     * 根据角色ID获取权限列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<PermissionVO> listPermissionsByRoleId(Long roleId);
    
    /**
     * 根据用户ID获取权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<PermissionVO> listPermissionsByUserId(Long userId);
    
    /**
     * 根据用户ID获取权限编码列表
     *
     * @param userId 用户ID
     * @return 权限编码列表
     */
    List<String> listPermissionCodesByUserId(Long userId);
    
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
     * 获取用户菜单树
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<PermissionVO> getUserMenuTree(Long userId);
    
    /**
     * 获取用户的前端路由配置
     *
     * @param userId 用户ID
     * @return 路由配置列表
     */
    List<RouterVO> getUserRouters(Long userId);
} 