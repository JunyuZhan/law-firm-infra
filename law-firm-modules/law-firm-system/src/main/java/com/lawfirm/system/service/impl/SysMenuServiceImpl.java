package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.system.entity.SysMenu;
import com.lawfirm.system.model.dto.SysMenuDTO;
import com.lawfirm.system.mapper.SysMenuMapper;
import com.lawfirm.system.service.SysMenuService;
import com.lawfirm.system.model.vo.MetaVo;
import com.lawfirm.system.model.vo.RouterVo;
import com.lawfirm.system.util.TreeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * 菜单服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu, SysMenuDTO> implements SysMenuService {

    private final SysMenuMapper menuMapper;

    @Override
    protected SysMenu createEntity() {
        return new SysMenu();
    }

    @Override
    protected SysMenuDTO createDTO() {
        return new SysMenuDTO();
    }

    @Override
    public SysMenu toEntity(SysMenuDTO dto) {
        if (dto == null) {
            return null;
        }
        SysMenu entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public SysMenuDTO toDTO(SysMenu entity) {
        if (entity == null) {
            return null;
        }
        SysMenuDTO dto = createDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public List<SysMenu> toEntityList(List<SysMenuDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysMenuDTO> toDTOList(List<SysMenu> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(SysMenuDTO menuDTO) {
        SysMenu menu = toEntity(menuDTO);
        // 检查菜单名称是否重复
        if (lambdaQuery().eq(SysMenu::getMenuName, menu.getMenuName()).exists()) {
            throw new IllegalArgumentException("菜单名称已存在");
        }
        save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenuDTO menuDTO) {
        SysMenu menu = toEntity(menuDTO);
        // 检查菜单是否存在
        if (!lambdaQuery().eq(SysMenu::getId, menu.getId()).exists()) {
            throw new IllegalArgumentException("菜单不存在");
        }
        // 检查菜单名称是否重复
        if (lambdaQuery()
                .eq(SysMenu::getMenuName, menu.getMenuName())
                .ne(SysMenu::getId, menu.getId())
                .exists()) {
            throw new IllegalArgumentException("菜单名称已存在");
        }
        updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        // 检查是否有子菜单
        if (hasChildren(menuId)) {
            throw new IllegalArgumentException("存在子菜单,不允许删除");
        }
        removeById(menuId);
    }

    @Override
    public List<SysMenuDTO> listByRoleId(Long roleId) {
        return toDTOList(menuMapper.selectByRoleId(roleId));
    }

    @Override
    public List<SysMenuDTO> listByUserId(Long userId) {
        return toDTOList(menuMapper.selectByUserId(userId));
    }

    @Override
    public List<SysMenuDTO> listVisible() {
        List<SysMenu> menus = lambdaQuery()
                .eq(SysMenu::getVisible, "0")
                .orderByAsc(SysMenu::getOrderNum)
                .list();
        return toDTOList(menus);
    }

    @Override
    public List<SysMenuDTO> listChildren(Long parentId) {
        List<SysMenu> menus = lambdaQuery()
                .eq(SysMenu::getParentId, parentId)
                .orderByAsc(SysMenu::getOrderNum)
                .list();
        return toDTOList(menus);
    }

    @Override
    public List<SysMenuDTO> buildMenuTree(List<SysMenuDTO> menus) {
        return TreeUtils.buildMenuTree(menus);
    }

    @Override
    public List<RouterVo> buildRouters(List<SysMenuDTO> menuDTOs) {
        List<RouterVo> routers = new ArrayList<>();
        for (SysMenuDTO menu : menuDTOs) {
            RouterVo router = new RouterVo();
            router.setName(menu.getMenuName());
            router.setPath(menu.getPath());
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getIsCache() == 1));
            
            List<SysMenuDTO> children = listChildren(menu.getId());
            if (!children.isEmpty()) {
                router.setChildren(buildRouters(children));
            }
            
            routers.add(router);
        }
        return routers;
    }

    private boolean hasChildren(Long menuId) {
        return lambdaQuery().eq(SysMenu::getParentId, menuId).exists();
    }
} 