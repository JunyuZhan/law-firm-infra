package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.cases.dto.business.CaseApprovalDTO;
import com.lawfirm.model.cases.service.business.CaseApprovalService;
import com.lawfirm.model.cases.vo.business.CaseApprovalVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lawfirm.cases.process.definition.CaseApprovalDefinition;
import com.lawfirm.cases.service.data.CaseApprovalDataService;
import com.lawfirm.model.cases.entity.business.CaseApproval;
import com.lawfirm.model.cases.entity.business.CaseApprovalFlow;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 案件审批服务实现类
 */
@Slf4j
@Service("casesApprovalService")
public class CaseApprovalServiceImpl implements CaseApprovalService {

    @Autowired
    private CaseApprovalDataService caseApprovalDataService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long initiateApproval(CaseApprovalDTO approvalDTO) {
        log.info("发起审批: {}", approvalDTO);
        if (approvalDTO.getCaseId() == null || approvalDTO.getApprovalType() == null) {
            throw new IllegalArgumentException("案件ID和审批类型不能为空");
        }
        // 设置第一个审批人
        List<String> roles = CaseApprovalDefinition.getApprovalRoles(String.valueOf(approvalDTO.getApprovalType()));
        approvalDTO.setCurrentApproverName(roles.get(0));
        approvalDTO.setApprovalStatus(1); // 1-待审批
        approvalDTO.setStartTime(LocalDateTime.now());
        approvalDTO.setDeadline(LocalDateTime.now().plusDays(3));
        // 构造主表实体
        CaseApproval approval = new CaseApproval();
        approval.setCaseId(approvalDTO.getCaseId());
        approval.setApprovalType(String.valueOf(approvalDTO.getApprovalType()));
        approval.setApprovalTitle(approvalDTO.getApprovalTitle());
        approval.setApprovalStatus(1);
        approval.setInitiatorId(approvalDTO.getInitiatorId());
        approval.setInitiatorName(approvalDTO.getInitiatorName());
        approval.setCurrentApproverId(approvalDTO.getCurrentApproverId());
        approval.setCurrentApproverName(approvalDTO.getCurrentApproverName());
        approval.setStartTime(approvalDTO.getStartTime());
        approval.setDeadline(approvalDTO.getDeadline());
        approval.setRemarks(approvalDTO.getRemarks());
        approval.setCreateTime(LocalDateTime.now());
        approval.setUpdateTime(LocalDateTime.now());
        Long approvalId = caseApprovalDataService.createApproval(approval);
        // 写入首个流转记录
        CaseApprovalFlow flow = new CaseApprovalFlow();
        flow.setApprovalId(approvalId);
        flow.setApproverId(approvalDTO.getCurrentApproverId());
        flow.setApproverName(approvalDTO.getCurrentApproverName());
        flow.setApprovalStatus(1);
        flow.setApprovalOpinion("发起审批");
        flow.setApprovalTime(LocalDateTime.now());
        flow.setNodeOrder(1);
        flow.setRemarks(approvalDTO.getRemarks());
        flow.setCreateTime(LocalDateTime.now());
        caseApprovalDataService.createApprovalFlow(flow);
        log.info("审批流程已创建，当前审批人角色: {}", roles.get(0));
        return approvalId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateApproval(CaseApprovalDTO approvalDTO) {
        log.info("更新审批: {}", approvalDTO);
        // TODO: 实现审批更新逻辑
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelApproval(Long approvalId, String reason) {
        log.info("取消审批: approvalId={}, reason={}", approvalId, reason);
        // TODO: 实现取消审批逻辑，更新状态为已取消
        return true;
    }

    @Override
    public CaseApprovalVO getApprovalDetail(Long approvalId) {
        log.info("获取审批详情: approvalId={}", approvalId);
        CaseApproval approval = caseApprovalDataService.getApprovalById(approvalId);
        if (approval == null) return null;
        CaseApprovalVO vo = new CaseApprovalVO();
        vo.setCaseId(approval.getCaseId());
        vo.setApprovalTitle(approval.getApprovalTitle());
        // 类型转换，若VO为Integer，主表为String
        try {
            vo.setApprovalType(approval.getApprovalType() != null ? Integer.valueOf(approval.getApprovalType()) : null);
        } catch (Exception e) {
            vo.setApprovalType(null);
        }
        vo.setApprovalStatus(approval.getApprovalStatus());
        vo.setInitiatorId(approval.getInitiatorId());
        vo.setInitiatorName(approval.getInitiatorName());
        vo.setCurrentApproverId(approval.getCurrentApproverId());
        vo.setCurrentApproverName(approval.getCurrentApproverName());
        vo.setStartTime(approval.getStartTime());
        vo.setDeadline(approval.getDeadline());
        vo.setCompletionTime(approval.getCompletionTime());
        vo.setRemarks(approval.getRemarks());
        return vo;
    }

    @Override
    public List<CaseApprovalVO> listCaseApprovals(Long caseId) {
        log.info("获取案件所有审批: caseId={}", caseId);
        // TODO: 查询案件所有审批
        return new ArrayList<>();
    }

    @Override
    public IPage<CaseApprovalVO> pageApprovals(Long caseId, Integer approvalType, Integer approvalStatus, Integer pageNum, Integer pageSize) {
        log.info("分页查询审批: caseId={}, approvalType={}, approvalStatus={}, pageNum={}, pageSize={}", caseId, approvalType, approvalStatus, pageNum, pageSize);
        // TODO: 分页查询审批
        return new Page<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approve(Long approvalId, boolean approved, String opinion) {
        log.info("审批操作: approvalId={}, approved={}, opinion={}", approvalId, approved, opinion);
        CaseApproval approval = caseApprovalDataService.getApprovalById(approvalId);
        if (approval == null) {
            throw new IllegalArgumentException("审批记录不存在");
        }
        // 获取当前审批人角色
        String approvalType = approval.getApprovalType();
        String currentRole = approval.getCurrentApproverName();
        List<String> roles = CaseApprovalDefinition.getApprovalRoles(approvalType);
        int currentOrder = roles.indexOf(currentRole);
        // 写入流转记录
        CaseApprovalFlow flow = new CaseApprovalFlow();
        flow.setApprovalId(approvalId);
        flow.setApproverId(approval.getCurrentApproverId());
        flow.setApproverName(currentRole);
        flow.setApprovalStatus(approved ? 2 : 3); // 2-通过 3-拒绝
        flow.setApprovalOpinion(opinion);
        flow.setApprovalTime(LocalDateTime.now());
        flow.setNodeOrder(currentOrder + 1);
        flow.setCreateTime(LocalDateTime.now());
        caseApprovalDataService.createApprovalFlow(flow);
        // 判断是否为最后一级
        boolean isFinal = CaseApprovalDefinition.isFinalApprover(approvalType, currentRole);
        if (approved && !isFinal) {
            // 流转到下一级
            String nextRole = CaseApprovalDefinition.getNextApprovalRole(approvalType, currentRole);
            approval.setCurrentApproverName(nextRole);
            approval.setApprovalStatus(1); // 1-待审批
            approval.setUpdateTime(LocalDateTime.now());
            caseApprovalDataService.updateApproval(approval);
            log.info("审批流转到下一级: {}", nextRole);
        } else {
            // 审批结束
            approval.setApprovalStatus(approved ? 2 : 3); // 2-通过 3-拒绝
            approval.setCompletionTime(LocalDateTime.now());
            approval.setUpdateTime(LocalDateTime.now());
            caseApprovalDataService.updateApproval(approval);
            log.info("审批流程结束，状态: {}", approved ? "通过" : "拒绝");
            // TODO: 可在此处联动案件主表状态变更
        }
        return true;
    }

    @Override
    public boolean transferApproval(Long approvalId, Long targetUserId, String reason) {
        log.info("转交审批: approvalId={}, targetUserId={}, reason={}", approvalId, targetUserId, reason);
        // TODO: 实现转交审批逻辑
        return true;
    }

    @Override
    public boolean addApprover(Long approvalId, Long addedUserId, Integer type) {
        log.info("加签: approvalId={}, addedUserId={}, type={}", approvalId, addedUserId, type);
        // TODO: 实现加签逻辑
        return true;
    }

    @Override
    public boolean removeApprover(Long approvalId, Long removedUserId, String reason) {
        log.info("减签: approvalId={}, removedUserId={}, reason={}", approvalId, removedUserId, reason);
        // TODO: 实现减签逻辑
        return true;
    }

    @Override
    public boolean urgeApproval(Long approvalId) {
        log.info("催办审批: approvalId={}", approvalId);
        // TODO: 实现催办逻辑
        return true;
    }

    @Override
    public List<CaseApprovalVO> listPendingApprovals(Long userId) {
        log.info("获取待办审批: userId={}", userId);
        // TODO: 查询当前用户待办审批
        return new ArrayList<>();
    }

    @Override
    public List<CaseApprovalVO> listHandledApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取已办审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        // TODO: 查询当前用户已办审批
        return new ArrayList<>();
    }

    @Override
    public List<CaseApprovalVO> listInitiatedApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取我发起的审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        // TODO: 查询当前用户发起的审批
        return new ArrayList<>();
    }

    @Override
    public List<CaseApprovalVO> getApprovalFlowRecords(Long approvalId) {
        log.info("获取审批流程记录: approvalId={}", approvalId);
        List<CaseApprovalFlow> flows = caseApprovalDataService.listFlowsByApprovalId(approvalId);
        List<CaseApprovalVO> voList = new ArrayList<>();
        for (CaseApprovalFlow flow : flows) {
            CaseApprovalVO vo = new CaseApprovalVO();
            vo.setCaseId(flow.getApprovalId()); // 用caseId字段临时存储审批ID，或可扩展VO
            vo.setCurrentApproverId(flow.getApproverId());
            vo.setCurrentApproverName(flow.getApproverName());
            vo.setApprovalStatus(flow.getApprovalStatus());
            vo.setApprovalOpinion(flow.getApprovalOpinion());
            vo.setCompletionTime(flow.getApprovalTime());
            vo.setRemarks(flow.getRemarks());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public boolean checkApprovalExists(Long approvalId) {
        log.info("检查审批是否存在: approvalId={}", approvalId);
        // TODO: 检查审批是否存在
        return true;
    }

    @Override
    public int countApprovals(Long caseId, Integer approvalType, Integer approvalStatus) {
        log.info("统计案件审批数量: caseId={}, approvalType={}, approvalStatus={}", caseId, approvalType, approvalStatus);
        // TODO: 统计审批数量
        return 0;
    }
} 