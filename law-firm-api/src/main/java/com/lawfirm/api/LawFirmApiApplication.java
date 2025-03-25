package com.lawfirm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 律师事务所API应用
 *
 * @since 2022-06-15
 */
@SpringBootApplication(
    scanBasePackages = {"com.lawfirm"},
    // dev-noredis环境下排除原始的安全自动配置
    exclude = {SecurityAutoConfiguration.class}
)
@EnableAspectJAutoProxy(exposeProxy = true)
public class LawFirmApiApplication {

    public static void main(String[] args) {
        // 允许循环引用，在启动时配置
        System.setProperty("spring.main.allow-circular-references", "true");
        SpringApplication.run(LawFirmApiApplication.class, args);
    }
} 