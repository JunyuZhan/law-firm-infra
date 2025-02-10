package com.lawfirm.staff.model.request.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 知识分享请求
 */
@Data
@Schema(description = "知识分享请求")
public class KnowledgeShareRequest {

    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @Schema(description = "分类ID")
    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @Schema(description = "标签列表")
    @Size(max = 5, message = "标签数量不能超过5个")
    private List<String> tags;

    @Schema(description = "附件列表")
    private List<String> attachments;

    @Schema(description = "是否公开")
    private Boolean isPublic = true;
} 