package com.lawfirm.common.security.authentication;

/**
 * 认证基础接口
 */
public interface Authentication {
    
    /**
     * 获取认证主体
     * @return 认证主体对象，通常是用户信息
     */
    Object getPrincipal();
    
    /**
     * 获取认证凭证
     * @return 认证凭证，通常是密码等敏感信息
     */
    Object getCredentials();
    
    /**
     * 是否已认证
     * @return true表示已认证，false表示未认证
     */
    boolean isAuthenticated();
} 