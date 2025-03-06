package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户Mapper接口实现类
 * 用于扩展基础UserMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final UserMapper userMapper;
    
    @Override
    public User selectById(Long id) {
        log.debug("自定义UserMapperImpl.selectById，参数：{}", id);
        return userMapper.selectById(id);
    }
    
    @Override
    public User selectByUsername(String username) {
        log.debug("自定义UserMapperImpl.selectByUsername，参数：{}", username);
        return userMapper.selectByUsername(username);
    }
    
    // 这里可以实现其他需要自定义逻辑的方法
    // 对于不需要自定义的方法，可以直接调用原始mapper的实现
    
    // 注意：如果原接口方法较多，可以考虑通过代理或其他方式减少样板代码
} 