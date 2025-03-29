package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 异步消息发送实现类
 * 只有在message.async.enabled=true时才启用
 */
@Component("coreAsyncMessageSenderImpl")
@ConditionalOnProperty(prefix = "message.async", name = "enabled", havingValue = "true")
public class AsyncMessageSenderImpl implements MessageSender {

    private final MessageSenderImpl messageSender;

    @Autowired
    public AsyncMessageSenderImpl(@Qualifier("messageSender") MessageSenderImpl messageSender) {
        this.messageSender = messageSender;
    }

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