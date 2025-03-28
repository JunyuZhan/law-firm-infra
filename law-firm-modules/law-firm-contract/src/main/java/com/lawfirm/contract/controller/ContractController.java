package com.lawfirm.contract.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.contract.dto.ContractCreateDTO;
import com.lawfirm.model.contract.dto.ContractQueryDTO;
import com.lawfirm.model.contract.dto.ContractUpdateDTO;
import com.lawfirm.model.contract.service.ContractService;
import com.lawfirm.model.contract.vo.ContractDetailVO;
import com.lawfirm.model.contract.vo.ContractVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合同管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
@Tag(name = "合同管理接口", description = "提供合同的创建、查询、修改、删除等功能，支持合同全生命周期管理")
public class ContractController {

    private final ContractService contractService;

    /**
     * 创建合同
     */
    @PostMapping
    @Operation(
        summary = "创建合同",
        description = "创建新的合同记录，包括合同基本信息、合同内容、合同附件等"
    )
    public CommonResult<Long> createContract(
            @Parameter(description = "合同创建参数，包括合同名称、类型、内容等") @RequestBody @Validated ContractCreateDTO createDTO) {
        log.info("创建合同: {}", createDTO.getContractName());
        Long contractId = contractService.createContract(createDTO);
        return CommonResult.success(contractId, "创建合同成功");
    }

    /**
     * 更新合同
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "更新合同",
        description = "根据ID更新合同信息，支持更新合同基本信息、内容和附件等"
    )
    public CommonResult<Boolean> updateContract(
            @Parameter(description = "合同ID") @PathVariable("id") Long id,
            @Parameter(description = "合同更新参数，包括需要更新的字段") @RequestBody @Validated ContractUpdateDTO updateDTO) {
        log.info("更新合同: {}", id);
        updateDTO.setId(id);
        boolean result = contractService.updateContract(updateDTO);
        return CommonResult.success(result, "更新合同" + (result ? "成功" : "失败"));
    }

    /**
     * 获取合同详情
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "获取合同详情",
        description = "根据ID获取合同的详细信息，包括基本信息、内容、附件、审批状态等"
    )
    public CommonResult<ContractDetailVO> getContract(
            @Parameter(description = "合同ID") @PathVariable("id") Long id) {
        log.info("获取合同详情: {}", id);
        // 假设服务层提供了获取详情的方法
        ContractDetailVO detailVO = contractService.getContractDetail(id);
        return CommonResult.success(detailVO);
    }

    /**
     * 删除合同
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "删除合同",
        description = "根据ID删除合同，同时删除关联的附件和审批记录等"
    )
    public CommonResult<Boolean> deleteContract(
            @Parameter(description = "合同ID") @PathVariable("id") Long id) {
        log.info("删除合同: {}", id);
        boolean result = contractService.removeById(id);
        return CommonResult.success(result, "删除合同" + (result ? "成功" : "失败"));
    }

    /**
     * 分页查询合同列表
     */
    @GetMapping("/page")
    @Operation(
        summary = "分页查询合同",
        description = "根据条件分页查询合同列表，支持按合同名称、类型、状态、创建时间等条件筛选"
    )
    @Parameters({
            @Parameter(name = "current", description = "当前页码，从1开始", required = true),
            @Parameter(name = "size", description = "每页记录数", required = true),
            @Parameter(name = "queryDTO", description = "查询参数，包括合同名称、类型、状态等")
    })
    public CommonResult<IPage<ContractVO>> pageContracts(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "查询参数，包括合同名称、类型、状态等") ContractQueryDTO queryDTO) {
        log.info("分页查询合同: current={}, size={}", current, size);
        Page<ContractVO> page = new Page<>(current, size);
        IPage<ContractVO> pageResult = contractService.pageContracts(page, queryDTO);
        return CommonResult.success(pageResult);
    }

    /**
     * 查询合同列表
     */
    @GetMapping("/list")
    @Operation(
        summary = "查询合同列表",
        description = "根据条件查询合同列表，不分页，支持按合同名称、类型、状态等条件筛选"
    )
    public CommonResult<List<ContractVO>> listContracts(
            @Parameter(description = "查询参数，包括合同名称、类型、状态等") ContractQueryDTO queryDTO) {
        log.info("查询合同列表");
        List<ContractVO> contracts = contractService.listContracts(queryDTO);
        return CommonResult.success(contracts);
    }
} 