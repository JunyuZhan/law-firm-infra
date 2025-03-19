package com.lawfirm.model.system.service;

import com.lawfirm.model.system.vo.MenuVO;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService {
    
    /**
     * 获取用户菜单列表
     * 
     * @return 菜单列表
     */
    List<MenuVO> getUserMenus();
    
    /**
     * 根据用户ID获取菜单列表
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuVO> getMenusByUserId(Long userId);
    
    /**
     * 根据角色ID获取菜单列表
     * 
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<MenuVO> getMenusByRoleId(Long roleId);
} 