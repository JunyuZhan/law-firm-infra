package com.lawfirm.api.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 工作流API应用
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lawfirm"})
@SpringBootApplication(scanBasePackages = {"com.lawfirm"})
public class WorkflowApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
    }
} 