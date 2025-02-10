package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.constant.MessageConstants;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.service.MessageCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 消息缓存服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageCacheServiceImpl implements MessageCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void cacheMessage(MessageEntity message) {
        try {
            String messageKey = MessageConstants.RedisKeyPrefix.MESSAGE_DETAIL + message.getId();
            String userMessageKey = MessageConstants.RedisKeyPrefix.USER_MESSAGE + message.getReceiverId();
            
            // 缓存消息详情
            redisTemplate.opsForValue().set(messageKey, message, 
                    MessageConstants.ExpireTime.MESSAGE_EXPIRE_DAYS, TimeUnit.DAYS);
            
            // 添加到用户的消息列表
            redisTemplate.opsForZSet().add(userMessageKey, message.getId(), 
                    message.getCreateTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
            redisTemplate.expire(userMessageKey, MessageConstants.ExpireTime.MESSAGE_EXPIRE_DAYS, TimeUnit.DAYS);
            
            // 未读消息计数
            if (!Boolean.TRUE.equals(message.getIsRead())) {
                String unreadKey = MessageConstants.RedisKeyPrefix.USER_UNREAD + message.getReceiverId();
                redisTemplate.opsForValue().increment(unreadKey);
                redisTemplate.expire(unreadKey, MessageConstants.ExpireTime.MESSAGE_EXPIRE_DAYS, TimeUnit.DAYS);
            }
            
            log.info("Message cached successfully: id={}, type={}", message.getId(), message.getType());
        } catch (Exception e) {
            log.error("Failed to cache message: " + message.getId(), e);
            throw new RuntimeException("Failed to cache message", e);
        }
    }

    @Override
    public MessageEntity getCachedMessage(String messageId) {
        try {
            return (MessageEntity) redisTemplate.opsForValue().get(
                    MessageConstants.RedisKeyPrefix.MESSAGE_DETAIL + messageId);
        } catch (Exception e) {
            log.error("Failed to get cached message: " + messageId, e);
            return null;
        }
    }

    @Override
    public List<String> getUserMessageIds(Long userId, int offset, int limit) {
        try {
            Set<Object> messageIds = redisTemplate.opsForZSet().reverseRange(
                    MessageConstants.RedisKeyPrefix.USER_MESSAGE + userId, offset, offset + limit - 1);
            return messageIds != null ? messageIds.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList()) : List.of();
        } catch (Exception e) {
            log.error("Failed to get user message ids: " + userId, e);
            return List.of();
        }
    }

    @Override
    public long getUnreadCount(Long userId) {
        try {
            String unreadKey = MessageConstants.RedisKeyPrefix.USER_UNREAD + userId;
            Object count = redisTemplate.opsForValue().get(unreadKey);
            return count != null ? Long.parseLong(count.toString()) : 0;
        } catch (Exception e) {
            log.error("Failed to get unread count: " + userId, e);
            return 0;
        }
    }

    @Override
    public void markAsRead(String messageId, Long userId) {
        try {
            MessageEntity message = getCachedMessage(messageId);
            if (message != null && !Boolean.TRUE.equals(message.getIsRead())) {
                // 更新消息状态
                message.setIsRead(true);
                redisTemplate.opsForValue().set(
                        MessageConstants.RedisKeyPrefix.MESSAGE_DETAIL + messageId, 
                        message, 
                        MessageConstants.ExpireTime.MESSAGE_EXPIRE_DAYS, 
                        TimeUnit.DAYS);
                
                // 更新未读计数
                String unreadKey = MessageConstants.RedisKeyPrefix.USER_UNREAD + userId;
                redisTemplate.opsForValue().decrement(unreadKey);
                
                log.info("Message marked as read: id={}", messageId);
            }
        } catch (Exception e) {
            log.error("Failed to mark message as read: " + messageId, e);
            throw new RuntimeException("Failed to mark message as read", e);
        }
    }

    @Override
    public void deleteMessageCache(String messageId, Long userId) {
        try {
            String messageKey = MessageConstants.RedisKeyPrefix.MESSAGE_DETAIL + messageId;
            String userMessageKey = MessageConstants.RedisKeyPrefix.USER_MESSAGE + userId;
            
            redisTemplate.delete(messageKey);
            redisTemplate.opsForZSet().remove(userMessageKey, messageId);
            
            log.info("Message cache deleted: id={}", messageId);
        } catch (Exception e) {
            log.error("Failed to delete message cache: " + messageId, e);
            throw new RuntimeException("Failed to delete message cache", e);
        }
    }
} 