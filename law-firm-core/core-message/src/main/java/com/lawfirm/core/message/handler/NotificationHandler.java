package com.lawfirm.core.message.handler;

import org.springframework.stereotype.Component;
import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.core.message.utils.MessageLogUtils;

@Component
public class NotificationHandler extends BaseMessageHandler<BaseNotify> {

    @Override
    protected void preProcess(String messageId, BaseNotify message) {
        // 验证通知消息的必要字段
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("通知内容不能为空");
        }
        if (message.getReceivers() == null || message.getReceivers().isEmpty()) {
            throw new IllegalArgumentException("通知接收者不能为空");
        }
    }

    @Override
    protected void doHandle(String messageId, BaseNotify message) {
        // 处理通知消息
        MessageLogUtils.logMessageProcess(messageId, "Processing notification: " + message.getContent());
        
        // TODO: 实现具体的通知发送逻辑
        // 1. 根据通知类型选择发送渠道
        // 2. 调用相应的发送服务
        // 3. 记录发送结果
    }

    @Override
    protected void postProcess(String messageId, BaseNotify message) {
        // 更新通知状态
        MessageLogUtils.logMessageProcess(messageId, "Notification processed successfully");
    }
} 