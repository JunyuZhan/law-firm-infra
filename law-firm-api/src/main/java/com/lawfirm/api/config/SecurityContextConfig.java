package com.lawfirm.api.config;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.common.security.context.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 安全上下文配置
 * 提供开发环境下的安全上下文实现
 */
@Configuration
public class SecurityContextConfig {

    /**
     * 提供一个开发环境使用的 SecurityContext 实现
     * 此实现仅用于开发测试，返回固定值
     */
    @Bean
    @Primary
    public SecurityContext securityContext() {
        return new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return null;
            }

            @Override
            public Authorization getAuthorization() {
                return null;
            }

            @Override
            public Long getCurrentUserId() {
                // 返回一个模拟的管理员ID，用于开发环境
                return 1L;
            }
        };
    }
} 