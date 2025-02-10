package com.lawfirm.model.document.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文档预览数据传输对象
 */
@Data
@Accessors(chain = true)
public class DocumentPreviewDTO {
    
    @NotNull(message = "文档ID不能为空")
    private Long documentId;              // 文档ID
    
    private String watermark;             // 水印内容
    private Boolean enableDownload;       // 允许下载
    private Integer previewQuality;       // 预览质量（1-100）
    private String previewFormat;         // 预览格式（PDF/HTML等）
} 