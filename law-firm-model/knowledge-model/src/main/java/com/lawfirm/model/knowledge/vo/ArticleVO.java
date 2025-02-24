package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.knowledge.enums.ArticleTypeEnum;
import com.lawfirm.model.knowledge.enums.ContentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ArticleVO extends BaseVO {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 内容类型
     */
    private ContentTypeEnum contentType;

    /**
     * 文章类型
     */
    private ArticleTypeEnum articleType;

    /**
     * 所属分类ID
     */
    private Long categoryId;

    /**
     * 所属分类名称
     */
    private String categoryName;

    /**
     * 所属分类完整路径
     */
    private String categoryPath;

    /**
     * 标签列表
     */
    private List<TagVO> tags = new ArrayList<>();

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 浏览次数
     */
    private Long viewCount;

    /**
     * 评论次数
     */
    private Long commentCount;

    /**
     * 点赞次数
     */
    private Long likeCount;

    /**
     * 收藏次数
     */
    private Long favoriteCount;

    /**
     * 分享次数
     */
    private Long shareCount;

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
     * 原文链接
     */
    private String sourceUrl;

    /**
     * 访问权限（0：公开，1：登录可见，2：指定角色可见）
     */
    private Integer accessLevel;

    /**
     * 可访问角色列表（逗号分隔）
     */
    private String accessRoles;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;

    /**
     * 发布时间
     */
    private Long publishTime;

    /**
     * SEO标题
     */
    private String seoTitle;

    /**
     * SEO关键词
     */
    private String seoKeywords;

    /**
     * SEO描述
     */
    private String seoDescription;

    /**
     * 附件列表
     */
    private List<AttachmentVO> attachments = new ArrayList<>();

    /**
     * 评论列表（最新的N条）
     */
    private List<CommentVO> latestComments = new ArrayList<>();

    /**
     * 相关文章（相同分类或标签的文章）
     */
    private List<ArticleBriefVO> relatedArticles = new ArrayList<>();

    /**
     * 文章简要信息VO
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    public static class ArticleBriefVO extends BaseVO {
        private String title;
        private String summary;
        private String coverImage;
        private Long viewCount;
        private Long commentCount;
        private Long publishTime;
    }
} 