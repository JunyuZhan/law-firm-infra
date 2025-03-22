package com.lawfirm.document.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.service.DocumentCategoryService;
import com.lawfirm.model.document.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档分类管理控制器
 */
@Slf4j
@RestController("documentCategoryController")
@RequiredArgsConstructor
@RequestMapping("/api/admin/document-categories")
@Tag(name = "文档分类管理", description = "文档分类管理相关接口")
public class DocumentCategoryController {

    @Qualifier("documentCategoryServiceImpl")
    private final DocumentCategoryService categoryService;

    /**
     * 创建分类
     */
    @PostMapping
    @Operation(summary = "创建分类")
    public CommonResult<Long> createCategory(
            @Parameter(description = "创建参数") @RequestBody @Validated CategoryCreateDTO createDTO) {
        Long categoryId = categoryService.createCategory(createDTO);
        return CommonResult.success(categoryId);
    }

    /**
     * 更新分类
     */
    @PutMapping
    @Operation(summary = "更新分类")
    public CommonResult<Void> updateCategory(
            @Parameter(description = "更新参数") @RequestBody @Validated CategoryUpdateDTO updateDTO) {
        categoryService.updateCategory(updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类")
    public CommonResult<Void> deleteCategory(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return CommonResult.success();
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取分类详情")
    public CommonResult<CategoryVO> getCategory(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        CategoryVO category = categoryService.getCategory(id);
        return CommonResult.success(category);
    }

    /**
     * 分页查询分类
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询分类")
    public CommonResult<Page<CategoryVO>> pageCategories(
            @Parameter(description = "查询参数") CategoryQueryDTO queryDTO,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        Page<CategoryVO> page = categoryService.pageCategories(queryDTO);
        return CommonResult.success(page);
    }

    /**
     * 获取分类列表
     */
    @GetMapping
    @Operation(summary = "获取分类列表")
    public CommonResult<List<CategoryVO>> listCategories(
            @Parameter(description = "查询参数") CategoryQueryDTO queryDTO) {
        List<CategoryVO> categories = categoryService.listCategories(queryDTO);
        return CommonResult.success(categories);
    }
}
