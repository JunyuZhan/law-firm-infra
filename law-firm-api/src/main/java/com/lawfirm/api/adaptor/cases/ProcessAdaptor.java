package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.business.CaseApprovalDTO;
import com.lawfirm.model.cases.service.business.CaseApprovalService;
import com.lawfirm.model.cases.vo.business.CaseApprovalVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件流程适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessAdaptor extends BaseAdaptor {

    private final CaseApprovalService approvalService;

    /**
     * 发起审批
     */
    public Long initiateApproval(CaseApprovalDTO approvalDTO) {
        log.info("发起审批: {}", approvalDTO);
        return approvalService.initiateApproval(approvalDTO);
    }

    /**
     * 更新审批信息
     */
    public boolean updateApproval(CaseApprovalDTO approvalDTO) {
        log.info("更新审批信息: {}", approvalDTO);
        return approvalService.updateApproval(approvalDTO);
    }

    /**
     * 取消审批
     */
    public boolean cancelApproval(Long approvalId, String reason) {
        log.info("取消审批: approvalId={}, reason={}", approvalId, reason);
        return approvalService.cancelApproval(approvalId, reason);
    }

    /**
     * 获取审批详情
     */
    public CaseApprovalVO getApprovalDetail(Long approvalId) {
        log.info("获取审批详情: {}", approvalId);
        return approvalService.getApprovalDetail(approvalId);
    }

    /**
     * 获取案件的所有审批
     */
    public List<CaseApprovalVO> listCaseApprovals(Long caseId) {
        log.info("获取案件的所有审批: caseId={}", caseId);
        return approvalService.listCaseApprovals(caseId);
    }

    /**
     * 分页查询审批
     */
    public IPage<CaseApprovalVO> pageApprovals(Long caseId, Integer approvalType, Integer approvalStatus, Integer pageNum, Integer pageSize) {
        log.info("分页查询审批: caseId={}, approvalType={}, approvalStatus={}, pageNum={}, pageSize={}", 
                caseId, approvalType, approvalStatus, pageNum, pageSize);
        return approvalService.pageApprovals(caseId, approvalType, approvalStatus, pageNum, pageSize);
    }

    /**
     * 审批操作
     */
    public boolean approve(Long approvalId, boolean approved, String opinion) {
        log.info("审批操作: approvalId={}, approved={}, opinion={}", approvalId, approved, opinion);
        return approvalService.approve(approvalId, approved, opinion);
    }

    /**
     * 转交审批
     */
    public boolean transferApproval(Long approvalId, Long targetUserId, String reason) {
        log.info("转交审批: approvalId={}, targetUserId={}, reason={}", approvalId, targetUserId, reason);
        return approvalService.transferApproval(approvalId, targetUserId, reason);
    }

    /**
     * 加签
     */
    public boolean addApprover(Long approvalId, Long addedUserId, Integer type) {
        log.info("加签: approvalId={}, addedUserId={}, type={}", approvalId, addedUserId, type);
        return approvalService.addApprover(approvalId, addedUserId, type);
    }

    /**
     * 减签
     */
    public boolean removeApprover(Long approvalId, Long removedUserId, String reason) {
        log.info("减签: approvalId={}, removedUserId={}, reason={}", approvalId, removedUserId, reason);
        return approvalService.removeApprover(approvalId, removedUserId, reason);
    }

    /**
     * 催办审批
     */
    public boolean urgeApproval(Long approvalId) {
        log.info("催办审批: {}", approvalId);
        return approvalService.urgeApproval(approvalId);
    }

    /**
     * 获取待办审批
     */
    public List<CaseApprovalVO> listPendingApprovals(Long userId) {
        log.info("获取待办审批: userId={}", userId);
        return approvalService.listPendingApprovals(userId);
    }

    /**
     * 获取已办审批
     */
    public List<CaseApprovalVO> listHandledApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取已办审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return approvalService.listHandledApprovals(userId, startTime, endTime);
    }

    /**
     * 获取我发起的审批
     */
    public List<CaseApprovalVO> listInitiatedApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取我发起的审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return approvalService.listInitiatedApprovals(userId, startTime, endTime);
    }

    /**
     * 获取审批流程记录
     */
    public List<CaseApprovalVO> getApprovalFlowRecords(Long approvalId) {
        log.info("获取审批流程记录: {}", approvalId);
        return approvalService.getApprovalFlowRecords(approvalId);
    }

    /**
     * 检查审批是否存在
     */
    public boolean checkApprovalExists(Long approvalId) {
        log.info("检查审批是否存在: {}", approvalId);
        return approvalService.checkApprovalExists(approvalId);
    }

    /**
     * 统计案件审批数量
     */
    public int countApprovals(Long caseId, Integer approvalType, Integer approvalStatus) {
        log.info("统计案件审批数量: caseId={}, approvalType={}, approvalStatus={}", 
                caseId, approvalType, approvalStatus);
        return approvalService.countApprovals(caseId, approvalType, approvalStatus);
    }
} 