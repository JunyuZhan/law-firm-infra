package com.lawfirm.staff.controller.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.client.finance.ChargeClient;
import com.lawfirm.staff.model.request.finance.charge.ChargeCreateRequest;
import com.lawfirm.staff.model.request.finance.charge.ChargePageRequest;
import com.lawfirm.staff.model.request.finance.charge.ChargeUpdateRequest;
import com.lawfirm.staff.model.response.finance.charge.ChargeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "费用管理")
@RestController
@RequestMapping("/finance/charge")
@RequiredArgsConstructor
public class ChargeController {

    private final ChargeClient chargeClient;

    @Operation(summary = "分页查询费用")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('finance:charge:query')")
    public Result<PageResult<ChargeResponse>> page(@Validated ChargePageRequest request) {
        return chargeClient.page(request);
    }

    @Operation(summary = "创建费用")
    @PostMapping
    @PreAuthorize("hasAuthority('finance:charge:create')")
    @OperationLog(description = "创建费用", operationType = "CREATE")
    public Result<Void> create(@RequestBody @Validated ChargeCreateRequest request) {
        return chargeClient.create(request);
    }

    @Operation(summary = "修改费用")
    @PutMapping
    @PreAuthorize("hasAuthority('finance:charge:update')")
    @OperationLog(description = "修改费用", operationType = "UPDATE")
    public Result<Void> update(@RequestBody @Validated ChargeUpdateRequest request) {
        return chargeClient.update(request);
    }

    @Operation(summary = "删除费用")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:charge:delete')")
    @OperationLog(description = "删除费用", operationType = "DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        return chargeClient.delete(id);
    }

    @Operation(summary = "获取费用详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:charge:query')")
    public Result<ChargeResponse> get(@PathVariable Long id) {
        return chargeClient.get(id);
    }

    @Operation(summary = "审核费用")
    @PutMapping("/audit/{id}")
    @PreAuthorize("hasAuthority('finance:charge:audit')")
    @OperationLog(description = "审核费用", operationType = "AUDIT")
    public Result<Void> audit(@PathVariable Long id, @RequestParam Integer status, @RequestParam String comment) {
        return chargeClient.audit(id, status, comment);
    }

    @Operation(summary = "导出费用")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('finance:charge:export')")
    @OperationLog(description = "导出费用", operationType = "EXPORT")
    public void export(@Validated ChargePageRequest request) {
        chargeClient.export(request);
    }

    @Operation(summary = "统计费用")
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('finance:charge:query')")
    public Result<Map<String, Object>> getStats(@Validated ChargePageRequest request) {
        return chargeClient.getStats(request);
    }
} 