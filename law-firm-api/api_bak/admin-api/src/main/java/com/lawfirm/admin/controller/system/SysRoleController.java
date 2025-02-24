package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.admin.client.system.SysRoleClient;
import com.lawfirm.admin.model.request.system.role.*;
import com.lawfirm.admin.model.response.system.role.SysRoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleClient sysRoleClient;

    @Operation(summary = "分页查询角色")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('system:role:query')")
    @OperationLog(description = "分页查询角色", operationType = "QUERY")
    public Result<PageResult<SysRoleResponse>> page(@Validated SysRolePageRequest request) {
        return sysRoleClient.page(request);
    }

    @Operation(summary = "创建角色")
    @PostMapping
    @PreAuthorize("hasAuthority('system:role:create')")
    @OperationLog(description = "创建角色", operationType = "CREATE")
    public Result<Void> create(@RequestBody @Validated SysRoleCreateRequest request) {
        return sysRoleClient.create(request);
    }

    @Operation(summary = "修改角色")
    @PutMapping
    @PreAuthorize("hasAuthority('system:role:update')")
    @OperationLog(description = "修改角色", operationType = "UPDATE")
    public Result<Void> update(@RequestBody @Validated SysRoleUpdateRequest request) {
        return sysRoleClient.update(request);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:delete')")
    @OperationLog(description = "删除角色", operationType = "DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        return sysRoleClient.delete(id);
    }

    @Operation(summary = "获取角色详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:query')")
    @OperationLog(description = "获取角色详情", operationType = "QUERY")
    public Result<SysRoleResponse> get(@PathVariable Long id) {
        return sysRoleClient.get(id);
    }

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:role:query')")
    @OperationLog(description = "获取角色列表", operationType = "QUERY")
    public Result<List<SysRoleResponse>> list() {
        return sysRoleClient.list();
    }

    @Operation(summary = "修改角色状态")
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('system:role:update')")
    @OperationLog(description = "修改角色状态", operationType = "UPDATE")
    public Result<Void> updateStatus(@RequestBody @Validated SysRoleUpdateStatusRequest request) {
        return sysRoleClient.updateStatus(request);
    }

    @Operation(summary = "分配角色菜单权限")
    @PutMapping("/menu")
    @PreAuthorize("hasAuthority('system:role:update')")
    @OperationLog(description = "分配角色菜单权限", operationType = "UPDATE")
    public Result<Void> assignMenu(@RequestBody @Validated SysRoleAssignMenuRequest request) {
        return sysRoleClient.assignMenu(request);
    }
} 
import com.lawfirm.model.base.enums.BaseEnum  
