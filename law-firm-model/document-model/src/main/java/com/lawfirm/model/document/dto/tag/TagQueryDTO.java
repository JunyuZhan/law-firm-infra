package com.lawfirm.model.document.dto.tag;

import com.lawfirm.model.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档标签查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TagQueryDTO extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签编码
     */
    private String tagCode;

    /**
     * 标签类型
     */
    private String tagType;

    /**
     * 是否系统标签
     */
    private Boolean isSystem;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 关键词（标签名称、编码、描述）
     */
    private String keyword;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方向（asc, desc）
     */
    private String sortDirection;
} 