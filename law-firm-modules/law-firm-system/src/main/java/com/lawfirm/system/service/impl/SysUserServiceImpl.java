package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.exception.BusinessException;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.system.mapper.SysUserMapper;
import com.lawfirm.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(SysUser user) {
        // 校验用户名是否已存在
        if (isUsernameExists(user.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 保存用户
        save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser user) {
        // 校验用户是否存在
        SysUser existingUser = getById(user.getId());
        if (existingUser == null) {
            throw new BusinessException("用户不存在");
        }

        // 如果修改了用户名,校验新用户名是否已存在
        if (!existingUser.getUsername().equals(user.getUsername()) 
            && isUsernameExists(user.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 不更新密码
        user.setPassword(null);
        
        // 更新用户
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 校验用户是否存在
        if (!existsById(id)) {
            throw new BusinessException("用户不存在");
        }
        
        // 删除用户
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id, String password) {
        // 校验用户是否存在
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 校验密码是否为空
        if (!StringUtils.hasText(password)) {
            throw new BusinessException("密码不能为空");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(password));
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(Long id, String oldPassword, String newPassword) {
        // 校验用户是否存在
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 校验旧密码是否正确
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码不正确");
        }

        // 校验新密码是否为空
        if (!StringUtils.hasText(newPassword)) {
            throw new BusinessException("新密码不能为空");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
    }

    @Override
    public SysUser getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<SysUser> listByDeptId(Long deptId) {
        return userMapper.selectByDeptId(deptId);
    }

    @Override
    public List<SysUser> listByRoleId(Long roleId) {
        return userMapper.selectByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 校验用户是否存在
        if (!existsById(userId)) {
            throw new BusinessException("用户不存在");
        }

        // 删除原有角色关联
        userMapper.deleteUserRoles(userId);

        // 添加新的角色关联
        if (roleIds != null && !roleIds.isEmpty()) {
            userMapper.insertUserRoles(userId, roleIds);
        }
    }

    /**
     * 判断用户名是否已存在
     */
    private boolean isUsernameExists(String username) {
        return lambdaQuery()
                .eq(SysUser::getUsername, username)
                .exists();
    }
} 