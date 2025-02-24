package com.lawfirm.core.message.config;

import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.core.message.sender.MessageSenderType;
import com.lawfirm.core.message.sender.impl.EmailMessageSender;
import com.lawfirm.core.message.sender.impl.SmsMessageSender;
import com.lawfirm.core.message.sender.impl.WechatMessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息发送器配置
 */
@Configuration
public class MessageSenderConfig {

    @Bean
    public Map<MessageSenderType, MessageSender> messageSenders(List<MessageSender> senders) {
        Map<MessageSenderType, MessageSender> senderMap = new HashMap<>();
        for (MessageSender sender : senders) {
            senderMap.put(sender.getType(), sender);
        }
        return senderMap;
    }

    @Bean
    public MessageSender smsMessageSender(MessageProperties messageProperties) {
        return new SmsMessageSender(messageProperties.getSms());
    }

    @Bean
    public MessageSender emailMessageSender(MessageProperties messageProperties) {
        return new EmailMessageSender(messageProperties.getEmail());
    }

    @Bean
    public MessageSender wechatMessageSender(MessageProperties messageProperties) {
        return new WechatMessageSender(messageProperties.getWechat());
    }
} 