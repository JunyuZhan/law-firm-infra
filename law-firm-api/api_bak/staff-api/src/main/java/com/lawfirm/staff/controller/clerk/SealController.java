package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.seal.request.CreateSealRequest;
import com.lawfirm.model.seal.request.UpdateSealRequest;
import com.lawfirm.model.seal.request.SealPageRequest;
import com.lawfirm.model.seal.response.SealResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 印章管理
 */
@Tag(name = "文员-印章管理")
@RestController
@RequestMapping("/staff/clerk/seal")
@RequiredArgsConstructor
public class SealController {

    @Operation(summary = "分页查询印章")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('clerk:seal:query')")
    public Result<PageResult<SealResponse>> page(SealPageRequest request) {
        return Result.ok();
    }

    @Operation(summary = "添加印章")
    @PostMapping
    @PreAuthorize("hasAuthority('clerk:seal:add')")
    @OperationLog(description = "添加印章")
    public Result<Void> add(@RequestBody @Validated CreateSealRequest request) {
        return Result.ok();
    }

    @Operation(summary = "修改印章")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:seal:update')")
    @OperationLog(description = "修改印章")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Validated UpdateSealRequest request) {
        return Result.ok();
    }

    @Operation(summary = "删除印章")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:seal:delete')")
    @OperationLog(description = "删除印章")
    public Result<Void> delete(@PathVariable Long id) {
        return Result.ok();
    }

    @Operation(summary = "获取印章详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:seal:query')")
    public Result<SealResponse> get(@PathVariable Long id) {
        return Result.ok();
    }

    @Operation(summary = "获取印章列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('clerk:seal:query')")
    public Result<List<SealResponse>> list() {
        return Result.ok();
    }

    @Operation(summary = "使用印章")
    @PostMapping("/{id}/use")
    @PreAuthorize("hasAuthority('clerk:seal:use')")
    @OperationLog(description = "使用印章")
    public Result<Void> use(@PathVariable Long id, @RequestParam String purpose) {
        return Result.ok();
    }

    @Operation(summary = "归还印章")
    @PostMapping("/{id}/return")
    @PreAuthorize("hasAuthority('clerk:seal:return')")
    @OperationLog(description = "归还印章")
    public Result<Void> returnSeal(@PathVariable Long id) {
        return Result.ok();
    }

    @Operation(summary = "交接印章")
    @PostMapping("/{id}/handover")
    @PreAuthorize("hasAuthority('clerk:seal:handover')")
    @OperationLog(description = "交接印章")
    public Result<Void> handover(@PathVariable Long id, @RequestParam Long toUserId) {
        return Result.ok();
    }

    @Operation(summary = "年检印章")
    @PostMapping("/{id}/inspect")
    @PreAuthorize("hasAuthority('clerk:seal:inspect')")
    @OperationLog(description = "年检印章")
    public Result<Void> inspect(@PathVariable Long id) {
        return Result.ok();
    }

    @Operation(summary = "销毁印章")
    @PostMapping("/{id}/destroy")
    @PreAuthorize("hasAuthority('clerk:seal:destroy')")
    @OperationLog(description = "销毁印章")
    public Result<Void> destroy(@PathVariable Long id, @RequestParam String reason) {
        return Result.ok();
    }
} 