package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 案件知识库展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseKnowledgeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 知识标题
     */
    private String knowledgeTitle;

    /**
     * 知识类型
     */
    private Integer knowledgeType;

    /**
     * 知识分类
     */
    private String category;

    /**
     * 知识内容
     */
    private String content;

    /**
     * 关键词（逗号分隔）
     */
    private String keywords;

    /**
     * 标签（逗号分隔）
     */
    private String tags;

    /**
     * 适用领域（逗号分隔）
     */
    private String applicableFields;

    /**
     * 适用地区（逗号分隔）
     */
    private String applicableRegions;

    /**
     * 法律依据
     */
    private String legalBasis;

    /**
     * 相关案例（逗号分隔）
     */
    private String relatedCases;

    /**
     * 相关文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 参考文献
     */
    private String references;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 审核状态
     */
    private Integer reviewStatus;

    /**
     * 审核意见
     */
    private String reviewOpinion;

    /**
     * 审核时间
     */
    private transient LocalDateTime reviewTime;

    /**
     * 发布状态
     */
    private Integer publishStatus;

    /**
     * 发布时间
     */
    private transient LocalDateTime publishTime;

    /**
     * 阅读次数
     */
    private Integer readCount;

    /**
     * 点赞次数
     */
    private Integer likeCount;

    /**
     * 收藏次数
     */
    private Integer favoriteCount;

    /**
     * 评分（1-5）
     */
    private Integer rating;

    /**
     * 评分人数
     */
    private Integer ratingCount;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 最后更新人ID
     */
    private Long lastUpdaterId;

    /**
     * 最后更新人姓名
     */
    private String lastUpdaterName;

    /**
     * 最后更新时间
     */
    private transient LocalDateTime lastUpdateTime;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 判断是否已发布
     */
    public boolean isPublished() {
        return publishStatus != null && publishStatus == 2;
    }

    /**
     * 判断是否已审核通过
     */
    public boolean isReviewed() {
        return reviewStatus != null && reviewStatus == 2;
    }

    /**
     * 判断是否被驳回
     */
    public boolean isRejected() {
        return reviewStatus != null && reviewStatus == 3;
    }

    /**
     * 获取发布距今天数
     */
    public long getDaysSincePublish() {
        if (publishTime == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(publishTime, LocalDateTime.now());
    }

    /**
     * 获取最后更新距今天数
     */
    public long getDaysSinceLastUpdate() {
        if (lastUpdateTime == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(lastUpdateTime, LocalDateTime.now());
    }

    /**
     * 获取平均评分
     */
    public double getAverageRating() {
        if (rating == null || ratingCount == null || ratingCount == 0) {
            return 0.0;
        }
        return rating.doubleValue() / ratingCount;
    }

    /**
     * 获取关键词列表
     */
    public List<String> getKeywordList() {
        if (keywords == null || keywords.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(keywords.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 获取标签列表
     */
    public List<String> getTagList() {
        if (tags == null || tags.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 获取适用领域列表
     */
    public List<String> getApplicableFieldList() {
        if (applicableFields == null || applicableFields.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(applicableFields.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 获取适用地区列表
     */
    public List<String> getApplicableRegionList() {
        if (applicableRegions == null || applicableRegions.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(applicableRegions.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 获取相关案例列表
     */
    public List<String> getRelatedCaseList() {
        if (relatedCases == null || relatedCases.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(relatedCases.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 获取相关文档ID列表
     */
    public List<Long> getDocumentIdList() {
        if (documentIds == null || documentIds.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(documentIds.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}