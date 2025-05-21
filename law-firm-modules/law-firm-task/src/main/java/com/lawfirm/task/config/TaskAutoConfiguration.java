package com.lawfirm.task.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.lawfirm.core.message.service.MessageSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * 任务模块自动配置类
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.lawfirm.task.service", "com.lawfirm.task.controller"})
@MapperScan(basePackages = {"com.lawfirm.model.task.mapper"})
public class TaskAutoConfiguration {

    @Bean(name = "taskMessageSender")
    public MessageSender taskMessageSender(@Qualifier("messageSender") MessageSender messageSender) {
        return messageSender;
    }
}