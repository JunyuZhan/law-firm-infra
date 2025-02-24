package com.lawfirm.model.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评论状态枚举
 */
@Getter
@AllArgsConstructor
public enum CommentStatusEnum {

    PENDING("PENDING", "待审核"),
    APPROVED("APPROVED", "已通过"),
    REJECTED("REJECTED", "已拒绝"),
    SPAM("SPAM", "垃圾评论");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
} 