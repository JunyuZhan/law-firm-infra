package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件进展枚举
 * 数值设计说明：
 * 1. 10-19: 初始阶段
 * 2. 20-29: 证据准备阶段
 * 3. 30-39: 文书准备阶段
 * 4. 40-49: 侦查阶段（刑事）/立案阶段（民事）
 * 5. 50-59: 审查起诉阶段（刑事）/庭前准备阶段（民事）
 * 6. 60-69: 审判阶段
 * 7. 70-79: 执行阶段
 * 8. 80-89: 结案阶段
 * 9. 90-99: 终结阶段
 */
@Getter
public enum CaseProgressEnum implements BaseEnum<Integer> {

    // 初始阶段 10-19
    CASE_RECEIVED(11, "案件接收"),
    INITIAL_REVIEW(12, "初步审查"),
    CLIENT_COMMUNICATION(13, "客户沟通"),
    CASE_ANALYSIS(14, "案情分析"),
    STRATEGY_PLANNING(15, "策略制定"),
    TEAM_ASSEMBLY(16, "团队组建"),

    // 证据准备阶段 20-29
    EVIDENCE_COLLECTION_STARTED(21, "证据收集开始"),
    DOCUMENT_GATHERING(22, "材料收集"),
    EVIDENCE_REVIEW(23, "证据审查"),
    EVIDENCE_ORGANIZATION(24, "证据整理"),
    MEETING_WITH_SUSPECT(25, "会见犯罪嫌疑人"),
    BACKGROUND_INVESTIGATION(26, "背景调查"),
    WITNESS_INTERVIEW(27, "证人走访"),
    EXPERT_CONSULTATION(28, "专家咨询"),
    EVIDENCE_COLLECTION_COMPLETED(29, "证据收集完成"),

    // 文书准备阶段 30-39
    DOCUMENT_DRAFTING_STARTED(31, "文书起草开始"),
    BAIL_APPLICATION(32, "取保候审申请"),
    DEFENSE_OPINION(33, "律师意见书"),
    COMPLAINT_DRAFTING(34, "诉状起草"),
    ARBITRATION_APPLICATION_DRAFTING(35, "仲裁申请书起草"),
    POWER_OF_ATTORNEY(36, "委托手续"),
    DOCUMENT_REVIEW(37, "文书审核"),
    DOCUMENT_REVISION(38, "文书修改"),
    DOCUMENT_FINALIZATION(39, "文书定稿"),
    DOCUMENT_PREPARATION_COMPLETED(39, "文书准备完成"),

    // 侦查/立案/受理阶段 40-49
    INVESTIGATION_STARTED(41, "侦查开始"),
    POLICE_COMMUNICATION(42, "公安沟通"),
    DETENTION_REVIEW(43, "羁押必要性审查"),
    INVESTIGATION_MONITORING(44, "侦查活动监督"),
    CASE_FILING(45, "案件受理"),
    ARBITRATION_FILING(46, "仲裁立案"),
    FILING_PAYMENT(47, "诉讼/仲裁费缴纳"),
    INVESTIGATION_COMPLETED(48, "侦查终结"),
    CASE_TRANSFER(49, "移送审查起诉"),

    // 审查起诉/庭前准备/仲裁前准备阶段 50-59
    PROSECUTION_REVIEW_STARTED(51, "审查起诉开始"),
    PROSECUTOR_COMMUNICATION(52, "检察院沟通"),
    SUPPLEMENTARY_INVESTIGATION(53, "补充侦查"),
    PROSECUTION_OPINION(54, "审查意见"),
    EVIDENCE_EXCHANGE(55, "证据交换"),
    PRE_TRIAL_MEETING(56, "庭前/仲裁前会议"),
    PROSECUTION_DECISION(57, "起诉决定"),
    NON_PROSECUTION_REVIEW(58, "不起诉复议"),
    PRE_TRIAL_COMPLETED(59, "庭前/仲裁前准备完成"),

    // 审判/仲裁审理阶段 60-69
    TRIAL_STARTED(61, "审判/仲裁审理开始"),
    COURT_PRESENTATION(62, "法庭/仲裁庭陈述"),
    EVIDENCE_PRESENTATION(63, "举证质证"),
    WITNESS_TESTIMONY(64, "证人证言"),
    COURT_DEBATE(65, "法庭/仲裁辩论"),
    FINAL_STATEMENT(66, "最后陈述"),
    SENTENCING_SUGGESTION(67, "量刑建议"),
    TRIAL_CONCLUDED(68, "审判/仲裁审理结束"),
    VERDICT_RECEIVED(69, "判决/裁决送达"),

    // 执行/救济阶段 70-79
    APPEAL_PREPARATION(71, "上诉/不服仲裁准备"),
    APPEAL_FILED(72, "提起上诉"),
    ARBITRATION_REVOCATION(73, "申请撤销仲裁裁决"),
    ENFORCEMENT_OBJECTION(74, "执行异议"),
    ENFORCEMENT_APPLICATION(75, "申请执行"),
    SETTLEMENT_NEGOTIATION(76, "和解协商"),
    ENFORCEMENT_MONITORING(77, "执行监督"),
    ENFORCEMENT_COMPLETED(78, "执行完毕"),
    ENFORCEMENT_CLOSED(79, "执行终结"),

    // 结案阶段 80-89
    CASE_CLOSING_STARTED(81, "结案开始"),
    FEE_SETTLEMENT(82, "费用结算"),
    DOCUMENT_ARCHIVING(83, "文件归档"),
    CLIENT_REPORTING(84, "客户报告"),
    TEAM_EVALUATION(85, "团队总结"),
    CASE_REVIEW(86, "案件复盘"),
    CASE_CLOSING_COMPLETED(87, "结案完成"),

    // 终结阶段 90-99
    COMPLETED(91, "案件完结"),
    ARCHIVED(92, "归档完成");

    private final Integer value;
    private final String description;

    CaseProgressEnum(Integer value, String description) {
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
    public static CaseProgressEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseProgressEnum progress : values()) {
            if (progress.value.equals(value)) {
                return progress;
            }
        }
        return null;
    }

    /**
     * 是否是初始阶段
     */
    public boolean isInitialStage() {
        return value >= 10 && value < 20;
    }

    /**
     * 是否是证据准备阶段
     */
    public boolean isEvidenceStage() {
        return value >= 20 && value < 30;
    }

    /**
     * 是否是文书准备阶段
     */
    public boolean isDocumentStage() {
        return value >= 30 && value < 40;
    }

    /**
     * 是否是立案阶段
     */
    public boolean isFilingStage() {
        return value >= 40 && value < 50;
    }

    /**
     * 是否是庭前准备阶段
     */
    public boolean isPreTrialStage() {
        return value >= 50 && value < 60;
    }

    /**
     * 是否是庭审阶段
     */
    public boolean isTrialStage() {
        return value >= 60 && value < 70;
    }

    /**
     * 是否是判决执行阶段
     */
    public boolean isJudgmentStage() {
        return value >= 70 && value < 80;
    }

    /**
     * 是否是结案阶段
     */
    public boolean isClosingStage() {
        return value >= 80 && value < 90;
    }

    /**
     * 是否是终结阶段
     */
    public boolean isCompletedStage() {
        return value >= 90;
    }

    /**
     * 是否是前期阶段
     */
    public boolean isEarlyStage() {
        return value < 40;
    }

    /**
     * 是否是中期阶段
     */
    public boolean isMiddleStage() {
        return value >= 40 && value < 70;
    }

    /**
     * 是否是后期阶段
     */
    public boolean isLateStage() {
        return value >= 70;
    }

    /**
     * 获取进展顺序（数字越小越靠前）
     */
    public int getOrder() {
        return value;
    }

    /**
     * 比较进展
     * @return 大于0表示当前进展更靠后，小于0表示当前进展更靠前，等于0表示相同
     */
    public int compareByProgress(CaseProgressEnum other) {
        if (other == null) {
            return -1;
        }
        return this.value.compareTo(other.value);
    }

    /**
     * 获取下一个主要进展节点
     */
    public CaseProgressEnum getNextMajorProgress() {
        int nextValue = ((value / 10) + 1) * 10 + 1;
        for (CaseProgressEnum progress : values()) {
            if (progress.value == nextValue) {
                return progress;
            }
        }
        return null;
    }

    /**
     * 是否需要团队评审
     */
    public boolean needTeamReview() {
        return this == DOCUMENT_FINALIZATION || this == PRE_TRIAL_COMPLETED || 
               this == PROSECUTION_DECISION || this == APPEAL_PREPARATION;
    }

    /**
     * 是否需要合伙人审批
     */
    public boolean needPartnerApproval() {
        return this == STRATEGY_PLANNING || this == DOCUMENT_FINALIZATION || 
               this == APPEAL_PREPARATION || this == ENFORCEMENT_APPLICATION;
    }

    /**
     * 是否需要客户确认
     */
    public boolean needClientConfirmation() {
        return this == STRATEGY_PLANNING || this == DOCUMENT_FINALIZATION || 
               this == APPEAL_PREPARATION || this == SETTLEMENT_NEGOTIATION;
    }

    /**
     * 是否需要费用结算
     */
    public boolean needFeeSettlement() {
        return this == FEE_SETTLEMENT || this == ENFORCEMENT_COMPLETED || 
               this == CASE_CLOSING_COMPLETED;
    }

    /**
     * 是否需要风险提示
     */
    public boolean needRiskWarning() {
        return this == STRATEGY_PLANNING || this == EVIDENCE_COLLECTION_COMPLETED || 
               this == BAIL_APPLICATION || this == PROSECUTION_REVIEW_STARTED ||
               this == TRIAL_STARTED || this == APPEAL_PREPARATION;
    }

    /**
     * 是否需要进度报告
     */
    public boolean needProgressReport() {
        return this == EVIDENCE_COLLECTION_COMPLETED || this == DOCUMENT_PREPARATION_COMPLETED || 
               this == INVESTIGATION_COMPLETED || this == PROSECUTION_DECISION ||
               this == TRIAL_CONCLUDED || this == ENFORCEMENT_COMPLETED;
    }

    /**
     * 是否是刑事案件特有节点
     */
    public boolean isCriminalOnly() {
        return this == MEETING_WITH_SUSPECT || this == BAIL_APPLICATION || 
               this == DEFENSE_OPINION || this == INVESTIGATION_STARTED ||
               this == POLICE_COMMUNICATION || this == DETENTION_REVIEW ||
               this == INVESTIGATION_MONITORING || this == INVESTIGATION_COMPLETED ||
               this == CASE_TRANSFER || this == PROSECUTION_REVIEW_STARTED ||
               this == PROSECUTOR_COMMUNICATION || this == SUPPLEMENTARY_INVESTIGATION ||
               this == PROSECUTION_OPINION || this == PROSECUTION_DECISION ||
               this == NON_PROSECUTION_REVIEW || this == FINAL_STATEMENT ||
               this == SENTENCING_SUGGESTION;
    }

    /**
     * 是否是民事案件特有节点
     */
    public boolean isCivilOnly() {
        return this == COMPLAINT_DRAFTING || this == CASE_FILING ||
               this == FILING_PAYMENT || this == ENFORCEMENT_APPLICATION ||
               this == SETTLEMENT_NEGOTIATION;
    }

    /**
     * 是否是仲裁案件特有节点
     */
    public boolean isArbitrationOnly() {
        return this == ARBITRATION_APPLICATION_DRAFTING || this == ARBITRATION_FILING ||
               this == ARBITRATION_REVOCATION || this == ENFORCEMENT_OBJECTION;
    }

    /**
     * 是否是商事仲裁节点
     */
    public boolean isCommercialArbitration() {
        return isArbitrationOnly();
    }

    /**
     * 是否是劳动仲裁节点
     */
    public boolean isLaborArbitration() {
        return isArbitrationOnly();
    }

    /**
     * 是否是侦查阶段节点（刑事）
     */
    public boolean isInvestigationStage() {
        return value >= 40 && value < 50 && isCriminalOnly();
    }

    /**
     * 是否是审查起诉阶段节点（刑事）
     */
    public boolean isProsecutionStage() {
        return value >= 50 && value < 60 && isCriminalOnly();
    }
} 