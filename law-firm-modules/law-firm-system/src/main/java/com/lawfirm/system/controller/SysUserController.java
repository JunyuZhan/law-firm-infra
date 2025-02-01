package com.lawfirm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.domain.R;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.system.model.dto.SysUserDTO;
import com.lawfirm.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户控制器
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public R<PageResult<SysUserDTO>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            SysUserDTO user) {
        Page<SysUser> page = new Page<>(current, size);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        // TODO: 根据DTO构建查询条件
        return R.ok(userService.page(page, queryWrapper));
    }

    @ApiOperation("查询列表")
    @GetMapping("/list")
    public R<List<SysUserDTO>> list(SysUserDTO user) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        // TODO: 根据DTO构建查询条件
        return R.ok(userService.list(queryWrapper));
    }

    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    public R<SysUserDTO> getById(@PathVariable Long id) {
        return R.ok(userService.findById(id));
    }

    @ApiOperation("新增")
    @PostMapping
    public R<SysUserDTO> add(@Validated @RequestBody SysUserDTO user) {
        return R.ok(userService.createUser(user));
    }

    @ApiOperation("修改")
    @PutMapping
    public R<SysUserDTO> update(@Validated @RequestBody SysUserDTO user) {
        return R.ok(userService.updateUser(user));
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable Long id) {
        userService.deleteUser(id);
        return R.ok();
    }

    @ApiOperation("修改密码")
    @PutMapping("/password")
    public R<Void> changePassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.changePassword(userId, oldPassword, newPassword);
        return R.ok();
    }

    @ApiOperation("根据用户名查询用户")
    @GetMapping("/username/{username}")
    public R<SysUserDTO> getByUsername(@PathVariable String username) {
        return R.ok(userService.getByUsername(username));
    }

    @ApiOperation("根据部门ID查询用户列表")
    @GetMapping("/dept/{deptId}")
    public R<List<SysUserDTO>> listByDeptId(@PathVariable Long deptId) {
        return R.ok(userService.listByDeptId(deptId));
    }

    @ApiOperation("根据角色ID查询用户列表")
    @GetMapping("/role/{roleId}")
    public R<List<SysUserDTO>> listByRoleId(@PathVariable Long roleId) {
        return R.ok(userService.listByRoleId(roleId));
    }

    @ApiOperation("分配用户角色")
    @PutMapping("/{userId}/roles")
    public R<Void> assignRoles(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        userService.updateUserRoles(userId, roleIds);
        return R.ok();
    }

    @ApiOperation("修改个人信息")
    @PutMapping("/profile")
    public R<SysUserDTO> updateProfile(@Validated @RequestBody SysUserDTO user) {
        return R.ok(userService.updateProfile(user));
    }

    @ApiOperation("修改头像")
    @PutMapping("/avatar")
    public R<Void> updateAvatar(@RequestParam Long userId, @RequestParam String avatar) {
        userService.updateAvatar(userId, avatar);
        return R.ok();
    }
} 