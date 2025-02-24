package com.lawfirm.model.knowledge.dto.category;

import com.lawfirm.model.base.query.BaseQuery;
import com.lawfirm.model.knowledge.enums.CategoryTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 分类查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CategoryQueryDTO extends BaseQuery {

    /**
     * 父分类ID
     */
    private Long parentId;

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
     * 是否允许发布文章
     */
    private Boolean allowPublish;

    /**
     * 访问权限
     */
    private Integer accessLevel;

    /**
     * 是否在导航显示
     */
    private Boolean showInNav;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 关键词（名称、编码、描述）
     */
    private String keyword;

    /**
     * 排序字段（name, createTime, weight, articleCount）
     */
    private String sortField;

    /**
     * 排序方向（asc, desc）
     */
    private String sortDirection;

    /**
     * 是否包含子分类
     */
    private Boolean includeChildren = false;

    /**
     * 是否只查询有文章的分类
     */
    private Boolean onlyHasArticles = false;
} 