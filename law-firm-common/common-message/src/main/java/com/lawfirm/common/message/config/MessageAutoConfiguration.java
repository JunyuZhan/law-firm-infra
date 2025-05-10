package com.lawfirm.common.message.config;

import com.lawfirm.common.message.properties.MessageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 消息模块自动配置类
 * 
 * 作为消息模块的配置入口，负责集成各种消息通道配置
 */
@Configuration("commonMessageAutoConfiguration")
@EnableConfigurationProperties(MessageProperties.class)
@ConditionalOnProperty(prefix = "law-firm.common.message", name = "enabled", matchIfMissing = true)
@Import({
    RocketMQConfig.class,
    EmailConfig.class
})
public class MessageAutoConfiguration {
    
    private final MessageProperties messageProperties;
    
    public MessageAutoConfiguration(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }
    
    /**
     * 消息通道选择器
     * 根据配置选择合适的消息通道
     */
    @Bean(name = "commonMessageChannelSelector")
    public MessageChannelSelector commonMessageChannelSelector() {
        return new MessageChannelSelector(messageProperties.getDefaultChannel());
    }
    
    /**
     * 消息通道选择器内部类
     */
    public static class MessageChannelSelector {
        private final String defaultChannel;
        
        public MessageChannelSelector(String defaultChannel) {
            this.defaultChannel = defaultChannel;
        }
        
        public String getDefaultChannel() {
            return defaultChannel;
        }
    }
} 