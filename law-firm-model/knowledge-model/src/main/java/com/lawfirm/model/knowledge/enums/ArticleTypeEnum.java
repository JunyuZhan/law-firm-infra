package com.lawfirm.model.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章类型枚举
 */
@Getter
@AllArgsConstructor
public enum ArticleTypeEnum {

    KNOWLEDGE("KNOWLEDGE", "知识文章"),
    CASE_STUDY("CASE_STUDY", "案例研究"),
    LEGAL_ANALYSIS("LEGAL_ANALYSIS", "法律分析"),
    INDUSTRY_NEWS("INDUSTRY_NEWS", "行业新闻"),
    INTERNAL_NOTICE("INTERNAL_NOTICE", "内部通知"),
    PRACTICE_GUIDE("PRACTICE_GUIDE", "实务指南"),
    OTHER("OTHER", "其他");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 