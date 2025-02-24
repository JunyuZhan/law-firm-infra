package com.lawfirm.model.document.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 知识文章实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_article")
public class ArticleDocument extends BaseDocument {

    /**
     * 文章分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 文章类型
     */
    @TableField("article_type")
    private String articleType;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 来源
     */
    @TableField("source")
    private String source;

    /**
     * 摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 封面图片
     */
    @TableField("cover_image")
    private String coverImage;

    /**
     * 阅读量
     */
    @TableField("read_count")
    private Long readCount;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Long likeCount;

    /**
     * 收藏数
     */
    @TableField("favorite_count")
    private Long favoriteCount;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Long commentCount;

    /**
     * 是否置顶
     */
    @TableField("is_top")
    private Boolean isTop;

    /**
     * 是否推荐
     */
    @TableField("is_recommend")
    private Boolean isRecommend;

    /**
     * 是否原创
     */
    @TableField("is_original")
    private Boolean isOriginal;

    /**
     * 审核状态
     */
    @TableField("review_status")
    private String reviewStatus;

    /**
     * 审核意见
     */
    @TableField("review_comment")
    private String reviewComment;
} 