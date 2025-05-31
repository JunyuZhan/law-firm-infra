package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseNotify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件通知服务实现
 * 处理邮件发送通知
 */
@Slf4j
@Service("emailNotificationService")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class EmailNotificationServiceImpl implements NotificationService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Override
    public void send(BaseNotify message) {
        try {
            String messageId = String.valueOf(message.getId() != null ? message.getId() : System.currentTimeMillis());
            
            MessageLogUtils.logMessageProcess(messageId, "[EMAIL] 开始发送邮件通知");
            
            // 验证消息内容
            if (message.getContent() == null || message.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("邮件内容不能为空");
            }
            
            if (message.getReceivers() == null || message.getReceivers().isEmpty()) {
                throw new IllegalArgumentException("邮件接收者不能为空");
            }
            
            // 检查邮件发送器是否可用
            if (mailSender == null) {
                log.warn("[EMAIL] JavaMailSender 未配置，使用模拟发送");
                simulateEmailSend(messageId, message);
                return;
            }
            
            // 实际发送邮件
            actualEmailSend(messageId, message);
            
        } catch (Exception e) {
            log.error("[EMAIL] 邮件通知发送失败", e);
            throw new RuntimeException("邮件通知发送失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 实际邮件发送
     */
    private void actualEmailSend(String messageId, BaseNotify message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("system@lawfirm.com"); // 可配置
            mailMessage.setSubject(message.getTitle() != null ? message.getTitle() : "系统通知");
            mailMessage.setText(message.getContent());
            
            // 批量发送
            for (String receiver : message.getReceivers()) {
                mailMessage.setTo(receiver);
                mailSender.send(mailMessage);
                
                MessageLogUtils.logMessageProcess(messageId, 
                    String.format("[EMAIL] 邮件已发送至: %s", receiver));
            }
            
            log.info("[EMAIL] 邮件通知发送成功 - 标题: {}, 接收者数量: {}", 
                message.getTitle(), message.getReceivers().size());
            
            MessageLogUtils.logMessageProcess(messageId, "[EMAIL] 邮件通知发送完成");
            
        } catch (Exception e) {
            log.error("[EMAIL] 实际邮件发送失败", e);
            throw e;
        }
    }
    
    /**
     * 模拟邮件发送（用于开发环境或邮件服务未配置时）
     */
    private void simulateEmailSend(String messageId, BaseNotify message) {
        log.info("[EMAIL] 模拟邮件发送 - 标题: {}, 接收者数量: {}", 
            message.getTitle(), message.getReceivers().size());
        
        for (String receiver : message.getReceivers()) {
            MessageLogUtils.logMessageProcess(messageId, 
                String.format("[EMAIL] 模拟发送邮件至: %s, 内容: %s", receiver, message.getContent()));
        }
        
        MessageLogUtils.logMessageProcess(messageId, "[EMAIL] 模拟邮件发送完成");
    }
} 