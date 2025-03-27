package com.lawfirm.common.security.context;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * SecurityContext接口的默认实现
 */
@Component
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "true", matchIfMissing = false)
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
        // 假设principal可能是一个包含用户ID的对象，或者直接是用户ID
        if (principal instanceof Long) {
            return (Long) principal;
        }
        // 如果是其他类型的用户对象，这里需要根据具体类型进行适配
        // 为了简化，我们默认返回null
        return null;
    }
} 