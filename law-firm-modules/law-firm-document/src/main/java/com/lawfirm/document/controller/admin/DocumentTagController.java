package com.lawfirm.document.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.service.DocumentTagService;
import com.lawfirm.document.constant.DocumentConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

import java.util.List;

/**
 * 文档标签管理控制器
 */
@Slf4j
@Tag(name = "文档标签", description = "文档标签管理接口")
@RestController("documentTagController")
@RequestMapping(DocumentConstants.API_TAG_PREFIX)
@RequiredArgsConstructor
public class DocumentTagController {

    @Qualifier("documentTagServiceImpl")
    private final DocumentTagService documentTagService;

    /**
     * 创建标签
     */
    @PostMapping
    @Operation(summary = "创建标签")
    @PreAuthorize(DOCUMENT_CREATE)
    public CommonResult<Long> createTag(@RequestBody @Validated DocumentTag tag) {
        documentTagService.save(tag);
        return CommonResult.success(tag.getId());
    }

    /**
     * 更新标签
     */
    @PutMapping
    @Operation(summary = "更新标签")
    @PreAuthorize(DOCUMENT_EDIT)
    public CommonResult<Void> updateTag(@RequestBody @Validated DocumentTag tag) {
        documentTagService.updateById(tag);
        return CommonResult.success();
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除标签")
    @PreAuthorize(DOCUMENT_DELETE)
    public CommonResult<Void> deleteTag(@PathVariable Long id) {
        documentTagService.removeById(id);
        return CommonResult.success();
    }

    /**
     * 获取标签详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取标签详情")
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<DocumentTag> getTag(@PathVariable Long id) {
        DocumentTag tag = documentTagService.getById(id);
        return CommonResult.success(tag);
    }

    /**
     * 分页查询标签
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询标签")
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<Page<DocumentTag>> pageTags(
            @Parameter(description = "标签名") @RequestParam(required = false) String tagName,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<DocumentTag> page = new Page<>(pageNum, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<DocumentTag> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        if (tagName != null && !tagName.isEmpty()) {
            wrapper.like(DocumentTag::getTagName, tagName);
        }
        Page<DocumentTag> result = documentTagService.page(page, wrapper);
        return CommonResult.success(result);
    }

    /**
     * 查询所有标签
     */
    @GetMapping
    @Operation(summary = "查询所有标签")
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<List<DocumentTag>> listTags() {
        List<DocumentTag> tags = documentTagService.list();
        return CommonResult.success(tags);
    }

    /**
     * 查询热门标签
     */
    @GetMapping("/hot")
    @Operation(summary = "查询热门标签")
    @PreAuthorize(DOCUMENT_VIEW)
    public CommonResult<List<DocumentTag>> hotTags(@RequestParam(defaultValue = "10") Integer limit) {
        List<DocumentTag> tags = documentTagService.list(new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<DocumentTag>().orderByDesc("usage_count").last("limit " + limit));
        return CommonResult.success(tags);
    }
} 