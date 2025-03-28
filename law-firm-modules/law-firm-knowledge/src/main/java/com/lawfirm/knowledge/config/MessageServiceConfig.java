package com.lawfirm.knowledge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 消息服务配置
 */
@Slf4j
@Configuration
public class MessageServiceConfig {

    /**
     * 系统消息和邮件消息功能接口
     */
    public interface MessageSendingService {
        void sendSystemMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds);
        void sendEmailMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds);
    }

    /**
     * 消息服务空实现（当未检测到消息服务实现时提供）
     */
    @Bean
    @ConditionalOnMissingBean(name = "messageServiceImpl")
    public MessageSendingService messageServiceImpl() {
        log.warn("未检测到消息服务实现，使用默认空实现");
        return new MessageSendingService() {
            @Override
            public void sendSystemMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("系统消息发送，模板: {}, 接收者数量: {}", templateCode, receiverIds.size());
            }

            @Override
            public void sendEmailMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("邮件消息发送，模板: {}, 接收者数量: {}", templateCode, receiverIds.size());
            }
        };
    }
} 