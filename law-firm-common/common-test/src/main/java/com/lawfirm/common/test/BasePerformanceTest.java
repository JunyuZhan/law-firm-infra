package com.lawfirm.common.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;

/**
 * 基础性能测试类
 * 提供通用的性能测试方法和断言
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BasePerformanceTest {

    /**
     * 断言条件为真
     */
    protected void assertTrue(boolean condition, String message) {
        Assertions.assertTrue(condition, message);
    }

    /**
     * 断言条件为假
     */
    protected void assertFalse(boolean condition, String message) {
        Assertions.assertFalse(condition, message);
    }

    /**
     * 断言相等
     */
    protected void assertEquals(Object expected, Object actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    /**
     * 断言不相等
     */
    protected void assertNotEquals(Object expected, Object actual, String message) {
        Assertions.assertNotEquals(expected, actual, message);
    }

    /**
     * 断言对象为空
     */
    protected void assertNull(Object object, String message) {
        Assertions.assertNull(object, message);
    }

    /**
     * 断言对象不为空
     */
    protected void assertNotNull(Object object, String message) {
        Assertions.assertNotNull(object, message);
    }

    /**
     * 断言抛出异常
     */
    protected void assertThrows(Class<? extends Throwable> expectedType, Executable executable, String message) {
        Assertions.assertThrows(expectedType, executable, message);
    }
} 