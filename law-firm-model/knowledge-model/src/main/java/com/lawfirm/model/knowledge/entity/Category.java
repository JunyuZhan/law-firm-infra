package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TreeEntity;
import com.lawfirm.model.knowledge.enums.CategoryTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分类实体
 */
@Data
@TableName("knowledge_category")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Category extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 分类编码（用于URL）
     */
    @TableField("code")
    private String code;

    /**
     * 分类类型
     */
    @TableField("category_type")
    private CategoryTypeEnum categoryType;

    /**
     * 分类描述
     */
    @TableField("description")
    private String description;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 文章数量
     */
    @TableField("article_count")
    private Long articleCount = 0L;

    /**
     * 是否允许发布
     */
    @TableField("allow_publish")
    private Boolean allowPublish = true;

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
     * 权重（用于排序）
     */
    @TableField("weight")
    private Integer weight = 0;

    /**
     * 是否在导航中显示
     */
    @TableField("show_in_nav")
    private Boolean showInNav = true;

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