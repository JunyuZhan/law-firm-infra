package com.lawfirm.task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.lawfirm.core.message.service.MessageSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * 任务模块自动配置类
 * 注意：Mapper扫描已在主应用中统一配置，此处不再重复
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.lawfirm.task.service", "com.lawfirm.task.controller"})
public class TaskAutoConfiguration {

    @Bean(name = "taskMessageSender")
    public MessageSender taskMessageSender(@Qualifier("messageSender") MessageSender messageSender) {
        return messageSender;
    }
}