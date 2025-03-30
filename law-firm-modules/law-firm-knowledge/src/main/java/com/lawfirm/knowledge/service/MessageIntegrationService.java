package com.lawfirm.knowledge.service;

import com.lawfirm.core.message.service.MessageTemplateService;
import com.lawfirm.knowledge.config.KnowledgeMessageServiceConfig.MessageSendingService;
import com.lawfirm.model.knowledge.entity.Knowledge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务集成
 * 示例如何集成core-message模块
 */
@Slf4j
@Service("knowledgeMessageService")
public class MessageIntegrationService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    @Qualifier("knowledgeMessageServiceImpl")
    private MessageSendingService messageService;

    @Autowired
    @Qualifier("messageTemplateServiceImpl")
    private MessageTemplateService templateService;

    /**
     * 知识文档更新后发送通知
     *
     * @param knowledge 知识文档
     * @param subscriberIds 订阅者ID列表
     */
    public void sendKnowledgeUpdateNotification(Knowledge knowledge, List<Long> subscriberIds) {
        if (knowledge == null || knowledge.getId() == null) {
            log.error("知识文档或ID为空，无法发送通知");
            return;
        }

        if (subscriberIds == null || subscriberIds.isEmpty()) {
            log.warn("订阅者列表为空，不发送通知");
            return;
        }

        try {
            // 构建消息变量
            Map<String, Object> variables = new HashMap<>();
            variables.put("knowledgeId", knowledge.getId());
            variables.put("knowledgeTitle", knowledge.getTitle());
            variables.put("knowledgeType", knowledge.getKnowledgeType());
            variables.put("updateTime", formatDateTime(knowledge.getUpdateTime()));
            variables.put("authorName", knowledge.getAuthorName());
            
            // 发送系统消息
            messageService.sendSystemMessage(
                "KNOWLEDGE_UPDATE",  // 消息模板代码
                variables,
                subscriberIds
            );
            
            log.info("知识更新通知发送成功: id={}, title={}, subscriberCount={}", 
                knowledge.getId(), knowledge.getTitle(), subscriberIds.size());
        } catch (Exception e) {
            log.error("知识更新通知发送失败: id={}, title={}, error={}", 
                knowledge.getId(), knowledge.getTitle(), e.getMessage(), e);
        }
    }

    /**
     * 发送知识审核请求通知
     *
     * @param knowledge 知识文档
     * @param reviewerIds 审核者ID列表
     */
    public void sendKnowledgeReviewNotification(Knowledge knowledge, List<Long> reviewerIds) {
        if (knowledge == null || knowledge.getId() == null) {
            log.error("知识文档或ID为空，无法发送审核通知");
            return;
        }

        if (reviewerIds == null || reviewerIds.isEmpty()) {
            log.warn("审核者列表为空，不发送审核通知");
            return;
        }

        try {
            // 构建消息变量
            Map<String, Object> variables = new HashMap<>();
            variables.put("knowledgeId", knowledge.getId());
            variables.put("knowledgeTitle", knowledge.getTitle());
            variables.put("knowledgeType", knowledge.getKnowledgeType());
            variables.put("requestTime", formatDateTime(LocalDateTime.now()));
            variables.put("authorName", knowledge.getAuthorName());
            
            // 审核链接
            String reviewUrl = String.format("/knowledge/review?id=%d", knowledge.getId());
            variables.put("reviewUrl", reviewUrl);
            
            // 发送系统消息
            messageService.sendSystemMessage(
                "KNOWLEDGE_REVIEW_REQUEST",  // 消息模板代码
                variables,
                reviewerIds
            );
            
            // 发送邮件通知
            messageService.sendEmailMessage(
                "KNOWLEDGE_REVIEW_EMAIL",  // 邮件模板代码
                variables,
                reviewerIds
            );
            
            log.info("知识审核通知发送成功: id={}, title={}, reviewerCount={}", 
                knowledge.getId(), knowledge.getTitle(), reviewerIds.size());
        } catch (Exception e) {
            log.error("知识审核通知发送失败: id={}, title={}, error={}", 
                knowledge.getId(), knowledge.getTitle(), e.getMessage(), e);
        }
    }

    /**
     * 格式化日期时间
     */
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : "";
    }
} 