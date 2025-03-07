package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.role.RoleCreateDTO;
import com.lawfirm.model.auth.dto.role.RoleUpdateDTO;
import com.lawfirm.model.auth.service.RoleService;
import com.lawfirm.model.auth.vo.RoleVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 创建角色
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:role:add')")
    public CommonResult<Long> createRole(@RequestBody @Valid RoleCreateDTO createDTO) {
        Long roleId = roleService.createRole(createDTO);
        return CommonResult.success(roleId);
    }

    /**
     * 更新角色
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public CommonResult<Void> updateRole(@PathVariable Long id, @RequestBody @Valid RoleCreateDTO updateDTO) {
        roleService.updateRole(id, updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public CommonResult<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return CommonResult.success();
    }

    /**
     * 批量删除角色
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public CommonResult<Void> batchDeleteRoles(@RequestBody List<Long> ids) {
        roleService.deleteRoles(ids);
        return CommonResult.success();
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:list')")
    public CommonResult<RoleVO> getRole(@PathVariable Long id) {
        RoleVO roleVO = roleService.getRoleById(id);
        return CommonResult.success(roleVO);
    }

    /**
     * 分页查询角色
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:role:list')")
    public CommonResult<Page<RoleVO>> pageRoles(RoleUpdateDTO queryDTO) {
        Page<RoleVO> page = roleService.pageRoles(queryDTO);
        return CommonResult.success(page);
    }

    /**
     * 获取所有角色
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('system:role:list')")
    public CommonResult<List<RoleVO>> listAllRoles() {
        List<RoleVO> roles = roleService.listAllRoles();
        return CommonResult.success(roles);
    }

    /**
     * 设置角色权限
     */
    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public CommonResult<Void> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        roleService.assignPermissions(id, permissionIds);
        return CommonResult.success();
    }

    /**
     * 获取角色权限ID列表
     */
    @GetMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('system:role:list')")
    public CommonResult<List<Long>> getRolePermissions(@PathVariable Long id) {
        List<Long> permissionIds = roleService.getRolePermissionIds(id);
        return CommonResult.success(permissionIds);
    }
}
