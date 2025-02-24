package com.lawfirm.model.search.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 搜索类型枚举
 */
@Getter
@AllArgsConstructor
public enum SearchTypeEnum {

    MATCH("MATCH", "匹配查询"),
    TERM("TERM", "精确查询"),
    RANGE("RANGE", "范围查询"),
    PREFIX("PREFIX", "前缀查询"),
    WILDCARD("WILDCARD", "通配符查询"),
    FUZZY("FUZZY", "模糊查询"),
    BOOL("BOOL", "布尔查询");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 