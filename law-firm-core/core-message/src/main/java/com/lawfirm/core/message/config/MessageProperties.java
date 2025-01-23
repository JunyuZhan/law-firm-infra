package com.lawfirm.core.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息配置属性
 */
@Data
@ConfigurationProperties(prefix = "message")
public class MessageProperties {
    
    /**
     * RocketMQ 配置
     */
    private RocketMQConfig rocketmq = new RocketMQConfig();
    
    /**
     * Redis 配置
     */
    private RedisConfig redis = new RedisConfig();
    
    /**
     * WebSocket 配置
     */
    private WebSocketConfig websocket = new WebSocketConfig();
    
    @Data
    public static class RocketMQConfig {
        /**
         * 消息主题
         */
        private String topic = "law-firm-message";
        
        /**
         * 消费者组
         */
        private String consumerGroup = "law-firm-message-consumer";
        
        /**
         * 生产者组
         */
        private String producerGroup = "law-firm-message-producer";
    }
    
    @Data
    public static class RedisConfig {
        /**
         * 消息 Key 前缀
         */
        private String messageKeyPrefix = "law-firm:message:";
        
        /**
         * 模板 Key 前缀
         */
        private String templateKeyPrefix = "law-firm:message:template:";
        
        /**
         * 设置 Key 前缀
         */
        private String settingKeyPrefix = "law-firm:message:setting:";
        
        /**
         * 订阅 Key 前缀
         */
        private String subscriptionKeyPrefix = "law-firm:message:subscription:";
        
        /**
         * 消息过期时间（天）
         */
        private int messageExpireDays = 30;
    }
    
    @Data
    public static class WebSocketConfig {
        /**
         * 端点路径
         */
        private String endpoint = "/ws/message";
        
        /**
         * 允许的源
         */
        private String[] allowedOrigins = {"*"};
        
        /**
         * 心跳间隔（秒）
         */
        private int heartbeatInterval = 60;
    }
} 