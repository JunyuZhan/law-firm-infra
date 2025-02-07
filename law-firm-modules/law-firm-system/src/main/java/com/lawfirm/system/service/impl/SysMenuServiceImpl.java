package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.data.vo.BaseVO;
import com.lawfirm.system.mapper.SysMenuMapper;
import com.lawfirm.model.system.dto.SysMenuDTO;
import com.lawfirm.model.system.entity.SysMenu;
import com.lawfirm.model.system.vo.MetaVo;
import com.lawfirm.model.system.vo.RouterVo;
import com.lawfirm.model.system.vo.SysMenuVO;
import com.lawfirm.system.service.SysMenuService;
import com.lawfirm.system.util.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * 菜单服务实现类
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu, SysMenuVO> implements SysMenuService {

    @Override
    protected SysMenuVO createVO() {
        return new SysMenuVO();
    }

    @Override
    protected SysMenu createEntity() {
        return new SysMenu();
    }

    @Override
    public SysMenuVO entityToVO(SysMenu entity) {
        if (entity == null) {
            return null;
        }
        SysMenuVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysMenu voToEntity(SysMenuVO vo) {
        if (vo == null) {
            return null;
        }
        SysMenu entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    private SysMenuDTO voToDTO(SysMenuVO vo) {
        if (vo == null) {
            return null;
        }
        SysMenuDTO dto = new SysMenuDTO();
        BeanUtils.copyProperties(vo, dto);
        if (!CollectionUtils.isEmpty(vo.getChildren())) {
            dto.setChildren(vo.getChildren().stream()
                    .map(this::voToDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private List<SysMenuDTO> voListToDTOList(List<SysMenuVO> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return Collections.emptyList();
        }
        return voList.stream()
                .map(this::voToDTO)
                .collect(Collectors.toList());
    }

    private SysMenuVO dtoToVO(SysMenuDTO dto) {
        if (dto == null) {
            return null;
        }
        SysMenuVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            vo.setChildren(dto.getChildren().stream()
                    .map(this::dtoToVO)
                    .collect(Collectors.toList()));
        }
        return vo;
    }

    @Override
    @Transactional
    public void createMenu(SysMenuDTO menu) {
        create(dtoToVO(menu));
    }

    @Override
    @Transactional
    public void updateMenu(SysMenuDTO menu) {
        update(dtoToVO(menu));
    }

    @Override
    @Transactional
    public void deleteMenu(Long menuId) {
        removeById(menuId);
    }

    @Override
    public List<SysMenuDTO> listByRoleId(Long roleId) {
        List<SysMenu> menus = baseMapper.selectByRoleId(roleId);
        return voListToDTOList(entityListToVOList(menus));
    }

    @Override
    public List<SysMenuDTO> listByUserId(Long userId) {
        List<SysMenu> menus = baseMapper.selectByUserId(userId);
        return voListToDTOList(entityListToVOList(menus));
    }

    @Override
    public List<SysMenuDTO> listVisible() {
        List<SysMenu> menus = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getVisible, "0")
                .orderByAsc(SysMenu::getOrderNum));
        return voListToDTOList(entityListToVOList(menus));
    }

    @Override
    public List<SysMenuDTO> listChildren(Long parentId) {
        List<SysMenu> menus = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, parentId)
                .orderByAsc(SysMenu::getOrderNum));
        return voListToDTOList(entityListToVOList(menus));
    }

    @Override
    public List<SysMenuDTO> buildMenuTree(List<SysMenuDTO> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>();
        }

        List<SysMenuDTO> returnList = new ArrayList<>();
        List<Long> tempList = menus.stream().map(SysMenuDTO::getId).collect(Collectors.toList());
        for (SysMenuDTO menu : menus) {
            // 如果是顶级节点,遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    private void recursionFn(List<SysMenuDTO> list, SysMenuDTO t) {
        // 得到子节点列表
        List<SysMenuDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuDTO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private List<SysMenuDTO> getChildList(List<SysMenuDTO> list, SysMenuDTO t) {
        return list.stream()
                .filter(n -> n.getParentId().equals(t.getId()))
                .collect(Collectors.toList());
    }

    private boolean hasChild(List<SysMenuDTO> list, SysMenuDTO t) {
        return getChildList(list, t).size() > 0;
    }

    @Override
    public List<RouterVo> buildRouters(List<SysMenuDTO> menus) {
        List<RouterVo> routers = new ArrayList<>();
        for (SysMenuDTO menu : menus) {
            RouterVo router = new RouterVo();
            router.setName(menu.getMenuName());
            router.setPath(menu.getPath());
            router.setComponent(menu.getComponent());
            router.setQuery(menu.getQuery());
            router.setHidden(Integer.valueOf(1).equals(menu.getVisible()));
            router.setAlwaysShow(true);
            
            MetaVo meta = new MetaVo();
            meta.setTitle(menu.getMenuName());
            meta.setIcon(menu.getIcon());
            meta.setNoCache(Integer.valueOf(1).equals(menu.getIsCache()));
            router.setMeta(meta);
            
            List<SysMenuDTO> children = menu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                router.setChildren(buildRouters(children));
            }
            
            routers.add(router);
        }
        return routers;
    }

    @Override
    public List<RouterVo> buildMenus(List<SysMenuDTO> menus) {
        return buildRouters(menus);
    }

    @Override
    public PageResult<SysMenuVO> pageVO(Page<SysMenu> page, QueryWrapper<SysMenu> wrapper) {
        Page<SysMenu> pageResult = baseMapper.selectPage(page, wrapper);
        List<SysMenuVO> records = pageResult.getRecords().stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
        return new PageResult<>(records, pageResult.getTotal());
    }

    @Override
    public List<SysMenuVO> listVO(QueryWrapper<SysMenu> wrapper) {
        List<SysMenu> list = baseMapper.selectList(wrapper);
        return list.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysMenuVO> listVO() {
        return listVO(new QueryWrapper<>());
    }

    @Override
    public SysMenuVO create(SysMenuVO vo) {
        SysMenu entity = voToEntity(vo);
        baseMapper.insert(entity);
        return entityToVO(entity);
    }

    @Override
    public SysMenuVO update(SysMenuVO vo) {
        SysMenu entity = voToEntity(vo);
        baseMapper.updateById(entity);
        return entityToVO(entity);
    }

    @Override
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public SysMenuVO findById(Long id) {
        SysMenu entity = baseMapper.selectById(id);
        if (entity == null) {
            return null;
        }
        return entityToVO(entity);
    }

    @Override
    public SysMenuVO getVOById(Long id) {
        return findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return baseMapper.selectById(id) != null;
    }
} 