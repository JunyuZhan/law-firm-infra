package com.lawfirm.mobile.controller.contract;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.contract.model.dto.ContractDTO;
import com.lawfirm.contract.model.query.ContractQuery;
import com.lawfirm.contract.model.vo.ContractVO;
import com.lawfirm.contract.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "移动端-合同管理")
@RestController
@RequestMapping("/mobile/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "分页查询合同")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('mobile:contract:query')")
    public Result<PageResult<ContractVO>> page(ContractQuery query) {
        return Result.ok(contractService.page(query));
    }

    @Operation(summary = "获取合同详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('mobile:contract:query')")
    public Result<ContractVO> get(@PathVariable Long id) {
        return Result.ok(contractService.get(id));
    }

    @Operation(summary = "获取我的合同")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('mobile:contract:query')")
    public Result<List<ContractVO>> getMyContracts() {
        return Result.ok(contractService.getMyContracts());
    }

    @Operation(summary = "获取待审核合同")
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('mobile:contract:query')")
    public Result<List<ContractVO>> getPendingContracts() {
        return Result.ok(contractService.getPendingContracts());
    }

    @Operation(summary = "审核合同")
    @PutMapping("/review/{id}")
    @PreAuthorize("hasAuthority('mobile:contract:review')")
    @OperationLog("审核合同")
    public Result<Void> review(@PathVariable Long id, @RequestParam Integer status, @RequestParam String comment) {
        contractService.review(id, status, comment);
        return Result.ok();
    }

    @Operation(summary = "预览合同")
    @GetMapping("/preview/{id}")
    @PreAuthorize("hasAuthority('mobile:contract:query')")
    public Result<String> preview(@PathVariable Long id) {
        return Result.ok(contractService.preview(id));
    }

    @Operation(summary = "获取合同提醒")
    @GetMapping("/reminders")
    @PreAuthorize("hasAuthority('mobile:contract:query')")
    public Result<List<ContractVO>> getReminders() {
        return Result.ok(contractService.getReminders());
    }

    @Operation(summary = "获取合同统计")
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('mobile:contract:query')")
    public Result<Map<String, Object>> getStats() {
        return Result.ok(contractService.getStats());
    }
} 