package com.lawfirm.cases.event;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.model.cases.event.CaseDocumentAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件文档处理器
 * <p>
 * 提供案件文档相关的处理功能，包括发布文档事件、处理文档变更等
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseDocumentHandler {
    
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    private final ApplicationEventPublisher eventPublisher;
    
    /**
     * 发布文档添加事件
     * 
     * @param caseId 案件ID
     * @param caseNumber 案件编号
     * @param documentId 文档ID
     * @param documentName 文档名称
     * @param documentType 文档类型
     * @param documentSize 文档大小
     * @param description 文档描述
     * @param tags 文档标签
     * @param confidential 是否机密
     */
    public void publishDocumentAddedEvent(
            Long caseId, 
            String caseNumber,
            Long documentId,
            String documentName,
            String documentType,
            Long documentSize,
            String description,
            String[] tags,
            boolean confidential) {
        
        log.info("发布案件文档添加事件: 案件ID={}, 文档ID={}, 文档名称={}", 
                caseId, documentId, documentName);
        
        try {
            CaseDocumentAddedEvent event = new CaseDocumentAddedEvent(caseId, caseNumber, documentId, documentName);
            event.setDocumentType(documentType);
            event.setDocumentSize(documentSize);
            event.setDocumentDescription(description);
            event.setDocumentTags(tags);
            event.setConfidential(confidential);
            
            // 设置触发人信息
            // TODO: 从当前上下文或安全上下文获取
            event.setTriggerId("0");
            event.setTriggerName("系统");
            event.setSource("DOCUMENT_UPLOAD");
            event.setMessage("添加了文档: " + documentName);
            
            // 发布事件
            eventPublisher.publishEvent(event);
            
            log.info("案件文档添加事件发布成功: 案件ID={}, 文档ID={}", caseId, documentId);
        } catch (Exception e) {
            log.error("案件文档添加事件发布失败: 案件ID={}, 文档ID={}, 错误={}", 
                    caseId, documentId, e.getMessage(), e);
        }
    }
    
    /**
     * 处理文档删除
     * 
     * @param caseId 案件ID 
     * @param documentId 文档ID
     * @param triggerId 触发人ID
     * @return 处理结果
     */
    public boolean handleDocumentDelete(Long caseId, Long documentId, Long triggerId) {
        log.info("处理文档删除: 案件ID={}, 文档ID={}", caseId, documentId);
        
        try {
            // 1. 记录文档删除审计
            auditProvider.auditDocumentOperation(
                    caseId,
                    triggerId,
                    documentId,
                    "DELETE"
            );
            
            // 2. 发送文档删除通知
            Map<String, Object> documentData = new HashMap<>();
            documentData.put("documentId", documentId);
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("eventType", "DOCUMENT_DELETED");
            messageData.put("documentData", documentData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), triggerId);
            
            log.info("文档删除处理成功: 案件ID={}, 文档ID={}", caseId, documentId);
            return true;
        } catch (Exception e) {
            log.error("文档删除处理失败: 案件ID={}, 文档ID={}, 错误={}", 
                    caseId, documentId, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 处理文档更新
     * 
     * @param caseId 案件ID
     * @param documentId 文档ID
     * @param changes 变更内容
     * @param triggerId 触发人ID
     * @return 处理结果
     */
    public boolean handleDocumentUpdate(Long caseId, Long documentId, Map<String, Object> changes, Long triggerId) {
        log.info("处理文档更新: 案件ID={}, 文档ID={}", caseId, documentId);
        
        try {
            // 1. 记录文档更新审计
            auditProvider.auditDocumentOperation(
                    caseId,
                    triggerId,
                    documentId,
                    "UPDATE"
            );
            
            // 2. 发送文档更新通知
            Map<String, Object> documentData = new HashMap<>();
            documentData.put("documentId", documentId);
            documentData.put("changes", changes);
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("eventType", "DOCUMENT_UPDATED");
            messageData.put("documentData", documentData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), triggerId);
            
            log.info("文档更新处理成功: 案件ID={}, 文档ID={}", caseId, documentId);
            return true;
        } catch (Exception e) {
            log.error("文档更新处理失败: 案件ID={}, 文档ID={}, 错误={}", 
                    caseId, documentId, e.getMessage(), e);
            return false;
        }
    }
} 