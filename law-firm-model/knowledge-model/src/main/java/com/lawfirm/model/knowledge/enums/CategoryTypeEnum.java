package com.lawfirm.model.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分类类型枚举
 */
@Getter
@AllArgsConstructor
public enum CategoryTypeEnum {

    SYSTEM("SYSTEM", "系统分类"),
    CUSTOM("CUSTOM", "自定义分类"),
    DEPARTMENT("DEPARTMENT", "部门分类"),
    PROJECT("PROJECT", "项目分类"),
    PRACTICE_AREA("PRACTICE_AREA", "业务领域"),
    CASE_TYPE("CASE_TYPE", "案件类型"),
    DOCUMENT_TYPE("DOCUMENT_TYPE", "文档类型");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 