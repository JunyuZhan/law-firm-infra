package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.mapper.RoleMapper;
import com.lawfirm.model.auth.dto.role.RoleCreateDTO;
import com.lawfirm.model.auth.dto.role.RoleQueryDTO;
import com.lawfirm.model.auth.dto.role.RoleUpdateDTO;
import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.service.RoleService;
import com.lawfirm.model.auth.vo.RoleVO;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证角色服务实现类
 */
@Slf4j
@Service("authRoleServiceImpl")
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {
    
    private final RoleMapper roleMapper;
    
    @Override
    public Long createRole(RoleCreateDTO createDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(createDTO, role);
        save(role);
        return role.getId();
    }
    
    @Override
    public void updateRole(Long id, RoleCreateDTO createDTO) {
        Role role = getById(id);
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }
        BeanUtils.copyProperties(createDTO, role);
        updateById(role);
    }
    
    @Override
    public boolean deleteRole(Long id) {
        return removeById(id);
    }
    
    @Override
    public void deleteRoles(List<Long> ids) {
        removeByIds(ids);
    }
    
    @Override
    public RoleVO getRoleById(Long id) {
        Role role = getById(id);
        if (role == null) {
            return null;
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role, roleVO);
        return roleVO;
    }
    
    @Override
    public Page<RoleVO> pageRoles(RoleQueryDTO queryDTO) {
        Page<Role> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        if (queryDTO.getRoleName() != null) {
            wrapper.like(Role::getName, queryDTO.getRoleName());
        }
        if (queryDTO.getRoleCode() != null) {
            wrapper.like(Role::getCode, queryDTO.getRoleCode());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(Role::getStatus, queryDTO.getStatus());
        }
        
        Page<Role> rolePage = page(page, wrapper);
        Page<RoleVO> roleVOPage = new Page<>();
        BeanUtils.copyProperties(rolePage, roleVOPage, "records");
        
        List<RoleVO> roleVOList = rolePage.getRecords().stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
        
        roleVOPage.setRecords(roleVOList);
        return roleVOPage;
    }
    
    @Override
    public List<RoleVO> listAllRoles() {
        List<Role> roles = list();
        return roles.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
    }
    
    @Override
    public Role getByCode(String code) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getCode, code);
        return getOne(wrapper);
    }
    
    @Override
    public void updateStatus(Long id, Integer status) {
        Role role = getById(id);
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }
        role.setStatus(status);
        updateById(role);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermissions(Long id, List<Long> permissionIds) {
        // 简化处理，实际应先删除角色权限关系，再添加新的关系
    }
    
    @Override
    public List<Long> getRolePermissionIds(Long id) {
        // 简化处理，实际应从数据库查询
        return new ArrayList<>();
    }
    
    @Override
    public List<Long> getRoleUserIds(Long id) {
        // 简化处理，实际应从数据库查询
        return new ArrayList<>();
    }
    
    @Override
    public Page<Role> getRolePage(Long current, Long size, String roleName) {
        Page<Role> page = new Page<>(current, size);
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (roleName != null) {
            wrapper.like(Role::getName, roleName);
        }
        return page(page, wrapper);
    }
    
    @Override
    public boolean createRole(Role role) {
        return save(role);
    }
    
    @Override
    public boolean updateRole(Role role) {
        return updateById(role);
    }
    
    @Override
    public List<Role> getRolesByUserId(Long userId) {
        // 简化处理，实际应从数据库查询
        return new ArrayList<>();
    }
    
    @Override
    public List<Role> getAllRoles() {
        return list();
    }
    
    @Override
    public boolean exists(QueryWrapper<Role> queryWrapper) {
        return count(queryWrapper) > 0;
    }
    
    @Override
    public long count(QueryWrapper<Role> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }
    
    @Override
    public Page<Role> page(Page<Role> page, QueryWrapper<Role> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public List<Role> list(QueryWrapper<Role> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public Role getById(Long id) {
        return baseMapper.selectById(id);
    }
    
    @Override
    public boolean save(Role entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(List<Role> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean update(Role entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatch(List<Role> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeBatch(List<Long> idList) {
        return super.removeByIds(idList);
    }

    @Override
    public boolean hasAdminRole(Long userId) {
        List<Role> roles = getRolesByUserId(userId);
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        
        return roles.stream()
                .anyMatch(role -> 
                    // 检查是否为系统管理员角色
                    Role.BUSINESS_ROLE_ADMIN.equals(role.getBusinessRoleType())
                    // 或者检查是否为律所主任角色
                    || Role.BUSINESS_ROLE_DIRECTOR.equals(role.getBusinessRoleType())
                    // 或者检查是否具有"admin"特殊角色编码
                    || "admin".equals(role.getCode())
                    || "ROLE_ADMIN".equals(role.getCode())
                );
    }
}

