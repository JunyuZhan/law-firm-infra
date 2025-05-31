package com.lawfirm.cases.service;

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
 * 案件模块消息服务
 * 专门处理案件相关的消息通知
 * 
 * <p>设计特点：</p>
 * <ul>
 *   <li>专注案件业务场景</li>
 *   <li>双重保险机制：优先core-message，回退本地实现</li>
 *   <li>完整的案件生命周期通知</li>
 * </ul>
 */
@Slf4j
@Service("caseMessageService")
public class CaseMessageService {

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
    private LocalCaseNotificationService localNotificationService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ================================ 案件业务场景通知方法 ================================

    /**
     * 发送案件创建通知
     */
    public void sendCaseCreatedNotification(Long caseId, String caseName, Long lawyerId, Map<String, Object> variables) {
        if (lawyerId == null) {
            log.warn("案件创建通知：接收者ID为空，caseId={}", caseId);
            return;
        }

        try {
            // 优先使用core-message
            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle("新案件分配：" + caseName);
                message.setContent("您有新案件需要处理，请及时查看。");
                message.setReceiverId(lawyerId);
                message.setBusinessId(caseId);
                message.setType(MessageTypeEnum.NOTICE);
                
                messageSender.send(message);
                log.info("[案件消息] 案件创建通知已发送: caseId={}, lawyerId={}", caseId, lawyerId);
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.send("case_created", lawyerId, "新案件分配：" + caseName, "您有新案件需要处理，请及时查看。", variables);
                log.info("[案件消息][降级] 案件创建通知已发送: caseId={}, lawyerId={}", caseId, lawyerId);
                return;
            }

            // 最后的日志记录
            log.info("[案件消息][日志] 案件创建: caseId={}, caseName={}, lawyerId={}", caseId, caseName, lawyerId);

        } catch (Exception e) {
            log.error("发送案件创建通知失败: caseId={}, error={}", caseId, e.getMessage());
            // 降级处理
            fallbackCaseNotification("案件创建", caseId, caseName, lawyerId);
        }
    }

    /**
     * 发送案件状态变更通知
     */
    public void sendCaseStatusChangedNotification(Long caseId, String caseName, String oldStatus, String newStatus, List<Long> lawyerIds) {
        if (lawyerIds == null || lawyerIds.isEmpty()) {
            log.warn("案件状态变更通知：接收者列表为空，caseId={}", caseId);
            return;
        }

        try {
            String title = "案件状态变更：" + caseName;
            String content = String.format("案件状态已从 %s 变更为 %s", oldStatus, newStatus);

            // 批量发送通知
            for (Long lawyerId : lawyerIds) {
                sendCaseNotification(caseId, title, content, lawyerId, MessageTypeEnum.NOTICE);
            }

            log.info("[案件消息] 案件状态变更通知已发送: caseId={}, oldStatus={}, newStatus={}, recipients={}", 
                    caseId, oldStatus, newStatus, lawyerIds.size());

        } catch (Exception e) {
            log.error("发送案件状态变更通知失败: caseId={}, error={}", caseId, e.getMessage());
        }
    }

    /**
     * 发送案件截止提醒
     */
    public void sendCaseDueReminder(Long caseId, String caseName, List<Long> lawyerIds, LocalDateTime dueDate) {
        if (lawyerIds == null || lawyerIds.isEmpty() || dueDate == null) return;

        try {
            String dueDateStr = dueDate.format(DATE_TIME_FORMATTER);
            String title = "案件截止提醒：" + caseName;
            String content = String.format("案件即将截止，截止时间：%s", dueDateStr);
            
            // 发送站内消息
            for (Long lawyerId : lawyerIds) {
                sendCaseNotification(caseId, title, content, lawyerId, MessageTypeEnum.NOTICE);
            }
            
            // 重要提醒，同时发送邮件
            sendCaseEmailNotification(title, content, lawyerIds.stream().map(String::valueOf).toList());
            
            log.info("[案件消息] 案件截止提醒已发送: caseId={}, recipients={}, dueDate={}", 
                    caseId, lawyerIds.size(), dueDateStr);

        } catch (Exception e) {
            log.error("发送案件截止提醒失败: caseId={}, error={}", caseId, e.getMessage());
        }
    }

    /**
     * 发送案件开庭提醒
     */
    public void sendCourtHearingReminder(Long caseId, String caseName, List<Long> lawyerIds, LocalDateTime hearingTime) {
        if (lawyerIds == null || lawyerIds.isEmpty() || hearingTime == null) return;

        try {
            String hearingTimeStr = hearingTime.format(DATE_TIME_FORMATTER);
            String title = "开庭提醒：" + caseName;
            String content = String.format("案件即将开庭，开庭时间：%s，请做好准备。", hearingTimeStr);
            
            // 发送站内消息
            for (Long lawyerId : lawyerIds) {
                sendCaseNotification(caseId, title, content, lawyerId, MessageTypeEnum.NOTICE);
            }
            
            // 开庭提醒，发送邮件和短信
            sendCaseEmailNotification(title, content, lawyerIds.stream().map(String::valueOf).toList());
            sendCaseSmsNotification(content, lawyerIds.stream().map(String::valueOf).toList());
            
            log.info("[案件消息] 开庭提醒已发送: caseId={}, recipients={}, hearingTime={}", 
                    caseId, lawyerIds.size(), hearingTimeStr);

        } catch (Exception e) {
            log.error("发送开庭提醒失败: caseId={}, error={}", caseId, e.getMessage());
        }
    }

    /**
     * 发送案件结案通知
     */
    public void sendCaseClosedNotification(Long caseId, String caseName, List<Long> lawyerIds, String result) {
        if (lawyerIds == null || lawyerIds.isEmpty()) return;

        try {
            String title = "案件结案：" + caseName;
            String content = String.format("案件已结案，结案结果：%s", result);
            
            // 发送通知
            for (Long lawyerId : lawyerIds) {
                sendCaseNotification(caseId, title, content, lawyerId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[案件消息] 案件结案通知已发送: caseId={}, recipients={}, result={}", 
                    caseId, lawyerIds.size(), result);

        } catch (Exception e) {
            log.error("发送案件结案通知失败: caseId={}, error={}", caseId, e.getMessage());
        }
    }

    /**
     * 发送客户案件进展通知
     */
    public void sendClientCaseProgressNotification(Long caseId, String caseName, Long clientId, String progress) {
        if (clientId == null) return;

        try {
            String title = "案件进展通知：" + caseName;
            String content = "您的案件有新进展：" + progress;
            
            sendCaseNotification(caseId, title, content, clientId, MessageTypeEnum.NOTICE);
            
            // 客户通知，同时发送邮件
            sendCaseEmailNotification(title, content, Arrays.asList(String.valueOf(clientId)));
            
            log.info("[案件消息] 客户案件进展通知已发送: caseId={}, clientId={}", caseId, clientId);

        } catch (Exception e) {
            log.error("发送客户案件进展通知失败: caseId={}, error={}", caseId, e.getMessage());
        }
    }

    // ================================ 私有辅助方法 ================================

    /**
     * 发送案件消息（通用方法）
     */
    private void sendCaseNotification(Long caseId, String title, String content, Long receiverId, MessageTypeEnum messageType) {
        if (messageSender != null) {
            BaseMessage message = new BaseMessage();
            message.setTitle(title);
            message.setContent(content);
            message.setReceiverId(receiverId);
            message.setBusinessId(caseId);
            message.setType(messageType);
            messageSender.send(message);
        } else {
            log.info("[案件消息][降级] 消息通知: title={}, receiverId={}", title, receiverId);
        }
    }

    /**
     * 发送案件邮件通知
     */
    private void sendCaseEmailNotification(String title, String content, List<String> receivers) {
        if (emailService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle(title);
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.EMAIL);
            emailService.send(notification);
        } else {
            log.info("[案件消息][降级] 邮件通知: title={}, receivers={}", title, receivers.size());
        }
    }

    /**
     * 发送案件短信通知
     */
    private void sendCaseSmsNotification(String content, List<String> receivers) {
        if (smsService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle("案件提醒");
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.SMS);
            smsService.send(notification);
        } else {
            log.info("[案件消息][降级] 短信通知: content={}, receivers={}", content, receivers.size());
        }
    }

    /**
     * 降级处理
     */
    private void fallbackCaseNotification(String action, Long caseId, String caseName, Long lawyerId) {
        log.info("[案件消息][最终降级] {}: caseId={}, caseName={}, lawyerId={}", action, caseId, caseName, lawyerId);
    }

    // ================================ 本地通知服务接口 ================================

    /**
     * 本地案件通知服务接口（降级使用）
     */
    public interface LocalCaseNotificationService {
        void send(String type, Long userId, String title, String content, Map<String, Object> variables);
        void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables);
    }
} 