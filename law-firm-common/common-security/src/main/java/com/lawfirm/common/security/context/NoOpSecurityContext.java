package com.lawfirm.common.security.context;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * SecurityContext接口的空实现
 * 当存储功能禁用时使用此实现
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpSecurityContext implements SecurityContext {

    @Override
    public Authentication getAuthentication() {
        log.warn("存储功能已禁用，SecurityContext.getAuthentication()返回null");
        return null;
    }

    @Override
    public Authorization getAuthorization() {
        log.warn("存储功能已禁用，SecurityContext.getAuthorization()返回null");
        return null;
    }

    @Override
    public Long getCurrentUserId() {
        log.warn("存储功能已禁用，SecurityContext.getCurrentUserId()返回null");
        return null;
    }
} 