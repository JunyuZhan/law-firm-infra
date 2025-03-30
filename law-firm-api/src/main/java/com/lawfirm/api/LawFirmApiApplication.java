package com.lawfirm.api;

import com.lawfirm.api.config.OpenApiConfig;
import com.lawfirm.auth.config.AuthAutoConfiguration;
import com.lawfirm.core.workflow.config.WorkflowDisableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 法律事务所API应用程序
 */
@SpringBootApplication(
    scanBasePackages = "com.lawfirm"
)
@ConfigurationPropertiesScan(basePackages = {"com.lawfirm.common", "com.lawfirm.core"})
@Import({OpenApiConfig.class, AuthAutoConfiguration.class, WorkflowDisableAutoConfiguration.class})
@ComponentScan(
    basePackages = {"com.lawfirm"},
    excludeFilters = {
        // 排除所有工作流相关的类
        @ComponentScan.Filter(
            type = FilterType.REGEX, 
            pattern = "com\\.lawfirm\\.core\\.workflow\\..*"
        ),
        // 排除所有Flowable相关的配置类
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*FlowableConfig"
        ),
        // 排除与Flowable相关的自动配置
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = "org\\.flowable\\.spring\\.boot\\..*"
        )
    }
)
public class LawFirmApiApplication {

    static {
        // 设置系统属性
        // 1. 禁用Flowable和工作流
        System.setProperty("flowable.enabled", "false");
        System.setProperty("lawfirm.workflow.enabled", "false");
        System.setProperty("flowable.check-process-definitions", "false");
        System.setProperty("flowable.database-schema-update", "false");
        
        // 2. 启用存储服务
        System.setProperty("lawfirm.storage.enabled", "true");
        
        // 3. 系统排除配置
        System.setProperty("spring.autoconfigure.exclude", 
            "org.flowable.spring.boot.FlowableAutoConfiguration," +
            "org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration," +
            "com.lawfirm.core.workflow.config.FlowableConfig");
    }

    public static void main(String[] args) {
        try {
            SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
            application.setLazyInitialization(true);
            application.run(args);
        } catch (Exception e) {
            System.err.println("应用启动失败: " + e.getMessage());
            e.printStackTrace();
            // 打印更详细的根因
            Throwable cause = e.getCause();
            while (cause != null) {
                System.err.println("故障原因: " + cause.getMessage());
                cause = cause.getCause();
            }
        }
    }
}