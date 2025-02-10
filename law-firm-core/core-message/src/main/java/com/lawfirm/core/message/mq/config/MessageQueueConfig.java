package com.lawfirm.core.message.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列配置
 */
@Configuration
public class MessageQueueConfig {
    
    @Value("${message.queue.exchange.direct:message.direct}")
    private String directExchangeName;
    
    @Value("${message.queue.exchange.delay:message.delay}")
    private String delayExchangeName;
    
    @Value("${message.queue.exchange.dlx:message.dlx}")
    private String dlxExchangeName;
    
    @Value("${message.queue.name.direct:message.all}")
    private String directQueueName;
    
    @Value("${message.queue.name.delay:message.delay}")
    private String delayQueueName;
    
    @Value("${message.queue.name.dlq:message.dlq}")
    private String dlqName;
    
    /**
     * 直接交换机
     */
    @Bean
    public DirectExchange messageDirectExchange() {
        return new DirectExchange(directExchangeName);
    }
    
    /**
     * 延迟交换机
     */
    @Bean
    public CustomExchange messageDelayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(delayExchangeName, "x-delayed-message", true, false, args);
    }
    
    /**
     * 死信交换机
     */
    @Bean
    public DirectExchange messageDlxExchange() {
        return new DirectExchange(dlxExchangeName);
    }
    
    /**
     * 实时消息队列
     */
    @Bean
    public Queue messageQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置消息过期时间为7天
        args.put("x-message-ttl", 7 * 24 * 60 * 60 * 1000);
        // 设置队列最大长度
        args.put("x-max-length", 1000000);
        // 设置死信交换机
        args.put("x-dead-letter-exchange", dlxExchangeName);
        args.put("x-dead-letter-routing-key", dlqName);
        
        return QueueBuilder.durable(directQueueName)
                .withArguments(args)
                .build();
    }
    
    /**
     * 延迟消息队列
     */
    @Bean
    public Queue messageDelayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", dlxExchangeName);
        args.put("x-dead-letter-routing-key", dlqName);
        
        return QueueBuilder.durable(delayQueueName)
                .withArguments(args)
                .build();
    }
    
    /**
     * 死信队列
     */
    @Bean
    public Queue messageDlq() {
        return QueueBuilder.durable(dlqName).build();
    }
    
    /**
     * 实时消息绑定
     */
    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue())
                .to(messageDirectExchange())
                .with(directQueueName);
    }
    
    /**
     * 延迟消息绑定
     */
    @Bean
    public Binding messageDelayBinding() {
        return BindingBuilder.bind(messageDelayQueue())
                .to(messageDelayExchange())
                .with(delayQueueName)
                .noargs();
    }
    
    /**
     * 死信队列绑定
     */
    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(messageDlq())
                .to(messageDlxExchange())
                .with(dlqName);
    }
} 