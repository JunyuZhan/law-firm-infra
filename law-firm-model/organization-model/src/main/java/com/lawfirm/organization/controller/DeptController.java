package com.lawfirm.organization.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.organization.model.entity.Dept;
import com.lawfirm.organization.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 */
@Tag(name = "部门管理")
@RestController
@RequestMapping("/organization/dept")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    @Operation(summary = "创建部门")
    @PostMapping
    public R<Void> createDept(@Validated @RequestBody Dept dept) {
        deptService.create(dept);
        return R.ok();
    }

    @Operation(summary = "更新部门")
    @PutMapping
    public R<Void> updateDept(@Validated @RequestBody Dept dept) {
        deptService.update(dept);
        return R.ok();
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    public R<Void> deleteDept(@PathVariable Long id) {
        deptService.delete(id);
        return R.ok();
    }

    @Operation(summary = "根据父ID查询子部门列表")
    @GetMapping("/children/{parentId}")
    public R<List<Dept>> listChildren(@PathVariable Long parentId) {
        return R.ok(deptService.listChildren(parentId));
    }

    @Operation(summary = "获取部门路径")
    @GetMapping("/{id}/path")
    public R<List<Dept>> getPath(@PathVariable Long id) {
        return R.ok(deptService.getPath(id));
    }

    @Operation(summary = "获取部门树")
    @GetMapping("/tree")
    public R<List<Dept>> getTree() {
        return R.ok(deptService.getTree());
    }

    @Operation(summary = "移动部门")
    @PutMapping("/{id}/parent/{parentId}")
    public R<Void> moveDept(@PathVariable Long id, @PathVariable Long parentId) {
        deptService.moveDept(id, parentId);
        return R.ok();
    }

    @Operation(summary = "调整部门顺序")
    @PutMapping("/{id}/order/{orderNum}")
    public R<Void> reorderDept(@PathVariable Long id, @PathVariable Integer orderNum) {
        deptService.reorderDept(id, orderNum);
        return R.ok();
    }
} 