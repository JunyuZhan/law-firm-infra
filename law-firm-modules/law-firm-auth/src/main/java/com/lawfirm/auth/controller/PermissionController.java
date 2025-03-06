package com.lawfirm.auth.controller;

import com.lawfirm.common.model.Result;
import com.lawfirm.model.auth.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.auth.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
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
    public Result<Long> createPermission(@RequestBody @Valid PermissionCreateDTO createDTO) {
        Long permissionId = permissionService.createPermission(createDTO);
        return Result.ok().data(permissionId);
    }

    /**
     * 更新权限
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:permission:edit')")
    public Result<Void> updatePermission(@PathVariable Long id, @RequestBody @Valid PermissionUpdateDTO updateDTO) {
        permissionService.updatePermission(id, updateDTO);
        return Result.ok();
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:permission:remove')")
    public Result<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return Result.ok();
    }

    /**
     * 获取权限详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public Result<PermissionVO> getPermission(@PathVariable Long id) {
        PermissionVO permissionVO = permissionService.getPermissionById(id);
        return Result.ok().data(permissionVO);
    }

    /**
     * 获取所有权限
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:permission:list')")
    public Result<List<PermissionVO>> listAllPermissions() {
        List<PermissionVO> permissions = permissionService.listAllPermissions();
        return Result.ok().data(permissions);
    }

    /**
     * 获取权限树
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public Result<List<PermissionVO>> getPermissionTree() {
        List<PermissionVO> permissionTree = permissionService.getPermissionTree();
        return Result.ok().data(permissionTree);
    }

    /**
     * 获取角色权限
     */
    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public Result<List<PermissionVO>> listPermissionsByRoleId(@PathVariable Long roleId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByRoleId(roleId);
        return Result.ok().data(permissions);
    }

    /**
     * 获取用户权限
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public Result<List<PermissionVO>> listPermissionsByUserId(@PathVariable Long userId) {
        List<PermissionVO> permissions = permissionService.listPermissionsByUserId(userId);
        return Result.ok().data(permissions);
    }

    /**
     * 获取用户权限编码
     */
    @GetMapping("/codes/user/{userId}")
    @PreAuthorize("hasAuthority('system:permission:list')")
    public Result<List<String>> listPermissionCodesByUserId(@PathVariable Long userId) {
        List<String> permissionCodes = permissionService.listPermissionCodesByUserId(userId);
        return Result.ok().data(permissionCodes);
    }
}
