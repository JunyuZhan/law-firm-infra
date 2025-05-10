package com.lawfirm.task.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * 任务模块自动配置类
 */
@Configuration(proxyBeanMethods = false)
@Component("taskAutoConfiguration")
@ComponentScan(basePackages = {"com.lawfirm.task.service", "com.lawfirm.task.controller"})
@MapperScan(basePackages = {"com.lawfirm.model.task.mapper"})
@Import({TaskApiConfig.class})
public class TaskAutoConfiguration {
} 