package com.lawfirm.model.document.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 预览生成DTO
 */
@Data
@Accessors(chain = true)
public class PreviewGenerateDTO {
    
    private Long documentId;          // 文档ID
    private String sourceUrl;         // 源文件URL
    private String sourceType;        // 源文件类型
    private String targetType;        // 目标类型
    private Boolean forceRegenerate;  // 是否强制重新生成
    private String watermark;         // 水印内容
    private String callback;          // 回调地址
} 