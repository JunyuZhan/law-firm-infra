package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.model.system.entity.SysUserRole;
import com.lawfirm.model.system.dto.SysUserDTO;
import com.lawfirm.model.system.vo.SysUserVO;
import com.lawfirm.system.mapper.SysUserMapper;
import com.lawfirm.system.service.SysUserService;
import com.lawfirm.system.service.SysUserRoleService;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.common.util.collection.CollUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser, SysUserVO> implements SysUserService {

    private final PasswordEncoder passwordEncoder;
    private final SysUserRoleService sysUserRoleService;

    @Override
    protected SysUserVO createVO() {
        return new SysUserVO();
    }

    @Override
    protected SysUser createEntity() {
        return new SysUser();
    }

    private SysUserVO dtoToVO(SysUserDTO dto) {
        if (dto == null) {
            return null;
        }
        SysUserVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    private SysUser dtoToEntity(SysUserDTO dto) {
        if (dto == null) {
            return null;
        }
        SysUser entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserVO create(SysUserDTO dto) {
        // 检查用户名是否已存在
        if (lambdaQuery().eq(SysUser::getUsername, dto.getUsername()).exists()) {
            throw new BusinessException("用户名已存在");
        }
        
        // 加密密码
        if (StringUtils.hasText(dto.getPassword())) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        SysUser entity = dtoToEntity(dto);
        if (save(entity)) {
            return entityToVO(entity);
        }
        throw new BusinessException("创建用户失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserVO update(SysUserDTO dto) {
        // 检查用户是否存在
        SysUser existingUser = getById(dto.getId());
        if (existingUser == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 如果修改了用户名，检查新用户名是否已存在
        if (!existingUser.getUsername().equals(dto.getUsername()) &&
            lambdaQuery().eq(SysUser::getUsername, dto.getUsername()).exists()) {
            throw new BusinessException("用户名已存在");
        }
        
        // 如果修改了密码，需要加密
        if (StringUtils.hasText(dto.getPassword()) && !dto.getPassword().equals(existingUser.getPassword())) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            dto.setPassword(existingUser.getPassword());
        }
        
        SysUser entity = dtoToEntity(dto);
        if (updateById(entity)) {
            return entityToVO(entity);
        }
        throw new BusinessException("更新用户失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 检查用户是否存在
        if (!lambdaQuery().eq(SysUser::getId, id).exists()) {
            throw new BusinessException("用户不存在");
        }
        
        if (!removeById(id)) {
            throw new BusinessException("删除用户失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        if (!removeBatchByIds(ids)) {
            throw new BusinessException("批量删除用户失败");
        }
    }

    @Override
    public SysUserVO findById(Long id) {
        return entityToVO(getById(id));
    }

    @Override
    public List<SysUserVO> list(QueryWrapper<SysUser> wrapper) {
        return entityListToVOList(baseMapper.selectList(wrapper));
    }

    @Override
    public PageResult<SysUserVO> page(Page<SysUser> page, QueryWrapper<SysUser> wrapper) {
        Page<SysUser> userPage = baseMapper.selectPage(page, wrapper);
        List<SysUserVO> records = entityListToVOList(userPage.getRecords());
        return new PageResult<>(records, userPage.getTotal());
    }

    @Override
    public void resetPassword(Long userId, String password) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(passwordEncoder.encode(password));
        updateById(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        String username = SecurityUtils.getUsername();
        SysUser user = lambdaQuery().eq(SysUser::getUsername, username).one();
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码不正确");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
    }

    @Override
    public void updateAvatar(Long userId, String avatar) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setAvatar(avatar);
        updateById(user);
    }

    @Override
    public SysUserVO updateProfile(SysUserDTO userDTO) {
        SysUser user = getById(userDTO.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        BeanUtils.copyProperties(userDTO, user, "password", "username", "status");
        if (updateById(user)) {
            return entityToVO(user);
        }
        throw new BusinessException("更新用户信息失败");
    }

    @Override
    public SysUserVO getByUsername(String username) {
        return entityToVO(lambdaQuery().eq(SysUser::getUsername, username).one());
    }

    @Override
    public List<SysUserVO> listByDeptId(Long deptId) {
        return entityListToVOList(lambdaQuery().eq(SysUser::getDeptId, deptId).list());
    }

    @Override
    public List<SysUserVO> listByRoleId(Long roleId) {
        return entityListToVOList(baseMapper.selectByRoleId(roleId));
    }

    @Override
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 先删除用户与角色关系
        sysUserRoleService.deleteByUserId(userId);
        
        // 保存用户与角色关系
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<SysUserRole> userRoles = roleIds.stream()
                .map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                })
                .collect(Collectors.toList());
            sysUserRoleService.saveBatch(userRoles);
        }
    }

    @Override
    public PageResult<SysUserVO> pageUsers(Integer pageNum, Integer pageSize, SysUserDTO query) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (StringUtils.hasText(query.getUsername())) {
            wrapper.like(SysUser::getUsername, query.getUsername());
        }
        if (StringUtils.hasText(query.getNickname())) {
            wrapper.like(SysUser::getNickname, query.getNickname());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SysUser::getStatus, query.getStatus());
        }
        if (query.getDeptId() != null) {
            wrapper.eq(SysUser::getDeptId, query.getDeptId());
        }
        
        // 执行分页查询
        Page<SysUser> result = page(page, wrapper);
        
        // 转换为VO
        List<SysUserVO> records = entityListToVOList(result.getRecords());
        
        return new PageResult<>(records, result.getTotal());
    }
}