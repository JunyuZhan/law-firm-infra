package com.lawfirm.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.document.manager.security.SecurityContext;

/**
 * 文档安全配置类
 * <p>
 * 提供文档模块所需的安全相关Bean
 * </p>
 */
@Configuration
public class DocumentSecurityConfig {
    
    /**
     * 文档安全上下文Bean
     * 
     * @param authorization 授权服务
     * @return 文档安全上下文
     */
    @Bean
    public SecurityContext documentSecurityContext(@Qualifier("jdbcAuthorization") Authorization authorization) {
        return new SecurityContext(authorization);
    }
} 