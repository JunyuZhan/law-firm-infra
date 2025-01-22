package com.lawfirm.common.test.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 基础测试配置类
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BaseTestConfig {
    
    /**
     * 创建Mock对象
     */
    protected <T> T createMock(Class<T> classToMock) {
        return org.mockito.Mockito.mock(classToMock);
    }
    
    /**
     * 验证Mock对象的方法调用
     */
    protected void verifyNoMoreInteractions(Object... mocks) {
        org.mockito.Mockito.verifyNoMoreInteractions(mocks);
    }
    
    /**
     * 重置Mock对象
     */
    protected void resetMocks(Object... mocks) {
        org.mockito.Mockito.reset(mocks);
    }
} 