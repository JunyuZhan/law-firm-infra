package com.lawfirm.model.base.storage.service;

import com.lawfirm.model.base.storage.model.PreviewInfo;

/**
 * 文件预览服务接口
 */
public interface PreviewService {
    
    /**
     * 生成预览
     *
     * @param fileId 文件ID
     * @param previewType 预览类型
     * @param params 预览参数
     * @return 预览信息
     */
    PreviewInfo generatePreview(String fileId, String previewType, String params);
    
    /**
     * 获取预览信息
     *
     * @param fileId 文件ID
     * @return 预览信息
     */
    PreviewInfo getPreviewInfo(String fileId);
    
    /**
     * 生成缩略图
     *
     * @param fileId 文件ID
     * @param width 宽度
     * @param height 高度
     * @return 预览信息
     */
    PreviewInfo generateThumbnail(String fileId, Integer width, Integer height);
    
    /**
     * 添加水印
     *
     * @param fileId 文件ID
     * @param watermark 水印文本
     * @return 预览信息
     */
    PreviewInfo addWatermark(String fileId, String watermark);
    
    /**
     * 转换为PDF
     *
     * @param fileId 文件ID
     * @return 预览信息
     */
    PreviewInfo convertToPdf(String fileId);
    
    /**
     * 获取文档页数
     *
     * @param fileId 文件ID
     * @return 页数
     */
    Integer getPageCount(String fileId);
    
    /**
     * 获取指定页预览
     *
     * @param fileId 文件ID
     * @param pageNumber 页码
     * @return 预览信息
     */
    PreviewInfo getPagePreview(String fileId, Integer pageNumber);
} 