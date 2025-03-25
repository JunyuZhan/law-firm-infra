package com.lawfirm.common.log.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.log.aspect.LogAspect;
import com.lawfirm.common.log.properties.LogProperties;
import com.lawfirm.common.log.filter.MdcTraceIdFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 日志自动配置类
 */
@Configuration
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {

    /**
     * 配置日志切面
     */
    @Bean("commonLogAspect")
    public LogAspect logAspect(ObjectMapper objectMapper, 
                              LogProperties logProperties,
                              ThreadPoolTaskExecutor asyncLogExecutor) {
        return new LogAspect(objectMapper, logProperties, asyncLogExecutor);
    }

    /**
     * 配置MDC过滤器，用于添加traceId
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public MdcTraceIdFilter mdcTraceIdFilter() {
        return new MdcTraceIdFilter();
    }

    /**
     * 配置异步日志线程池
     */
    @Bean("asyncLogExecutor")
    public ThreadPoolTaskExecutor asyncLogExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(2);
        // 最大线程数
        executor.setMaxPoolSize(5);
        // 队列容量
        executor.setQueueCapacity(100);
        // 线程名前缀
        executor.setThreadNamePrefix("async-log-");
        // 拒绝策略：调用者运行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务完成后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
} 