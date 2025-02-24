package com.lawfirm.common.security.token;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.test.MockSecurityObjects.MockAuthentication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 令牌服务接口测试类
 */
public class TokenServiceTest {

    /**
     * 令牌服务Mock实现
     */
    private static class MockTokenService implements TokenService {
        @Override
        public String generateToken(Authentication authentication) {
            if (authentication == null || !authentication.isAuthenticated()) return null;
            // 模拟生成token：principal + "_token"
            return authentication.getPrincipal() + "_token";
        }

        @Override
        public boolean validateToken(String token) {
            if (token == null) return false;
            // 模拟验证token：检查是否以_token结尾
            return token.endsWith("_token");
        }

        @Override
        public Authentication getAuthenticationFromToken(String token) {
            if (!validateToken(token)) return null;
            // 模拟从token中提取认证信息
            String principal = token.substring(0, token.length() - 6); // 移除"_token"
            return new MockAuthentication(principal, null, true);
        }
    }

    private final TokenService tokenService = new MockTokenService();

    @Test
    void testGenerateTokenWithValidAuth() {
        // 准备测试数据
        Authentication auth = new MockAuthentication("testUser", "testPass", true);

        // 生成token
        String token = tokenService.generateToken(auth);

        // 验证结果
        assertNotNull(token);
        assertEquals("testUser_token", token);
    }

    @Test
    void testGenerateTokenWithInvalidAuth() {
        // 准备测试数据
        Authentication auth = new MockAuthentication("testUser", "testPass", false);

        // 生成token
        String token = tokenService.generateToken(auth);

        // 验证结果
        assertNull(token);
    }

    @Test
    void testValidateTokenWithValidToken() {
        // 准备测试数据
        String token = "testUser_token";

        // 验证结果
        assertTrue(tokenService.validateToken(token));
    }

    @Test
    void testValidateTokenWithInvalidToken() {
        assertFalse(tokenService.validateToken(null));
        assertFalse(tokenService.validateToken("invalid_token_format"));
    }

    @Test
    void testGetAuthenticationFromValidToken() {
        // 准备测试数据
        String token = "testUser_token";

        // 获取认证信息
        Authentication auth = tokenService.getAuthenticationFromToken(token);

        // 验证结果
        assertNotNull(auth);
        assertEquals("testUser", auth.getPrincipal());
        assertTrue(auth.isAuthenticated());
    }

    @Test
    void testGetAuthenticationFromInvalidToken() {
        assertNull(tokenService.getAuthenticationFromToken(null));
        assertNull(tokenService.getAuthenticationFromToken("invalid_token_format"));
    }
} 