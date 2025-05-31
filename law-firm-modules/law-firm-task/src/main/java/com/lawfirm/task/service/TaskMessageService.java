package com.lawfirm.task.service;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.core.message.service.MessageTemplateService;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务模块消息服务
 * 专门处理任务相关的消息通知
 * 
 * <p>设计特点：</p>
 * <ul>
 *   <li>专注任务业务场景</li>
 *   <li>双重保险机制：优先core-message，回退本地实现</li>
 *   <li>完整的任务生命周期通知</li>
 * </ul>
 */
@Service("taskMessageService")
public class TaskMessageService {

    private static final Logger log = LoggerFactory.getLogger(TaskMessageService.class);

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
    private LocalTaskNotificationService localNotificationService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ================================ 任务业务场景通知方法 ================================

    /**
     * 发送任务创建通知
     */
    public void sendTaskCreatedNotification(Long taskId, String taskName, Long assigneeId, Map<String, Object> variables) {
        if (assigneeId == null) {
            log.warn("任务创建通知：接收者ID为空，taskId={}", taskId);
            return;
        }

        try {
            // 优先使用core-message
            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle("新任务分配：" + taskName);
                message.setContent("您有新任务，请及时处理。");
                message.setReceiverId(assigneeId);
                message.setBusinessId(taskId);
                message.setType(MessageTypeEnum.NOTICE);
                
                messageSender.send(message);
                log.info("[任务消息] 任务创建通知已发送: taskId={}, assigneeId={}", taskId, assigneeId);
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.send("task_created", assigneeId, "新任务分配：" + taskName, "您有新任务，请及时处理。", variables);
                log.info("[任务消息][降级] 任务创建通知已发送: taskId={}, assigneeId={}", taskId, assigneeId);
                return;
            }

            // 最后的日志记录
            log.info("[任务消息][日志] 任务创建: taskId={}, taskName={}, assigneeId={}", taskId, taskName, assigneeId);

        } catch (Exception e) {
            log.error("发送任务创建通知失败: taskId={}, error={}", taskId, e.getMessage());
            // 降级处理
            fallbackTaskNotification("任务创建", taskId, taskName, assigneeId);
        }
    }

    /**
     * 发送任务分配通知
     */
    public void sendTaskAssignmentNotification(Long taskId, String taskTitle, String description, Long assigneeId, LocalDateTime deadline) {
        if (assigneeId == null) return;

        try {
            String title = "任务分配：" + taskTitle;
            String content = String.format("任务描述：%s，截止时间：%s", 
                    description, deadline.format(DATE_TIME_FORMATTER));
            
            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle(title);
                message.setContent(content);
                message.setReceiverId(assigneeId);
                message.setBusinessId(taskId);
                message.setType(MessageTypeEnum.NOTICE);
                messageSender.send(message);
            }
            
            log.info("[任务消息] 任务分配通知已发送: taskId={}, assigneeId={}", taskId, assigneeId);

        } catch (Exception e) {
            log.error("发送任务分配通知失败: taskId={}, error={}", taskId, e.getMessage());
        }
    }

    /**
     * 发送任务完成通知
     */
    public void sendTaskCompletedNotification(Long taskId, String taskName, Long assigneeId, Map<String, Object> variables) {
        if (assigneeId == null) return;

        try {
            sendTaskNotification(taskId, "任务完成：" + taskName, "您的任务已完成。", assigneeId, MessageTypeEnum.NOTICE);
            log.info("[任务消息] 任务完成通知已发送: taskId={}, assigneeId={}", taskId, assigneeId);

        } catch (Exception e) {
            log.error("发送任务完成通知失败: taskId={}, error={}", taskId, e.getMessage());
        }
    }

    /**
     * 发送任务截止提醒
     */
    public void sendTaskDeadlineReminder(Long taskId, String taskTitle, LocalDateTime deadline, List<Long> assigneeIds, int hoursRemaining) {
        if (assigneeIds == null || assigneeIds.isEmpty()) return;

        try {
            String title = "任务截止提醒：" + taskTitle;
            String content = String.format("截止时间：%s，还有%d小时", 
                    deadline.format(DATE_TIME_FORMATTER), hoursRemaining);
            
            for (Long assigneeId : assigneeIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(assigneeId);
                    message.setBusinessId(taskId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[任务消息] 任务截止提醒已发送: taskId={}, hoursRemaining={}, recipients={}", 
                    taskId, hoursRemaining, assigneeIds.size());

        } catch (Exception e) {
            log.error("发送任务截止提醒失败: taskId={}, error={}", taskId, e.getMessage());
        }
    }

    /**
     * 发送任务分配通知（WorkTaskServiceImpl调用的方法）
     */
    public void sendTaskAssignedNotification(Long taskId, String taskName, Long assigneeId, Long oldAssigneeId, Map<String, Object> variables) {
        if (assigneeId == null) {
            log.warn("任务分配通知：新接收者ID为空，taskId={}", taskId);
            return;
        }

        try {
            String title = "任务重新分配：" + taskName;
            String content = "任务已重新分配给您，请及时处理。";
            if (oldAssigneeId != null) {
                content = "任务已从其他人员重新分配给您，请及时处理。";
            }

            // 给新接收者发通知
            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle(title);
                message.setContent(content);
                message.setReceiverId(assigneeId);
                message.setBusinessId(taskId);
                message.setType(MessageTypeEnum.NOTICE);
                messageSender.send(message);
            }

            // 如果有原接收者，也给原接收者发一个任务转移通知
            if (oldAssigneeId != null && !oldAssigneeId.equals(assigneeId)) {
                String oldTitle = "任务转移通知：" + taskName;
                String oldContent = "您的任务已转移给其他人员处理。";
                
                if (messageSender != null) {
                    BaseMessage oldMessage = new BaseMessage();
                    oldMessage.setTitle(oldTitle);
                    oldMessage.setContent(oldContent);
                    oldMessage.setReceiverId(oldAssigneeId);
                    oldMessage.setBusinessId(taskId);
                    oldMessage.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(oldMessage);
                }
            }

            log.info("[任务消息] 任务分配通知已发送: taskId={}, newAssigneeId={}, oldAssigneeId={}", 
                    taskId, assigneeId, oldAssigneeId);

        } catch (Exception e) {
            log.error("发送任务分配通知失败: taskId={}, error={}", taskId, e.getMessage());
            // 降级处理
            fallbackTaskNotification("任务分配", taskId, taskName, assigneeId);
        }
    }

    /**
     * 发送任务截止提醒（WorkTaskServiceImpl调用的方法）
     */
    public void sendTaskDueReminder(Long taskId, String taskName, Long assigneeId, LocalDateTime dueDate) {
        if (assigneeId == null) {
            log.warn("任务截止提醒：接收者ID为空，taskId={}", taskId);
            return;
        }

        try {
            String title = "任务截止提醒：" + taskName;
            String content = String.format("任务截止时间：%s，请及时处理。", 
                    dueDate.format(DATE_TIME_FORMATTER));

            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle(title);
                message.setContent(content);
                message.setReceiverId(assigneeId);
                message.setBusinessId(taskId);
                message.setType(MessageTypeEnum.NOTICE);
                messageSender.send(message);
            }

            log.info("[任务消息] 任务截止提醒已发送: taskId={}, assigneeId={}, dueDate={}", 
                    taskId, assigneeId, dueDate);

        } catch (Exception e) {
            log.error("发送任务截止提醒失败: taskId={}, error={}", taskId, e.getMessage());
            // 降级处理
            fallbackTaskNotification("任务截止提醒", taskId, taskName, assigneeId);
        }
    }

    /**
     * 发送任务超时通知（WorkTaskServiceImpl调用的方法）
     */
    public void sendTaskOverdueNotification(Long taskId, String taskName, Long assigneeId, LocalDateTime dueDate) {
        if (assigneeId == null) {
            log.warn("任务超时通知：接收者ID为空，taskId={}", taskId);
            return;
        }

        try {
            String title = "任务超时通知：" + taskName;
            String content = String.format("任务已超过截止时间（%s），请尽快处理。", 
                    dueDate.format(DATE_TIME_FORMATTER));

            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle(title);
                message.setContent(content);
                message.setReceiverId(assigneeId);
                message.setBusinessId(taskId);
                message.setType(MessageTypeEnum.NOTICE);
                messageSender.send(message);
            }

            log.info("[任务消息] 任务超时通知已发送: taskId={}, assigneeId={}, dueDate={}", 
                    taskId, assigneeId, dueDate);

        } catch (Exception e) {
            log.error("发送任务超时通知失败: taskId={}, error={}", taskId, e.getMessage());
            // 降级处理
            fallbackTaskNotification("任务超时", taskId, taskName, assigneeId);
        }
    }

    /**
     * 发送任务状态变更通知
     */
    public void sendTaskStatusChangeNotification(Long taskId, String taskTitle, String oldStatus, String newStatus, List<Long> stakeholderIds) {
        if (stakeholderIds == null || stakeholderIds.isEmpty()) return;

        try {
            String title = "任务状态变更：" + taskTitle;
            String content = String.format("状态从 %s 变更为 %s", oldStatus, newStatus);
            
            for (Long stakeholderId : stakeholderIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(stakeholderId);
                    message.setBusinessId(taskId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[任务消息] 任务状态变更通知已发送: taskId={}, oldStatus={}, newStatus={}, recipients={}", 
                    taskId, oldStatus, newStatus, stakeholderIds.size());

        } catch (Exception e) {
            log.error("发送任务状态变更通知失败: taskId={}, error={}", taskId, e.getMessage());
        }
    }

    /**
     * 发送批量任务通知
     */
    public void sendBatchTaskNotification(List<Long> recipientIds, String subject, String content, Map<String, Object> variables) {
        if (recipientIds == null || recipientIds.isEmpty()) {
            log.warn("批量任务通知：接收者列表为空");
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
                log.info("[任务消息] 批量任务通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.sendBatch("task_batch", recipientIds, subject, content, variables);
                log.info("[任务消息][降级] 批量任务通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            log.info("[任务消息][日志] 批量任务通知: subject={}, recipients={}", subject, recipientIds);

        } catch (Exception e) {
            log.error("发送批量任务通知失败: subject={}, error={}", subject, e.getMessage());
        }
    }

    /**
     * 使用模板发送任务通知
     */
    public void sendTaskTemplateNotification(String templateCode, Map<String, Object> variables, List<Long> receiverIds) {
        if (templateService == null || receiverIds == null || receiverIds.isEmpty()) {
            log.warn("模板任务通知：模板服务不可用或接收者为空");
            return;
        }

        try {
            // 获取模板内容
            String titleTemplate = templateService.getTemplate(templateCode + "_TITLE");
            String contentTemplate = templateService.getTemplate(templateCode + "_CONTENT");

            if (titleTemplate == null || contentTemplate == null) {
                log.warn("任务通知模板不存在: {}", templateCode);
                return;
            }

            // 简单的变量替换（实际项目中应该使用模板引擎）
            String title = replaceVariables(titleTemplate, variables);
            String content = replaceVariables(contentTemplate, variables);

            // 发送通知
            for (Long receiverId : receiverIds) {
                sendTaskNotification(null, title, content, receiverId, MessageTypeEnum.NOTICE);
            }

            log.info("[任务消息] 模板任务通知已发送: template={}, recipients={}", templateCode, receiverIds.size());

        } catch (Exception e) {
            log.error("发送模板任务通知失败: template={}, error={}", templateCode, e.getMessage());
        }
    }

    // ================================ 私有辅助方法 ================================

    /**
     * 发送任务消息（通用方法）
     */
    private void sendTaskNotification(Long taskId, String title, String content, Long receiverId, MessageTypeEnum messageType) {
        if (messageSender != null) {
            BaseMessage message = new BaseMessage();
            message.setTitle(title);
            message.setContent(content);
            message.setReceiverId(receiverId);
            message.setBusinessId(taskId);
            message.setType(messageType);
            messageSender.send(message);
        } else {
            log.info("[任务消息][降级] 消息通知: title={}, receiverId={}", title, receiverId);
        }
    }

    /**
     * 发送任务邮件通知
     */
    private void sendTaskEmailNotification(String title, String content, List<String> receivers) {
        if (emailService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle(title);
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.EMAIL);
            emailService.send(notification);
        } else {
            log.info("[任务消息][降级] 邮件通知: title={}, receivers={}", title, receivers.size());
        }
    }

    /**
     * 发送任务短信通知
     */
    private void sendTaskSmsNotification(String content, List<String> receivers) {
        if (smsService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle("任务提醒");
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.SMS);
            smsService.send(notification);
        } else {
            log.info("[任务消息][降级] 短信通知: content={}, receivers={}", content, receivers.size());
        }
    }

    /**
     * 简单的变量替换
     */
    private String replaceVariables(String template, Map<String, Object> variables) {
        String result = template;
        if (variables != null) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                String placeholder = "\\{\\{" + entry.getKey() + "\\}\\}";
                String value = entry.getValue() != null ? entry.getValue().toString() : "";
                result = result.replaceAll(placeholder, value);
            }
        }
        return result;
    }

    /**
     * 降级处理
     */
    private void fallbackTaskNotification(String action, Long taskId, String taskName, Long assigneeId) {
        log.info("[任务消息][最终降级] {}: taskId={}, taskName={}, assigneeId={}", action, taskId, taskName, assigneeId);
    }

    // ================================ 本地通知服务接口 ================================

    /**
     * 本地任务通知服务接口（降级使用）
     */
    public interface LocalTaskNotificationService {
        void send(String type, Long userId, String title, String content, Map<String, Object> variables);
        void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables);
    }
} 