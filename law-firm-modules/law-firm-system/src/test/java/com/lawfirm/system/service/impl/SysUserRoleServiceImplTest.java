package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.system.entity.SysUserRole;
import com.lawfirm.system.mapper.SysUserRoleMapper;
import com.lawfirm.model.system.vo.SysUserRoleVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SysUserRoleServiceImplTest {

    @Mock
    private SysUserRoleMapper userRoleMapper;

    @InjectMocks
    private SysUserRoleServiceImpl userRoleService;

    @Test
    void saveBatch_WithUserIdAndRoleIds_Success() {
        // 准备测试数据
        Long userId = 1L;
        List<Long> roleIds = Arrays.asList(1L, 2L, 3L);

        when(userRoleMapper.delete(any())).thenReturn(0);
        when(userRoleMapper.insert(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> userRoleService.saveBatch(userId, roleIds));

        // 验证结果
        verify(userRoleMapper).delete(any());
        verify(userRoleMapper, times(3)).insert(any());
    }

    @Test
    void saveBatch_WithUserRoles_Success() {
        // 准备测试数据
        List<SysUserRole> userRoles = Arrays.asList(
            createUserRole(1L, 1L),
            createUserRole(1L, 2L),
            createUserRole(2L, 1L)
        );

        when(userRoleMapper.delete(any())).thenReturn(0);
        when(userRoleMapper.insert(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> userRoleService.saveBatch(userRoles));

        // 验证结果
        verify(userRoleMapper, times(2)).delete(any());
        verify(userRoleMapper, times(3)).insert(any());
    }

    @Test
    void listByUserId_Success() {
        // 准备测试数据
        Long userId = 1L;
        List<SysUserRole> userRoles = Arrays.asList(
            createUserRole(userId, 1L),
            createUserRole(userId, 2L)
        );

        when(userRoleMapper.selectList(any())).thenReturn(userRoles);

        // 执行测试
        List<SysUserRoleVO> result = userRoleService.listByUserId(userId);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        result.forEach(vo -> {
            assertEquals(userId, vo.getUserId());
            assertNotNull(vo.getRoleId());
        });
        verify(userRoleMapper).selectList(any());
    }

    @Test
    void listByRoleId_Success() {
        // 准备测试数据
        Long roleId = 1L;
        List<SysUserRole> userRoles = Arrays.asList(
            createUserRole(1L, roleId),
            createUserRole(2L, roleId)
        );

        when(userRoleMapper.selectList(any())).thenReturn(userRoles);

        // 执行测试
        List<SysUserRoleVO> result = userRoleService.listByRoleId(roleId);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        result.forEach(vo -> {
            assertEquals(roleId, vo.getRoleId());
            assertNotNull(vo.getUserId());
        });
        verify(userRoleMapper).selectList(any());
    }

    @Test
    void deleteByUserId_Success() {
        // 准备测试数据
        Long userId = 1L;
        when(userRoleMapper.delete(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> userRoleService.deleteByUserId(userId));

        // 验证结果
        verify(userRoleMapper).delete(any());
    }

    @Test
    void deleteByRoleId_Success() {
        // 准备测试数据
        Long roleId = 1L;
        when(userRoleMapper.delete(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> userRoleService.deleteByRoleId(roleId));

        // 验证结果
        verify(userRoleMapper).delete(any());
    }

    private SysUserRole createUserRole(Long userId, Long roleId) {
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

    private SysUserRoleVO createUserRoleVO(Long userId, Long roleId) {
        SysUserRoleVO vo = new SysUserRoleVO();
        vo.setUserId(userId);
        vo.setRoleId(roleId);
        return vo;
    }
} 