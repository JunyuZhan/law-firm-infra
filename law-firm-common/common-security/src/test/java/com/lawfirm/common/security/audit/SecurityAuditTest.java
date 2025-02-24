package com.lawfirm.common.security.audit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 安全审计接口测试类
 */
public class SecurityAuditTest {

    /**
     * 安全审计Mock实现
     */
    private static class MockSecurityAudit implements SecurityAudit {
        private String lastEventType;
        private Object lastPrincipal;
        private String lastPermission;
        private String lastOperation;
        private String lastResource;
        private boolean lastResult;

        @Override
        public void logAuthenticationEvent(Object principal, String eventType, boolean result) {
            this.lastPrincipal = principal;
            this.lastEventType = eventType;
            this.lastResult = result;
        }

        @Override
        public void logAuthorizationEvent(Object principal, String permission, boolean result) {
            this.lastPrincipal = principal;
            this.lastPermission = permission;
            this.lastResult = result;
        }

        @Override
        public void logOperationEvent(Object principal, String operation, String resource) {
            this.lastPrincipal = principal;
            this.lastOperation = operation;
            this.lastResource = resource;
        }

        // Getters for verification
        public String getLastEventType() { return lastEventType; }
        public Object getLastPrincipal() { return lastPrincipal; }
        public String getLastPermission() { return lastPermission; }
        public String getLastOperation() { return lastOperation; }
        public String getLastResource() { return lastResource; }
        public boolean getLastResult() { return lastResult; }
    }

    private final MockSecurityAudit securityAudit = new MockSecurityAudit();

    @Test
    void testLogAuthenticationEventWithValidData() {
        // 准备测试数据
        Object principal = "testUser";
        String eventType = "LOGIN";
        boolean result = true;

        // 记录事件
        securityAudit.logAuthenticationEvent(principal, eventType, result);

        // 验证结果
        assertEquals(principal, securityAudit.getLastPrincipal());
        assertEquals(eventType, securityAudit.getLastEventType());
        assertTrue(securityAudit.getLastResult());
    }

    @Test
    void testLogAuthenticationEventWithNullPrincipal() {
        // 记录事件
        securityAudit.logAuthenticationEvent(null, "LOGIN", false);

        // 验证结果
        assertNull(securityAudit.getLastPrincipal());
        assertEquals("LOGIN", securityAudit.getLastEventType());
        assertFalse(securityAudit.getLastResult());
    }

    @Test
    void testLogAuthorizationEventWithValidData() {
        // 准备测试数据
        Object principal = "testUser";
        String permission = "user:create";
        boolean result = true;

        // 记录事件
        securityAudit.logAuthorizationEvent(principal, permission, result);

        // 验证结果
        assertEquals(principal, securityAudit.getLastPrincipal());
        assertEquals(permission, securityAudit.getLastPermission());
        assertTrue(securityAudit.getLastResult());
    }

    @Test
    void testLogAuthorizationEventWithNullData() {
        // 记录事件
        securityAudit.logAuthorizationEvent(null, null, false);

        // 验证结果
        assertNull(securityAudit.getLastPrincipal());
        assertNull(securityAudit.getLastPermission());
        assertFalse(securityAudit.getLastResult());
    }

    @Test
    void testLogOperationEventWithValidData() {
        // 准备测试数据
        Object principal = "testUser";
        String operation = "CREATE";
        String resource = "user";

        // 记录事件
        securityAudit.logOperationEvent(principal, operation, resource);

        // 验证结果
        assertEquals(principal, securityAudit.getLastPrincipal());
        assertEquals(operation, securityAudit.getLastOperation());
        assertEquals(resource, securityAudit.getLastResource());
    }

    @Test
    void testLogOperationEventWithNullData() {
        // 记录事件
        securityAudit.logOperationEvent(null, null, null);

        // 验证结果
        assertNull(securityAudit.getLastPrincipal());
        assertNull(securityAudit.getLastOperation());
        assertNull(securityAudit.getLastResource());
    }
} 