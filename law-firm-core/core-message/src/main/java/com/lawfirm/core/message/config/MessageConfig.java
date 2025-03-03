package com.lawfirm.core.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 消息配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "message")
public class MessageConfig {

    /**
     * 发送者配置
     */
    private SenderConfig sender = new SenderConfig();
    private ReceiverConfig receiver;
    private StorageConfig storage;

    @Data
    public static class SenderConfig {
        /**
         * 异步线程池大小
         */
        private int asyncPoolSize = 10;

        /**
         * 异步线程池最大大小
         */
        private int asyncPoolMaxSize = 20;

        /**
         * 异步线程池队列容量
         */
        private int asyncPoolQueueCapacity = 1000;

        public int getAsyncPoolSize() {
            return asyncPoolSize;
        }

        public int getAsyncPoolMaxSize() {
            return asyncPoolMaxSize;
        }

        public int getAsyncPoolQueueCapacity() {
            return asyncPoolQueueCapacity;
        }
    }

    @Data
    public static class ReceiverConfig {
        private int poolSize = 10;
        private int maxConcurrent = 100;
    }

    @Data
    public static class StorageConfig {
        private String type = "redis";
        private String ttl = "7d";
    }

    @Bean
    public ThreadPoolTaskExecutor messageTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(sender.getAsyncPoolSize());
        executor.setMaxPoolSize(sender.getAsyncPoolMaxSize());
        executor.setQueueCapacity(sender.getAsyncPoolQueueCapacity());
        executor.setThreadNamePrefix("message-async-");
        executor.initialize();
        return executor;
    }
} 