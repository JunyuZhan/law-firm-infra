package com.lawfirm.api;

import com.lawfirm.api.config.FlowableConfig;
import com.lawfirm.api.config.WorkflowExclusionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;

/**
 * 法律事务所API应用程序
 */
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"com.lawfirm.common", "com.lawfirm.core"})
@Import({FlowableConfig.class, WorkflowExclusionConfig.class})
public class LawFirmApiApplication {

    static {
        // 禁用Flowable相关功能
        System.setProperty("flowable.enabled", "false");
        System.setProperty("lawfirm.workflow.enabled", "false");
        // 启用存储服务
        System.setProperty("lawfirm.storage.enabled", "true");
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
        application.setLazyInitialization(true);
        application.run(args);
    }
}