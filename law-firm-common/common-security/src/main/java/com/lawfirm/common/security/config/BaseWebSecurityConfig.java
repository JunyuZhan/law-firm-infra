package com.lawfirm.common.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础Web安全配置类
 * 提供通用的安全配置，可被各模块继承和扩展
 */
@Configuration("commonBaseWebSecurityConfig")
@Slf4j
public class BaseWebSecurityConfig {

    /**
     * Web安全过滤器链配置方法
     * 
     * 使用条件注解，仅在缺少webSecurityFilterChain Bean时创建
     * 避免与模块中定义的Bean冲突
     * 
     * @param http HttpSecurity实例
     * @return 配置好的SecurityFilterChain
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Bean("commonWebSecurityFilterChain")
    @ConditionalOnMissingBean(name = {"webSecurityFilterChain"})
    public SecurityFilterChain commonWebSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置Web安全过滤链 - 默认配置");
        
        // 提供默认的安全配置，通常用于开发环境或简单场景
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**", "/health/**", "/info/**").permitAll()
                .anyRequest().authenticated());
                
        return http.build();
    }
} 