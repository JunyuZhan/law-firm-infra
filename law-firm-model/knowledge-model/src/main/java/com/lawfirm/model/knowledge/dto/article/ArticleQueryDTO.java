package com.lawfirm.model.knowledge.dto.article;

import com.lawfirm.model.base.query.BaseQuery;
import com.lawfirm.model.knowledge.enums.ArticleTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文章查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ArticleQueryDTO extends BaseQuery {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章类型
     */
    private ArticleTypeEnum articleType;

    /**
     * 所属分类ID
     */
    private Long categoryId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 是否推荐
     */
    private Boolean recommended;

    /**
     * 是否原创
     */
    private Boolean original;

    /**
     * 访问权限
     */
    private Integer accessLevel;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 关键词（标题、摘要、内容）
     */
    private String keyword;

    /**
     * 排序字段（title, createTime, viewCount, commentCount, likeCount）
     */
    private String sortField;

    /**
     * 排序方向（asc, desc）
     */
    private String sortDirection;
} 