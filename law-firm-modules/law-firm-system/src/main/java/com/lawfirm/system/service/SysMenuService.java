package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.system.entity.SysMenu;
import com.lawfirm.model.system.dto.SysMenuDTO;
import com.lawfirm.model.system.vo.RouterVo;
import com.lawfirm.model.system.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface SysMenuService extends BaseService<SysMenu, SysMenuVO> {

    /**
     * 创建菜单
     */
    void createMenu(SysMenuDTO menu);

    /**
     * 更新菜单
     */
    void updateMenu(SysMenuDTO menu);

    /**
     * 删除菜单
     */
    void deleteMenu(Long menuId);

    /**
     * 根据角色ID查询菜单列表
     */
    List<SysMenuDTO> listByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单列表
     */
    List<SysMenuDTO> listByUserId(Long userId);

    /**
     * 查询可见的菜单列表
     */
    List<SysMenuDTO> listVisible();

    /**
     * 查询子菜单列表
     */
    List<SysMenuDTO> listChildren(Long parentId);

    /**
     * 构建菜单树
     */
    List<SysMenuDTO> buildMenuTree(List<SysMenuDTO> menus);

    /**
     * 构建前端路由
     */
    List<RouterVo> buildRouters(List<SysMenuDTO> menus);

    /**
     * 构建前端路由所需要的菜单
     */
    List<RouterVo> buildMenus(List<SysMenuDTO> menus);
}