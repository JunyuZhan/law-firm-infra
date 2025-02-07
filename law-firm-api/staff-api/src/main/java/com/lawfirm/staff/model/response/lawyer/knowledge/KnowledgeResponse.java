package com.lawfirm.staff.model.response.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "知识库响应")
public class KnowledgeResponse {

    @Schema(description = "知识ID")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "知识类型")
    private Integer type;

    @Schema(description = "知识类型名称")
    private String typeName;

    @Schema(description = "标签列表")
    private List<String> tags;

    @Schema(description = "附件列表")
    private List<String> attachments;

    @Schema(description = "是否公开")
    private Boolean isPublic;

    @Schema(description = "浏览次数")
    private Integer viewCount;

    @Schema(description = "点赞次数")
    private Integer likeCount;

    @Schema(description = "收藏次数")
    private Integer favoriteCount;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "备注")
    private String remark;
} 