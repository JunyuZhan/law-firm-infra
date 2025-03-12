package com.lawfirm.document.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.document.dto.tag.TagCreateDTO;
import com.lawfirm.model.document.dto.tag.TagQueryDTO;
import com.lawfirm.model.document.dto.tag.TagUpdateDTO;
import com.lawfirm.model.document.service.DocumentTagService;
import com.lawfirm.model.document.vo.TagVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档标签管理接口
 */
@Tag(name = "文档标签管理")
@RestController
@RequestMapping("/admin/document/tag")
@RequiredArgsConstructor
@Validated
public class DocumentTagController {

    private final DocumentTagService tagService;

    @Operation(summary = "创建标签")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('document:tag:create')")
    public ResponseResult<Long> createTag(@Valid @RequestBody TagCreateDTO createDTO) {
        return ResponseResult.success(tagService.createTag(createDTO));
    }

    @Operation(summary = "更新标签")
    @PutMapping
    @PreAuthorize("@ss.hasPermission('document:tag:update')")
    public ResponseResult<Void> updateTag(@Valid @RequestBody TagUpdateDTO updateDTO) {
        tagService.updateTag(updateDTO);
        return ResponseResult.success();
    }

    @Operation(summary = "删除标签")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('document:tag:delete')")
    public ResponseResult<Void> deleteTag(@Parameter(description = "标签ID") @PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseResult.success();
    }

    @Operation(summary = "获取标签")
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('document:tag:query')")
    public ResponseResult<TagVO> getTag(@Parameter(description = "标签ID") @PathVariable Long id) {
        return ResponseResult.success(tagService.getTag(id));
    }

    @Operation(summary = "查询标签列表")
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('document:tag:query')")
    public ResponseResult<List<TagVO>> listTags(TagQueryDTO queryDTO) {
        return ResponseResult.success(tagService.listTags(queryDTO));
    }

    @Operation(summary = "分页查询标签")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('document:tag:query')")
    public ResponseResult<Page<TagVO>> pageTags(TagQueryDTO queryDTO) {
        return ResponseResult.success(tagService.pageTags(queryDTO));
    }
} 