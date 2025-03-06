package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.auth.exception.BusinessException;
import com.lawfirm.auth.service.support.PermissionSupport;
import com.lawfirm.model.auth.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.auth.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.entity.RolePermission;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import com.lawfirm.model.auth.mapper.RolePermissionMapper;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionSupport permissionSupport;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPermission(PermissionCreateDTO createDTO) {
        // 检查权限编码是否已存在
        if (isPermissionCodeExists(createDTO.getPermissionCode(), null)) {
            throw new BusinessException("权限编码已存在");
        }

        // 检查父级权限是否存在
        if (createDTO.getParentId() != null && createDTO.getParentId() > 0) {
            Permission parent = this.getById(createDTO.getParentId());
            if (parent == null) {
                throw new BusinessException("父级权限不存在");
            }
        }

        // 构建权限实体
        Permission permission = new Permission();
        BeanUtils.copyProperties(createDTO, permission);
        
        // 设置默认值
        if (permission.getSortOrder() == null) {
            permission.setSortOrder(0);
        }
        if (permission.getStatus() == null) {
            permission.setStatus(1);
        }
        if (permission.getParentId() == null) {
            permission.setParentId(0L);
        }

        // 保存权限
        this.save(permission);
        return permission.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(Long id, PermissionUpdateDTO updateDTO) {
        // 检查权限是否存在
        Permission permission = this.getById(id);
        if (permission == null) {
            throw new BusinessException("权限不存在");
        }

        // 检查权限编码是否已存在（排除自身）
        if (StringUtils.isNotBlank(updateDTO.getPermissionCode()) && 
                !updateDTO.getPermissionCode().equals(permission.getPermissionCode()) && 
                isPermissionCodeExists(updateDTO.getPermissionCode(), id)) {
            throw new BusinessException("权限编码已存在");
        }

        // 检查父级权限是否存在，且不能将自己设为自己的父级
        if (updateDTO.getParentId() != null && updateDTO.getParentId() > 0) {
            if (updateDTO.getParentId().equals(id)) {
                throw new BusinessException("不能将自己设为父级权限");
            }
            
            Permission parent = this.getById(updateDTO.getParentId());
            if (parent == null) {
                throw new BusinessException("父级权限不存在");
            }
            
            // 检查是否形成循环依赖
            if (isCircularReference(id, updateDTO.getParentId())) {
                throw new BusinessException("不能设置子权限为父级权限");
            }
        }

        // 更新权限
        BeanUtils.copyProperties(updateDTO, permission);
        this.updateById(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Long id) {
        // 检查权限是否存在
        Permission permission = this.getById(id);
        if (permission == null) {
            throw new BusinessException("权限不存在");
        }

        // 检查是否有子权限
        LambdaQueryWrapper<Permission> wrapper = Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getParentId, id);
        long count = this.count(wrapper);
        if (count > 0) {
            throw new BusinessException("该权限下存在子权限，无法删除");
        }

        // 删除角色-权限关联
        rolePermissionMapper.deleteByPermissionId(id);

        // 删除权限
        this.removeById(id);
    }

    @Override
    public PermissionVO getPermissionById(Long id) {
        Permission permission = this.getById(id);
        if (permission == null) {
            return null;
        }
        return permissionSupport.convertToVO(permission);
    }

    @Override
    public List<PermissionVO> listAllPermissions() {
        List<Permission> permissions = this.list(Wrappers.<Permission>lambdaQuery()
                .orderByAsc(Permission::getSortOrder));
        return permissions.stream()
                .map(permissionSupport::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> getPermissionTree() {
        List<Permission> permissions = this.list(Wrappers.<Permission>lambdaQuery()
                .orderByAsc(Permission::getSortOrder));
        
        return buildPermissionTree(permissions);
    }

    @Override
    public List<PermissionVO> listPermissionsByRoleId(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByRoleId(roleId);
        if (rolePermissions.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        List<Permission> permissions = this.listByIds(permissionIds);
        return permissions.stream()
                .map(permissionSupport::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> listPermissionsByUserId(Long userId) {
        List<Permission> permissions = permissionMapper.selectByUserId(userId);
        return permissions.stream()
                .map(permissionSupport::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listPermissionCodesByUserId(Long userId) {
        List<Permission> permissions = permissionMapper.selectByUserId(userId);
        return permissions.stream()
                .map(Permission::getPermissionCode)
                .collect(Collectors.toList());
    }

    /**
     * 判断权限编码是否已存在
     *
     * @param permissionCode 权限编码
     * @param excludeId 排除的权限ID
     * @return 是否存在
     */
    private boolean isPermissionCodeExists(String permissionCode, Long excludeId) {
        LambdaQueryWrapper<Permission> wrapper = Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getPermissionCode, permissionCode);
        
        if (excludeId != null) {
            wrapper.ne(Permission::getId, excludeId);
        }
        
        return this.count(wrapper) > 0;
    }

    /**
     * 判断是否形成循环依赖
     *
     * @param id 当前权限ID
     * @param parentId 要设置的父级权限ID
     * @return 是否形成循环依赖
     */
    private boolean isCircularReference(Long id, Long parentId) {
        // 获取所有子权限ID
        Set<Long> childrenIds = getChildrenIds(id);
        // 如果子权限ID中包含了要设置的父级权限ID，则形成循环依赖
        return childrenIds.contains(parentId);
    }

    /**
     * 获取所有子权限ID
     *
     * @param id 权限ID
     * @return 所有子权限ID集合
     */
    private Set<Long> getChildrenIds(Long id) {
        Set<Long> childrenIds = new HashSet<>();
        getChildrenIdsRecursively(id, childrenIds);
        return childrenIds;
    }

    /**
     * 递归获取所有子权限ID
     *
     * @param id 权限ID
     * @param childrenIds 子权限ID集合
     */
    private void getChildrenIdsRecursively(Long id, Set<Long> childrenIds) {
        LambdaQueryWrapper<Permission> wrapper = Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getParentId, id)
                .select(Permission::getId);
        
        List<Permission> children = this.list(wrapper);
        
        for (Permission child : children) {
            childrenIds.add(child.getId());
            getChildrenIdsRecursively(child.getId(), childrenIds);
        }
    }

    /**
     * 构建权限树
     *
     * @param permissions 权限列表
     * @return 树形权限列表
     */
    private List<PermissionVO> buildPermissionTree(List<Permission> permissions) {
        List<PermissionVO> result = new ArrayList<>();
        
        // 转换为VO
        List<PermissionVO> permissionVOs = permissions.stream()
                .map(permissionSupport::convertToVO)
                .collect(Collectors.toList());
        
        // 构建映射表，便于快速查找
        Map<Long, PermissionVO> permissionMap = permissionVOs.stream()
                .collect(Collectors.toMap(PermissionVO::getId, permission -> permission));
        
        // 构建树形结构
        for (PermissionVO permission : permissionVOs) {
            if (permission.getParentId() == null || permission.getParentId() == 0) {
                // 根节点
                result.add(permission);
            } else {
                // 子节点，添加到父节点的children中
                PermissionVO parent = permissionMap.get(permission.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(permission);
                }
            }
        }
        
        return result;
    }
}
