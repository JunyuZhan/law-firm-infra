package com.lawfirm.archive.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 档案创建DTO
 */
@Data
public class ArchiveCreateDTO {
    
    /**
     * 档案编号
     */
    @NotBlank(message = "档案编号不能为空")
    private String archiveNumber;
    
    /**
     * 档案名称
     */
    @NotBlank(message = "档案名称不能为空")
    private String archiveName;
    
    /**
     * 档案分类ID
     */
    @NotNull(message = "档案分类不能为空")
    private Long categoryId;
    
    /**
     * 档案描述
     */
    private String description;
    
    /**
     * 存储位置
     */
    @NotBlank(message = "存储位置不能为空")
    private String location;
    
    /**
     * 保管期限
     */
    @NotBlank(message = "保管期限不能为空")
    private String retentionPeriod;
    
    /**
     * 密级
     */
    @NotBlank(message = "密级不能为空")
    private String securityLevel;
    
    /**
     * 归档人
     */
    @NotBlank(message = "归档人不能为空")
    private String filingPerson;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联客户ID
     */
    private Long clientId;
    
    /**
     * 电子文档路径
     */
    private String documentPath;
    
    /**
     * 备注
     */
    private String remarks;
} 