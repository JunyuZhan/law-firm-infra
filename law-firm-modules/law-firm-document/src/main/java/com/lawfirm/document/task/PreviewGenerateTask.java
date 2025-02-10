package com.lawfirm.document.task;

import com.lawfirm.core.storage.model.PreviewInfo;
import com.lawfirm.core.storage.service.PreviewService;
import com.lawfirm.core.storage.service.StorageService;
import com.lawfirm.document.entity.DocumentPreview;
import com.lawfirm.document.entity.DocumentStorage;
import com.lawfirm.document.enums.DocumentPreviewStatusEnum;
import com.lawfirm.model.document.repository.DocumentPreviewRepository;
import com.lawfirm.model.document.repository.DocumentStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预览生成任务处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PreviewGenerateTask {

    private final DocumentPreviewRepository documentPreviewRepository;
    private final DocumentStorageRepository documentStorageRepository;
    private final PreviewService previewService;
    private final StorageService storageService;

    /**
     * 异步生成预览
     */
    @Async("previewTaskExecutor")
    @Transactional
    public void generatePreview(Long documentId) {
        try {
            // 获取预览记录
            DocumentPreview preview = documentPreviewRepository.findByDocumentId(documentId)
                    .orElseThrow(() -> new IllegalStateException("预览记录不存在"));

            // 获取存储记录
            DocumentStorage storage = documentStorageRepository.findFirstByDocumentIdOrderByCreateTimeDesc(documentId);
            if (storage == null) {
                throw new IllegalStateException("文档存储记录不存在");
            }

            // 生成预览
            PreviewInfo previewInfo = previewService.generatePreview(
                    storage.getFileHash(),
                    preview.getPreviewFormat(),
                    buildPreviewParams(preview)
            );

            // 更新预览记录
            preview.setPreviewStatus(DocumentPreviewStatusEnum.GENERATED)
                    .setPreviewUrl(previewInfo.getPreviewUrl())
                    .setPreviewGenerateTime(LocalDateTime.now())
                    .setPreviewFileSize(storage.getFileSize())
                    .setPageCount(previewInfo.getPageCount());

            documentPreviewRepository.save(preview);

            log.info("预览生成成功：documentId={}", documentId);
        } catch (Exception e) {
            log.error("预览生成失败：documentId=" + documentId, e);
            updatePreviewStatus(documentId, DocumentPreviewStatusEnum.FAILED, e.getMessage());
        }
    }

    /**
     * 批量处理待生成的预览
     */
    @Async("previewTaskExecutor")
    @Transactional
    public void processGeneratingPreviews() {
        List<DocumentPreview> generatingPreviews = documentPreviewRepository
                .findByPreviewStatus(DocumentPreviewStatusEnum.GENERATING);

        for (DocumentPreview preview : generatingPreviews) {
            generatePreview(preview.getDocumentId());
        }
    }

    /**
     * 重试失败的预览
     */
    @Async("previewTaskExecutor")
    @Transactional
    public void retryFailedPreviews(Integer maxRetries) {
        LocalDateTime beforeTime = LocalDateTime.now().minusHours(1);
        List<DocumentPreview> failedPreviews = documentPreviewRepository
                .findByPreviewStatusAndCreateTimeBeforeAndRetryCountLessThan(
                        DocumentPreviewStatusEnum.FAILED, beforeTime, maxRetries);

        for (DocumentPreview preview : failedPreviews) {
            generatePreview(preview.getDocumentId());
        }
    }

    /**
     * 清理过期预览
     */
    @Async("previewTaskExecutor")
    @Transactional
    public void cleanExpiredPreviews(Integer days) {
        LocalDateTime beforeTime = LocalDateTime.now().minusDays(days);
        List<DocumentPreview> expiredPreviews = documentPreviewRepository.findPreviewsToClean(beforeTime);

        for (DocumentPreview preview : expiredPreviews) {
            try {
                // 删除预览文件
                if (preview.getPreviewUrl() != null) {
                    String fileId = extractFileId(preview.getPreviewUrl());
                    storageService.delete(fileId);
                }
                // 删除预览记录
                documentPreviewRepository.delete(preview);
            } catch (Exception e) {
                log.error("清理过期预览失败：previewId=" + preview.getId(), e);
            }
        }
    }

    private void updatePreviewStatus(Long documentId, DocumentPreviewStatusEnum status, String errorMessage) {
        try {
            DocumentPreview preview = documentPreviewRepository.findByDocumentId(documentId)
                    .orElseThrow(() -> new IllegalStateException("预览记录不存在"));

            preview.setPreviewStatus(status)
                    .setErrorMessage(errorMessage);

            documentPreviewRepository.save(preview);
        } catch (Exception e) {
            log.error("更新预览状态失败：documentId=" + documentId, e);
        }
    }

    private String buildPreviewParams(DocumentPreview preview) {
        // TODO: 构建预览参数
        return null;
    }

    private String extractFileId(String previewUrl) {
        // TODO: 从预览URL中提取文件ID
        return null;
    }
} 