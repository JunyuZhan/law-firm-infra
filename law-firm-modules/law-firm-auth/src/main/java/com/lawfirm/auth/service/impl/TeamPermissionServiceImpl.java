package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.entity.TeamPermission;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import com.lawfirm.model.auth.mapper.TeamPermissionMapper;
import com.lawfirm.model.auth.service.TeamPermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 团队权限服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPermissionServiceImpl extends ServiceImpl<TeamPermissionMapper, TeamPermission> implements TeamPermissionService {

    private final PermissionMapper permissionMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean grantTeamPermission(Long teamId, Long permissionId) {
        // 检查权限是否存在
        if (!checkPermissionExists(permissionId)) {
            log.warn("权限不存在: {}", permissionId);
            return false;
        }
        
        // 检查是否已授予
        if (checkPermissionGranted(teamId, permissionId)) {
            log.info("团队权限已存在: 团队ID={}, 权限ID={}", teamId, permissionId);
            return true;
        }
        
        // 创建团队权限关联
        TeamPermission teamPermission = new TeamPermission();
        teamPermission.setTeamId(teamId);
        teamPermission.setPermissionId(permissionId);
        teamPermission.setCreateTime(LocalDateTime.now());
        
        boolean result = save(teamPermission);
        log.info("授予团队权限: 团队ID={}, 权限ID={}, 结果={}", teamId, permissionId, result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean revokeTeamPermission(Long teamId, Long permissionId) {
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .eq(TeamPermission::getPermissionId, permissionId);
        
        boolean result = remove(queryWrapper);
        log.info("撤销团队权限: 团队ID={}, 权限ID={}, 结果={}", teamId, permissionId, result);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PermissionVO> listPermissionsByTeamId(Long teamId) {
        // 查询团队拥有的权限ID列表
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .select(TeamPermission::getPermissionId);
        
        List<TeamPermission> teamPermissions = list(queryWrapper);
        if (CollectionUtils.isEmpty(teamPermissions)) {
            return Collections.emptyList();
        }
        
        // 获取权限ID列表
        List<Long> permissionIds = teamPermissions.stream()
                .map(TeamPermission::getPermissionId)
                .collect(Collectors.toList());
        
        // 查询权限信息
        List<Permission> permissions = permissionMapper.selectBatchIds(permissionIds);
        
        // 转换为VO
        return permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean hasTeamPermission(Long teamId, String permissionCode) {
        // 查询权限ID
        LambdaQueryWrapper<Permission> pQueryWrapper = new LambdaQueryWrapper<>();
        pQueryWrapper.eq(Permission::getCode, permissionCode)
                    .select(Permission::getId);
        
        Permission permission = permissionMapper.selectOne(pQueryWrapper);
        if (permission == null) {
            return false;
        }
        
        // 查询团队权限
        LambdaQueryWrapper<TeamPermission> tpQueryWrapper = new LambdaQueryWrapper<>();
        tpQueryWrapper.eq(TeamPermission::getTeamId, teamId)
                     .eq(TeamPermission::getPermissionId, permission.getId());
        
        return count(tpQueryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchGrantTeamPermissions(Long teamId, List<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return true;
        }
        
        // 过滤已授予的权限
        List<Long> existingPermissionIds = getExistingPermissionIds(teamId, permissionIds);
        List<Long> newPermissionIds = permissionIds.stream()
                .filter(id -> !existingPermissionIds.contains(id))
                .collect(Collectors.toList());
        
        if (CollectionUtils.isEmpty(newPermissionIds)) {
            return true;
        }
        
        // 检查权限是否存在
        List<Permission> permissions = permissionMapper.selectBatchIds(newPermissionIds);
        if (permissions.size() != newPermissionIds.size()) {
            log.warn("部分权限不存在: {}", newPermissionIds);
            return false;
        }
        
        // 批量创建团队权限关联
        List<TeamPermission> teamPermissions = new ArrayList<>();
        for (Long permissionId : newPermissionIds) {
            TeamPermission teamPermission = new TeamPermission();
            teamPermission.setTeamId(teamId);
            teamPermission.setPermissionId(permissionId);
            teamPermission.setCreateTime(LocalDateTime.now());
            teamPermissions.add(teamPermission);
        }
        
        boolean result = saveBatch(teamPermissions);
        log.info("批量授予团队权限: 团队ID={}, 权限数量={}, 结果={}", teamId, newPermissionIds.size(), result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRevokeTeamPermissions(Long teamId, List<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return true;
        }
        
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .in(TeamPermission::getPermissionId, permissionIds);
        
        boolean result = remove(queryWrapper);
        log.info("批量撤销团队权限: 团队ID={}, 权限数量={}, 结果={}", teamId, permissionIds.size(), result);
        return result;
    }

    @Override
    public List<PermissionVO> listUserTeamPermissions(Long userId) {
        // 获取用户所属团队的权限ID列表
        List<Long> permissionIds = baseMapper.selectPermissionIdsByUserId(userId);
        if (CollectionUtils.isEmpty(permissionIds)) {
            return Collections.emptyList();
        }
        
        // 查询权限信息
        List<Permission> permissions = permissionMapper.selectBatchIds(permissionIds);
        
        // 转换为VO
        return permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean grantUserTeamResourcePermission(Long teamId, Long userId, String resourceType, Long permissionId) {
        // 检查权限是否存在
        if (!checkPermissionExists(permissionId)) {
            log.warn("权限不存在: {}", permissionId);
            return false;
        }
        
        // 检查是否已授予
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .eq(TeamPermission::getUserId, userId)
                   .eq(TeamPermission::getResourceType, resourceType)
                   .eq(TeamPermission::getPermissionId, permissionId)
                   .eq(TeamPermission::getDeleted, 0);
        
        if (count(queryWrapper) > 0) {
            log.info("用户团队资源权限已存在: 团队ID={}, 用户ID={}, 资源类型={}, 权限ID={}", 
                    teamId, userId, resourceType, permissionId);
            return true;
        }
        
        // 创建用户团队资源权限关联
        TeamPermission teamPermission = new TeamPermission();
        teamPermission.setTeamId(teamId);
        teamPermission.setUserId(userId);
        teamPermission.setResourceType(resourceType);
        teamPermission.setPermissionId(permissionId);
        teamPermission.setCreateTime(LocalDateTime.now());
        teamPermission.setUpdateTime(LocalDateTime.now());
        teamPermission.setDeleted(0);
        
        boolean result = save(teamPermission);
        log.info("授予用户团队资源权限: 团队ID={}, 用户ID={}, 资源类型={}, 权限ID={}, 结果={}", 
                teamId, userId, resourceType, permissionId, result);
        return result;
    }

    @Override
    public boolean revokeUserTeamResourcePermission(Long teamId, Long userId, String resourceType, Long permissionId) {
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .eq(TeamPermission::getUserId, userId)
                   .eq(TeamPermission::getResourceType, resourceType)
                   .eq(TeamPermission::getPermissionId, permissionId);
        
        boolean result = remove(queryWrapper);
        log.info("撤销用户团队资源权限: 团队ID={}, 用户ID={}, 资源类型={}, 权限ID={}, 结果={}", 
                teamId, userId, resourceType, permissionId, result);
        return result;
    }

    @Override
    public boolean hasUserTeamResourcePermission(Long teamId, Long userId, String resourceType) {
        // 查询团队拥有的权限（资源类型匹配或通用）
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .and(wrapper -> 
                       wrapper.eq(TeamPermission::getUserId, userId)
                              .or()
                              .isNull(TeamPermission::getUserId)
                   )
                   .and(wrapper -> 
                       wrapper.eq(TeamPermission::getResourceType, resourceType)
                              .or()
                              .isNull(TeamPermission::getResourceType)
                   )
                   .eq(TeamPermission::getDeleted, 0);
        
        return count(queryWrapper) > 0;
    }
    
    /**
     * 检查权限是否存在
     */
    private boolean checkPermissionExists(Long permissionId) {
        return permissionMapper.selectById(permissionId) != null;
    }
    
    /**
     * 检查团队是否已被授予权限
     */
    private boolean checkPermissionGranted(Long teamId, Long permissionId) {
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .eq(TeamPermission::getPermissionId, permissionId);
        return count(queryWrapper) > 0;
    }
    
    /**
     * 获取已存在的权限ID列表
     */
    @SuppressWarnings("unchecked")
    private List<Long> getExistingPermissionIds(Long teamId, List<Long> permissionIds) {
        LambdaQueryWrapper<TeamPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamPermission::getTeamId, teamId)
                   .in(TeamPermission::getPermissionId, permissionIds)
                   .select(TeamPermission::getPermissionId);
        
        List<TeamPermission> teamPermissions = list(queryWrapper);
        return teamPermissions.stream()
                .map(TeamPermission::getPermissionId)
                .collect(Collectors.toList());
    }
    
    /**
     * 将权限实体转换为VO
     */
    private PermissionVO convertToVO(Permission permission) {
        if (permission == null) {
            return null;
        }
        
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        return vo;
    }
} 