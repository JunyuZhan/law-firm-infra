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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController extends BaseController {

    private final ClientServiceImpl clientService;

    /**
     * 分页查询客户列表
     *
     * @param queryDTO 查询条件
     * @return 客户分页列表
     */
    @GetMapping("/list")
    public CommonResult<List<ClientVO>> list(ClientQueryDTO queryDTO) {
        return success(clientService.listClients(queryDTO));
    }

    /**
     * 获取客户详情
     *
     * @param id 客户ID
     * @return 客户详情
     */
    @GetMapping("/{id}")
    public CommonResult<ClientVO> getClientById(@PathVariable("id") Long id) {
        return success(clientService.getClient(id));
    }

    /**
     * 新增客户
     *
     * @param createDTO 客户信息
     * @return 操作结果
     */
    @PostMapping
    public CommonResult<Long> add(@Validated @RequestBody ClientCreateDTO createDTO) {
        return success(clientService.createClient(createDTO));
    }

    /**
     * 修改客户
     *
     * @param updateDTO 客户信息
     * @return 操作结果
     */
    @PutMapping
    public CommonResult<Boolean> update(@Validated @RequestBody ClientUpdateDTO updateDTO) {
        clientService.updateClient(updateDTO);
        return success(true);
    }

    /**
     * 删除客户
     *
     * @param id 客户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return success(true);
    }

    /**
     * 更新客户状态
     *
     * @param id 客户ID
     * @param status 状态
     * @return 操作结果
     */
    @PutMapping("/{id}/status/{status}")
    public CommonResult<Boolean> updateStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Integer status) {
        clientService.updateStatus(id, status);
        return success(true);
    }

    /**
     * 更新客户信用等级
     *
     * @param id 客户ID
     * @param creditLevel 信用等级
     * @return 操作结果
     */
    @PutMapping("/{id}/credit/{creditLevel}")
    public CommonResult<Boolean> updateCreditLevel(
            @PathVariable("id") Long id,
            @PathVariable("creditLevel") String creditLevel) {
        clientService.updateCreditLevel(id, creditLevel);
        return success(true);
    }
}
