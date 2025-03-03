package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件知识库数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseKnowledgeDTO extends BaseDTO {

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
} 