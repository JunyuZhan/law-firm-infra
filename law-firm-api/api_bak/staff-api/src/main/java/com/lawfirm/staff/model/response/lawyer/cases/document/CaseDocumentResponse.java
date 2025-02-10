package com.lawfirm.staff.model.response.lawyer.cases.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "案件文档响应")
public class CaseDocumentResponse {
    
    @Schema(description = "文档ID")
    private Long id;
    
    @Schema(description = "案件ID")
    private Long caseId;
    
    @Schema(description = "文档类型")
    private Integer type;
    
    @Schema(description = "文档类型名称")
    private String typeName;
    
    @Schema(description = "文档名称")
    private String name;
    
    @Schema(description = "文档路径")
    private String path;
    
    @Schema(description = "是否保密")
    private Boolean isConfidential;
    
    @Schema(description = "上传人ID")
    private Long uploaderId;
    
    @Schema(description = "上传人")
    private String uploader;
    
    @Schema(description = "上传时间")
    private LocalDateTime uploadTime;
    
    @Schema(description = "版本号")
    private String version;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @Schema(description = "备注")
    private String remark;
} 