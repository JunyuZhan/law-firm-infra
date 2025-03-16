package com.lawfirm.contract.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.contract.service.ContractTemplateService;
import com.lawfirm.contract.util.TemplateParser;
import com.lawfirm.model.contract.dto.ContractTemplateCreateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateQueryDTO;
import com.lawfirm.model.contract.dto.ContractTemplateUpdateDTO;
import com.lawfirm.model.contract.vo.ContractTemplateDetailVO;
import com.lawfirm.model.contract.vo.ContractTemplateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 合同模板控制器
 */
@Slf4j
@RestController
@RequestMapping("/contract-templates")
@RequiredArgsConstructor
@Tag(name = "合同模板接口", description = "提供合同模板的创建、查询、修改、删除等功能")
public class ContractTemplateController {

    private final ContractTemplateService contractTemplateService;
    
    private final TemplateParser templateParser;

    /**
     * 创建合同模板
     */
    @PostMapping
    @Operation(summary = "创建合同模板", description = "创建新的合同模板")
    public CommonResult<Long> createContractTemplate(@RequestBody @Validated ContractTemplateCreateDTO createDTO) {
        log.info("创建合同模板: {}", createDTO.getTemplateName());
        Long templateId = contractTemplateService.createTemplate(createDTO);
        return CommonResult.success(templateId, "创建合同模板成功");
    }

    /**
     * 更新合同模板
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新合同模板", description = "根据ID更新合同模板")
    public CommonResult<Boolean> updateContractTemplate(
            @PathVariable("id") Long id,
            @RequestBody @Validated ContractTemplateUpdateDTO updateDTO) {
        log.info("更新合同模板: {}", id);
        updateDTO.setId(id);
        boolean result = contractTemplateService.updateTemplate(updateDTO);
        return CommonResult.success(result, "更新合同模板" + (result ? "成功" : "失败"));
    }

    /**
     * 获取合同模板详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取合同模板详情", description = "根据ID获取合同模板详情")
    public CommonResult<ContractTemplateDetailVO> getContractTemplate(@PathVariable("id") Long id) {
        log.info("获取合同模板详情: {}", id);
        ContractTemplateDetailVO detailVO = contractTemplateService.getTemplateDetail(id);
        return CommonResult.success(detailVO);
    }

    /**
     * 删除合同模板
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除合同模板", description = "根据ID删除合同模板")
    public CommonResult<Boolean> deleteContractTemplate(@PathVariable("id") Long id) {
        log.info("删除合同模板: {}", id);
        boolean result = contractTemplateService.removeById(id);
        return CommonResult.success(result, "删除合同模板" + (result ? "成功" : "失败"));
    }

    /**
     * 分页查询合同模板
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询合同模板", description = "根据条件分页查询合同模板")
    @Parameters({
            @Parameter(name = "current", description = "当前页码，从1开始", required = true),
            @Parameter(name = "size", description = "每页记录数", required = true)
    })
    public CommonResult<IPage<ContractTemplateVO>> pageContractTemplates(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            ContractTemplateQueryDTO queryDTO) {
        log.info("分页查询合同模板: current={}, size={}", current, size);
        Page<ContractTemplateVO> page = new Page<>(current, size);
        IPage<ContractTemplateVO> pageResult = contractTemplateService.pageTemplates(page, queryDTO);
        return CommonResult.success(pageResult);
    }

    /**
     * 查询合同模板列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询合同模板列表", description = "根据条件查询合同模板列表")
    public CommonResult<List<ContractTemplateVO>> listContractTemplates(ContractTemplateQueryDTO queryDTO) {
        log.info("查询合同模板列表");
        List<ContractTemplateVO> templates = contractTemplateService.listTemplates(queryDTO);
        return CommonResult.success(templates);
    }
    
    /**
     * 提取模板变量
     */
    @PostMapping("/extract-variables")
    @Operation(summary = "提取模板变量", description = "从模板内容中提取变量")
    public CommonResult<Map<String, String>> extractTemplateVariables(@RequestBody String templateContent) {
        log.info("提取模板变量");
        Map<String, String> variables = templateParser.extractVariables(templateContent);
        return CommonResult.success(variables);
    }
    
    /**
     * 验证模板变量
     */
    @PostMapping("/validate-variables")
    @Operation(summary = "验证模板变量", description = "验证模板变量是否完整")
    public CommonResult<Boolean> validateTemplateVariables(
            @RequestBody Map<String, Object> request) {
        log.info("验证模板变量");
        String templateContent = (String) request.get("templateContent");
        @SuppressWarnings("unchecked")
        Map<String, Object> variables = (Map<String, Object>) request.get("variables");
        boolean valid = templateParser.validateTemplateVariables(templateContent, variables);
        return CommonResult.success(valid, valid ? "验证通过" : "验证失败");
    }
    
    /**
     * 启用合同模板
     */
    @PutMapping("/{id}/enable")
    @Operation(summary = "启用合同模板", description = "启用指定ID的合同模板")
    public CommonResult<Boolean> enableContractTemplate(@PathVariable("id") Long id) {
        log.info("启用合同模板: {}", id);
        boolean result = contractTemplateService.enableTemplate(id);
        return CommonResult.success(result, "启用合同模板" + (result ? "成功" : "失败"));
    }
    
    /**
     * 禁用合同模板
     */
    @PutMapping("/{id}/disable")
    @Operation(summary = "禁用合同模板", description = "禁用指定ID的合同模板")
    public CommonResult<Boolean> disableContractTemplate(@PathVariable("id") Long id) {
        log.info("禁用合同模板: {}", id);
        boolean result = contractTemplateService.disableTemplate(id);
        return CommonResult.success(result, "禁用合同模板" + (result ? "成功" : "失败"));
    }
} 