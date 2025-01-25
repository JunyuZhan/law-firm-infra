package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysMenu;

import java.util.List;

/**
 * 系统菜单服务接口
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 创建菜单
     */
    @Log(module = "菜单管理", businessType = BusinessType.INSERT, description = "创建菜单")
    void createMenu(SysMenu menu);

    /**
     * 更新菜单
     */
    @Log(module = "菜单管理", businessType = BusinessType.UPDATE, description = "更新菜单")
    void updateMenu(SysMenu menu);

    /**
     * 删除菜单
     */
    @Log(module = "菜单管理", businessType = BusinessType.DELETE, description = "删除菜单")
    void deleteMenu(Long id);

    /**
     * 根据角色ID查询菜单列表
     */
    List<SysMenu> listByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单列表
     */
    List<SysMenu> listByUserId(Long userId);

    /**
     * 查询所有可见的菜单列表
     */
    List<SysMenu> listVisible();

    /**
     * 查询子菜单列表
     */
    List<SysMenu> listChildren(Long parentId);

    /**
     * 构建菜单树
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 构建前端路由
     */
    List<SysMenu> buildRouters(List<SysMenu> menus);
} 