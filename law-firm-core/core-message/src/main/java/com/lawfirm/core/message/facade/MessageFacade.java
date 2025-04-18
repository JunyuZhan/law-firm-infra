package com.lawfirm.core.message.facade;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.model.message.entity.business.CaseMessage;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 消息门面类
 * 提供统一的消息发送接口，内部依赖MessageSender
 */
@Slf4j
@RequiredArgsConstructor
public class MessageFacade {

    private final MessageSender messageSender;

    /**
     * 发送通知
     */
    public void sendNotify(BaseNotify notify, List<String> receivers, NotifyChannelEnum channel) {
        log.debug("发送通知消息，接收者数量: {}", receivers != null ? receivers.size() : 0);
        notify.setReceivers(receivers);
        notify.setChannel(channel);
        messageSender.send(notify);
    }

    /**
     * 发送案件消息
     */
    public void sendCaseMessage(CaseMessage message, Long caseId, List<String> receivers) {
        log.debug("发送案件消息，案件ID: {}, 接收者数量: {}", caseId, receivers != null ? receivers.size() : 0);
        message.setCaseId(caseId);
        message.setReceivers(receivers);
        message.setType(MessageTypeEnum.CASE);
        messageSender.send(message);
    }

    /**
     * 发送系统消息
     */
    public void sendSystemMessage(SystemMessage message, Integer type, List<String> receivers) {
        log.debug("发送系统消息，类型: {}, 接收者数量: {}", type, receivers != null ? receivers.size() : 0);
        message.setType(MessageTypeEnum.SYSTEM);
        message.setReceivers(receivers);
        messageSender.send(message);
    }

    /**
     * 获取消息
     */
    public BaseMessage getMessage(String messageId) {
        log.debug("获取消息，ID: {}", messageId);
        return messageSender.getMessage(messageId);
    }

    /**
     * 删除消息
     */
    public void deleteMessage(String messageId) {
        log.debug("删除消息，ID: {}", messageId);
        messageSender.deleteMessage(messageId);
    }
} 