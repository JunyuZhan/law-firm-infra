package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.dto.SysRoleDTO;
import com.lawfirm.model.system.vo.SysRoleVO;
import com.lawfirm.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.common.web.controller.BaseController;

import java.util.List;

/**
 * 系统角色控制器
 */
@Tag(name = "系统角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController extends BaseController {

    private final SysRoleService roleService;

    @PostMapping
    @Operation(summary = "创建角色")
    public R<SysRoleVO> createRole(@Validated @RequestBody SysRoleVO role) {
        return R.ok(roleService.createRole(role));
    }

    @PutMapping
    @Operation(summary = "更新角色")
    public R<SysRoleVO> updateRole(@Validated @RequestBody SysRoleVO role) {
        return R.ok(roleService.updateRole(role));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public R<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return R.ok();
    }

    @GetMapping("/list")
    @Operation(summary = "获取角色列表")
    public R<List<SysRoleVO>> listRoles(SysRoleVO query) {
        return R.ok(roleService.list(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情")
    public R<SysRoleVO> getRoleById(@PathVariable Long id) {
        return R.ok(roleService.getVOById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有角色")
    public R<List<SysRoleVO>> getAllRoles() {
        return R.ok(roleService.listVO());
    }

    @Operation(summary = "根据用户ID查询角色列表")
    @GetMapping("/user/{userId}")
    public R<List<SysRoleVO>> listByUserId(@PathVariable Long userId) {
        return R.ok(roleService.listByUserId(userId));
    }

    @Operation(summary = "根据角色编码查询角色")
    @GetMapping("/code/{code}")
    public R<SysRoleVO> getByCode(@PathVariable String code) {
        return R.ok(roleService.getByCode(code));
    }

    @Operation(summary = "查询默认角色列表")
    @GetMapping("/default")
    public R<List<SysRoleVO>> listDefaultRoles() {
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