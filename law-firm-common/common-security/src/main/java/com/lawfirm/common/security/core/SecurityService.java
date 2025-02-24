package com.lawfirm.common.security.core;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.common.security.context.SecurityContext;

/**
 * 安全服务接口
 * 提供认证和授权相关的核心服务
 */
public interface SecurityService {
    
    /**
     * 获取当前安全上下文
     * @return 安全上下文对象，不会返回null
     */
    SecurityContext getSecurityContext();
    
    /**
     * 设置认证信息
     * @param authentication 要设置的认证信息对象
     */
    void setAuthentication(Authentication authentication);
    
    /**
     * 设置授权信息
     * @param authorization 要设置的授权信息对象
     */
    void setAuthorization(Authorization authorization);
    
    /**
     * 清除安全上下文
     */
    void clearContext();
} 