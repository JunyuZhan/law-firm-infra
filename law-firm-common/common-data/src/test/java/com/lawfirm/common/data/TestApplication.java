package com.lawfirm.common.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.lawfirm.common.data.config.TestConfig;

/**
 * 测试应用程序
 * <p>
 * 用于common-data模块的测试
 * 不再使用JPA，改为基于MyBatis的数据访问
 * </p>
 */
@SpringBootApplication(scanBasePackages = {
    "com.lawfirm.common.data",
    "com.lawfirm.common.core",
    "com.lawfirm.model"
})
@Import(TestConfig.class)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
} 