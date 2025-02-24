package com.lawfirm.common.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试应用程序基类
 */
@SpringBootApplication
public abstract class TestApplication {
    
    /**
     * 配置测试应用程序
     */
    protected abstract void configureTestApplication();

    /**
     * 初始化测试环境
     */
    protected abstract void initializeTestEnvironment();
} 