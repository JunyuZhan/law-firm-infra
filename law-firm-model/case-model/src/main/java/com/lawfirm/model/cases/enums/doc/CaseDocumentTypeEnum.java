package com.lawfirm.model.cases.enums.doc;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件文书类型枚举
 */
@Getter
public enum CaseDocumentTypeEnum implements BaseEnum<Integer> {

    // 诉讼文书类
    COMPLAINT(1, "起诉状", CaseDocumentCategoryEnum.LITIGATION_DOCUMENT),
    DEFENSE(2, "答辩状", CaseDocumentCategoryEnum.LITIGATION_DOCUMENT),
    APPEAL(3, "上诉状", CaseDocumentCategoryEnum.LITIGATION_DOCUMENT),
    ATTORNEY_SPEECH(4, "代理词", CaseDocumentCategoryEnum.LITIGATION_DOCUMENT),
    LEGAL_OPINION(5, "法律意见书", CaseDocumentCategoryEnum.LITIGATION_DOCUMENT),
    
    // 证据材料类
    EVIDENCE_LIST(6, "证据清单", CaseDocumentCategoryEnum.EVIDENCE_MATERIAL),
    WITNESS_STATEMENT(7, "证人证言", CaseDocumentCategoryEnum.EVIDENCE_MATERIAL),
    EXPERT_OPINION(8, "专家意见", CaseDocumentCategoryEnum.EVIDENCE_MATERIAL),
    
    // 合同文件类
    SETTLEMENT_AGREEMENT(9, "和解协议", CaseDocumentCategoryEnum.CONTRACT_DOCUMENT),
    ENGAGEMENT_CONTRACT(10, "委托合同", CaseDocumentCategoryEnum.CONTRACT_DOCUMENT),
    POWER_OF_ATTORNEY(11, "授权委托书", CaseDocumentCategoryEnum.CONTRACT_DOCUMENT),
    
    // 法院文书类
    JUDGMENT(12, "判决书", CaseDocumentCategoryEnum.COURT_DOCUMENT),
    RULING(13, "裁定书", CaseDocumentCategoryEnum.COURT_DOCUMENT),
    MEDIATION_STATEMENT(14, "调解书", CaseDocumentCategoryEnum.COURT_DOCUMENT),
    ENFORCEMENT_ORDER(15, "执行裁定书", CaseDocumentCategoryEnum.COURT_DOCUMENT),
    
    // 调查报告类
    INVESTIGATION_REPORT(16, "调查报告", CaseDocumentCategoryEnum.INVESTIGATION_REPORT),
    SITE_SURVEY(17, "现场勘查报告", CaseDocumentCategoryEnum.INVESTIGATION_REPORT),
    INTERVIEW_RECORD(18, "访谈记录", CaseDocumentCategoryEnum.INVESTIGATION_REPORT),
    
    // 会议记录类
    TEAM_MEETING_MINUTES(19, "团队会议纪要", CaseDocumentCategoryEnum.MEETING_MINUTES),
    CLIENT_MEETING_MINUTES(20, "客户会见纪要", CaseDocumentCategoryEnum.MEETING_MINUTES),
    NEGOTIATION_MINUTES(21, "谈判会议纪要", CaseDocumentCategoryEnum.MEETING_MINUTES),
    
    // 工作报告类
    CASE_PROGRESS_REPORT(22, "案件进展报告", CaseDocumentCategoryEnum.WORK_REPORT),
    WEEKLY_REPORT(23, "周工作报告", CaseDocumentCategoryEnum.WORK_REPORT),
    MONTHLY_REPORT(24, "月工作报告", CaseDocumentCategoryEnum.WORK_REPORT),
    
    // 财务文件类
    FEE_AGREEMENT(25, "收费协议", CaseDocumentCategoryEnum.FINANCIAL_DOCUMENT),
    EXPENSE_REPORT(26, "费用清单", CaseDocumentCategoryEnum.FINANCIAL_DOCUMENT),
    PAYMENT_RECEIPT(27, "收费收据", CaseDocumentCategoryEnum.FINANCIAL_DOCUMENT),
    
    // 通知文件类
    HEARING_NOTICE(28, "开庭通知", CaseDocumentCategoryEnum.NOTIFICATION_DOCUMENT),
    DOCUMENT_RECEIPT(29, "文书送达回执", CaseDocumentCategoryEnum.NOTIFICATION_DOCUMENT),
    CASE_NOTICE(30, "案件通知书", CaseDocumentCategoryEnum.NOTIFICATION_DOCUMENT),
    
    // 其他文书
    OTHER(99, "其他文书", CaseDocumentCategoryEnum.OTHER_DOCUMENT);

    private final Integer value;
    private final String description;
    private final CaseDocumentCategoryEnum category;

    CaseDocumentTypeEnum(Integer value, String description, CaseDocumentCategoryEnum category) {
        this.value = value;
        this.description = description;
        this.category = category;
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
     * 获取文档分类
     */
    public CaseDocumentCategoryEnum getCategory() {
        return category;
    }

    /**
     * 根据值获取枚举
     */
    public static CaseDocumentTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseDocumentTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据分类获取文书类型列表
     */
    public static CaseDocumentTypeEnum[] getTypesByCategory(CaseDocumentCategoryEnum category) {
        if (category == null) {
            return new CaseDocumentTypeEnum[]{};
        }
        return java.util.Arrays.stream(values())
            .filter(type -> type.category == category)
            .toArray(CaseDocumentTypeEnum[]::new);
    }

    /**
     * 是否需要审核
     */
    public boolean needReview() {
        return this.category.needReview() || 
               this == LEGAL_OPINION || 
               this == INVESTIGATION_REPORT || 
               this == CASE_PROGRESS_REPORT;
    }

    /**
     * 是否需要签名
     */
    public boolean needSignature() {
        return this.category.needSignature() || 
               this == COMPLAINT || 
               this == DEFENSE || 
               this == APPEAL || 
               this == ATTORNEY_SPEECH;
    }

    /**
     * 是否需要编号
     */
    public boolean needSerialNumber() {
        return this.category.needSerialNumber();
    }

    /**
     * 获取默认安全级别
     */
    public DocumentSecurityLevelEnum getDefaultSecurityLevel() {
        return this.category.getDefaultSecurityLevel();
    }

    /**
     * 获取建议保存期限（月）
     */
    public int getSuggestedRetentionMonths() {
        return this.category.getSuggestedRetentionMonths();
    }
} 