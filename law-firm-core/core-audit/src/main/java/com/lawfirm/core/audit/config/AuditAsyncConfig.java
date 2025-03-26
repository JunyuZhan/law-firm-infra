package com.lawfirm.core.audit.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 审计模块专用异步配置
 * 改名为AuditAsyncConfig，避免与其他模块的AsyncConfig冲突
 */
@EnableAsync
@Configuration
@RequiredArgsConstructor
public class AuditAsyncConfig {

    private final AuditProperties auditProperties;

    @Bean("auditAsyncExecutor")
    public Executor auditAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(auditProperties.getAsync().getPoolSize());
        executor.setMaxPoolSize(auditProperties.getAsync().getPoolSize());
        executor.setQueueCapacity(auditProperties.getAsync().getQueueCapacity());
        executor.setThreadNamePrefix("audit-async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
} 