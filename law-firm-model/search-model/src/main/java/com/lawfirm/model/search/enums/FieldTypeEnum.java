package com.lawfirm.model.search.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字段类型枚举
 */
@Getter
@AllArgsConstructor
public enum FieldTypeEnum {

    TEXT("TEXT", "文本"),
    KEYWORD("KEYWORD", "关键字"),
    LONG("LONG", "长整型"),
    INTEGER("INTEGER", "整型"),
    SHORT("SHORT", "短整型"),
    BYTE("BYTE", "字节"),
    DOUBLE("DOUBLE", "双精度"),
    FLOAT("FLOAT", "单精度"),
    DATE("DATE", "日期"),
    BOOLEAN("BOOLEAN", "布尔"),
    BINARY("BINARY", "二进制"),
    OBJECT("OBJECT", "对象"),
    NESTED("NESTED", "嵌套");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 