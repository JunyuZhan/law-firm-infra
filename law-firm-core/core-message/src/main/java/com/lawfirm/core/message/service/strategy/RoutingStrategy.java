package com.lawfirm.core.message.service.strategy;

import org.springframework.stereotype.Component;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoutingStrategy {
    
    private final Map<MessageTypeEnum, BaseMessageHandler<?>> handlers = new ConcurrentHashMap<>();

    public void registerHandler(MessageTypeEnum type, BaseMessageHandler<?> handler) {
        handlers.put(type, handler);
    }

    @SuppressWarnings("unchecked")
    public <T> BaseMessageHandler<T> getHandler(MessageTypeEnum type) {
        BaseMessageHandler<?> handler = handlers.get(type);
        if (handler == null) {
            throw new IllegalArgumentException("未找到消息类型[" + type + "]对应的处理器");
        }
        return (BaseMessageHandler<T>) handler;
    }

    public boolean hasHandler(MessageTypeEnum type) {
        return handlers.containsKey(type);
    }
} 