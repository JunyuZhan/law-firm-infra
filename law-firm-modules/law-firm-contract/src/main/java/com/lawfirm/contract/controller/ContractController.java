package com.lawfirm.contract.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.core.domain.R;
import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 合同管理控制器
 */
@Tag(name = "合同管理")
@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "创建合同")
    @PostMapping
    public R<Long> createContract(@Validated @RequestBody Contract contract) {
        return R.ok(contractService.createContract(contract));
    }

    @Operation(summary = "更新合同")
    @PutMapping("/{id}")
    public R<Void> updateContract(@Parameter(description = "合同ID") @PathVariable Long id,
                                @Validated @RequestBody Contract contract) {
        contract.setId(id);
        contractService.updateContract(contract);
        return R.ok();
    }

    @Operation(summary = "删除合同")
    @DeleteMapping("/{id}")
    public R<Void> deleteContract(@Parameter(description = "合同ID") @PathVariable Long id) {
        contractService.deleteContract(id);
        return R.ok();
    }

    @Operation(summary = "获取合同详情")
    @GetMapping("/{id}")
    public R<Contract> getContractDetail(@Parameter(description = "合同ID") @PathVariable Long id) {
        return R.ok(contractService.getContractDetail(id));
    }

    @Operation(summary = "分页查询合同列表")
    @GetMapping("/page")
    public R<IPage<Contract>> pageContracts(@Parameter(description = "页码") @RequestParam(defaultValue = "1") int page,
                                          @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size,
                                          @Parameter(description = "合同类型") @RequestParam(required = false) Integer type,
                                          @Parameter(description = "合同状态") @RequestParam(required = false) Integer status,
                                          @Parameter(description = "关键字") @RequestParam(required = false) String keyword) {
        return R.ok(contractService.pageContracts(page, size, type, status, keyword));
    }

    @Operation(summary = "提交合同审批")
    @PostMapping("/{id}/submit")
    public R<Void> submitApproval(@Parameter(description = "合同ID") @PathVariable Long id) {
        contractService.submitApproval(id);
        return R.ok();
    }

    @Operation(summary = "撤回合同审批")
    @PostMapping("/{id}/withdraw")
    public R<Void> withdrawApproval(@Parameter(description = "合同ID") @PathVariable Long id) {
        contractService.withdrawApproval(id);
        return R.ok();
    }

    @Operation(summary = "终止合同")
    @PostMapping("/{id}/terminate")
    public R<Void> terminateContract(@Parameter(description = "合同ID") @PathVariable Long id,
                                   @Parameter(description = "终止原因") @RequestParam String reason) {
        contractService.terminateContract(id, reason);
        return R.ok();
    }
} 