package com.lawfirm.cases.controller;

import com.lawfirm.cases.constant.CaseBusinessConstants;
import com.lawfirm.cases.service.CaseContractService;
import com.lawfirm.model.cases.dto.business.CaseContractDTO;
import com.lawfirm.model.cases.vo.business.CaseContractVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * 案件合同关联控制器
 */
@Tag(name = "案件合同关联", description = "提供案件与合同关联关系的API接口")
@RestController("caseContractController")
@RequestMapping(CaseBusinessConstants.Controller.API_PREFIX + "/{caseId}/contracts")
@RequiredArgsConstructor
public class CaseContractController {

    private final CaseContractService caseContractService;

    @Operation(summary = "关联合同到案件")
    @PostMapping("/associate/{contractId}")
    @PreAuthorize("hasAuthority('" + CONTRACT_EDIT + "')")
    public ResponseEntity<Boolean> associateContract(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        boolean success = caseContractService.associateContractWithCase(caseId, contractId);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "取消关联合同")
    @DeleteMapping("/disassociate/{contractId}")
    @PreAuthorize("hasAuthority('" + CONTRACT_EDIT + "')")
    public ResponseEntity<Boolean> disassociateContract(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        boolean success = caseContractService.disassociateContractFromCase(caseId, contractId);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "获取案件关联的合同详情")
    @GetMapping("/{contractId}")
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    public ResponseEntity<CaseContractVO> getContractDetail(
            @Parameter(description = "案件ID") @PathVariable Long caseId,
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        CaseContractVO contract = caseContractService.getCaseContractDetail(caseId, contractId);
        return ResponseEntity.ok(contract);
    }

    @Operation(summary = "获取案件的所有关联合同")
    @GetMapping
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    public ResponseEntity<List<CaseContractVO>> listContracts(
            @Parameter(description = "案件ID") @PathVariable Long caseId) {
        List<CaseContractVO> contracts = caseContractService.listCaseContracts(caseId);
        return ResponseEntity.ok(contracts);
    }

    @Operation(summary = "检查案件是否有有效合同")
    @GetMapping("/status")
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    public ResponseEntity<Boolean> checkContractStatus(
            @Parameter(description = "案件ID") @PathVariable Long caseId) {
        boolean isValid = caseContractService.checkCaseContractStatus(caseId);
        return ResponseEntity.ok(isValid);
    }
} 