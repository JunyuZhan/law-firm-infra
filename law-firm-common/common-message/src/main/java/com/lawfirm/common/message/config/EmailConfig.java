package com.lawfirm.common.message.config;

import com.lawfirm.common.message.properties.MessageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件配置类
 */
@Configuration("commonEmailConfig")
@ConditionalOnProperty(prefix = "law-firm.common.message.email", name = "enabled", havingValue = "true")
public class EmailConfig {
    
    private final MessageProperties messageProperties;
    
    public EmailConfig(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }
    
    /**
     * 邮件发送器
     * 仅在启用Email时创建
     */
    @Bean(name = "commonEmailSender")
    public EmailSender emailSender() {
        return new EmailSender(
            messageProperties.getEmail().getFrom(),
            messageProperties.getEmail().getFromName()
        );
    }
    
    /**
     * 邮件发送器内部类
     */
    public static class EmailSender {
        private final String from;
        private final String fromName;
        
        public EmailSender(String from, String fromName) {
            this.from = from;
            this.fromName = fromName;
        }
        
        // 实际的邮件发送代码可以在这里实现
    }
} 