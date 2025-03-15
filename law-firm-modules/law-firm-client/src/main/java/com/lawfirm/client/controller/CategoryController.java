package com.lawfirm.client.controller;

import com.lawfirm.model.client.service.CategoryService;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.entity.common.ClientCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户分类控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client/category")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    /**
     * 获取分类树结构
     *
     * @return 分类树
     */
    @GetMapping("/tree")
    public CommonResult<List<ClientCategory>> tree() {
        return success(categoryService.getCategoryTree());
    }

    /**
     * 获取分类列表（平铺结构）
     *
     * @return 分类列表
     */
    @GetMapping("/list")
    public CommonResult<List<ClientCategory>> list() {
        return success(categoryService.list());
    }

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类详情
     */
    @GetMapping("/{id}")
    public CommonResult<ClientCategory> getById(@PathVariable("id") Long id) {
        return success(categoryService.getCategory(id));
    }

    /**
     * 新增分类
     *
     * @param category 分类信息
     * @return 操作结果
     */
    @PostMapping
    public CommonResult<Long> add(@Validated @RequestBody ClientCategory category) {
        return success(categoryService.createCategory(category));
    }

    /**
     * 修改分类
     *
     * @param category 分类信息
     * @return 操作结果
     */
    @PutMapping
    public CommonResult<Boolean> update(@Validated @RequestBody ClientCategory category) {
        categoryService.updateCategory(category);
        return success(true);
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        return success(categoryService.deleteCategory(id));
    }

    /**
     * 获取子分类
     *
     * @param parentId 父级ID
     * @return 子分类列表
     */
    @GetMapping("/children/{parentId}")
    public CommonResult<List<ClientCategory>> getChildren(@PathVariable("parentId") Long parentId) {
        return success(categoryService.listByParentId(parentId));
    }
}
