package com.lawfirm.common.log.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.log.aspect.LogAspect;
import com.lawfirm.common.log.properties.LogProperties;
import com.lawfirm.common.log.filter.MdcTraceIdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 日志自动配置类
 * 
 * 提供日志相关功能的统一配置
 */
@Configuration("commonLogAutoConfiguration")
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnProperty(prefix = "law-firm.common.log", name = "enabled", matchIfMissing = true)
public class LogAutoConfiguration {

    /**
     * 配置日志切面
     * 优先使用commonCoreObjectMapper，避免创建重复实例
     */
    @Bean("commonLogAspect")
    public LogAspect logAspect(
            @Autowired @Qualifier("commonCoreObjectMapper") ObjectMapper objectMapper, 
            LogProperties logProperties,
            @Qualifier("commonAsyncLogExecutor") ThreadPoolTaskExecutor asyncLogExecutor) {
        return new LogAspect(objectMapper, logProperties, asyncLogExecutor);
    }

    /**
     * 配置MDC过滤器，用于添加traceId
     */
    @Bean(name = "commonMdcTraceIdFilter")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public MdcTraceIdFilter mdcTraceIdFilter() {
        return new MdcTraceIdFilter();
    }

    /**
     * 配置异步日志线程池
     */
    @Bean("commonAsyncLogExecutor")
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