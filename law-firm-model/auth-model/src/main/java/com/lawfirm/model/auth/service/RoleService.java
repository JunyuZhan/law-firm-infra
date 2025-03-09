package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.dto.role.RoleCreateDTO;
import com.lawfirm.model.auth.dto.role.RoleUpdateDTO;
import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.vo.RoleVO;
import com.lawfirm.model.base.service.BaseService;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService extends BaseService<Role> {
    
    /**
     * 创建角色
     *
     * @param createDTO 角色创建DTO
     * @return 角色ID
     */
    Long createRole(RoleCreateDTO createDTO);
    
    /**
     * 更新角色
     *
     * @param id 角色ID
     * @param createDTO 角色创建DTO
     */
    void updateRole(Long id, RoleCreateDTO createDTO);
    
    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 是否删除成功
     */
    boolean deleteRole(Long id);
    
    /**
     * 批量删除角色
     *
     * @param ids 角色ID列表
     */
    void deleteRoles(List<Long> ids);
    
    /**
     * 获取角色详情
     *
     * @param id 角色ID
     * @return 角色视图对象
     */
    RoleVO getRoleById(Long id);
    
    /**
     * 分页查询角色
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    Page<RoleVO> pageRoles(RoleUpdateDTO queryDTO);
    
    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    List<RoleVO> listAllRoles();
    
    /**
     * 根据编码获取角色
     *
     * @param code 角色编码
     * @return 角色实体
     */
    Role getByCode(String code);
    
    /**
     * 更新角色状态
     *
     * @param id 角色ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);
    
    /**
     * 分配权限
     *
     * @param id 角色ID
     * @param permissionIds 权限ID列表
     */
    void assignPermissions(Long id, List<Long> permissionIds);
    
    /**
     * 获取角色权限ID列表
     *
     * @param id 角色ID
     * @return 权限ID列表
     */
    List<Long> getRolePermissionIds(Long id);
    
    /**
     * 获取角色用户ID列表
     *
     * @param id 角色ID
     * @return 用户ID列表
     */
    List<Long> getRoleUserIds(Long id);
    
    /**
     * 分页查询角色
     *
     * @param current 当前页
     * @param size 每页大小
     * @param roleName 角色名称
     * @return 分页结果
     */
    Page<Role> getRolePage(Long current, Long size, String roleName);
    
    /**
     * 创建角色
     *
     * @param role 角色实体
     * @return 是否创建成功
     */
    boolean createRole(Role role);
    
    /**
     * 更新角色
     *
     * @param role 角色实体
     * @return 是否更新成功
     */
    boolean updateRole(Role role);
    
    /**
     * 根据用户ID获取角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> getRolesByUserId(Long userId);
    
    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    List<Role> getAllRoles();
} 