package com.lawfirm.contract.service;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.core.message.service.MessageTemplateService;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
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
 * 合同模块消息服务
 * 专门处理合同相关的消息通知
 * 
 * <p>业务场景：</p>
 * <ul>
 *   <li>合同创建/修改通知</li>
 *   <li>合同审核通知</li>
 *   <li>合同签署提醒</li>
 *   <li>合同到期提醒</li>
 *   <li>合同执行状态变更通知</li>
 * </ul>
 */
@Slf4j
@Service("contractMessageService")
public class ContractMessageService {

    // Core消息服务依赖
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
    private LocalContractNotificationService localNotificationService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ================================ 合同生命周期通知 ================================

    /**
     * 发送合同创建通知
     */
    public void sendContractCreatedNotification(Long contractId, String contractNo, String contractTitle, Long assigneeId, Map<String, Object> variables) {
        if (assigneeId == null) {
            log.warn("合同创建通知：接收者ID为空，contractId={}", contractId);
            return;
        }

        try {
            String title = "新合同分配：" + contractTitle;
            String content = String.format("合同编号：%s，已分配给您处理。", contractNo);
            
            sendContractNotification(contractId, title, content, assigneeId, MessageTypeEnum.NOTICE);
            
            log.info("[合同消息] 合同创建通知已发送: contractId={}, assigneeId={}", contractId, assigneeId);

        } catch (Exception e) {
            log.error("发送合同创建通知失败: contractId={}, error={}", contractId, e.getMessage());
        }
    }

    /**
     * 发送合同状态变更通知
     */
    public void sendContractStatusChangedNotification(Long contractId, String contractNo, String contractTitle, String oldStatus, String newStatus, List<Long> notifyIds) {
        if (notifyIds == null || notifyIds.isEmpty()) return;

        try {
            String title = "合同状态变更：" + contractTitle;
            String content = String.format("合同编号：%s，状态已从 %s 变更为 %s", contractNo, oldStatus, newStatus);
            
            // 批量发送通知
            for (Long notifyId : notifyIds) {
                sendContractNotification(contractId, title, content, notifyId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[合同消息] 合同状态变更通知已发送: contractId={}, oldStatus={}, newStatus={}, recipients={}", 
                    contractId, oldStatus, newStatus, notifyIds.size());

        } catch (Exception e) {
            log.error("发送合同状态变更通知失败: contractId={}, error={}", contractId, e.getMessage());
        }
    }

    // ================================ 合同审核通知 ================================

    /**
     * 发送合同审核请求通知
     */
    public void sendContractReviewRequestNotification(Long contractId, String contractNo, String contractTitle, String submitterName, List<Long> reviewerIds) {
        if (reviewerIds == null || reviewerIds.isEmpty()) return;

        try {
            String title = "合同审核请求：" + contractTitle;
            String content = String.format("合同编号：%s，提交人：%s，请及时审核。", contractNo, submitterName);
            
            // 通知审核者
            for (Long reviewerId : reviewerIds) {
                sendContractNotification(contractId, title, content, reviewerId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[合同消息] 合同审核请求通知已发送: contractId={}, submitterName={}, recipients={}", 
                    contractId, submitterName, reviewerIds.size());

        } catch (Exception e) {
            log.error("发送合同审核请求通知失败: contractId={}, error={}", contractId, e.getMessage());
        }
    }

    /**
     * 发送合同审核结果通知
     */
    public void sendContractReviewResultNotification(Long contractId, String contractNo, String contractTitle, String result, String comments, Long submitterId) {
        if (submitterId == null) return;

        try {
            String title = "合同审核结果：" + contractTitle;
            String content = String.format("合同编号：%s，审核结果：%s", contractNo, result);
            if (comments != null && !comments.trim().isEmpty()) {
                content += "，审核意见：" + comments;
            }
            
            sendContractNotification(contractId, title, content, submitterId, MessageTypeEnum.NOTICE);
            
            log.info("[合同消息] 合同审核结果通知已发送: contractId={}, result={}, submitterId={}", 
                    contractId, result, submitterId);

        } catch (Exception e) {
            log.error("发送合同审核结果通知失败: contractId={}, error={}", contractId, e.getMessage());
        }
    }

    // ================================ 合同签署通知 ================================

    /**
     * 发送合同签署提醒
     */
    public void sendContractSigningReminder(Long contractId, String contractNo, String contractTitle, List<Long> signerIds, LocalDateTime dueDate) {
        if (signerIds == null || signerIds.isEmpty()) return;

        try {
            String title = "合同签署提醒：" + contractTitle;
            String content = String.format("合同编号：%s，请及时签署。", contractNo);
            if (dueDate != null) {
                content += "，截止时间：" + dueDate.format(DATE_TIME_FORMATTER);
            }
            
            // 发送站内消息
            for (Long signerId : signerIds) {
                sendContractNotification(contractId, title, content, signerId, MessageTypeEnum.NOTICE);
            }
            
            // 重要提醒，同时发送邮件
            sendContractEmailNotification(title, content, signerIds.stream().map(String::valueOf).toList());
            
            log.info("[合同消息] 合同签署提醒已发送: contractId={}, recipients={}, dueDate={}", 
                    contractId, signerIds.size(), dueDate);

        } catch (Exception e) {
            log.error("发送合同签署提醒失败: contractId={}, error={}", contractId, e.getMessage());
        }
    }

    /**
     * 发送合同签署完成通知
     */
    public void sendContractSignedNotification(Long contractId, String contractNo, String contractTitle, String signerName, List<Long> notifyIds) {
        if (notifyIds == null || notifyIds.isEmpty()) return;

        try {
            String title = "合同签署完成：" + contractTitle;
            String content = String.format("合同编号：%s，签署人：%s，已完成签署。", contractNo, signerName);
            
            // 通知相关人员
            for (Long notifyId : notifyIds) {
                sendContractNotification(contractId, title, content, notifyId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[合同消息] 合同签署完成通知已发送: contractId={}, signerName={}, recipients={}", 
                    contractId, signerName, notifyIds.size());

        } catch (Exception e) {
            log.error("发送合同签署完成通知失败: contractId={}, error={}", contractId, e.getMessage());
        }
    }

    // ================================ 合同到期提醒 ================================

    /**
     * 发送合同到期提醒
     */
    public void sendContractExpirationReminder(Long contractId, String contractNo, String contractTitle, LocalDateTime expirationDate, List<Long> managerIds, int daysBefore) {
        if (managerIds == null || managerIds.isEmpty()) return;

        try {
            String title = "合同到期提醒：" + contractTitle;
            String content = String.format("合同编号：%s，将于 %s 到期（还有%d天），请及时处理。", 
                    contractNo, expirationDate.format(DATE_TIME_FORMATTER), daysBefore);
            
            // 发送站内消息
            for (Long managerId : managerIds) {
                sendContractNotification(contractId, title, content, managerId, MessageTypeEnum.NOTICE);
            }
            
            // 到期提醒，发送邮件
            sendContractEmailNotification(title, content, managerIds.stream().map(String::valueOf).toList());
            
            // 如果只剩3天或更少，同时发送短信
            if (daysBefore <= 3) {
                sendContractSmsNotification(content, managerIds.stream().map(String::valueOf).toList());
            }
            
            log.info("[合同消息] 合同到期提醒已发送: contractId={}, expirationDate={}, daysBefore={}, recipients={}", 
                    contractId, expirationDate, daysBefore, managerIds.size());

        } catch (Exception e) {
            log.error("发送合同到期提醒失败: contractId={}, error={}", contractId, e.getMessage());
        }
    }

    // ================================ 批量通知方法 ================================

    /**
     * 发送批量合同通知
     */
    public void sendBatchContractNotification(List<Long> recipientIds, String subject, String content, Map<String, Object> variables) {
        if (recipientIds == null || recipientIds.isEmpty()) {
            log.warn("批量合同通知：接收者列表为空");
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
                log.info("[合同消息] 批量合同通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.sendBatch("contract_batch", recipientIds, subject, content, variables);
                log.info("[合同消息][降级] 批量合同通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            log.info("[合同消息][日志] 批量合同通知: subject={}, recipients={}", subject, recipientIds);

        } catch (Exception e) {
            log.error("发送批量合同通知失败: subject={}, error={}", subject, e.getMessage());
        }
    }

    // ================================ 私有辅助方法 ================================

    /**
     * 发送合同消息（通用方法）
     */
    private void sendContractNotification(Long businessId, String title, String content, Long receiverId, MessageTypeEnum messageType) {
        if (messageSender != null) {
            BaseMessage message = new BaseMessage();
            message.setTitle(title);
            message.setContent(content);
            message.setReceiverId(receiverId);
            message.setBusinessId(businessId);
            message.setType(messageType);
            messageSender.send(message);
        } else {
            log.info("[合同消息][降级] 消息通知: title={}, receiverId={}", title, receiverId);
        }
    }

    /**
     * 发送合同邮件通知
     */
    private void sendContractEmailNotification(String title, String content, List<String> receivers) {
        if (emailService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle(title);
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.EMAIL);
            emailService.send(notification);
        } else {
            log.info("[合同消息][降级] 邮件通知: title={}, receivers={}", title, receivers.size());
        }
    }

    /**
     * 发送合同短信通知
     */
    private void sendContractSmsNotification(String content, List<String> receivers) {
        if (smsService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle("合同提醒");
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.SMS);
            smsService.send(notification);
        } else {
            log.info("[合同消息][降级] 短信通知: content={}, receivers={}", content, receivers.size());
        }
    }

    // ================================ 本地通知服务接口 ================================

    /**
     * 本地合同通知服务接口（降级使用）
     */
    public interface LocalContractNotificationService {
        void send(String type, Long userId, String title, String content, Map<String, Object> variables);
        void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables);
    }
} 