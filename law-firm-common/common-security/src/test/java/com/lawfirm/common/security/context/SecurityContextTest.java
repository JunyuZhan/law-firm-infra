package com.lawfirm.common.security.context;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.common.security.test.MockSecurityObjects.MockAuthentication;
import com.lawfirm.common.security.test.MockSecurityObjects.MockAuthorization;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 安全上下文接口测试类
 */
public class SecurityContextTest {

    /**
     * 安全上下文Mock实现
     */
    private static class MockSecurityContext implements SecurityContext {
        private final Authentication authentication;
        private final Authorization authorization;

        public MockSecurityContext(Authentication authentication, Authorization authorization) {
            this.authentication = authentication;
            this.authorization = authorization;
        }

        @Override
        public Authentication getAuthentication() {
            return authentication;
        }

        @Override
        public Authorization getAuthorization() {
            return authorization;
        }

        @Override
        public Long getCurrentUserId() {
            if (authentication == null) {
                return null;
            }
            Object principal = authentication.getPrincipal();
            if (principal instanceof Long) {
                return (Long) principal;
            }
            return null;
        }

        @Override
        public String getCurrentUsername() {
            if (authentication == null) {
                return null;
            }
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                return (String) principal;
            }
            return null;
        }

        @Override
        public Long getCurrentTenantId() {
            // 测试用，直接返回null或可根据principal类型自定义
            return null;
        }
    }

    @Test
    void testGetAuthenticationWithValidAuth() {
        // 准备测试数据
        Authentication auth = new MockAuthentication("testUser", "testPass", true);
        SecurityContext context = new MockSecurityContext(auth, null);

        // 验证结果
        Authentication resultAuth = context.getAuthentication();
        assertNotNull(resultAuth);
        assertEquals("testUser", resultAuth.getPrincipal());
        assertEquals("testPass", resultAuth.getCredentials());
        assertTrue(resultAuth.isAuthenticated());
    }

    @Test
    void testGetAuthenticationWithNoAuth() {
        // 准备测试数据
        SecurityContext context = new MockSecurityContext(null, null);

        // 验证结果
        assertNull(context.getAuthentication());
    }

    @Test
    void testGetAuthorizationWithValidAuth() {
        // 准备测试数据
        Authorization authorization = new MockAuthorization(
            new String[]{"user:read"}, 
            new String[]{"user"}
        );
        SecurityContext context = new MockSecurityContext(null, authorization);

        // 验证结果
        Authorization resultAuth = context.getAuthorization();
        assertNotNull(resultAuth);
        assertTrue(resultAuth.hasPermission("user:read"));
        assertTrue(resultAuth.hasRole("user"));
    }

    @Test
    void testGetAuthorizationWithNoAuth() {
        // 准备测试数据
        SecurityContext context = new MockSecurityContext(null, null);

        // 验证结果
        assertNull(context.getAuthorization());
    }

    @Test
    void testGetCurrentUserIdWithValidAuth() {
        // 准备测试数据
        Authentication auth = new MockAuthentication(1L, "testPass", true);
        SecurityContext context = new MockSecurityContext(auth, null);

        // 验证结果
        Long userId = context.getCurrentUserId();
        assertNotNull(userId);
        assertEquals(1L, userId);
    }

    @Test
    void testGetCurrentUserIdWithNoAuth() {
        // 准备测试数据
        SecurityContext context = new MockSecurityContext(null, null);

        // 验证结果
        assertNull(context.getCurrentUserId());
    }

    @Test
    void testGetCurrentUserIdWithInvalidPrincipal() {
        // 准备测试数据
        Authentication auth = new MockAuthentication("testUser", "testPass", true);
        SecurityContext context = new MockSecurityContext(auth, null);

        // 验证结果
        assertNull(context.getCurrentUserId());
    }
} 