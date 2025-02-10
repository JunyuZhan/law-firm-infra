package com.lawfirm.model.document.service;

import com.lawfirm.model.document.dto.PreviewGenerateDTO;
import com.lawfirm.model.document.entity.DocumentPreview;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 预览格式转换服务接口
 */
public interface PreviewConverterService {
    
    /**
     * 转换为PDF
     */
    void convertToPdf(InputStream input, OutputStream output, String sourceType);
    
    /**
     * 转换为HTML
     */
    void convertToHtml(InputStream input, OutputStream output, String sourceType);
    
    /**
     * 转换为图片
     */
    void convertToImage(InputStream input, OutputStream output, String sourceType, Integer pageNumber);
    
    /**
     * 生成缩略图
     */
    void generateThumbnail(InputStream input, OutputStream output, String sourceType, Integer width, Integer height);
    
    /**
     * 添加水印
     */
    void addWatermark(InputStream input, OutputStream output, String watermark);
    
    /**
     * 获取文档页数
     */
    Integer getPageCount(InputStream input, String sourceType);
    
    /**
     * 检查文件类型是否支持转换
     */
    boolean isSupported(String sourceType, String targetType);
    
    /**
     * 获取支持的源文件类型
     */
    String[] getSupportedSourceTypes();
    
    /**
     * 获取支持的目标文件类型
     */
    String[] getSupportedTargetTypes();
} 