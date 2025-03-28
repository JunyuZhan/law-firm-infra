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
@Tag(name = "知识分类管理", description = "提供知识分类的创建、查询、修改、删除等功能，支持分类树的管理和分类顺序的调整")
@RestController
@RequestMapping("/knowledge/category")
@RequiredArgsConstructor
@Validated
public class KnowledgeCategoryController {

    private final KnowledgeCategoryService categoryService;

    @Operation(
        summary = "创建分类",
        description = "创建新的知识分类，支持设置分类名称、父分类、排序等信息"
    )
    @PostMapping
    public Long createCategory(
            @Parameter(description = "分类创建参数，包括分类名称、父分类ID、排序号等") @RequestBody KnowledgeCategory category) {
        return categoryService.createCategory(category);
    }

    @Operation(
        summary = "更新分类",
        description = "更新已存在的知识分类信息，支持修改分类名称、描述等基本信息"
    )
    @PutMapping("/{id}")
    public void updateCategory(
            @Parameter(description = "分类ID") @PathVariable Long id,
            @Parameter(description = "分类更新参数，包括需要更新的字段") @RequestBody KnowledgeCategory category) {
        category.setId(id);
        categoryService.updateCategory(category);
    }

    @Operation(
        summary = "删除分类",
        description = "删除指定的知识分类，如果分类下有子分类或知识则不允许删除"
    )
    @DeleteMapping("/{id}")
    public void deleteCategory(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @Operation(
        summary = "获取子分类列表",
        description = "获取指定分类的直接子分类列表，按排序号升序排列"
    )
    @GetMapping("/children/{parentId}")
    public List<KnowledgeCategory> listChildren(
            @Parameter(description = "父分类ID") @PathVariable Long parentId) {
        return categoryService.listChildren(parentId);
    }

    @Operation(
        summary = "获取分类路径",
        description = "获取指定分类到根分类的完整路径，从根分类到当前分类的顺序排列"
    )
    @GetMapping("/{id}/path")
    public List<KnowledgeCategory> getPath(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        return categoryService.getPath(id);
    }

    @Operation(
        summary = "获取分类树",
        description = "获取完整的知识分类树结构，包含所有分类的层级关系"
    )
    @GetMapping("/tree")
    public List<KnowledgeCategory> getTree() {
        return categoryService.getTree();
    }

    @Operation(
        summary = "移动分类",
        description = "将分类移动到新的父分类下，同时会更新其所有子分类的路径"
    )
    @PostMapping("/{id}/move")
    public void moveCategory(
            @Parameter(description = "要移动的分类ID") @PathVariable Long id,
            @Parameter(description = "目标父分类ID，如果为0则移动到根分类") @RequestParam Long targetParentId) {
        categoryService.moveCategory(id, targetParentId);
    }

    @Operation(
        summary = "调整分类顺序",
        description = "调整分类在同级分类中的显示顺序，较小的排序号排在前面"
    )
    @PostMapping("/{id}/reorder")
    public void reorderCategory(
            @Parameter(description = "分类ID") @PathVariable Long id,
            @Parameter(description = "新的排序号，同级分类中的序号") @RequestParam Integer newOrder) {
        categoryService.reorderCategory(id, newOrder);
    }
} 