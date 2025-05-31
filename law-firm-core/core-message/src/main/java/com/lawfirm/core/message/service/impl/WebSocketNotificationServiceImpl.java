package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseNotify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * WebSocket通知服务实现
 * 处理实时消息推送
 */
@Slf4j
@Service("webSocketNotificationService")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WebSocketNotificationServiceImpl implements NotificationService {

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void send(BaseNotify message) {
        try {
            String messageId = String.valueOf(message.getId() != null ? message.getId() : System.currentTimeMillis());
            
            MessageLogUtils.logMessageProcess(messageId, "[WEBSOCKET] 开始发送WebSocket通知");
            
            // 验证消息内容
            if (message.getContent() == null || message.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("WebSocket消息内容不能为空");
            }
            
            if (message.getReceivers() == null || message.getReceivers().isEmpty()) {
                throw new IllegalArgumentException("WebSocket消息接收者不能为空");
            }
            
            // 检查WebSocket模板是否可用
            if (messagingTemplate == null) {
                log.warn("[WEBSOCKET] SimpMessagingTemplate 未配置，使用模拟发送");
                simulateWebSocketSend(messageId, message);
                return;
            }
            
            // 实际发送WebSocket消息
            actualWebSocketSend(messageId, message);
            
        } catch (Exception e) {
            log.error("[WEBSOCKET] WebSocket通知发送失败", e);
            throw new RuntimeException("WebSocket通知发送失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 实际WebSocket消息发送
     */
    private void actualWebSocketSend(String messageId, BaseNotify message) {
        try {
            // 构造WebSocket消息
            WebSocketMessage wsMessage = WebSocketMessage.builder()
                .id(messageId)
                .title(message.getTitle())
                .content(message.getContent())
                .type("NOTIFICATION")
                .timestamp(System.currentTimeMillis())
                .build();
            
            // 发送给指定用户
            for (String receiver : message.getReceivers()) {
                messagingTemplate.convertAndSendToUser(
                    receiver, 
                    "/queue/notifications", 
                    wsMessage
                );
                
                MessageLogUtils.logMessageProcess(messageId, 
                    String.format("[WEBSOCKET] 消息已推送至用户: %s", receiver));
            }
            
            // 也可以发送到主题（所有订阅者）
            if (message.getTitle() != null && message.getTitle().contains("系统公告")) {
                messagingTemplate.convertAndSend("/topic/announcements", wsMessage);
                MessageLogUtils.logMessageProcess(messageId, "[WEBSOCKET] 系统公告已广播");
            }
            
            log.info("[WEBSOCKET] WebSocket通知发送成功 - 标题: {}, 接收者数量: {}", 
                message.getTitle(), message.getReceivers().size());
            
            MessageLogUtils.logMessageProcess(messageId, "[WEBSOCKET] WebSocket通知发送完成");
            
        } catch (Exception e) {
            log.error("[WEBSOCKET] 实际WebSocket发送失败", e);
            throw e;
        }
    }
    
    /**
     * 模拟WebSocket发送
     */
    private void simulateWebSocketSend(String messageId, BaseNotify message) {
        log.info("[WEBSOCKET] 模拟WebSocket发送 - 标题: {}, 接收者数量: {}", 
            message.getTitle(), message.getReceivers().size());
        
        for (String receiver : message.getReceivers()) {
            MessageLogUtils.logMessageProcess(messageId, 
                String.format("[WEBSOCKET] 模拟推送至用户: %s, 内容: %s", receiver, message.getContent()));
        }
        
        MessageLogUtils.logMessageProcess(messageId, "[WEBSOCKET] 模拟WebSocket发送完成");
    }
    
    /**
     * WebSocket消息实体
     */
    public static class WebSocketMessage {
        private String id;
        private String title;
        private String content;
        private String type;
        private Long timestamp;
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public Long getTimestamp() { return timestamp; }
        public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
        
        public static Builder builder() {
            return new Builder();
        }
        
        public static class Builder {
            private WebSocketMessage message = new WebSocketMessage();
            
            public Builder id(String id) {
                message.setId(id);
                return this;
            }
            
            public Builder title(String title) {
                message.setTitle(title);
                return this;
            }
            
            public Builder content(String content) {
                message.setContent(content);
                return this;
            }
            
            public Builder type(String type) {
                message.setType(type);
                return this;
            }
            
            public Builder timestamp(Long timestamp) {
                message.setTimestamp(timestamp);
                return this;
            }
            
            public WebSocketMessage build() {
                return message;
            }
        }
    }
} 