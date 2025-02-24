package com.lawfirm.common.data.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
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
        
        // 验证拦截器
        assertTrue(mybatisPlusInterceptor.getInterceptors().stream()
            .anyMatch(i -> i instanceof PaginationInnerInterceptor),
            "Pagination interceptor should be configured");
            
        assertTrue(mybatisPlusInterceptor.getInterceptors().stream()
            .anyMatch(i -> i instanceof OptimisticLockerInnerInterceptor),
            "Optimistic locker interceptor should be configured");
            
        assertTrue(mybatisPlusInterceptor.getInterceptors().stream()
            .anyMatch(i -> i instanceof BlockAttackInnerInterceptor),
            "Block attack interceptor should be configured");
    }

    @Test
    void contextLoads() {
        // 测试Spring上下文是否正确加载
    }
} 