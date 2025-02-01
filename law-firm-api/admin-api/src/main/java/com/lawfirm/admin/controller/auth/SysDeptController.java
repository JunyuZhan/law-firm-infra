package com.lawfirm.admin.controller.auth;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.organization.model.dto.DeptDTO;
import com.lawfirm.organization.model.entity.Dept;
import com.lawfirm.organization.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 部门管理Controller
 */
@Tag(name = "部门管理")
@RestController
@RequestMapping("/organization/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final DeptService deptService;

    @Operation(summary = "创建部门")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createDept(@Valid @RequestBody DeptDTO dto) {
        deptService.create(dto.toEntity(Dept.class));
        return ApiResult.ok();
    }

    @Operation(summary = "更新部门")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateDept(@PathVariable Long id, @Valid @RequestBody DeptDTO dto) {
        dto.setId(id);
        deptService.update(dto.toEntity(Dept.class));
        return ApiResult.ok();
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteDept(@PathVariable Long id) {
        deptService.delete(id);
        return ApiResult.ok();
    }

    @Operation(summary = "获取部门详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<DeptDTO> getDept(@PathVariable Long id) {
        return ApiResult.ok(deptService.getById(id).toDTO(DeptDTO.class));
    }

    @Operation(summary = "获取部门树")
    @GetMapping("/tree")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<DeptDTO>> getDeptTree() {
        return ApiResult.ok(deptService.getDeptTree());
    }

    @Operation(summary = "获取当前用户的部门树")
    @GetMapping("/current/tree")
    public ApiResult<List<DeptDTO>> getCurrentUserDeptTree() {
        return ApiResult.ok(deptService.getUserDepts(getCurrentUserId()));
    }

    @Operation(summary = "移动部门")
    @PutMapping("/{id}/parent/{parentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> moveDept(@PathVariable Long id, @PathVariable Long parentId) {
        deptService.moveDept(id, parentId);
        return ApiResult.ok();
    }

    @Operation(summary = "调整部门顺序")
    @PutMapping("/{id}/order/{orderNum}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> reorderDept(@PathVariable Long id, @PathVariable Integer orderNum) {
        deptService.reorderDept(id, orderNum);
        return ApiResult.ok();
    }

    /**
     * 获取当前用户ID
     */
    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
} 