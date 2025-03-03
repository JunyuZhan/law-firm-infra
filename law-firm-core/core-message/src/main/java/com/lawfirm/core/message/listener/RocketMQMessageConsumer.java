package com.lawfirm.core.message.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lawfirm.core.message.service.impl.MessageManagerImpl;
import com.lawfirm.core.message.utils.MessageLogUtils;
import com.lawfirm.model.message.entity.base.BaseMessage;

@Component
@RocketMQMessageListener(
    topic = "${message.rocketmq.topic}",
    consumerGroup = "${message.rocketmq.consumer-group}"
)
public class RocketMQMessageConsumer implements org.apache.rocketmq.spring.core.RocketMQListener<BaseMessage> {

    @Autowired
    private MessageManagerImpl messageManager;

    @Override
    public void onMessage(BaseMessage message) {
        try {
            MessageLogUtils.logMessageReceive(String.valueOf(message.getId()), message);
            
            // 处理消息
            messageManager.processMessage(String.valueOf(message.getId()));
        } catch (Exception e) {
            MessageLogUtils.logMessageError(String.valueOf(message.getId()), "RocketMQ消息监听处理失败", e);
            // 根据业务需求决定是否重试或者进入死信队列
            throw e;
        }
    }
} 