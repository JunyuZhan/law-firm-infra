package com.lawfirm.task.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 任务模块自动配置类
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.lawfirm.task.service", "com.lawfirm.task.controller"})
@MapperScan(basePackages = {"com.lawfirm.model.task.mapper"})
public class TaskAutoConfiguration {
}