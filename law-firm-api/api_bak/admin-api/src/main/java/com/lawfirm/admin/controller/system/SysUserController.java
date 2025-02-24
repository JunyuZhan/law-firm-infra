package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.admin.client.system.SysUserClient;
import com.lawfirm.admin.model.request.system.user.*;
import com.lawfirm.admin.model.response.system.user.SysUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "系统用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserClient sysUserClient;

    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('system:user:query')")
    @OperationLog(description = "分页查询用户", operationType = "QUERY")
    public Result<PageResult<SysUserResponse>> page(@Validated SysUserPageRequest request) {
        return sysUserClient.page(request);
    }

    @Operation(summary = "创建用户")
    @PostMapping
    @PreAuthorize("hasAuthority('system:user:create')")
    @OperationLog(description = "创建用户", operationType = "CREATE")
    public Result<Void> create(@RequestBody @Validated SysUserCreateRequest request) {
        return sysUserClient.create(request);
    }

    @Operation(summary = "修改用户")
    @PutMapping
    @PreAuthorize("hasAuthority('system:user:update')")
    @OperationLog(description = "修改用户", operationType = "UPDATE")
    public Result<Void> update(@RequestBody @Validated SysUserUpdateRequest request) {
        return sysUserClient.update(request);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @OperationLog(description = "删除用户", operationType = "DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        return sysUserClient.delete(id);
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:query')")
    @OperationLog(description = "获取用户详情", operationType = "QUERY")
    public Result<SysUserResponse> get(@PathVariable Long id) {
        return sysUserClient.get(id);
    }

    @Operation(summary = "重置密码")
    @PutMapping("/reset-password")
    @PreAuthorize("hasAuthority('system:user:update')")
    @OperationLog(description = "重置密码", operationType = "UPDATE")
    public Result<Void> resetPassword(@RequestBody @Validated SysUserResetPasswordRequest request) {
        return sysUserClient.resetPassword(request);
    }

    @Operation(summary = "修改密码")
    @PutMapping("/update-password")
    @PreAuthorize("hasAuthority('system:user:update')")
    @OperationLog(description = "修改密码", operationType = "UPDATE")
    public Result<Void> updatePassword(@RequestBody @Validated SysUserUpdatePasswordRequest request) {
        return sysUserClient.updatePassword(request);
    }

    @Operation(summary = "修改状态")
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('system:user:update')")
    @OperationLog(description = "修改用户状态", operationType = "UPDATE")
    public Result<Void> updateStatus(@RequestBody @Validated SysUserUpdateStatusRequest request) {
        return sysUserClient.updateStatus(request);
    }
} 
import com.lawfirm.model.base.enums.BaseEnum  
