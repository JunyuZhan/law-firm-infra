package com.lawfirm.model.cases.enums.doc;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件文档分类枚举
 */
@Getter
public enum CaseDocumentCategoryEnum implements BaseEnum<Integer> {

    /**
     * 诉讼文书
     */
    LITIGATION_DOCUMENT(1, "诉讼文书"),

    /**
     * 证据材料
     */
    EVIDENCE_MATERIAL(2, "证据材料"),

    /**
     * 合同文件
     */
    CONTRACT_DOCUMENT(3, "合同文件"),

    /**
     * 法院文书
     */
    COURT_DOCUMENT(4, "法院文书"),

    /**
     * 调查报告
     */
    INVESTIGATION_REPORT(5, "调查报告"),

    /**
     * 会议记录
     */
    MEETING_MINUTES(6, "会议记录"),

    /**
     * 工作报告
     */
    WORK_REPORT(7, "工作报告"),

    /**
     * 财务文件
     */
    FINANCIAL_DOCUMENT(8, "财务文件"),

    /**
     * 通知文件
     */
    NOTIFICATION_DOCUMENT(9, "通知文件"),

    /**
     * 其他文件
     */
    OTHER_DOCUMENT(99, "其他文件");

    private final Integer value;
    private final String description;

    CaseDocumentCategoryEnum(Integer value, String description) {
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
    public static CaseDocumentCategoryEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseDocumentCategoryEnum category : values()) {
            if (category.value.equals(value)) {
                return category;
            }
        }
        return null;
    }

    /**
     * 是否需要特殊权限
     */
    public boolean needSpecialPermission() {
        return this == FINANCIAL_DOCUMENT || this == CONTRACT_DOCUMENT;
    }

    /**
     * 是否需要归档
     */
    public boolean needArchive() {
        return this != OTHER_DOCUMENT;
    }

    /**
     * 是否需要审核
     */
    public boolean needReview() {
        return this == LITIGATION_DOCUMENT || this == INVESTIGATION_REPORT || 
               this == WORK_REPORT || this == FINANCIAL_DOCUMENT;
    }

    /**
     * 是否需要签名
     */
    public boolean needSignature() {
        return this == LITIGATION_DOCUMENT || this == CONTRACT_DOCUMENT || 
               this == INVESTIGATION_REPORT || this == WORK_REPORT;
    }

    /**
     * 是否需要编号
     */
    public boolean needSerialNumber() {
        return this == LITIGATION_DOCUMENT || this == COURT_DOCUMENT || 
               this == CONTRACT_DOCUMENT || this == FINANCIAL_DOCUMENT;
    }

    /**
     * 获取默认安全级别
     */
    public DocumentSecurityLevelEnum getDefaultSecurityLevel() {
        switch (this) {
            case FINANCIAL_DOCUMENT:
            case CONTRACT_DOCUMENT:
                return DocumentSecurityLevelEnum.CONFIDENTIAL;
            case LITIGATION_DOCUMENT:
            case COURT_DOCUMENT:
                return DocumentSecurityLevelEnum.INTERNAL;
            default:
                return DocumentSecurityLevelEnum.PUBLIC;
        }
    }

    /**
     * 获取建议保存期限（月）
     */
    public int getSuggestedRetentionMonths() {
        switch (this) {
            case LITIGATION_DOCUMENT:
            case COURT_DOCUMENT:
            case CONTRACT_DOCUMENT:
            case FINANCIAL_DOCUMENT:
                return 120; // 10年
            case EVIDENCE_MATERIAL:
                return 60; // 5年
            case INVESTIGATION_REPORT:
            case WORK_REPORT:
                return 36; // 3年
            case MEETING_MINUTES:
            case NOTIFICATION_DOCUMENT:
                return 24; // 2年
            default:
                return 12; // 1年
        }
    }
} 