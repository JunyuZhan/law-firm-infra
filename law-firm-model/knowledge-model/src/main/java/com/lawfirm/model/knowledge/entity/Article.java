package com.lawfirm.model.knowledge.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.knowledge.enums.ArticleTypeEnum;
import com.lawfirm.model.knowledge.enums.ContentTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章实体
 */
@Data
@Entity
@Table(name = "knowledge_article")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Article extends ModelBaseEntity {

    /**
     * 文章标题
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 文章摘要
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 文章内容
     */
    @Column(name = "content", columnDefinition = "text", nullable = false)
    private String content;

    /**
     * 内容类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentTypeEnum contentType;

    /**
     * 文章类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "article_type", nullable = false)
    private ArticleTypeEnum articleType;

    /**
     * 所属分类ID
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * 所属分类（延迟加载）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    /**
     * 标签列表
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "knowledge_article_tag",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    /**
     * 封面图片
     */
    @Column(name = "cover_image")
    private String coverImage;

    /**
     * 作者ID
     */
    @Column(name = "author_id")
    private Long authorId;

    /**
     * 作者名称
     */
    @Column(name = "author_name")
    private String authorName;

    /**
     * 浏览次数
     */
    @Column(name = "view_count")
    private Long viewCount = 0L;

    /**
     * 评论次数
     */
    @Column(name = "comment_count")
    private Long commentCount = 0L;

    /**
     * 点赞次数
     */
    @Column(name = "like_count")
    private Long likeCount = 0L;

    /**
     * 收藏次数
     */
    @Column(name = "favorite_count")
    private Long favoriteCount = 0L;

    /**
     * 分享次数
     */
    @Column(name = "share_count")
    private Long shareCount = 0L;

    /**
     * 是否置顶
     */
    @Column(name = "top", nullable = false)
    private Boolean top = false;

    /**
     * 是否推荐
     */
    @Column(name = "recommended", nullable = false)
    private Boolean recommended = false;

    /**
     * 是否原创
     */
    @Column(name = "original", nullable = false)
    private Boolean original = true;

    /**
     * 原文链接
     */
    @Column(name = "source_url")
    private String sourceUrl;

    /**
     * 访问权限（0：公开，1：登录可见，2：指定角色可见）
     */
    @Column(name = "access_level", nullable = false)
    private Integer accessLevel = 0;

    /**
     * 可访问角色列表（逗号分隔）
     */
    @Column(name = "access_roles")
    private String accessRoles;

    /**
     * 是否允许评论
     */
    @Column(name = "allow_comment", nullable = false)
    private Boolean allowComment = true;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Long publishTime;

    /**
     * SEO标题
     */
    @Column(name = "seo_title")
    private String seoTitle;

    /**
     * SEO关键词
     */
    @Column(name = "seo_keywords")
    private String seoKeywords;

    /**
     * SEO描述
     */
    @Column(name = "seo_description")
    private String seoDescription;
} 