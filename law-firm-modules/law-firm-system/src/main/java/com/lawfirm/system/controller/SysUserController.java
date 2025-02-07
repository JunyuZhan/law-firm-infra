package com.lawfirm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.model.system.dto.SysUserDTO;
import com.lawfirm.model.system.vo.SysUserVO;
import com.lawfirm.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.common.web.controller.BaseController;

import java.util.List;

/**
 * 系统用户控制器
 */
@Tag(name = "系统用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController extends BaseController {

    private final SysUserService userService;

    @Operation(summary = "创建用户")
    @PostMapping
    public R<SysUserVO> create(@Valid @RequestBody SysUserDTO user) {
        return R.ok(userService.create(user));
    }

    @Operation(summary = "更新用户")
    @PutMapping
    public R<SysUserVO> update(@Valid @RequestBody SysUserDTO user) {
        return R.ok(userService.update(user));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return R.ok();
    }

    @Operation(summary = "批量删除用户")
    @DeleteMapping("/batch")
    public R<Void> deleteBatch(@RequestBody List<Long> ids) {
        userService.deleteBatch(ids);
        return R.ok();
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public R<SysUserVO> get(@PathVariable Long id) {
        return R.ok(userService.findById(id));
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public R<List<SysUserVO>> list(SysUserDTO query) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        // TODO: 构建查询条件
        return R.ok(userService.list(wrapper));
    }

    @Operation(summary = "分页获取用户列表")
    @GetMapping("/page")
    public R<PageResult<SysUserVO>> page(@Valid SysUserDTO query,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return R.ok(userService.pageUsers(pageNum, pageSize, query));
    }

    @Operation(summary = "重置密码")
    @PutMapping("/{userId}/password/reset")
    public R<Void> resetPassword(@PathVariable Long userId, @RequestParam String password) {
        userService.resetPassword(userId, password);
        return R.ok();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public R<Void> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassword(oldPassword, newPassword);
        return R.ok();
    }

    @Operation(summary = "更新头像")
    @PutMapping("/{userId}/avatar")
    public R<Void> updateAvatar(@PathVariable Long userId, @RequestParam String avatar) {
        userService.updateAvatar(userId, avatar);
        return R.ok();
    }

    @Operation(summary = "更新个人信息")
    @PutMapping("/profile")
    public R<SysUserVO> updateProfile(@Valid @RequestBody SysUserDTO user) {
        return R.ok(userService.updateProfile(user));
    }

    @Operation(summary = "分配角色")
    @PutMapping("/{userId}/roles")
    public R<Void> assignRoles(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        userService.assignRoles(userId, roleIds);
        return R.ok();
    }
} 