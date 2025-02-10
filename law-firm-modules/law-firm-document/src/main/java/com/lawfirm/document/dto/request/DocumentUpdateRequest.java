package com.lawfirm.document.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 文档更新请求
 */
@Data
public class DocumentUpdateRequest {
    
    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;
    
    /**
     * 文档名称
     */
    private String docName;
    
    /**
     * 文档类型
     */
    private Integer docType;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 关键词
     */
    private String keywords;
    
    /**
     * 文档描述
     */
    private String description;
    
    /**
     * 备注
     */
    private String remark;
} 