package com.lawfirm.staff.model.response.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "知识分类响应")
public class KnowledgeCategoryResponse {

    @Schema(description = "分类ID")
    private Long id;

    @Schema(description = "父分类ID")
    private Long parentId;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "分类编码")
    private String code;

    @Schema(description = "分类描述")
    private String description;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(0:禁用 1:启用)")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "子分类")
    private List<KnowledgeCategoryResponse> children;

    @Schema(description = "知识数量")
    private Long knowledgeCount;
} 