package com.lawfirm.admin.controller.auth;

import com.lawfirm.admin.model.ApiResult;
import com.lawfirm.admin.model.request.auth.user.CreateUserRequest;
import com.lawfirm.admin.model.request.auth.user.UpdateUserRequest;
import com.lawfirm.admin.model.response.auth.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 系统用户Controller
 */
@Tag(name = "系统用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    @Operation(summary = "创建用户")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteUser(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<UserResponse> getUser(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<UserResponse>> pageUsers(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String username,
                                               @RequestParam(required = false) String realName,
                                               @RequestParam(required = false) Long deptId,
                                               @RequestParam(required = false) Integer status) {
        return ApiResult.success();
    }

    @Operation(summary = "重置密码")
    @PutMapping("/{id}/password/reset")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> resetPassword(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public ApiResult<Void> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return ApiResult.success();
    }

    @Operation(summary = "修改个人信息")
    @PutMapping("/profile")
    public ApiResult<Void> updateProfile(@Valid @RequestBody UpdateUserRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "获取个人信息")
    @GetMapping("/profile")
    public ApiResult<UserResponse> getProfile() {
        return ApiResult.success();
    }

    @Operation(summary = "分配角色")
    @PutMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return ApiResult.success();
    }

    @Operation(summary = "检查用户名是否存在")
    @GetMapping("/check/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkUsernameExists(@PathVariable String username) {
        return ApiResult.success();
    }

    @Operation(summary = "导出用户数据")
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportUsers() {
        // 导出逻辑
    }
} 
import com.lawfirm.model.base.enums.BaseEnum  
