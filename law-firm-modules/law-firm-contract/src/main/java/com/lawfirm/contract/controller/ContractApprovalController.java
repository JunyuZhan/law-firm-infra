package com.lawfirm.contract.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.contract.entity.ContractApproval;
import com.lawfirm.contract.service.ContractApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合同审批控制器
 */
@Tag(name = "合同审批管理")
@RestController
@RequestMapping("/contract/approval")
@RequiredArgsConstructor
public class ContractApprovalController {

    private final ContractApprovalService contractApprovalService;

    @Operation(summary = "创建审批记录")
    @PostMapping("/create")
    public R<Void> createApproval(@RequestParam Long contractId,
                                 @RequestParam Integer node,
                                 @RequestParam Long approverId,
                                 @RequestParam String approverName) {
        contractApprovalService.createApproval(contractId, node, approverId, approverName);
        return R.ok();
    }

    @Operation(summary = "审批通过")
    @PostMapping("/approve/{id}")
    public R<Void> approve(@PathVariable Long id, @RequestParam String comment) {
        contractApprovalService.approve(id, comment);
        return R.ok();
    }

    @Operation(summary = "审批驳回")
    @PostMapping("/reject/{id}")
    public R<Void> reject(@PathVariable Long id, @RequestParam String comment) {
        contractApprovalService.reject(id, comment);
        return R.ok();
    }

    @Operation(summary = "获取合同审批记录")
    @GetMapping("/list/{contractId}")
    public R<List<ContractApproval>> getContractApprovals(@PathVariable Long contractId) {
        return R.ok(contractApprovalService.getContractApprovals(contractId));
    }

    @Operation(summary = "获取待审批记录")
    @GetMapping("/pending/{approverId}")
    public R<List<ContractApproval>> getPendingApprovals(@PathVariable Long approverId) {
        return R.ok(contractApprovalService.getPendingApprovals(approverId));
    }
} 