package com.lawfirm.core.message.facade;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.model.message.entity.business.CaseMessage;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息门面类
 */
@Component
@RequiredArgsConstructor
public class MessageFacade {

    private final MessageSender messageSender;

    /**
     * 发送通知
     */
    public void sendNotify(BaseNotify notify, List<String> receivers, NotifyChannelEnum channel) {
        notify.setReceivers(receivers);
        notify.setChannel(channel);
        messageSender.send(notify);
    }

    /**
     * 发送案件消息
     */
    public void sendCaseMessage(CaseMessage message, Long caseId, List<String> receivers) {
        message.setCaseId(caseId);
        message.setReceivers(receivers);
        message.setType(MessageTypeEnum.CASE);
        messageSender.send(message);
    }

    /**
     * 发送系统消息
     */
    public void sendSystemMessage(SystemMessage message, Integer type, List<String> receivers) {
        message.setType(MessageTypeEnum.SYSTEM);
        message.setReceivers(receivers);
        messageSender.send(message);
    }

    /**
     * 获取消息
     */
    public BaseMessage getMessage(String messageId) {
        return messageSender.getMessage(messageId);
    }

    /**
     * 删除消息
     */
    public void deleteMessage(String messageId) {
        messageSender.deleteMessage(messageId);
    }
} 