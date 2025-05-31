package com.lawfirm.client.service;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.core.message.service.MessageTemplateService;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
import com.lawfirm.client.constant.ClientModuleConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 客户模块消息服务
 * 专门处理客户相关的消息通知
 * 
 * <p>设计特点：</p>
 * <ul>
 *   <li>专注客户业务场景</li>
 *   <li>双重保险机制：优先core-message，回退本地实现</li>
 *   <li>完整的客户生命周期通知</li>
 * </ul>
 */
@Slf4j
@Service("clientMessageService")
public class ClientMessageService {

    // Core消息服务依赖（优先使用）
    @Autowired(required = false)
    @Qualifier("messageSender")
    private MessageSender messageSender;

    @Autowired(required = false)
    @Qualifier("emailNotificationService")
    private NotificationService emailService;

    @Autowired(required = false)
    @Qualifier("smsNotificationService")
    private NotificationService smsService;

    @Autowired(required = false)
    @Qualifier("internalNotificationService")
    private NotificationService internalService;

    @Autowired(required = false)
    @Qualifier("messageTemplateService")
    private MessageTemplateService templateService;

    // 本地降级服务
    @Autowired(required = false)
    private LocalClientNotificationService localNotificationService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ================================ 客户业务场景通知方法 ================================

    /**
     * 发送客户创建通知
     */
    public void sendClientCreatedNotification(Long clientId, String clientName, Long assigneeId, Map<String, Object> variables) {
        if (assigneeId == null) {
            log.warn("客户创建通知：接收者ID为空，clientId={}", clientId);
            return;
        }

        try {
            // 优先使用core-message
            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle("新客户分配：" + clientName);
                message.setContent("您有新客户需要跟进，请及时联系。");
                message.setReceiverId(assigneeId);
                message.setBusinessId(clientId);
                message.setType(MessageTypeEnum.NOTICE);
                
                messageSender.send(message);
                log.info("[客户消息] 客户创建通知已发送: clientId={}, assigneeId={}", clientId, assigneeId);
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.send("client_created", assigneeId, "新客户分配：" + clientName, "您有新客户需要跟进，请及时联系。", variables);
                log.info("[客户消息][降级] 客户创建通知已发送: clientId={}, assigneeId={}", clientId, assigneeId);
                return;
            }

            // 最后的日志记录
            log.info("[客户消息][日志] 客户创建: clientId={}, clientName={}, assigneeId={}", clientId, clientName, assigneeId);

        } catch (Exception e) {
            log.error("发送客户创建通知失败: clientId={}, error={}", clientId, e.getMessage());
            // 降级处理
            fallbackClientNotification("客户创建", clientId, clientName, assigneeId);
        }
    }

    /**
     * 发送客户状态变更通知
     */
    public void sendClientStatusChangedNotification(Long clientId, String clientName, String oldStatus, String newStatus, List<Long> assigneeIds) {
        if (assigneeIds == null || assigneeIds.isEmpty()) {
            log.warn("客户状态变更通知：接收者列表为空，clientId={}", clientId);
            return;
        }

        try {
            String title = "客户状态变更：" + clientName;
            String content = String.format("客户状态已从 %s 变更为 %s", oldStatus, newStatus);

            // 批量发送通知
            for (Long assigneeId : assigneeIds) {
                sendClientNotification(clientId, title, content, assigneeId, MessageTypeEnum.NOTICE);
            }

            log.info("[客户消息] 客户状态变更通知已发送: clientId={}, oldStatus={}, newStatus={}, recipients={}", 
                    clientId, oldStatus, newStatus, assigneeIds.size());

        } catch (Exception e) {
            log.error("发送客户状态变更通知失败: clientId={}, error={}", clientId, e.getMessage());
        }
    }

    /**
     * 发送跟进提醒通知
     */
    public void sendFollowUpReminder(Long followUpId, Long clientId, String clientName, Long assigneeId, LocalDateTime followUpTime) {
        if (assigneeId == null || followUpTime == null) return;

        try {
            String followUpTimeStr = followUpTime.format(DATE_TIME_FORMATTER);
            String title = "客户跟进提醒：" + clientName;
            String content = String.format("您有客户需要跟进，计划时间：%s", followUpTimeStr);
            
            sendClientNotification(clientId, title, content, assigneeId, MessageTypeEnum.NOTICE);
            
            log.info("[客户消息] 跟进提醒已发送: followUpId={}, clientId={}, assigneeId={}, followUpTime={}", 
                    followUpId, clientId, assigneeId, followUpTimeStr);

        } catch (Exception e) {
            log.error("发送跟进提醒失败: followUpId={}, error={}", followUpId, e.getMessage());
        }
    }

    /**
     * 发送紧急跟进提醒
     */
    public void sendUrgentFollowUpReminder(Long followUpId, Long clientId, String clientName, Long assigneeId, LocalDateTime followUpTime) {
        if (assigneeId == null || followUpTime == null) return;

        try {
            String followUpTimeStr = followUpTime.format(DATE_TIME_FORMATTER);
            String title = "紧急跟进提醒：" + clientName;
            String content = String.format("您有客户需要紧急跟进，计划时间：%s，请立即处理！", followUpTimeStr);
            
            sendClientNotification(clientId, title, content, assigneeId, MessageTypeEnum.NOTICE);
            
            // 紧急提醒，同时发送邮件
            sendClientEmailNotification(title, content, Arrays.asList(String.valueOf(assigneeId)));
            
            log.info("[客户消息] 紧急跟进提醒已发送: followUpId={}, clientId={}, assigneeId={}, followUpTime={}", 
                    followUpId, clientId, assigneeId, followUpTimeStr);

        } catch (Exception e) {
            log.error("发送紧急跟进提醒失败: followUpId={}, error={}", followUpId, e.getMessage());
        }
    }

    /**
     * 发送超期跟进提醒
     */
    public void sendOverdueFollowUpReminder(Long followUpId, Long clientId, String clientName, Long assigneeId, LocalDateTime followUpTime, long hoursOverdue) {
        if (assigneeId == null || followUpTime == null) return;

        try {
            String followUpTimeStr = followUpTime.format(DATE_TIME_FORMATTER);
            String title = "超期跟进提醒：" + clientName;
            String content = String.format("您有客户跟进已超期，计划时间：%s，已超期：%d 小时", followUpTimeStr, hoursOverdue);
            
            sendClientNotification(clientId, title, content, assigneeId, MessageTypeEnum.NOTICE);
            
            // 超期提醒，发送邮件和短信
            sendClientEmailNotification(title, content, Arrays.asList(String.valueOf(assigneeId)));
            sendClientSmsNotification(content, Arrays.asList(String.valueOf(assigneeId)));
            
            log.info("[客户消息] 超期跟进提醒已发送: followUpId={}, clientId={}, assigneeId={}, hoursOverdue={}", 
                    followUpId, clientId, assigneeId, hoursOverdue);

        } catch (Exception e) {
            log.error("发送超期跟进提醒失败: followUpId={}, error={}", followUpId, e.getMessage());
        }
    }

    /**
     * 发送客户联系人变更通知
     */
    public void sendContactChangedNotification(Long clientId, String clientName, String contactName, String changeType, List<Long> assigneeIds) {
        if (assigneeIds == null || assigneeIds.isEmpty()) return;

        try {
            String title = "客户联系人变更：" + clientName;
            String content = String.format("客户联系人 %s 已%s", contactName, changeType);
            
            // 发送通知
            for (Long assigneeId : assigneeIds) {
                sendClientNotification(clientId, title, content, assigneeId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[客户消息] 联系人变更通知已发送: clientId={}, contactName={}, changeType={}, recipients={}", 
                    clientId, contactName, changeType, assigneeIds.size());

        } catch (Exception e) {
            log.error("发送联系人变更通知失败: clientId={}, error={}", clientId, e.getMessage());
        }
    }

    /**
     * 发送客户标签变更通知
     */
    public void sendTagChangedNotification(Long clientId, String clientName, List<String> addedTags, List<String> removedTags, List<Long> assigneeIds) {
        if (assigneeIds == null || assigneeIds.isEmpty()) return;

        try {
            String title = "客户标签变更：" + clientName;
            StringBuilder content = new StringBuilder("客户标签已更新：");
            
            if (addedTags != null && !addedTags.isEmpty()) {
                content.append("新增[").append(String.join(", ", addedTags)).append("]");
            }
            if (removedTags != null && !removedTags.isEmpty()) {
                content.append("移除[").append(String.join(", ", removedTags)).append("]");
            }
            
            // 发送通知
            for (Long assigneeId : assigneeIds) {
                sendClientNotification(clientId, title, content.toString(), assigneeId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[客户消息] 标签变更通知已发送: clientId={}, addedTags={}, removedTags={}, recipients={}", 
                    clientId, addedTags, removedTags, assigneeIds.size());

        } catch (Exception e) {
            log.error("发送标签变更通知失败: clientId={}, error={}", clientId, e.getMessage());
        }
    }

    /**
     * 发送批量客户通知
     */
    public void sendBatchClientNotification(List<Long> recipientIds, String subject, String content, Map<String, Object> variables) {
        if (recipientIds == null || recipientIds.isEmpty()) {
            log.warn("批量客户通知：接收者列表为空");
            return;
        }

        try {
            // 使用core-message批量发送
            if (messageSender != null) {
                for (Long userId : recipientIds) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(subject);
                    message.setContent(content);
                    message.setReceiverId(userId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
                log.info("[客户消息] 批量客户通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.sendBatch("client_batch", recipientIds, subject, content, variables);
                log.info("[客户消息][降级] 批量客户通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            log.info("[客户消息][日志] 批量客户通知: subject={}, recipients={}", subject, recipientIds);

        } catch (Exception e) {
            log.error("发送批量客户通知失败: subject={}, error={}", subject, e.getMessage());
        }
    }

    // ================================ 私有辅助方法 ================================

    /**
     * 发送客户消息（通用方法）
     */
    private void sendClientNotification(Long clientId, String title, String content, Long receiverId, MessageTypeEnum messageType) {
        if (messageSender != null) {
            BaseMessage message = new BaseMessage();
            message.setTitle(title);
            message.setContent(content);
            message.setReceiverId(receiverId);
            message.setBusinessId(clientId);
            message.setType(messageType);
            messageSender.send(message);
        } else {
            log.info("[客户消息][降级] 消息通知: title={}, receiverId={}", title, receiverId);
        }
    }

    /**
     * 发送客户邮件通知
     */
    private void sendClientEmailNotification(String title, String content, List<String> receivers) {
        if (emailService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle(title);
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.EMAIL);
            emailService.send(notification);
        } else {
            log.info("[客户消息][降级] 邮件通知: title={}, receivers={}", title, receivers.size());
        }
    }

    /**
     * 发送客户短信通知
     */
    private void sendClientSmsNotification(String content, List<String> receivers) {
        if (smsService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle("客户提醒");
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.SMS);
            smsService.send(notification);
        } else {
            log.info("[客户消息][降级] 短信通知: content={}, receivers={}", content, receivers.size());
        }
    }

    /**
     * 降级处理
     */
    private void fallbackClientNotification(String action, Long clientId, String clientName, Long assigneeId) {
        log.info("[客户消息][最终降级] {}: clientId={}, clientName={}, assigneeId={}", action, clientId, clientName, assigneeId);
    }

    // ================================ 本地通知服务接口 ================================

    /**
     * 本地客户通知服务接口（降级使用）
     */
    public interface LocalClientNotificationService {
        void send(String type, Long userId, String title, String content, Map<String, Object> variables);
        void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables);
    }
} 