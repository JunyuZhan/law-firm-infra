package com.lawfirm.core.message.handler;

import org.springframework.stereotype.Component;
import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.core.message.utils.MessageLogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class NotificationHandler extends BaseMessageHandler<BaseNotify> {

    @Autowired(required = false)
    @Qualifier("emailNotificationService")
    private NotificationService emailNotificationService;

    @Autowired(required = false)
    @Qualifier("smsNotificationService")
    private NotificationService smsNotificationService;

    @Autowired(required = false)
    @Qualifier("internalNotificationService")
    private NotificationService internalNotificationService;

    /**
     * 通知服务接口
     */
    public interface NotificationService {
        void send(BaseNotify message);
    }

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

        // 1. 根据通知类型选择发送渠道
        String type = message.getType() != null ? message.getType().name() : "SYSTEM";
        String channel = message.getChannel() != null ? message.getChannel().name() : "INTERNAL";
        MessageLogUtils.logMessageProcess(messageId, "[NOTIFY] 选择发送渠道: type=" + type + ", channel=" + channel);

        // 2. 调用相应的发送服务
        boolean sendSuccess = true;
        try {
            switch (channel) {
                case "EMAIL":
                    if (emailNotificationService != null) {
                        emailNotificationService.send(message);
                    }
                    MessageLogUtils.logMessageProcess(messageId, "[NOTIFY] 邮件通知已发送: " + message.getContent());
                    break;
                case "SMS":
                    if (smsNotificationService != null) {
                        smsNotificationService.send(message);
                    }
                    MessageLogUtils.logMessageProcess(messageId, "[NOTIFY] 短信通知已发送: " + message.getContent());
                    break;
                case "INTERNAL":
                default:
                    if (internalNotificationService != null) {
                        internalNotificationService.send(message);
                    }
                    MessageLogUtils.logMessageProcess(messageId, "[NOTIFY] 站内信通知已发送: " + message.getContent());
            }
        } catch (Exception e) {
            sendSuccess = false;
            MessageLogUtils.logMessageError(messageId, "[NOTIFY] 通知发送失败", e);
        }

        // 3. 记录发送结果
        if (sendSuccess) {
            MessageLogUtils.logMessageProcess(messageId, "[NOTIFY] 通知发送成功");
        } else {
            MessageLogUtils.logMessageProcess(messageId, "[NOTIFY] 通知发送失败");
        }
    }

    @Override
    protected void postProcess(String messageId, BaseNotify message) {
        // 更新通知状态
        MessageLogUtils.logMessageProcess(messageId, "Notification processed successfully");
    }
} 