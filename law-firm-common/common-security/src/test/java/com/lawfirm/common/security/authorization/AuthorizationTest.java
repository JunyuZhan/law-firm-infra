package com.lawfirm.common.security.authorization;

import com.lawfirm.common.security.test.MockSecurityObjects.MockAuthorization;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 授权接口测试类
 */
public class AuthorizationTest {

    @Test
    void testHasPermissionWithValidPermissions() {
        // 准备测试数据
        String[] permissions = {"user:create", "user:read"};
        Authorization auth = new MockAuthorization(permissions, null);

        // 验证结果
        assertTrue(auth.hasPermission("user:create"));
        assertTrue(auth.hasPermission("user:read"));
        assertFalse(auth.hasPermission("user:delete"));
    }

    @Test
    void testHasPermissionWithNoPermissions() {
        // 准备测试数据
        Authorization auth = new MockAuthorization(null, null);

        // 验证结果
        assertFalse(auth.hasPermission("user:create"));
    }

    @Test
    void testHasRoleWithValidRoles() {
        // 准备测试数据
        String[] roles = {"admin", "user"};
        Authorization auth = new MockAuthorization(null, roles);

        // 验证结果
        assertTrue(auth.hasRole("admin"));
        assertTrue(auth.hasRole("user"));
        assertFalse(auth.hasRole("guest"));
    }

    @Test
    void testHasRoleWithNoRoles() {
        // 准备测试数据
        Authorization auth = new MockAuthorization(null, null);

        // 验证结果
        assertFalse(auth.hasRole("admin"));
    }
} 