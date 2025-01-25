package com.lawfirm.auth.aspect;

import com.lawfirm.auth.annotation.RequirePermission;
import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.auth.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PermissionAspectTest {

    @InjectMocks
    private PermissionAspect permissionAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private LoginUser loginUser;
    private Method testMethod;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // 准备测试数据
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEnabled(true);

        Set<String> permissions = new HashSet<>();
        permissions.add("auth:test");
        
        loginUser = new LoginUser(user, permissions);

        // Mock SecurityContext
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock Authentication
        when(authentication.getPrincipal()).thenReturn(loginUser);
        when(authentication.isAuthenticated()).thenReturn(true);

        // 获取测试方法
        testMethod = TestController.class.getMethod("testMethod");
    }

    @Test
    void checkPermissionSuccess() throws Throwable {
        // Mock方法签名
        when(joinPoint.getSignature()).thenReturn(mock(org.aspectj.lang.reflect.MethodSignature.class));
        when(((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod())
            .thenReturn(testMethod);

        // 执行测试
        permissionAspect.checkPermission(joinPoint);

        // 验证方法被调用
        verify(joinPoint).proceed();
    }

    @Test
    void checkPermissionFailNoAuth() {
        // Mock未认证状态
        when(authentication.isAuthenticated()).thenReturn(false);

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> permissionAspect.checkPermission(joinPoint));
    }

    @Test
    void checkPermissionFailNoPermission() throws NoSuchMethodException {
        // 清空权限
        loginUser.setPermissions(new HashSet<>());

        // Mock方法签名
        when(joinPoint.getSignature()).thenReturn(mock(org.aspectj.lang.reflect.MethodSignature.class));
        when(((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod())
            .thenReturn(testMethod);

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> permissionAspect.checkPermission(joinPoint));
    }

    // 测试用的控制器类
    private static class TestController {
        @RequirePermission("auth:test")
        public void testMethod() {
        }
    }
} 