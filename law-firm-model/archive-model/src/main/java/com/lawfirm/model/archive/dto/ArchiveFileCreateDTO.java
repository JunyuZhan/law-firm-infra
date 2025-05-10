package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 档案文件创建DTO
 */
@Data
@Schema(description = "档案文件创建DTO")
public class ArchiveFileCreateDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 档案标题
     */
    @NotBlank(message = "档案标题不能为空")
    @Schema(description = "档案标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    
    /**
     * a档案类型（1-案件档案，2-合同档案，3-文书档案等）
     */
    @NotNull(message = "档案类型不能为空")
    @Schema(description = "档案类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer archiveType;
    
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
     * 文件路径
     */
    @NotBlank(message = "文件路径不能为空")
    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filePath;
    
    /**
     * 文件大小（KB）
     */
    @Schema(description = "文件大小（KB）")
    private Long fileSize;
    
    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;
    
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