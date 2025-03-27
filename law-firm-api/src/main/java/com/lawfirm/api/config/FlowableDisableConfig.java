package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Flowable禁用配置
 * 
 * 用于禁用所有Flowable相关组件，避免系统启动时自动初始化Flowable
 */
@Configuration
@EnableAutoConfiguration(exclude = {
    // 由于无法直接引用Flowable的类（避免编译错误），使用字符串形式指定需要排除的自动配置类
    FlywayAutoConfiguration.class
})
@ConditionalOnMissingClass({
    "org.flowable.spring.boot.FlowableAutoConfiguration",
    "org.flowable.engine.ProcessEngine"
})
public class FlowableDisableConfig {
    
    /**
     * 创建Flowable替代Bean，用于防止Spring Boot自动配置尝试创建Flowable相关组件
     */
    @Bean
    public String flowableDisabled() {
        System.out.println("Flowable组件已禁用");
        return "flowableDisabled";
    }
} 