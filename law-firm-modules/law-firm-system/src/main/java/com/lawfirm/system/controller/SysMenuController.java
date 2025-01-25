package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysMenu;
import com.lawfirm.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统菜单控制器
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;

    @Operation(summary = "创建菜单")
    @PostMapping
    public R<Void> createMenu(@Validated @RequestBody SysMenu menu) {
        menuService.createMenu(menu);
        return R.ok();
    }

    @Operation(summary = "更新菜单")
    @PutMapping
    public R<Void> updateMenu(@Validated @RequestBody SysMenu menu) {
        menuService.updateMenu(menu);
        return R.ok();
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    public R<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return R.ok();
    }

    @Operation(summary = "根据角色ID查询菜单列表")
    @GetMapping("/role/{roleId}")
    public R<List<SysMenu>> listByRoleId(@PathVariable Long roleId) {
        return R.ok(menuService.listByRoleId(roleId));
    }

    @Operation(summary = "根据用户ID查询菜单列表")
    @GetMapping("/user/{userId}")
    public R<List<SysMenu>> listByUserId(@PathVariable Long userId) {
        return R.ok(menuService.listByUserId(userId));
    }

    @Operation(summary = "查询可见菜单列表")
    @GetMapping("/visible")
    public R<List<SysMenu>> listVisible() {
        return R.ok(menuService.listVisible());
    }

    @Operation(summary = "根据父ID查询子菜单列表")
    @GetMapping("/children/{parentId}")
    public R<List<SysMenu>> listChildren(@PathVariable Long parentId) {
        return R.ok(menuService.listChildren(parentId));
    }

    @Operation(summary = "构建菜单树")
    @PostMapping("/tree")
    public R<List<SysMenu>> buildMenuTree(@RequestBody List<SysMenu> menus) {
        return R.ok(menuService.buildMenuTree(menus));
    }

    @Operation(summary = "构建前端路由")
    @PostMapping("/routers")
    public R<List<SysMenu>> buildRouters(@RequestBody List<SysMenu> menus) {
        return R.ok(menuService.buildRouters(menus));
    }
} 