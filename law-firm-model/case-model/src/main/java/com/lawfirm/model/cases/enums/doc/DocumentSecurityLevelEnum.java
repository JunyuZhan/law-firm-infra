package com.lawfirm.model.cases.enums.doc;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 文档安全级别枚举
 */
@Getter
public enum DocumentSecurityLevelEnum implements BaseEnum<Integer> {

    /**
     * 公开
     * 所有人可访问
     */
    PUBLIC(1, "公开"),

    /**
     * 内部
     * 仅本所律师可访问
     */
    INTERNAL(2, "内部"),

    /**
     * 保密
     * 仅案件团队成员可访问
     */
    CONFIDENTIAL(3, "保密"),

    /**
     * 机密
     * 仅主办律师和合伙人可访问
     */
    SECRET(4, "机密"),

    /**
     * 绝密
     * 仅指定人员可访问
     */
    TOP_SECRET(5, "绝密");

    private final Integer value;
    private final String description;

    DocumentSecurityLevelEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 根据值获取枚举
     */
    public static DocumentSecurityLevelEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (DocumentSecurityLevelEnum level : values()) {
            if (level.value.equals(value)) {
                return level;
            }
        }
        return null;
    }

    /**
     * 是否需要访问记录
     */
    public boolean needAccessLog() {
        return this != PUBLIC;
    }

    /**
     * 是否允许下载
     */
    public boolean allowDownload() {
        return this == PUBLIC || this == INTERNAL;
    }

    /**
     * 是否允许打印
     */
    public boolean allowPrint() {
        return this == PUBLIC || this == INTERNAL || this == CONFIDENTIAL;
    }

    /**
     * 是否需要水印
     */
    public boolean needWatermark() {
        return this != PUBLIC;
    }

    /**
     * 是否需要加密存储
     */
    public boolean needEncryption() {
        return this == SECRET || this == TOP_SECRET;
    }

    /**
     * 是否需要审批才能访问
     */
    public boolean needApprovalToAccess() {
        return this == SECRET || this == TOP_SECRET;
    }

    /**
     * 获取默认有效期（天）
     * 0表示永久有效
     */
    public int getDefaultValidityDays() {
        switch (this) {
            case TOP_SECRET:
                return 30;
            case SECRET:
                return 90;
            case CONFIDENTIAL:
                return 180;
            default:
                return 0;
        }
    }

    /**
     * 是否需要定期审查
     */
    public boolean needPeriodicReview() {
        return this == SECRET || this == TOP_SECRET;
    }

    /**
     * 获取审查周期（天）
     */
    public int getReviewCycleDays() {
        switch (this) {
            case TOP_SECRET:
                return 30;
            case SECRET:
                return 90;
            case CONFIDENTIAL:
                return 180;
            default:
                return 0;
        }
    }
} 