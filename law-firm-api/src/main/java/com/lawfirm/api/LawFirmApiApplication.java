package com.lawfirm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 律师事务所API应用启动类
 * 
 * 作为系统的入口点，负责初始化和启动应用程序。
 * 该类配置了Spring Boot应用程序的基本设置，包括组件扫描路径和其他启动参数。
 * 
 * 注意：所有模块中的Bean应通过明确的命名方式(@Service, @Component等)避免冲突，
 * 而不是通过复杂的扫描配置
 */
@SpringBootApplication(
    scanBasePackages = {
        "com.lawfirm.api",
        "com.lawfirm.common",
        "com.lawfirm.core",
        "com.lawfirm.model",
        "com.lawfirm.modules"
    }
)
@EnableTransactionManagement
public class LawFirmApiApplication {
    /**
     * 应用程序主入口方法
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(LawFirmApiApplication.class, args);
    }
} 