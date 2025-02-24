package com.lawfirm.core.message.service;

import com.lawfirm.core.message.model.Message;
import com.lawfirm.core.message.model.MessageTemplate;
import com.lawfirm.core.message.model.UserMessageSetting;

import java.util.List;
import java.util.Map;

/**
 * 消息服务接口
 */
public interface MessageService {
    /**
     * 发送消息
     */
    String sendMessage(Message message);

    /**
     * 根据模板发送消息
     */
    String sendTemplateMessage(String templateCode, Map<String, Object> params, 
                             Long receiverId, String businessType, String businessId);

    /**
     * 发送系统通知
     */
    List<String> sendSystemNotice(String title, String content, List<Long> receiverIds);

    /**
     * 标记消息为已读
     */
    void markAsRead(String messageId, Long userId);

    /**
     * 批量标记消息为已读
     */
    void markAsRead(List<String> messageIds, Long userId);

    /**
     * 获取未读消息数量
     */
    long getUnreadCount(Long userId);

    /**
     * 获取消息列表
     */
    List<Message> listMessages(Long userId, int page, int size);

    /**
     * 创建消息模板
     */
    String createTemplate(MessageTemplate template);

    /**
     * 更新消息模板
     */
    void updateTemplate(MessageTemplate template);

    /**
     * 删除消息模板
     */
    void deleteTemplate(String templateId);

    /**
     * 获取消息模板
     */
    MessageTemplate getTemplate(String templateId);

    /**
     * 获取用户消息设置
     */
    UserMessageSetting getUserSetting(Long userId);

    /**
     * 更新用户消息设置
     */
    void updateUserSetting(UserMessageSetting setting);

    /**
     * 订阅消息
     */
    void subscribe(Long userId, String clientId);

    /**
     * 取消订阅
     */
    void unsubscribe(Long userId, String clientId);
}