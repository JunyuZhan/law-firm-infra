package com.lawfirm.common.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.lawfirm.common.data.config.TestConfig;

@SpringBootApplication(scanBasePackages = {
    "com.lawfirm.common.data",
    "com.lawfirm.common.core",
    "com.lawfirm.model"
})
@EntityScan(basePackages = {
    "com.lawfirm.model.entity",
    "com.lawfirm.common.data.entity"
})
@EnableJpaRepositories(basePackages = {
    "com.lawfirm.common.data.repository",
    "com.lawfirm.model.repository"
})
@Import(TestConfig.class)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
} 