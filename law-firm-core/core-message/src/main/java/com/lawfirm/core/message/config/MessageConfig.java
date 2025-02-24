package com.lawfirm.core.message.config;

import com.aliyun.teaopenapi.models.Config;
import com.aliyun.dysmsapi20170525.Client;
import com.lawfirm.common.message.handler.MessageIdempotentHandler;
import com.lawfirm.common.message.handler.MessageRateLimiter;
import com.lawfirm.common.message.metrics.MessageMetrics;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

/**
 * 消息配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "message")
public class MessageConfig {
    
    /**
     * 阿里云短信配置
     */
    private AliyunSmsConfig sms = new AliyunSmsConfig();
    
    /**
     * 微信配置
     */
    private WechatConfig wechat = new WechatConfig();
    
    /**
     * Redis配置
     */
    private RedisConfig redis = new RedisConfig();
    
    /**
     * 线程池配置
     */
    private ThreadPoolConfig threadPool = new ThreadPoolConfig();
    
    @Bean
    public Client smsClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(sms.getAccessKeyId())
                .setAccessKeySecret(sms.getAccessKeySecret())
                .setEndpoint(sms.getEndpoint());
        return new Client(config);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public MessageMetrics messageMetrics() {
        return new MessageMetrics();
    }
    
    @Bean
    public MessageIdempotentHandler messageIdempotentHandler(RedisTemplate<String, Object> redisTemplate) {
        return new MessageIdempotentHandler(redisTemplate, redis.getMessageExpireDays());
    }
    
    @Bean
    public MessageRateLimiter messageRateLimiter(RedisTemplate<String, Object> redisTemplate) {
        return new MessageRateLimiter(redisTemplate);
    }
    
    @Bean("messageTaskExecutor")
    public Executor messageTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPool.getCorePoolSize());
        executor.setMaxPoolSize(threadPool.getMaxPoolSize());
        executor.setQueueCapacity(threadPool.getQueueCapacity());
        executor.setThreadNamePrefix("message-task-");
        executor.initialize();
        return executor;
    }
    
    @Data
    public static class AliyunSmsConfig {
        private String accessKeyId;
        private String accessKeySecret;
        private String endpoint = "dysmsapi.aliyuncs.com";
        private String signName;
    }
    
    @Data
    public static class WechatConfig {
        private String appId;
        private String appSecret;
        private String token;
        private String aesKey;
    }
    
    @Data
    public static class RedisConfig {
        private String messageKeyPrefix = "message:user:";
        private String subscriptionKeyPrefix = "message:subscription:";
        private Integer messageExpireDays = 7;
    }
    
    @Data
    public static class ThreadPoolConfig {
        private int corePoolSize = 5;
        private int maxPoolSize = 10;
        private int queueCapacity = 25;
    }
} 