package com.lawfirm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 律师事务所API应用
 *
 * @since 2022-06-15
 */
@SpringBootApplication(
    // 扫描全部必要的包，确保所有服务都能被正确加载
    scanBasePackages = {
        "com.lawfirm.api", 
        "com.lawfirm.model",
        "com.lawfirm.auth",
        "com.lawfirm.client"
    },
    exclude = {SecurityAutoConfiguration.class}
)
public class LawFirmApiApplication {

    public static void main(String[] args) {
        // 允许循环引用
        System.setProperty("spring.main.allow-circular-references", "true");
        
        // 核心模块功能开关
        System.setProperty("lawfirm.audit.enabled", "false");  // 禁用审计功能
        System.setProperty("lawfirm.workflow.enabled", "false"); // 禁用工作流功能
        System.setProperty("lawfirm.storage.enabled", "false"); // 禁用存储功能
        
        // API文档默认关闭，可通过环境变量ENABLE_API_DOCS启用
        String enableApiDocs = System.getenv("ENABLE_API_DOCS");
        if (!"true".equals(enableApiDocs)) {
            System.setProperty("springdoc.api-docs.enabled", "false");
            System.setProperty("springdoc.swagger-ui.enabled", "false");
        }
        
        // 开发环境下启用所有必要的模块
        System.setProperty("lawfirm.client.enabled", "true");
        System.setProperty("lawfirm.auth.enabled", "true");
        
        SpringApplication.run(LawFirmApiApplication.class, args);
    }
}