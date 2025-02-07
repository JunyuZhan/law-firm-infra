package com.lawfirm.staff.model.response.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "知识评论响应")
public class KnowledgeCommentResponse {

    @Schema(description = "评论ID")
    private Long id;

    @Schema(description = "知识ID")
    private Long knowledgeId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "父评论ID")
    private Long parentId;

    @Schema(description = "评论人ID")
    private Long userId;

    @Schema(description = "评论人姓名")
    private String userName;

    @Schema(description = "评论人头像")
    private String userAvatar;

    @Schema(description = "回复用户ID")
    private Long replyUserId;

    @Schema(description = "回复用户姓名")
    private String replyUserName;

    @Schema(description = "点赞数")
    private Long likeCount;

    @Schema(description = "是否已点赞")
    private Boolean liked;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子评论列表")
    private List<KnowledgeCommentResponse> children;
} 