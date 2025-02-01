package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysMenu;
import com.lawfirm.system.model.dto.SysMenuDTO;
import com.lawfirm.system.model.vo.RouterVo;
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
    public R<Void> createMenu(@Validated @RequestBody SysMenuDTO menu) {
        menuService.createMenu(menu);
        return R.ok();
    }

    @Operation(summary = "更新菜单")
    @PutMapping
    public R<Void> updateMenu(@Validated @RequestBody SysMenuDTO menu) {
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
    public R<List<SysMenuDTO>> listByRoleId(@PathVariable Long roleId) {
        return R.ok(menuService.listByRoleId(roleId));
    }

    @Operation(summary = "根据用户ID查询菜单列表")
    @GetMapping("/user/{userId}")
    public R<List<SysMenuDTO>> listByUserId(@PathVariable Long userId) {
        return R.ok(menuService.listByUserId(userId));
    }

    @Operation(summary = "查询可见菜单列表")
    @GetMapping("/visible")
    public R<List<SysMenuDTO>> listVisible() {
        return R.ok(menuService.listVisible());
    }

    @Operation(summary = "根据父ID查询子菜单列表")
    @GetMapping("/children/{parentId}")
    public R<List<SysMenuDTO>> listChildren(@PathVariable Long parentId) {
        return R.ok(menuService.listChildren(parentId));
    }

    @Operation(summary = "构建菜单树")
    @PostMapping("/tree")
    public R<List<SysMenuDTO>> buildMenuTree(@RequestBody List<SysMenuDTO> menus) {
        return R.ok(menuService.buildMenuTree(menus));
    }

    @Operation(summary = "构建前端路由")
    @PostMapping("/routers")
    public R<List<RouterVo>> buildRouters(@RequestBody List<SysMenuDTO> menus) {
        return R.ok(menuService.buildRouters(menus));
    }
} 