package com.lawfirm.model.client.entity.common;

import com.lawfirm.model.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户分类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ClientCategory extends BaseModel {

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类层级
     */
    private Integer level;

    /**
     * 上级分类ID
     */
    private Long parentId;

    /**
     * 分类路径
     */
    private String categoryPath;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序权重
     */
    private Integer sortWeight;

    /**
     * 分类状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 是否允许客户选择 0-不允许 1-允许
     */
    private Integer allowSelect;

    /**
     * 是否系统预置 0-否 1-是
     */
    private Integer isSystem;
} 