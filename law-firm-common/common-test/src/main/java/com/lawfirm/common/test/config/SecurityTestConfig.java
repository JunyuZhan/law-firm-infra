package com.lawfirm.common.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestExecutionListener;

/**
 * 测试环境安全配置
 */
@Configuration
@EnableWebSecurity
public class SecurityTestConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public TestExecutionListener withSecurityContextTestExecutionListener() {
        return new WithSecurityContextTestExecutionListener();
    }
} 