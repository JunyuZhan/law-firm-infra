package com.lawfirm.model.organization.controller;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.organization.vo.DepartmentVO;
import com.lawfirm.model.organization.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理接口
 */
@Tag(name = "部门管理", description = "部门管理相关接口")
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @Operation(summary = "创建部门")
    @OperationLog(description = "创建部门", operationType = "INSERT")
    public Result<DepartmentVO> create(@RequestBody @Validated DepartmentVO vo) {
        departmentService.create(vo);
        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "更新部门")
    @OperationLog(description = "更新部门", operationType = "UPDATE")
    public Result<DepartmentVO> update(@RequestBody @Validated DepartmentVO vo) {
        departmentService.update(vo);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除部门")
    @OperationLog(description = "删除部门", operationType = "DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return Result.ok();
    }

    @GetMapping("/tree")
    @Operation(summary = "获取部门树")
    public Result<List<DepartmentVO>> getTree() {
        return Result.ok(departmentService.getTree());
    }

    @GetMapping("/children/{parentId}")
    @Operation(summary = "获取子部门")
    public Result<List<DepartmentVO>> getChildren(@PathVariable Long parentId) {
        return Result.ok(departmentService.getChildren(parentId));
    }

    @PutMapping("/{id}/parent/{parentId}")
    @Operation(summary = "移动部门")
    @OperationLog(description = "移动部门", operationType = "UPDATE")
    public Result<Void> moveDepartment(@PathVariable Long id, @PathVariable Long parentId) {
        departmentService.moveDepartment(id, parentId);
        return Result.ok();
    }

    @PutMapping("/{id}/sort/{sortOrder}")
    @Operation(summary = "调整部门顺序")
    @OperationLog(description = "调整部门顺序", operationType = "UPDATE")
    public Result<Void> reorderDepartment(@PathVariable Long id, @PathVariable Integer sortOrder) {
        departmentService.reorderDepartment(id, sortOrder);
        return Result.ok();
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "获取角色关联的部门")
    public Result<List<DepartmentVO>> getRoleDepartments(@PathVariable Long roleId) {
        return Result.ok(departmentService.getRoleDepartments(roleId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户关联的部门")
    public Result<List<DepartmentVO>> getUserDepartments(@PathVariable Long userId) {
        return Result.ok(departmentService.getUserDepartments(userId));
    }
} 