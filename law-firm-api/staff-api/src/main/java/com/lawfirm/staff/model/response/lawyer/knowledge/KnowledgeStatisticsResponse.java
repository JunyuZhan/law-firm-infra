package com.lawfirm.staff.model.response.lawyer.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "知识统计响应")
public class KnowledgeStatisticsResponse {

    @Schema(description = "知识总数")
    private Long totalCount;

    @Schema(description = "我的知识数")
    private Long myCount;

    @Schema(description = "本月新增数")
    private Long monthlyNewCount;

    @Schema(description = "收藏数")
    private Long favoriteCount;

    @Schema(description = "点赞数")
    private Long likeCount;

    @Schema(description = "评论数")
    private Long commentCount;

    @Schema(description = "浏览数")
    private Long viewCount;

    @Schema(description = "下载数")
    private Long downloadCount;

    @Schema(description = "分享数")
    private Long shareCount;

    @Schema(description = "分类分布")
    private List<CategoryDistribution> categoryDistributions;

    @Schema(description = "标签分布")
    private List<TagDistribution> tagDistributions;

    @Data
    @Schema(description = "分类分布")
    public static class CategoryDistribution {
        
        @Schema(description = "分类ID")
        private Long categoryId;

        @Schema(description = "分类名称")
        private String categoryName;

        @Schema(description = "数量")
        private Long count;

        @Schema(description = "占比")
        private BigDecimal percentage;
    }

    @Data
    @Schema(description = "标签分布")
    public static class TagDistribution {
        
        @Schema(description = "标签名称")
        private String tagName;

        @Schema(description = "数量")
        private Long count;

        @Schema(description = "占比")
        private BigDecimal percentage;
    }
} 