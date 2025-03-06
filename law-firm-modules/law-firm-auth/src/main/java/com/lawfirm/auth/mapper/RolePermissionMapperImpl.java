package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.RolePermission;
import com.lawfirm.model.auth.mapper.RolePermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限关联Mapper接口实现类
 * 用于扩展基础RolePermissionMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RolePermissionMapperImpl implements RolePermissionMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final RolePermissionMapper rolePermissionMapper;
    
    @Override
    public List<RolePermission> selectByRoleId(Long roleId) {
        log.debug("自定义RolePermissionMapperImpl.selectByRoleId，参数：{}", roleId);
        return rolePermissionMapper.selectByRoleId(roleId);
    }
    
    @Override
    public int deleteByRoleId(Long roleId) {
        log.debug("自定义RolePermissionMapperImpl.deleteByRoleId，参数：{}", roleId);
        return rolePermissionMapper.deleteByRoleId(roleId);
    }
    
    @Override
    public int deleteByPermissionId(Long permissionId) {
        log.debug("自定义RolePermissionMapperImpl.deleteByPermissionId，参数：{}", permissionId);
        return rolePermissionMapper.deleteByPermissionId(permissionId);
    }
    
    @Override
    public int batchInsert(List<RolePermission> rolePermissions) {
        log.debug("自定义RolePermissionMapperImpl.batchInsert，参数数量：{}", rolePermissions.size());
        return rolePermissionMapper.batchInsert(rolePermissions);
    }
    
    // 实现其他接口方法...
}
