package com.lawfirm.common.security.test;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;

/**
 * 安全模块测试工具类
 */
public class MockSecurityObjects {
    
    /**
     * Mock认证信息实现
     */
    public static class MockAuthentication implements Authentication {
        private final Object principal;
        private final Object credentials;
        private final boolean authenticated;

        public MockAuthentication(Object principal, Object credentials, boolean authenticated) {
            this.principal = principal;
            this.credentials = credentials;
            this.authenticated = authenticated;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        @Override
        public Object getCredentials() {
            return credentials;
        }

        @Override
        public boolean isAuthenticated() {
            return authenticated;
        }
    }

    /**
     * Mock授权信息实现
     */
    public static class MockAuthorization implements Authorization {
        private final String[] permissions;
        private final String[] roles;

        public MockAuthorization(String[] permissions, String[] roles) {
            this.permissions = permissions;
            this.roles = roles;
        }

        @Override
        public boolean hasPermission(String permission) {
            if (permissions == null) return false;
            for (String p : permissions) {
                if (p.equals(permission)) return true;
            }
            return false;
        }

        @Override
        public boolean hasRole(String role) {
            if (roles == null) return false;
            for (String r : roles) {
                if (r.equals(role)) return true;
            }
            return false;
        }
    }
} 