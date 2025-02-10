package com.lawfirm.model.base.storage.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件预览信息
 */
@Data
@Accessors(chain = true)
public class PreviewInfo {
    
    /**
     * 预览ID
     */
    private String id;
    
    /**
     * 文件ID
     */
    private String fileId;
    
    /**
     * 预览类型（PDF/IMAGE/HTML/OFFICE）
     */
    private String previewType;
    
    /**
     * 预览URL
     */
    private String previewUrl;
    
    /**
     * 预览状态（READY/GENERATING/FAILED）
     */
    private String status;
    
    /**
     * 错误信息
     */
    private String error;
    
    /**
     * 缩略图URL
     */
    private String thumbnailUrl;
    
    /**
     * 页数（文档类型）
     */
    private Integer pageCount;
    
    /**
     * 分辨率（图片类型）
     */
    private String resolution;
    
    /**
     * 水印文本
     */
    private String watermark;
    
    /**
     * 预览参数（JSON格式）
     */
    private String params;
} 