package com.lawfirm.staff.model.request.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "知识库更新请求")
public class KnowledgeUpdateRequest {

    @NotNull(message = "知识ID不能为空")
    @Schema(description = "知识ID")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

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