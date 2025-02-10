package com.lawfirm.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 预览任务配置
 */
@Configuration
@EnableAsync
@EnableScheduling
public class PreviewTaskConfig {

    /**
     * 预览任务线程池
     */
    @Bean("previewTaskExecutor")
    public Executor previewTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("preview-task-");
        executor.initialize();
        return executor;
    }
} 