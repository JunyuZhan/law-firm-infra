package com.lawfirm.common.message.config;

import com.lawfirm.common.message.properties.MessageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ配置类
 */
@Configuration("commonRocketMQConfig")
@ConditionalOnProperty(prefix = "law-firm.common.message.rocketmq", name = "enabled", havingValue = "true")
public class RocketMQConfig {
    
    private final MessageProperties messageProperties;
    
    public RocketMQConfig(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }
    
    /**
     * RocketMQ通道工厂
     * 仅在启用RocketMQ时创建
     */
    @Bean(name = "commonRocketMQChannelFactory")
    public RocketMQChannelFactory rocketMQChannelFactory() {
        return new RocketMQChannelFactory(
            messageProperties.getRocketmq().getNameServer(),
            messageProperties.getRocketmq().getProducerGroup(),
            messageProperties.getRocketmq().getConsumerGroup()
        );
    }
    
    /**
     * RocketMQ通道工厂内部类
     */
    public static class RocketMQChannelFactory {
        private final String nameServer;
        private final String producerGroup;
        private final String consumerGroup;
        
        public RocketMQChannelFactory(String nameServer, String producerGroup, String consumerGroup) {
            this.nameServer = nameServer;
            this.producerGroup = producerGroup;
            this.consumerGroup = consumerGroup;
        }
        
        // 实际的RocketMQ客户端配置代码可以在这里实现
    }
} 