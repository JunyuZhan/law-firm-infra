package com.lawfirm.core.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.lawfirm.common.security.context.SecurityContextHolder;
import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import com.lawfirm.core.message.service.MessageManager;
import com.lawfirm.core.message.service.strategy.RoutingStrategy;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.model.message.service.MessageService;
import com.lawfirm.model.message.dto.message.MessageQueryDTO;
import com.lawfirm.model.message.vo.MessageVO;
import lombok.RequiredArgsConstructor;

/**
 * 消息管理实现类
 */
@Component("messageManagerImpl")
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class MessageManagerImpl implements MessageManager {

    @Autowired
    private RoutingStrategy routingStrategy;

    @Autowired
    private MessageSenderImpl messageSender;

    @Autowired
    private MessageService messageService;

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

    public int getUnreadMessageCount(Long userId, String type, String businessId) {
        // 通过消息服务获取未读消息数量
        // type为消息类型字符串，需转为MessageTypeEnum
        MessageTypeEnum messageType = null;
        try {
            messageType = MessageTypeEnum.valueOf(type);
        } catch (Exception ignored) {}
        MessageQueryDTO queryDTO = new MessageQueryDTO();
        queryDTO.setReceiverId(userId);
        if (messageType != null) {
            queryDTO.setMessageType(messageType.getValue());
        }
        if (businessId != null) {
            try {
                queryDTO.setBusinessId(Long.valueOf(businessId));
            } catch (Exception ignored) {}
        }
        queryDTO.setStatus(1); // 1-未读
        return messageService.listMessages(queryDTO).size();
    }

    public java.util.List<Object> getMessages(String type, String businessId, int page, int size) {
        // 通过消息服务分页获取消息列表
        MessageTypeEnum messageType = null;
        try {
            messageType = MessageTypeEnum.valueOf(type);
        } catch (Exception ignored) {}
        MessageQueryDTO queryDTO = new MessageQueryDTO();
        if (messageType != null) {
            queryDTO.setMessageType(messageType.getValue());
        }
        if (businessId != null) {
            try {
                queryDTO.setBusinessId(Long.valueOf(businessId));
            } catch (Exception ignored) {}
        }
        queryDTO.setPageNum(page);
        queryDTO.setPageSize(size);
        java.util.List<MessageVO> voList = messageService.listMessages(queryDTO);
        return new java.util.ArrayList<>(voList);
    }

    public void markMessagesAsRead(Long userId, java.util.List<String> messageIds) {
        // 通过消息服务批量标记为已读
        if (messageIds != null) {
            for (String id : messageIds) {
                try {
                    messageService.readMessage(Long.valueOf(id));
                } catch (Exception e) {
                    System.out.println("标记消息为已读失败: " + id + ", 用户: " + userId);
                }
            }
        }
    }
} 