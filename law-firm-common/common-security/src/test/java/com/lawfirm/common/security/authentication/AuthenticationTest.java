package com.lawfirm.common.security.authentication;

import com.lawfirm.common.security.test.MockSecurityObjects.MockAuthentication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 认证接口测试类
 */
public class AuthenticationTest {

    @Test
    void testAuthenticationWithValidCredentials() {
        // 准备测试数据
        String username = "testUser";
        String password = "testPass";
        Authentication auth = new MockAuthentication(username, password, true);

        // 验证结果
        assertEquals(username, auth.getPrincipal());
        assertEquals(password, auth.getCredentials());
        assertTrue(auth.isAuthenticated());
    }

    @Test
    void testAuthenticationWithInvalidCredentials() {
        // 准备测试数据
        Authentication auth = new MockAuthentication(null, null, false);

        // 验证结果
        assertNull(auth.getPrincipal());
        assertNull(auth.getCredentials());
        assertFalse(auth.isAuthenticated());
    }
} 