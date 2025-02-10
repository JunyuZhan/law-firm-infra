package com.lawfirm.model.document.service.impl;

import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.entity.DocumentOcrResult;
import com.lawfirm.model.document.enums.OcrStatusEnum;
import com.lawfirm.model.document.repository.DocumentOcrResultRepository;
import com.lawfirm.model.document.repository.DocumentRepository;
import com.lawfirm.model.document.service.DocumentOcrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档OCR服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentOcrServiceImpl implements DocumentOcrService {

    private final DocumentRepository documentRepository;
    private final DocumentOcrResultRepository ocrResultRepository;

    @Override
    @Transactional
    public void submitOcrTask(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("文档不存在"));
        
        // 更新文档OCR状态为处理中
        document.setOcrStatus(OcrStatusEnum.PROCESSING);
        documentRepository.save(document);
        
        // TODO: 调用实际的OCR服务进行处理
        // 这里需要集成具体的OCR实现，如百度OCR、阿里OCR等
    }

    @Override
    public List<DocumentOcrResult> getOcrResults(Long documentId) {
        return ocrResultRepository.findByDocumentIdOrderByPageNoAsc(documentId);
    }

    @Override
    public String getOcrStatus(Long documentId) {
        return documentRepository.findById(documentId)
                .map(doc -> doc.getOcrStatus().getCode())
                .orElse(null);
    }

    @Override
    @Transactional
    public void updateOcrResult(DocumentOcrResult result) {
        Document document = documentRepository.findById(result.getDocumentId())
                .orElseThrow(() -> new IllegalArgumentException("文档不存在"));
        
        // 保存OCR结果
        ocrResultRepository.save(result);
        
        // 更新文档OCR状态
        document.setOcrStatus(OcrStatusEnum.COMPLETED);
        document.setOcrTime(LocalDateTime.now());
        documentRepository.save(document);
    }

    @Override
    @Transactional
    public void deleteOcrResults(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("文档不存在"));
        
        // 删除OCR结果
        ocrResultRepository.deleteByDocumentId(documentId);
        
        // 重置文档OCR状态
        document.setOcrStatus(OcrStatusEnum.NOT_STARTED);
        document.setOcrTime(null);
        document.setOcrErrorMessage(null);
        documentRepository.save(document);
    }

    @Override
    public byte[] exportOcrResult(Long documentId, String format) {
        List<DocumentOcrResult> results = getOcrResults(documentId);
        if (results.isEmpty()) {
            throw new IllegalStateException("OCR结果不存在");
        }
        
        // TODO: 根据不同格式导出OCR结果
        // 这里需要实现具体的导出逻辑
        return new byte[0];
    }
} 