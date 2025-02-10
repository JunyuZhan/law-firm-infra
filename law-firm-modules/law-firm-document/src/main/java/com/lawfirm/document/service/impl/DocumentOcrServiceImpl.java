package com.lawfirm.document.service.impl;

import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.entity.DocumentOcrResult;
import com.lawfirm.model.document.enums.OcrStatusEnum;
import com.lawfirm.model.document.repository.DocumentOcrResultRepository;
import com.lawfirm.model.document.repository.DocumentRepository;
import com.lawfirm.model.document.service.DocumentOcrService;
import com.lawfirm.document.service.IDocumentStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * OCR服务实现
 * 实现model层定义的DocumentOcrService接口
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentOcrServiceImpl implements DocumentOcrService {

    private final DocumentRepository documentRepository;
    private final DocumentOcrResultRepository ocrResultRepository;
    private final IDocumentStorageService storageService;
    private final OcrProcessor ocrProcessor;

    @Override
    @Transactional
    public void submitOcrTask(Long documentId) {
        log.info("提交OCR任务，文档ID：{}", documentId);
        
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("文档不存在"));
        
        try {
            // 更新文档OCR状态为处理中
            document.setOcrStatus(OcrStatusEnum.PROCESSING);
            documentRepository.save(document);
            
            // 获取文档内容
            InputStream fileContent = storageService.getFileContent(document.getDocumentNumber(), document.getCurrentVersion());
            
            // 调用OCR处理器进行识别
            ocrProcessor.process(documentId, fileContent);
            
        } catch (Exception e) {
            log.error("提交OCR任务失败", e);
            document.setOcrStatus(OcrStatusEnum.FAILED);
            document.setOcrErrorMessage(e.getMessage());
            documentRepository.save(document);
            throw new RuntimeException("提交OCR任务失败", e);
        }
    }

    @Override
    public List<DocumentOcrResult> getOcrResults(Long documentId) {
        log.debug("获取OCR结果，文档ID：{}", documentId);
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
        log.info("更新OCR结果，文档ID：{}，页码：{}", result.getDocumentId(), result.getPageNo());
        
        Document document = documentRepository.findById(result.getDocumentId())
                .orElseThrow(() -> new IllegalArgumentException("文档不存在"));
        
        try {
            // 保存OCR结果
            ocrResultRepository.save(result);
            
            // 更新文档OCR状态
            document.setOcrStatus(OcrStatusEnum.COMPLETED);
            document.setOcrTime(LocalDateTime.now());
            documentRepository.save(document);
            
        } catch (Exception e) {
            log.error("更新OCR结果失败", e);
            throw new RuntimeException("更新OCR结果失败", e);
        }
    }

    @Override
    @Transactional
    public void deleteOcrResults(Long documentId) {
        log.info("删除OCR结果，文档ID：{}", documentId);
        
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("文档不存在"));
        
        try {
            // 删除OCR结果
            ocrResultRepository.deleteByDocumentId(documentId);
            
            // 重置文档OCR状态
            document.setOcrStatus(OcrStatusEnum.NOT_STARTED);
            document.setOcrTime(null);
            document.setOcrErrorMessage(null);
            documentRepository.save(document);
            
        } catch (Exception e) {
            log.error("删除OCR结果失败", e);
            throw new RuntimeException("删除OCR结果失败", e);
        }
    }

    @Override
    public byte[] exportOcrResult(Long documentId, String format) {
        log.info("导出OCR结果，文档ID：{}，格式：{}", documentId, format);
        
        List<DocumentOcrResult> results = getOcrResults(documentId);
        if (results.isEmpty()) {
            throw new IllegalStateException("OCR结果不存在");
        }
        
        try {
            return ocrProcessor.exportResult(results, format);
        } catch (Exception e) {
            log.error("导出OCR结果失败", e);
            throw new RuntimeException("导出OCR结果失败", e);
        }
    }
} 