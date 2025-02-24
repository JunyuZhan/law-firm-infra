package com.lawfirm.common.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * 认证提供者接口
 * 用于支持不同的认证方式，如用户名密码认证、手机验证码认证、二维码认证等
 */
public interface AuthenticationProvider {
    
    /**
     * 执行认证
     * @param authentication 待认证的信息
     * @return 认证结果
     * @throws AuthenticationException 认证失败时抛出异常
     */
    Authentication authenticate(Authentication authentication);
    
    /**
     * 是否支持此认证类型
     * @param authentication 认证类型
     * @return 是否支持
     */
    boolean supports(Class<?> authentication);
} 