package com.lawfirm.model.cases.enums.doc;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 文档状态枚举
 */
@Getter
public enum DocumentStatusEnum implements BaseEnum<Integer> {

    /**
     * 草稿
     */
    DRAFT(1, "草稿"),

    /**
     * 待审核
     */
    PENDING_REVIEW(2, "待审核"),

    /**
     * 审核中
     */
    IN_REVIEW(3, "审核中"),

    /**
     * 需修改
     */
    NEED_REVISION(4, "需修改"),

    /**
     * 已审核
     */
    REVIEWED(5, "已审核"),

    /**
     * 待签署
     */
    PENDING_SIGNATURE(6, "待签署"),

    /**
     * 已签署
     */
    SIGNED(7, "已签署"),

    /**
     * 待归档
     */
    PENDING_ARCHIVE(8, "待归档"),

    /**
     * 已归档
     */
    ARCHIVED(9, "已归档"),

    /**
     * 已作废
     */
    VOID(10, "已作废");

    private final Integer value;
    private final String description;

    DocumentStatusEnum(Integer value, String description) {
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
    public static DocumentStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (DocumentStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 是否可编辑
     */
    public boolean isEditable() {
        return this == DRAFT || this == NEED_REVISION;
    }

    /**
     * 是否是终态
     */
    public boolean isTerminal() {
        return this == ARCHIVED || this == VOID;
    }

    /**
     * 是否需要审核
     */
    public boolean needReview() {
        return this == PENDING_REVIEW || this == IN_REVIEW;
    }

    /**
     * 是否需要签署
     */
    public boolean needSignature() {
        return this == PENDING_SIGNATURE;
    }

    /**
     * 是否已完成
     */
    public boolean isCompleted() {
        return this == SIGNED || this == ARCHIVED;
    }

    /**
     * 是否可以作废
     */
    public boolean canVoid() {
        return this != VOID && this != ARCHIVED;
    }

    /**
     * 是否可以归档
     */
    public boolean canArchive() {
        return this == SIGNED || this == PENDING_ARCHIVE;
    }

    /**
     * 获取下一个可能的状态
     */
    public DocumentStatusEnum[] getNextPossibleStatus() {
        switch (this) {
            case DRAFT:
                return new DocumentStatusEnum[]{PENDING_REVIEW, VOID};
            case PENDING_REVIEW:
                return new DocumentStatusEnum[]{IN_REVIEW, VOID};
            case IN_REVIEW:
                return new DocumentStatusEnum[]{NEED_REVISION, REVIEWED, VOID};
            case NEED_REVISION:
                return new DocumentStatusEnum[]{PENDING_REVIEW, VOID};
            case REVIEWED:
                return new DocumentStatusEnum[]{PENDING_SIGNATURE, PENDING_ARCHIVE, VOID};
            case PENDING_SIGNATURE:
                return new DocumentStatusEnum[]{SIGNED, VOID};
            case SIGNED:
                return new DocumentStatusEnum[]{PENDING_ARCHIVE, VOID};
            case PENDING_ARCHIVE:
                return new DocumentStatusEnum[]{ARCHIVED, VOID};
            default:
                return new DocumentStatusEnum[]{};
        }
    }
} 