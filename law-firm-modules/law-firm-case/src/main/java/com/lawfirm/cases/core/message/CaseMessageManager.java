package com.lawfirm.cases.core.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.core.message.service.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import com.lawfirm.model.message.entity.business.CaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 案件消息管理器
 * <p>
 * 负责与core-message模块集成，实现案件相关消息的处理功能。
 * 包括消息发送、接收、通知和事件处理等。
 * </p>
 *
 * @author JunyuZhan
 */
@Slf4j
@Component
public class CaseMessageManager {
    
    private final MessageSender messageSender;
    private MessageManager messageManager;
    
    private static final String CASE_TOPIC = "case-events";
    private static final String CASE_NOTIFICATION_TOPIC = "case-notifications";
    
    /**
     * 构造函数
     * 通过构造函数注入必需的MessageSender和可选的MessageManager
     */
    @Autowired
    public CaseMessageManager(
            @Qualifier("messageSender") MessageSender messageSender,
            @Autowired(required = false) @Qualifier("messageManagerImpl") MessageManager messageManager) {
        this.messageSender = messageSender;
        this.messageManager = messageManager;
        log.info("初始化案件消息管理器, MessageManager状态: {}", messageManager != null ? "可用" : "不可用");
    }
    
    /**
     * 发送案件状态变更消息
     *
     * @param caseId 案件ID
     * @param oldStatus 旧状态
     * @param newStatus 新状态
     * @param operatorId 操作人ID
     * @param reason 变更原因
     */
    public void sendCaseStatusChangeMessage(Long caseId, String oldStatus, String newStatus, Long operatorId, String reason) {
        log.info("发送案件状态变更消息, 案件ID: {}, 状态从 {} 变更到 {}", caseId, oldStatus, newStatus);
        
        try {
            CaseMessage message = new CaseMessage();
            message.setCaseId(caseId)
                   .setOperationType("STATUS_CHANGE")
                   .setOperationDesc(reason)
                   .setSenderId(operatorId)
                   .setMessageType(MessageTypeEnum.CASE)
                   .setContent(String.format("案件状态从%s变更为%s", oldStatus, newStatus));
            messageSender.send(message);
            
            log.info("案件状态变更消息发送成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件状态变更消息发送失败", e);
            throw new RuntimeException("发送案件状态变更消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 发送案件分配消息
     *
     * @param caseId 案件ID
     * @param assigneeId 受理人ID
     * @param assignerId 分配人ID
     * @param assignmentType 分配类型 (NEW/TRANSFER/REASSIGN)
     */
    public void sendCaseAssignmentMessage(Long caseId, Long assigneeId, Long assignerId, String assignmentType) {
        log.info("发送案件分配消息, 案件ID: {}, 受理人: {}, 分配类型: {}", caseId, assigneeId, assignmentType);
        
        try {
            CaseMessage message = new CaseMessage();
            message.setCaseId(caseId)
                   .setOperationType("ASSIGNMENT")
                   .setOperationDesc(assignmentType)
                   .setSenderId(assignerId)
                   .setReceiverId(assigneeId)
                   .setMessageType(MessageTypeEnum.CASE)
                   .setContent(String.format("案件分配给用户%s，类型：%s", assigneeId, assignmentType));
            messageSender.send(message);
            
            log.info("案件分配消息发送成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件分配消息发送失败", e);
            throw new RuntimeException("发送案件分配消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 发送案件截止日期提醒消息
     *
     * @param caseId 案件ID
     * @param deadlineType 截止日期类型
     * @param deadlineDate 截止日期
     * @param recipientIds 接收人ID列表
     */
    public void sendCaseDeadlineReminderMessage(Long caseId, String deadlineType, long deadlineDate, List<Long> recipientIds) {
        log.info("发送案件截止日期提醒消息, 案件ID: {}, 截止日期类型: {}", caseId, deadlineType);
        
        try {
            CaseMessage message = new CaseMessage();
            message.setCaseId(caseId)
                   .setOperationType("DEADLINE_REMINDER")
                   .setOperationDesc(deadlineType)
                   .setMessageType(MessageTypeEnum.CASE)
                   .setContent(String.format("案件截止日期提醒：%s，时间：%d", deadlineType, deadlineDate));
            // 可扩展：设置receivers
            messageSender.send(message);
            
            log.info("案件截止日期提醒消息发送成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件截止日期提醒消息发送失败", e);
            throw new RuntimeException("发送案件截止日期提醒消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 发送案件文档更新消息
     *
     * @param caseId 案件ID
     * @param documentId 文档ID
     * @param documentName 文档名称
     * @param operationType 操作类型 (UPLOAD/UPDATE/DELETE)
     * @param operatorId 操作人ID
     */
    public void sendCaseDocumentUpdateMessage(Long caseId, String documentId, String documentName, String operationType, Long operatorId) {
        log.info("发送案件文档更新消息, 案件ID: {}, 文档ID: {}, 操作类型: {}", caseId, documentId, operationType);
        
        try {
            CaseMessage message = new CaseMessage();
            message.setCaseId(caseId)
                   .setOperationType("DOCUMENT_UPDATE")
                   .setOperationDesc(operationType)
                   .setSenderId(operatorId)
                   .setMessageType(MessageTypeEnum.CASE)
                   .setContent(String.format("文档[%s]（ID:%s）%s", documentName, documentId, operationType));
            messageSender.send(message);
            
            log.info("案件文档更新消息发送成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件文档更新消息发送失败", e);
            throw new RuntimeException("发送案件文档更新消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 发送案件评论消息
     *
     * @param caseId 案件ID
     * @param commentId 评论ID
     * @param commentContent 评论内容
     * @param commenterId 评论人ID
     * @param mentionedUserIds 提及的用户ID列表
     */
    public void sendCaseCommentMessage(Long caseId, Long commentId, String commentContent, Long commenterId, List<Long> mentionedUserIds) {
        log.info("发送案件评论消息, 案件ID: {}, 评论ID: {}", caseId, commentId);
        
        try {
            CaseMessage message = new CaseMessage();
            message.setCaseId(caseId)
                   .setOperationType("COMMENT")
                   .setOperationDesc(commentContent)
                   .setSenderId(commenterId)
                   .setMessageType(MessageTypeEnum.CASE)
                   .setContent(commentContent);
            // 可扩展：设置receivers为mentionedUserIds
            messageSender.send(message);
            
            log.info("案件评论消息发送成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件评论消息发送失败", e);
            throw new RuntimeException("发送案件评论消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 发送案件团队变更消息
     *
     * @param caseId 案件ID
     * @param teamChanges 团队变更信息
     * @param operatorId 操作人ID
     */
    public void sendCaseTeamChangeMessage(Long caseId, List<Map<String, Object>> teamChanges, Long operatorId) {
        log.info("发送案件团队变更消息, 案件ID: {}, 变更数量: {}", caseId, teamChanges.size());
        
        try {
            // 构建消息内容
            Map<String, Object> messageData = Map.of(
                "caseId", caseId,
                "eventType", "TEAM_CHANGE",
                "teamChanges", teamChanges,
                "operatorId", operatorId,
                "timestamp", System.currentTimeMillis()
            );
            
            // 正确做法：将 messageData 封装为 CaseMessage 或 BaseMessage
            CaseMessage message = new CaseMessage();
            message.setCaseId(caseId)
                   .setOperationType("TEAM_CHANGE")
                   .setSenderId(operatorId)
                   .setMessageType(MessageTypeEnum.CASE)
                   .setContent("团队变更: " + teamChanges.toString());
            messageSender.send(message);
            
            log.info("案件团队变更消息发送成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件团队变更消息发送失败", e);
            throw new RuntimeException("发送案件团队变更消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取案件未读消息数量
     *
     * @param userId 用户ID
     * @param caseId 案件ID
     * @return 未读消息数量
     */
    public int getUnreadCaseMessageCount(Long userId, Long caseId) {
        if (messageManager == null) {
            log.warn("MessageManager未配置，无法获取未读消息数量");
            return 0;
        }
        
        return messageManager.getUnreadMessageCount(userId, "CASE", caseId.toString());
    }
    
    /**
     * 获取案件消息列表
     *
     * @param caseId 案件ID
     * @param page 页码
     * @param size 每页大小
     * @return 消息列表
     */
    public List<Object> getCaseMessages(Long caseId, int page, int size) {
        if (messageManager == null) {
            log.warn("MessageManager未配置，无法获取案件消息列表");
            return new ArrayList<>();
        }
        
        return messageManager.getMessages("CASE", caseId.toString(), page, size);
    }
    
    /**
     * 标记案件消息为已读
     *
     * @param userId 用户ID
     * @param messageIds 消息ID列表
     */
    public void markCaseMessagesAsRead(Long userId, List<String> messageIds) {
        if (messageManager == null) {
            log.warn("MessageManager未配置，无法标记消息为已读");
            return;
        }
        
        messageManager.markMessagesAsRead(userId, messageIds);
    }
} 