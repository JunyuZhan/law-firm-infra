package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.admin.client.system.SysMenuClient;
import com.lawfirm.admin.model.request.system.menu.*;
import com.lawfirm.admin.model.response.system.menu.SysMenuResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统菜单管理")
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuClient sysMenuClient;

    @Operation(summary = "获取菜单树")
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:menu:query')")
    @OperationLog(description = "获取菜单树", operationType = "QUERY")
    public Result<List<SysMenuResponse>> tree() {
        return sysMenuClient.tree();
    }

    @Operation(summary = "创建菜单")
    @PostMapping
    @PreAuthorize("hasAuthority('system:menu:create')")
    @OperationLog(description = "创建菜单", operationType = "CREATE")
    public Result<Void> create(@RequestBody @Validated SysMenuCreateRequest request) {
        return sysMenuClient.create(request);
    }

    @Operation(summary = "修改菜单")
    @PutMapping
    @PreAuthorize("hasAuthority('system:menu:update')")
    @OperationLog(description = "修改菜单", operationType = "UPDATE")
    public Result<Void> update(@RequestBody @Validated SysMenuUpdateRequest request) {
        return sysMenuClient.update(request);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:delete')")
    @OperationLog(description = "删除菜单", operationType = "DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        return sysMenuClient.delete(id);
    }

    @Operation(summary = "获取菜单详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    @OperationLog(description = "获取菜单详情", operationType = "QUERY")
    public Result<SysMenuResponse> get(@PathVariable Long id) {
        return sysMenuClient.get(id);
    }

    @Operation(summary = "获取角色菜单")
    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    @OperationLog(description = "获取角色菜单", operationType = "QUERY")
    public Result<List<Long>> getRoleMenuIds(@PathVariable Long roleId) {
        return sysMenuClient.getRoleMenuIds(roleId);
    }

    @Operation(summary = "获取用户菜单")
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('system:menu:query')")
    @OperationLog(description = "获取用户菜单", operationType = "QUERY")
    public Result<List<SysMenuResponse>> getUserMenus() {
        return sysMenuClient.getUserMenus();
    }
} 