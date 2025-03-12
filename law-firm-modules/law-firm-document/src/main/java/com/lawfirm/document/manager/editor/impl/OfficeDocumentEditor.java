package com.lawfirm.document.manager.editor.impl;

import com.lawfirm.common.cache.service.CacheService;
import com.lawfirm.core.audit.service.AuditService;
import com.lawfirm.model.document.vo.DocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Office文档编辑器实现
 */
@Slf4j
@Component("officeDocumentEditor")
public class OfficeDocumentEditor extends AbstractDocumentEditor {

    public OfficeDocumentEditor(CacheService cacheService,
                              AuditService auditService,
                              SimpMessagingTemplate messagingTemplate) {
        super(cacheService, auditService, messagingTemplate);
    }

    @Override
    public String getEditUrl(Long documentId) {
        // TODO: 实现Office Online或其他在线编辑服务的集成
        return "/office/edit/" + documentId;
    }

    @Override
    public void saveContent(Long documentId, InputStream content, Long userId) {
        try {
            // 1. 加载Word文档
            XWPFDocument document = new XWPFDocument(content);
            
            // 2. 处理文档内容
            // TODO: 实现文档保存逻辑
            
            // 3. 记录审计日志
            auditService.recordOperation(
                "DOCUMENT",
                documentId.toString(),
                "SAVE",
                userId,
                "保存Office文档"
            );
        } catch (IOException e) {
            log.error("保存Office文档失败", e);
            throw new RuntimeException("保存Office文档失败", e);
        }
    }

    @Override
    public List<DocumentVO> getEditHistory(Long documentId) {
        // TODO: 实现编辑历史记录查询
        return List.of();
    }
} 