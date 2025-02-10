package com.lawfirm.model.base.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 业务消息服务
 */
public interface BusinessMessageService {
    
    /**
     * 发送消息
     */
    String sendMessage(MessageEntity message);
    
    /**
     * 发送定时消息
     */
    String sendMessage(MessageEntity message, LocalDateTime scheduledTime);
    
    /**
     * 发送模板消息
     */
    String sendTemplateMessage(String templateCode, Map<String, Object> params,
                             Long receiverId, String businessType, String businessId);
    
    /**
     * 发送系统通知
     */
    List<String> sendSystemNotice(String title, String content, List<Long> receiverIds);
    
    /**
     * 撤回消息
     */
    void recallMessage(String messageId, Long userId);
    
    /**
     * 标记消息已读
     */
    void markAsRead(String messageId, Long userId);
    
    /**
     * 订阅消息
     */
    void subscribe(Long userId, String clientId);
    
    /**
     * 取消订阅
     */
    void unsubscribe(Long userId, String clientId);
} 