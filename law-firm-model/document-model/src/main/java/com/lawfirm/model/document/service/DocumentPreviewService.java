package com.lawfirm.model.document.service;

import com.lawfirm.model.document.dto.DocumentPreviewDTO;
import com.lawfirm.model.document.dto.PreviewGenerateDTO;
import com.lawfirm.model.document.entity.DocumentPreview;
import com.lawfirm.model.document.vo.DocumentPreviewVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文档预览服务接口
 */
public interface DocumentPreviewService {
    
    /**
     * 生成预览
     */
    DocumentPreview generatePreview(PreviewGenerateDTO generateDTO);
    
    /**
     * 获取预览信息
     */
    DocumentPreviewVO getPreviewInfo(Long documentId);
    
    /**
     * 更新预览状态
     */
    void updatePreviewStatus(Long documentId, String status, String errorMessage);
    
    /**
     * 删除预览
     */
    void deletePreview(Long documentId);
    
    /**
     * 清理过期预览
     */
    void cleanExpiredPreviews(Integer days);
    
    /**
     * 重试失败的预览
     */
    void retryFailedPreviews(Integer maxRetries);
    
    /**
     * 获取预览记录
     */
    DocumentPreview getPreview(Long previewId);
    
    /**
     * 分页查询预览记录
     */
    Page<DocumentPreview> listPreviews(Long documentId, Pageable pageable);
    
    /**
     * 获取正在生成的预览
     */
    List<DocumentPreview> getGeneratingPreviews();
    
    /**
     * 获取生成失败的预览
     */
    List<DocumentPreview> getFailedPreviews();
    
    /**
     * 更新预览配置
     */
    DocumentPreview updatePreviewConfig(Long documentId, DocumentPreviewDTO previewDTO);
    
    /**
     * 检查预览是否可用
     */
    boolean isPreviewAvailable(Long documentId);
    
    /**
     * 获取预览URL
     */
    String getPreviewUrl(Long documentId);
} 