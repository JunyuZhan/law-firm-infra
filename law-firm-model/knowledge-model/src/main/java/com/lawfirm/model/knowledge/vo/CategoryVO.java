package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.knowledge.enums.CategoryTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CategoryVO extends BaseVO {

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 父分类名称
     */
    private String parentName;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String code;

    /**
     * 分类类型
     */
    private CategoryTypeEnum categoryType;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 图标
     */
    private String icon;

    /**
     * 文章数量
     */
    private Long articleCount;

    /**
     * 是否允许发布文章
     */
    private Boolean allowPublish;

    /**
     * 访问权限（0：公开，1：登录可见，2：指定角色可见）
     */
    private Integer accessLevel;

    /**
     * 可访问角色列表
     */
    private String accessRoles;

    /**
     * 排序权重
     */
    private Integer weight;

    /**
     * 是否在导航显示
     */
    private Boolean showInNav;

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
     * 完整路径（例如：根分类/子分类/当前分类）
     */
    private String fullPath;

    /**
     * 层级（从0开始）
     */
    private Integer level;

    /**
     * 子分类列表
     */
    private List<CategoryVO> children = new ArrayList<>();

    /**
     * 最新文章列表
     */
    private List<ArticleVO.ArticleBriefVO> latestArticles = new ArrayList<>();
} 