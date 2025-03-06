package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.auth.exception.BusinessException;
import com.lawfirm.auth.mapper.RoleMapperImpl;
import com.lawfirm.auth.mapper.RolePermissionMapperImpl;
import com.lawfirm.auth.mapper.UserRoleMapperImpl;
import com.lawfirm.model.auth.dto.role.RoleCreateDTO;
import com.lawfirm.model.auth.dto.role.RoleUpdateDTO;
import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.entity.RolePermission;
import com.lawfirm.model.auth.mapper.RoleMapper;
import com.lawfirm.model.auth.service.RoleService;
import com.lawfirm.model.auth.vo.RoleVO;
import com.lawfirm.common.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapperImpl roleMapper;
    private final RolePermissionMapperImpl rolePermissionMapper;
    private final UserRoleMapperImpl userRoleMapper;

    @Override
    @Transactional
    public Long createRole(RoleCreateDTO createDTO) {
        // 检查角色编码是否已存在
        Role existingRole = getByCode(createDTO.getCode());
        if (existingRole != null) {
            throw new BusinessException("角色编码已存在");
        }

        // 创建角色实体并保存
        Role role = BeanUtils.copyProperties(createDTO, Role.class);
        role.setStatus(0); // 默认正常状态
        
        boolean saved = roleMapper.insert(role) > 0;
        if (!saved) {
            throw new BusinessException("角色保存失败");
        }

        // 分配权限
        if (createDTO.getPermissionIds() != null && createDTO.getPermissionIds().length > 0) {
            assignPermissions(role.getId(), Arrays.asList(createDTO.getPermissionIds()));
        }

        return role.getId();
    }

    @Override
    @Transactional
    public void updateRole(Long id, RoleCreateDTO updateDTO) {
        // 检查角色是否存在
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 检查角色编码是否已被其他角色使用
        Role existingRole = getByCode(updateDTO.getCode());
        if (existingRole != null && !existingRole.getId().equals(id)) {
            throw new BusinessException("角色编码已存在");
        }

        // 更新角色信息
        BeanUtils.copyProperties(updateDTO, role);
        boolean updated = roleMapper.update(role) > 0;
        if (!updated) {
            throw new BusinessException("角色更新失败");
        }

        // 更新权限
        if (updateDTO.getPermissionIds() != null) {
            assignPermissions(id, Arrays.asList(updateDTO.getPermissionIds()));
        }
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        // 检查角色是否存在
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 检查角色是否被用户引用
        List<Long> userIds = getRoleUserIds(id);
        if (userIds != null && !userIds.isEmpty()) {
            throw new BusinessException("该角色已分配给用户，无法删除");
        }

        // 删除角色权限关联
        rolePermissionMapper.deleteByRoleId(id);

        // 删除角色
        roleMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteRoles(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        for (Long id : ids) {
            deleteRole(id);
        }
    }

    @Override
    public RoleVO getRoleById(Long id) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            return null;
        }

        RoleVO roleVO = BeanUtils.copyProperties(role, RoleVO.class);

        // 获取角色权限ID列表
        List<Long> permissionIds = getRolePermissionIds(id);
        roleVO.setPermissionIds(permissionIds);

        // 获取角色用户数量
        Long userCount = userRoleMapper.countUserByRoleId(id);
        roleVO.setUserCount(userCount);

        return roleVO;
    }

    @Override
    public Page<RoleVO> pageRoles(RoleUpdateDTO queryDTO) {
        // 分页查询
        int pageNum = queryDTO.getPageNum() != null ? queryDTO.getPageNum() : 1;
        int pageSize = queryDTO.getPageSize() != null ? queryDTO.getPageSize() : 10;
        
        // 构建查询条件
        String name = queryDTO.getRoleName();
        
        // 执行分页查询
        List<Role> roles = roleMapper.selectPage(pageNum, pageSize, name);
        int total = roleMapper.selectCount(name);
        
        // 转换为VO
        List<RoleVO> roleVOList = BeanUtils.copyList(roles, RoleVO.class);
        
        // 设置用户数量
        for (RoleVO roleVO : roleVOList) {
            Long userCount = userRoleMapper.countUserByRoleId(roleVO.getId());
            roleVO.setUserCount(userCount);
        }
        
        // 构建分页结果
        Page<RoleVO> page = new Page<>(pageNum, pageSize);
        page.setRecords(roleVOList);
        page.setTotal(total);
        
        return page;
    }

    @Override
    public List<RoleVO> listAllRoles() {
        List<Role> roles = roleMapper.selectAll();
        return BeanUtils.copyList(roles, RoleVO.class);
    }

    @Override
    public Role getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return roleMapper.selectByCode(code);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Integer status) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        
        role.setStatus(status);
        roleMapper.update(role);
    }

    @Override
    @Transactional
    public void assignPermissions(Long id, List<Long> permissionIds) {
        // 检查角色是否存在
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        
        // 删除原有权限
        rolePermissionMapper.deleteByRoleId(id);
        
        // 新增权限
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<RolePermission> rolePermissions = new ArrayList<>();
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(id);
                rolePermission.setPermissionId(permissionId);
                rolePermissions.add(rolePermission);
            }
            rolePermissionMapper.batchInsert(rolePermissions);
        }
    }

    @Override
    public List<Long> getRolePermissionIds(Long id) {
        return rolePermissionMapper.selectPermissionIdsByRoleId(id);
    }

    @Override
    public List<Long> getRoleUserIds(Long id) {
        return userRoleMapper.selectUserIdsByRoleId(id);
    }
    
    @Override
    public List<Long> listPermissionIdsByRoleId(Long id) {
        return rolePermissionMapper.selectPermissionIdsByRoleId(id);
    }
}
