package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.client.finance.ContractClient;
import com.lawfirm.staff.model.request.lawyer.contract.ContractPageRequest;
import com.lawfirm.staff.model.response.lawyer.contract.ContractResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "合同查看", description = "文员-合同查看相关接口")
@RestController
@RequestMapping("/clerk/contract")
@RequiredArgsConstructor
public class ContractViewController {

    private final ContractClient contractClient;

    @Operation(summary = "分页查询合同")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('clerk:contract:view')")
    @OperationLog(description = "文员查询合同", operationType = "QUERY")
    public Result<PageResult<ContractResponse>> page(ContractPageRequest request) {
        return contractClient.page(request);
    }

    @Operation(summary = "获取合同详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:contract:view')")
    @OperationLog(description = "文员查看合同详情", operationType = "QUERY")
    public Result<ContractResponse> get(@PathVariable Long id) {
        return contractClient.get(id);
    }

    @Operation(summary = "下载合同")
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAuthority('clerk:contract:view')")
    @OperationLog(description = "文员下载合同", operationType = "EXPORT")
    public void download(@PathVariable Long id) {
        contractClient.download(id);
    }

    @Operation(summary = "预览合同")
    @GetMapping("/preview/{id}")
    @PreAuthorize("hasAuthority('clerk:contract:view')")
    @OperationLog(description = "文员预览合同", operationType = "QUERY")
    public Result<String> preview(@PathVariable Long id) {
        return contractClient.preview(id);
    }
} 