package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.model.base.response.ApiResponse;
import com.lawfirm.model.base.query.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.common.log.annotation.OperationType;
import com.lawfirm.staff.model.response.lawyer.client.ClientResponse;
import com.lawfirm.staff.model.response.lawyer.client.ClientStatsResponse;
import com.lawfirm.staff.model.request.lawyer.client.ClientPageRequest;
import com.lawfirm.staff.client.lawyer.ClientClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 律师-客户管理
 */
@Tag(name = "律师-客户管理")
@RestController
@RequestMapping("/api/v1/lawyer/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientClient clientClient;

    @GetMapping("/page")
    @Operation(summary = "分页查询我的客户")
    @PreAuthorize("hasAnyAuthority('lawyer:client:query')")
    @OperationLog(description = "分页查询我的客户", operationType = OperationType.QUERY)
    public ApiResponse<PageResult<ClientResponse>> page(@Valid ClientPageRequest request) {
        return clientClient.page(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取客户详情")
    @PreAuthorize("hasAnyAuthority('lawyer:client:query')")
    @OperationLog(description = "获取客户详情", operationType = OperationType.QUERY)
    public ApiResponse<ClientResponse> getById(@PathVariable Long id) {
        return clientClient.getById(id);
    }

    @GetMapping("/stats")
    @Operation(summary = "获取我的客户统计信息")
    @PreAuthorize("hasAnyAuthority('lawyer:client:query')")
    @OperationLog(description = "获取我的客户统计信息", operationType = OperationType.QUERY)
    public ApiResponse<ClientStatsResponse> getStats() {
        return clientClient.getStats();
    }
} 