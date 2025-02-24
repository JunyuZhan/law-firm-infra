package com.lawfirm.model.base.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("基础结果对象测试")
class BaseResultTest {

    @Test
    @DisplayName("测试创建成功结果（无数据）")
    void testSuccessWithoutData() {
        BaseResult<String> result = BaseResult.success();
        
        assertTrue(result.isSuccess());
        assertEquals("200", result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试创建成功结果（有数据）")
    void testSuccessWithData() {
        String data = "test data";
        BaseResult<String> result = BaseResult.success(data);
        
        assertTrue(result.isSuccess());
        assertEquals("200", result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertEquals(data, result.getData());
    }

    @Test
    @DisplayName("测试创建失败结果")
    void testError() {
        String code = "500";
        String message = "系统错误";
        BaseResult<String> result = BaseResult.error(code, message);
        
        assertFalse(result.isSuccess());
        assertEquals(code, result.getCode());
        assertEquals(message, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        String data = "test data";
        BaseResult<String> result = new BaseResult<String>()
            .setSuccess(true)
            .setCode("200")
            .setMessage("成功")
            .setData(data);
        
        assertTrue(result.isSuccess());
        assertEquals("200", result.getCode());
        assertEquals("成功", result.getMessage());
        assertEquals(data, result.getData());
    }

    @Test
    @DisplayName("测试泛型支持")
    void testGenericSupport() {
        // 测试字符串类型
        BaseResult<String> stringResult = BaseResult.success("test");
        assertTrue(stringResult.isSuccess());
        assertEquals("test", stringResult.getData());
        
        // 测试整数类型
        BaseResult<Integer> intResult = BaseResult.success(123);
        assertTrue(intResult.isSuccess());
        assertEquals(123, intResult.getData());
        
        // 测试自定义对象类型
        TestData testData = new TestData("test", 123);
        BaseResult<TestData> objectResult = BaseResult.success(testData);
        assertTrue(objectResult.isSuccess());
        assertEquals(testData, objectResult.getData());
    }

    // 用于测试的内部类
    @lombok.Data
    @lombok.AllArgsConstructor
    private static class TestData {
        private String name;
        private Integer value;
    }
} 