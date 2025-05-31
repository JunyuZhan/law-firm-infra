package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseNotify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 站内通知服务实现
 * 处理系统内部消息通知
 */
@Slf4j
@Service("internalNotificationService")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class InternalNotificationServiceImpl implements NotificationService {

    @Override
    public void send(BaseNotify message) {
        try {
            String messageId = String.valueOf(message.getId() != null ? message.getId() : System.currentTimeMillis());
            
            MessageLogUtils.logMessageProcess(messageId, "[INTERNAL] 开始发送站内通知");
            
            // 验证消息内容
            if (message.getContent() == null || message.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("通知内容不能为空");
            }
            
            if (message.getReceivers() == null || message.getReceivers().isEmpty()) {
                throw new IllegalArgumentException("通知接收者不能为空");
            }
            
            // 记录发送信息
            log.info("[INTERNAL] 发送站内通知 - 标题: {}, 接收者数量: {}", 
                message.getTitle(), message.getReceivers().size());
            
            // 模拟发送处理（实际项目中这里会保存到数据库通知表）
            for (String receiver : message.getReceivers()) {
                MessageLogUtils.logMessageProcess(messageId, 
                    String.format("[INTERNAL] 向用户 %s 发送通知: %s", receiver, message.getTitle()));
            }
            
            MessageLogUtils.logMessageProcess(messageId, "[INTERNAL] 站内通知发送成功");
            
        } catch (Exception e) {
            log.error("[INTERNAL] 站内通知发送失败", e);
            throw new RuntimeException("站内通知发送失败: " + e.getMessage(), e);
        }
    }
} 