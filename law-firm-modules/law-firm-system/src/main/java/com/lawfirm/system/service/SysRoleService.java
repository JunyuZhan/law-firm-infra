package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.system.model.entity.SysRole;
import com.lawfirm.system.model.dto.SysRoleDTO;

import java.util.List;

/**
 * 角色服务接口
 */
public interface SysRoleService extends BaseService<SysRole, SysRoleDTO> {

    /**
     * 创建角色
     */
    @OperationLog(description = "创建角色", operationType = "CREATE")
    SysRoleDTO createRole(SysRoleDTO role);

    /**
     * 更新角色
     */
    @OperationLog(description = "更新角色", operationType = "UPDATE")
    SysRoleDTO updateRole(SysRoleDTO role);

    /**
     * 删除角色
     */
    @OperationLog(description = "删除角色", operationType = "DELETE")
    void deleteRole(Long id);

    /**
     * 根据角色编码查询角色
     */
    SysRoleDTO getByCode(String code);

    /**
     * 根据用户ID查询角色列表
     */
    List<SysRoleDTO> listByUserId(Long userId);

    /**
     * 查询默认角色列表
     */
    List<SysRoleDTO> listDefaultRoles();

    /**
     * 分配角色菜单
     */
    @OperationLog(description = "分配角色菜单", operationType = "GRANT")
    void assignMenus(Long roleId, List<Long> menuIds);

    /**
     * 分配角色数据权限
     */
    @OperationLog(description = "分配角色数据权限", operationType = "GRANT")
    void assignDataScope(Long roleId, Integer dataScope);
} 