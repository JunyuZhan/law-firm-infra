package com.lawfirm.document.service;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文档模块消息服务
 * 处理文档相关的通知（审核、版本更新等）
 */
@Slf4j
@Service("documentMessageService")
public class DocumentMessageService {

    @Autowired(required = false)
    @Qualifier("messageSender")
    private MessageSender messageSender;

    /**
     * 发送文档审核通知
     */
    public void sendDocumentReviewNotification(Long documentId, String documentTitle, String submitterName, List<Long> reviewerIds) {
        if (reviewerIds == null || reviewerIds.isEmpty()) return;

        try {
            String title = "文档审核请求：" + documentTitle;
            String content = String.format("提交人：%s，请及时审核。", submitterName);
            
            for (Long reviewerId : reviewerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(reviewerId);
                    message.setBusinessId(documentId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[文档消息] 文档审核通知已发送: documentId={}, recipients={}", documentId, reviewerIds.size());

        } catch (Exception e) {
            log.error("发送文档审核通知失败: documentId={}, error={}", documentId, e.getMessage());
        }
    }

    /**
     * 发送文档更新通知
     */
    public void sendDocumentUpdateNotification(Long documentId, String documentTitle, String version, List<Long> notifyIds) {
        if (notifyIds == null || notifyIds.isEmpty()) return;

        try {
            String title = "文档更新通知：" + documentTitle;
            String content = String.format("文档已更新到版本：%s", version);
            
            for (Long notifyId : notifyIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(notifyId);
                    message.setBusinessId(documentId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[文档消息] 文档更新通知已发送: documentId={}, version={}, recipients={}", documentId, version, notifyIds.size());

        } catch (Exception e) {
            log.error("发送文档更新通知失败: documentId={}, error={}", documentId, e.getMessage());
        }
    }
} 