package com.lawfirm.common.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务配置类
 * <p>
 * 提供用于业务异步任务执行的线程池，区别于系统基础线程池
 * </p>
 */
@Slf4j
@Configuration("commonAsyncConfig")
@EnableAsync
public class AsyncConfig {

    /**
     * 提供应用层异步任务执行器
     * <p>
     * 用于处理应用层的异步任务
     * </p>
     * 
     * @return 应用任务执行器
     */
    @Bean(name = "commonApplicationTaskExecutor")
    @ConditionalOnMissingBean(name = "commonApplicationTaskExecutor")
    public TaskExecutor applicationTaskExecutor() {
        log.info("创建应用层异步任务执行器");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("common-app-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
    
    /**
     * 提供专用于业务逻辑的异步任务执行器
     * <p>
     * 当没有其他AsyncTaskExecutor bean时使用
     * </p>
     * 
     * @return 异步任务执行器
     */
    @Bean(name = "commonBusinessAsyncTaskExecutor")
    @ConditionalOnMissingBean(name = "commonBusinessAsyncTaskExecutor")
    public AsyncTaskExecutor businessAsyncTaskExecutor() {
        log.info("创建业务逻辑异步任务执行器");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(120);
        executor.setThreadNamePrefix("common-business-async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
} 