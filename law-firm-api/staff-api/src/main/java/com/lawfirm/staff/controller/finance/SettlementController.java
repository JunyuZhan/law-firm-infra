package com.lawfirm.staff.controller.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.finance.model.dto.SettlementDTO;
import com.lawfirm.finance.model.query.SettlementQuery;
import com.lawfirm.finance.model.vo.SettlementVO;
import com.lawfirm.finance.service.SettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "结算管理")
@RestController
@RequestMapping("/finance/settlement")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @Operation(summary = "分页查询结算")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('finance:settlement:query')")
    public Result<PageResult<SettlementVO>> page(SettlementQuery query) {
        return Result.ok(settlementService.page(query));
    }

    @Operation(summary = "添加结算")
    @PostMapping
    @PreAuthorize("hasAuthority('finance:settlement:add')")
    @OperationLog("添加结算")
    public Result<Void> add(@RequestBody @Validated SettlementDTO settlementDTO) {
        settlementService.add(settlementDTO);
        return Result.ok();
    }

    @Operation(summary = "修改结算")
    @PutMapping
    @PreAuthorize("hasAuthority('finance:settlement:update')")
    @OperationLog("修改结算")
    public Result<Void> update(@RequestBody @Validated SettlementDTO settlementDTO) {
        settlementService.update(settlementDTO);
        return Result.ok();
    }

    @Operation(summary = "删除结算")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:settlement:delete')")
    @OperationLog("删除结算")
    public Result<Void> delete(@PathVariable Long id) {
        settlementService.delete(id);
        return Result.ok();
    }

    @Operation(summary = "获取结算详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:settlement:query')")
    public Result<SettlementVO> get(@PathVariable Long id) {
        return Result.ok(settlementService.get(id));
    }

    @Operation(summary = "审核结算")
    @PutMapping("/audit/{id}")
    @PreAuthorize("hasAuthority('finance:settlement:audit')")
    @OperationLog("审核结算")
    public Result<Void> audit(@PathVariable Long id, @RequestParam Integer status, @RequestParam String comment) {
        settlementService.audit(id, status, comment);
        return Result.ok();
    }

    @Operation(summary = "确认收款")
    @PutMapping("/confirm-receipt/{id}")
    @PreAuthorize("hasAuthority('finance:settlement:confirm')")
    @OperationLog("确认收款")
    public Result<Void> confirmReceipt(@PathVariable Long id) {
        settlementService.confirmReceipt(id);
        return Result.ok();
    }

    @Operation(summary = "导出结算")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('finance:settlement:export')")
    @OperationLog("导出结算")
    public void export(SettlementQuery query) {
        settlementService.export(query);
    }

    @Operation(summary = "统计结算")
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('finance:settlement:query')")
    public Result<Map<String, Object>> getStats(SettlementQuery query) {
        return Result.ok(settlementService.getStats(query));
    }
} 