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
import com.lawfirm.contract.constant.ContractConstants;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

/**
 * 合同模板控制器
 * 适配Vue-Vben-Admin风格
 */
@Slf4j
@RestController("contractTemplateController")
@RequestMapping(ContractConstants.API_TEMPLATE_PREFIX)
@RequiredArgsConstructor
@Tag(name = "合同模板管理", description = "提供合同模板的创建、查询、修改、删除等功能")
public class ContractTemplateController {

    private final ContractTemplateService contractTemplateService;
    
    private final TemplateParser templateParser;

    /**
     * 创建合同模板
     */
    @PostMapping
    @Operation(
        summary = "创建合同模板",
        description = "创建新的合同模板，包括模板名称、编码、内容、变量定义等信息"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_CREATE + "')")
    public CommonResult<Long> createContractTemplate(
            @Parameter(description = "模板创建参数，包括模板名称、编码、内容等") @RequestBody @Validated ContractTemplateCreateDTO createDTO) {
        log.info("创建合同模板: {}", createDTO.getTemplateName());
        Long templateId = contractTemplateService.createTemplate(createDTO);
        return CommonResult.success(templateId, "创建合同模板成功");
    }

    /**
     * 更新合同模板
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "更新合同模板",
        description = "更新已存在的合同模板信息，支持更新模板名称、内容、变量定义等"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_EDIT + "')")
    public CommonResult<Boolean> updateContractTemplate(
            @Parameter(description = "模板ID") @PathVariable("id") Long id,
            @Parameter(description = "模板更新参数，包括需要更新的字段") @RequestBody @Validated ContractTemplateUpdateDTO updateDTO) {
        log.info("更新合同模板: {}", id);
        updateDTO.setId(id);
        boolean result = contractTemplateService.updateTemplate(updateDTO);
        return CommonResult.success(result, "更新合同模板" + (result ? "成功" : "失败"));
    }

    /**
     * 获取合同模板详情
     */
    @GetMapping("/getContractTemplate/{id}")
    @Operation(
        summary = "获取合同模板详情",
        description = "根据ID获取合同模板的详细信息，包括模板内容、变量定义、使用状态等"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    public CommonResult<ContractTemplateDetailVO> getContractTemplate(
            @Parameter(description = "模板ID") @PathVariable("id") Long id) {
        log.info("获取合同模板详情: {}", id);
        ContractTemplateDetailVO detailVO = contractTemplateService.getTemplateDetail(id);
        return CommonResult.success(detailVO);
    }

    /**
     * 删除合同模板
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "删除合同模板",
        description = "根据ID删除合同模板，如果模板已被使用则不允许删除"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_DELETE + "')")
    public CommonResult<Boolean> deleteContractTemplate(
            @Parameter(description = "模板ID") @PathVariable("id") Long id) {
        log.info("删除合同模板: {}", id);
        boolean result = contractTemplateService.removeById(id);
        return CommonResult.success(result, "删除合同模板" + (result ? "成功" : "失败"));
    }

    /**
     * 分页查询合同模板
     */
    @GetMapping("/getContractTemplatePage")
    @Operation(
        summary = "分页查询合同模板",
        description = "根据条件分页查询合同模板列表，支持按模板名称、编码、状态等条件筛选"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    @Parameters({
            @Parameter(name = "current", description = "当前页码，从1开始", required = true),
            @Parameter(name = "size", description = "每页记录数", required = true),
            @Parameter(name = "queryDTO", description = "查询参数，包括模板名称、编码、状态等")
    })
    public CommonResult<IPage<ContractTemplateVO>> getContractTemplatePage(
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
    @GetMapping("/getContractTemplateList")
    @Operation(
        summary = "查询合同模板列表",
        description = "根据条件查询合同模板列表，不分页，支持按模板名称、编码、状态等条件筛选"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    public CommonResult<List<ContractTemplateVO>> getContractTemplateList(
            @Parameter(description = "查询参数，包括模板名称、编码、状态等") ContractTemplateQueryDTO queryDTO) {
        log.info("查询合同模板列表");
        List<ContractTemplateVO> templates = contractTemplateService.listTemplates(queryDTO);
        return CommonResult.success(templates);
    }
    
    /**
     * 提取模板变量
     */
    @PostMapping("/extractTemplateVariables")
    @Operation(
        summary = "提取模板变量",
        description = "从模板内容中提取所有变量，返回变量名称和描述的映射关系"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    public CommonResult<Map<String, String>> extractTemplateVariables(
            @Parameter(description = "模板内容") @RequestBody String templateContent) {
        log.info("提取模板变量");
        Map<String, String> variables = templateParser.extractVariables(templateContent);
        return CommonResult.success(variables);
    }
    
    /**
     * 验证模板变量
     */
    @PostMapping("/validateTemplateVariables")
    @Operation(
        summary = "验证模板变量",
        description = "验证模板变量是否完整，检查所有必填变量是否都已提供值"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_VIEW + "')")
    public CommonResult<Boolean> validateTemplateVariables(
            @Parameter(description = "请求参数，包括模板内容和变量值") @RequestBody Map<String, Object> request) {
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
    @PutMapping("/enableContractTemplate/{id}")
    @Operation(
        summary = "启用合同模板",
        description = "启用指定的合同模板，启用后可以被选择使用"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_EDIT + "')")
    public CommonResult<Boolean> enableContractTemplate(
            @Parameter(description = "模板ID") @PathVariable("id") Long id) {
        log.info("启用合同模板: {}", id);
        boolean result = contractTemplateService.enableTemplate(id);
        return CommonResult.success(result, "启用合同模板" + (result ? "成功" : "失败"));
    }
    
    /**
     * 禁用合同模板
     */
    @PutMapping("/disableContractTemplate/{id}")
    @Operation(
        summary = "禁用合同模板",
        description = "禁用指定的合同模板，禁用后不能被选择使用"
    )
    @PreAuthorize("hasAuthority('" + CONTRACT_EDIT + "')")
    public CommonResult<Boolean> disableContractTemplate(
            @Parameter(description = "模板ID") @PathVariable("id") Long id) {
        log.info("禁用合同模板: {}", id);
        boolean result = contractTemplateService.disableTemplate(id);
        return CommonResult.success(result, "禁用合同模板" + (result ? "成功" : "失败"));
    }
} 