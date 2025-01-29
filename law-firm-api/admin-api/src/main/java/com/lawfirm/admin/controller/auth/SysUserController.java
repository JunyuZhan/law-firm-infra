package com.lawfirm.admin.controller.auth;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.auth.model.dto.SysUserDTO;
import com.lawfirm.auth.model.vo.SysUserVO;
import com.lawfirm.auth.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统用户Controller
 */
@Api(tags = "系统用户")
@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @ApiOperation("创建用户")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createUser(@Valid @RequestBody SysUserDTO dto) {
        userService.createUser(dto);
        return ApiResult.ok();
    }

    @ApiOperation("更新用户")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateUser(@PathVariable Long id, @Valid @RequestBody SysUserDTO dto) {
        userService.updateUser(id, dto);
        return ApiResult.ok();
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResult.ok();
    }

    @ApiOperation("获取用户详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<SysUserVO> getUser(@PathVariable Long id) {
        return ApiResult.ok(userService.getUser(id));
    }

    @ApiOperation("分页查询用户")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysUserVO>> pageUsers(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String username,
                                               @RequestParam(required = false) String realName,
                                               @RequestParam(required = false) Long deptId,
                                               @RequestParam(required = false) Integer status) {
        return ApiResult.ok(userService.pageUsers(pageNum, pageSize, username, realName, deptId, status));
    }

    @ApiOperation("重置密码")
    @PutMapping("/{id}/password/reset")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> resetPassword(@PathVariable Long id) {
        userService.resetPassword(id);
        return ApiResult.ok();
    }

    @ApiOperation("修改密码")
    @PutMapping("/password")
    public ApiResult<Void> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.updatePassword(oldPassword, newPassword);
        return ApiResult.ok();
    }

    @ApiOperation("更新个人信息")
    @PutMapping("/profile")
    public ApiResult<Void> updateProfile(@Valid @RequestBody SysUserDTO dto) {
        userService.updateProfile(dto);
        return ApiResult.ok();
    }

    @ApiOperation("分配角色")
    @PutMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.assignRoles(id, roleIds);
        return ApiResult.ok();
    }

    @ApiOperation("检查用户名是否存在")
    @GetMapping("/check/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkUsernameExists(@PathVariable String username) {
        return ApiResult.ok(userService.checkUsernameExists(username));
    }

    @ApiOperation("导出用户数据")
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportUsers() {
        userService.exportUsers();
    }
} 