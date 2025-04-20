package com.lawfirm.knowledge.config;

import com.lawfirm.core.message.service.MessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 知识库模块消息服务配置
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "knowledge", name = "message.enabled", havingValue = "true", matchIfMissing = true)
public class KnowledgeMessageServiceConfig {

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
    @Bean("knowledgeMessageServiceImpl")
    @ConditionalOnMissingBean(name = "knowledgeMessageServiceImpl")
    public MessageSendingService knowledgeMessageServiceImpl() {
        log.warn("未检测到知识库消息服务实现，使用默认空实现");
        return new MessageSendingService() {
            @Override
            public void sendSystemMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("知识库系统消息发送，模板: {}, 接收者数量: {}", templateCode, receiverIds.size());
            }

            @Override
            public void sendEmailMessage(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
                log.info("知识库邮件消息发送，模板: {}, 接收者数量: {}", templateCode, receiverIds.size());
            }
        };
    }
    
    /**
     * 消息模板服务空实现（当消息服务关闭时提供）
     */
    @Bean("messageTemplateServiceImpl")
    @ConditionalOnMissingBean(name = "messageTemplateServiceImpl")
    public MessageTemplateService messageTemplateServiceImpl() {
        log.warn("未检测到消息模板服务实现，使用默认空实现");
        return new MessageTemplateService() {
            private final Map<String, String> templates = new ConcurrentHashMap<>();
            
            @Override
            public boolean registerTemplate(String templateKey, String templateContent) {
                log.info("消息模板注册 (空实现): {}", templateKey);
                templates.put(templateKey, templateContent);
                return true;
            }
            
            @Override
            public String getTemplate(String templateKey) {
                log.info("获取消息模板 (空实现): {}", templateKey);
                return templates.getOrDefault(templateKey, "");
            }
            
            @Override
            public boolean removeTemplate(String templateKey) {
                log.info("删除消息模板 (空实现): {}", templateKey);
                templates.remove(templateKey);
                return true;
            }
        };
    }
}
