package com.lawfirm.system.service.impl;

import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.system.model.entity.SysRole;
import com.lawfirm.system.model.dto.SysRoleDTO;
import com.lawfirm.system.mapper.SysRoleMapper;
import com.lawfirm.system.service.SysRoleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole, SysRoleDTO> implements SysRoleService {

    private final SysRoleMapper roleMapper;

    @Override
    protected SysRole createEntity() {
        return new SysRole();
    }

    @Override
    protected SysRoleDTO createDTO() {
        return new SysRoleDTO();
    }

    @Override
    public SysRoleDTO createRole(SysRoleDTO roleDTO) {
        SysRole role = toEntity(roleDTO);
        save(role);
        return toDTO(role);
    }

    @Override
    public SysRoleDTO updateRole(SysRoleDTO roleDTO) {
        SysRole role = toEntity(roleDTO);
        updateById(role);
        return toDTO(role);
    }

    @Override
    public void deleteRole(Long id) {
        removeById(id);
    }

    @Override
    public List<SysRoleDTO> listByUserId(Long userId) {
        List<SysRole> roles = roleMapper.selectByUserId(userId);
        return roles.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SysRoleDTO> listDefaultRoles() {
        List<SysRole> roles = roleMapper.selectDefaultRoles();
        return roles.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SysRoleDTO getByCode(String code) {
        SysRole role = roleMapper.selectByCode(code);
        return toDTO(role);
    }

    @Override
    public void assignMenus(Long roleId, List<Long> menuIds) {
        roleMapper.deleteRoleMenus(roleId);
        if (!CollectionUtils.isEmpty(menuIds)) {
            roleMapper.insertRoleMenus(roleId, menuIds);
        }
    }

    @Override
    public void assignDataScope(Long roleId, Integer dataScope) {
        SysRole role = getById(roleId);
        if (role != null) {
            role.setDataScope(dataScope);
            updateById(role);
        }
    }
} 