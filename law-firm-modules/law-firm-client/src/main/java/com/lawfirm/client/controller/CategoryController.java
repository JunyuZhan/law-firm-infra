package com.lawfirm.client.controller;

import com.lawfirm.model.client.service.CategoryService;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.entity.common.ClientCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.client.constant.ClientConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

import java.util.List;

/**
 * 客户分类控制器
 */
@Tag(name = "客户分类管理")
@Slf4j
@RestController("categoryController")
@RequiredArgsConstructor
@RequestMapping(ClientConstants.API_CATEGORY_PREFIX)
public class CategoryController extends BaseController {

    @Qualifier("clientCategoryServiceImpl")
    private final CategoryService categoryService;

    /**
     * 获取分类树结构
     */
    @Operation(summary = "获取分类树结构")
    @PreAuthorize(CLIENT_VIEW)
    @GetMapping("/tree")
    public CommonResult<List<ClientCategory>> tree() {
        return success(categoryService.getCategoryTree());
    }

    /**
     * 获取分类列表（平铺结构）
     */
    @Operation(summary = "获取分类列表")
    @PreAuthorize(CLIENT_VIEW)
    @GetMapping("/list")
    public CommonResult<List<ClientCategory>> list() {
        return success(categoryService.list());
    }

    /**
     * 获取分类详情
     */
    @Operation(summary = "获取分类详情")
    @PreAuthorize(CLIENT_VIEW)
    @GetMapping("/{id}")
    public CommonResult<ClientCategory> getById(@PathVariable("id") Long id) {
        return success(categoryService.getCategory(id));
    }

    /**
     * 新增分类
     */
    @Operation(summary = "新增分类")
    @PreAuthorize(CLIENT_EDIT)
    @PostMapping
    public CommonResult<Long> add(@Validated @RequestBody ClientCategory category) {
        return success(categoryService.createCategory(category));
    }

    /**
     * 修改分类
     */
    @Operation(summary = "修改分类")
    @PreAuthorize(CLIENT_EDIT)
    @PutMapping
    public CommonResult<Boolean> update(@Validated @RequestBody ClientCategory category) {
        categoryService.updateCategory(category);
        return success(true);
    }

    /**
     * 删除分类
     */
    @Operation(summary = "删除分类")
    @PreAuthorize(CLIENT_DELETE)
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        return success(categoryService.deleteCategory(id));
    }

    /**
     * 获取子分类
     */
    @Operation(summary = "获取子分类列表")
    @PreAuthorize(CLIENT_VIEW)
    @GetMapping("/children/{parentId}")
    public CommonResult<List<ClientCategory>> getChildren(@PathVariable("parentId") Long parentId) {
        return success(categoryService.listByParentId(parentId));
    }
}
