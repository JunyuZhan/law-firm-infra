package com.lawfirm.admin.controller.system;

import com.lawfirm.admin.model.ApiResult;
import com.lawfirm.admin.model.request.system.post.CreatePostRequest;
import com.lawfirm.admin.model.request.system.post.PostPageRequest;
import com.lawfirm.admin.model.request.system.post.UpdatePostRequest;
import com.lawfirm.admin.model.response.system.post.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "岗位管理")
@RestController
@RequestMapping("/system/post")
@RequiredArgsConstructor
public class SysPostController {

    @Operation(summary = "分页查询岗位")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:post:query')")
    public ApiResult<List<PostResponse>> page(PostPageRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "添加岗位")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:post:add')")
    public ApiResult<Void> add(@RequestBody @Validated CreatePostRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "修改岗位")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:post:update')")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Validated UpdatePostRequest request) {
        return ApiResult.success();
    }

    @Operation(summary = "删除岗位")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public ApiResult<Void> delete(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "获取岗位详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:post:query')")
    public ApiResult<PostResponse> get(@PathVariable Long id) {
        return ApiResult.success();
    }

    @Operation(summary = "获取岗位列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:post:query')")
    public ApiResult<List<PostResponse>> list() {
        return ApiResult.success();
    }
} 
import com.lawfirm.model.base.enums.BaseEnum  
