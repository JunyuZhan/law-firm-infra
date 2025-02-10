package com.lawfirm.cases.service;

import com.lawfirm.cases.constant.CaseStatusEnum;
import com.lawfirm.cases.model.dto.CaseApprovalDTO;
import com.lawfirm.cases.model.dto.CaseClosureDTO;

/**
 * 案件生命周期管理服务
 */
public interface CaseLifecycleService {

    /**
     * 案件立案
     *
     * @param caseId 案件ID
     * @param approvalDTO 立案审批信息
     */
    void fileCaseApproval(Long caseId, CaseApprovalDTO approvalDTO);

    /**
     * 开始案件办理
     *
     * @param caseId 案件ID
     */
    void startProcessing(Long caseId);

    /**
     * 开始案件准备
     *
     * @param caseId 案件ID
     */
    void startPreparing(Long caseId);

    /**
     * 开始开庭
     *
     * @param caseId 案件ID
     */
    void startCourt(Long caseId);

    /**
     * 开始调解
     *
     * @param caseId 案件ID
     */
    void startMediation(Long caseId);

    /**
     * 记录判决结果
     *
     * @param caseId 案件ID
     * @param judgmentResult 判决结果
     */
    void recordJudgment(Long caseId, String judgmentResult);

    /**
     * 开始执行
     *
     * @param caseId 案件ID
     */
    void startExecution(Long caseId);

    /**
     * 完成执行
     *
     * @param caseId 案件ID
     */
    void completeExecution(Long caseId);

    /**
     * 案件结案
     *
     * @param caseId 案件ID
     * @param closureDTO 结案信息
     */
    void closeCase(Long caseId, CaseClosureDTO closureDTO);

    /**
     * 暂停案件
     *
     * @param caseId 案件ID
     * @param reason 暂停原因
     */
    void suspendCase(Long caseId, String reason);

    /**
     * 终止案件
     *
     * @param caseId 案件ID
     * @param reason 终止原因
     */
    void terminateCase(Long caseId, String reason);

    /**
     * 撤销案件
     *
     * @param caseId 案件ID
     * @param reason 撤销原因
     */
    void cancelCase(Long caseId, String reason);

    /**
     * 获取案件当前状态
     *
     * @param caseId 案件ID
     * @return 案件状态
     */
    CaseStatusEnum getCurrentStatus(Long caseId);

    /**
     * 检查案件状态迁移是否合法
     *
     * @param fromStatus 当前状态
     * @param toStatus 目标状态
     * @return 是否合法
     */
    boolean isValidStatusTransition(CaseStatusEnum fromStatus, CaseStatusEnum toStatus);
} 