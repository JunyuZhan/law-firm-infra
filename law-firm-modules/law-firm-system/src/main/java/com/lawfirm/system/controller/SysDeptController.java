package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysDept;
import com.lawfirm.system.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统部门控制器
 */
@Tag(name = "部门管理")
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;

    @Operation(summary = "创建部门")
    @PostMapping
    public R<Void> createDept(@Validated @RequestBody SysDept dept) {
        deptService.createDept(dept);
        return R.ok();
    }

    @Operation(summary = "更新部门")
    @PutMapping
    public R<Void> updateDept(@Validated @RequestBody SysDept dept) {
        deptService.updateDept(dept);
        return R.ok();
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    public R<Void> deleteDept(@PathVariable Long id) {
        deptService.deleteDept(id);
        return R.ok();
    }

    @Operation(summary = "根据父ID查询子部门列表")
    @GetMapping("/children/{parentId}")
    public R<List<SysDept>> listChildren(@PathVariable Long parentId) {
        return R.ok(deptService.listChildren(parentId));
    }

    @Operation(summary = "获取部门路径")
    @GetMapping("/{id}/path")
    public R<List<SysDept>> getPath(@PathVariable Long id) {
        return R.ok(deptService.getPath(id));
    }

    @Operation(summary = "获取部门树")
    @GetMapping("/tree")
    public R<List<SysDept>> getTree() {
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