package com.lawfirm.archive.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 档案更新DTO
 */
@Data
public class ArchiveUpdateDTO {
    
    /**
     * 档案ID
     */
    @NotNull(message = "档案ID不能为空")
    private Long id;
    
    /**
     * 档案名称
     */
    private String archiveName;
    
    /**
     * 档案分类ID
     */
    private Long categoryId;
    
    /**
     * 档案描述
     */
    private String description;
    
    /**
     * 存储位置
     */
    private String location;
    
    /**
     * 保管期限
     */
    private String retentionPeriod;
    
    /**
     * 密级
     */
    private String securityLevel;
    
    /**
     * 电子文档路径
     */
    private String documentPath;
    
    /**
     * 备注
     */
    private String remarks;
} 