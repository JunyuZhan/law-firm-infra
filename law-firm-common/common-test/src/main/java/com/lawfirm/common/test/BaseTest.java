package com.lawfirm.common.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试基类，提供基础测试配置
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class BaseTest {

    /**
     * 测试前的准备工作
     */
    protected abstract void beforeTest();

    /**
     * 测试后的清理工作
     */
    protected abstract void afterTest();

    /**
     * 清理测试数据
     */
    protected abstract void clearTestData();
} 