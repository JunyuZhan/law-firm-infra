package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentOcrResult;

import java.util.List;

/**
 * 文档OCR服务接口
 */
public interface DocumentOcrService {
    
    /**
     * 提交OCR任务
     *
     * @param documentId 文档ID
     */
    void submitOcrTask(Long documentId);
    
    /**
     * 获取OCR结果
     *
     * @param documentId 文档ID
     * @return OCR结果列表（按页码排序）
     */
    List<DocumentOcrResult> getOcrResults(Long documentId);
    
    /**
     * 获取OCR任务状态
     *
     * @param documentId 文档ID
     * @return OCR状态
     */
    String getOcrStatus(Long documentId);
    
    /**
     * 更新OCR结果
     *
     * @param result OCR结果
     */
    void updateOcrResult(DocumentOcrResult result);
    
    /**
     * 删除OCR结果
     *
     * @param documentId 文档ID
     */
    void deleteOcrResults(Long documentId);
    
    /**
     * 导出OCR结果
     *
     * @param documentId 文档ID
     * @param format 导出格式（TXT/PDF/WORD等）
     * @return 导出文件的字节数组
     */
    byte[] exportOcrResult(Long documentId, String format);
} 