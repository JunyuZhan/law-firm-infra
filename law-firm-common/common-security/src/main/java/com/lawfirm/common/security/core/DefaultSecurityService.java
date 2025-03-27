package com.lawfirm.common.security.core;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.common.security.context.ThreadLocalSecurityContextHolder;

/**
 * SecurityService接口的默认实现
 * 提供基于ThreadLocal的安全上下文管理
 */
@Service
@Primary
public class DefaultSecurityService implements SecurityService {
    
    private final ThreadLocalSecurityContextHolder contextHolder;
    
    @Autowired
    public DefaultSecurityService(ThreadLocalSecurityContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityContext getSecurityContext() {
        return contextHolder.getContext();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setAuthentication(Authentication authentication) {
        com.lawfirm.common.security.context.DefaultSecurityContext context = 
            (com.lawfirm.common.security.context.DefaultSecurityContext) getSecurityContext();
        context.setAuthentication(authentication);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setAuthorization(Authorization authorization) {
        com.lawfirm.common.security.context.DefaultSecurityContext context = 
            (com.lawfirm.common.security.context.DefaultSecurityContext) getSecurityContext();
        context.setAuthorization(authorization);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearContext() {
        contextHolder.clearContext();
    }
} 