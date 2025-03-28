package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.role.RoleCreateDTO;
import com.lawfirm.model.auth.dto.role.RoleUpdateDTO;
import com.lawfirm.model.auth.service.RoleService;
import com.lawfirm.model.auth.vo.RoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 * 适配Vue-Vben-Admin风格
 */
@Slf4j
@Tag(name = "角色管理", description = "角色相关接口")
@RestController("roleController")
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    
    private final RoleService roleService;
    
    /**
     * 创建角色
     */
    @Operation(summary = "创建角色", description = "创建新角色")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:create')")
    public CommonResult<Long> createRole(@Valid @RequestBody RoleCreateDTO createDTO) {
        Long roleId = roleService.createRole(createDTO);
        return CommonResult.success(roleId);
    }
    
    /**
     * 更新角色
     */
    @Operation(summary = "更新角色", description = "更新角色信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CommonResult<?> updateRole(@PathVariable Long id, @Valid @RequestBody RoleCreateDTO updateDTO) {
        roleService.updateRole(id, updateDTO);
        return CommonResult.success();
    }
    
    /**
     * 删除角色
     */
    @Operation(summary = "删除角色", description = "删除指定角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public CommonResult<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return CommonResult.success();
    }
    
    /**
     * 批量删除角色
     */
    @Operation(summary = "批量删除角色", description = "批量删除角色")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public CommonResult<?> batchDeleteRoles(@RequestBody List<Long> ids) {
        roleService.deleteRoles(ids);
        return CommonResult.success();
    }
    
    /**
     * 获取角色详情
     */
    @Operation(summary = "获取角色详情", description = "获取指定角色详情")
    @GetMapping("/getRole/{id}")
    @PreAuthorize("hasAuthority('sys:role:read')")
    public CommonResult<RoleVO> getRole(@PathVariable Long id) {
        RoleVO roleVO = roleService.getRoleById(id);
        return CommonResult.success(roleVO);
    }
    
    /**
     * 分页查询角色
     */
    @Operation(summary = "分页查询角色", description = "分页查询角色列表")
    @GetMapping("/getRolePage")
    @PreAuthorize("hasAuthority('sys:role:read')")
    public CommonResult<Page<RoleVO>> getRolePage(RoleUpdateDTO queryDTO) {
        Page<RoleVO> page = roleService.pageRoles(queryDTO);
        return CommonResult.success(page);
    }
    
    /**
     * 获取所有角色
     */
    @Operation(summary = "获取所有角色", description = "获取所有角色列表")
    @GetMapping("/getRoleList")
    @PreAuthorize("hasAuthority('sys:role:read')")
    public CommonResult<List<RoleVO>> getRoleList() {
        List<RoleVO> roles = roleService.listAllRoles();
        return CommonResult.success(roles);
    }
    
    /**
     * 更新角色状态
     */
    @Operation(summary = "更新角色状态", description = "启用或禁用角色")
    @PutMapping("/updateStatus/{id}")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        roleService.updateStatus(id, status);
        return CommonResult.success();
    }
    
    /**
     * 分配角色权限
     */
    @Operation(summary = "分配角色权限", description = "为角色分配权限")
    @PutMapping("/assignPermissions/{id}")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CommonResult<?> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        roleService.assignPermissions(id, permissionIds);
        return CommonResult.success();
    }
    
    /**
     * 获取角色权限ID列表
     */
    @Operation(summary = "获取角色权限ID列表", description = "获取角色已分配的权限ID列表")
    @GetMapping("/getRolePermissions/{id}")
    @PreAuthorize("hasAuthority('sys:role:read')")
    public CommonResult<List<Long>> getRolePermissions(@PathVariable Long id) {
        List<Long> permissionIds = roleService.getRolePermissionIds(id);
        return CommonResult.success(permissionIds);
    }
} 