package com.lawfirm.document.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文档添加请求
 */
@Data
public class DocumentAddRequest {
    
    /**
     * 文档编号
     */
    @NotBlank(message = "文档编号不能为空")
    private String docNo;
    
    /**
     * 文档名称
     */
    @NotBlank(message = "文档名称不能为空")
    private String docName;
    
    /**
     * 文档类型
     */
    @NotNull(message = "文档类型不能为空")
    private Integer docType;
    
    /**
     * 文件类型
     */
    @NotBlank(message = "文件类型不能为空")
    private String fileType;
    
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