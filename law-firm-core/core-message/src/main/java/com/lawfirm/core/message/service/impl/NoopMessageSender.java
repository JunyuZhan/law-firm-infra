package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息发送器的空实现
 * 当消息服务被禁用时使用，防止系统因缺少依赖而启动失败
 */
@Slf4j
@Component("messageSender")
@Primary
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "false", matchIfMissing = true)
public class NoopMessageSender implements MessageSender {
    
    private final Map<String, BaseMessage> messageStore = new HashMap<>();
    
    public NoopMessageSender() {
        log.info("初始化空消息发送器 - 消息服务已禁用");
    }
    
    @Override
    public void send(BaseMessage message) {
        log.debug("空消息发送实现-记录消息");
        // 空实现，仅记录消息到内存中（仅用于开发测试）
        if (message != null && message.getId() != null) {
            messageStore.put(message.getId().toString(), message);
        }
    }

    @Override
    public BaseMessage getMessage(String messageId) {
        log.debug("空消息发送实现-获取消息: {}", messageId);
        // 从内存存储中获取消息
        return messageStore.get(messageId);
    }

    @Override
    public void deleteMessage(String messageId) {
        log.debug("空消息发送实现-删除消息: {}", messageId);
        // 从内存存储中删除消息
        messageStore.remove(messageId);
    }

    @Override
    public void deleteMessages(List<Long> messageIds) {
        log.debug("空消息发送实现-批量删除消息");
        // 批量删除消息
        if (messageIds != null) {
            messageIds.forEach(id -> messageStore.remove(id.toString()));
        }
    }
} 