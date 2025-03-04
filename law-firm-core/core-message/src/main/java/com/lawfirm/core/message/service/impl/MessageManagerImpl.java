package com.lawfirm.core.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawfirm.common.security.context.SecurityContextHolder;
import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import com.lawfirm.core.message.service.strategy.RoutingStrategy;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;

@Service
public class MessageManagerImpl {

    @Autowired
    private RoutingStrategy routingStrategy;

    @Autowired
    private MessageSenderImpl messageSender;

    public void processMessage(String messageId) {
        // 验证消息处理权限
        if (!SecurityContextHolder.hasPermission("message:process")) {
            throw new SecurityException("无权处理消息");
        }

        BaseMessage message = messageSender.getMessage(messageId);
        if (message == null) {
            throw new IllegalArgumentException("消息不存在: " + messageId);
        }

        MessageTypeEnum type = message.getType();
        if (!routingStrategy.hasHandler(type)) {
            MessageLogUtils.logMessageError(messageId, "未找到对应的消息处理器", null);
            throw new IllegalStateException("未找到消息类型[" + type + "]对应的处理器");
        }

        try {
            BaseMessageHandler<BaseMessage> handler = routingStrategy.getHandler(type);
            handler.handle(messageId, message);
        } catch (Exception e) {
            MessageLogUtils.logMessageError(messageId, "消息处理失败", e);
            throw e;
        }
    }

    public void registerHandler(MessageTypeEnum type, BaseMessageHandler<?> handler) {
        // 验证注册处理器权限
        if (!SecurityContextHolder.hasPermission("message:handler:register")) {
            throw new SecurityException("无权注册消息处理器");
        }
        routingStrategy.registerHandler(type, handler);
    }
} 