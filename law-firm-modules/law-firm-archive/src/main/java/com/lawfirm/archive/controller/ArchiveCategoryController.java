package com.lawfirm.archive.controller;

import com.lawfirm.archive.converter.CategoryConverter;
import com.lawfirm.archive.model.dto.CategoryCreateDTO;
import com.lawfirm.archive.model.dto.CategoryUpdateDTO;
import com.lawfirm.archive.model.entity.ArchiveCategory;
import com.lawfirm.archive.model.vo.CategoryVO;
import com.lawfirm.archive.service.ArchiveCategoryService;
import com.lawfirm.common.core.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 档案分类控制器
 */
@Tag(name = "档案分类管理")
@RestController
@RequestMapping("/archive/categories")
@RequiredArgsConstructor
public class ArchiveCategoryController {

    private final ArchiveCategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @Operation(summary = "创建分类")
    @PostMapping
    public ApiResult<CategoryVO> createCategory(@Valid @RequestBody CategoryCreateDTO createDTO) {
        ArchiveCategory category = categoryConverter.toEntity(createDTO);
        category = categoryService.createCategory(category);
        return ApiResult.success(categoryConverter.toVO(category));
    }

    @Operation(summary = "更新分类")
    @PutMapping("/{id}")
    public ApiResult<CategoryVO> updateCategory(
            @Parameter(description = "分类ID") @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateDTO updateDTO) {
        ArchiveCategory category = categoryService.getCategoryById(id);
        categoryConverter.updateEntity(category, updateDTO);
        category = categoryService.updateCategory(category);
        return ApiResult.success(categoryConverter.toVO(category));
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteCategory(@Parameter(description = "分类ID") @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResult.success();
    }

    @Operation(summary = "获取分类详情")
    @GetMapping("/{id}")
    public ApiResult<CategoryVO> getCategory(@Parameter(description = "分类ID") @PathVariable Long id) {
        ArchiveCategory category = categoryService.getCategoryById(id);
        return ApiResult.success(categoryConverter.toVO(category));
    }

    @Operation(summary = "获取所有分类")
    @GetMapping
    public ApiResult<List<CategoryVO>> getAllCategories() {
        List<ArchiveCategory> categories = categoryService.getAllCategories();
        List<CategoryVO> voList = categories.stream()
                .map(categoryConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "获取子分类")
    @GetMapping("/{parentId}/children")
    public ApiResult<List<CategoryVO>> getChildCategories(
            @Parameter(description = "父分类ID") @PathVariable Long parentId) {
        List<ArchiveCategory> categories = categoryService.getChildCategories(parentId);
        List<CategoryVO> voList = categories.stream()
                .map(categoryConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "获取分类树")
    @GetMapping("/tree")
    public ApiResult<List<CategoryVO>> getCategoryTree() {
        List<ArchiveCategory> categories = categoryService.getCategoryTree();
        List<CategoryVO> voList = categories.stream()
                .map(categoryConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "获取分类路径")
    @GetMapping("/{id}/path")
    public ApiResult<List<CategoryVO>> getCategoryPath(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        List<ArchiveCategory> categories = categoryService.getCategoryPath(id);
        List<CategoryVO> voList = categories.stream()
                .map(categoryConverter::toVO)
                .collect(Collectors.toList());
        return ApiResult.success(voList);
    }

    @Operation(summary = "检查分类编码是否存在")
    @GetMapping("/check-code")
    public ApiResult<Boolean> checkCategoryCodeExists(
            @Parameter(description = "分类编码") @RequestParam String categoryCode) {
        return ApiResult.success(categoryService.checkCategoryCodeExists(categoryCode));
    }

    @Operation(summary = "检查分类名称是否存在")
    @GetMapping("/check-name")
    public ApiResult<Boolean> checkCategoryNameExists(
            @Parameter(description = "分类名称") @RequestParam String categoryName) {
        return ApiResult.success(categoryService.checkCategoryNameExists(categoryName));
    }
} 