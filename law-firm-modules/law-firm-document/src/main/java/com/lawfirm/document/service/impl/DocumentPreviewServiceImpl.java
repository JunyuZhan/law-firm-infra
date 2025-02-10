package com.lawfirm.model.document.service.impl;

import com.lawfirm.model.document.dto.DocumentPreviewDTO;
import com.lawfirm.model.document.dto.PreviewGenerateDTO;
import com.lawfirm.model.document.entity.DocumentPreview;
import com.lawfirm.model.document.enums.DocumentPreviewStatusEnum;
import com.lawfirm.model.document.repository.DocumentPreviewRepository;
import com.lawfirm.model.document.service.DocumentPreviewService;
import com.lawfirm.model.document.vo.DocumentPreviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档预览服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentPreviewServiceImpl implements DocumentPreviewService {

    private final DocumentPreviewRepository documentPreviewRepository;
    
    @Override
    @Transactional
    public DocumentPreview generatePreview(PreviewGenerateDTO generateDTO) {
        // 检查是否已存在预览记录
        DocumentPreview preview = documentPreviewRepository.findByDocumentId(generateDTO.getDocumentId())
                .orElse(new DocumentPreview().setDocumentId(generateDTO.getDocumentId()));
        
        // 设置预览信息
        preview.setPreviewStatus(DocumentPreviewStatusEnum.GENERATING)
                .setSourceType(generateDTO.getSourceType())
                .setPreviewFormat(generateDTO.getTargetType())
                .setWatermark(generateDTO.getWatermark())
                .setCallback(generateDTO.getCallback())
                .setRetryCount(0);
        
        return documentPreviewRepository.save(preview);
    }
    
    @Override
    public DocumentPreviewVO getPreviewInfo(Long documentId) {
        DocumentPreview preview = documentPreviewRepository.findByDocumentId(documentId)
                .orElse(null);
        
        if (preview == null) {
            return new DocumentPreviewVO()
                    .setDocumentId(documentId)
                    .setPreviewStatus(DocumentPreviewStatusEnum.NOT_STARTED);
        }
        
        DocumentPreviewVO vo = new DocumentPreviewVO();
        BeanUtils.copyProperties(preview, vo);
        return vo;
    }
    
    @Override
    @Transactional
    public void updatePreviewStatus(Long documentId, String status, String errorMessage) {
        DocumentPreview preview = documentPreviewRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new IllegalStateException("预览记录不存在"));
        
        preview.setPreviewStatus(DocumentPreviewStatusEnum.valueOf(status));
        if (DocumentPreviewStatusEnum.FAILED.name().equals(status)) {
            preview.setErrorMessage(errorMessage);
        } else if (DocumentPreviewStatusEnum.GENERATED.name().equals(status)) {
            preview.setPreviewGenerateTime(LocalDateTime.now());
        }
        
        documentPreviewRepository.save(preview);
    }
    
    @Override
    @Transactional
    public void deletePreview(Long documentId) {
        documentPreviewRepository.deleteByDocumentId(documentId);
    }
    
    @Override
    @Transactional
    public void cleanExpiredPreviews(Integer days) {
        LocalDateTime beforeTime = LocalDateTime.now().minusDays(days);
        List<DocumentPreview> expiredPreviews = documentPreviewRepository.findPreviewsToClean(beforeTime);
        documentPreviewRepository.deleteAll(expiredPreviews);
    }
    
    @Override
    @Transactional
    public void retryFailedPreviews(Integer maxRetries) {
        LocalDateTime beforeTime = LocalDateTime.now().minusHours(1);
        List<DocumentPreview> failedPreviews = documentPreviewRepository
                .findByPreviewStatusAndCreateTimeBeforeAndRetryCountLessThan(
                        DocumentPreviewStatusEnum.FAILED, beforeTime, maxRetries);
        
        for (DocumentPreview preview : failedPreviews) {
            preview.setPreviewStatus(DocumentPreviewStatusEnum.GENERATING)
                    .setRetryCount(preview.getRetryCount() + 1)
                    .setErrorMessage(null);
            documentPreviewRepository.save(preview);
        }
    }
    
    @Override
    public DocumentPreview getPreview(Long previewId) {
        return documentPreviewRepository.findById(previewId).orElse(null);
    }
    
    @Override
    public Page<DocumentPreview> listPreviews(Long documentId, Pageable pageable) {
        return documentPreviewRepository.findByDocumentIdOrderByCreateTimeDesc(documentId, pageable);
    }
    
    @Override
    public List<DocumentPreview> getGeneratingPreviews() {
        return documentPreviewRepository.findByPreviewStatus(DocumentPreviewStatusEnum.GENERATING);
    }
    
    @Override
    public List<DocumentPreview> getFailedPreviews() {
        return documentPreviewRepository.findByPreviewStatus(DocumentPreviewStatusEnum.FAILED);
    }
    
    @Override
    @Transactional
    public DocumentPreview updatePreviewConfig(Long documentId, DocumentPreviewDTO previewDTO) {
        DocumentPreview preview = documentPreviewRepository.findByDocumentId(documentId)
                .orElse(new DocumentPreview().setDocumentId(documentId));
        
        preview.setWatermark(previewDTO.getWatermark())
                .setEnableDownload(previewDTO.getEnableDownload())
                .setPreviewQuality(previewDTO.getPreviewQuality())
                .setPreviewFormat(previewDTO.getPreviewFormat());
        
        return documentPreviewRepository.save(preview);
    }
    
    @Override
    public boolean isPreviewAvailable(Long documentId) {
        return documentPreviewRepository.findByDocumentId(documentId)
                .map(preview -> DocumentPreviewStatusEnum.GENERATED.equals(preview.getPreviewStatus()))
                .orElse(false);
    }
    
    @Override
    public String getPreviewUrl(Long documentId) {
        return documentPreviewRepository.findByDocumentId(documentId)
                .filter(preview -> DocumentPreviewStatusEnum.GENERATED.equals(preview.getPreviewStatus()))
                .map(DocumentPreview::getPreviewUrl)
                .orElse(null);
    }
} 