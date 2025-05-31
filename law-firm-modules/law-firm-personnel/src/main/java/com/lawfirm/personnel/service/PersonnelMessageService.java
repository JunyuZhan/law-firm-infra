package com.lawfirm.personnel.service;

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
 * 人事模块消息服务
 * 专门处理员工、组织架构相关的消息通知
 * 
 * <p>业务场景：</p>
 * <ul>
 *   <li>员工入职/离职通知</li>
 *   <li>职位变更通知</li>
 *   <li>组织架构调整通知</li>
 *   <li>考勤异常提醒</li>
 *   <li>绩效评估通知</li>
 * </ul>
 */
@Slf4j
@Service("personnelMessageService")
public class PersonnelMessageService {

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
    private LocalPersonnelNotificationService localNotificationService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ================================ 员工生命周期通知 ================================

    /**
     * 发送员工入职通知
     */
    public void sendEmployeeOnboardingNotification(Long employeeId, String employeeName, Long managerId, String departmentName, LocalDateTime startDate) {
        if (managerId == null) {
            log.warn("员工入职通知：管理者ID为空，employeeId={}", employeeId);
            return;
        }

        try {
            String title = "新员工入职通知：" + employeeName;
            String content = String.format("新员工 %s 已加入 %s 部门，入职时间：%s", 
                    employeeName, departmentName, startDate.format(DATE_TIME_FORMATTER));
            
            sendPersonnelNotification(employeeId, title, content, managerId, MessageTypeEnum.NOTICE);
            
            log.info("[人事消息] 员工入职通知已发送: employeeId={}, managerId={}", employeeId, managerId);

        } catch (Exception e) {
            log.error("发送员工入职通知失败: employeeId={}, error={}", employeeId, e.getMessage());
        }
    }

    /**
     * 发送员工离职通知
     */
    public void sendEmployeeOffboardingNotification(Long employeeId, String employeeName, List<Long> managerIds, String departmentName, LocalDateTime endDate) {
        if (managerIds == null || managerIds.isEmpty()) {
            log.warn("员工离职通知：管理者列表为空，employeeId={}", employeeId);
            return;
        }

        try {
            String title = "员工离职通知：" + employeeName;
            String content = String.format("员工 %s 已从 %s 部门离职，离职时间：%s", 
                    employeeName, departmentName, endDate.format(DATE_TIME_FORMATTER));
            
            // 批量发送通知
            for (Long managerId : managerIds) {
                sendPersonnelNotification(employeeId, title, content, managerId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[人事消息] 员工离职通知已发送: employeeId={}, recipients={}", employeeId, managerIds.size());

        } catch (Exception e) {
            log.error("发送员工离职通知失败: employeeId={}, error={}", employeeId, e.getMessage());
        }
    }

    /**
     * 发送职位变更通知
     */
    public void sendPositionChangeNotification(Long employeeId, String employeeName, String oldPosition, String newPosition, List<Long> notifyIds) {
        if (notifyIds == null || notifyIds.isEmpty()) return;

        try {
            String title = "职位变更通知：" + employeeName;
            String content = String.format("员工 %s 职位已从 %s 变更为 %s", employeeName, oldPosition, newPosition);
            
            // 批量发送通知
            for (Long notifyId : notifyIds) {
                sendPersonnelNotification(employeeId, title, content, notifyId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[人事消息] 职位变更通知已发送: employeeId={}, oldPosition={}, newPosition={}, recipients={}", 
                    employeeId, oldPosition, newPosition, notifyIds.size());

        } catch (Exception e) {
            log.error("发送职位变更通知失败: employeeId={}, error={}", employeeId, e.getMessage());
        }
    }

    /**
     * 发送部门调动通知
     */
    public void sendDepartmentTransferNotification(Long employeeId, String employeeName, String oldDepartment, String newDepartment, List<Long> notifyIds) {
        if (notifyIds == null || notifyIds.isEmpty()) return;

        try {
            String title = "部门调动通知：" + employeeName;
            String content = String.format("员工 %s 已从 %s 调动至 %s", employeeName, oldDepartment, newDepartment);
            
            // 批量发送通知
            for (Long notifyId : notifyIds) {
                sendPersonnelNotification(employeeId, title, content, notifyId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[人事消息] 部门调动通知已发送: employeeId={}, oldDepartment={}, newDepartment={}, recipients={}", 
                    employeeId, oldDepartment, newDepartment, notifyIds.size());

        } catch (Exception e) {
            log.error("发送部门调动通知失败: employeeId={}, error={}", employeeId, e.getMessage());
        }
    }

    // ================================ 组织架构变更通知 ================================

    /**
     * 发送组织架构调整通知
     */
    public void sendOrganizationChangeNotification(String changeType, String changeDescription, List<Long> affectedEmployeeIds) {
        if (affectedEmployeeIds == null || affectedEmployeeIds.isEmpty()) return;

        try {
            String title = "组织架构调整通知";
            String content = String.format("组织架构发生%s：%s", changeType, changeDescription);
            
            // 批量发送通知
            for (Long employeeId : affectedEmployeeIds) {
                sendPersonnelNotification(null, title, content, employeeId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[人事消息] 组织架构调整通知已发送: changeType={}, recipients={}", changeType, affectedEmployeeIds.size());

        } catch (Exception e) {
            log.error("发送组织架构调整通知失败: changeType={}, error={}", changeType, e.getMessage());
        }
    }

    /**
     * 发送新部门创建通知
     */
    public void sendNewDepartmentNotification(String departmentName, Long managerId, List<Long> hrIds) {
        if (hrIds == null || hrIds.isEmpty()) return;

        try {
            String title = "新部门创建通知";
            String content = String.format("新部门 %s 已创建，部门负责人：%s", departmentName, getEmployeeName(managerId));
            
            // 发送给HR团队
            for (Long hrId : hrIds) {
                sendPersonnelNotification(null, title, content, hrId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[人事消息] 新部门创建通知已发送: departmentName={}, managerId={}, recipients={}", 
                    departmentName, managerId, hrIds.size());

        } catch (Exception e) {
            log.error("发送新部门创建通知失败: departmentName={}, error={}", departmentName, e.getMessage());
        }
    }

    // ================================ 考勤和绩效通知 ================================

    /**
     * 发送考勤异常提醒
     */
    public void sendAttendanceAnomalyNotification(Long employeeId, String employeeName, String anomalyType, String description, Long managerId) {
        if (managerId == null) return;

        try {
            String title = "考勤异常提醒：" + employeeName;
            String content = String.format("员工 %s 存在考勤异常：%s - %s", employeeName, anomalyType, description);
            
            sendPersonnelNotification(employeeId, title, content, managerId, MessageTypeEnum.NOTICE);
            
            log.info("[人事消息] 考勤异常提醒已发送: employeeId={}, anomalyType={}, managerId={}", 
                    employeeId, anomalyType, managerId);

        } catch (Exception e) {
            log.error("发送考勤异常提醒失败: employeeId={}, error={}", employeeId, e.getMessage());
        }
    }

    /**
     * 发送绩效评估通知
     */
    public void sendPerformanceEvaluationNotification(Long employeeId, String employeeName, String evaluationPeriod, String status, Long managerId) {
        if (managerId == null) return;

        try {
            String title = "绩效评估通知：" + employeeName;
            String content = String.format("员工 %s 的 %s 绩效评估状态：%s", employeeName, evaluationPeriod, status);
            
            sendPersonnelNotification(employeeId, title, content, managerId, MessageTypeEnum.NOTICE);
            
            log.info("[人事消息] 绩效评估通知已发送: employeeId={}, evaluationPeriod={}, status={}, managerId={}", 
                    employeeId, evaluationPeriod, status, managerId);

        } catch (Exception e) {
            log.error("发送绩效评估通知失败: employeeId={}, error={}", employeeId, e.getMessage());
        }
    }

    /**
     * 发送薪资调整通知
     */
    public void sendSalaryAdjustmentNotification(Long employeeId, String employeeName, String adjustmentType, String reason, List<Long> hrIds) {
        if (hrIds == null || hrIds.isEmpty()) return;

        try {
            String title = "薪资调整通知：" + employeeName;
            String content = String.format("员工 %s 薪资%s，原因：%s", employeeName, adjustmentType, reason);
            
            // 发送给HR团队
            for (Long hrId : hrIds) {
                sendPersonnelNotification(employeeId, title, content, hrId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[人事消息] 薪资调整通知已发送: employeeId={}, adjustmentType={}, recipients={}", 
                    employeeId, adjustmentType, hrIds.size());

        } catch (Exception e) {
            log.error("发送薪资调整通知失败: employeeId={}, error={}", employeeId, e.getMessage());
        }
    }

    // ================================ 批量通知方法 ================================

    /**
     * 发送批量人事通知
     */
    public void sendBatchPersonnelNotification(List<Long> recipientIds, String subject, String content, Map<String, Object> variables) {
        if (recipientIds == null || recipientIds.isEmpty()) {
            log.warn("批量人事通知：接收者列表为空");
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
                log.info("[人事消息] 批量人事通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.sendBatch("personnel_batch", recipientIds, subject, content, variables);
                log.info("[人事消息][降级] 批量人事通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            log.info("[人事消息][日志] 批量人事通知: subject={}, recipients={}", subject, recipientIds);

        } catch (Exception e) {
            log.error("发送批量人事通知失败: subject={}, error={}", subject, e.getMessage());
        }
    }

    // ================================ 私有辅助方法 ================================

    /**
     * 发送人事消息（通用方法）
     */
    private void sendPersonnelNotification(Long businessId, String title, String content, Long receiverId, MessageTypeEnum messageType) {
        if (messageSender != null) {
            BaseMessage message = new BaseMessage();
            message.setTitle(title);
            message.setContent(content);
            message.setReceiverId(receiverId);
            message.setBusinessId(businessId);
            message.setType(messageType);
            messageSender.send(message);
        } else {
            log.info("[人事消息][降级] 消息通知: title={}, receiverId={}", title, receiverId);
        }
    }

    /**
     * 发送人事邮件通知
     */
    private void sendPersonnelEmailNotification(String title, String content, List<String> receivers) {
        if (emailService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle(title);
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.EMAIL);
            emailService.send(notification);
        } else {
            log.info("[人事消息][降级] 邮件通知: title={}, receivers={}", title, receivers.size());
        }
    }

    /**
     * 发送人事短信通知
     */
    private void sendPersonnelSmsNotification(String content, List<String> receivers) {
        if (smsService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle("人事提醒");
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.SMS);
            smsService.send(notification);
        } else {
            log.info("[人事消息][降级] 短信通知: content={}, receivers={}", content, receivers.size());
        }
    }

    /**
     * 获取员工姓名（简化实现）
     */
    private String getEmployeeName(Long employeeId) {
        if (employeeId == null) return "未知";
        return "员工" + employeeId;
    }

    // ================================ 本地通知服务接口 ================================

    /**
     * 本地人事通知服务接口（降级使用）
     */
    public interface LocalPersonnelNotificationService {
        void send(String type, Long userId, String title, String content, Map<String, Object> variables);
        void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables);
    }
} 