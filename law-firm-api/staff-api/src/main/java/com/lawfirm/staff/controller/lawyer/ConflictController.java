package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.model.request.lawyer.conflict.ConflictCheckRequest;
import com.lawfirm.staff.model.request.lawyer.conflict.ConflictPageRequest;
import com.lawfirm.staff.model.response.lawyer.conflict.ConflictCheckResponse;
import com.lawfirm.staff.model.response.lawyer.conflict.ConflictResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "利益冲突管理")
@RestController
@RequestMapping("/conflict")
@RequiredArgsConstructor
public class ConflictController {

    @Operation(summary = "分页查询冲突记录")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('conflict:query')")
    public Result<PageResult<ConflictResponse>> page(ConflictPageRequest request) {
        // TODO: 调用冲突检查服务
        return Result.ok();
    }

    @Operation(summary = "冲突检查")
    @PostMapping("/check")
    @PreAuthorize("hasAuthority('conflict:check')")
    @OperationLog(value = "冲突检查")
    public Result<ConflictCheckResponse> check(@RequestBody @Validated ConflictCheckRequest request) {
        // TODO: 调用冲突检查服务
        return Result.ok();
    }

    @Operation(summary = "获取冲突记录详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('conflict:query')")
    public Result<ConflictResponse> get(@PathVariable Long id) {
        // TODO: 调用冲突检查服务
        return Result.ok();
    }

    @Operation(summary = "查询我的冲突记录")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('conflict:query')")
    public Result<PageResult<ConflictResponse>> getMyConflicts(ConflictPageRequest request) {
        // TODO: 调用冲突检查服务，只查询当前登录律师的冲突记录
        return Result.ok();
    }

    @Operation(summary = "导出冲突记录")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('conflict:export')")
    @OperationLog(value = "导出冲突记录")
    public void export(ConflictPageRequest request) {
        // TODO: 调用冲突检查服务
    }
} 