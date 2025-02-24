package com.lawfirm.common.security.authentication;

import com.lawfirm.common.security.test.MockSecurityObjects.MockAuthentication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 认证提供者接口测试类
 */
public class AuthenticationProviderTest {

    /**
     * 认证提供者Mock实现
     */
    private static class MockAuthenticationProvider implements AuthenticationProvider {
        @Override
        public Authentication authenticate(Authentication authentication) {
            // 模拟认证逻辑
            if ("testUser".equals(authentication.getPrincipal()) 
                && "testPass".equals(authentication.getCredentials())) {
                return new MockAuthentication(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    true
                );
            }
            return new MockAuthentication(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                false
            );
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return Authentication.class.isAssignableFrom(authentication);
        }
    }

    @Test
    void testAuthenticateWithValidCredentials() {
        // 准备测试数据
        AuthenticationProvider provider = new MockAuthenticationProvider();
        Authentication inputAuth = new MockAuthentication(
            "testUser", "testPass", false
        );

        // 执行认证
        Authentication resultAuth = provider.authenticate(inputAuth);

        // 验证结果
        assertTrue(resultAuth.isAuthenticated());
        assertEquals("testUser", resultAuth.getPrincipal());
        assertEquals("testPass", resultAuth.getCredentials());
    }

    @Test
    void testAuthenticateWithInvalidCredentials() {
        // 准备测试数据
        AuthenticationProvider provider = new MockAuthenticationProvider();
        Authentication inputAuth = new MockAuthentication(
            "wrongUser", "wrongPass", false
        );

        // 执行认证
        Authentication resultAuth = provider.authenticate(inputAuth);

        // 验证结果
        assertFalse(resultAuth.isAuthenticated());
    }

    @Test
    void testSupports() {
        AuthenticationProvider provider = new MockAuthenticationProvider();
        
        // 验证支持的类型
        assertTrue(provider.supports(Authentication.class));
        assertTrue(provider.supports(MockAuthentication.class));
        
        // 验证不支持的类型
        assertFalse(provider.supports(Object.class));
        assertFalse(provider.supports(String.class));
    }
} 