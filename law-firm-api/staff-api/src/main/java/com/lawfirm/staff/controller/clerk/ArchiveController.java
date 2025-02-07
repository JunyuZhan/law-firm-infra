package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.core.response.ApiResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.client.clerk.ArchiveClient;
import com.lawfirm.staff.model.request.clerk.archive.ArchiveCreateRequest;
import com.lawfirm.staff.model.request.clerk.archive.ArchivePageRequest;
import com.lawfirm.staff.model.request.clerk.archive.ArchiveUpdateRequest;
import com.lawfirm.staff.model.response.clerk.archive.ArchiveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "文员-档案管理")
@RestController
@RequestMapping("/api/v1/clerk/archives")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveClient archiveClient;

    @Operation(summary = "分页查询档案")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('clerk:archive:query')")
    @OperationLog(description = "分页查询档案", operationType = "QUERY")
    public ApiResult<PageResult<ArchiveResponse>> page(@Validated ArchivePageRequest request) {
        return archiveClient.page(request);
    }

    @Operation(summary = "创建档案")
    @PostMapping
    @PreAuthorize("hasAuthority('clerk:archive:create')")
    @OperationLog(description = "创建档案", operationType = "CREATE")
    public ApiResult<ArchiveResponse> create(@RequestBody @Validated ArchiveCreateRequest request) {
        return archiveClient.create(request);
    }

    @Operation(summary = "更新档案")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:archive:update')")
    @OperationLog(description = "更新档案", operationType = "UPDATE")
    public ApiResult<ArchiveResponse> update(@PathVariable Long id, @RequestBody @Validated ArchiveUpdateRequest request) {
        return archiveClient.update(id, request);
    }

    @Operation(summary = "删除档案")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:archive:delete')")
    @OperationLog(description = "删除档案", operationType = "DELETE")
    public ApiResult<Void> delete(@PathVariable Long id) {
        return archiveClient.delete(id);
    }

    @Operation(summary = "获取档案详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:archive:query')")
    @OperationLog(description = "获取档案详情", operationType = "QUERY")
    public ApiResult<ArchiveResponse> get(@PathVariable Long id) {
        return archiveClient.get(id);
    }

    @Operation(summary = "借阅档案")
    @PostMapping("/{id}/borrow")
    @PreAuthorize("hasAuthority('clerk:archive:borrow')")
    @OperationLog(description = "借阅档案", operationType = "CREATE")
    public ApiResult<Void> borrow(
            @PathVariable Long id,
            @RequestParam String borrower,
            @RequestParam LocalDateTime expectedReturnTime,
            @RequestParam String borrowReason) {
        return archiveClient.borrow(id, borrower, expectedReturnTime, borrowReason);
    }

    @Operation(summary = "归还档案")
    @PostMapping("/{id}/return")
    @PreAuthorize("hasAuthority('clerk:archive:return')")
    @OperationLog(description = "归还档案", operationType = "UPDATE")
    public ApiResult<Void> returnArchive(@PathVariable Long id) {
        return archiveClient.returnArchive(id);
    }
} 