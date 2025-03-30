package com.lawfirm.auth.config;

import com.lawfirm.common.security.authentication.Authentication;
import com.lawfirm.common.security.authorization.Authorization;
import com.lawfirm.common.security.context.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * 开发环境安全上下文配置
 * <p>
 * 提供开发环境下的安全上下文实现
 * </p>
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
public class DevSecurityContextConfig {

    /**
     * 提供一个开发环境使用的SecurityContext实现
     * <p>
     * 此实现仅用于开发测试，返回固定值
     * </p>
     * 
     * @return 开发环境安全上下文
     */
    @Bean
    @Primary
    public SecurityContext securityContext() {
        log.info("初始化开发环境安全上下文，返回固定用户ID: 1");
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