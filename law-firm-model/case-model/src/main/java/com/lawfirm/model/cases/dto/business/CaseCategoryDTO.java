package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 案件分类数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseCategoryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String categoryDescription;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类层级
     */
    private Integer level;

    /**
     * 分类路径
     */
    private String path;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

    /**
     * 图标
     */
    private String icon;

    /**
     * 颜色
     */
    private String color;

    /**
     * 是否允许删除
     */
    private Boolean allowDelete;

    /**
     * 是否系统内置
     */
    private Boolean isSystem;

    /**
     * 备注
     */
    private String remark;
} 