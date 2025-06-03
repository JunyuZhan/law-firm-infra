package com.lawfirm.model.document.service;

import com.lawfirm.model.document.dto.OcrRequestDTO;
import com.lawfirm.model.document.vo.OcrResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * OCR识别服务接口
 */
public interface OcrService {
    
    /**
     * 图片文字识别
     * 
     * @param requestDTO OCR识别请求
     * @return 识别结果
     */
    OcrResultVO recognizeImage(OcrRequestDTO requestDTO);
    
    /**
     * 批量图片识别
     * 
     * @param files 图片文件列表
     * @param documentType 文档类型
     * @param engineType OCR引擎类型
     * @return 识别结果列表
     */
    List<OcrResultVO> batchRecognize(List<MultipartFile> files, String documentType, String engineType);
    
    /**
     * 获取支持的文档类型
     * 
     * @return 支持的文档类型列表
     */
    List<String> getSupportedDocumentTypes();
    
    /**
     * 获取可用的OCR引擎
     * 
     * @return OCR引擎列表
     */
    List<String> getAvailableEngines();
    
    /**
     * 根据文档类型进行结构化数据提取
     * 
     * @param rawText 原始识别文本
     * @param documentType 文档类型
     * @return 结构化数据
     */
    Map<String, Object> extractStructuredData(String rawText, String documentType);
    
    /**
     * 获取OCR识别历史
     * 
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 识别历史列表
     */
    List<OcrResultVO> getOcrHistory(Long businessId, String businessType);
} 