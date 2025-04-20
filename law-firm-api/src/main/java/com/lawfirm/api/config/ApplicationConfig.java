package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.context.annotation.Import;
import com.lawfirm.system.config.MonitorCacheConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 应用基础配置
 * <p>
 * 导入系统监控缓存配置以确保系统监控功能正常工作
 * 配置应用主线程池和异步处理能力
 * </p>
 */
@Configuration
@EnableAsync
@Import(MonitorCacheConfig.class)
public class ApplicationConfig {

    /**
     * 主线程池配置
     */
    @Bean
    @Primary
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("law-firm-api-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
} 