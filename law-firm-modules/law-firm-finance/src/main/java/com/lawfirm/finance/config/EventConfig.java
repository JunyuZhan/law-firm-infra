package com.lawfirm.finance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

/**
 * 财务事件配置类
 * 配置事件处理相关的线程池和异步执行器
 */
@Configuration
@EnableAsync
@ConditionalOnProperty(prefix = "law-firm.finance.event", name = "async", havingValue = "true", matchIfMissing = false)
public class EventConfig {
    
    /**
     * 财务事件处理线程池
     * 用于异步处理财务相关事件
     */
    @Bean(name = "financeEventExecutor")
    public Executor financeEventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("finance-event-");
        executor.initialize();
        return executor;
    }
}