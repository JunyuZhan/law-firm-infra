package com.lawfirm.core.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.entity.ProcessPermissionEntity;
import com.lawfirm.core.workflow.model.ProcessPermission;
import com.lawfirm.core.workflow.repository.ProcessPermissionRepository;
import com.lawfirm.core.workflow.service.impl.ProcessPermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessPermissionServiceTest {

    @Mock
    private ProcessPermissionRepository processPermissionRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProcessPermissionServiceImpl processPermissionService;

    private ProcessPermission permission;
    private ProcessPermissionEntity entity;

    @BeforeEach
    void setUp() {
        permission = new ProcessPermission()
                .setProcessKey("test-process")
                .setProcessName("测试流程")
                .setCategory("test")
                .setEnabled(true)
                .setTenantId("default")
                .setRemark("测试备注");

        entity = new ProcessPermissionEntity()
                .setProcessKey("test-process")
                .setProcessName("测试流程")
                .setCategory("test")
                .setEnabled(true)
                .setTenantId("default")
                .setRemark("测试备注")
                .setCreateTime(LocalDateTime.now());
    }

    @Test
    void createPermission() {
        when(processPermissionRepository.save(any(ProcessPermissionEntity.class))).thenReturn(entity);

        ProcessPermission result = processPermissionService.createPermission(permission);

        assertNotNull(result);
        assertEquals(permission.getProcessKey(), result.getProcessKey());
        assertEquals(permission.getProcessName(), result.getProcessName());
        assertEquals(permission.getCategory(), result.getCategory());
        assertEquals(permission.isEnabled(), result.isEnabled());
        assertEquals(permission.getTenantId(), result.getTenantId());
        assertEquals(permission.getRemark(), result.getRemark());

        verify(processPermissionRepository).save(any(ProcessPermissionEntity.class));
    }

    @Test
    void updatePermission() {
        when(processPermissionRepository.findByProcessKey(permission.getProcessKey())).thenReturn(Optional.of(entity));
        when(processPermissionRepository.save(any(ProcessPermissionEntity.class))).thenReturn(entity);

        ProcessPermission result = processPermissionService.updatePermission(permission);

        assertNotNull(result);
        assertEquals(permission.getProcessKey(), result.getProcessKey());
        assertEquals(permission.getProcessName(), result.getProcessName());
        assertEquals(permission.getCategory(), result.getCategory());
        assertEquals(permission.isEnabled(), result.isEnabled());
        assertEquals(permission.getTenantId(), result.getTenantId());
        assertEquals(permission.getRemark(), result.getRemark());

        verify(processPermissionRepository).findByProcessKey(permission.getProcessKey());
        verify(processPermissionRepository).save(any(ProcessPermissionEntity.class));
    }

    @Test
    void deletePermission() {
        processPermissionService.deletePermission(permission.getProcessKey());

        verify(processPermissionRepository).deleteByProcessKey(permission.getProcessKey());
    }

    @Test
    void getPermission() {
        when(processPermissionRepository.findByProcessKey(permission.getProcessKey())).thenReturn(Optional.of(entity));

        ProcessPermission result = processPermissionService.getPermission(permission.getProcessKey());

        assertNotNull(result);
        assertEquals(permission.getProcessKey(), result.getProcessKey());
        assertEquals(permission.getProcessName(), result.getProcessName());
        assertEquals(permission.getCategory(), result.getCategory());
        assertEquals(permission.isEnabled(), result.isEnabled());
        assertEquals(permission.getTenantId(), result.getTenantId());
        assertEquals(permission.getRemark(), result.getRemark());

        verify(processPermissionRepository).findByProcessKey(permission.getProcessKey());
    }

    @Test
    void listPermissions() {
        List<ProcessPermissionEntity> entities = Arrays.asList(entity);
        when(processPermissionRepository.findByCategoryAndEnabled(permission.getCategory(), true))
                .thenReturn(entities);

        List<ProcessPermission> results = processPermissionService.listPermissions(permission.getCategory(), true);

        assertNotNull(results);
        assertEquals(1, results.size());
        ProcessPermission result = results.get(0);
        assertEquals(permission.getProcessKey(), result.getProcessKey());
        assertEquals(permission.getProcessName(), result.getProcessName());
        assertEquals(permission.getCategory(), result.getCategory());
        assertEquals(permission.isEnabled(), result.isEnabled());
        assertEquals(permission.getTenantId(), result.getTenantId());
        assertEquals(permission.getRemark(), result.getRemark());

        verify(processPermissionRepository).findByCategoryAndEnabled(permission.getCategory(), true);
    }

    @Test
    void checkStartPermission() {
        when(processPermissionRepository.findByProcessKey(permission.getProcessKey())).thenReturn(Optional.of(entity));

        boolean result = processPermissionService.checkStartPermission(permission.getProcessKey(), "test-user");

        assertTrue(result);
        verify(processPermissionRepository).findByProcessKey(permission.getProcessKey());
    }

    @Test
    void checkTaskPermission() {
        when(processPermissionRepository.findByProcessKey(permission.getProcessKey())).thenReturn(Optional.of(entity));

        boolean result = processPermissionService.checkTaskPermission(permission.getProcessKey(), "test-task", "test-user");

        assertTrue(result);
        verify(processPermissionRepository).findByProcessKey(permission.getProcessKey());
    }
} 