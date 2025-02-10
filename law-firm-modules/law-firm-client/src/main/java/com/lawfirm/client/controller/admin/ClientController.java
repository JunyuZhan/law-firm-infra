package com.lawfirm.client.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.client.service.IClientService;
import com.lawfirm.client.vo.request.ClientAddRequest;
import com.lawfirm.client.vo.request.ClientQueryRequest;
import com.lawfirm.client.vo.request.ClientUpdateRequest;
import com.lawfirm.client.vo.response.ClientResponse;
import com.lawfirm.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 客户管理接口
 */
@Tag(name = "客户管理接口")
@RestController
@RequestMapping("/admin/client")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "添加客户")
    @PostMapping
    public Result<Long> addClient(@Validated @RequestBody ClientAddRequest request) {
        return Result.success(clientService.addClient(request));
    }

    @Operation(summary = "更新客户")
    @PutMapping
    public Result<Void> updateClient(@Validated @RequestBody ClientUpdateRequest request) {
        clientService.updateClient(request);
        return Result.success();
    }

    @Operation(summary = "删除客户")
    @DeleteMapping("/{id}")
    public Result<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return Result.success();
    }

    @Operation(summary = "分页查询客户")
    @GetMapping("/page")
    public Result<IPage<ClientResponse>> pageClients(@Validated ClientQueryRequest request) {
        return Result.success(clientService.pageClients(request));
    }

    @Operation(summary = "获取客户详情")
    @GetMapping("/{id}")
    public Result<ClientResponse> getClient(@PathVariable Long id) {
        return Result.success(clientService.getClientById(id));
    }
} 