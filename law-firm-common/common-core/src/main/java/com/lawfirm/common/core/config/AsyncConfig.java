package com.lawfirm.common.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步任务配置类
 * <p>
 * 提供通用的异步任务执行器
 * </p>
 */
@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 提供默认的异步任务执行器
     * <p>
     * 当没有其他TaskExecutor bean时使用
     * </p>
     * 
     * @return 应用任务执行器
     */
    @Bean(name = "applicationTaskExecutor")
    @ConditionalOnMissingBean(name = "applicationTaskExecutor")
    public TaskExecutor applicationTaskExecutor() {
        log.info("创建默认应用任务执行器");
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("app-task-");
        executor.setConcurrencyLimit(10);
        return executor;
    }
    
    /**
     * 提供默认的AsyncTaskExecutor
     * <p>
     * 当没有其他AsyncTaskExecutor bean时使用
     * </p>
     * 
     * @return 异步任务执行器
     */
    @Bean(name = "asyncTaskExecutor")
    @ConditionalOnMissingBean(AsyncTaskExecutor.class)
    public AsyncTaskExecutor asyncTaskExecutor() {
        log.info("创建默认异步任务执行器");
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("async-");
        executor.setConcurrencyLimit(10);
        return executor;
    }
} 