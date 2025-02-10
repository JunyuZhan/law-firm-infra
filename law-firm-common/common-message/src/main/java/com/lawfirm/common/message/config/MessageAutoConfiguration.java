package com.lawfirm.common.message.config;

import com.lawfirm.common.message.handler.MessageIdempotentHandler;
import com.lawfirm.common.message.handler.MessageRateLimiter;
import com.lawfirm.common.message.handler.MessageRetryHandler;
import com.lawfirm.common.message.handler.impl.Bucket4jMessageRateLimiter;
import com.lawfirm.common.message.handler.impl.RedisMessageIdempotentHandler;
import com.lawfirm.common.message.handler.impl.SimpleMessageRetryHandler;
import com.lawfirm.common.message.metrics.MessageMetrics;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 消息自动配置类
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(MessageProperties.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class MessageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(StringRedisTemplate.class)
    @ConditionalOnProperty(prefix = "message.processing.idempotent", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MessageIdempotentHandler messageIdempotentHandler(
            StringRedisTemplate redisTemplate,
            @Validated MessageProperties messageProperties) {
        return new RedisMessageIdempotentHandler(redisTemplate, 
                messageProperties.getProcessing().getIdempotent().getExpireHours());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "message.processing.rate-limit", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MessageRateLimiter messageRateLimiter(@Validated MessageProperties messageProperties) {
        return new Bucket4jMessageRateLimiter(
                messageProperties.getProcessing().getRateLimit().getCapacity(),
                messageProperties.getProcessing().getRateLimit().getRate());
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageMetrics messageMetrics() {
        return new MessageMetrics();
    }

    @Bean
    @ConditionalOnMissingBean(name = "messageTaskExecutor")
    public TaskExecutor messageTaskExecutor(MessageProperties messageProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("message-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "message.processing.retry", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MessageRetryHandler messageRetryHandler(@Validated MessageProperties messageProperties) {
        MessageProperties.Retry retryProps = messageProperties.getProcessing().getRetry();
        return new SimpleMessageRetryHandler(
                retryProps.getMaxAttempts(),
                retryProps.getInitialInterval(),
                retryProps.getMultiplier(),
                retryProps.getMaxInterval()
        );
    }
} 