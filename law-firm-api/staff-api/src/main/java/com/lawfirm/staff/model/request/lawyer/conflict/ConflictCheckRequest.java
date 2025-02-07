package com.lawfirm.staff.model.request.lawyer.conflict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "冲突检查请求")
public class ConflictCheckRequest {

    @Schema(description = "检查类型")
    @NotNull(message = "检查类型不能为空")
    private Integer type;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "检查内容")
    private String content;

    @Schema(description = "备注")
    private String remark;
} 