package com.lawfirm.model.search.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 索引类型枚举
 */
@Getter
@AllArgsConstructor
public enum IndexTypeEnum {

    DOCUMENT("DOCUMENT", "文档"),
    USER("USER", "用户"),
    CLIENT("CLIENT", "客户"),
    CASE("CASE", "案件"),
    CONTRACT("CONTRACT", "合同"),
    KNOWLEDGE("KNOWLEDGE", "知识库");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 