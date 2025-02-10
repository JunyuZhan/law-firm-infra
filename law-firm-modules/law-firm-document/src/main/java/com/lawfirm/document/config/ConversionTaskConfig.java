package com.lawfirm.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 文件转换任务配置
 */
@Configuration
@EnableAsync
public class ConversionTaskConfig {

    /**
     * 文件转换任务线程池
     */
    @Bean("conversionTaskExecutor")
    public Executor conversionTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("conversion-task-");
        executor.initialize();
        return executor;
    }
} 