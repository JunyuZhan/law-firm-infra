package com.lawfirm.core.message.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.lawfirm.core.message.service.impl.MessageManagerImpl;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseMessage;

/**
 * Redis消息监听器
 * 使用cacheRedisTemplate进行消息处理
 */
@Component
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedisMessageListener implements MessageListener {

    /**
     * 注入cacheRedisTemplate作为消息处理模板
     */
    @Autowired
    @Qualifier("commonCacheRedisTemplate")
    private RedisTemplate<String, Object> messageRedisTemplate;

    @Autowired
    private MessageManagerImpl messageManager;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String channel = new String(pattern);
            BaseMessage baseMessage = (BaseMessage) messageRedisTemplate.getValueSerializer()
                    .deserialize(message.getBody());
            
            MessageLogUtils.logMessageReceive(String.valueOf(baseMessage.getId()), baseMessage);
            
            // 处理消息
            messageManager.processMessage(String.valueOf(baseMessage.getId()));
        } catch (Exception e) {
            MessageLogUtils.logMessageError("UNKNOWN", "Redis消息监听处理失败", e);
        }
    }
} 