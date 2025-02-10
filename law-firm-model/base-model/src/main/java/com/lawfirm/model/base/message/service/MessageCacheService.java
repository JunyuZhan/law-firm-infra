package com.lawfirm.model.base.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;
import java.util.List;

/**
 * 消息缓存服务接口
 */
public interface MessageCacheService {
    
    /**
     * 缓存消息
     */
    void cacheMessage(MessageEntity message);

    /**
     * 获取缓存的消息
     */
    MessageEntity getCachedMessage(String messageId);

    /**
     * 获取用户的消息ID列表
     */
    List<String> getUserMessageIds(Long userId, int offset, int limit);

    /**
     * 获取用户未读消息数
     */
    long getUnreadCount(Long userId);

    /**
     * 标记消息已读
     */
    void markAsRead(String messageId, Long userId);

    /**
     * 删除消息缓存
     */
    void deleteMessageCache(String messageId, Long userId);
} 