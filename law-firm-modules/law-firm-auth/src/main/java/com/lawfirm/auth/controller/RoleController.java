package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.model.Result;
import com.lawfirm.model.auth.dto.role.RoleCreateDTO;
import com.lawfirm.model.auth.dto.role.RoleQueryDTO;
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
    public Result<Long> createRole(@RequestBody @Valid RoleCreateDTO createDTO) {
        Long roleId = roleService.createRole(createDTO);
        return Result.ok().data(roleId);
    }

    /**
     * 更新角色
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> updateRole(@PathVariable Long id, @RequestBody @Valid RoleUpdateDTO updateDTO) {
        roleService.updateRole(id, updateDTO);
        return Result.ok();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.ok();
    }

    /**
     * 批量删除角色
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public Result<Void> batchDeleteRoles(@RequestBody List<Long> ids) {
        roleService.deleteRoles(ids);
        return Result.ok();
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result<RoleVO> getRole(@PathVariable Long id) {
        RoleVO roleVO = roleService.getRoleById(id);
        return Result.ok().data(roleVO);
    }

    /**
     * 分页查询角色
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result<Page<RoleVO>> pageRoles(RoleQueryDTO queryDTO) {
        Page<RoleVO> page = roleService.pageRoles(queryDTO);
        return Result.ok().data(page);
    }

    /**
     * 获取所有角色
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result<List<RoleVO>> listAllRoles() {
        List<RoleVO> roles = roleService.listAllRoles();
        return Result.ok().data(roles);
    }

    /**
     * 设置角色权限
     */
    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        roleService.assignPermissions(id, permissionIds);
        return Result.ok();
    }

    /**
     * 获取角色权限ID列表
     */
    @GetMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result<List<Long>> getRolePermissions(@PathVariable Long id) {
        List<Long> permissionIds = roleService.listPermissionIdsByRoleId(id);
        return Result.ok().data(permissionIds);
    }
}
