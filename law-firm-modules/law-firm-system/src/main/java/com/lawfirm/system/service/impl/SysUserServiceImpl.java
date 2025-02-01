package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.system.model.dto.SysUserDTO;
import com.lawfirm.system.mapper.SysUserMapper;
import com.lawfirm.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser, SysUserDTO> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected SysUser createEntity() {
        return new SysUser();
    }

    @Override
    protected SysUserDTO createDTO() {
        return new SysUserDTO();
    }

    @Override
    public SysUser toEntity(SysUserDTO dto) {
        if (dto == null) {
            return null;
        }
        SysUser entity = new SysUser();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public SysUserDTO toDTO(SysUser entity) {
        if (entity == null) {
            return null;
        }
        SysUserDTO dto = new SysUserDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public List<SysUser> toEntityList(List<SysUserDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysUserDTO> toDTOList(List<SysUser> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserDTO createUser(SysUserDTO userDTO) {
        // 检查用户名是否重复
        if (lambdaQuery().eq(SysUser::getUsername, userDTO.getUsername()).exists()) {
            throw new IllegalArgumentException("用户名已存在");
        }

        SysUser user = toEntity(userDTO);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return toDTO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserDTO updateUser(SysUserDTO userDTO) {
        // 检查用户是否存在
        SysUser existingUser = getById(userDTO.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 检查用户名是否重复
        if (!existingUser.getUsername().equals(userDTO.getUsername()) &&
                lambdaQuery().eq(SysUser::getUsername, userDTO.getUsername()).exists()) {
            throw new IllegalArgumentException("用户名已存在");
        }

        SysUser user = toEntity(userDTO);
        // 如果密码不为空，则加密密码
        if (user.getPassword() != null && !user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        updateById(user);
        return toDTO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        removeById(userId);
    }

    @Override
    public SysUserDTO getByUsername(String username) {
        return toDTO(lambdaQuery().eq(SysUser::getUsername, username).one());
    }

    @Override
    public List<SysUserDTO> listByDeptId(Long deptId) {
        return toDTOList(baseMapper.selectByDeptId(deptId));
    }

    @Override
    public List<SysUserDTO> listByRoleId(Long roleId) {
        return toDTOList(baseMapper.selectByRoleId(roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(Long userId, String avatar) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        user.setAvatar(avatar);
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
    }

    @Override
    public void resetPassword(Long userId, String password) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(password));
        updateById(user);
    }

    @Override
    public SysUserDTO updateProfile(SysUserDTO userDTO) {
        // 检查用户是否存在
        SysUser existingUser = getById(userDTO.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        SysUser user = toEntity(userDTO);
        // 只更新允许的字段
        existingUser.setNickname(user.getNickname());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());
        existingUser.setSex(user.getSex());

        updateById(existingUser);
        return toDTO(existingUser);
    }

    @Override
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 检查用户是否存在
        if (!exists(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 更新用户角色
        baseMapper.deleteUserRoles(userId);
        if (roleIds != null && !roleIds.isEmpty()) {
            baseMapper.insertUserRoles(userId, roleIds);
        }
    }

    @Override
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        // 检查用户是否存在
        if (!exists(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 更新用户角色
        baseMapper.deleteUserRoles(userId);
        if (roleIds != null && !roleIds.isEmpty()) {
            baseMapper.insertUserRoles(userId, roleIds);
        }
    }

    private boolean exists(Long id) {
        return lambdaQuery().eq(SysUser::getId, id).exists();
    }
} 