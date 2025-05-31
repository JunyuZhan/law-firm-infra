package com.lawfirm.evidence.service;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 证据模块消息服务
 * 处理证据收集、审核、过期提醒等通知
 */
@Service("evidenceMessageService")
public class EvidenceMessageService {

    private static final Logger log = LoggerFactory.getLogger(EvidenceMessageService.class);

    @Autowired(required = false)
    @Qualifier("messageSender")
    private MessageSender messageSender;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送证据收集任务通知
     */
    public void sendEvidenceCollectionTaskNotification(Long taskId, String taskDescription, Long caseId, String caseName, List<Long> assigneeIds) {
        if (assigneeIds == null || assigneeIds.isEmpty()) return;

        try {
            String title = "证据收集任务：" + caseName;
            String content = String.format("案件ID：%d，任务描述：%s", caseId, taskDescription);
            
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
            
            log.info("[证据消息] 证据收集任务通知已发送: taskId={}, caseId={}, recipients={}", 
                    taskId, caseId, assigneeIds.size());

        } catch (Exception e) {
            log.error("发送证据收集任务通知失败: taskId={}, error={}", taskId, e.getMessage());
        }
    }

    /**
     * 发送证据审核通知
     */
    public void sendEvidenceReviewNotification(Long evidenceId, String evidenceType, String submitterName, Long caseId, List<Long> reviewerIds) {
        if (reviewerIds == null || reviewerIds.isEmpty()) return;

        try {
            String title = "证据审核通知：" + evidenceType;
            String content = String.format("案件ID：%d，提交人：%s，请及时审核。", caseId, submitterName);
            
            for (Long reviewerId : reviewerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(reviewerId);
                    message.setBusinessId(evidenceId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[证据消息] 证据审核通知已发送: evidenceId={}, evidenceType={}, recipients={}", 
                    evidenceId, evidenceType, reviewerIds.size());

        } catch (Exception e) {
            log.error("发送证据审核通知失败: evidenceId={}, error={}", evidenceId, e.getMessage());
        }
    }

    /**
     * 发送证据过期提醒
     */
    public void sendEvidenceExpirationReminder(Long evidenceId, String evidenceType, LocalDateTime expirationDate, Long caseId, List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) return;

        try {
            String title = "证据过期提醒：" + evidenceType;
            String content = String.format("案件ID：%d，证据将于 %s 过期，请及时处理。", 
                    caseId, expirationDate.format(DATE_TIME_FORMATTER));
            
            for (Long managerId : managerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(managerId);
                    message.setBusinessId(evidenceId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[证据消息] 证据过期提醒已发送: evidenceId={}, expirationDate={}, recipients={}", 
                    evidenceId, expirationDate, managerIds.size());

        } catch (Exception e) {
            log.error("发送证据过期提醒失败: evidenceId={}, error={}", evidenceId, e.getMessage());
        }
    }

    /**
     * 发送证据链完整性检查通知
     */
    public void sendEvidenceChainCheckNotification(Long caseId, String caseName, String checkResult, List<String> issues, List<Long> lawyerIds) {
        if (lawyerIds == null || lawyerIds.isEmpty()) return;

        try {
            String title = "证据链检查：" + caseName;
            String content = String.format("检查结果：%s", checkResult);
            if (issues != null && !issues.isEmpty()) {
                content += "，发现问题：" + String.join("、", issues);
            }
            
            for (Long lawyerId : lawyerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(lawyerId);
                    message.setBusinessId(caseId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[证据消息] 证据链检查通知已发送: caseId={}, checkResult={}, recipients={}", 
                    caseId, checkResult, lawyerIds.size());

        } catch (Exception e) {
            log.error("发送证据链检查通知失败: caseId={}, error={}", caseId, e.getMessage());
        }
    }
} 