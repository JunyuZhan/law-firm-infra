package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户控制器
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @Operation(summary = "创建用户")
    @PostMapping
    public R<Void> createUser(@Validated @RequestBody SysUser user) {
        userService.createUser(user);
        return R.ok();
    }

    @Operation(summary = "更新用户")
    @PutMapping
    public R<Void> updateUser(@Validated @RequestBody SysUser user) {
        userService.updateUser(user);
        return R.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public R<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return R.ok();
    }

    @Operation(summary = "重置密码")
    @PutMapping("/{id}/password")
    public R<Void> resetPassword(@PathVariable Long id, @RequestParam String password) {
        userService.resetPassword(id, password);
        return R.ok();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public R<Void> changePassword(@RequestParam Long id, 
                                @RequestParam String oldPassword,
                                @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return R.ok();
    }

    @Operation(summary = "根据用户名查询用户")
    @GetMapping("/username/{username}")
    public R<SysUser> getByUsername(@PathVariable String username) {
        return R.ok(userService.getByUsername(username));
    }

    @Operation(summary = "根据部门ID查询用户列表")
    @GetMapping("/dept/{deptId}")
    public R<List<SysUser>> listByDeptId(@PathVariable Long deptId) {
        return R.ok(userService.listByDeptId(deptId));
    }

    @Operation(summary = "根据角色ID查询用户列表")
    @GetMapping("/role/{roleId}")
    public R<List<SysUser>> listByRoleId(@PathVariable Long roleId) {
        return R.ok(userService.listByRoleId(roleId));
    }

    @Operation(summary = "分配用户角色")
    @PutMapping("/{userId}/roles")
    public R<Void> assignRoles(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        userService.assignRoles(userId, roleIds);
        return R.ok();
    }
} 