package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 异步任务配置类
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 提供默认的异步任务执行器
     * 当没有其他TaskExecutor bean时使用
     */
    @Bean
    @ConditionalOnMissingBean(name = "applicationTaskExecutor")
    public TaskExecutor applicationTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("app-task-");
        executor.setConcurrencyLimit(10);
        return executor;
    }
    
    /**
     * 提供默认的AsyncTaskExecutor
     * 当没有其他AsyncTaskExecutor bean时使用
     */
    @Bean
    @ConditionalOnMissingBean(AsyncTaskExecutor.class)
    public AsyncTaskExecutor asyncTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("async-");
        executor.setConcurrencyLimit(10);
        return executor;
    }
} 