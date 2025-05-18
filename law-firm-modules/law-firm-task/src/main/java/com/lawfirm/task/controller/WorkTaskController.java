package com.lawfirm.task.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.task.dto.WorkTaskAttachmentDTO;
import com.lawfirm.model.task.dto.WorkTaskCommentDTO;
import com.lawfirm.model.task.dto.WorkTaskDTO;
import com.lawfirm.model.task.query.WorkTaskQuery;
import com.lawfirm.model.task.service.WorkTaskAttachmentService;
import com.lawfirm.model.task.service.WorkTaskCommentService;
import com.lawfirm.model.task.service.WorkTaskService;
import com.lawfirm.model.task.vo.WorkTaskVO;
import com.lawfirm.task.constant.TaskBusinessConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作任务控制器
 */
@Slf4j
@Tag(name = "工作任务管理", description = "工作任务管理接口")
@RestController("workTaskController")
@RequestMapping(TaskBusinessConstants.Controller.API_PREFIX)
@RequiredArgsConstructor
public class WorkTaskController {

    private final WorkTaskService workTaskService;
    private final WorkTaskCommentService workTaskCommentService;
    private final WorkTaskAttachmentService workTaskAttachmentService;
    
    /**
     * 创建工作任务
     */
    @Operation(summary = "创建工作任务", description = "创建新的工作任务")
    @PostMapping
    public CommonResult<Long> createTask(
            @Parameter(description = "工作任务创建参数") 
            @RequestBody @Validated WorkTaskDTO taskDTO) {
        log.info("创建工作任务: {}", taskDTO);
        Long taskId = workTaskService.createTask(taskDTO);
        return CommonResult.success(taskId);
    }
    
    /**
     * 更新工作任务
     */
    @Operation(summary = "更新工作任务", description = "更新已有工作任务的基本信息")
    @PutMapping("/{taskId}")
    public CommonResult<Void> updateTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "工作任务更新参数") 
            @RequestBody @Validated WorkTaskDTO taskDTO) {
        log.info("更新工作任务: id={}, {}", taskId, taskDTO);
        taskDTO.setId(taskId);
        workTaskService.updateTask(taskDTO);
        return CommonResult.success();
    }
    
    /**
     * 删除工作任务
     */
    @Operation(summary = "删除工作任务", description = "删除指定的工作任务记录")
    @DeleteMapping("/{taskId}")
    public CommonResult<Void> deleteTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("删除工作任务: {}", taskId);
        workTaskService.deleteTask(taskId);
        return CommonResult.success();
    }
    
    /**
     * 获取工作任务详情
     */
    @Operation(summary = "获取工作任务详情", description = "获取工作任务的详细信息")
    @GetMapping("/{taskId}")
    public CommonResult<WorkTaskDTO> getTaskDetail(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取工作任务详情: {}", taskId);
        return CommonResult.success(workTaskService.getTaskDetail(taskId));
    }
    
    /**
     * 查询工作任务列表
     */
    @Operation(summary = "查询工作任务列表", description = "根据条件查询工作任务列表")
    @GetMapping
    public CommonResult<List<WorkTaskDTO>> queryTaskList(
            @Parameter(description = "查询参数") 
            @Validated WorkTaskQuery query) {
        log.info("查询工作任务列表: {}", query);
        return CommonResult.success(workTaskService.queryTaskList(query));
    }
    
    /**
     * 更新工作任务状态
     */
    @Operation(summary = "更新工作任务状态", description = "更新指定工作任务的状态")
    @PutMapping("/{taskId}/status/{status}")
    public CommonResult<Void> updateTaskStatus(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "目标状态") 
            @PathVariable Integer status) {
        log.info("更新工作任务状态: id={}, status={}", taskId, status);
        workTaskService.updateTaskStatus(taskId, status);
        return CommonResult.success();
    }
    
    /**
     * 更新工作任务优先级
     */
    @Operation(summary = "更新工作任务优先级", description = "更新指定工作任务的优先级")
    @PutMapping("/{taskId}/priority/{priority}")
    public CommonResult<Void> updateTaskPriority(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "优先级") 
            @PathVariable Integer priority) {
        log.info("更新工作任务优先级: id={}, priority={}", taskId, priority);
        workTaskService.updateTaskPriority(taskId, priority);
        return CommonResult.success();
    }
    
    /**
     * 分配工作任务
     */
    @Operation(summary = "分配工作任务", description = "将工作任务分配给指定负责人")
    @PutMapping("/{taskId}/assignee/{assigneeId}")
    public CommonResult<Void> assignTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "负责人ID") 
            @PathVariable Long assigneeId) {
        log.info("分配工作任务: id={}, assigneeId={}", taskId, assigneeId);
        workTaskService.assignTask(taskId, assigneeId);
        return CommonResult.success();
    }
    
    /**
     * 完成工作任务
     */
    @Operation(summary = "完成工作任务", description = "将工作任务标记为已完成")
    @PutMapping("/{taskId}/complete")
    public CommonResult<Void> completeTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("完成工作任务: {}", taskId);
        workTaskService.completeTask(taskId);
        return CommonResult.success();
    }
    
    /**
     * 取消工作任务
     */
    @Operation(summary = "取消工作任务", description = "取消指定的工作任务")
    @PutMapping("/{taskId}/cancel")
    public CommonResult<Void> cancelTask(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId,
            @Parameter(description = "取消原因") 
            @RequestParam(required = false) String reason) {
        log.info("取消工作任务: id={}, reason={}", taskId, reason);
        workTaskService.cancelTask(taskId, reason);
        return CommonResult.success();
    }
    
    /**
     * 获取任务评论列表
     */
    @Operation(summary = "获取任务评论列表", description = "获取指定任务的所有评论")
    @GetMapping("/{taskId}/comments")
    public CommonResult<List<WorkTaskCommentDTO>> getTaskComments(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取任务评论列表: taskId={}", taskId);
        return CommonResult.success(workTaskCommentService.getTaskComments(taskId));
    }
    
    /**
     * 获取任务附件列表
     */
    @Operation(summary = "获取任务附件列表", description = "获取指定任务的所有附件")
    @GetMapping("/{taskId}/attachments")
    public CommonResult<List<WorkTaskAttachmentDTO>> getTaskAttachments(
            @Parameter(description = "任务ID") 
            @PathVariable Long taskId) {
        log.info("获取任务附件列表: taskId={}", taskId);
        return CommonResult.success(workTaskAttachmentService.getAttachmentsByTaskId(taskId));
    }
} 