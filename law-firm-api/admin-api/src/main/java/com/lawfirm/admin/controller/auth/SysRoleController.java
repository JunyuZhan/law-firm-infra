package com.lawfirm.admin.controller.auth;

import com.lawfirm.admin.model.ApiResult;
import com.lawfirm.admin.model.request.auth.role.CreateRoleRequest;
import com.lawfirm.admin.model.request.auth.role.UpdateRoleRequest;
import com.lawfirm.admin.model.response.RoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 系统角色Controller
 */
@Tag(name = "系统角色管理")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {

    @Operation(summary = "创建角色")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createRole(@Valid @RequestBody CreateRoleRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateRole(@PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteRole(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "获取角色详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<RoleResponse> getRole(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "分页查询角色")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<RoleResponse>> pageRoles(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String roleName,
                                               @RequestParam(required = false) String roleCode,
                                               @RequestParam(required = false) Integer status) {
        return ApiResult.success();
    }

    @Operation(summary = "获取所有角色")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<RoleResponse>> listRoles() {
        return ApiResult.success();
    }

    @Operation(summary = "分配菜单权限")
    @PutMapping("/{id}/menus")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> assignMenus(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        return ApiResult.success();
    }

    @Operation(summary = "获取角色菜单ID列表")
    @GetMapping("/{id}/menus")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<Long>> getRoleMenuIds(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "检查角色编码是否存在")
    @GetMapping("/check/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkRoleCodeExists(@PathVariable String code) {
        return ApiResult.success();
    }

    @Operation(summary = "导出角色数据")
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportRoles() {
        // 导出逻辑
    }
} 