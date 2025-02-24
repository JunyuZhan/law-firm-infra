package com.lawfirm.common.data.aspect;

import com.lawfirm.common.data.annotation.DataSource;
import com.lawfirm.common.data.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
class DataSourceAspectTest {

    @Autowired
    private TestService testService;

    @Test
    void testDataSourceSwitch() {
        assertDoesNotThrow(() -> {
            testService.masterMethod();
            testService.slaveMethod();
        });
    }

    @Test
    void contextLoads() {
        // 测试上下文加载
    }

    @Component
    static class TestService {
        @DataSource("master")
        public void masterMethod() {
            // 模拟主库操作
        }

        @DataSource("slave")
        public void slaveMethod() {
            // 模拟从库操作
        }
    }
} 