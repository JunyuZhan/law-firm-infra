package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 档案文件更新DTO
 */
@Data
@Schema(description = "档案文件更新DTO")
public class ArchiveFileUpdateDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 文件ID
     */
    @NotBlank(message = "文件ID不能为空")
    @Schema(description = "文件ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    
    /**
     * 档案标题
     */
    @Schema(description = "档案标题")
    private String title;
    
    /**
     * 档案类型
     */
    @Schema(description = "档案类型")
    private Integer archiveType;
    
    /**
     * 档案状态
     */
    @Schema(description = "档案状态")
    private Integer archiveStatus;
    
    /**
     * 业务ID（案件ID/合同ID等）
     */
    @Schema(description = "业务ID")
    private String businessId;
    
    /**
     * 业务类型（案件/合同等）
     */
    @Schema(description = "业务类型")
    private String businessType;
    
    /**
     * 存储位置编码
     */
    @Schema(description = "存储位置编码")
    private String storageLocation;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    
    /**
     * 文件顺序号
     */
    @Schema(description = "文件顺序号")
    private Integer sort;
} 