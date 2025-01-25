package com.lawfirm.client.controller;

import com.lawfirm.client.service.ClientService;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.query.ClientQuery;
import com.lawfirm.model.client.vo.ClientVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "客户管理")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "创建客户")
    @PostMapping
    public Result<ClientVO> create(@RequestBody Client client) {
        return Result.success(clientService.create(client));
    }

    @Operation(summary = "批量创建客户")
    @PostMapping("/batch")
    public Result<List<ClientVO>> batchCreate(@RequestBody List<Client> clients) {
        return Result.success(clientService.batchCreate(clients));
    }

    @Operation(summary = "更新客户")
    @PutMapping("/{id}")
    public Result<ClientVO> update(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        return Result.success(clientService.update(client));
    }

    @Operation(summary = "批量更新客户")
    @PutMapping("/batch")
    public Result<List<ClientVO>> batchUpdate(@RequestBody List<Client> clients) {
        return Result.success(clientService.batchUpdate(clients));
    }

    @Operation(summary = "删除客户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return Result.success();
    }

    @Operation(summary = "获取客户详情")
    @GetMapping("/{id}")
    public Result<ClientVO> get(@PathVariable Long id) {
        return Result.success(clientService.getById(id));
    }

    @Operation(summary = "分页查询客户")
    @GetMapping
    public Result<List<ClientVO>> list(ClientQuery query) {
        return Result.success(clientService.list(query));
    }

    @Operation(summary = "按名称模糊查询")
    @GetMapping("/search")
    public Result<List<ClientVO>> search(@RequestParam String name) {
        return Result.success(clientService.findByNameLike(name));
    }

    @Operation(summary = "启用客户")
    @PutMapping("/{id}/enable")
    public Result<Void> enable(@PathVariable Long id, @RequestParam String operator) {
        clientService.enableClient(id, operator);
        return Result.success();
    }

    @Operation(summary = "批量启用客户")
    @PutMapping("/batch/enable")
    public Result<Void> batchEnable(@RequestBody List<Long> ids, @RequestParam String operator) {
        clientService.enableClients(ids, operator);
        return Result.success();
    }

    @Operation(summary = "禁用客户")
    @PutMapping("/{id}/disable")
    public Result<Void> disable(@PathVariable Long id, @RequestParam String operator) {
        clientService.disableClient(id, operator);
        return Result.success();
    }

    @Operation(summary = "批量禁用客户")
    @PutMapping("/batch/disable")
    public Result<Void> batchDisable(@RequestBody List<Long> ids, @RequestParam String operator) {
        clientService.disableClients(ids, operator);
        return Result.success();
    }

    @Operation(summary = "按类型统计客户")
    @GetMapping("/count/type")
    public Result<Map<String, Long>> countByType() {
        return Result.success(clientService.countByType());
    }

    @Operation(summary = "按状态统计客户")
    @GetMapping("/count/status")
    public Result<Map<String, Long>> countByStatus() {
        return Result.success(clientService.countByStatus());
    }

    @Operation(summary = "查询个人客户")
    @GetMapping("/personal")
    public Result<List<ClientVO>> findPersonalClients(ClientQuery query) {
        return Result.success(clientService.findPersonalClients(query));
    }

    @Operation(summary = "查询企业客户")
    @GetMapping("/enterprise")
    public Result<List<ClientVO>> findEnterpriseClients(ClientQuery query) {
        return Result.success(clientService.findEnterpriseClients(query));
    }

    @Operation(summary = "检查客户编号是否存在")
    @GetMapping("/check/number/{clientNumber}")
    public Result<Boolean> checkClientNumberExists(@PathVariable String clientNumber) {
        return Result.success(clientService.checkClientNumberExists(clientNumber));
    }

    @Operation(summary = "检查证件号码是否存在")
    @GetMapping("/check/id/{idNumber}")
    public Result<Boolean> checkIdNumberExists(@PathVariable String idNumber) {
        return Result.success(clientService.checkIdNumberExists(idNumber));
    }
} 