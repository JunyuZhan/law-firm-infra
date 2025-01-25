package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.entity.KnowledgeCategory;
import com.lawfirm.knowledge.service.KnowledgeCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识分类管理
 */
@Tag(name = "知识分类管理")
@RestController
@RequestMapping("/knowledge/category")
@RequiredArgsConstructor
@Validated
public class KnowledgeCategoryController {

    private final KnowledgeCategoryService categoryService;

    @Operation(summary = "创建分类")
    @PostMapping
    public Long createCategory(@RequestBody KnowledgeCategory category) {
        return categoryService.createCategory(category);
    }

    @Operation(summary = "更新分类")
    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody KnowledgeCategory category) {
        category.setId(id);
        categoryService.updateCategory(category);
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @Operation(summary = "获取子分类列表")
    @GetMapping("/children/{parentId}")
    public List<KnowledgeCategory> listChildren(@PathVariable Long parentId) {
        return categoryService.listChildren(parentId);
    }

    @Operation(summary = "获取分类路径")
    @GetMapping("/{id}/path")
    public List<KnowledgeCategory> getPath(@PathVariable Long id) {
        return categoryService.getPath(id);
    }

    @Operation(summary = "获取分类树")
    @GetMapping("/tree")
    public List<KnowledgeCategory> getTree() {
        return categoryService.getTree();
    }

    @Operation(summary = "移动分类")
    @PostMapping("/{id}/move")
    public void moveCategory(
            @PathVariable Long id,
            @Parameter(description = "目标父分类ID") @RequestParam Long targetParentId) {
        categoryService.moveCategory(id, targetParentId);
    }

    @Operation(summary = "调整分类顺序")
    @PostMapping("/{id}/reorder")
    public void reorderCategory(
            @PathVariable Long id,
            @Parameter(description = "新的顺序号") @RequestParam Integer newOrder) {
        categoryService.reorderCategory(id, newOrder);
    }
} 