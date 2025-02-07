package com.lawfirm.staff.controller.finance;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.model.request.finance.contract.ContractPageRequest;
import com.lawfirm.staff.model.response.finance.contract.ContractResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "财务合同查看")
@RestController
@RequestMapping("/finance/contract")
@RequiredArgsConstructor
public class ContractViewController {

    @Operation(summary = "分页查询合同")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('finance:contract:query')")
    public Result<PageResult<ContractResponse>> page(ContractPageRequest request) {
        // TODO: 调用合同服务
        return Result.ok();
    }

    @Operation(summary = "获取合同详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:contract:query')")
    public Result<ContractResponse> get(@PathVariable Long id) {
        // TODO: 调用合同服务
        return Result.ok();
    }

    @Operation(summary = "下载合同文件")
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAuthority('finance:contract:download')")
    public void download(@PathVariable Long id) {
        // TODO: 调用合同服务
    }
} 