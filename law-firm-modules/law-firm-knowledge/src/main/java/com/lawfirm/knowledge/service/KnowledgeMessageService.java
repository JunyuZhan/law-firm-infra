package com.lawfirm.knowledge.service;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 知识库模块消息服务
 * 处理知识分享、文章审核、知识推荐等通知
 */
@Service("knowledgeMessageService")
public class KnowledgeMessageService {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeMessageService.class);

    @Autowired(required = false)
    @Qualifier("messageSender")
    private MessageSender messageSender;

    /**
     * 发送知识文章审核通知
     */
    public void sendKnowledgeReviewNotification(Long knowledgeId, String title, String authorName, List<Long> reviewerIds) {
        if (reviewerIds == null || reviewerIds.isEmpty()) return;

        try {
            String messageTitle = "知识文章审核：" + title;
            String content = String.format("作者：%s，请及时审核知识文章。", authorName);
            
            for (Long reviewerId : reviewerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(messageTitle);
                    message.setContent(content);
                    message.setReceiverId(reviewerId);
                    message.setBusinessId(knowledgeId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[知识库消息] 知识文章审核通知已发送: knowledgeId={}, recipients={}", knowledgeId, reviewerIds.size());

        } catch (Exception e) {
            log.error("发送知识文章审核通知失败: knowledgeId={}, error={}", knowledgeId, e.getMessage());
        }
    }

    /**
     * 发送知识推荐通知
     */
    public void sendKnowledgeRecommendationNotification(Long knowledgeId, String title, String category, List<Long> targetUserIds) {
        if (targetUserIds == null || targetUserIds.isEmpty()) return;

        try {
            String messageTitle = "知识推荐：" + title;
            String content = String.format("分类：%s，推荐您阅读这篇知识文章。", category);
            
            for (Long userId : targetUserIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(messageTitle);
                    message.setContent(content);
                    message.setReceiverId(userId);
                    message.setBusinessId(knowledgeId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[知识库消息] 知识推荐通知已发送: knowledgeId={}, category={}, recipients={}", 
                    knowledgeId, category, targetUserIds.size());

        } catch (Exception e) {
            log.error("发送知识推荐通知失败: knowledgeId={}, error={}", knowledgeId, e.getMessage());
        }
    }

    /**
     * 发送知识更新通知
     */
    public void sendKnowledgeUpdateNotification(Long knowledgeId, String title, String updateType, List<Long> subscriberIds) {
        if (subscriberIds == null || subscriberIds.isEmpty()) return;

        try {
            String messageTitle = "知识更新：" + title;
            String content = String.format("更新类型：%s，知识内容已更新。", updateType);
            
            for (Long subscriberId : subscriberIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(messageTitle);
                    message.setContent(content);
                    message.setReceiverId(subscriberId);
                    message.setBusinessId(knowledgeId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[知识库消息] 知识更新通知已发送: knowledgeId={}, updateType={}, recipients={}", 
                    knowledgeId, updateType, subscriberIds.size());

        } catch (Exception e) {
            log.error("发送知识更新通知失败: knowledgeId={}, error={}", knowledgeId, e.getMessage());
        }
    }
} 