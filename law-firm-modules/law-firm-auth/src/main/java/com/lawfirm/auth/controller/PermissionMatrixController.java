package com.lawfirm.auth.controller;

import com.lawfirm.auth.constant.AuthApiConstants;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.permission.PermissionMatrixDTO;
import com.lawfirm.model.auth.enums.DataScopeEnum;
import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import com.lawfirm.model.auth.service.PermissionMatrixService;
import com.lawfirm.model.auth.vo.PermissionMatrixVO;
import com.lawfirm.model.auth.enums.RoleEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 权限矩阵控制器
 * 提供权限矩阵相关的REST API接口
 *
 * @author System
 */
@Slf4j
@RestController
@RequestMapping(AuthApiConstants.Api.PERMISSION_MATRIX)
@RequiredArgsConstructor
@Tag(name = "权限矩阵管理", description = "权限矩阵查询和权限检查接口")
public class PermissionMatrixController {

    private final PermissionMatrixService permissionMatrixService;

    @Operation(summary = "获取完整权限矩阵", description = "获取所有角色对所有模块的权限配置矩阵")
    @GetMapping("/full")
    @PreAuthorize("hasAuthority('system:permission:read')")
    public CommonResult<PermissionMatrixVO> getFullPermissionMatrix() {
        log.info("获取完整权限矩阵");
        PermissionMatrixVO matrix = permissionMatrixService.getFullPermissionMatrix();
        return CommonResult.success(matrix);
    }

    @Operation(summary = "获取当前用户权限矩阵", description = "获取当前登录用户的个性化权限矩阵")
    @GetMapping("/current-user")
    public CommonResult<PermissionMatrixVO> getCurrentUserPermissionMatrix() {
        // 这里需要从安全上下文获取当前用户ID
        Long currentUserId = getCurrentUserId();
        log.info("获取当前用户权限矩阵，用户ID: {}", currentUserId);
        
        PermissionMatrixVO matrix = permissionMatrixService.getUserPermissionMatrix(currentUserId);
        return CommonResult.success(matrix);
    }

    @Operation(summary = "获取指定用户权限矩阵", description = "获取指定用户的权限矩阵")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('system:permission:read')")
    public CommonResult<PermissionMatrixVO> getUserPermissionMatrix(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        log.info("获取用户权限矩阵，用户ID: {}", userId);
        PermissionMatrixVO matrix = permissionMatrixService.getUserPermissionMatrix(userId);
        return CommonResult.success(matrix);
    }

    @Operation(summary = "获取角色权限矩阵", description = "获取指定角色的权限矩阵")
    @GetMapping("/role/{roleCode}")
    @PreAuthorize("hasAuthority('system:permission:read')")
    public CommonResult<PermissionMatrixVO> getRolePermissionMatrix(
            @Parameter(description = "角色编码") @PathVariable String roleCode) {
        log.info("获取角色权限矩阵，角色编码: {}", roleCode);
        
        RoleEnum roleEnum = RoleEnum.getByCode(roleCode);
         if (roleEnum == null) {
             return CommonResult.error("无效的角色编码: " + roleCode);
         }
         PermissionMatrixVO matrix = permissionMatrixService.getRolePermissionMatrix(roleEnum);
         return CommonResult.success(matrix);
    }

    @Operation(summary = "检查用户权限", description = "检查用户是否有指定模块的操作权限")
    @GetMapping("/check")
    public CommonResult<Boolean> checkUserPermission(
            @Parameter(description = "模块编码") @RequestParam String moduleCode,
            @Parameter(description = "操作编码") @RequestParam String operationCode,
            @Parameter(description = "用户ID，不传则检查当前用户") @RequestParam(required = false) Long userId) {
        
        Long targetUserId = userId != null ? userId : getCurrentUserId();
        log.debug("检查用户权限，用户ID: {}, 模块: {}, 操作: {}", targetUserId, moduleCode, operationCode);
        
        ModuleTypeEnum moduleType = getModuleTypeByCode(moduleCode);
        OperationTypeEnum operationType = OperationTypeEnum.getByCode(operationCode);
        
        if (moduleType == null || operationType == null) {
            return CommonResult.error("无效的模块编码或操作编码");
        }
        
        boolean hasPermission = permissionMatrixService.checkUserPermission(targetUserId, moduleType, operationType);
        return CommonResult.success(hasPermission);
    }

    @Operation(summary = "获取用户数据范围", description = "获取用户在指定模块的数据访问范围")
    @GetMapping("/data-scope")
    public CommonResult<DataScopeEnum> getUserDataScope(
            @Parameter(description = "模块编码") @RequestParam String moduleCode,
            @Parameter(description = "用户ID，不传则检查当前用户") @RequestParam(required = false) Long userId) {
        
        Long targetUserId = userId != null ? userId : getCurrentUserId();
        log.debug("获取用户数据范围，用户ID: {}, 模块: {}", targetUserId, moduleCode);
        
        ModuleTypeEnum moduleType = getModuleTypeByCode(moduleCode);
        if (moduleType == null) {
            return CommonResult.error("无效的模块编码: " + moduleCode);
        }
        
        DataScopeEnum dataScope = permissionMatrixService.getUserDataScope(targetUserId, moduleType);
        return CommonResult.success(dataScope);
    }

    @Operation(summary = "批量检查用户权限", description = "一次性检查用户的多个权限")
    @PostMapping("/batch-check")
    public CommonResult<Map<String, Boolean>> batchCheckUserPermissions(
            @Parameter(description = "权限检查请求列表") @Valid @RequestBody List<PermissionMatrixDTO> permissionRequests,
            @Parameter(description = "用户ID，不传则检查当前用户") @RequestParam(required = false) Long userId) {
        
        Long targetUserId = userId != null ? userId : getCurrentUserId();
        log.debug("批量检查用户权限，用户ID: {}, 请求数量: {}", targetUserId, permissionRequests.size());
        
        Map<String, Boolean> results = permissionMatrixService.batchCheckUserPermissions(targetUserId, permissionRequests);
        return CommonResult.success(results);
    }

    @Operation(summary = "获取用户可访问模块", description = "获取用户可以访问的模块列表")
    @GetMapping("/accessible-modules")
    public CommonResult<List<ModuleTypeEnum>> getUserAccessibleModules(
            @Parameter(description = "用户ID，不传则检查当前用户") @RequestParam(required = false) Long userId) {
        
        Long targetUserId = userId != null ? userId : getCurrentUserId();
        log.debug("获取用户可访问模块，用户ID: {}", targetUserId);
        
        List<ModuleTypeEnum> modules = permissionMatrixService.getUserAccessibleModules(targetUserId);
        return CommonResult.success(modules);
    }

    @Operation(summary = "获取用户模块操作权限", description = "获取用户在指定模块的可执行操作列表")
    @GetMapping("/module-operations")
    public CommonResult<List<OperationTypeEnum>> getUserModuleOperations(
            @Parameter(description = "模块编码") @RequestParam String moduleCode,
            @Parameter(description = "用户ID，不传则检查当前用户") @RequestParam(required = false) Long userId) {
        
        Long targetUserId = userId != null ? userId : getCurrentUserId();
        log.debug("获取用户模块操作权限，用户ID: {}, 模块: {}", targetUserId, moduleCode);
        
        ModuleTypeEnum moduleType = getModuleTypeByCode(moduleCode);
        if (moduleType == null) {
            return CommonResult.error("无效的模块编码: " + moduleCode);
        }
        
        List<OperationTypeEnum> operations = permissionMatrixService.getUserModuleOperations(targetUserId, moduleType);
        return CommonResult.success(operations);
    }

    @Operation(summary = "刷新权限矩阵缓存", description = "清除权限矩阵缓存，强制重新生成")
    @PostMapping("/refresh")
    @PreAuthorize("hasAuthority('system:permission:manage')")
    public CommonResult<Void> refreshPermissionMatrix() {
        log.info("刷新权限矩阵缓存");
        permissionMatrixService.refreshPermissionMatrix();
        return CommonResult.success();
    }

    @Operation(summary = "验证权限矩阵配置", description = "验证权限矩阵配置的完整性和合理性")
    @GetMapping("/validate")
    @PreAuthorize("hasAuthority('system:permission:read')")
    public CommonResult<Map<String, Object>> validatePermissionMatrix() {
        log.info("验证权限矩阵配置");
        Map<String, Object> report = permissionMatrixService.validatePermissionMatrix();
        return CommonResult.success(report);
    }

    /**
     * 获取当前登录用户ID
     * 这里需要根据实际的安全框架实现
     */
    private Long getCurrentUserId() {
        // TODO: 从Spring Security上下文或其他安全框架获取当前用户ID
        // 这里暂时返回一个示例值，实际实现需要根据项目的认证机制
        return 1L; // 示例用户ID
    }

    /**
     * 根据模块编码获取模块类型枚举
     */
    private ModuleTypeEnum getModuleTypeByCode(String moduleCode) {
        for (ModuleTypeEnum moduleType : ModuleTypeEnum.values()) {
            if (moduleType.getCode().equals(moduleCode)) {
                return moduleType;
            }
        }
        return null;
    }
}