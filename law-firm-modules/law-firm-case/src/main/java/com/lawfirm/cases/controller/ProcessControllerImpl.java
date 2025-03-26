package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseApprovalDTO;
import com.lawfirm.model.cases.service.business.CaseApprovalService;
import com.lawfirm.model.cases.vo.business.CaseApprovalVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件流程管理控制器实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cases/process")
@Tag(name = "案件流程管理", description = "案件流程管理相关接口")
public class ProcessControllerImpl {

    private final CaseApprovalService approvalService;

    public ProcessControllerImpl(@Qualifier("casesApprovalService") CaseApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping("/approvals")
    @Operation(summary = "发起审批")
    public Long initiateApproval(@RequestBody @Validated CaseApprovalDTO approvalDTO) {
        log.info("发起审批: {}", approvalDTO);
        return approvalService.initiateApproval(approvalDTO);
    }

    @PutMapping("/approvals")
    @Operation(summary = "更新审批信息")
    public boolean updateApproval(@RequestBody @Validated CaseApprovalDTO approvalDTO) {
        log.info("更新审批信息: {}", approvalDTO);
        return approvalService.updateApproval(approvalDTO);
    }

    @DeleteMapping("/approvals/{approvalId}")
    @Operation(summary = "取消审批")
    public boolean cancelApproval(
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        log.info("取消审批: approvalId={}, reason={}", approvalId, reason);
        return approvalService.cancelApproval(approvalId, reason);
    }

    @GetMapping("/approvals/{approvalId}")
    @Operation(summary = "获取审批详情")
    public CaseApprovalVO getApprovalDetail(@PathVariable("approvalId") Long approvalId) {
        log.info("获取审批详情: {}", approvalId);
        return approvalService.getApprovalDetail(approvalId);
    }

    @GetMapping("/cases/{caseId}/approvals")
    @Operation(summary = "获取案件的所有审批")
    public List<CaseApprovalVO> listCaseApprovals(@PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有审批: caseId={}", caseId);
        return approvalService.listCaseApprovals(caseId);
    }

    @GetMapping("/cases/{caseId}/approvals/page")
    @Operation(summary = "分页查询审批")
    public IPage<CaseApprovalVO> pageApprovals(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "审批类型") @RequestParam(required = false) Integer approvalType,
            @Parameter(description = "审批状态") @RequestParam(required = false) Integer approvalStatus,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询审批: caseId={}, approvalType={}, approvalStatus={}, pageNum={}, pageSize={}", 
                caseId, approvalType, approvalStatus, pageNum, pageSize);
        return approvalService.pageApprovals(caseId, approvalType, approvalStatus, pageNum, pageSize);
    }

    @PutMapping("/approvals/{approvalId}/approve")
    @Operation(summary = "审批操作")
    public boolean approve(
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "是否通过") @RequestParam boolean approved,
            @Parameter(description = "审批意见") @RequestParam(required = false) String opinion) {
        log.info("审批操作: approvalId={}, approved={}, opinion={}", approvalId, approved, opinion);
        return approvalService.approve(approvalId, approved, opinion);
    }

    @PutMapping("/approvals/{approvalId}/transfer")
    @Operation(summary = "转交审批")
    public boolean transferApproval(
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "目标用户ID") @RequestParam Long targetUserId,
            @Parameter(description = "转交原因") @RequestParam String reason) {
        log.info("转交审批: approvalId={}, targetUserId={}, reason={}", approvalId, targetUserId, reason);
        return approvalService.transferApproval(approvalId, targetUserId, reason);
    }

    @PutMapping("/approvals/{approvalId}/approvers/add")
    @Operation(summary = "加签")
    public boolean addApprover(
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "加签用户ID") @RequestParam Long addedUserId,
            @Parameter(description = "加签类型（1-前加签 2-后加签）") @RequestParam Integer type) {
        log.info("加签: approvalId={}, addedUserId={}, type={}", approvalId, addedUserId, type);
        return approvalService.addApprover(approvalId, addedUserId, type);
    }

    @PutMapping("/approvals/{approvalId}/approvers/remove")
    @Operation(summary = "减签")
    public boolean removeApprover(
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "减签用户ID") @RequestParam Long removedUserId,
            @Parameter(description = "减签原因") @RequestParam String reason) {
        log.info("减签: approvalId={}, removedUserId={}, reason={}", approvalId, removedUserId, reason);
        return approvalService.removeApprover(approvalId, removedUserId, reason);
    }

    @PostMapping("/approvals/{approvalId}/urge")
    @Operation(summary = "催办审批")
    public boolean urgeApproval(@PathVariable("approvalId") Long approvalId) {
        log.info("催办审批: {}", approvalId);
        return approvalService.urgeApproval(approvalId);
    }

    @GetMapping("/approvals/pending")
    @Operation(summary = "获取待办审批")
    public List<CaseApprovalVO> listPendingApprovals(
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        log.info("获取待办审批: userId={}", userId);
        return approvalService.listPendingApprovals(userId);
    }

    @GetMapping("/approvals/handled")
    @Operation(summary = "获取已办审批")
    public List<CaseApprovalVO> listHandledApprovals(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取已办审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return approvalService.listHandledApprovals(userId, startTime, endTime);
    }

    @GetMapping("/approvals/initiated")
    @Operation(summary = "获取我发起的审批")
    public List<CaseApprovalVO> listInitiatedApprovals(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取我发起的审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return approvalService.listInitiatedApprovals(userId, startTime, endTime);
    }

    @GetMapping("/approvals/{approvalId}/flow")
    @Operation(summary = "获取审批流程记录")
    public List<CaseApprovalVO> getApprovalFlowRecords(@PathVariable("approvalId") Long approvalId) {
        log.info("获取审批流程记录: {}", approvalId);
        return approvalService.getApprovalFlowRecords(approvalId);
    }

    @GetMapping("/approvals/{approvalId}/exists")
    @Operation(summary = "检查审批是否存在")
    public boolean checkApprovalExists(@PathVariable("approvalId") Long approvalId) {
        log.info("检查审批是否存在: {}", approvalId);
        return approvalService.checkApprovalExists(approvalId);
    }

    @GetMapping("/cases/{caseId}/approvals/count")
    @Operation(summary = "统计案件审批数量")
    public int countApprovals(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "审批类型") @RequestParam(required = false) Integer approvalType,
            @Parameter(description = "审批状态") @RequestParam(required = false) Integer approvalStatus) {
        log.info("统计案件审批数量: caseId={}, approvalType={}, approvalStatus={}", 
                caseId, approvalType, approvalStatus);
        return approvalService.countApprovals(caseId, approvalType, approvalStatus);
    }
} 