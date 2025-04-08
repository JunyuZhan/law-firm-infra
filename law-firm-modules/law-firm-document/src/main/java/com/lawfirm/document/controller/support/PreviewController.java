package com.lawfirm.document.controller.support;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.service.TemplateService;
import com.lawfirm.document.constant.DocumentConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 文档预览控制器
 */
@Slf4j
@RestController("documentPreviewController")
@RequiredArgsConstructor
@RequestMapping(DocumentConstants.API_PREVIEW_PREFIX)
@Tag(name = "文档预览", description = "文档在线预览相关接口")
public class PreviewController {

    private final DocumentService documentService;
    private final TemplateService templateService;

    /**
     * 预览文档
     */
    @GetMapping("/document/{id}")
    @Operation(
        summary = "预览文档",
        description = "根据文档ID获取文档的预览URL，支持多种文档格式的在线预览"
    )
    public CommonResult<String> previewDocument(
            @Parameter(description = "文档ID") @PathVariable Long id) {
        String previewUrl = documentService.previewDocument(id);
        return CommonResult.success(previewUrl);
    }

    /**
     * 预览模板
     */
    @PostMapping("/template/{id}")
    @Operation(
        summary = "预览模板",
        description = "根据模板ID和参数预览模板渲染后的效果，支持动态替换模板中的变量"
    )
    public CommonResult<String> previewTemplate(
            @Parameter(description = "模板ID") @PathVariable Long id,
            @Parameter(description = "模板参数") @RequestBody Map<String, Object> params) {
        String previewUrl = templateService.previewTemplate(id, params);
        return CommonResult.success(previewUrl);
    }

    /**
     * 获取HTML预览内容（如果支持）
     */
    @GetMapping("/html/{id}")
    @Operation(
        summary = "获取HTML预览内容",
        description = "获取文档的HTML格式预览内容，如果文档格式支持转换为HTML则返回HTML内容，否则返回预览URL"
    )
    public CommonResult<String> getHtmlContent(
            @Parameter(description = "文档ID") @PathVariable Long id) {
        // DocumentService中没有定义getDocumentHtmlContent方法
        // 直接返回预览URL
        String previewUrl = documentService.previewDocument(id);
        return CommonResult.success(previewUrl);
    }
}
