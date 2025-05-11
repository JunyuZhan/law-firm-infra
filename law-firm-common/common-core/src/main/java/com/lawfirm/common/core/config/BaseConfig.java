package com.lawfirm.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 基础配置类
 * 作为所有配置类的基类，提供基础的系统配置
 */
@Configuration("commonBaseConfig")
@EnableAsync
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BaseConfig {
    
    /**
     * 配置系统级基础线程池
     * 
     * 该线程池专用于系统底层任务执行，与AsyncConfig中的异步任务线程池区分
     * 
     * @return 系统基础线程池
     */
    @Bean("commonSystemBaseTaskExecutor")
    @ConditionalOnMissingBean(name = "commonSystemBaseTaskExecutor")
    public Executor systemBaseTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // 核心线程数
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(10);
        // 队列大小
        executor.setQueueCapacity(100);
        // 线程名前缀 - 使用明确的前缀区分用途
        executor.setThreadNamePrefix("common-system-base-");
        // 线程保活时间
        executor.setKeepAliveSeconds(60);
        // 拒绝策略：由调用线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务完成后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        
        executor.initialize();
        return executor;
    }
} 