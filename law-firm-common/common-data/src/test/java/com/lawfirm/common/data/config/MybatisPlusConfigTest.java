package com.lawfirm.common.data.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "mybatis-plus.mapper.packages=com.lawfirm.**.mapper"
})
class MybatisPlusConfigTest extends BaseConfigTest {

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Test
    void mybatisPlusInterceptor_ShouldBeConfigured() {
        assertNotNull(mybatisPlusInterceptor, "MyBatis Plus interceptor should be configured");
        
        // 简化后的配置测试，只验证拦截器对象不为空
        assertNotNull(mybatisPlusInterceptor.getInterceptors(), "Interceptors list should not be null");
    }

    @Test
    void contextLoads() {
        // 测试Spring上下文是否正确加载
    }
} 