package com.lawfirm.staff.model.request.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "知识库创建请求")
public class KnowledgeCreateRequest {

    @NotBlank(message = "标题不能为空")
    @Schema(description = "标题")
    private String title;

    @NotBlank(message = "内容不能为空")
    @Schema(description = "内容")
    private String content;

    @NotNull(message = "知识类型不能为空")
    @Schema(description = "知识类型")
    private Integer type;

    @Schema(description = "标签列表")
    private List<String> tags;

    @Schema(description = "附件列表")
    private List<String> attachments;

    @Schema(description = "是否公开")
    private Boolean isPublic;

    @Schema(description = "备注")
    private String remark;
} 