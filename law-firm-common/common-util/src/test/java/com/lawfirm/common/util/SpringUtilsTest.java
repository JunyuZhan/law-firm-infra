package com.lawfirm.common.util;

import com.lawfirm.common.util.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestConfig.class)
class SpringUtilsTest extends BaseUtilTest {

    @Autowired
    private SpringUtils springUtils;
    
    private ApplicationContext mockContext;
    private TestBean testBean;
    
    @BeforeEach
    void setUp() {
        mockContext = mock(ApplicationContext.class);
        testBean = new TestBean();
        springUtils.setApplicationContext(mockContext);
    }

    @Test
    void getBean_ShouldReturnBean_WhenUsingName() {
        // 准备测试数据
        String beanName = "testBean";
        when(mockContext.getBean(beanName)).thenReturn(testBean);
        
        // 执行测试
        Object result = SpringUtils.getBean(beanName);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(testBean, result);
        verify(mockContext).getBean(beanName);
    }
    
    @Test
    void getBean_ShouldReturnBean_WhenUsingClass() {
        // 准备测试数据
        when(mockContext.getBean(TestBean.class)).thenReturn(testBean);
        
        // 执行测试
        TestBean result = SpringUtils.getBean(TestBean.class);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(testBean, result);
        verify(mockContext).getBean(TestBean.class);
    }
    
    @Test
    void getBean_ShouldReturnBean_WhenUsingNameAndClass() {
        // 准备测试数据
        String beanName = "testBean";
        when(mockContext.getBean(beanName, TestBean.class)).thenReturn(testBean);
        
        // 执行测试
        TestBean result = SpringUtils.getBean(beanName, TestBean.class);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(testBean, result);
        verify(mockContext).getBean(beanName, TestBean.class);
    }
    
    @Test
    void getBean_ShouldThrowException_WhenBeanNotFound() {
        // 准备测试数据
        String beanName = "nonExistentBean";
        when(mockContext.getBean(beanName))
            .thenThrow(new NoSuchBeanDefinitionException(beanName));
        
        // 验证异常
        assertThrows(NoSuchBeanDefinitionException.class, () -> 
            SpringUtils.getBean(beanName));
    }

    @Test
    void getApplicationContext_ShouldReturnContext() {
        // 执行测试
        ApplicationContext result = SpringUtils.getApplicationContext();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(mockContext, result);
    }
    
    // 测试用的Bean类
    static class TestBean {
        private String name;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
} 