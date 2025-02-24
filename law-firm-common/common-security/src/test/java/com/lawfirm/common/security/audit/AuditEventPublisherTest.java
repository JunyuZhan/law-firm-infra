package com.lawfirm.common.security.audit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 审计事件发布者接口测试类
 */
public class AuditEventPublisherTest {

    /**
     * 审计事件发布者Mock实现
     */
    private static class MockAuditEventPublisher implements AuditEventPublisher {
        private String lastEventType;
        private Object lastPrincipal;
        private String lastPermission;
        private String lastOperation;
        private String lastResource;
        private boolean lastResult;

        @Override
        public void publishAuthenticationEvent(Object principal, String eventType, boolean result) {
            this.lastPrincipal = principal;
            this.lastEventType = eventType;
            this.lastResult = result;
        }

        @Override
        public void publishAuthorizationEvent(Object principal, String permission, boolean result) {
            this.lastPrincipal = principal;
            this.lastPermission = permission;
            this.lastResult = result;
        }

        @Override
        public void publishOperationEvent(Object principal, String operation, String resource) {
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

    private final MockAuditEventPublisher publisher = new MockAuditEventPublisher();

    @Test
    void testPublishAuthenticationEventWithValidData() {
        // 准备测试数据
        Object principal = "testUser";
        String eventType = "LOGIN";
        boolean result = true;

        // 发布事件
        publisher.publishAuthenticationEvent(principal, eventType, result);

        // 验证结果
        assertEquals(principal, publisher.getLastPrincipal());
        assertEquals(eventType, publisher.getLastEventType());
        assertTrue(publisher.getLastResult());
    }

    @Test
    void testPublishAuthenticationEventWithNullPrincipal() {
        // 发布事件
        publisher.publishAuthenticationEvent(null, "LOGIN", false);

        // 验证结果
        assertNull(publisher.getLastPrincipal());
        assertEquals("LOGIN", publisher.getLastEventType());
        assertFalse(publisher.getLastResult());
    }

    @Test
    void testPublishAuthorizationEventWithValidData() {
        // 准备测试数据
        Object principal = "testUser";
        String permission = "user:create";
        boolean result = true;

        // 发布事件
        publisher.publishAuthorizationEvent(principal, permission, result);

        // 验证结果
        assertEquals(principal, publisher.getLastPrincipal());
        assertEquals(permission, publisher.getLastPermission());
        assertTrue(publisher.getLastResult());
    }

    @Test
    void testPublishAuthorizationEventWithNullData() {
        // 发布事件
        publisher.publishAuthorizationEvent(null, null, false);

        // 验证结果
        assertNull(publisher.getLastPrincipal());
        assertNull(publisher.getLastPermission());
        assertFalse(publisher.getLastResult());
    }

    @Test
    void testPublishOperationEventWithValidData() {
        // 准备测试数据
        Object principal = "testUser";
        String operation = "CREATE";
        String resource = "user";

        // 发布事件
        publisher.publishOperationEvent(principal, operation, resource);

        // 验证结果
        assertEquals(principal, publisher.getLastPrincipal());
        assertEquals(operation, publisher.getLastOperation());
        assertEquals(resource, publisher.getLastResource());
    }

    @Test
    void testPublishOperationEventWithNullData() {
        // 发布事件
        publisher.publishOperationEvent(null, null, null);

        // 验证结果
        assertNull(publisher.getLastPrincipal());
        assertNull(publisher.getLastOperation());
        assertNull(publisher.getLastResource());
    }
} 