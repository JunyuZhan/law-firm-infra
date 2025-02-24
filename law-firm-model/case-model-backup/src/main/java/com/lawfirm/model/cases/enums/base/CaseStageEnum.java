package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件阶段枚举
 */
@Getter
public enum CaseStageEnum implements BaseEnum<String> {
    
    // 民事案件阶段
    CIVIL_FIRST_INSTANCE("民事一审"),
    CIVIL_SECOND_INSTANCE("民事二审"),
    CIVIL_RETRIAL("民事再审"),
    CIVIL_EXECUTION("民事执行"),
    CIVIL_SETTLEMENT("民事调解"),
    CIVIL_WITHDRAWAL("民事撤诉"),
    CIVIL_APPEAL("民事上诉"),
    CIVIL_PROTEST("民事抗诉"),
    
    // 刑事案件阶段
    CRIMINAL_CASE_FILING("刑事立案"),
    CRIMINAL_INVESTIGATION("刑事侦查"),
    CRIMINAL_DETENTION("刑事拘留"),
    CRIMINAL_BAIL("取保候审"),
    CRIMINAL_RESIDENTIAL_SURVEILLANCE("监视居住"),
    CRIMINAL_ARREST_REVIEW("审查逮捕"),
    CRIMINAL_ARREST("逮捕"),
    CRIMINAL_SUPPLEMENTARY_INVESTIGATION("补充侦查"),
    CRIMINAL_PROSECUTION_REVIEW("审查起诉"),
    CRIMINAL_PROSECUTION("提起公诉"),
    CRIMINAL_NON_PROSECUTION("不起诉"),
    CRIMINAL_CONDITIONAL_NON_PROSECUTION("附条件不起诉"),
    CRIMINAL_FIRST_INSTANCE_PREPARATION("一审审前准备"),
    CRIMINAL_FIRST_INSTANCE("一审审理"),
    CRIMINAL_FIRST_INSTANCE_SUMMARY("一审简易程序"),
    CRIMINAL_FIRST_INSTANCE_JUDGMENT("一审判决"),
    CRIMINAL_APPEAL_PROSECUTION("检察院上诉"),
    CRIMINAL_APPEAL_DEFENDANT("被告人上诉"),
    CRIMINAL_SECOND_INSTANCE("二审审理"),
    CRIMINAL_SECOND_INSTANCE_JUDGMENT("二审判决"),
    CRIMINAL_PROTEST("抗诉"),
    CRIMINAL_RETRIAL("再审"),
    CRIMINAL_DEATH_PENALTY_REVIEW("死刑复核"),
    CRIMINAL_EXECUTION_PREPARATION("执行准备"),
    CRIMINAL_EXECUTION("刑罚执行"),
    CRIMINAL_PAROLE("假释"),
    CRIMINAL_COMMUTATION("减刑"),
    CRIMINAL_TEMPORARY_SERVE_OUTSIDE("暂予监外执行"),
    CRIMINAL_MEDICAL_PAROLE("保外就医"),
    
    // 行政案件阶段
    ADMINISTRATIVE_RECONSIDERATION("行政复议"),
    ADMINISTRATIVE_FIRST_INSTANCE("行政一审"),
    ADMINISTRATIVE_SECOND_INSTANCE("行政二审"),
    ADMINISTRATIVE_RETRIAL("行政再审"),
    ADMINISTRATIVE_EXECUTION("行政执行"),
    ADMINISTRATIVE_SETTLEMENT("行政调解"),
    ADMINISTRATIVE_WITHDRAWAL("行政撤诉"),
    ADMINISTRATIVE_APPEAL("行政上诉"),
    ADMINISTRATIVE_PROTEST("行政抗诉"),
    
    // 仲裁案件阶段
    ARBITRATION_APPLICATION("仲裁申请"),
    ARBITRATION_ACCEPTANCE("仲裁受理"),
    ARBITRATION_HEARING("仲裁审理"),
    ARBITRATION_MEDIATION("仲裁调解"),
    ARBITRATION_AWARD("仲裁裁决"),
    ARBITRATION_EXECUTION("仲裁执行"),
    
    // 非诉讼案件阶段
    NON_LITIGATION_APPLICATION("非诉申请"),
    NON_LITIGATION_REVIEW("非诉审查"),
    NON_LITIGATION_HEARING("非诉审理"),
    NON_LITIGATION_DECISION("非诉裁定"),
    NON_LITIGATION_EXECUTION("非诉执行"),
    
    // 特别程序案件阶段
    SPECIAL_APPLICATION("特别程序申请"),
    SPECIAL_REVIEW("特别程序审查"),
    SPECIAL_HEARING("特别程序审理"),
    SPECIAL_DECISION("特别程序裁定"),
    SPECIAL_EXECUTION("特别程序执行"),
    
    // 执行案件阶段
    EXECUTION_APPLICATION("执行申请"),
    EXECUTION_ACCEPTANCE("执行受理"),
    EXECUTION_IMPLEMENTATION("执行实施"),
    EXECUTION_CONCLUSION("执行结案"),
    EXECUTION_TERMINATION("执行终结"),
    
    // 执行异议和复议阶段
    EXECUTION_OBJECTION("执行异议"),
    EXECUTION_OBJECTION_REVIEW("执行异议审查"),
    EXECUTION_OBJECTION_RULING("执行异议裁定"),
    EXECUTION_RECONSIDERATION("执行复议"),
    EXECUTION_RECONSIDERATION_REVIEW("执行复议审查"),
    EXECUTION_RECONSIDERATION_RULING("执行复议裁定"),
    EXECUTION_SUPERVISION("执行监督"),
    EXECUTION_COMPROMISE("执行和解"),
    EXECUTION_POSTPONE("执行中止"),
    EXECUTION_RESUME("执行恢复"),
    EXECUTION_TRANSFER("执行移送"),
    
    // 执行救济程序
    EXECUTION_REMEDY_APPLICATION("执行救济申请"),
    EXECUTION_REMEDY_REVIEW("执行救济审查"),
    EXECUTION_REMEDY_RULING("执行救济裁定"),
    
    // 国家赔偿案件阶段
    COMPENSATION_APPLICATION("赔偿申请"),
    COMPENSATION_REVIEW("赔偿审查"),
    COMPENSATION_HEARING("赔偿审理"),
    COMPENSATION_DECISION("赔偿决定"),
    COMPENSATION_EXECUTION("赔偿执行"),
    
    // 调解案件阶段
    MEDIATION_APPLICATION("调解申请"),
    MEDIATION_ACCEPTANCE("调解受理"),
    MEDIATION_PROCESS("调解过程"),
    MEDIATION_AGREEMENT("调解协议"),
    MEDIATION_CONFIRMATION("调解确认"),
    
    // 通用阶段
    CASE_FILING("立案"),
    CASE_ACCEPTANCE("受理"),
    CASE_HEARING("审理"),
    CASE_JUDGMENT("判决"),
    CASE_RULING("裁定"),
    CASE_MEDIATION("调解"),
    CASE_SETTLEMENT("和解"),
    CASE_WITHDRAWAL("撤诉"),
    CASE_CONCLUSION("结案"),
    CASE_TERMINATION("终结"),
    CASE_SUSPENSION("中止"),
    CASE_RESUMPTION("恢复"),
    CASE_APPEAL("上诉"),
    CASE_PROTEST("抗诉"),
    CASE_RETRIAL("再审"),
    CASE_EXECUTION("执行");

    private final String description;

    CaseStageEnum(String description) {
        this.description = description;
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
     * 获取案件类型对应的所有可能阶段
     * @param caseType 案件类型
     * @return 该类型案件的所有可能阶段
     */
    public static CaseStageEnum[] getStagesByType(CaseTypeEnum caseType) {
        switch (caseType) {
            case CIVIL:
                return new CaseStageEnum[]{
                    CASE_FILING, CASE_ACCEPTANCE, CASE_HEARING,
                    CIVIL_FIRST_INSTANCE, CIVIL_SECOND_INSTANCE, CIVIL_RETRIAL,
                    CIVIL_SETTLEMENT, CIVIL_WITHDRAWAL, CIVIL_EXECUTION,
                    CIVIL_APPEAL, CIVIL_PROTEST,
                    CASE_MEDIATION, CASE_SETTLEMENT
                };
            case CRIMINAL:
                return new CaseStageEnum[]{
                    CRIMINAL_CASE_FILING, CRIMINAL_INVESTIGATION, CRIMINAL_DETENTION,
                    CRIMINAL_BAIL, CRIMINAL_RESIDENTIAL_SURVEILLANCE,
                    CRIMINAL_ARREST_REVIEW, CRIMINAL_ARREST,
                    CRIMINAL_SUPPLEMENTARY_INVESTIGATION,
                    CRIMINAL_PROSECUTION_REVIEW, CRIMINAL_PROSECUTION,
                    CRIMINAL_NON_PROSECUTION, CRIMINAL_CONDITIONAL_NON_PROSECUTION,
                    CRIMINAL_FIRST_INSTANCE_PREPARATION, CRIMINAL_FIRST_INSTANCE,
                    CRIMINAL_FIRST_INSTANCE_SUMMARY, CRIMINAL_FIRST_INSTANCE_JUDGMENT,
                    CRIMINAL_APPEAL_PROSECUTION, CRIMINAL_APPEAL_DEFENDANT,
                    CRIMINAL_SECOND_INSTANCE, CRIMINAL_SECOND_INSTANCE_JUDGMENT,
                    CRIMINAL_PROTEST, CRIMINAL_RETRIAL,
                    CRIMINAL_DEATH_PENALTY_REVIEW,
                    CRIMINAL_EXECUTION_PREPARATION, CRIMINAL_EXECUTION,
                    CRIMINAL_PAROLE, CRIMINAL_COMMUTATION,
                    CRIMINAL_TEMPORARY_SERVE_OUTSIDE, CRIMINAL_MEDICAL_PAROLE
                };
            case ADMINISTRATIVE:
                return new CaseStageEnum[]{
                    CASE_FILING, CASE_ACCEPTANCE,
                    ADMINISTRATIVE_RECONSIDERATION, ADMINISTRATIVE_FIRST_INSTANCE,
                    ADMINISTRATIVE_SECOND_INSTANCE, ADMINISTRATIVE_RETRIAL,
                    ADMINISTRATIVE_EXECUTION, ADMINISTRATIVE_SETTLEMENT,
                    ADMINISTRATIVE_WITHDRAWAL, ADMINISTRATIVE_APPEAL,
                    ADMINISTRATIVE_PROTEST,
                    COMPENSATION_APPLICATION, COMPENSATION_REVIEW,
                    COMPENSATION_HEARING, COMPENSATION_DECISION,
                    COMPENSATION_EXECUTION
                };
            case ARBITRATION:
                return new CaseStageEnum[]{
                    ARBITRATION_APPLICATION, ARBITRATION_ACCEPTANCE,
                    ARBITRATION_HEARING, ARBITRATION_MEDIATION,
                    ARBITRATION_AWARD, ARBITRATION_EXECUTION
                };
            case NON_LITIGATION:
                return new CaseStageEnum[]{
                    NON_LITIGATION_APPLICATION, NON_LITIGATION_REVIEW,
                    NON_LITIGATION_HEARING, NON_LITIGATION_DECISION,
                    NON_LITIGATION_EXECUTION,
                    MEDIATION_APPLICATION, MEDIATION_ACCEPTANCE,
                    MEDIATION_PROCESS, MEDIATION_AGREEMENT,
                    MEDIATION_CONFIRMATION
                };
            case ENFORCEMENT:
                return new CaseStageEnum[]{
                    EXECUTION_APPLICATION, EXECUTION_ACCEPTANCE,
                    EXECUTION_IMPLEMENTATION, EXECUTION_CONCLUSION,
                    EXECUTION_TERMINATION, EXECUTION_OBJECTION,
                    EXECUTION_OBJECTION_REVIEW, EXECUTION_OBJECTION_RULING,
                    EXECUTION_RECONSIDERATION, EXECUTION_RECONSIDERATION_REVIEW,
                    EXECUTION_RECONSIDERATION_RULING, EXECUTION_SUPERVISION,
                    EXECUTION_COMPROMISE, EXECUTION_POSTPONE,
                    EXECUTION_RESUME, EXECUTION_TRANSFER,
                    EXECUTION_REMEDY_APPLICATION, EXECUTION_REMEDY_REVIEW,
                    EXECUTION_REMEDY_RULING
                };
            default:
                return new CaseStageEnum[]{
                    CASE_FILING, CASE_ACCEPTANCE, CASE_HEARING,
                    CASE_JUDGMENT, CASE_RULING, CASE_MEDIATION,
                    CASE_SETTLEMENT, CASE_WITHDRAWAL, CASE_CONCLUSION,
                    CASE_TERMINATION, CASE_SUSPENSION, CASE_RESUMPTION,
                    CASE_APPEAL, CASE_PROTEST, CASE_RETRIAL, CASE_EXECUTION
                };
        }
    }
    
    /**
     * 判断当前阶段是否可以转换到目标阶段
     * @param targetStage 目标阶段
     * @return 是否可以转换
     */
    public boolean canTransitionTo(CaseStageEnum targetStage) {
        // TODO: 实现阶段转换的业务规则
        // 例如：一审->二审，二审->再审等
        return true;
    }
} 