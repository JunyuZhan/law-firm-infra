package com.lawfirm.document.service.impl;

import com.lawfirm.model.document.dto.OcrRequestDTO;
import com.lawfirm.model.document.service.OcrService;
import com.lawfirm.model.document.vo.OcrResultVO;
import com.lawfirm.document.service.ocr.OcrEngineFactory;
import com.lawfirm.document.service.ocr.OcrEngine;
import com.lawfirm.document.service.ocr.ImagePreprocessor;
import com.lawfirm.document.service.ocr.StructuredDataExtractor;
import com.lawfirm.common.core.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OCR识别服务实现
 */
@Slf4j
@Service("ocrService")  // 指定bean名称
@RequiredArgsConstructor
public class OcrServiceImpl implements OcrService {
    
    @Qualifier("ocrEngineFactory")
    private final OcrEngineFactory ocrEngineFactory;
    
    @Qualifier("imagePreprocessor")
    private final ImagePreprocessor imagePreprocessor;
    
    @Qualifier("structuredDataExtractor")
    private final StructuredDataExtractor structuredDataExtractor;
    
    // 缓存支持的文档类型和引擎类型
    private static final List<String> SUPPORTED_DOCUMENT_TYPES = Arrays.asList(
        "general", "contract", "id_card", "business_license", 
        "court_document", "evidence", "invoice", "bank_card"
    );
    
    private static final List<String> AVAILABLE_ENGINES = Arrays.asList(
        "baidu", "tencent", "aliyun", "tesseract"
    );
    
    // OCR历史记录缓存（实际应用中应使用数据库）
    private final Map<String, List<OcrResultVO>> ocrHistoryCache = new ConcurrentHashMap<>();
    
    @Override
    public OcrResultVO recognizeImage(OcrRequestDTO requestDTO) {
        String recognitionId = UUID.randomUUID().toString().replace("-", "");
        long startTime = System.currentTimeMillis();
        
        log.info("开始OCR识别，ID: {}, 文档类型: {}, 引擎: {}", 
                recognitionId, requestDTO.getDocumentType(), requestDTO.getEngineType());
        
        try {
            // 1. 验证文件
            validateImageFile(requestDTO.getImageFile());
            
            // 2. 图像预处理
            byte[] processedImage = imagePreprocessor.preprocess(
                requestDTO.getImageFile().getBytes(), 
                requestDTO.getDocumentType()
            );
            
            // 3. 获取OCR引擎
            OcrEngine ocrEngine = ocrEngineFactory.getEngine(requestDTO.getEngineType());
            
            // 4. 执行OCR识别
            OcrResultVO result = ocrEngine.recognize(
                processedImage, 
                requestDTO.getLanguage(),
                requestDTO.getDocumentType()
            );
            
            // 5. 设置基本信息
            result.setRecognitionId(recognitionId);
            result.setDocumentType(requestDTO.getDocumentType());
            result.setEngineType(requestDTO.getEngineType());
            result.setRecognitionTime(LocalDateTime.now());
            result.setProcessingTime(System.currentTimeMillis() - startTime);
            result.setStatus("SUCCESS");
            
            // 6. 结构化数据提取
            if (Boolean.TRUE.equals(requestDTO.getNeedStructured()) && result.getRawText() != null) {
                Map<String, Object> structuredData = extractStructuredData(
                    result.getRawText(), 
                    requestDTO.getDocumentType()
                );
                result.setStructuredData(structuredData);
            }
            
            // 7. 设置图片信息
            OcrResultVO.ImageInfo imageInfo = new OcrResultVO.ImageInfo();
            imageInfo.setSize(requestDTO.getImageFile().getSize());
            imageInfo.setFormat(getFileExtension(requestDTO.getImageFile().getOriginalFilename()));
            result.setImageInfo(imageInfo);
            
            // 8. 保存识别历史
            saveOcrHistory(requestDTO, result);
            
            log.info("OCR识别完成，ID: {}, 耗时: {}ms, 置信度: {}", 
                    recognitionId, result.getProcessingTime(), result.getConfidence());
            
            return result;
            
        } catch (Exception e) {
            log.error("OCR识别失败，ID: {}", recognitionId, e);
            
            // 构建失败结果
            OcrResultVO failResult = new OcrResultVO();
            failResult.setRecognitionId(recognitionId);
            failResult.setDocumentType(requestDTO.getDocumentType());
            failResult.setEngineType(requestDTO.getEngineType());
            failResult.setRecognitionTime(LocalDateTime.now());
            failResult.setProcessingTime(System.currentTimeMillis() - startTime);
            failResult.setStatus("FAILED");
            failResult.setErrorMessage(e.getMessage());
            
            return failResult;
        }
    }
    
    @Override
    public List<OcrResultVO> batchRecognize(List<MultipartFile> files, String documentType, String engineType) {
        log.info("开始批量OCR识别，文件数量: {}", files.size());
        
        List<CompletableFuture<OcrResultVO>> futures = files.stream()
            .map(file -> CompletableFuture.supplyAsync(() -> {
                OcrRequestDTO requestDTO = new OcrRequestDTO();
                requestDTO.setImageFile(file);
                requestDTO.setDocumentType(documentType);
                requestDTO.setEngineType(engineType);
                return recognizeImage(requestDTO);
            }))
            .toList();
        
        return futures.stream()
            .map(CompletableFuture::join)
            .toList();
    }
    
    @Override
    public List<String> getSupportedDocumentTypes() {
        return new ArrayList<>(SUPPORTED_DOCUMENT_TYPES);
    }
    
    @Override
    public List<String> getAvailableEngines() {
        return new ArrayList<>(AVAILABLE_ENGINES);
    }
    
    @Override
    public Map<String, Object> extractStructuredData(String rawText, String documentType) {
        return structuredDataExtractor.extract(rawText, documentType);
    }
    
    @Override
    public List<OcrResultVO> getOcrHistory(Long businessId, String businessType) {
        String key = businessType + "_" + businessId;
        return ocrHistoryCache.getOrDefault(key, new ArrayList<>());
    }
    
    /**
     * 验证图片文件
     */
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("图片文件不能为空");
        }
        
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new BusinessException("文件名不能为空");
        }
        
        String extension = getFileExtension(filename).toLowerCase();
        if (!Arrays.asList("jpg", "jpeg", "png", "bmp", "tiff", "pdf").contains(extension)) {
            throw new BusinessException("不支持的文件格式: " + extension);
        }
        
        // 限制文件大小 (20MB)
        if (file.getSize() > 20 * 1024 * 1024) {
            throw new BusinessException("文件大小不能超过20MB");
        }
    }
    
    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
    
    /**
     * 保存OCR识别历史
     */
    private void saveOcrHistory(OcrRequestDTO requestDTO, OcrResultVO result) {
        if (requestDTO.getBusinessId() != null && requestDTO.getBusinessType() != null) {
            String key = requestDTO.getBusinessType() + "_" + requestDTO.getBusinessId();
            ocrHistoryCache.computeIfAbsent(key, k -> new ArrayList<>()).add(result);
        }
    }
} 