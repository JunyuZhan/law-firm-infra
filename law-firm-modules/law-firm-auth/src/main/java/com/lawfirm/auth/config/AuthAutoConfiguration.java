package com.lawfirm.auth.config;

import com.lawfirm.auth.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 认证模块自动配置类
 * 用于自动配置认证模块的各个组件
 */
@Configuration
@ComponentScan(basePackages = {
    "com.lawfirm.auth.controller",
    "com.lawfirm.auth.service",
    "com.lawfirm.auth.security"
})
@Import({
    SecurityConfig.class,
    WebSecurityConfig.class,
    RedisConfig.class,
    MybatisPlusConfig.class
})
public class AuthAutoConfiguration {
    // 自动配置类，通过导入方式整合所有配置
}
