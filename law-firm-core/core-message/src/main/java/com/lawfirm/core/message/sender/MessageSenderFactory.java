package com.lawfirm.core.message.sender;

import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.enums.MessageSenderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Set;

/**
 * 消息发送器工厂
 */
@Component
@RequiredArgsConstructor
public class MessageSenderFactory {
    
    private final Map<MessageSenderType, MessageSender> senderMap;
    
    public MessageSenderFactory(List<MessageSender> senders) {
        this.senderMap = senders.stream()
            .collect(Collectors.toMap(MessageSender::getType, Function.identity()));
    }
    
    /**
     * 获取指定类型的消息发送器
     *
     * @param type 发送器类型
     * @return 消息发送器
     */
    public MessageSender getSender(MessageSenderType type) {
        MessageSender sender = senderMap.get(type);
        if (sender == null) {
            throw new IllegalArgumentException("Unsupported message sender type: " + type);
        }
        return sender;
    }
    
    /**
     * 判断是否支持指定类型的发送器
     *
     * @param type 发送器类型
     * @return 是否支持
     */
    public boolean supports(MessageSenderType type) {
        return senderMap.containsKey(type);
    }
    
    /**
     * 获取所有支持的发送器类型
     *
     * @return 发送器类型集合
     */
    public Set<MessageSenderType> getSupportedTypes() {
        return senderMap.keySet();
    }
} 