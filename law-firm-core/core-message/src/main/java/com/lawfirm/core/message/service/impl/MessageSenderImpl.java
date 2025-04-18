package com.lawfirm.core.message.service.impl;

import com.lawfirm.common.security.context.SecurityContextHolder;
import com.lawfirm.common.security.crypto.CryptoService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;

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

    @Override
    public void send(BaseMessage message) {
        log.info("发送消息: {}", message != null ? message.getClass().getSimpleName() : "null");
        // 验证发送权限
        if (!SecurityContextHolder.hasPermission("message:send")) {
            throw new SecurityException("无权发送消息");
        }

        // 加密敏感内容
        if (message.isContainsSensitiveData() && cryptoService != null) {
            message.setContent(cryptoService.encrypt(message.getContent()));
        }

        // TODO: 实现消息发送逻辑
    }

    @Override
    public BaseMessage getMessage(String messageId) {
        log.info("获取消息: {}", messageId);
        // 验证查看权限
        if (!SecurityContextHolder.hasPermission("message:view")) {
            throw new SecurityException("无权查看消息");
        }

        // TODO: 实现获取消息逻辑
        return null;
    }

    @Override
    public void deleteMessage(String messageId) {
        log.info("删除消息: {}", messageId);
        // 验证删除权限
        if (!SecurityContextHolder.hasPermission("message:delete")) {
            throw new SecurityException("无权删除消息");
        }

        // TODO: 实现删除消息逻辑
    }
    
    @Override
    public void deleteMessages(List<Long> messageIds) {
        log.info("批量删除消息, 数量: {}", messageIds != null ? messageIds.size() : 0);
        // 验证删除权限
        if (!SecurityContextHolder.hasPermission("message:delete")) {
            throw new SecurityException("无权删除消息");
        }
        
        // TODO: 实现批量删除消息逻辑
    }
} 