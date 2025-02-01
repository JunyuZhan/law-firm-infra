package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.system.model.dto.SysRoleDTO;
import com.lawfirm.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色控制器
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @Operation(summary = "创建角色")
    @PostMapping
    public R<Void> createRole(@Validated @RequestBody SysRoleDTO role) {
        roleService.createRole(role);
        return R.ok();
    }

    @Operation(summary = "更新角色")
    @PutMapping
    public R<Void> updateRole(@Validated @RequestBody SysRoleDTO role) {
        roleService.updateRole(role);
        return R.ok();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public R<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return R.ok();
    }

    @Operation(summary = "根据用户ID查询角色列表")
    @GetMapping("/user/{userId}")
    public R<List<SysRoleDTO>> listByUserId(@PathVariable Long userId) {
        return R.ok(roleService.listByUserId(userId));
    }

    @Operation(summary = "根据角色编码查询角色")
    @GetMapping("/code/{code}")
    public R<SysRoleDTO> getByCode(@PathVariable String code) {
        return R.ok(roleService.getByCode(code));
    }

    @Operation(summary = "查询默认角色列表")
    @GetMapping("/default")
    public R<List<SysRoleDTO>> listDefaultRoles() {
        return R.ok(roleService.listDefaultRoles());
    }

    @Operation(summary = "分配角色菜单")
    @PutMapping("/{roleId}/menus")
    public R<Void> assignMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        roleService.assignMenus(roleId, menuIds);
        return R.ok();
    }

    @Operation(summary = "分配角色数据权限")
    @PutMapping("/{roleId}/datascope")
    public R<Void> assignDataScope(@PathVariable Long roleId, @RequestParam Integer dataScope) {
        roleService.assignDataScope(roleId, dataScope);
        return R.ok();
    }
} 