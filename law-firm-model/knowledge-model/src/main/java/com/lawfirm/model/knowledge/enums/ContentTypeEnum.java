package com.lawfirm.model.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 内容类型枚举
 */
@Getter
@AllArgsConstructor
public enum ContentTypeEnum {

    MARKDOWN("MARKDOWN", "Markdown格式"),
    HTML("HTML", "HTML格式"),
    PLAIN_TEXT("PLAIN_TEXT", "纯文本"),
    RICH_TEXT("RICH_TEXT", "富文本");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 