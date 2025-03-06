package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.Role;
import com.lawfirm.model.auth.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色Mapper接口实现类
 * 用于扩展基础RoleMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleMapperImpl implements RoleMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final RoleMapper roleMapper;
    
    @Override
    public Role selectById(Long id) {
        log.debug("自定义RoleMapperImpl.selectById，参数：{}", id);
        return roleMapper.selectById(id);
    }
    
    @Override
    public List<Role> selectByUserId(Long userId) {
        log.debug("自定义RoleMapperImpl.selectByUserId，参数：{}", userId);
        return roleMapper.selectByUserId(userId);
    }
    
    @Override
    public Role selectByCode(String roleCode) {
        log.debug("自定义RoleMapperImpl.selectByCode，参数：{}", roleCode);
        return roleMapper.selectByCode(roleCode);
    }
    
    // 实现其他接口方法...
}
