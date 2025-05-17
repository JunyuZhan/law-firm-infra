package com.lawfirm.cases.controller;

import com.lawfirm.model.cases.dto.business.CaseApprovalDTO;
import com.lawfirm.model.cases.service.business.CaseApprovalService;
import com.lawfirm.model.cases.vo.business.CaseApprovalVO;
import com.lawfirm.cases.constant.CaseBusinessConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 审批流控制器
 */
@Slf4j
@Tag(name = "案件审批流", description = "案件审批流接口")
@RestController("caseApprovalController")
@RequestMapping(CaseBusinessConstants.Controller.API_APPROVAL_PREFIX)
@RequiredArgsConstructor
public class CaseApprovalController {

    private final CaseApprovalService caseApprovalService;

    /**
     * 发起审批
     */
    @Operation(summary = "发起审批", description = "发起案件审批流，包括出函审批等多类型")
    @PostMapping("/initiate")
    public Long initiateApproval(
            @Parameter(description = "审批发起参数，包括案件ID、类型、标题、发起人等")
            @RequestBody @Validated CaseApprovalDTO dto) {
        log.info("发起审批: {}", dto);
        return caseApprovalService.initiateApproval(dto);
    }

    /**
     * 审批操作（同意/拒绝）
     */
    @Operation(summary = "审批操作", description = "对审批流进行同意/拒绝操作")
    @PostMapping("/approve")
    public boolean approve(
            @Parameter(description = "审批ID") @RequestParam Long approvalId,
            @Parameter(description = "是否同意") @RequestParam boolean approved,
            @Parameter(description = "审批意见") @RequestParam String opinion) {
        log.info("审批操作: approvalId={}, approved={}, opinion={}", approvalId, approved, opinion);
        return caseApprovalService.approve(approvalId, approved, opinion);
    }

    /**
     * 查询审批详情
     */
    @Operation(summary = "查询审批详情", description = "根据审批ID获取审批主表详情")
    @GetMapping("/detail/{approvalId}")
    public CaseApprovalVO getApprovalDetail(
            @Parameter(description = "审批ID") @PathVariable Long approvalId) {
        log.info("查询审批详情: approvalId={}", approvalId);
        return caseApprovalService.getApprovalDetail(approvalId);
    }

    /**
     * 查询审批流转历史
     */
    @Operation(summary = "查询审批流转历史", description = "根据审批ID获取所有流转节点记录")
    @GetMapping("/flow/{approvalId}")
    public List<CaseApprovalVO> getApprovalFlowRecords(
            @Parameter(description = "审批ID") @PathVariable Long approvalId) {
        log.info("查询审批流转历史: approvalId={}", approvalId);
        return caseApprovalService.getApprovalFlowRecords(approvalId);
    }
} 