package com.lawfirm.model.document.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档类型枚举
 */
@Getter
@AllArgsConstructor
public enum DocumentTypeEnum {

    CASE("CASE", "案件文档"),
    CONTRACT("CONTRACT", "合同文档"),
    ARTICLE("ARTICLE", "知识文章"),
    TEMPLATE("TEMPLATE", "模板文档"),
    ATTACHMENT("ATTACHMENT", "附件"),
    OTHER("OTHER", "其他文档");

    private final String value;
    private final String label;
} 