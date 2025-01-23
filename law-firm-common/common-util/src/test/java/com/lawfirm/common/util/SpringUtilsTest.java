package com.lawfirm.common.util;

import com.lawfirm.common.test.config.BaseTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpringUtilsTest extends BaseTestConfig {

    @Autowired
    private SpringUtils springUtils;

    @Test
    void testGetBean() {
        // 准备测试数据
        String beanName = "testBean";
        TestBean testBean = new TestBean();
        ApplicationContext mockContext = mock(ApplicationContext.class);
        when(mockContext.getBean(beanName)).thenReturn(testBean);
        when(mockContext.getBean(TestBean.class)).thenReturn(testBean);
        when(mockContext.getBean(beanName, TestBean.class)).thenReturn(testBean);
        
        // 设置ApplicationContext
        springUtils.setApplicationContext(mockContext);
        
        // 测试getBean(String name)
        Object result1 = SpringUtils.getBean(beanName);
        assertNotNull(result1);
        assertEquals(testBean, result1);
        
        // 测试getBean(Class<T> clazz)
        TestBean result2 = SpringUtils.getBean(TestBean.class);
        assertNotNull(result2);
        assertEquals(testBean, result2);
        
        // 测试getBean(String name, Class<T> clazz)
        TestBean result3 = SpringUtils.getBean(beanName, TestBean.class);
        assertNotNull(result3);
        assertEquals(testBean, result3);
        
        // 验证方法调用
        verify(mockContext).getBean(beanName);
        verify(mockContext).getBean(TestBean.class);
        verify(mockContext).getBean(beanName, TestBean.class);
    }

    @Test
    void testGetApplicationContext() {
        ApplicationContext mockContext = mock(ApplicationContext.class);
        springUtils.setApplicationContext(mockContext);
        
        ApplicationContext result = SpringUtils.getApplicationContext();
        assertNotNull(result);
        assertEquals(mockContext, result);
    }
    
    // 测试用的Bean类
    static class TestBean {
    }
} 