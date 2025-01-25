package com.lawfirm.finance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 财务模块配置类
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.lawfirm.finance.repository")
public class FinanceConfig {
    // 配置项可以根据需要添加
} 