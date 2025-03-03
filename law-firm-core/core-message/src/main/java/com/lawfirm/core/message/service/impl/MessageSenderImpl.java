package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息发送实现类
 */
@Service
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    @Override
    public void send(BaseMessage message) {
        // TODO: 实现消息发送逻辑
    }

    @Override
    public BaseMessage getMessage(String messageId) {
        // TODO: 实现获取消息逻辑
        return null;
    }

    @Override
    public void deleteMessage(String messageId) {
        // TODO: 实现删除消息逻辑
    }

    @Override
    public void deleteMessages(List<Long> messageIds) {
        // TODO: 实现批量删除消息逻辑
    }
} 