package com.lawfirm.document.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.service.TemplateService;
import com.lawfirm.model.document.vo.TemplateVO;
import com.lawfirm.document.constant.DocumentConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 模板管理控制器
 */
@Slf4j
@Tag(name = "文档模板", description = "文档模板管理接口")
@RestController("documentTemplateController")
@RequestMapping(DocumentConstants.API_TEMPLATE_PREFIX)
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    /**
     * 创建模板
     */
    @PostMapping
    @Operation(
        summary = "创建模板",
        description = "创建新的文档模板，包括模板名称、编码、文档类型、业务类型等基本信息"
    )
    @PreAuthorize(DOCUMENT_CREATE)
    public CommonResult<Long> createTemplate(
            @Parameter(description = "创建参数") @RequestBody @Validated TemplateCreateDTO createDTO) {
        Long templateId = templateService.createTemplate(createDTO);
        return CommonResult.success(templateId);
    }

    /**
     * 更新模板
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "更新模板",
        description = "更新已存在的文档模板信息，包括模板名称、编码、文档类型、业务类型等"
    )
    @PreAuthorize(DOCUMENT_EDIT)
    public CommonResult<Void> updateTemplate(
            @Parameter(description = "模板ID") @PathVariable Long id,
            @Parameter(description = "更新参数") @RequestBody @Validated TemplateUpdateDTO updateDTO) {
        templateService.updateTemplate(id, updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除模板
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "删除模板",
        description = "根据ID删除单个文档模板，同时删除模板文件"
    )
    @PreAuthorize(DOCUMENT_DELETE)
    public CommonResult<Void> deleteTemplate(
            @Parameter(description = "模板ID") @PathVariable Long id) {
        templateService.deleteTemplate(id);
        return CommonResult.success();
    }

    /**
     * 批量删除模板
     */
    @DeleteMapping("/batch")
    @Operation(
        summary = "批量删除模板",
        description = "批量删除多个文档模板，支持同时删除多个模板及其文件"
    )
    @PreAuthorize(DOCUMENT_DELETE)
    public CommonResult<Void> deleteTemplates(
            @Parameter(description = "模板ID列表") @RequestBody List<Long> ids) {
        templateService.deleteTemplates(ids);
        return CommonResult.success();
    }

    /**
     * 获取模板详情
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "获取模板详情",
        description = "根据ID获取模板的详细信息，包括基本信息和变量定义等"
    )
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<TemplateVO> getTemplate(
            @Parameter(description = "模板ID") @PathVariable Long id) {
        TemplateVO template = templateService.getTemplateById(id);
        return CommonResult.success(template);
    }

    /**
     * 根据编码获取模板
     */
    @GetMapping("/code/{code}")
    @Operation(
        summary = "根据编码获取模板",
        description = "根据模板编码获取模板详情，编码是模板的唯一业务标识"
    )
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<TemplateVO> getTemplateByCode(
            @Parameter(description = "模板编码") @PathVariable String code) {
        TemplateVO template = templateService.getTemplateByCode(code);
        return CommonResult.success(template);
    }

    /**
     * 分页查询模板
     */
    @GetMapping
    @Operation(
        summary = "分页查询模板",
        description = "分页获取模板列表，支持按文档类型、业务类型等条件筛选"
    )
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<Page<TemplateVO>> pageTemplates(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "文档类型") @RequestParam(required = false) String docType,
            @Parameter(description = "业务类型") @RequestParam(required = false) String businessType) {
        Page<TemplateDocument> page = new Page<>(pageNum, pageSize);
        TemplateDocument template = new TemplateDocument();
        template.setDocType(docType);
        template.setBusinessType(businessType);
        Page<TemplateVO> result = templateService.pageTemplates(page, template);
        return CommonResult.success(result);
    }

    /**
     * 列出业务类型下的模板
     */
    @GetMapping("/business/{businessType}")
    @Operation(
        summary = "列出业务类型下的模板",
        description = "获取指定业务类型下的所有模板列表，用于业务系统选择模板"
    )
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<List<TemplateVO>> listTemplatesByBusinessType(
            @Parameter(description = "业务类型") @PathVariable String businessType) {
        List<TemplateVO> templates = templateService.listTemplatesByBusinessType(businessType);
        return CommonResult.success(templates);
    }

    /**
     * 生成文档
     */
    @PostMapping("/{id}/generate")
    @Operation(
        summary = "生成文档",
        description = "根据模板和参数生成新的文档，支持变量替换和格式转换"
    )
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<Long> generateDocument(
            @Parameter(description = "模板ID") @PathVariable Long id,
            @Parameter(description = "参数数据") @RequestBody Map<String, Object> params) {
        Long documentId = templateService.generateDocument(id, params);
        return CommonResult.success(documentId);
    }

    /**
     * 预览模板
     */
    @PostMapping("/{id}/preview")
    @Operation(
        summary = "预览模板",
        description = "预览模板渲染后的效果，支持实时预览变量替换的结果"
    )
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<String> previewTemplate(
            @Parameter(description = "模板ID") @PathVariable Long id,
            @Parameter(description = "参数数据") @RequestBody Map<String, Object> params) {
        String previewUrl = templateService.previewTemplate(id, params);
        return CommonResult.success(previewUrl);
    }
}
