package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.exception.BusinessException;
import com.lawfirm.model.system.entity.SysMenu;
import com.lawfirm.system.mapper.SysMenuMapper;
import com.lawfirm.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper menuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(SysMenu menu) {
        // 校验上级菜单是否存在
        if (menu.getParentId() != null && menu.getParentId() != 0L) {
            if (!existsById(menu.getParentId())) {
                throw new BusinessException("上级菜单不存在");
            }
        }
        
        // 保存菜单
        save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenu menu) {
        // 校验菜单是否存在
        if (!existsById(menu.getId())) {
            throw new BusinessException("菜单不存在");
        }

        // 校验上级菜单是否存在
        if (menu.getParentId() != null && menu.getParentId() != 0L) {
            if (!existsById(menu.getParentId())) {
                throw new BusinessException("上级菜单不存在");
            }
        }
        
        // 更新菜单
        updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long id) {
        // 校验菜单是否存在
        if (!existsById(id)) {
            throw new BusinessException("菜单不存在");
        }

        // 校验是否存在子菜单
        if (hasChildren(id)) {
            throw new BusinessException("存在子菜单,不能删除");
        }
        
        // 删除菜单
        removeById(id);
    }

    @Override
    public List<SysMenu> listByRoleId(Long roleId) {
        return menuMapper.selectByRoleId(roleId);
    }

    @Override
    public List<SysMenu> listByUserId(Long userId) {
        return menuMapper.selectByUserId(userId);
    }

    @Override
    public List<SysMenu> listVisible() {
        return menuMapper.selectVisible();
    }

    @Override
    public List<SysMenu> listChildren(Long parentId) {
        return menuMapper.selectChildren(parentId);
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        // 构建父子关系
        Map<Long, List<SysMenu>> parentMap = menus.stream()
                .collect(Collectors.groupingBy(SysMenu::getParentId));

        // 递归构建树形结构
        return buildTree(0L, parentMap);
    }

    @Override
    public List<SysMenu> buildRouters(List<SysMenu> menus) {
        // 过滤出目录和菜单
        List<SysMenu> routerMenus = menus.stream()
                .filter(m -> m.getType() <= 1)
                .collect(Collectors.toList());

        // 构建路由树
        return buildMenuTree(routerMenus);
    }

    /**
     * 递归构建树形结构
     */
    private List<SysMenu> buildTree(Long parentId, Map<Long, List<SysMenu>> parentMap) {
        List<SysMenu> children = parentMap.get(parentId);
        if (children == null) {
            return new ArrayList<>();
        }

        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu menu : children) {
            menu.setChildren(buildTree(menu.getId(), parentMap));
            tree.add(menu);
        }

        return tree;
    }

    /**
     * 判断是否存在子菜单
     */
    private boolean hasChildren(Long id) {
        return lambdaQuery()
                .eq(SysMenu::getParentId, id)
                .exists();
    }
} 