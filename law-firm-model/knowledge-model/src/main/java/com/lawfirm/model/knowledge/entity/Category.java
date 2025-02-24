package com.lawfirm.model.knowledge.entity;

import com.lawfirm.model.base.entity.TreeEntity;
import com.lawfirm.model.knowledge.enums.CategoryTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分类实体
 */
@Data
@Entity
@Table(name = "knowledge_category")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Category extends TreeEntity {

    /**
     * 分类名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 分类编码（用于URL）
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 分类类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryTypeEnum categoryType;

    /**
     * 分类描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 文章数量
     */
    @Column(name = "article_count")
    private Long articleCount = 0L;

    /**
     * 是否允许发布文章
     */
    @Column(name = "allow_publish")
    private Boolean allowPublish = true;

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
     * 排序权重
     */
    @Column(name = "weight")
    private Integer weight = 0;

    /**
     * 是否在导航显示
     */
    @Column(name = "show_in_nav")
    private Boolean showInNav = true;

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