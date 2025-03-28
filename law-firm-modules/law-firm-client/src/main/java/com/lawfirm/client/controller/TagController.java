package com.lawfirm.client.controller;

import com.lawfirm.client.service.impl.TagServiceImpl;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.entity.common.ClientTag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户标签控制器
 */
@Tag(name = "客户标签管理")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client/tag")
public class TagController extends BaseController {

    private final TagServiceImpl tagService;

    /**
     * 获取所有标签列表
     */
    @Operation(summary = "获取所有标签列表")
    @GetMapping("/list")
    public CommonResult<List<ClientTag>> list() {
        return success(tagService.listAllTags());
    }

    /**
     * 根据类型获取标签列表
     */
    @Operation(summary = "根据类型获取标签列表")
    @GetMapping("/list/{tagType}")
    public CommonResult<List<ClientTag>> listByType(@PathVariable("tagType") String tagType) {
        return success(tagService.listByType(tagType));
    }

    /**
     * 获取标签详情
     */
    @Operation(summary = "获取标签详情")
    @GetMapping("/{id}")
    public CommonResult<ClientTag> getById(@PathVariable("id") Long id) {
        return success(tagService.getTag(id));
    }

    /**
     * 获取客户的标签列表
     */
    @Operation(summary = "获取客户的标签列表")
    @GetMapping("/client/{clientId}")
    public CommonResult<List<ClientTag>> getClientTags(@PathVariable("clientId") Long clientId) {
        return success(tagService.getClientTags(clientId));
    }

    /**
     * 新增标签
     */
    @Operation(summary = "新增标签")
    @PostMapping
    public CommonResult<Long> add(@Validated @RequestBody ClientTag tag) {
        return success(tagService.createTag(tag));
    }

    /**
     * 修改标签
     */
    @Operation(summary = "修改标签")
    @PutMapping
    public CommonResult<Boolean> update(@Validated @RequestBody ClientTag tag) {
        tagService.updateTag(tag);
        return success(true);
    }

    /**
     * 删除标签
     */
    @Operation(summary = "删除标签")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        return success(tagService.deleteTag(id));
    }

    /**
     * 为客户添加标签
     */
    @Operation(summary = "为客户添加标签")
    @PostMapping("/client/{clientId}/tag/{tagId}")
    public CommonResult<Boolean> addTagToClient(
            @PathVariable("clientId") Long clientId,
            @PathVariable("tagId") Long tagId) {
        tagService.addTagToClient(clientId, tagId);
        return success(true);
    }

    /**
     * 移除客户的标签
     */
    @Operation(summary = "移除客户的标签")
    @DeleteMapping("/client/{clientId}/tag/{tagId}")
    public CommonResult<Boolean> removeTagFromClient(
            @PathVariable("clientId") Long clientId,
            @PathVariable("tagId") Long tagId) {
        tagService.removeTagFromClient(clientId, tagId);
        return success(true);
    }

    /**
     * 设置客户标签
     */
    @Operation(summary = "设置客户标签")
    @PutMapping("/client/{clientId}/tags")
    public CommonResult<Boolean> setClientTags(
            @PathVariable("clientId") Long clientId,
            @RequestBody List<Long> tagIds) {
        tagService.setClientTags(clientId, tagIds);
        return success(true);
    }
}
