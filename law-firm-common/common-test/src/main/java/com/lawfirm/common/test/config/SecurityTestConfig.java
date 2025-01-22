package com.lawfirm.common.test.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

/**
 * 安全测试配置类
 */
@TestConfiguration
@TestExecutionListeners(WithSecurityContextTestExecutionListener.class)
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .anyRequest().permitAll();
    }

    @Bean
    public WithSecurityContextTestExecutionListener withSecurityContextTestExecutionListener() {
        return new WithSecurityContextTestExecutionListener();
    }
} 