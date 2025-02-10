package com.lawfirm.staff.model.request.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "知识评论请求")
public class KnowledgeCommentRequest {

    @NotNull(message = "知识ID不能为空")
    @Schema(description = "知识ID")
    private Long knowledgeId;

    @NotBlank(message = "评论内容不能为空")
    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "父评论ID")
    private Long parentId;

    @Schema(description = "回复用户ID")
    private Long replyUserId;
} 