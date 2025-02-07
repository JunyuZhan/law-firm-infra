package com.lawfirm.staff.model.request.lawyer.cases.relation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "案件关联创建请求")
public class CaseRelationCreateRequest {
    
    @NotNull(message = "主案件ID不能为空")
    @Schema(description = "主案件ID")
    private Long mainCaseId;
    
    @NotNull(message = "关联案件ID不能为空")
    @Schema(description = "关联案件ID")
    private Long relatedCaseId;
    
    @NotNull(message = "关联类型不能为空")
    @Schema(description = "关联类型")
    private Integer type;
    
    @Schema(description = "关联说明")
    private String description;
    
    @Schema(description = "备注")
    private String remark;
} 