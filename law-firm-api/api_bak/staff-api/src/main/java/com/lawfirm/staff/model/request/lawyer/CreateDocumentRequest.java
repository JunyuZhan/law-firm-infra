package com.lawfirm.staff.model.request.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "创建文档请求")
public class CreateDocumentRequest {

    @Schema(description = "文档标题")
    @NotBlank(message = "文档标题不能为空")
    private String title;

    @Schema(description = "文档类型")
    @NotNull(message = "文档类型不能为空")
    private Integer type;

    @Schema(description = "文档内容")
    private String content;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "文档描述")
    private String description;

    @Schema(description = "标签")
    private String[] tags;

    @Schema(description = "备注")
    private String remark;
} 