package com.lawfirm.model.storage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存储桶类型枚举
 */
@Getter
@AllArgsConstructor
public enum BucketTypeEnum {

    PUBLIC("PUBLIC", "公开"),
    PRIVATE("PRIVATE", "私有"),
    READONLY("READONLY", "只读"),
    WRITEONLY("WRITEONLY", "只写");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 