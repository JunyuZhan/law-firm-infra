package com.lawfirm.admin.controller.auth;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.auth.model.dto.SysRoleDTO;
import com.lawfirm.auth.model.vo.SysRoleVO;
import com.lawfirm.auth.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统角色Controller
 */
@Api(tags = "系统角色")
@RestController
@RequestMapping("/auth/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @ApiOperation("创建角色")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createRole(@Valid @RequestBody SysRoleDTO dto) {
        roleService.createRole(dto);
        return ApiResult.ok();
    }

    @ApiOperation("更新角色")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateRole(@PathVariable Long id, @Valid @RequestBody SysRoleDTO dto) {
        roleService.updateRole(id, dto);
        return ApiResult.ok();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResult.ok();
    }

    @ApiOperation("获取角色详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<SysRoleVO> getRole(@PathVariable Long id) {
        return ApiResult.ok(roleService.getRole(id));
    }

    @ApiOperation("分页查询角色")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysRoleVO>> pageRoles(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String roleName,
                                               @RequestParam(required = false) String roleCode,
                                               @RequestParam(required = false) Integer status) {
        return ApiResult.ok(roleService.pageRoles(pageNum, pageSize, roleName, roleCode, status));
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysRoleVO>> listRoles() {
        return ApiResult.ok(roleService.listRoles());
    }

    @ApiOperation("分配菜单权限")
    @PutMapping("/{id}/menus")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> assignMenus(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        roleService.assignMenus(id, menuIds);
        return ApiResult.ok();
    }

    @ApiOperation("获取角色菜单ID列表")
    @GetMapping("/{id}/menus")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<Long>> getRoleMenuIds(@PathVariable Long id) {
        return ApiResult.ok(roleService.getRoleMenuIds(id));
    }

    @ApiOperation("检查角色编码是否存在")
    @GetMapping("/check/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkRoleCodeExists(@PathVariable String code) {
        return ApiResult.ok(roleService.checkRoleCodeExists(code));
    }

    @ApiOperation("导出角色数据")
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportRoles() {
        roleService.exportRoles();
    }
} 