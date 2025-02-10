package com.lawfirm.document.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文档版本添加请求
 */
@Data
public class DocumentVersionAddRequest {
    
    /**
     * 文档ID
     */
    @NotNull(message = "文档ID不能为空")
    private Long docId;
    
    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;
    
    /**
     * 存储路径
     */
    @NotBlank(message = "存储路径不能为空")
    private String storagePath;
    
    /**
     * 变更说明
     */
    @NotBlank(message = "变更说明不能为空")
    private String changeDesc;
    
    /**
     * 备注
     */
    private String remark;
} 