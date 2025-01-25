package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件进展枚举
 */
@Getter
public enum CaseProgressEnum implements BaseEnum<String> {
    
    // 立案阶段
    CASE_FILING_PREPARATION("立案准备", ProgressGroup.FILING, 0),
    CASE_FILING_SUBMITTED("立案提交", ProgressGroup.FILING, 10),
    CASE_FILING_ACCEPTED("立案受理", ProgressGroup.FILING, 20),
    CASE_FILING_SUPPLEMENTARY("立案补充", ProgressGroup.FILING, 15),
    
    // 审理阶段
    TRIAL_PREPARATION("审理准备", ProgressGroup.TRIAL, 30),
    EVIDENCE_SUBMISSION("证据提交", ProgressGroup.TRIAL, 40),
    EVIDENCE_EXCHANGE("证据交换", ProgressGroup.TRIAL, 45),
    COURT_HEARING("开庭审理", ProgressGroup.TRIAL, 50),
    COURT_INVESTIGATION("法庭调查", ProgressGroup.TRIAL, 55),
    COURT_DEBATE("法庭辩论", ProgressGroup.TRIAL, 60),
    FINAL_STATEMENT("最后陈述", ProgressGroup.TRIAL, 65),
    
    // 调解阶段
    MEDIATION_PREPARATION("调解准备", ProgressGroup.MEDIATION, 70),
    MEDIATION_ONGOING("调解进行", ProgressGroup.MEDIATION, 75),
    MEDIATION_AGREEMENT("达成调解", ProgressGroup.MEDIATION, 80),
    MEDIATION_FAILED("调解失败", ProgressGroup.MEDIATION, 85),
    
    // 判决阶段
    JUDGMENT_PENDING("等待判决", ProgressGroup.JUDGMENT, 90),
    JUDGMENT_RECEIVED("收到判决", ProgressGroup.JUDGMENT, 95),
    JUDGMENT_EFFECTIVE("判决生效", ProgressGroup.JUDGMENT, 100),
    
    // 执行阶段
    ENFORCEMENT_APPLICATION("申请执行", ProgressGroup.ENFORCEMENT, 110),
    ENFORCEMENT_ONGOING("执行进行", ProgressGroup.ENFORCEMENT, 120),
    ENFORCEMENT_COMPLETED("执行完毕", ProgressGroup.ENFORCEMENT, 130),
    ENFORCEMENT_TERMINATED("终止执行", ProgressGroup.ENFORCEMENT, 125),
    
    // 结案阶段
    CASE_CLOSED("正常结案", ProgressGroup.CLOSING, 140),
    CASE_WITHDRAWN("撤诉结案", ProgressGroup.CLOSING, 135),
    CASE_DISMISSED("驳回结案", ProgressGroup.CLOSING, 135),
    CASE_TRANSFERRED("移送结案", ProgressGroup.CLOSING, 135);

    private final String description;
    private final ProgressGroup progressGroup;
    private final Integer progressValue;  // 进展值，用于排序和进度计算

    CaseProgressEnum(String description, ProgressGroup progressGroup, Integer progressValue) {
        this.description = description;
        this.progressGroup = progressGroup;
        this.progressValue = progressValue;
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
     * 获取进展分组
     * @return 进展分组
     */
    public ProgressGroup getProgressGroup() {
        return this.progressGroup;
    }

    /**
     * 获取进展值
     * @return 进展值
     */
    public Integer getProgressValue() {
        return this.progressValue;
    }

    /**
     * 判断是否为立案阶段
     * @return 是否立案阶段
     */
    public boolean isFilingStage() {
        return this.progressGroup == ProgressGroup.FILING;
    }

    /**
     * 判断是否为审理阶段
     * @return 是否审理阶段
     */
    public boolean isTrialStage() {
        return this.progressGroup == ProgressGroup.TRIAL;
    }

    /**
     * 判断是否为调解阶段
     * @return 是否调解阶段
     */
    public boolean isMediationStage() {
        return this.progressGroup == ProgressGroup.MEDIATION;
    }

    /**
     * 判断是否为判决阶段
     * @return 是否判决阶段
     */
    public boolean isJudgmentStage() {
        return this.progressGroup == ProgressGroup.JUDGMENT;
    }

    /**
     * 判断是否为执行阶段
     * @return 是否执行阶段
     */
    public boolean isEnforcementStage() {
        return this.progressGroup == ProgressGroup.ENFORCEMENT;
    }

    /**
     * 判断是否为结案阶段
     * @return 是否结案阶段
     */
    public boolean isClosingStage() {
        return this.progressGroup == ProgressGroup.CLOSING;
    }

    /**
     * 计算案件进度百分比
     * @return 进度百分比
     */
    public Double calculateProgress() {
        if (this.progressValue >= 140) {
            return 100.0;
        }
        return (this.progressValue * 100.0) / 140;
    }

    /**
     * 判断是否可以进入下一阶段
     * @param next 下一进展
     * @return 是否可以进入
     */
    public boolean canProgressTo(CaseProgressEnum next) {
        return next.getProgressValue() > this.progressValue;
    }

    /**
     * 获取进展说明
     * @return 完整的进展说明
     */
    public String getProgressDescription() {
        return String.format("%s - %s (进度: %.1f%%)", 
            this.progressGroup.getDescription(),
            this.description,
            this.calculateProgress());
    }

    /**
     * 进展分组
     */
    public enum ProgressGroup {
        FILING("立案阶段"),
        TRIAL("审理阶段"),
        MEDIATION("调解阶段"),
        JUDGMENT("判决阶段"),
        ENFORCEMENT("执行阶段"),
        CLOSING("结案阶段");

        private final String description;

        ProgressGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 