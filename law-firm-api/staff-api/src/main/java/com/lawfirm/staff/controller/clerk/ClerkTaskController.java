package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.model.request.clerk.task.TaskCreateRequest;
import com.lawfirm.staff.model.request.clerk.task.TaskPageRequest;
import com.lawfirm.staff.model.request.clerk.task.TaskUpdateRequest;
import com.lawfirm.staff.model.response.clerk.task.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "书记员任务管理")
@RestController
@RequestMapping("/clerk/task")
@RequiredArgsConstructor
public class ClerkTaskController {

    @Operation(summary = "分页查询任务")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('clerk:task:query')")
    public Result<PageResult<TaskResponse>> page(TaskPageRequest request) {
        // TODO: 调用任务服务
        return Result.ok();
    }

    @Operation(summary = "创建任务")
    @PostMapping
    @PreAuthorize("hasAuthority('clerk:task:create')")
    @OperationLog(value = "文员-创建任务")
    public Result<TaskResponse> create(@RequestBody @Validated TaskCreateRequest request) {
        // TODO: 调用任务服务
        return Result.ok();
    }

    @Operation(summary = "修改任务")
    @PutMapping
    @PreAuthorize("hasAuthority('clerk:task:update')")
    @OperationLog(value = "文员-修改任务")
    public Result<Void> update(@RequestBody @Validated TaskUpdateRequest request) {
        // TODO: 调用任务服务
        return Result.ok();
    }

    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:task:delete')")
    @OperationLog(value = "文员-删除任务")
    public Result<Void> delete(@PathVariable Long id) {
        // TODO: 调用任务服务
        return Result.ok();
    }

    @Operation(summary = "获取任务详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:task:query')")
    public Result<TaskResponse> get(@PathVariable Long id) {
        // TODO: 调用任务服务
        return Result.ok();
    }

    @Operation(summary = "查询我的任务")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('clerk:task:query')")
    public Result<PageResult<TaskResponse>> getMyTasks(TaskPageRequest request) {
        // TODO: 调用任务服务，只查询当前登录书记员的任务
        return Result.ok();
    }

    @Operation(summary = "完成任务")
    @PutMapping("/complete/{id}")
    @PreAuthorize("hasAuthority('clerk:task:complete')")
    @OperationLog(value = "文员-完成任务")
    public Result<Void> complete(@PathVariable Long id) {
        // TODO: 调用任务服务
        return Result.ok();
    }
} 