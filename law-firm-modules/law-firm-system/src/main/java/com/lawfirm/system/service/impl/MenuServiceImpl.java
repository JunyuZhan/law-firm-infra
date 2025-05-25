package com.lawfirm.system.service.impl;

import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import com.lawfirm.model.auth.enums.PermissionTypeEnum;
import com.lawfirm.model.system.service.MenuService;
import com.lawfirm.model.system.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Slf4j
@Service("menuServiceImpl")
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final PermissionMapper permissionMapper;

    @Override
    public List<MenuVO> getUserMenus() {
        log.info("获取当前用户菜单列表");
        // 从权限表中获取所有菜单类型的权限
        List<Permission> permissions = permissionMapper.selectMenuTree();
        return convertToMenuVOList(permissions);
    }

    @Override
    public List<MenuVO> getMenusByUserId(Long userId) {
        log.info("获取用户ID为 {} 的菜单列表", userId);
        List<Permission> permissions = permissionMapper.selectMenusByUserId(userId);
        return convertToMenuVOList(permissions);
    }

    @Override
    public List<MenuVO> getMenusByRoleId(Long roleId) {
        log.info("获取角色ID为 {} 的菜单列表", roleId);
        List<Permission> permissions = permissionMapper.selectPermissionsByRoleId(roleId);
        return convertToMenuVOList(permissions);
    }

    /**
     * 将权限列表转换为菜单VO列表
     */
    private List<MenuVO> convertToMenuVOList(List<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return new ArrayList<>();
        }
        
        return permissions.stream()
            .filter(p -> PermissionTypeEnum.MENU.getCode().equals(p.getType()))
            .map(this::convertToMenuVO)
            .collect(Collectors.toList());
    }

    /**
     * 将权限实体转换为菜单VO
     */
    private MenuVO convertToMenuVO(Permission permission) {
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(permission, menuVO);
        menuVO.setId(permission.getId());
        menuVO.setMenuType(permission.getType().toString());
        return menuVO;
    }
} 