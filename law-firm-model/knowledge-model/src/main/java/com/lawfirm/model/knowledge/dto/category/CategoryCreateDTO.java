package com.lawfirm.model.knowledge.dto.category;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.knowledge.enums.CategoryTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 分类创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CategoryCreateDTO extends BaseDTO {

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称不能超过50个字符")
    private String name;

    /**
     * 分类编码
     */
    @NotBlank(message = "分类编码不能为空")
    @Size(max = 50, message = "分类编码不能超过50个字符")
    private String code;

    /**
     * 分类类型
     */
    @NotNull(message = "分类类型不能为空")
    private CategoryTypeEnum categoryType;

    /**
     * 分类描述
     */
    @Size(max = 500, message = "分类描述不能超过500个字符")
    private String description;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否允许发布文章
     */
    private Boolean allowPublish = true;

    /**
     * 访问权限（0：公开，1：登录可见，2：指定角色可见）
     */
    private Integer accessLevel = 0;

    /**
     * 可访问角色列表（逗号分隔）
     */
    private String accessRoles;

    /**
     * 排序权重
     */
    private Integer weight = 0;

    /**
     * 是否在导航显示
     */
    private Boolean showInNav = true;

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
} 