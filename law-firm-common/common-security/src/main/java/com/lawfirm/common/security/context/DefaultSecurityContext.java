package com.lawfirm.common.security.context;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * SecurityContext接口的默认实现
 */
@Component("commonDefaultSecurityContext")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = false)
public class DefaultSecurityContext implements SecurityContext {

    private Authentication authentication;
    private Authorization authorization;

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    @Override
    public Long getCurrentUserId() {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof Long) {
            return (Long) principal;
        }
        if (principal instanceof com.lawfirm.common.security.core.UserDetails) {
            return ((com.lawfirm.common.security.core.UserDetails) principal).getUserId();
        }
        return null;
    }

    /**
     * 获取当前用户名
     */
    public String getCurrentUsername() {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof com.lawfirm.common.security.core.UserDetails) {
            return ((com.lawfirm.common.security.core.UserDetails) principal).getUsername();
        }
        return null;
    }

    /**
     * 获取当前租户ID（如有实现）
     */
    public Long getCurrentTenantId() {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof com.lawfirm.common.security.core.UserDetails) {
            try {
                java.lang.reflect.Method m = principal.getClass().getMethod("getTenantId");
                Object tid = m.invoke(principal);
                if (tid instanceof Long) {
                    return (Long) tid;
                }
            } catch (Exception ignored) {}
        }
        return null;
    }
} 