package com.lawfirm.model.document.vo;

import com.lawfirm.model.document.enums.DocumentPreviewStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档预览视图对象
 */
@Data
@Accessors(chain = true)
public class DocumentPreviewVO {
    
    private Long documentId;              // 文档ID
    private DocumentPreviewStatusEnum previewStatus;  // 预览状态
    private String previewUrl;            // 预览文件URL
    private LocalDateTime previewGenerateTime;  // 预览生成时间
    private Long previewFileSize;         // 预览文件大小
    private String errorMessage;          // 错误信息（预览失败时）
} 