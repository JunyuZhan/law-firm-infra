package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.knowledge.enums.ArticleTypeEnum;
import com.lawfirm.model.knowledge.enums.ContentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章实体
 */
@Data
@TableName("knowledge_article")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Article extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文章标题
     */
    @TableField("title")
    private String title;

    /**
     * 文章摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 文章内容
     */
    @TableField("content")
    private String content;

    /**
     * 内容类型
     */
    @TableField("content_type")
    private ContentTypeEnum contentType;

    /**
     * 文章类型
     */
    @TableField("article_type")
    private ArticleTypeEnum articleType;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 所属分类（延迟加载）
     */
    @TableField(exist = false)
    private Category category;

    /**
     * 标签列表
     */
    @TableField(exist = false)
    private transient List<Tag> tags = new ArrayList<>();

    /**
     * 封面图片
     */
    @TableField("cover_image")
    private String coverImage;

    /**
     * 作者ID
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 作者名称
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 浏览量
     */
    @TableField("view_count")
    private Long viewCount = 0L;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Long commentCount = 0L;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Long likeCount = 0L;

    /**
     * 收藏数
     */
    @TableField("favorite_count")
    private Long favoriteCount = 0L;

    /**
     * 分享数
     */
    @TableField("share_count")
    private Long shareCount = 0L;

    /**
     * 是否置顶
     */
    @TableField("top")
    private Boolean top = false;

    /**
     * 是否推荐
     */
    @TableField("recommended")
    private Boolean recommended = false;

    /**
     * 是否原创
     */
    @TableField("original")
    private Boolean original = true;

    /**
     * 原文链接（非原创时）
     */
    @TableField("source_url")
    private String sourceUrl;

    /**
     * 访问权限（0-公开，1-登录，2-角色）
     */
    @TableField("access_level")
    private Integer accessLevel = 0;

    /**
     * 访问角色（逗号分隔的角色ID或代码）
     */
    @TableField("access_roles")
    private String accessRoles;

    /**
     * 是否允许评论
     */
    @TableField("allow_comment")
    private Boolean allowComment = true;

    /**
     * 发布时间（时间戳）
     */
    @TableField("publish_time")
    private Long publishTime;

    /**
     * SEO标题
     */
    @TableField("seo_title")
    private String seoTitle;

    /**
     * SEO关键词
     */
    @TableField("seo_keywords")
    private String seoKeywords;

    /**
     * SEO描述
     */
    @TableField("seo_description")
    private String seoDescription;
} 