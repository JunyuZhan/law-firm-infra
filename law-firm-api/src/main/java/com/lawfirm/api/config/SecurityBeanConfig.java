package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;

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
     * 文档安全上下文Bean
     * 避免文档模块的SecurityContext自动装配失败
     */
    @Bean
    public SecurityContext documentSecurityContext(@Qualifier("jdbcAuthorization") Authorization authorization) {
        return new SecurityContext(authorization);
    }
} 