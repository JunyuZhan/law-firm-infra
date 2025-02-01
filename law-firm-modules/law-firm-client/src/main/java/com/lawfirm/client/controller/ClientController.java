package com.lawfirm.client.controller;

import com.lawfirm.client.service.ClientService;
import com.lawfirm.common.core.result.R;
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
    public R<ClientVO> create(@RequestBody ClientVO clientVO) {
        return R.ok(clientService.create(clientVO));
    }

    @Operation(summary = "批量创建客户")
    @PostMapping("/batch")
    public R<List<ClientVO>> batchCreate(@RequestBody List<Client> clients) {
        return R.ok(clientService.batchCreate(clients));
    }

    @Operation(summary = "更新客户")
    @PutMapping("/{id}")
    public R<ClientVO> update(@PathVariable Long id, @RequestBody ClientVO clientVO) {
        clientVO.setId(id);
        return R.ok(clientService.update(clientVO));
    }

    @Operation(summary = "批量更新客户")
    @PutMapping("/batch")
    public R<List<ClientVO>> batchUpdate(@RequestBody List<Client> clients) {
        return R.ok(clientService.batchUpdate(clients));
    }

    @Operation(summary = "删除客户")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        clientService.removeById(id);
        return R.ok();
    }

    @Operation(summary = "获取客户详情")
    @GetMapping("/{id}")
    public R<ClientVO> get(@PathVariable Long id) {
        return R.ok(clientService.findById(id));
    }

    @Operation(summary = "分页查询客户")
    @GetMapping
    public R<List<ClientVO>> list(ClientQuery query) {
        return R.ok(clientService.findByQuery(query));
    }

    @Operation(summary = "按名称模糊查询")
    @GetMapping("/search")
    public R<List<ClientVO>> search(@RequestParam String name) {
        return R.ok(clientService.findByNameLike(name));
    }

    @Operation(summary = "启用客户")
    @PutMapping("/{id}/enable")
    public R<Void> enable(@PathVariable Long id, @RequestParam String operator) {
        clientService.enableClient(id, operator);
        return R.ok();
    }

    @Operation(summary = "批量启用客户")
    @PutMapping("/batch/enable")
    public R<Void> batchEnable(@RequestBody List<Long> ids, @RequestParam String operator) {
        clientService.enableClients(ids, operator);
        return R.ok();
    }

    @Operation(summary = "禁用客户")
    @PutMapping("/{id}/disable")
    public R<Void> disable(@PathVariable Long id, @RequestParam String operator) {
        clientService.disableClient(id, operator);
        return R.ok();
    }

    @Operation(summary = "批量禁用客户")
    @PutMapping("/batch/disable")
    public R<Void> batchDisable(@RequestBody List<Long> ids, @RequestParam String operator) {
        clientService.disableClients(ids, operator);
        return R.ok();
    }

    @Operation(summary = "按类型统计客户")
    @GetMapping("/count/type")
    public R<Map<String, Long>> countByType() {
        return R.ok(clientService.countByType());
    }

    @Operation(summary = "按状态统计客户")
    @GetMapping("/count/status")
    public R<Map<String, Long>> countByStatus() {
        return R.ok(clientService.countByStatus());
    }

    @Operation(summary = "查询个人客户")
    @GetMapping("/personal")
    public R<List<ClientVO>> findPersonalClients(ClientQuery query) {
        return R.ok(clientService.findPersonalClients(query));
    }

    @Operation(summary = "查询企业客户")
    @GetMapping("/enterprise")
    public R<List<ClientVO>> findEnterpriseClients(ClientQuery query) {
        return R.ok(clientService.findEnterpriseClients(query));
    }

    @Operation(summary = "检查客户编号是否存在")
    @GetMapping("/check/number/{clientNumber}")
    public R<Boolean> checkClientNumberExists(@PathVariable String clientNumber) {
        return R.ok(clientService.checkClientNumberExists(clientNumber));
    }

    @Operation(summary = "检查证件号码是否存在")
    @GetMapping("/check/id/{idNumber}")
    public R<Boolean> checkIdNumberExists(@PathVariable String idNumber) {
        return R.ok(clientService.checkIdNumberExists(idNumber));
    }
} 