package com.lawfirm.document.controller;

import com.lawfirm.model.document.service.DocumentPermissionService;
import com.lawfirm.common.core.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.lawfirm.document.constant.DocumentConstants;

import java.util.List;

/**
 * 文档权限管理控制器
 */
@Slf4j
@Tag(name = "文档权限", description = "文档权限管理接口")
@RestController("documentPermissionController")
@RequestMapping(DocumentConstants.API_PERMISSION_PREFIX)
@RequiredArgsConstructor
@Validated
public class DocumentPermissionController {

    private final DocumentPermissionService documentPermissionService;

    @Operation(
        summary = "同步业务系统文档权限",
        description = "将业务系统中的文档权限同步到文档系统，支持批量设置用户的文档访问权限"
    )
    @PostMapping("/sync")
    public CommonResult<Void> syncBusinessDocumentsPermission(
            @Parameter(description = "业务类型") @RequestParam String businessType,
            @Parameter(description = "业务ID") @RequestParam Long businessId,
            @Parameter(description = "用户权限列表") @RequestBody List<DocumentPermissionService.UserPermission> userPermissions) {
        documentPermissionService.syncBusinessDocumentsPermission(businessType, businessId, userPermissions);
        return CommonResult.success();
    }

    @Operation(
        summary = "添加用户文档权限",
        description = "为指定用户添加文档的访问权限，可以设置不同级别的权限类型（如：只读、编辑等）"
    )
    @PostMapping("/user")
    public CommonResult<Void> addUserPermission(
            @Parameter(description = "业务类型") @RequestParam String businessType,
            @Parameter(description = "业务ID") @RequestParam Long businessId,
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "权限类型") @RequestParam String permission) {
        documentPermissionService.addUserPermission(businessType, businessId, userId, permission);
        return CommonResult.success();
    }

    @Operation(
        summary = "移除用户文档权限",
        description = "移除指定用户对文档的所有访问权限"
    )
    @DeleteMapping("/user")
    public CommonResult<Void> removeUserPermission(
            @Parameter(description = "业务类型") @RequestParam String businessType,
            @Parameter(description = "业务ID") @RequestParam Long businessId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        documentPermissionService.removeUserPermission(businessType, businessId, userId);
        return CommonResult.success();
    }
} 