package com.lawfirm.cases.controller;

import com.lawfirm.cases.service.CaseContractService;
import com.lawfirm.model.cases.dto.business.CaseContractDTO;
import com.lawfirm.model.cases.vo.business.CaseContractVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件合同控制器
 */
@Tag(name = "案件合同管理", description = "提供案件合同相关的API接口")
@RestController("caseContractController")
@RequestMapping("/api/v1/cases/{caseId}/contracts")
@RequiredArgsConstructor
public class CaseContractController {

    private final CaseContractService caseContractService;

    @Operation(summary = "创建案件合同")
    @PostMapping
    public ResponseEntity<Long> createContract(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同信息") @RequestBody CaseContractDTO contractDTO) {
        Long contractId = caseContractService.createCaseContract(caseId, contractDTO);
        return ResponseEntity.ok(contractId);
    }

    @Operation(summary = "更新案件合同")
    @PutMapping("/{contractId}")
    public ResponseEntity<Boolean> updateContract(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId,
            @Parameter(description = "合同信息") @RequestBody CaseContractDTO contractDTO) {
        boolean success = caseContractService.updateCaseContract(caseId, contractId, contractDTO);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "删除案件合同")
    @DeleteMapping("/{contractId}")
    public ResponseEntity<Boolean> deleteContract(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        boolean success = caseContractService.deleteCaseContract(caseId, contractId);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "获取案件合同详情")
    @GetMapping("/{contractId}")
    public ResponseEntity<CaseContractVO> getContractDetail(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        CaseContractVO contract = caseContractService.getCaseContractDetail(caseId, contractId);
        return ResponseEntity.ok(contract);
    }

    @Operation(summary = "获取案件的所有合同")
    @GetMapping
    public ResponseEntity<List<CaseContractVO>> listContracts(
            @Parameter(description = "案件ID") @PathVariable Long caseId) {
        List<CaseContractVO> contracts = caseContractService.listCaseContracts(caseId);
        return ResponseEntity.ok(contracts);
    }

    @Operation(summary = "签署案件合同")
    @PostMapping("/{contractId}/sign")
    public ResponseEntity<Boolean> signContract(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId,
            @Parameter(description = "签署人ID") @RequestParam Long signerId,
            @Parameter(description = "签名数据") @RequestParam String signatureData) {
        boolean success = caseContractService.signCaseContract(caseId, contractId, signerId, signatureData);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "审核案件合同")
    @PostMapping("/{contractId}/review")
    public ResponseEntity<Boolean> reviewContract(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId,
            @Parameter(description = "是否通过") @RequestParam boolean approved,
            @Parameter(description = "审核意见") @RequestParam String opinion) {
        boolean success = caseContractService.reviewCaseContract(caseId, contractId, approved, opinion);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "检查案件合同状态")
    @GetMapping("/status")
    public ResponseEntity<Boolean> checkContractStatus(
            @Parameter(description = "案件ID") @PathVariable Long caseId) {
        boolean isValid = caseContractService.checkCaseContractStatus(caseId);
        return ResponseEntity.ok(isValid);
    }
} 