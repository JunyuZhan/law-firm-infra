package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.auth.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.auth.vo.RouterVO;
import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.auth.constant.AuthApiConstants;
import com.lawfirm.model.auth.constant.PermissionConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 权限控制器
 * 适配Vue-Vben-Admin风格
 */
@Slf4j
@Tag(name = "权限管理", description = "权限相关接口")
@RestController("permissionController")
@RequestMapping(AuthApiConstants.Api.PERMISSION)
@RequiredArgsConstructor
public class PermissionController {
    
    private final PermissionService permissionService;
    
    /**
     * 创建权限
     */
    @Operation(summary = "创建权限", description = "创建新的权限")
    @PostMapping
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_CREATE + "')")
    public CommonResult<Long> createPermission(@Valid @RequestBody PermissionCreateDTO createDTO) {
        Long permissionId = permissionService.createPermission(createDTO);
        return CommonResult.success(permissionId);
    }
    
    /**
     * 更新权限
     */
    @Operation(summary = "更新权限", description = "更新权限信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_UPDATE + "')")
    public CommonResult<?> updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionUpdateDTO updateDTO) {
        permissionService.updatePermission(id, updateDTO);
        return CommonResult.success();
    }
    
    /**
     * 删除权限
     */
    @Operation(summary = "删除权限", description = "删除指定权限")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_DELETE + "')")
    public CommonResult<?> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return CommonResult.success();
    }
    
    /**
     * 获取权限详情
     */
    @Operation(summary = "获取权限详情", description = "获取指定权限详情")
    @GetMapping("/getPermission/{id}")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_READ + "')")
    public CommonResult<PermissionVO> getPermission(@PathVariable Long id) {
        PermissionVO permissionVO = permissionService.getPermissionById(id);
        return CommonResult.success(permissionVO);
    }
    
    /**
     * 获取所有权限
     */
    @Operation(summary = "获取所有权限", description = "获取所有权限列表")
    @GetMapping("/getPermissionList")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_READ + "')")
    public CommonResult<List<PermissionVO>> getPermissionList() {
        List<PermissionVO> permissions = permissionService.listAllPermissions();
        return CommonResult.success(permissions);
    }
    
    /**
     * 获取权限树
     */
    @Operation(summary = "获取权限树", description = "获取权限树形结构")
    @GetMapping("/getPermissionTree")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_READ + "')")
    public CommonResult<List<PermissionVO>> getPermissionTree() {
        List<PermissionVO> permissionTree = permissionService.getPermissionTree();
        return CommonResult.success(permissionTree);
    }
    
    /**
     * 获取角色的权限列表
     */
    @Operation(summary = "获取角色的权限列表", description = "获取指定角色的权限列表")
    @GetMapping("/getRolePermissions/{roleId}")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_READ + "')")
    public CommonResult<List<PermissionVO>> getRolePermissions(@PathVariable Long roleId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByRoleId(roleId);
        return CommonResult.success(permissions);
    }
    
    /**
     * 获取用户的权限列表
     */
    @Operation(summary = "获取用户的权限列表", description = "获取指定用户的权限列表")
    @GetMapping("/getUserPermissions/{userId}")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_READ + "')")
    public CommonResult<List<PermissionVO>> getUserPermissions(@PathVariable Long userId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByUserId(userId);
        return CommonResult.success(permissions);
    }
    
    /**
     * 获取当前用户的权限列表
     */
    @Operation(summary = "获取当前用户的权限列表", description = "获取当前登录用户的权限列表")
    @GetMapping("/getCurrentUserPermissions")
    public CommonResult<List<PermissionVO>> getCurrentUserPermissions() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<PermissionVO> permissions = permissionService.listPermissionsByUserId(userId);
        return CommonResult.success(permissions);
    }
    
    /**
     * 更新权限状态
     */
    @Operation(summary = "更新权限状态", description = "启用或禁用权限")
    @PutMapping("/updateStatus/{id}")
    @PreAuthorize("hasAuthority('" + SYS_PERMISSION_UPDATE + "')")
    public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        permissionService.updateStatus(id, status);
        return CommonResult.success();
    }
    
    /**
     * 获取用户菜单树
     */
    @Operation(summary = "获取用户菜单树", description = "获取当前用户的菜单树")
    @GetMapping("/getMenuList")
    public CommonResult<List<PermissionVO>> getMenuList() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<PermissionVO> menuTree = permissionService.getUserMenuTree(userId);
        return CommonResult.success(menuTree);
    }
    
    /**
     * 获取用户的前端路由配置
     */
    @Operation(summary = "获取用户的前端路由配置", description = "获取当前用户的前端路由配置")
    @GetMapping("/getUserRouters")
    public CommonResult<List<RouterVO>> getUserRouters() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<RouterVO> routers = permissionService.getUserRouters(userId);
        return CommonResult.success(routers);
    }
    
    /**
     * 获取权限编码列表
     */
    @Operation(summary = "获取权限编码列表", description = "获取当前用户的权限编码列表，用于前端权限控制")
    @GetMapping("/getPermCode")
    public CommonResult<List<String>> getPermCode() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<String> permCodes = permissionService.listPermissionCodesByUserId(userId);
        return CommonResult.success(permCodes);
    }
}

