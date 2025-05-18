package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.cases.dto.business.CaseApprovalDTO;
import com.lawfirm.model.cases.service.business.CaseApprovalService;
import com.lawfirm.model.cases.service.base.CaseService;
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

    @Autowired(required = false)
    private CaseService caseService;

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
        if (approvalDTO.getId() == null) throw new IllegalArgumentException("审批ID不能为空");
        CaseApproval approval = caseApprovalDataService.getApprovalById(approvalDTO.getId());
        if (approval == null) throw new IllegalArgumentException("审批记录不存在");
        // 只允许更新部分字段
        approval.setApprovalTitle(approvalDTO.getApprovalTitle());
        approval.setApprovalType(approvalDTO.getApprovalType() != null ? approvalDTO.getApprovalType().toString() : null);
        approval.setDeadline(approvalDTO.getDeadline());
        approval.setRemarks(approvalDTO.getRemarks());
        approval.setUpdateTime(java.time.LocalDateTime.now());
        caseApprovalDataService.updateApproval(approval);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelApproval(Long approvalId, String reason) {
        log.info("取消审批: approvalId={}, reason={}", approvalId, reason);
        CaseApproval approval = caseApprovalDataService.getApprovalById(approvalId);
        if (approval == null) throw new IllegalArgumentException("审批记录不存在");
        approval.setApprovalStatus(4); // 4-已取消
        approval.setUpdateTime(java.time.LocalDateTime.now());
        approval.setCompletionTime(java.time.LocalDateTime.now());
        caseApprovalDataService.updateApproval(approval);
        // 写入流转记录
        CaseApprovalFlow flow = new CaseApprovalFlow();
        flow.setApprovalId(approvalId);
        flow.setApproverId(approval.getCurrentApproverId());
        flow.setApproverName(approval.getCurrentApproverName());
        flow.setApprovalStatus(4);
        flow.setApprovalOpinion(reason != null ? reason : "主动取消");
        flow.setApprovalTime(java.time.LocalDateTime.now());
        flow.setNodeOrder(0);
        flow.setCreateTime(java.time.LocalDateTime.now());
        caseApprovalDataService.createApprovalFlow(flow);
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
        List<CaseApproval> approvals = caseApprovalDataService.listApprovalsByCaseId(caseId);
        List<CaseApprovalVO> voList = new ArrayList<>();
        for (CaseApproval approval : approvals) {
            CaseApprovalVO vo = getApprovalDetail(approval.getId());
            if (vo != null) voList.add(vo);
        }
        return voList;
    }

    @Override
    public IPage<CaseApprovalVO> pageApprovals(Long caseId, Integer approvalType, Integer approvalStatus, Integer pageNum, Integer pageSize) {
        log.info("分页查询审批: caseId={}, approvalType={}, approvalStatus={}, pageNum={}, pageSize={}", caseId, approvalType, approvalStatus, pageNum, pageSize);
        Page<CaseApproval> page = new Page<>(pageNum, pageSize);
        IPage<CaseApproval> approvalPage = caseApprovalDataService.pageApprovals(page, caseId, approvalType, approvalStatus);
        Page<CaseApprovalVO> voPage = new Page<>();
        voPage.setCurrent(pageNum);
        voPage.setSize(pageSize);
        voPage.setTotal(approvalPage.getTotal());
        List<CaseApprovalVO> voList = new ArrayList<>();
        for (CaseApproval approval : approvalPage.getRecords()) {
            CaseApprovalVO vo = getApprovalDetail(approval.getId());
            if (vo != null) voList.add(vo);
        }
        voPage.setRecords(voList);
        return voPage;
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
            // 联动案件主表状态变更
            if (caseService != null) {
                Integer targetStatus = approved ? 2 : 3; // 假设2代表已通过，3代表已拒绝
                String reason = approved ? "审批通过" : "审批拒绝";
                caseService.changeStatus(approval.getCaseId(), targetStatus, reason);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferApproval(Long approvalId, Long targetUserId, String reason) {
        log.info("转交审批: approvalId={}, targetUserId={}, reason={}", approvalId, targetUserId, reason);
        CaseApproval approval = caseApprovalDataService.getApprovalById(approvalId);
        if (approval == null) throw new IllegalArgumentException("审批记录不存在");
        // 假设可通过targetUserId查到用户名
        String targetUserName = "用户" + targetUserId; // 实际应查用户表
        approval.setCurrentApproverId(targetUserId);
        approval.setCurrentApproverName(targetUserName);
        approval.setUpdateTime(LocalDateTime.now());
        caseApprovalDataService.updateApproval(approval);
        // 写入流转记录
        CaseApprovalFlow flow = new CaseApprovalFlow();
        flow.setApprovalId(approvalId);
        flow.setApproverId(targetUserId);
        flow.setApproverName(targetUserName);
        flow.setApprovalStatus(1); // 1-待审批
        flow.setApprovalOpinion(reason != null ? reason : "转交审批");
        flow.setApprovalTime(LocalDateTime.now());
        flow.setNodeOrder(0);
        flow.setCreateTime(LocalDateTime.now());
        caseApprovalDataService.createApprovalFlow(flow);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addApprover(Long approvalId, Long addedUserId, Integer type) {
        log.info("加签: approvalId={}, addedUserId={}, type={}", approvalId, addedUserId, type);
        // 这里只做流转记录，实际可扩展审批链
        String addedUserName = "用户" + addedUserId;
        CaseApprovalFlow flow = new CaseApprovalFlow();
        flow.setApprovalId(approvalId);
        flow.setApproverId(addedUserId);
        flow.setApproverName(addedUserName);
        flow.setApprovalStatus(0); // 0-加签
        flow.setApprovalOpinion("加签");
        flow.setApprovalTime(LocalDateTime.now());
        flow.setNodeOrder(type != null ? type : 0);
        flow.setCreateTime(LocalDateTime.now());
        caseApprovalDataService.createApprovalFlow(flow);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeApprover(Long approvalId, Long removedUserId, String reason) {
        log.info("减签: approvalId={}, removedUserId={}, reason={}", approvalId, removedUserId, reason);
        // 这里只做流转记录，实际可扩展审批链
        String removedUserName = "用户" + removedUserId;
        CaseApprovalFlow flow = new CaseApprovalFlow();
        flow.setApprovalId(approvalId);
        flow.setApproverId(removedUserId);
        flow.setApproverName(removedUserName);
        flow.setApprovalStatus(0); // 0-减签
        flow.setApprovalOpinion(reason != null ? reason : "减签");
        flow.setApprovalTime(LocalDateTime.now());
        flow.setNodeOrder(0);
        flow.setCreateTime(LocalDateTime.now());
        caseApprovalDataService.createApprovalFlow(flow);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean urgeApproval(Long approvalId) {
        log.info("催办审批: approvalId={}", approvalId);
        CaseApproval approval = caseApprovalDataService.getApprovalById(approvalId);
        if (approval == null) throw new IllegalArgumentException("审批记录不存在");
        // 写入催办流转记录
        CaseApprovalFlow flow = new CaseApprovalFlow();
        flow.setApprovalId(approvalId);
        flow.setApproverId(approval.getCurrentApproverId());
        flow.setApproverName(approval.getCurrentApproverName());
        flow.setApprovalStatus(5); // 5-催办
        flow.setApprovalOpinion("催办");
        flow.setApprovalTime(LocalDateTime.now());
        flow.setNodeOrder(0);
        flow.setCreateTime(LocalDateTime.now());
        caseApprovalDataService.createApprovalFlow(flow);
        // 可扩展：调用消息服务通知当前审批人
        return true;
    }

    @Override
    public List<CaseApprovalVO> listPendingApprovals(Long userId) {
        log.info("获取待办审批: userId={}", userId);
        // 查询当前用户为当前审批人的审批，且状态为待审批
        List<CaseApproval> approvals = caseApprovalDataService.listApprovalsByCurrentApprover(userId, 1); // 1-待审批
        List<CaseApprovalVO> voList = new ArrayList<>();
        for (CaseApproval approval : approvals) {
            CaseApprovalVO vo = getApprovalDetail(approval.getId());
            if (vo != null) voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<CaseApprovalVO> listHandledApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取已办审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        // 查询当前用户已处理过的审批
        List<CaseApproval> approvals = caseApprovalDataService.listHandledApprovals(userId, startTime, endTime);
        List<CaseApprovalVO> voList = new ArrayList<>();
        for (CaseApproval approval : approvals) {
            CaseApprovalVO vo = getApprovalDetail(approval.getId());
            if (vo != null) voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<CaseApprovalVO> listInitiatedApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取我发起的审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        // 查询当前用户发起的审批
        List<CaseApproval> approvals = caseApprovalDataService.listInitiatedApprovals(userId, startTime, endTime);
        List<CaseApprovalVO> voList = new ArrayList<>();
        for (CaseApproval approval : approvals) {
            CaseApprovalVO vo = getApprovalDetail(approval.getId());
            if (vo != null) voList.add(vo);
        }
        return voList;
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
        CaseApproval approval = caseApprovalDataService.getApprovalById(approvalId);
        return approval != null;
    }

    @Override
    public int countApprovals(Long caseId, Integer approvalType, Integer approvalStatus) {
        log.info("统计案件审批数量: caseId={}, approvalType={}, approvalStatus={}", caseId, approvalType, approvalStatus);
        return caseApprovalDataService.countApprovals(caseId, approvalType, approvalStatus);
    }
} 