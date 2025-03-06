package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.UserGroup;
import com.lawfirm.model.auth.mapper.UserGroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户组Mapper接口实现类
 * 用于扩展基础UserGroupMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserGroupMapperImpl implements UserGroupMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final UserGroupMapper userGroupMapper;
    
    @Override
    public UserGroup selectById(Long id) {
        log.debug("自定义UserGroupMapperImpl.selectById，参数：{}", id);
        return userGroupMapper.selectById(id);
    }
    
    @Override
    public UserGroup selectByCode(String code) {
        log.debug("自定义UserGroupMapperImpl.selectByCode，参数：{}", code);
        return userGroupMapper.selectByCode(code);
    }
    
    @Override
    public List<UserGroup> selectAll() {
        log.debug("自定义UserGroupMapperImpl.selectAll");
        return userGroupMapper.selectAll();
    }
    
    @Override
    public List<UserGroup> selectByUserId(Long userId) {
        log.debug("自定义UserGroupMapperImpl.selectByUserId，参数：{}", userId);
        return userGroupMapper.selectByUserId(userId);
    }
    
    @Override
    public int addUserToGroup(Long userGroupId, Long userId) {
        log.debug("自定义UserGroupMapperImpl.addUserToGroup，参数：userGroupId={}，userId={}", userGroupId, userId);
        return userGroupMapper.addUserToGroup(userGroupId, userId);
    }
    
    @Override
    public int removeUserFromGroup(Long userGroupId, Long userId) {
        log.debug("自定义UserGroupMapperImpl.removeUserFromGroup，参数：userGroupId={}，userId={}", userGroupId, userId);
        return userGroupMapper.removeUserFromGroup(userGroupId, userId);
    }
    
    @Override
    public int removeAllUsersFromGroup(Long userGroupId) {
        log.debug("自定义UserGroupMapperImpl.removeAllUsersFromGroup，参数：{}", userGroupId);
        return userGroupMapper.removeAllUsersFromGroup(userGroupId);
    }
    
    // 实现其他接口方法...
}
