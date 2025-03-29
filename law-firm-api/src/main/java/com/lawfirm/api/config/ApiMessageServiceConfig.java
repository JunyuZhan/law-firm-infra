package com.lawfirm.api.config;

import com.lawfirm.common.security.crypto.CryptoService;
import com.lawfirm.core.message.service.MessageTemplateService;
import com.lawfirm.core.message.service.impl.MessageTemplateServiceImpl;
import com.lawfirm.knowledge.config.MessageServiceConfig.MessageSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * API模块消息服务配置
 * 提供消息发送、消息模板和通知等相关服务实现
 */
@Slf4j
@Configuration
public class ApiMessageServiceConfig {

    /**
     * 加密服务Bean
     */
    @Bean
    public CryptoService cryptoService() {
        log.info("创建加密服务");
        return new CryptoService();
    }
    
    /**
     * 消息模板服务实现
     * 添加别名messageTemplateServiceImpl兼容原有注入
     */
    @Bean({"messageTemplateService", "messageTemplateServiceImpl"})
    @ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MessageTemplateService messageTemplateService() {
        log.info("创建消息模板服务");
        return new MessageTemplateServiceImpl();
    }
    
    /**
     * 知识库模块消息服务实现
     */
    @Bean("messageService")
    public MessageSendingService messageService() {
        log.info("创建知识库模块消息服务实现");
        return new MessageSendingService() {
            @Override
            public void sendSystemMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("系统消息发送，模板: {}, 变量: {}, 接收者数量: {}", 
                    templateCode, variables, receiverIds.size());
            }

            @Override
            public void sendEmailMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("邮件消息发送，模板: {}, 变量: {}, 接收者数量: {}", 
                    templateCode, variables, receiverIds.size());
            }
        };
    }
    
    /**
     * RocketMQ功能禁用标记
     */
    @Bean
    @ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "false", matchIfMissing = true)
    public Object rocketMQDisabledChecker() {
        log.info("RocketMQ功能已禁用");
        return new Object();
    }
} 