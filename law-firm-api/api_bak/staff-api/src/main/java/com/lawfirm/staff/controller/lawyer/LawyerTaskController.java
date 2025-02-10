package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.annotation.OperationLog;
import com.lawfirm.staff.annotation.OperationType;
import com.lawfirm.task.model.dto.TaskDTO;
import com.lawfirm.task.model.query.TaskQuery;
import com.lawfirm.task.model.vo.TaskVO;
import com.lawfirm.staff.client.TaskClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 任务管理
 */
@Tag(name = "律师-任务管理")
@RestController
@RequestMapping("/staff/lawyer/task")
@RequiredArgsConstructor
public class LawyerTaskController {

    private final TaskClient taskClient;

    @Operation(summary = "分页查询任务")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('lawyer:task:query')")
    @OperationLog(value = "律师-分页查询任务", type = OperationType.QUERY)
    public Result<PageResult<TaskVO>> page(TaskQuery query) {
        return taskClient.page(query);
    }

    @Operation(summary = "创建任务")
    @PostMapping
    @PreAuthorize("hasAuthority('lawyer:task:add')")
    @OperationLog(value = "律师-创建任务", type = OperationType.CREATE)
    public Result<Void> create(@RequestBody @Validated TaskDTO taskDTO) {
        return taskClient.add(taskDTO);
    }

    @Operation(summary = "修改任务")
    @PutMapping
    @PreAuthorize("hasAuthority('lawyer:task:update')")
    @OperationLog(value = "律师-修改任务", type = OperationType.UPDATE)
    public Result<Void> update(@RequestBody @Validated TaskDTO taskDTO) {
        return taskClient.update(taskDTO);
    }

    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:task:delete')")
    @OperationLog(value = "律师-删除任务", type = OperationType.DELETE)
    public Result<Void> delete(@PathVariable Long id) {
        return taskClient.delete(id);
    }

    @Operation(summary = "获取任务详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:task:query')")
    @OperationLog(value = "律师-获取任务详情", type = OperationType.QUERY)
    public Result<TaskVO> get(@PathVariable Long id) {
        return taskClient.get(id);
    }

    @Operation(summary = "获取我的任务")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('lawyer:task:query')")
    @OperationLog(value = "律师-获取我的任务", type = OperationType.QUERY)
    public Result<PageResult<TaskVO>> getMyTasks(TaskQuery query) {
        return taskClient.getMyTasks(query);
    }

    @Operation(summary = "获取部门任务")
    @GetMapping("/department")
    @PreAuthorize("hasAuthority('lawyer:task:query')")
    @OperationLog(value = "律师-获取部门任务", type = OperationType.QUERY)
    public Result<PageResult<TaskVO>> getDepartmentTasks(TaskQuery query) {
        return taskClient.getDepartmentTasks(query);
    }

    @Operation(summary = "更新任务状态")
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('lawyer:task:update')")
    @OperationLog(value = "律师-更新任务状态", type = OperationType.UPDATE)
    public Result<Void> updateStatus(@RequestBody @Validated TaskDTO taskDTO) {
        return taskClient.updateStatus(taskDTO);
    }
} 