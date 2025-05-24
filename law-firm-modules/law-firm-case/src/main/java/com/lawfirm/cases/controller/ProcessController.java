package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.cases.constant.CaseBusinessConstants;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 案件流程控制器
 */
@Slf4j
@RestController("processController")
@RequestMapping(CaseBusinessConstants.Controller.API_PROCESS_PREFIX)
@RequiredArgsConstructor
@Tag(name = "案件流程管理", description = "提供案件流程管理功能，包括流程定义、实例创建、任务处理等功能")
public class ProcessController {

    @Qualifier("casesApprovalService")
    private final CaseApprovalService approvalService;

    @Operation(
        summary = "发起审批",
        description = "发起新的审批流程，包括设置审批类型、审批人、审批内容等信息"
    )
    @PostMapping("/approvals")
    @PreAuthorize(PROCESS_APPROVAL_INITIATE)
    public Long initiateApproval(
            @Parameter(description = "审批信息，包括审批类型、标题、内容、审批人等") 
            @RequestBody @Validated CaseApprovalDTO approvalDTO) {
        log.info("发起审批: {}", approvalDTO);
        return approvalService.initiateApproval(approvalDTO);
    }

    @Operation(
        summary = "更新审批信息",
        description = "更新未开始处理的审批信息，包括审批内容、审批人等"
    )
    @PutMapping("/approvals")
    @PreAuthorize(PROCESS_APPROVAL_EDIT)
    public boolean updateApproval(
            @Parameter(description = "更新的审批信息，包括可修改的审批相关字段") 
            @RequestBody @Validated CaseApprovalDTO approvalDTO) {
        log.info("更新审批信息: {}", approvalDTO);
        return approvalService.updateApproval(approvalDTO);
    }

    @Operation(
        summary = "取消审批",
        description = "取消未完成的审批流程，需要提供取消原因"
    )
    @DeleteMapping("/approvals/{approvalId}")
    @PreAuthorize(PROCESS_APPROVAL_CANCEL)
    public boolean cancelApproval(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "取消原因，说明取消审批的具体原因") 
            @RequestParam String reason) {
        log.info("取消审批: approvalId={}, reason={}", approvalId, reason);
        return approvalService.cancelApproval(approvalId, reason);
    }

    @Operation(
        summary = "获取审批详情",
        description = "获取审批的详细信息，包括审批基本信息、当前审批状态、审批历史等"
    )
    @GetMapping("/approvals/{approvalId}")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public CaseApprovalVO getApprovalDetail(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId) {
        log.info("获取审批详情: {}", approvalId);
        return approvalService.getApprovalDetail(approvalId);
    }

    @Operation(
        summary = "获取案件的所有审批",
        description = "获取指定案件关联的所有审批记录列表"
    )
    @GetMapping("/cases/{caseId}/approvals")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public List<CaseApprovalVO> listCaseApprovals(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有审批: caseId={}", caseId);
        return approvalService.listCaseApprovals(caseId);
    }

    @Operation(
        summary = "分页查询审批",
        description = "分页查询案件的审批记录，支持按审批类型和状态筛选"
    )
    @GetMapping("/cases/{caseId}/approvals/page")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public IPage<CaseApprovalVO> pageApprovals(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "审批类型：1-立案审批，2-结案审批，3-文书审批，4-费用审批") 
            @RequestParam(required = false) Integer approvalType,
            @Parameter(description = "审批状态：1-待审批，2-审批中，3-已通过，4-已拒绝，5-已取消") 
            @RequestParam(required = false) Integer approvalStatus,
            @Parameter(description = "页码，从1开始") 
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页显示记录数") 
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询审批: caseId={}, approvalType={}, approvalStatus={}, pageNum={}, pageSize={}", 
                caseId, approvalType, approvalStatus, pageNum, pageSize);
        return approvalService.pageApprovals(caseId, approvalType, approvalStatus, pageNum, pageSize);
    }

    @Operation(
        summary = "审批操作",
        description = "对审批进行通过或拒绝操作，需要提供审批意见"
    )
    @PutMapping("/approvals/{approvalId}/approve")
    @PreAuthorize(PROCESS_APPROVAL_APPROVE)
    public boolean approve(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "是否通过：true-通过，false-拒绝") 
            @RequestParam boolean approved,
            @Parameter(description = "审批意见，说明通过或拒绝的具体原因") 
            @RequestParam(required = false) String opinion) {
        log.info("审批操作: approvalId={}, approved={}, opinion={}", approvalId, approved, opinion);
        return approvalService.approve(approvalId, approved, opinion);
    }

    @Operation(
        summary = "转交审批",
        description = "将审批转交给其他用户处理，需要提供转交原因"
    )
    @PutMapping("/approvals/{approvalId}/transfer")
    @PreAuthorize(PROCESS_APPROVAL_TRANSFER)
    public boolean transferApproval(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "接收转交的用户ID") 
            @RequestParam Long targetUserId,
            @Parameter(description = "转交原因，说明转交审批的具体原因") 
            @RequestParam String reason) {
        log.info("转交审批: approvalId={}, targetUserId={}, reason={}", approvalId, targetUserId, reason);
        return approvalService.transferApproval(approvalId, targetUserId, reason);
    }

    @Operation(
        summary = "加签",
        description = "在当前审批人前或后添加新的审批人"
    )
    @PutMapping("/approvals/{approvalId}/approvers/add")
    @PreAuthorize(PROCESS_APPROVAL_EDIT)
    public boolean addApprover(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "新增审批人的用户ID") 
            @RequestParam Long addedUserId,
            @Parameter(description = "加签类型：1-前加签（在当前审批人之前），2-后加签（在当前审批人之后）") 
            @RequestParam Integer type) {
        log.info("加签: approvalId={}, addedUserId={}, type={}", approvalId, addedUserId, type);
        return approvalService.addApprover(approvalId, addedUserId, type);
    }

    @Operation(
        summary = "减签",
        description = "移除审批流程中的某个审批人，需要提供减签原因"
    )
    @PutMapping("/approvals/{approvalId}/approvers/remove")
    @PreAuthorize(PROCESS_APPROVAL_EDIT)
    public boolean removeApprover(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId,
            @Parameter(description = "被移除审批人的用户ID") 
            @RequestParam Long removedUserId,
            @Parameter(description = "减签原因，说明移除该审批人的具体原因") 
            @RequestParam String reason) {
        log.info("减签: approvalId={}, removedUserId={}, reason={}", approvalId, removedUserId, reason);
        return approvalService.removeApprover(approvalId, removedUserId, reason);
    }

    @Operation(
        summary = "催办审批",
        description = "对未处理的审批进行催办，系统将通知相关审批人"
    )
    @PostMapping("/approvals/{approvalId}/urge")
    @PreAuthorize(PROCESS_APPROVAL_URGE)
    public boolean urgeApproval(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId) {
        log.info("催办审批: {}", approvalId);
        return approvalService.urgeApproval(approvalId);
    }

    @Operation(
        summary = "获取待办审批",
        description = "获取指定用户待处理的审批列表"
    )
    @GetMapping("/approvals/pending")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public List<CaseApprovalVO> listPendingApprovals(
            @Parameter(description = "用户ID") 
            @RequestParam Long userId) {
        log.info("获取待办审批: userId={}", userId);
        return approvalService.listPendingApprovals(userId);
    }

    @Operation(
        summary = "获取已办审批",
        description = "获取指定用户在指定时间范围内已处理的审批列表"
    )
    @GetMapping("/approvals/handled")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public List<CaseApprovalVO> listHandledApprovals(
            @Parameter(description = "用户ID") 
            @RequestParam Long userId,
            @Parameter(description = "开始时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取已办审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return approvalService.listHandledApprovals(userId, startTime, endTime);
    }

    @Operation(
        summary = "获取我发起的审批",
        description = "获取指定用户在指定时间范围内发起的审批列表"
    )
    @GetMapping("/approvals/initiated")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public List<CaseApprovalVO> listInitiatedApprovals(
            @Parameter(description = "用户ID") 
            @RequestParam Long userId,
            @Parameter(description = "开始时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取我发起的审批: userId={}, startTime={}, endTime={}", userId, startTime, endTime);
        return approvalService.listInitiatedApprovals(userId, startTime, endTime);
    }

    @Operation(
        summary = "获取审批流程记录",
        description = "获取审批的完整处理记录，包括每个审批环节的处理情况"
    )
    @GetMapping("/approvals/{approvalId}/flow")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public List<CaseApprovalVO> getApprovalFlowRecords(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId) {
        log.info("获取审批流程记录: {}", approvalId);
        return approvalService.getApprovalFlowRecords(approvalId);
    }

    @Operation(
        summary = "检查审批是否存在",
        description = "检查指定ID的审批是否存在"
    )
    @GetMapping("/approvals/{approvalId}/exists")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public boolean checkApprovalExists(
            @Parameter(description = "审批ID") 
            @PathVariable("approvalId") Long approvalId) {
        log.info("检查审批是否存在: {}", approvalId);
        return approvalService.checkApprovalExists(approvalId);
    }

    @Operation(
        summary = "统计案件审批数量",
        description = "统计指定案件的审批数量，支持按审批类型和状态统计"
    )
    @GetMapping("/cases/{caseId}/approvals/count")
    @PreAuthorize(PROCESS_APPROVAL_VIEW)
    public int countApprovals(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "审批类型：1-立案审批，2-结案审批，3-文书审批，4-费用审批") 
            @RequestParam(required = false) Integer approvalType,
            @Parameter(description = "审批状态：1-待审批，2-审批中，3-已通过，4-已拒绝，5-已取消") 
            @RequestParam(required = false) Integer approvalStatus) {
        log.info("统计案件审批数量: caseId={}, approvalType={}, approvalStatus={}", 
                caseId, approvalType, approvalStatus);
        return approvalService.countApprovals(caseId, approvalType, approvalStatus);
    }
} 