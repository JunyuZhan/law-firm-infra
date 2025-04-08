package com.lawfirm.task.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 任务模块自动配置类
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.lawfirm.task"})
@MapperScan(basePackages = {"com.lawfirm.task.mapper"})
@Import({TaskApiConfig.class})
public class TaskAutoConfiguration {
} 