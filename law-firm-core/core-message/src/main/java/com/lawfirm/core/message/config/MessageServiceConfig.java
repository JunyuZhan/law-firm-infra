package com.lawfirm.core.message.config;

import com.lawfirm.common.security.crypto.CryptoService;
import com.lawfirm.core.message.service.MessageTemplateService;
import com.lawfirm.core.message.service.impl.MessageTemplateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 消息服务配置
 * <p>
 * 提供消息发送、消息模板和通知等相关服务实现
 * </p>
 */
@Slf4j
@Configuration
public class MessageServiceConfig {

    /**
     * 加密服务Bean
     */
    @Bean(name = "cryptoService")
    @ConditionalOnProperty(name = "message.crypto.enabled", havingValue = "true", matchIfMissing = true)
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
     * 通用消息服务接口
     * 可由各模块根据需要实现
     */
    public interface MessageSendingService {
        /**
         * 发送系统消息
         *
         * @param templateCode 模板代码
         * @param variables 模板变量
         * @param receiverIds 接收者ID列表
         */
        void sendSystemMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds);

        /**
         * 发送邮件消息
         *
         * @param templateCode 模板代码
         * @param variables 模板变量
         * @param receiverIds 接收者ID列表
         */
        void sendEmailMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds);
    }
    
    /**
     * 默认消息服务实现
     */
    @Bean("defaultMessageService")
    @ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MessageSendingService defaultMessageService() {
        log.info("创建默认消息服务实现");
        return new MessageSendingService() {
            @Override
            public void sendSystemMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("系统消息发送，模板: {}, 变量: {}, 接收者数量: {}", 
                    templateCode, variables, receiverIds != null ? receiverIds.size() : 0);
            }

            @Override
            public void sendEmailMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("邮件消息发送，模板: {}, 变量: {}, 接收者数量: {}", 
                    templateCode, variables, receiverIds != null ? receiverIds.size() : 0);
            }
        };
    }
    
    /**
     * RocketMQ功能禁用标记
     */
    @Bean(name = "rocketMQDisabledChecker")
    @ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "false", matchIfMissing = true)
    public Object rocketMQDisabledChecker() {
        log.info("RocketMQ功能已禁用");
        return new Object();
    }
} 