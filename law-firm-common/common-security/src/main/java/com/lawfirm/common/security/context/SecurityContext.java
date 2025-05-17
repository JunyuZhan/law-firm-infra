package com.lawfirm.common.security.context;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;

/**
 * 安全上下文接口
 * 用于存储当前线程的认证和授权信息
 */
public interface SecurityContext {
    
    /**
     * 获取当前认证信息
     * @return 认证信息对象，未认证时可能返回null
     */
    Authentication getAuthentication();
    
    /**
     * 获取当前授权信息
     * @return 授权信息对象，未授权时可能返回null
     */
    Authorization getAuthorization();

    /**
     * 获取当前用户ID
     * @return 当前用户ID，未认证时返回null
     */
    Long getCurrentUserId();

    /**
     * 获取当前用户名
     * @return 当前用户名，未认证时返回null
     */
    String getCurrentUsername();

    /**
     * 获取当前租户ID
     * @return 当前租户ID，未认证或无多租户时返回null
     */
    Long getCurrentTenantId();
} 