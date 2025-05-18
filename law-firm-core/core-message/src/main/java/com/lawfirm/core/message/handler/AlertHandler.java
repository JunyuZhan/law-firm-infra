package com.lawfirm.core.message.handler;

import org.springframework.stereotype.Component;
import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.core.message.utils.MessageLogUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class AlertHandler extends BaseMessageHandler<SystemMessage> {

    @Autowired(required = false)
    private EmergencyService emergencyService;

    /**
     * 应急服务接口（自动工单、值班通知等）
     */
    public interface EmergencyService {
        void createIncident(String messageId, SystemMessage message, String level);
        void notifyOnDuty(String messageId, SystemMessage message, String level);
    }

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

        // 1. 根据预警级别确定处理优先级
        String level = String.valueOf(message.getLevel());
        int priority = 1; // 默认优先级
        switch (level) {
            case "CRITICAL":
                priority = 3;
                break;
            case "HIGH":
                priority = 2;
                break;
            case "MEDIUM":
                priority = 1;
                break;
            case "LOW":
                priority = 0;
                break;
            default:
                priority = 1;
        }
        // 2. 发送预警通知（可对接短信/邮件/钉钉等）
        MessageLogUtils.logMessageProcess(messageId, "[ALERT] 通知发送: level=" + level + ", priority=" + priority + ", content=" + message.getContent());

        // 3. 记录预警事件（可写入数据库/日志/监控平台）
        MessageLogUtils.logMessageProcess(messageId, "[ALERT] 事件记录: " + message.getContent());

        // 4. 触发相应的处理流程（如自动工单、告警升级等）
        if (priority >= 2) {
            // 高优先级自动触发应急流程
            MessageLogUtils.logMessageProcess(messageId, "[ALERT] 触发应急处理流程: level=" + level);
            if (emergencyService != null) {
                emergencyService.createIncident(messageId, message, level);
                emergencyService.notifyOnDuty(messageId, message, level);
            }
        }
    }

    @Override
    protected void postProcess(String messageId, SystemMessage message) {
        // 更新预警状态
        MessageLogUtils.logMessageProcess(messageId, "Alert processed successfully");
    }
} 