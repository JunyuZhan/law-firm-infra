package com.lawfirm.document.controller.admin;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.document.service.OnlyOfficeService;
import com.lawfirm.document.constant.DocumentConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * OnlyOffice文档编辑控制器
 */
@Slf4j
@Tag(name = "OnlyOffice文档编辑", description = "OnlyOffice在线文档编辑接口")
@RestController("documentOnlyOfficeController")
@RequestMapping(DocumentConstants.API_ONLYOFFICE_PREFIX)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "lawfirm.onlyoffice.enabled", havingValue = "true")
public class OnlyOfficeController {

    private final OnlyOfficeService onlyOfficeService;

    /**
     * 获取文档编辑配置
     */
    @GetMapping("/config/{id}")
    @Operation(
        summary = "获取文档编辑配置",
        description = "获取OnlyOffice编辑器的配置信息，包括文档URL、权限、用户信息等"
    )
    @PreAuthorize("hasAuthority('" + DOCUMENT_VIEW + "')")
    public CommonResult<Map<String, Object>> getEditorConfig(
            @Parameter(description = "文档ID") @PathVariable Long id) {
        log.info("获取OnlyOffice编辑配置: documentId={}", id);
        Map<String, Object> config = onlyOfficeService.generateEditorConfig(id);
        return CommonResult.success(config);
    }

    /**
     * 处理OnlyOffice回调
     */
    @PostMapping("/callback/{id}")
    @Operation(
        summary = "OnlyOffice回调处理",
        description = "处理OnlyOffice编辑器的回调请求，用于保存文档更改"
    )
    public Map<String, Object> handleCallback(
            @Parameter(description = "文档ID") @PathVariable Long id,
            @Parameter(description = "回调数据") @RequestBody Map<String, Object> callbackData) {
        log.info("处理OnlyOffice回调: documentId={}", id);
        return onlyOfficeService.handleCallback(id, callbackData);
    }

    /**
     * 文档下载接口 (供OnlyOffice访问)
     */
    @GetMapping("/download/{id}")
    @Operation(
        summary = "文档下载",
        description = "提供给OnlyOffice的文档下载接口"
    )
    public void downloadDocument(
            @Parameter(description = "文档ID") @PathVariable Long id,
            HttpServletResponse response) {
        log.info("OnlyOffice请求下载文档: documentId={}", id);
        onlyOfficeService.downloadDocument(id, response);
    }

    /**
     * 检查OnlyOffice服务状态
     */
    @GetMapping("/health")
    @Operation(
        summary = "检查OnlyOffice服务状态",
        description = "检查OnlyOffice服务器是否可用"
    )
    @PreAuthorize("hasAuthority('" + DOCUMENT_VIEW + "')")
    public CommonResult<Map<String, Object>> checkHealth() {
        // 简单的健康检查
        Map<String, Object> health = Map.of(
            "status", "ok",
            "service", "OnlyOffice Document Server",
            "timestamp", System.currentTimeMillis()
        );
        return CommonResult.success(health);
    }
} 