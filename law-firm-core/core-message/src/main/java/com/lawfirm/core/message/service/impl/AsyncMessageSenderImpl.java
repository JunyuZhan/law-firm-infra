package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 异步消息发送实现类
 */
@Component("asyncMessageSenderImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AsyncMessageSenderImpl implements MessageSender {

    private final MessageSenderImpl messageSender;

    @Async
    public CompletableFuture<Void> sendAsync(BaseMessage message) {
        return CompletableFuture.runAsync(() -> send(message));
    }

    @Override
    public void send(BaseMessage message) {
        messageSender.send(message);
    }

    @Override
    public BaseMessage getMessage(String messageId) {
        return messageSender.getMessage(messageId);
    }

    @Override
    public void deleteMessage(String messageId) {
        messageSender.deleteMessage(messageId);
    }

    @Override
    public void deleteMessages(List<Long> messageIds) {
        messageSender.deleteMessages(messageIds);
    }
} 