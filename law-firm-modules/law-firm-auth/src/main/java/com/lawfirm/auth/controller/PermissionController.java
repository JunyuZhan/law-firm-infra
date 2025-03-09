package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.auth.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.auth.vo.RouterVO;
import com.lawfirm.model.base.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 权限控制器
 */
@Slf4j
@Tag(name = "权限管理", description = "权限相关接口")
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    
    private final PermissionService permissionService;
    
    /**
     * 创建权限
     */
    @Operation(summary = "创建权限", description = "创建新权限")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:permission:create')")
    public CommonResult<Long> createPermission(@Valid @RequestBody PermissionCreateDTO createDTO) {
        Long permissionId = permissionService.createPermission(createDTO);
        return CommonResult.success(permissionId);
    }
    
    /**
     * 更新权限
     */
    @Operation(summary = "更新权限", description = "更新权限信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:permission:update')")
    public CommonResult<?> updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionUpdateDTO updateDTO) {
        permissionService.updatePermission(id, updateDTO);
        return CommonResult.success();
    }
    
    /**
     * 删除权限
     */
    @Operation(summary = "删除权限", description = "删除指定权限")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:permission:delete')")
    public CommonResult<?> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return CommonResult.success();
    }
    
    /**
     * 获取权限详情
     */
    @Operation(summary = "获取权限详情", description = "获取指定权限详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public CommonResult<PermissionVO> getPermissionById(@PathVariable Long id) {
        PermissionVO permissionVO = permissionService.getPermissionById(id);
        return CommonResult.success(permissionVO);
    }
    
    /**
     * 获取所有权限列表
     */
    @Operation(summary = "获取所有权限列表", description = "获取所有权限列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public CommonResult<List<PermissionVO>> listAllPermissions() {
        List<PermissionVO> permissions = permissionService.listAllPermissions();
        return CommonResult.success(permissions);
    }
    
    /**
     * 获取权限树结构
     */
    @Operation(summary = "获取权限树结构", description = "获取权限树结构")
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public CommonResult<List<PermissionVO>> getPermissionTree() {
        List<PermissionVO> permissionTree = permissionService.getPermissionTree();
        return CommonResult.success(permissionTree);
    }
    
    /**
     * 根据角色ID获取权限列表
     */
    @Operation(summary = "根据角色ID获取权限列表", description = "根据角色ID获取权限列表")
    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public CommonResult<List<PermissionVO>> listPermissionsByRoleId(@PathVariable Long roleId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByRoleId(roleId);
        return CommonResult.success(permissions);
    }
    
    /**
     * 根据用户ID获取权限列表
     */
    @Operation(summary = "根据用户ID获取权限列表", description = "根据用户ID获取权限列表")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public CommonResult<List<PermissionVO>> listPermissionsByUserId(@PathVariable Long userId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByUserId(userId);
        return CommonResult.success(permissions);
    }
    
    /**
     * 根据用户ID获取权限编码列表
     */
    @Operation(summary = "根据用户ID获取权限编码列表", description = "根据用户ID获取权限编码列表")
    @GetMapping("/user/{userId}/codes")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public CommonResult<List<String>> listPermissionCodesByUserId(@PathVariable Long userId) {
        List<String> permissionCodes = permissionService.listPermissionCodesByUserId(userId);
        return CommonResult.success(permissionCodes);
    }
    
    /**
     * 分页查询权限
     */
    @Operation(summary = "分页查询权限", description = "分页查询权限列表")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public CommonResult<Page<PermissionVO>> pagePermissions(BaseDTO dto) {
        Page<PermissionVO> page = permissionService.pagePermissions(dto);
        return CommonResult.success(page);
    }
    
    /**
     * 更新权限状态
     */
    @Operation(summary = "更新权限状态", description = "启用或禁用权限")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('sys:permission:update')")
    public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        permissionService.updateStatus(id, status);
        return CommonResult.success();
    }
    
    /**
     * 获取用户菜单树
     */
    @Operation(summary = "获取用户菜单树", description = "获取当前用户的菜单树")
    @GetMapping("/user/menus")
    public CommonResult<List<PermissionVO>> getUserMenuTree() {
        // 这里需要获取当前用户ID，可以通过SecurityUtils工具类获取
        Long userId = 0L; // 临时使用0，实际应该从SecurityContext中获取
        List<PermissionVO> menuTree = permissionService.getUserMenuTree(userId);
        return CommonResult.success(menuTree);
    }
    
    /**
     * 获取用户的前端路由配置
     */
    @Operation(summary = "获取用户的前端路由配置", description = "获取当前用户的前端路由配置")
    @GetMapping("/user/routers")
    public CommonResult<List<RouterVO>> getUserRouters() {
        // 这里需要获取当前用户ID，可以通过SecurityUtils工具类获取
        Long userId = 0L; // 临时使用0，实际应该从SecurityContext中获取
        List<RouterVO> routers = permissionService.getUserRouters(userId);
        return CommonResult.success(routers);
    }
}

