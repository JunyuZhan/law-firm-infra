package com.lawfirm.common.security.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 安全注解测试类
 */
public class SecurityAnnotationTest {

    @RequiresPermissions({"user:create", "user:read"})
    private void methodWithMultiplePermissions() {}

    @RequiresPermissions("user:create")
    private void methodWithSinglePermission() {}

    @RequiresPermissions(value = {"user:create", "user:read"}, logical = Logical.OR)
    private void methodWithOrLogical() {}

    @RequiresRoles({"admin", "user"})
    private void methodWithMultipleRoles() {}

    @RequiresRoles("admin")
    private void methodWithSingleRole() {}

    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    private void methodWithOrLogicalRoles() {}

    @Test
    void testRequiresPermissionsAnnotation() throws java.lang.NoSuchMethodException {
        // 测试多权限注解
        RequiresPermissions multiPerms = getClass()
            .getDeclaredMethod("methodWithMultiplePermissions")
            .getAnnotation(RequiresPermissions.class);
        assertNotNull(multiPerms);
        assertEquals(2, multiPerms.value().length);
        assertEquals(Logical.AND, multiPerms.logical());

        // 测试单权限注解
        RequiresPermissions singlePerm = getClass()
            .getDeclaredMethod("methodWithSinglePermission")
            .getAnnotation(RequiresPermissions.class);
        assertNotNull(singlePerm);
        assertEquals(1, singlePerm.value().length);
        assertEquals("user:create", singlePerm.value()[0]);

        // 测试OR逻辑注解
        RequiresPermissions orPerm = getClass()
            .getDeclaredMethod("methodWithOrLogical")
            .getAnnotation(RequiresPermissions.class);
        assertNotNull(orPerm);
        assertEquals(Logical.OR, orPerm.logical());
    }

    @Test
    void testRequiresRolesAnnotation() throws java.lang.NoSuchMethodException {
        // 测试多角色注解
        RequiresRoles multiRoles = getClass()
            .getDeclaredMethod("methodWithMultipleRoles")
            .getAnnotation(RequiresRoles.class);
        assertNotNull(multiRoles);
        assertEquals(2, multiRoles.value().length);
        assertEquals(Logical.AND, multiRoles.logical());

        // 测试单角色注解
        RequiresRoles singleRole = getClass()
            .getDeclaredMethod("methodWithSingleRole")
            .getAnnotation(RequiresRoles.class);
        assertNotNull(singleRole);
        assertEquals(1, singleRole.value().length);
        assertEquals("admin", singleRole.value()[0]);

        // 测试OR逻辑注解
        RequiresRoles orRole = getClass()
            .getDeclaredMethod("methodWithOrLogicalRoles")
            .getAnnotation(RequiresRoles.class);
        assertNotNull(orRole);
        assertEquals(Logical.OR, orRole.logical());
    }

    @Test
    void testLogicalEnum() {
        assertEquals(2, Logical.values().length);
        assertArrayEquals(new Logical[]{Logical.AND, Logical.OR}, Logical.values());
    }
} 