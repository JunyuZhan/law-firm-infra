package com.lawfirm.finance.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.lawfirm.finance")
@EnableJpaRepositories(basePackages = "com.lawfirm.finance.repository")
@EnableTransactionManagement
public class TestConfig {
} 