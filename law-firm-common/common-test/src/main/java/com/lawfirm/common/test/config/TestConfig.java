package com.lawfirm.common.test.config;

import org.springframework.context.annotation.Configuration;

/**
 * 测试配置接口
 */
@Configuration
public interface TestConfig {

    /**
     * 配置测试数据源
     */
    void configureTestDataSource();

    /**
     * 配置测试事务管理器
     */
    void configureTestTransactionManager();

    /**
     * 配置测试环境
     */
    void configureTestEnvironment();
} 