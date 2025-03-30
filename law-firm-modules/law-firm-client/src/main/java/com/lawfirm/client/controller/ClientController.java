package com.lawfirm.client.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.client.service.impl.ClientServiceImpl;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.dto.client.ClientCreateDTO;
import com.lawfirm.model.client.dto.client.ClientQueryDTO;
import com.lawfirm.model.client.dto.client.ClientUpdateDTO;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.vo.ClientVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理控制器
 */
@Tag(name = "客户管理")
@Slf4j
@RestController("clientController")
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController extends BaseController {

    private final ClientServiceImpl clientService;

    /**
     * 分页查询客户列表
     */
    @Operation(summary = "分页查询客户列表")
    @GetMapping("/list")
    public CommonResult<List<ClientVO>> list(ClientQueryDTO queryDTO) {
        return success(clientService.listClients(queryDTO));
    }

    /**
     * 获取客户详情
     */
    @Operation(summary = "获取客户详情")
    @GetMapping("/{id}")
    public CommonResult<ClientVO> getClientById(@PathVariable("id") Long id) {
        return success(clientService.getClient(id));
    }

    /**
     * 新增客户
     */
    @Operation(summary = "新增客户")
    @PostMapping
    public CommonResult<Long> add(@Validated @RequestBody ClientCreateDTO createDTO) {
        return success(clientService.createClient(createDTO));
    }

    /**
     * 修改客户
     */
    @Operation(summary = "修改客户信息")
    @PutMapping
    public CommonResult<Boolean> update(@Validated @RequestBody ClientUpdateDTO updateDTO) {
        clientService.updateClient(updateDTO);
        return success(true);
    }

    /**
     * 删除客户
     */
    @Operation(summary = "删除客户")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return success(true);
    }

    /**
     * 更新客户状态
     */
    @Operation(summary = "更新客户状态")
    @PutMapping("/{id}/status/{status}")
    public CommonResult<Boolean> updateStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Integer status) {
        clientService.updateStatus(id, status);
        return success(true);
    }

    /**
     * 更新客户信用等级
     */
    @Operation(summary = "更新客户信用等级")
    @PutMapping("/{id}/credit/{creditLevel}")
    public CommonResult<Boolean> updateCreditLevel(
            @PathVariable("id") Long id,
            @PathVariable("creditLevel") String creditLevel) {
        clientService.updateCreditLevel(id, creditLevel);
        return success(true);
    }
}
