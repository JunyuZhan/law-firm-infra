package com.lawfirm.core.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;

import java.util.List;

public interface MessageCacheService {
    /**
     * 缓存消息
     */
    void cacheMessage(MessageEntity message);

    /**
     * 获取消息
     */
    MessageEntity getMessage(String messageId);

    /**
     * 缓存消息模板
     */
    void cacheTemplate(MessageTemplateEntity template);

    /**
     * 获取消息模板
     */
    MessageTemplateEntity getTemplate(String templateId);

    /**
     * 缓存用户消息设置
     */
    void cacheUserSetting(UserMessageSettingEntity setting);

    /**
     * 获取用户消息设置
     */
    UserMessageSettingEntity getUserSetting(Long userId);

    /**
     * 获取用户未读消息列表
     */
    List<MessageEntity> getUnreadMessages(Long userId);

    /**
     * 标记消息为已读
     */
    void markMessageAsRead(String messageId);

    /**
     * 删除消息
     */
    void deleteMessage(String messageId);
} 