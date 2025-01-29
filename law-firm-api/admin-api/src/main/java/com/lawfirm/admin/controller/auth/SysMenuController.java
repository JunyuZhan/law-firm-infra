package com.lawfirm.admin.controller.auth;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.auth.model.dto.SysMenuDTO;
import com.lawfirm.auth.model.vo.SysMenuVO;
import com.lawfirm.auth.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统菜单Controller
 */
@Api(tags = "系统菜单")
@RestController
@RequestMapping("/auth/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;

    @ApiOperation("创建菜单")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createMenu(@Valid @RequestBody SysMenuDTO dto) {
        menuService.createMenu(dto);
        return ApiResult.ok();
    }

    @ApiOperation("更新菜单")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateMenu(@PathVariable Long id, @Valid @RequestBody SysMenuDTO dto) {
        menuService.updateMenu(id, dto);
        return ApiResult.ok();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ApiResult.ok();
    }

    @ApiOperation("获取菜单详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<SysMenuVO> getMenu(@PathVariable Long id) {
        return ApiResult.ok(menuService.getMenu(id));
    }

    @ApiOperation("获取菜单树")
    @GetMapping("/tree")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysMenuVO>> getMenuTree() {
        return ApiResult.ok(menuService.getMenuTree());
    }

    @ApiOperation("获取当前用户菜单树")
    @GetMapping("/current/tree")
    public ApiResult<List<SysMenuVO>> getCurrentUserMenuTree() {
        return ApiResult.ok(menuService.getCurrentUserMenuTree());
    }

    @ApiOperation("获取当前用户权限列表")
    @GetMapping("/current/permissions")
    public ApiResult<List<String>> getCurrentUserPermissions() {
        return ApiResult.ok(menuService.getCurrentUserPermissions());
    }

    @ApiOperation("检查菜单权限标识是否存在")
    @GetMapping("/check/{permission}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkPermissionExists(@PathVariable String permission) {
        return ApiResult.ok(menuService.checkPermissionExists(permission));
    }
} 