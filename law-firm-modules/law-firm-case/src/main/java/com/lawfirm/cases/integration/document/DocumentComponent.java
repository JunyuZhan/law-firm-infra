package com.lawfirm.cases.integration.document;

import com.lawfirm.model.document.dto.DocumentDTO;
import com.lawfirm.model.document.dto.DocumentUploadDTO;
import com.lawfirm.model.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 文档组件
 * 负责与文档模块的集成，提供案件文档管理功能
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentComponent {

    private final DocumentService documentService;

    /**
     * 上传案件文档
     *
     * @param caseId 案件ID
     * @param file 文件
     * @param documentType 文档类型
     * @param name 文档名称
     * @param description 文档描述
     * @param userId 上传用户ID
     * @return 文档ID
     */
    public Optional<Long> uploadCaseDocument(Long caseId, MultipartFile file, String documentType, 
                                             String name, String description, Long userId) {
        if (caseId == null || file == null || file.isEmpty()) {
            log.warn("上传案件文档参数无效，caseId={}", caseId);
            return Optional.empty();
        }

        try {
            DocumentUploadDTO uploadDTO = DocumentUploadDTO.builder()
                    .businessType("CASE")
                    .businessId(caseId)
                    .documentType(documentType)
                    .name(name)
                    .description(description)
                    .createUserId(userId)
                    .build();

            Long documentId = documentService.uploadDocument(file, uploadDTO);
            return Optional.ofNullable(documentId);
        } catch (Exception e) {
            log.error("上传案件文档异常，caseId={}", caseId, e);
            return Optional.empty();
        }
    }

    /**
     * 获取案件的文档列表
     *
     * @param caseId 案件ID
     * @return 文档列表
     */
    public List<DocumentDTO> getCaseDocuments(Long caseId) {
        if (caseId == null) {
            return Collections.emptyList();
        }

        try {
            return documentService.getBusinessDocuments("CASE", caseId);
        } catch (Exception e) {
            log.error("获取案件文档列表异常，caseId={}", caseId, e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取文档详情
     *
     * @param documentId 文档ID
     * @return 文档详情
     */
    public Optional<DocumentDTO> getDocumentDetail(Long documentId) {
        if (documentId == null) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(documentService.getDocumentDetail(documentId));
        } catch (Exception e) {
            log.error("获取文档详情异常，documentId={}", documentId, e);
            return Optional.empty();
        }
    }

    /**
     * 删除案件文档
     *
     * @param documentId 文档ID
     * @param userId 操作用户ID
     * @return 是否删除成功
     */
    public boolean deleteDocument(Long documentId, Long userId) {
        if (documentId == null) {
            return false;
        }

        try {
            return documentService.deleteDocument(documentId, userId);
        } catch (Exception e) {
            log.error("删除文档异常，documentId={}", documentId, e);
            return false;
        }
    }

    /**
     * 设置文档标签
     *
     * @param documentId 文档ID
     * @param tags 标签列表
     * @return 是否设置成功
     */
    public boolean setDocumentTags(Long documentId, List<String> tags) {
        if (documentId == null || tags == null) {
            return false;
        }

        try {
            return documentService.setDocumentTags(documentId, tags);
        } catch (Exception e) {
            log.error("设置文档标签异常，documentId={}", documentId, e);
            return false;
        }
    }
} 