package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.common.security.authorization.DefaultAuthorization;
import com.lawfirm.document.manager.security.SecurityContext;

/**
 * 安全相关Bean配置类
 * 提供SecurityContext所需的授权相关bean
 */
@Configuration
public class SecurityBeanConfig {
    
    /**
     * 提供默认的授权实现Bean
     * 在JDBC授权实现无效时使用此Bean
     */
    @Bean
    @Primary
    public Authorization defaultAuthorization() {
        return new DefaultAuthorization();
    }
    
    /**
     * 提供文档安全上下文Bean
     * 避免文档模块的SecurityContext自动装配失败
     */
    @Bean
    public SecurityContext documentSecurityContext(Authorization authorization) {
        return new SecurityContext(authorization);
    }
} 