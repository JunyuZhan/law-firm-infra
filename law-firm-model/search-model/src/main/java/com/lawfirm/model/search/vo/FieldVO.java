package com.lawfirm.model.search.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.search.enums.FieldTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字段视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FieldVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private FieldTypeEnum fieldType;

    /**
     * 是否分词
     */
    private Boolean analyzed;

    /**
     * 是否存储
     */
    private Boolean stored;

    /**
     * 是否索引
     */
    private Boolean indexed;

    /**
     * 分词器
     */
    private String analyzer;

    /**
     * 搜索分词器
     */
    private String searchAnalyzer;

    /**
     * 权重
     */
    private Float boost;

    /**
     * 复制到字段
     */
    private String copyTo;

    /**
     * 忽略大小写
     */
    private Boolean ignoreCase;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 字段描述
     */
    private String description;
} 