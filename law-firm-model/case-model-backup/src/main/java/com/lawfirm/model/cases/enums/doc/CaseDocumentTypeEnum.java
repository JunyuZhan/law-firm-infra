package com.lawfirm.model.cases.enums.doc;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件文档类型枚举
 */
@Getter
public enum CaseDocumentTypeEnum implements BaseEnum<String> {
    
    // 诉讼文书
    COMPLAINT("起诉状", DocGroup.LITIGATION, true, true),
    DEFENSE("答辩状", DocGroup.LITIGATION, true, true),
    COUNTERCLAIM("反诉状", DocGroup.LITIGATION, true, true),
    APPEAL("上诉状", DocGroup.LITIGATION, true, true),
    MEDIATION_STATEMENT("调解书", DocGroup.LITIGATION, true, true),
    JUDGMENT("判决书", DocGroup.LITIGATION, true, true),
    RULING("裁定书", DocGroup.LITIGATION, true, true),
    
    // 证据材料
    EVIDENCE_LIST("证据清单", DocGroup.EVIDENCE, true, false),
    EVIDENCE_ORIGINAL("原始证据", DocGroup.EVIDENCE, false, true),
    EVIDENCE_COPY("证据副本", DocGroup.EVIDENCE, false, false),
    EVIDENCE_PRESERVATION("证据保全", DocGroup.EVIDENCE, true, true),
    
    // 合同文书
    CONTRACT("合同文本", DocGroup.CONTRACT, true, true),
    CONTRACT_SUPPLEMENT("补充协议", DocGroup.CONTRACT, true, true),
    CONTRACT_AMENDMENT("变更协议", DocGroup.CONTRACT, true, true),
    CONTRACT_TERMINATION("终止协议", DocGroup.CONTRACT, true, true),
    
    // 授权文件
    POWER_OF_ATTORNEY("授权委托书", DocGroup.AUTHORIZATION, true, true),
    SPECIAL_AUTHORIZATION("特别授权委托", DocGroup.AUTHORIZATION, true, true),
    AGENCY_AGREEMENT("代理协议", DocGroup.AUTHORIZATION, true, true),
    
    // 财务文件
    INVOICE("发票", DocGroup.FINANCIAL, true, true),
    FEE_AGREEMENT("收费协议", DocGroup.FINANCIAL, true, true),
    PAYMENT_RECEIPT("付款凭证", DocGroup.FINANCIAL, true, true),
    COST_SETTLEMENT("费用结算单", DocGroup.FINANCIAL, true, false),
    
    // 工作文件
    CASE_SUMMARY("案件小结", DocGroup.WORKING, false, false),
    MEETING_MINUTES("会议记录", DocGroup.WORKING, false, false),
    WORK_PLAN("工作计划", DocGroup.WORKING, false, false),
    RESEARCH_MEMO("法律研究", DocGroup.WORKING, false, false),
    
    // 通信文件
    CORRESPONDENCE("往来函件", DocGroup.COMMUNICATION, false, false),
    EMAIL("电子邮件", DocGroup.COMMUNICATION, false, false),
    NOTICE("通知书", DocGroup.COMMUNICATION, true, false),
    
    // 其他文件
    OTHER("其他文件", DocGroup.OTHER, false, false);

    private final String description;
    private final DocGroup docGroup;
    private final boolean needsOriginal;  // 是否需要原件
    private final boolean needsArchive;   // 是否需要归档

    CaseDocumentTypeEnum(String description, DocGroup docGroup, boolean needsOriginal, boolean needsArchive) {
        this.description = description;
        this.docGroup = docGroup;
        this.needsOriginal = needsOriginal;
        this.needsArchive = needsArchive;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 获取文档分组
     * @return 文档分组
     */
    public DocGroup getDocGroup() {
        return this.docGroup;
    }

    /**
     * 判断是否需要原件
     * @return 是否需要原件
     */
    public boolean needsOriginal() {
        return this.needsOriginal;
    }

    /**
     * 判断是否需要归档
     * @return 是否需要归档
     */
    public boolean needsArchive() {
        return this.needsArchive;
    }

    /**
     * 判断是否为诉讼文书
     * @return 是否诉讼文书
     */
    public boolean isLitigationDoc() {
        return this.docGroup == DocGroup.LITIGATION;
    }

    /**
     * 判断是否为证据材料
     * @return 是否证据材料
     */
    public boolean isEvidence() {
        return this.docGroup == DocGroup.EVIDENCE;
    }

    /**
     * 判断是否为合同文书
     * @return 是否合同文书
     */
    public boolean isContract() {
        return this.docGroup == DocGroup.CONTRACT;
    }

    /**
     * 判断是否为授权文件
     * @return 是否授权文件
     */
    public boolean isAuthorization() {
        return this.docGroup == DocGroup.AUTHORIZATION;
    }

    /**
     * 判断是否为财务文件
     * @return 是否财务文件
     */
    public boolean isFinancial() {
        return this.docGroup == DocGroup.FINANCIAL;
    }

    /**
     * 获取文档要求说明
     * @return 文档要求说明
     */
    public String getRequirements() {
        StringBuilder requirements = new StringBuilder();
        if (this.needsOriginal) {
            requirements.append("[需要原件]");
        }
        if (this.needsArchive) {
            requirements.append("[需要归档]");
        }
        if (requirements.length() > 0) {
            return this.description + requirements.toString();
        }
        return this.description;
    }

    /**
     * 文档分组
     */
    public enum DocGroup {
        LITIGATION("诉讼文书"),
        EVIDENCE("证据材料"),
        CONTRACT("合同文书"),
        AUTHORIZATION("授权文件"),
        FINANCIAL("财务文件"),
        WORKING("工作文件"),
        COMMUNICATION("通信文件"),
        OTHER("其他文件");

        private final String description;

        DocGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 