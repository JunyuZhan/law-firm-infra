package com.lawfirm.knowledge.controller;

import com.lawfirm.knowledge.constant.KnowledgeConstants;
import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;
import com.lawfirm.model.knowledge.service.KnowledgeCategoryService;
import com.lawfirm.model.knowledge.vo.KnowledgeCategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识分类控制器
 */
@Slf4j
@Tag(name = "知识分类", description = "知识分类管理接口")
@RestController("knowledgeCategoryController")
@RequestMapping(KnowledgeConstants.API_CATEGORY_PREFIX)
public class KnowledgeCategoryController {

    @Autowired
    private KnowledgeCategoryService categoryService;

    @Autowired
    private KnowledgeConvert knowledgeConvert;

    /**
     * 获取分类树
     */
    @Operation(summary = "获取分类树", description = "获取完整的知识分类树结构")
    @GetMapping("/tree")
    public ResponseEntity<List<KnowledgeCategoryVO>> getCategoryTree() {
        List<KnowledgeCategory> categoryTree = categoryService.getCategoryTree();
        return ResponseEntity.ok(knowledgeConvert.toCategoryVOList(categoryTree));
    }

    /**
     * 获取子分类
     */
    @Operation(summary = "获取子分类", description = "根据父分类ID获取子分类列表")
    @GetMapping("/children/{parentId}")
    public ResponseEntity<List<KnowledgeCategoryVO>> getSubCategories(
            @Parameter(description = "父分类ID") @PathVariable Long parentId) {
        List<KnowledgeCategory> subCategories = categoryService.getSubCategories(parentId);
        return ResponseEntity.ok(knowledgeConvert.toCategoryVOList(subCategories));
    }

    /**
     * 获取分类路径
     */
    @Operation(summary = "获取分类路径", description = "获取指定分类ID的完整路径（从根到当前）")
    @GetMapping("/path/{categoryId}")
    public ResponseEntity<List<KnowledgeCategoryVO>> getCategoryPath(
            @Parameter(description = "分类ID") @PathVariable Long categoryId) {
        List<KnowledgeCategory> categoryPath = categoryService.getCategoryPath(categoryId);
        return ResponseEntity.ok(knowledgeConvert.toCategoryVOList(categoryPath));
    }

    /**
     * 创建分类
     */
    @Operation(summary = "创建分类", description = "创建新的知识分类")
    @PostMapping
    public ResponseEntity<KnowledgeCategoryVO> createCategory(
            @Parameter(description = "分类信息") @RequestBody @Valid KnowledgeCategory category) {
        boolean success = categoryService.save(category);
        if (success) {
            KnowledgeCategory saved = categoryService.getById(category.getId());
            return ResponseEntity.ok(knowledgeConvert.toCategoryVO(saved));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新分类
     */
    @Operation(summary = "更新分类", description = "根据ID更新知识分类")
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeCategoryVO> updateCategory(
            @Parameter(description = "分类ID") @PathVariable Long id,
            @Parameter(description = "分类信息") @RequestBody @Valid KnowledgeCategory category) {
        // 确保ID一致
        if (category.getId() == null) {
            category.setId(id);
        } else if (!id.equals(category.getId())) {
            return ResponseEntity.badRequest().build();
        }
        
        // 检查分类是否存在
        KnowledgeCategory existingCategory = categoryService.getById(id);
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        
        boolean success = categoryService.save(category);
        if (success) {
            KnowledgeCategory updated = categoryService.getById(category.getId());
            return ResponseEntity.ok(knowledgeConvert.toCategoryVO(updated));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除分类
     */
    @Operation(summary = "删除分类", description = "根据ID删除知识分类")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        try {
            boolean success = categoryService.remove(id);
            return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            log.error("删除分类失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
} 