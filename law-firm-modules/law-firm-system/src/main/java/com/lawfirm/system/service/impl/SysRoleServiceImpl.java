package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.data.vo.BaseVO;
import com.lawfirm.system.mapper.SysRoleMapper;
import com.lawfirm.system.mapper.SysUserRoleMapper;
import com.lawfirm.system.mapper.SysRoleMenuMapper;
import com.lawfirm.model.system.entity.SysRole;
import com.lawfirm.model.system.entity.SysUserRole;
import com.lawfirm.model.system.entity.SysRoleMenu;
import com.lawfirm.model.system.vo.SysRoleVO;
import com.lawfirm.system.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * 角色服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole, SysRoleVO> implements SysRoleService {

    private final SysRoleMapper roleMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysRoleMenuMapper roleMenuMapper;

    @Override
    protected SysRoleVO createVO() {
        return new SysRoleVO();
    }

    @Override
    protected SysRole createEntity() {
        return new SysRole();
    }

    @Override
    public SysRoleVO createRole(SysRoleVO role) {
        // 检查角色名称是否已存在
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName, role.getRoleName())
                .or()
                .eq(SysRole::getRoleCode, role.getRoleCode());
        if (count(wrapper) > 0) {
            throw new IllegalArgumentException("角色名称或编码已存在");
        }

        return create(role);
    }

    @Override
    public SysRoleVO updateRole(SysRoleVO role) {
        // 检查角色是否存在
        SysRole existingRole = getById(role.getId());
        if (existingRole == null) {
            throw new IllegalArgumentException("角色不存在");
        }

        // 检查角色名称是否已被其他角色使用
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(SysRole::getId, role.getId())
                .and(w -> w.eq(SysRole::getRoleName, role.getRoleName())
                        .or()
                        .eq(SysRole::getRoleCode, role.getRoleCode()));
        if (count(wrapper) > 0) {
            throw new IllegalArgumentException("角色名称或编码已存在");
        }

        return update(role);
    }

    @Override
    public void deleteRole(Long id) {
        // 检查角色是否存在
        if (!exists(id)) {
            throw new IllegalArgumentException("角色不存在");
        }

        // 删除角色关联的菜单和用户关系
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, id));
        userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, id));
        
        removeById(id);
    }

    @Override
    public SysRoleVO getByCode(String code) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleCode, code);
        return entityToVO(getOne(wrapper));
    }

    @Override
    public List<SysRoleVO> listByUserId(Long userId) {
        List<SysRole> roles = roleMapper.selectListByUserId(userId);
        return entityListToVOList(roles);
    }

    @Override
    public List<SysRoleVO> listDefaultRoles() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getIsDefault, true)
                .eq(SysRole::getStatus, 1)
                .orderByAsc(SysRole::getOrderNum);
        return entityListToVOList(list(wrapper));
    }

    @Override
    public void assignMenus(Long roleId, List<Long> menuIds) {
        // 检查角色是否存在
        if (!exists(roleId)) {
            throw new IllegalArgumentException("角色不存在");
        }

        // 删除原有的角色-菜单关系
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId));

        // 批量插入新的角色-菜单关系
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<SysRoleMenu> roleMenus = menuIds.stream()
                    .map(menuId -> {
                        SysRoleMenu roleMenu = new SysRoleMenu();
                        roleMenu.setRoleId(roleId);
                        roleMenu.setMenuId(menuId);
                        return roleMenu;
                    })
                    .collect(Collectors.toList());
            roleMenuMapper.insertBatch(roleMenus);
        }
    }

    @Override
    public void assignDataScope(Long roleId, Integer dataScope) {
        // 检查角色是否存在
        SysRole role = getById(roleId);
        if (role == null) {
            throw new IllegalArgumentException("角色不存在");
        }

        // 更新角色数据范围
        role.setDataScope(dataScope);
        updateById(role);
    }

    @Override
    public List<SysRoleVO> list(SysRoleVO query) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(query.getRoleName()), SysRole::getRoleName, query.getRoleName())
                .like(StringUtils.hasText(query.getRoleCode()), SysRole::getRoleCode, query.getRoleCode())
                .eq(query.getStatus() != null, SysRole::getStatus, query.getStatus())
                .orderByAsc(SysRole::getOrderNum);
        return entityListToVOList(list(wrapper));
    }

    @Override
    public boolean exists(Long id) {
        return getById(id) != null;
    }

    @Override
    public SysRoleVO getVOById(Long id) {
        return entityToVO(getById(id));
    }

    @Override
    public List<SysRoleVO> listVO() {
        return entityListToVOList(list());
    }

    @Override
    public List<SysRoleVO> listVO(QueryWrapper<SysRole> wrapper) {
        return entityListToVOList(list(wrapper));
    }

    @Override
    public PageResult<SysRoleVO> pageVO(Page<SysRole> page, QueryWrapper<SysRole> wrapper) {
        Page<SysRole> result = page(page, wrapper);
        return new PageResult<>(result.getRecords().stream()
                .map(this::entityToVO)
                .collect(Collectors.toList()), result.getTotal());
    }
}