package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseNotify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 短信通知服务实现
 * 处理短信发送通知
 */
@Slf4j
@Service("smsNotificationService")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SmsNotificationServiceImpl implements NotificationService {

    @Override
    public void send(BaseNotify message) {
        try {
            String messageId = String.valueOf(message.getId() != null ? message.getId() : System.currentTimeMillis());
            
            MessageLogUtils.logMessageProcess(messageId, "[SMS] 开始发送短信通知");
            
            // 验证消息内容
            if (message.getContent() == null || message.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("短信内容不能为空");
            }
            
            if (message.getReceivers() == null || message.getReceivers().isEmpty()) {
                throw new IllegalArgumentException("短信接收者不能为空");
            }
            
            // 验证短信内容长度（短信有字符限制）
            if (message.getContent().length() > 300) {
                log.warn("[SMS] 短信内容过长，将截断至300字符");
                message.setContent(message.getContent().substring(0, 300) + "...");
            }
            
            // 模拟发送短信（实际项目中这里会调用阿里云短信API）
            simulateSmsSend(messageId, message);
            
        } catch (Exception e) {
            log.error("[SMS] 短信通知发送失败", e);
            throw new RuntimeException("短信通知发送失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 模拟短信发送
     * TODO: 实际项目中需要集成阿里云短信服务
     */
    private void simulateSmsSend(String messageId, BaseNotify message) {
        log.info("[SMS] 模拟短信发送 - 内容长度: {}, 接收者数量: {}", 
            message.getContent().length(), message.getReceivers().size());
        
        for (String receiver : message.getReceivers()) {
            // 验证手机号格式
            if (!isValidPhoneNumber(receiver)) {
                MessageLogUtils.logMessageProcess(messageId, 
                    String.format("[SMS] 跳过无效手机号: %s", receiver));
                continue;
            }
            
            MessageLogUtils.logMessageProcess(messageId, 
                String.format("[SMS] 模拟发送短信至: %s, 内容: %s", 
                    maskPhoneNumber(receiver), message.getContent()));
        }
        
        MessageLogUtils.logMessageProcess(messageId, "[SMS] 模拟短信发送完成");
    }
    
    /**
     * 验证手机号格式
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        // 简单的手机号验证（中国大陆）
        return phoneNumber.matches("^1[3-9]\\d{9}$");
    }
    
    /**
     * 手机号脱敏显示
     */
    private String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 11) {
            return phoneNumber;
        }
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }
    
    /**
     * 实际短信发送方法（示例）
     * TODO: 集成阿里云短信API
     */
    @SuppressWarnings("unused")
    private void actualSmsSend(String messageId, BaseNotify message) {
        // 示例：阿里云短信发送逻辑
        /*
        try {
            Config config = new Config()
                .setAccessKeyId("your-access-key-id")
                .setAccessKeySecret("your-access-key-secret")
                .setEndpoint("dysmsapi.aliyuncs.com");
                
            Client client = new Client(config);
            
            for (String receiver : message.getReceivers()) {
                SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(receiver)
                    .setSignName("律师事务所")
                    .setTemplateCode("SMS_TEMPLATE_CODE")
                    .setTemplateParam("{\"content\":\"" + message.getContent() + "\"}");
                    
                SendSmsResponse response = client.sendSms(sendSmsRequest);
                
                if ("OK".equals(response.getBody().getCode())) {
                    MessageLogUtils.logMessageProcess(messageId, 
                        String.format("[SMS] 短信发送成功至: %s", maskPhoneNumber(receiver)));
                } else {
                    log.error("[SMS] 短信发送失败: {}", response.getBody().getMessage());
                }
            }
        } catch (Exception e) {
            log.error("[SMS] 阿里云短信发送异常", e);
            throw e;
        }
        */
    }
} 