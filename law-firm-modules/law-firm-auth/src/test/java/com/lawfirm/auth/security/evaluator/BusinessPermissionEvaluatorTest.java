package com.lawfirm.auth.security.evaluator;

import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.auth.service.BusinessPermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * BusinessPermissionEvaluator 测试类
 * <p>
 * 测试自定义权限评估器的各种场景，确保财务模块权限检查正常工作
 * </p>
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class BusinessPermissionEvaluatorTest {

    @Mock
    private BusinessPermissionService businessPermissionService;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    private BusinessPermissionEvaluator permissionEvaluator;
    
    @BeforeEach
    void setUp() {
        permissionEvaluator = new BusinessPermissionEvaluator(businessPermissionService, securityContext);
    }
    
    @Test
    void testHasPermission_WithValidParameters_ShouldReturnTrue() {
        // Given
        Long userId = 1L;
        String resource = "fee";
        String operation = "create";
        
        when(securityContext.getCurrentUserId()).thenReturn(userId);
        when(businessPermissionService.checkPermission(userId, resource, null, operation)).thenReturn(true);
        
        // When
        boolean result = permissionEvaluator.hasPermission(authentication, resource, operation);
        
        // Then
        assertTrue(result);
        verify(businessPermissionService).checkPermission(userId, resource, null, operation);
    }
    
    @Test
    void testHasPermission_WithInvalidParameters_ShouldReturnFalse() {
        // When & Then
        assertFalse(permissionEvaluator.hasPermission(null, "fee", "create"));
        assertFalse(permissionEvaluator.hasPermission(authentication, null, "create"));
        assertFalse(permissionEvaluator.hasPermission(authentication, "fee", null));
    }
    
    @Test
    void testHasPermission_WithNoUserId_ShouldReturnFalse() {
        // Given
        when(securityContext.getCurrentUserId()).thenReturn(null);
        
        // When
        boolean result = permissionEvaluator.hasPermission(authentication, "fee", "create");
        
        // Then
        assertFalse(result);
        verify(businessPermissionService, never()).checkPermission(any(), any(), any(), any());
    }
    
    @Test
    void testHasPermission_WithTargetId_ShouldReturnTrue() {
        // Given
        Long userId = 1L;
        Long targetId = 100L;
        String targetType = "expense";
        String operation = "approve";
        
        when(securityContext.getCurrentUserId()).thenReturn(userId);
        when(businessPermissionService.checkPermission(userId, targetType, targetId, operation)).thenReturn(true);
        
        // When
        boolean result = permissionEvaluator.hasPermission(authentication, targetId, targetType, operation);
        
        // Then
        assertTrue(result);
        verify(businessPermissionService).checkPermission(userId, targetType, targetId, operation);
    }
    
    @Test
    void testHasPermission_WithStringTargetId_ShouldParseAndWork() {
        // Given
        Long userId = 1L;
        String targetId = "200";
        String targetType = "income";
        String operation = "confirm";
        
        when(securityContext.getCurrentUserId()).thenReturn(userId);
        when(businessPermissionService.checkPermission(userId, targetType, 200L, operation)).thenReturn(true);
        
        // When
        boolean result = permissionEvaluator.hasPermission(authentication, targetId, targetType, operation);
        
        // Then
        assertTrue(result);
        verify(businessPermissionService).checkPermission(userId, targetType, 200L, operation);
    }
    
    @Test
    void testHasPermission_WithInvalidTargetId_ShouldReturnFalse() {
        // Given
        Long userId = 1L;
        String targetId = "invalid";
        String targetType = "fee";
        String operation = "view";
        
        when(securityContext.getCurrentUserId()).thenReturn(userId);
        
        // When
        boolean result = permissionEvaluator.hasPermission(authentication, targetId, targetType, operation);
        
        // Then
        assertFalse(result);
        verify(businessPermissionService, never()).checkPermission(any(), any(), any(), any());
    }
    
    @Test
    void testHasPermission_WithException_ShouldReturnFalse() {
        // Given
        Long userId = 1L;
        String resource = "fee";
        String operation = "delete";
        
        when(securityContext.getCurrentUserId()).thenReturn(userId);
        when(businessPermissionService.checkPermission(userId, resource, null, operation))
            .thenThrow(new RuntimeException("Database error"));
        
        // When
        boolean result = permissionEvaluator.hasPermission(authentication, resource, operation);
        
        // Then
        assertFalse(result);
    }
    
    @Test
    void testFinanceModulePermissions() {
        // Given
        Long userId = 1L;
        when(securityContext.getCurrentUserId()).thenReturn(userId);
        
        // 模拟财务模块的各种权限检查
        when(businessPermissionService.checkPermission(userId, "fee", null, "create")).thenReturn(true);
        when(businessPermissionService.checkPermission(userId, "fee", null, "approve")).thenReturn(false);
        when(businessPermissionService.checkPermission(userId, "expense", null, "pay")).thenReturn(true);
        when(businessPermissionService.checkPermission(userId, "income", null, "confirm")).thenReturn(true);
        
        // When & Then
        assertTrue(permissionEvaluator.hasPermission(authentication, "fee", "create"));
        assertFalse(permissionEvaluator.hasPermission(authentication, "fee", "approve"));
        assertTrue(permissionEvaluator.hasPermission(authentication, "expense", "pay"));
        assertTrue(permissionEvaluator.hasPermission(authentication, "income", "confirm"));
    }
}