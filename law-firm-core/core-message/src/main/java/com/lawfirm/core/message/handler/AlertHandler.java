package com.lawfirm.core.message.handler;

import org.springframework.stereotype.Component;
import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.core.message.utils.MessageLogUtils;

@Component
public class AlertHandler extends BaseMessageHandler<SystemMessage> {

    @Override
    protected void preProcess(String messageId, SystemMessage message) {
        // 验证预警消息的必要字段
        if (message.getLevel() == null) {
            throw new IllegalArgumentException("预警级别不能为空");
        }
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("预警内容不能为空");
        }
    }

    @Override
    protected void doHandle(String messageId, SystemMessage message) {
        // 处理预警消息
        MessageLogUtils.logMessageProcess(messageId, "Processing alert: " + message.getContent());
        
        // TODO: 实现具体的预警处理逻辑
        // 1. 根据预警级别确定处理优先级
        // 2. 发送预警通知
        // 3. 记录预警事件
        // 4. 触发相应的处理流程
    }

    @Override
    protected void postProcess(String messageId, SystemMessage message) {
        // 更新预警状态
        MessageLogUtils.logMessageProcess(messageId, "Alert processed successfully");
    }
} 