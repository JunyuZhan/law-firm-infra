package com.lawfirm.contract.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.contract.constant.ContractConstants;
import com.lawfirm.model.contract.dto.ContractConflictCheckDTO;
import com.lawfirm.model.contract.dto.ContractConflictResultDTO;
import com.lawfirm.model.contract.service.ContractConflictService;
import com.lawfirm.model.contract.vo.ContractConflictVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * 合同冲突控制器
 */
@Tag(name = "合同冲突管理", description = "合同冲突检查、查询和解决")
@RestController("contractConflictController")
@RequestMapping(ContractConstants.API_CONFLICT_PREFIX)
@RequiredArgsConstructor
public class ContractConflictController {

    private final ContractConflictService conflictService;

    @Operation(summary = "检查合同冲突")
    @PostMapping("/check")
    @PreAuthorize(CONTRACT_VIEW)
    public CommonResult<ContractConflictResultDTO> checkConflict(
            @Parameter(description = "冲突检查参数") @Validated @RequestBody ContractConflictCheckDTO checkDTO) {
        ContractConflictResultDTO result = conflictService.checkConflict(checkDTO);
        return CommonResult.success(result);
    }

    @Operation(summary = "获取冲突历史")
    @GetMapping("/history/{contractId}")
    @PreAuthorize(CONTRACT_VIEW)
    public CommonResult<List<ContractConflictVO>> getConflictHistory(
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        List<ContractConflictVO> history = conflictService.getConflictHistory(contractId);
        return CommonResult.success(history);
    }

    @Operation(summary = "分页查询冲突")
    @GetMapping("/page")
    @PreAuthorize(CONTRACT_VIEW)
    public CommonResult<Page<ContractConflictVO>> pageConflicts(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "检查状态") @RequestParam(required = false) Integer checkStatus,
            @Parameter(description = "冲突类型") @RequestParam(required = false) String conflictType,
            @Parameter(description = "是否已解决") @RequestParam(required = false) Boolean isResolved) {
        Page<ContractConflictVO> page = new Page<>(pageNum, pageSize);
        Page<ContractConflictVO> result = conflictService.pageConflicts(page, contractId, conflictType, checkStatus, isResolved);
        return CommonResult.success(result);
    }

    @Operation(summary = "解决冲突")
    @PostMapping("/{id}/resolve")
    @PreAuthorize(CONTRACT_EDIT)
    public CommonResult<Void> resolveConflict(
            @Parameter(description = "冲突ID") @PathVariable Long id,
            @Parameter(description = "解决方案") @RequestParam String resolution) {
        conflictService.resolveConflict(id, resolution);
        return CommonResult.success();
    }

    @Operation(summary = "批量解决冲突")
    @PostMapping("/batch-resolve")
    @PreAuthorize(CONTRACT_EDIT)
    public CommonResult<Void> batchResolveConflicts(
            @Parameter(description = "冲突ID列表") @RequestBody List<Long> ids,
            @Parameter(description = "解决方案") @RequestParam String resolution) {
        conflictService.batchResolveConflicts(ids, resolution);
        return CommonResult.success();
    }

    @Operation(summary = "获取冲突详情")
    @GetMapping("/{id}")
    @PreAuthorize(CONTRACT_VIEW)
    public CommonResult<ContractConflictVO> getConflictDetail(
            @Parameter(description = "冲突ID") @PathVariable Long id) {
        ContractConflictVO detail = conflictService.getConflictDetail(id);
        return CommonResult.success(detail);
    }

    @Operation(summary = "检查是否存在冲突")
    @GetMapping("/exists/{contractId}")
    @PreAuthorize(CONTRACT_VIEW)
    public CommonResult<Boolean> hasConflict(
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        boolean exists = conflictService.hasConflict(contractId);
        return CommonResult.success(exists);
    }

    @Operation(summary = "获取未解决冲突数量")
    @GetMapping("/count/{contractId}")
    @PreAuthorize(CONTRACT_VIEW)
    public CommonResult<Integer> countUnresolvedConflicts(@PathVariable Long contractId) {
        return CommonResult.success(conflictService.getUnresolvedConflictCount(contractId));
    }
} 