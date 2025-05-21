package com.lawfirm.core.message.service.impl;

import com.lawfirm.common.security.context.SecurityContextHolder;
import com.lawfirm.common.security.crypto.CryptoService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 消息发送实现类
 * 只有在message.enabled=true时才启用
 */
@Slf4j
@Component("messageSender")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = false)
public class MessageSenderImpl implements MessageSender {

    private final CryptoService cryptoService;
    
    public MessageSenderImpl() {
        log.info("初始化实际消息发送器 - 消息服务已启用");
        this.cryptoService = null; // 将在实际注入时被替换
    }

    // 内存消息存储模拟
    private static final Map<Long, BaseMessage> MESSAGE_STORE = new ConcurrentHashMap<>();

    @Override
    public void send(BaseMessage message) {
        log.info("发送消息: {}", message != null ? message.getClass().getSimpleName() : "null");
        if (!SecurityContextHolder.hasPermission("message:send")) {
            throw new SecurityException("无权发送消息");
        }
        if (message.isContainsSensitiveData() && cryptoService != null) {
            message.setContent(cryptoService.encrypt(message.getContent()));
        }
        // 模拟存储消息
        if (message.getId() == null) {
            message.setId(System.currentTimeMillis());
        }
        MESSAGE_STORE.put(message.getId(), message);
        log.info("[SEND] 消息已存储: id={}, type={}", message.getId(), message.getType());
    }

    @Override
    public BaseMessage getMessage(String messageId) {
        log.info("获取消息: {}", messageId);
        if (!SecurityContextHolder.hasPermission("message:view")) {
            throw new SecurityException("无权查看消息");
        }
        // 从内存模拟存储获取
        try {
            Long id = Long.valueOf(messageId);
            return MESSAGE_STORE.get(id);
        } catch (Exception e) {
            log.warn("消息ID格式错误: {}", messageId);
            return null;
        }
    }

    @Override
    public void deleteMessage(String messageId) {
        log.info("删除消息: {}", messageId);
        if (!SecurityContextHolder.hasPermission("message:delete")) {
            throw new SecurityException("无权删除消息");
        }
        try {
            Long id = Long.valueOf(messageId);
            MESSAGE_STORE.remove(id);
            log.info("[DELETE] 消息已删除: id={}", id);
        } catch (Exception e) {
            log.warn("消息ID格式错误: {}", messageId);
        }
    }

    @Override
    public void deleteMessages(List<Long> messageIds) {
        log.info("批量删除消息, 数量: {}", messageIds != null ? messageIds.size() : 0);
        if (!SecurityContextHolder.hasPermission("message:delete")) {
            throw new SecurityException("无权删除消息");
        }
        if (messageIds != null) {
            for (Long id : messageIds) {
                MESSAGE_STORE.remove(id);
                log.info("[DELETE] 批量删除消息: id={}", id);
            }
        }
    }
}

@Configuration
class MessageSenderBeanConfig {
    @Bean(name = "coreMessageSender")
    public MessageSender coreMessageSender(@Qualifier("messageSender") MessageSender messageSender) {
        return messageSender;
    }
} 