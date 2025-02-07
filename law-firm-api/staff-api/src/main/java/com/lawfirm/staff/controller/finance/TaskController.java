package com.lawfirm.staff.controller.finance;

import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.base.query.PageResult;
import com.lawfirm.model.base.response.ApiResponse;
import com.lawfirm.staff.model.request.finance.task.TaskCreateRequest;
import com.lawfirm.staff.model.request.finance.task.TaskPageRequest;
import com.lawfirm.staff.model.request.finance.task.TaskUpdateRequest;
import com.lawfirm.staff.model.response.finance.task.TaskResponse;
import com.lawfirm.staff.service.finance.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "财务任务管理")
@RestController
@RequestMapping("/api/v1/finance/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/page")
    @Operation(summary = "分页查询财务任务")
    @PreAuthorize("hasAnyAuthority('finance:task:query')")
    @OperationLog(description = "分页查询财务任务", operationType = "QUERY")
    public ApiResponse<PageResult<TaskResponse>> page(@Valid TaskPageRequest request) {
        return ApiResponse.ok(taskService.page(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取财务任务详情")
    @PreAuthorize("hasAnyAuthority('finance:task:query')")
    @OperationLog(description = "获取财务任务详情", operationType = "QUERY")
    public ApiResponse<TaskResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(taskService.getById(id));
    }

    @PostMapping
    @Operation(summary = "创建财务任务")
    @PreAuthorize("hasAnyAuthority('finance:task:create')")
    @OperationLog(description = "创建财务任务", operationType = "CREATE")
    public ApiResponse<Void> create(@Valid @RequestBody TaskCreateRequest request) {
        taskService.create(request);
        return ApiResponse.ok();
    }

    @PutMapping
    @Operation(summary = "更新财务任务")
    @PreAuthorize("hasAnyAuthority('finance:task:update')")
    @OperationLog(description = "更新财务任务", operationType = "UPDATE")
    public ApiResponse<Void> update(@Valid @RequestBody TaskUpdateRequest request) {
        taskService.update(request);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除财务任务")
    @PreAuthorize("hasAnyAuthority('finance:task:delete')")
    @OperationLog(description = "删除财务任务", operationType = "DELETE")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ApiResponse.ok();
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "完成财务任务")
    @PreAuthorize("hasAnyAuthority('finance:task:update')")
    @OperationLog(description = "完成财务任务", operationType = "UPDATE")
    public ApiResponse<Void> complete(@PathVariable Long id) {
        taskService.complete(id);
        return ApiResponse.ok();
    }
} 