package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.UserRole;
import com.lawfirm.model.auth.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联Mapper接口实现类
 * 用于扩展基础UserRoleMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRoleMapperImpl implements UserRoleMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final UserRoleMapper userRoleMapper;
    
    @Override
    public List<UserRole> selectByUserId(Long userId) {
        log.debug("自定义UserRoleMapperImpl.selectByUserId，参数：{}", userId);
        return userRoleMapper.selectByUserId(userId);
    }
    
    @Override
    public List<UserRole> selectByRoleId(Long roleId) {
        log.debug("自定义UserRoleMapperImpl.selectByRoleId，参数：{}", roleId);
        return userRoleMapper.selectByRoleId(roleId);
    }
    
    @Override
    public int deleteByUserId(Long userId) {
        log.debug("自定义UserRoleMapperImpl.deleteByUserId，参数：{}", userId);
        return userRoleMapper.deleteByUserId(userId);
    }
    
    @Override
    public int deleteByRoleId(Long roleId) {
        log.debug("自定义UserRoleMapperImpl.deleteByRoleId，参数：{}", roleId);
        return userRoleMapper.deleteByRoleId(roleId);
    }
    
    @Override
    public int batchInsert(List<UserRole> userRoles) {
        log.debug("自定义UserRoleMapperImpl.batchInsert，参数个数：{}", userRoles.size());
        return userRoleMapper.batchInsert(userRoles);
    }
    
    // 实现其他接口方法...
}
