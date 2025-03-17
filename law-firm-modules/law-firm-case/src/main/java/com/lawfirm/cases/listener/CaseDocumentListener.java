package com.lawfirm.cases.listener;

import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.model.cases.event.CaseDocumentAddedEvent;
import com.lawfirm.model.cases.mapper.business.CaseDocumentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件文档变更监听器
 * <p>
 * 监听案件文档相关事件，处理文档添加、删除、更新等操作的后续处理
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseDocumentListener {

    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    private final CaseDocumentMapper documentMapper;
    
    /**
     * 监听案件文档添加事件
     *
     * @param event 案件文档添加事件
     */
    @EventListener
    public void onCaseDocumentAdded(CaseDocumentAddedEvent event) {
        log.info("监听到案件文档添加事件: 案件ID={}, 文档ID={}, 文档名称={}", 
                event.getCaseId(), event.getDocumentId(), event.getDocumentName());
        
        Long caseId = event.getCaseId();
        String caseNumber = event.getCaseNumber();
        Long documentId = event.getDocumentId();
        
        try {
            // 1. 记录文档添加审计
            Long triggerId = 0L; // 实际应从事件中获取
            
            auditProvider.auditDocumentOperation(
                    caseId,
                    triggerId,
                    documentId,
                    "UPLOAD"
            );
            
            // 2. 发送文档添加通知
            Map<String, Object> documentData = new HashMap<>();
            documentData.put("documentId", documentId);
            documentData.put("documentName", event.getDocumentName());
            documentData.put("documentType", event.getDocumentType());
            documentData.put("documentSize", event.getDocumentSize());
            documentData.put("documentDescription", event.getDocumentDescription());
            documentData.put("confidential", event.isConfidential());
            documentData.put("tags", event.getDocumentTags());
            documentData.put("addTime", event.getOccurTime());
            
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("caseId", caseId);
            messageData.put("caseNumber", caseNumber);
            messageData.put("eventType", "DOCUMENT_ADDED");
            messageData.put("documentData", documentData);
            
            messageManager.sendCaseTeamChangeMessage(caseId, 
                    java.util.List.of(messageData), triggerId);
            
            // 3. 处理特定类型文档的业务逻辑
            handleSpecificDocumentType(event);
            
            log.info("案件文档添加事件处理成功: 案件ID={}, 文档ID={}", caseId, documentId);
        } catch (Exception e) {
            log.error("案件文档添加事件处理失败: 案件ID={}, 文档ID={}, 错误={}", 
                    caseId, documentId, e.getMessage(), e);
        }
    }
    
    /**
     * 处理特定类型文档的业务逻辑
     *
     * @param event 文档添加事件
     */
    private void handleSpecificDocumentType(CaseDocumentAddedEvent event) {
        String documentType = event.getDocumentType();
        Long caseId = event.getCaseId();
        Long documentId = event.getDocumentId();
        
        // 根据文档类型执行不同的业务逻辑
        switch (documentType) {
            case "CONTRACT":
                log.info("合同文档已添加: 案件ID={}, 文档ID={}", caseId, documentId);
                // 可以触发合同相关处理逻辑
                break;
            case "COURT_FILING":
                log.info("法院文书已添加: 案件ID={}, 文档ID={}", caseId, documentId);
                // 可以触发法院文书特殊处理
                break;
            case "EVIDENCE":
                log.info("证据文档已添加: 案件ID={}, 文档ID={}", caseId, documentId);
                // 可以触发证据编目或分析
                if (event.isConfidential()) {
                    log.info("机密证据文档已添加，执行特殊保护措施");
                    // 执行额外的保密措施
                }
                break;
            case "CLIENT_DOCUMENT":
                log.info("客户文档已添加: 案件ID={}, 文档ID={}", caseId, documentId);
                // 可以触发客户通知
                break;
            default:
                log.info("其他类型文档已添加: 案件ID={}, 文档ID={}, 类型={}", 
                        caseId, documentId, documentType);
                break;
        }
    }
} 