package com.lawfirm.staff.model.response.lawyer.cases.relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "案件关联响应")
public class CaseRelationResponse {
    
    @Schema(description = "关联ID")
    private Long id;
    
    @Schema(description = "主案件ID")
    private Long mainCaseId;
    
    @Schema(description = "主案件名称")
    private String mainCaseName;
    
    @Schema(description = "关联案件ID")
    private Long relatedCaseId;
    
    @Schema(description = "关联案件名称")
    private String relatedCaseName;
    
    @Schema(description = "关联类型")
    private Integer type;
    
    @Schema(description = "关联类型名称")
    private String typeName;
    
    @Schema(description = "关联说明")
    private String description;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "创建人ID")
    private Long creatorId;
    
    @Schema(description = "创建人")
    private String creator;
    
    @Schema(description = "备注")
    private String remark;
} 