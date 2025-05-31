package com.lawfirm.finance.service;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 财务模块消息服务
 * 专门处理财务相关的消息通知
 * 
 * <p>业务场景：</p>
 * <ul>
 *   <li>账单到期提醒</li>
 *   <li>付款/收款通知</li>
 *   <li>发票开具通知</li>
 *   <li>预算超支提醒</li>
 *   <li>费用审批通知</li>
 * </ul>
 */
@Slf4j
@Service("financeMessageService")
public class FinanceMessageService {

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
    private LocalFinanceNotificationService localNotificationService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ================================ 账单和收款通知 ================================

    /**
     * 发送账单到期提醒
     */
    public void sendBillDueReminder(Long billId, String billNo, BigDecimal amount, LocalDateTime dueDate, Long clientId, List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) {
            log.warn("账单到期提醒：管理者列表为空，billId={}", billId);
            return;
        }

        try {
            String title = "账单到期提醒：" + billNo;
            String content = String.format("账单金额：%s，到期时间：%s，请及时催收。", 
                    amount, dueDate.format(DATE_TIME_FORMATTER));
            
            // 发送给相关管理者
            for (Long managerId : managerIds) {
                sendFinanceNotification(billId, title, content, managerId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[财务消息] 账单到期提醒已发送: billId={}, amount={}, recipients={}", 
                    billId, amount, managerIds.size());

        } catch (Exception e) {
            log.error("发送账单到期提醒失败: billId={}, error={}", billId, e.getMessage());
        }
    }

    /**
     * 发送付款完成通知
     */
    public void sendPaymentCompletedNotification(Long paymentId, String paymentNo, BigDecimal amount, String payerName, List<Long> financeTeamIds) {
        if (financeTeamIds == null || financeTeamIds.isEmpty()) return;

        try {
            String title = "付款完成通知：" + paymentNo;
            String content = String.format("付款人：%s，付款金额：%s，已完成付款。", payerName, amount);
            
            // 通知财务团队
            for (Long financeId : financeTeamIds) {
                sendFinanceNotification(paymentId, title, content, financeId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[财务消息] 付款完成通知已发送: paymentId={}, amount={}, recipients={}", 
                    paymentId, amount, financeTeamIds.size());

        } catch (Exception e) {
            log.error("发送付款完成通知失败: paymentId={}, error={}", paymentId, e.getMessage());
        }
    }

    /**
     * 发送收款确认通知
     */
    public void sendReceiptConfirmationNotification(Long receiptId, String receiptNo, BigDecimal amount, String clientName, List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) return;

        try {
            String title = "收款确认通知：" + receiptNo;
            String content = String.format("客户：%s，收款金额：%s，已确认收款。", clientName, amount);
            
            // 通知相关管理者
            for (Long managerId : managerIds) {
                sendFinanceNotification(receiptId, title, content, managerId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[财务消息] 收款确认通知已发送: receiptId={}, amount={}, recipients={}", 
                    receiptId, amount, managerIds.size());

        } catch (Exception e) {
            log.error("发送收款确认通知失败: receiptId={}, error={}", receiptId, e.getMessage());
        }
    }

    // ================================ 发票相关通知 ================================

    /**
     * 发送发票开具通知
     */
    public void sendInvoiceIssuedNotification(Long invoiceId, String invoiceNo, BigDecimal amount, String clientName, List<Long> recipientIds) {
        if (recipientIds == null || recipientIds.isEmpty()) return;

        try {
            String title = "发票开具通知：" + invoiceNo;
            String content = String.format("客户：%s，发票金额：%s，发票已开具。", clientName, amount);
            
            // 通知相关人员
            for (Long recipientId : recipientIds) {
                sendFinanceNotification(invoiceId, title, content, recipientId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[财务消息] 发票开具通知已发送: invoiceId={}, amount={}, recipients={}", 
                    invoiceId, amount, recipientIds.size());

        } catch (Exception e) {
            log.error("发送发票开具通知失败: invoiceId={}, error={}", invoiceId, e.getMessage());
        }
    }

    /**
     * 发送发票作废通知
     */
    public void sendInvoiceVoidNotification(Long invoiceId, String invoiceNo, String reason, List<Long> financeTeamIds) {
        if (financeTeamIds == null || financeTeamIds.isEmpty()) return;

        try {
            String title = "发票作废通知：" + invoiceNo;
            String content = String.format("发票已作废，原因：%s", reason);
            
            // 通知财务团队
            for (Long financeId : financeTeamIds) {
                sendFinanceNotification(invoiceId, title, content, financeId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[财务消息] 发票作废通知已发送: invoiceId={}, reason={}, recipients={}", 
                    invoiceId, reason, financeTeamIds.size());

        } catch (Exception e) {
            log.error("发送发票作废通知失败: invoiceId={}, error={}", invoiceId, e.getMessage());
        }
    }

    // ================================ 预算和费用审批通知 ================================

    /**
     * 发送预算超支提醒
     */
    public void sendBudgetExceededNotification(Long budgetId, String budgetName, BigDecimal budgetAmount, BigDecimal actualAmount, List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) return;

        try {
            String title = "预算超支提醒：" + budgetName;
            String content = String.format("预算金额：%s，实际支出：%s，已超出预算。", budgetAmount, actualAmount);
            
            // 紧急通知管理者
            for (Long managerId : managerIds) {
                sendFinanceNotification(budgetId, title, content, managerId, MessageTypeEnum.SYSTEM);
            }
            
            // 预算超支，发送邮件和短信
            sendFinanceEmailNotification(title, content, managerIds.stream().map(String::valueOf).toList());
            sendFinanceSmsNotification(content, managerIds.stream().map(String::valueOf).toList());
            
            log.info("[财务消息] 预算超支提醒已发送: budgetId={}, budgetAmount={}, actualAmount={}, recipients={}", 
                    budgetId, budgetAmount, actualAmount, managerIds.size());

        } catch (Exception e) {
            log.error("发送预算超支提醒失败: budgetId={}, error={}", budgetId, e.getMessage());
        }
    }

    /**
     * 发送费用审批通知
     */
    public void sendExpenseApprovalNotification(Long expenseId, String expenseNo, BigDecimal amount, String applicantName, List<Long> approverIds) {
        if (approverIds == null || approverIds.isEmpty()) return;

        try {
            String title = "费用审批通知：" + expenseNo;
            String content = String.format("申请人：%s，申请金额：%s，请及时审批。", applicantName, amount);
            
            // 通知审批者
            for (Long approverId : approverIds) {
                sendFinanceNotification(expenseId, title, content, approverId, MessageTypeEnum.NOTICE);
            }
            
            log.info("[财务消息] 费用审批通知已发送: expenseId={}, amount={}, recipients={}", 
                    expenseId, amount, approverIds.size());

        } catch (Exception e) {
            log.error("发送费用审批通知失败: expenseId={}, error={}", expenseId, e.getMessage());
        }
    }

    /**
     * 发送费用审批结果通知
     */
    public void sendExpenseApprovalResultNotification(Long expenseId, String expenseNo, String result, String reason, Long applicantId) {
        if (applicantId == null) return;

        try {
            String title = "费用审批结果：" + expenseNo;
            String content = String.format("审批结果：%s，原因：%s", result, reason != null ? reason : "");
            
            sendFinanceNotification(expenseId, title, content, applicantId, MessageTypeEnum.NOTICE);
            
            log.info("[财务消息] 费用审批结果通知已发送: expenseId={}, result={}, applicantId={}", 
                    expenseId, result, applicantId);

        } catch (Exception e) {
            log.error("发送费用审批结果通知失败: expenseId={}, error={}", expenseId, e.getMessage());
        }
    }

    // ================================ 批量通知方法 ================================

    /**
     * 发送批量财务通知
     */
    public void sendBatchFinanceNotification(List<Long> recipientIds, String subject, String content, Map<String, Object> variables) {
        if (recipientIds == null || recipientIds.isEmpty()) {
            log.warn("批量财务通知：接收者列表为空");
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
                log.info("[财务消息] 批量财务通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            // 降级到本地实现
            if (localNotificationService != null) {
                localNotificationService.sendBatch("finance_batch", recipientIds, subject, content, variables);
                log.info("[财务消息][降级] 批量财务通知已发送: subject={}, recipients={}", subject, recipientIds.size());
                return;
            }

            log.info("[财务消息][日志] 批量财务通知: subject={}, recipients={}", subject, recipientIds);

        } catch (Exception e) {
            log.error("发送批量财务通知失败: subject={}, error={}", subject, e.getMessage());
        }
    }

    // ================================ 私有辅助方法 ================================

    /**
     * 发送财务消息（通用方法）
     */
    private void sendFinanceNotification(Long businessId, String title, String content, Long receiverId, MessageTypeEnum messageType) {
        if (messageSender != null) {
            BaseMessage message = new BaseMessage();
            message.setTitle(title);
            message.setContent(content);
            message.setReceiverId(receiverId);
            message.setBusinessId(businessId);
            message.setType(messageType);
            messageSender.send(message);
        } else {
            log.info("[财务消息][降级] 消息通知: title={}, receiverId={}", title, receiverId);
        }
    }

    /**
     * 发送财务邮件通知
     */
    private void sendFinanceEmailNotification(String title, String content, List<String> receivers) {
        if (emailService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle(title);
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.EMAIL);
            emailService.send(notification);
        } else {
            log.info("[财务消息][降级] 邮件通知: title={}, receivers={}", title, receivers.size());
        }
    }

    /**
     * 发送财务短信通知
     */
    private void sendFinanceSmsNotification(String content, List<String> receivers) {
        if (smsService != null) {
            BaseNotify notification = new BaseNotify();
            notification.setTitle("财务提醒");
            notification.setContent(content);
            notification.setReceivers(receivers);
            notification.setChannel(NotifyChannelEnum.SMS);
            smsService.send(notification);
        } else {
            log.info("[财务消息][降级] 短信通知: content={}, receivers={}", content, receivers.size());
        }
    }

    // ================================ 本地通知服务接口 ================================

    /**
     * 本地财务通知服务接口（降级使用）
     */
    public interface LocalFinanceNotificationService {
        void send(String type, Long userId, String title, String content, Map<String, Object> variables);
        void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables);
    }
} 