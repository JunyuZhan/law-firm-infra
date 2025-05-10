package com.lawfirm.common.message.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息模块配置属性
 */
@Data
@ConfigurationProperties(prefix = "law-firm.common.message")
public class MessageProperties {

    /**
     * 是否启用消息功能
     */
    private boolean enabled = true;

    /**
     * 默认消息通道
     */
    private String defaultChannel = "rocketmq";

    /**
     * RocketMQ配置
     */
    private RocketMQ rocketmq = new RocketMQ();

    /**
     * 邮件配置
     */
    private Email email = new Email();

    /**
     * RocketMQ配置项
     */
    @Data
    public static class RocketMQ {
        /**
         * 名称服务器地址
         */
        private String nameServer = "localhost:9876";

        /**
         * 生产者组
         */
        private String producerGroup = "law-firm-producer";
        
        /**
         * 消费者组
         */
        private String consumerGroup = "law-firm-consumer";
        
        /**
         * 是否启用
         */
        private boolean enabled = false;
    }

    /**
     * 邮件配置项
     */
    @Data
    public static class Email {
        /**
         * 发件人
         */
        private String from = "system@lawfirm.com";
        
        /**
         * 发件人显示名称
         */
        private String fromName = "律师事务所系统";
        
        /**
         * 是否启用
         */
        private boolean enabled = false;
    }
} 