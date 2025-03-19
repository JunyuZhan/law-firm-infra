package com.lawfirm.document.controller;

import com.lawfirm.model.document.service.DocumentPermissionService;
import com.lawfirm.common.core.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档权限管理控制器
 */
@Tag(name = "文档权限管理")
@RestController
@RequestMapping("/api/v1/document/permissions")
@RequiredArgsConstructor
@Validated
public class DocumentPermissionController {

    private final DocumentPermissionService documentPermissionService;

    @Operation(summary = "同步业务系统文档权限")
    @PostMapping("/sync")
    public ResponseResult<Void> syncBusinessDocumentsPermission(
            @Parameter(description = "业务类型") @RequestParam String businessType,
            @Parameter(description = "业务ID") @RequestParam Long businessId,
            @Parameter(description = "用户权限列表") @RequestBody List<DocumentPermissionService.UserPermission> userPermissions) {
        documentPermissionService.syncBusinessDocumentsPermission(businessType, businessId, userPermissions);
        return ResponseResult.success();
    }

    @Operation(summary = "添加用户文档权限")
    @PostMapping("/user")
    public ResponseResult<Void> addUserPermission(
            @Parameter(description = "业务类型") @RequestParam String businessType,
            @Parameter(description = "业务ID") @RequestParam Long businessId,
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "权限类型") @RequestParam String permission) {
        documentPermissionService.addUserPermission(businessType, businessId, userId, permission);
        return ResponseResult.success();
    }

    @Operation(summary = "移除用户文档权限")
    @DeleteMapping("/user")
    public ResponseResult<Void> removeUserPermission(
            @Parameter(description = "业务类型") @RequestParam String businessType,
            @Parameter(description = "业务ID") @RequestParam Long businessId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        documentPermissionService.removeUserPermission(businessType, businessId, userId);
        return ResponseResult.success();
    }
} 