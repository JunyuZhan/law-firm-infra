package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限Mapper接口实现类
 * 用于扩展基础PermissionMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PermissionMapperImpl implements PermissionMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final PermissionMapper permissionMapper;
    
    @Override
    public Permission selectById(Long id) {
        log.debug("自定义PermissionMapperImpl.selectById，参数：{}", id);
        return permissionMapper.selectById(id);
    }
    
    @Override
    public List<Permission> selectByUserId(Long userId) {
        log.debug("自定义PermissionMapperImpl.selectByUserId，参数：{}", userId);
        
        // 这里可以添加自定义逻辑，例如：
        // 1. 添加权限过滤规则
        // 2. 添加权限缓存处理
        // 3. 整合不同来源的权限
        
        return permissionMapper.selectByUserId(userId);
    }
    
    @Override
    public List<Permission> selectByRoleId(Long roleId) {
        log.debug("自定义PermissionMapperImpl.selectByRoleId，参数：{}", roleId);
        return permissionMapper.selectByRoleId(roleId);
    }
    
    // 实现其他接口方法...
}
