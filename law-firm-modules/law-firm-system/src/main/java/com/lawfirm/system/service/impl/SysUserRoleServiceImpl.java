package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.model.system.entity.SysUserRole;
import com.lawfirm.system.mapper.SysUserRoleMapper;
import com.lawfirm.system.service.SysUserRoleService;
import com.lawfirm.model.system.vo.SysUserRoleVO;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联服务实现类
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole, SysUserRoleVO> implements SysUserRoleService {

    @Override
    protected SysUserRole createEntity() {
        return new SysUserRole();
    }

    @Override
    protected SysUserRoleVO createVO() {
        return new SysUserRoleVO();
    }

    @Override
    public SysUserRoleVO entityToVO(SysUserRole entity) {
        if (entity == null) {
            return null;
        }
        SysUserRoleVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysUserRole voToEntity(SysUserRoleVO vo) {
        if (vo == null) {
            return null;
        }
        SysUserRole entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public PageResult<SysUserRoleVO> pageVO(Page<SysUserRole> page, QueryWrapper<SysUserRole> wrapper) {
        Page<SysUserRole> result = page(page, wrapper);
        List<SysUserRoleVO> records = result.getRecords().stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
        return new PageResult<>(records, result.getTotal());
    }

    @Override
    public List<SysUserRoleVO> listVO(QueryWrapper<SysUserRole> wrapper) {
        return list(wrapper).stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchUserRoles(List<SysUserRole> userRoles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return true;
        }

        try {
            // 按用户ID分组处理
            userRoles.stream()
                    .collect(Collectors.groupingBy(SysUserRole::getUserId))
                    .forEach((userId, roles) -> {
                        // 删除用户现有角色
                        remove(new LambdaQueryWrapper<SysUserRole>()
                                .eq(SysUserRole::getUserId, userId));
                        
                        // 批量保存新角色
                        super.saveBatch(roles);
                    });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Long userId, List<Long> roleIds) {
        try {
            // 先删除用户与角色关联
            remove(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, userId));

            // 批量添加用户与角色关联
            if (!CollectionUtils.isEmpty(roleIds)) {
                List<SysUserRole> userRoles = roleIds.stream()
                        .map(roleId -> {
                            SysUserRole userRole = new SysUserRole();
                            userRole.setUserId(userId);
                            userRole.setRoleId(roleId);
                            return userRole;
                        })
                        .collect(Collectors.toList());
                super.saveBatch(userRoles);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<SysUserRoleVO> listByUserId(Long userId) {
        List<SysUserRole> userRoles = list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));
        return userRoles.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysUserRoleVO> listByRoleId(Long roleId) {
        List<SysUserRole> userRoles = list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, roleId));
        return userRoles.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(Long userId) {
        remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleId(Long roleId) {
        remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, roleId));
    }
} 