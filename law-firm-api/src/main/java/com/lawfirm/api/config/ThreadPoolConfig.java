package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * 为系统提供统一的线程池管理
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 创建日志处理线程池
     * 用于异步记录日志等操作
     * 注意：必须使用threadPoolTaskExecutor名称，与LogAspect所需的Bean名称一致
     */
    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(100);
        // 线程名前缀
        executor.setThreadNamePrefix("lawfirm-task-");
        // 拒绝策略：由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();
        return executor;
    }
} 