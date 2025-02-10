package com.lawfirm.cases.service.impl;

import com.lawfirm.cases.constant.CaseStatusEnum;
import com.lawfirm.cases.model.dto.CaseApprovalDTO;
import com.lawfirm.cases.model.dto.CaseClosureDTO;
import com.lawfirm.cases.model.entity.CaseInfo;
import com.lawfirm.cases.model.entity.CaseStatusLog;
import com.lawfirm.cases.service.CaseLifecycleService;
import com.lawfirm.cases.util.CaseStatusTransitionUtil;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 案件生命周期服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CaseLifecycleServiceImpl implements CaseLifecycleService {

    private final CaseInfoMapper caseInfoMapper;
    private final CaseApprovalMapper caseApprovalMapper;
    private final CaseClosureMapper caseClosureMapper;
    private final CaseStatusLogMapper caseStatusLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fileCaseApproval(Long caseId, CaseApprovalDTO approvalDTO) {
        // 获取案件信息
        CaseInfo caseInfo = getCaseInfo(caseId);
        
        // 检查状态流转
        CaseStatusEnum targetStatus = approvalDTO.getApproved() ? 
            CaseStatusEnum.FILED : CaseStatusEnum.REJECTED;
        checkStatusTransition(caseInfo.getCaseStatus(), targetStatus);
        
        // 保存审批记录
        CaseApproval approval = new CaseApproval();
        approval.setCaseId(caseId);
        approval.setApproverId(approvalDTO.getApproverId());
        approval.setApproverName(approvalDTO.getApproverName());
        approval.setApprovalOpinion(approvalDTO.getApprovalOpinion());
        approval.setApproved(approvalDTO.getApproved());
        approval.setApprovalTime(approvalDTO.getApprovalTime());
        approval.setRemarks(approvalDTO.getRemarks());
        caseApprovalMapper.insert(approval);
        
        // 更新案件状态
        updateCaseStatus(caseInfo, targetStatus, approvalDTO.getApprovalOpinion());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startProcessing(Long caseId) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.PROCESSING, "开始办理案件");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startPreparing(Long caseId) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.PREPARING, "开始准备案件");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startCourt(Long caseId) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.IN_COURT, "开始开庭");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startMediation(Long caseId) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.MEDIATION, "开始调解");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordJudgment(Long caseId, String judgmentResult) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.JUDGMENT, "记录判决结果：" + judgmentResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startExecution(Long caseId) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.EXECUTING, "开始执行");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeExecution(Long caseId) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.EXECUTED, "完成执行");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeCase(Long caseId, CaseClosureDTO closureDTO) {
        // 获取案件信息
        CaseInfo caseInfo = getCaseInfo(caseId);
        
        // 检查状态流转
        checkStatusTransition(caseInfo.getCaseStatus(), CaseStatusEnum.CLOSED);
        
        // 保存结案记录
        CaseClosure closure = new CaseClosure();
        closure.setCaseId(caseId);
        closure.setCloserId(closureDTO.getCloserId());
        closure.setCloserName(closureDTO.getCloserName());
        closure.setClosureTime(closureDTO.getClosureTime());
        closure.setClosureMethod(closureDTO.getClosureMethod());
        closure.setClosureReason(closureDTO.getClosureReason());
        closure.setActualFee(closureDTO.getActualFee());
        closure.setSummary(closureDTO.getSummary());
        closure.setRemarks(closureDTO.getRemarks());
        caseClosureMapper.insert(closure);
        
        // 更新案件状态和结案时间
        caseInfo.setActualEndTime(closureDTO.getClosureTime());
        updateCaseStatus(caseInfo, CaseStatusEnum.CLOSED, closureDTO.getClosureReason());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspendCase(Long caseId, String reason) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.SUSPENDED, reason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateCase(Long caseId, String reason) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.TERMINATED, reason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelCase(Long caseId, String reason) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        updateCaseStatus(caseInfo, CaseStatusEnum.CANCELLED, reason);
    }

    @Override
    public CaseStatusEnum getCurrentStatus(Long caseId) {
        CaseInfo caseInfo = getCaseInfo(caseId);
        return CaseStatusEnum.valueOf(caseInfo.getCaseStatus());
    }

    @Override
    public boolean isValidStatusTransition(CaseStatusEnum fromStatus, CaseStatusEnum toStatus) {
        return CaseStatusTransitionUtil.isValidTransition(fromStatus, toStatus);
    }

    /**
     * 获取案件信息
     */
    private CaseInfo getCaseInfo(Long caseId) {
        CaseInfo caseInfo = caseInfoMapper.selectById(caseId);
        if (caseInfo == null) {
            throw new BusinessException("案件不存在");
        }
        return caseInfo;
    }

    /**
     * 检查状态流转是否合法
     */
    private void checkStatusTransition(String currentStatus, CaseStatusEnum targetStatus) {
        CaseStatusEnum fromStatus = CaseStatusEnum.valueOf(currentStatus);
        if (!isValidStatusTransition(fromStatus, targetStatus)) {
            throw new BusinessException(String.format("不允许从%s状态流转到%s状态", 
                fromStatus.getDescription(), targetStatus.getDescription()));
        }
    }

    /**
     * 更新案件状态
     */
    private void updateCaseStatus(CaseInfo caseInfo, CaseStatusEnum targetStatus, String reason) {
        // 检查状态流转
        checkStatusTransition(caseInfo.getCaseStatus(), targetStatus);
        
        // 记录状态变更日志
        CaseStatusLog statusLog = new CaseStatusLog();
        statusLog.setCaseId(caseInfo.getId());
        statusLog.setFromStatus(caseInfo.getCaseStatus());
        statusLog.setToStatus(targetStatus.name());
        statusLog.setOperationTime(LocalDateTime.now());
        statusLog.setReason(reason);
        caseStatusLogMapper.insert(statusLog);
        
        // 更新案件状态
        caseInfo.setCaseStatus(targetStatus.name());
        caseInfoMapper.updateById(caseInfo);
        
        log.info("案件{}状态从{}更新为{}", caseInfo.getId(), 
            statusLog.getFromStatus(), statusLog.getToStatus());
    }
} 