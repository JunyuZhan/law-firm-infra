package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysRole;

import java.util.List;

/**
 * 系统角色服务接口
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 创建角色
     */
    @Log(module = "角色管理", businessType = BusinessType.INSERT, description = "创建角色")
    void createRole(SysRole role);

    /**
     * 更新角色
     */
    @Log(module = "角色管理", businessType = BusinessType.UPDATE, description = "更新角色")
    void updateRole(SysRole role);

    /**
     * 删除角色
     */
    @Log(module = "角色管理", businessType = BusinessType.DELETE, description = "删除角色")
    void deleteRole(Long id);

    /**
     * 根据角色编码查询角色
     */
    SysRole getByCode(String code);

    /**
     * 根据用户ID查询角色列表
     */
    List<SysRole> listByUserId(Long userId);

    /**
     * 查询默认角色列表
     */
    List<SysRole> listDefaultRoles();

    /**
     * 分配菜单权限
     */
    @Log(module = "角色管理", businessType = BusinessType.GRANT, description = "分配角色菜单权限")
    void assignMenus(Long roleId, List<Long> menuIds);

    /**
     * 分配数据权限
     */
    @Log(module = "角色管理", businessType = BusinessType.GRANT, description = "分配角色数据权限")
    void assignDataScope(Long roleId, Integer dataScope, List<Long> deptIds);
} 