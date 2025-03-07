package com.lawfirm.auth.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.auth.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.auth.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.auth.vo.RouterVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理控制器
 */
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 创建权限
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:permission:add')")
    public CommonResult<Long> createPermission(@RequestBody @Valid PermissionCreateDTO createDTO) {
        Long permissionId = permissionService.createPermission(createDTO);
        return CommonResult.success(permissionId);
    }

    /**
     * 更新权限
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:permission:edit')")
    public CommonResult<Void> updatePermission(@PathVariable Long id, @RequestBody @Valid PermissionUpdateDTO updateDTO) {
        permissionService.updatePermission(id, updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:permission:remove')")
    public CommonResult<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return CommonResult.success();
    }

    /**
     * 获取权限详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public CommonResult<PermissionVO> getPermission(@PathVariable Long id) {
        PermissionVO permissionVO = permissionService.getPermissionById(id);
        return CommonResult.success(permissionVO);
    }

    /**
     * 获取所有权限
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:permission:list')")
    public CommonResult<List<PermissionVO>> listAllPermissions() {
        List<PermissionVO> permissions = permissionService.listAllPermissions();
        return CommonResult.success(permissions);
    }

    /**
     * 获取权限树
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public CommonResult<List<PermissionVO>> getPermissionTree() {
        List<PermissionVO> permissionTree = permissionService.getPermissionTree();
        return CommonResult.success(permissionTree);
    }

    /**
     * 获取角色权限
     */
    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public CommonResult<List<PermissionVO>> listPermissionsByRoleId(@PathVariable Long roleId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByRoleId(roleId);
        return CommonResult.success(permissions);
    }

    /**
     * 获取用户权限
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public CommonResult<List<PermissionVO>> listPermissionsByUserId(@PathVariable Long userId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByUserId(userId);
        return CommonResult.success(permissions);
    }

    /**
     * 获取用户权限编码
     */
    @GetMapping("/codes/user/{userId}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public CommonResult<List<String>> listPermissionCodesByUserId(@PathVariable Long userId) {
        List<String> permissionCodes = permissionService.listPermissionCodesByUserId(userId);
        return CommonResult.success(permissionCodes);
    }

    /**
     * 获取当前用户的路由配置
     */
    @GetMapping("/routes")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<List<RouterVO>> getUserRouters() {
        Long userId = SecurityUtils.getUserId();
        List<RouterVO> routers = permissionService.getUserRouters(userId);
        return CommonResult.success(routers);
    }
}
