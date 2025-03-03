package com.lawfirm.model.search.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.search.enums.FieldTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 搜索字段实体
 */
@Data
@TableName("search_field")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SearchField extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段名称
     */
    @TableField("field_name")
    private String fieldName;

    /**
     * 字段类型
     */
    @TableField("field_type")
    private FieldTypeEnum fieldType;

    /**
     * 是否分析
     */
    @TableField("analyzed")
    private Boolean analyzed = false;

    /**
     * 是否存储
     */
    @TableField("stored")
    private Boolean stored = true;

    /**
     * 是否索引
     */
    @TableField("indexed")
    private Boolean indexed = true;

    /**
     * 分析器
     */
    @TableField("analyzer")
    private String analyzer;

    /**
     * 搜索分析器
     */
    @TableField("search_analyzer")
    private String searchAnalyzer;

    /**
     * 权重
     */
    @TableField("boost")
    private Float boost = 1.0f;

    /**
     * 复制到字段
     */
    @TableField("copy_to")
    private String copyTo;

    /**
     * 是否忽略大小写
     */
    @TableField("ignore_case")
    private Boolean ignoreCase = false;

    /**
     * 是否必填
     */
    @TableField("required")
    private Boolean required = false;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 字段描述
     */
    @TableField("description")
    private String description;
} 