package com.lawfirm.model.search.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.search.enums.FieldTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 搜索字段实体
 */
@Data
@Entity
@Table(name = "search_field")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SearchField extends ModelBaseEntity {

    /**
     * 字段名称
     */
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    /**
     * 字段类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false)
    private FieldTypeEnum fieldType;

    /**
     * 是否分词
     */
    @Column(name = "analyzed", nullable = false)
    private Boolean analyzed = false;

    /**
     * 是否存储
     */
    @Column(name = "stored", nullable = false)
    private Boolean stored = true;

    /**
     * 是否索引
     */
    @Column(name = "indexed", nullable = false)
    private Boolean indexed = true;

    /**
     * 分词器
     */
    @Column(name = "analyzer")
    private String analyzer;

    /**
     * 搜索分词器
     */
    @Column(name = "search_analyzer")
    private String searchAnalyzer;

    /**
     * 权重
     */
    @Column(name = "boost")
    private Float boost = 1.0f;

    /**
     * 复制到字段
     */
    @Column(name = "copy_to")
    private String copyTo;

    /**
     * 忽略大小写
     */
    @Column(name = "ignore_case", nullable = false)
    private Boolean ignoreCase = false;

    /**
     * 是否必填
     */
    @Column(name = "required", nullable = false)
    private Boolean required = false;

    /**
     * 默认值
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     * 字段描述
     */
    @Column(name = "description")
    private String description;
} 