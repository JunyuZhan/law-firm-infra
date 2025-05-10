package com.lawfirm.common.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 通用Bean配置类
 * 提供系统核心Bean的配置和定义
 */
@Configuration("commonBeanConfig")
public class BeanConfig {
    
    /**
     * 配置ObjectMapper，处理日期序列化
     * 
     * @return 自定义配置的ObjectMapper
     */
    @Bean(name = "commonCoreObjectMapper")
    @Primary
    @ConditionalOnMissingBean(name = "commonCoreObjectMapper")
    public ObjectMapper commonCoreObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
    
    /**
     * 配置通用线程池执行器
     * 
     * @return 线程池执行器
     */
    @Bean(name = "commonCoreTaskExecutor")
    @ConditionalOnMissingBean(name = "commonCoreTaskExecutor")
    public TaskExecutor commonCoreTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("core-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
    
    /**
     * 方法级别的参数校验处理器
     * 
     * @return 方法校验后处理器
     */
    @Bean(name = "commonMethodValidationPostProcessor")
    @ConditionalOnMissingBean(name = "commonMethodValidationPostProcessor")
    public MethodValidationPostProcessor commonMethodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
} 