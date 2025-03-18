package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseTaskDTO;
import com.lawfirm.model.cases.service.business.CaseTaskService;
import com.lawfirm.model.cases.vo.business.CaseTaskVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件任务管理控制器实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cases/tasks")
@RequiredArgsConstructor
@Tag(name = "案件任务管理", description = "案件任务管理相关接口")
public class TaskControllerImpl {

    private final CaseTaskService taskService;

    @PostMapping
    @Operation(summary = "创建任务")
    public Long createTask(@RequestBody @Validated CaseTaskDTO taskDTO) {
        log.info("创建任务: {}", taskDTO);
        return taskService.createTask(taskDTO);
    }

    @PostMapping("/batch")
    @Operation(summary = "批量创建任务")
    public boolean batchCreateTasks(@RequestBody @Validated List<CaseTaskDTO> taskDTOs) {
        log.info("批量创建任务: {}", taskDTOs);
        return taskService.batchCreateTasks(taskDTOs);
    }

    @PutMapping
    @Operation(summary = "更新任务")
    public boolean updateTask(@RequestBody @Validated CaseTaskDTO taskDTO) {
        log.info("更新任务: {}", taskDTO);
        return taskService.updateTask(taskDTO);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "删除任务")
    public boolean deleteTask(@PathVariable("taskId") Long taskId) {
        log.info("删除任务: {}", taskId);
        return taskService.deleteTask(taskId);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除任务")
    public boolean batchDeleteTasks(@RequestBody List<Long> taskIds) {
        log.info("批量删除任务: {}", taskIds);
        return taskService.batchDeleteTasks(taskIds);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "获取任务详情")
    public CaseTaskVO getTaskDetail(@PathVariable("taskId") Long taskId) {
        log.info("获取任务详情: {}", taskId);
        return taskService.getTaskDetail(taskId);
    }

    @GetMapping("/cases/{caseId}")
    @Operation(summary = "获取案件的所有任务")
    public List<CaseTaskVO> listCaseTasks(@PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有任务: caseId={}", caseId);
        return taskService.listCaseTasks(caseId);
    }

    @GetMapping("/cases/{caseId}/page")
    @Operation(summary = "分页查询任务")
    public IPage<CaseTaskVO> pageTasks(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "任务类型") @RequestParam(required = false) Integer taskType,
            @Parameter(description = "任务状态") @RequestParam(required = false) Integer taskStatus,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询任务: caseId={}, taskType={}, taskStatus={}, pageNum={}, pageSize={}", 
                caseId, taskType, taskStatus, pageNum, pageSize);
        return taskService.pageTasks(caseId, taskType, taskStatus, pageNum, pageSize);
    }

    @PostMapping("/{taskId}/start")
    @Operation(summary = "开始任务")
    public boolean startTask(@PathVariable("taskId") Long taskId) {
        log.info("开始任务: {}", taskId);
        return taskService.startTask(taskId);
    }

    @PostMapping("/{taskId}/pause")
    @Operation(summary = "暂停任务")
    public boolean pauseTask(
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "暂停原因") @RequestParam String reason) {
        log.info("暂停任务: taskId={}, reason={}", taskId, reason);
        return taskService.pauseTask(taskId, reason);
    }

    @PostMapping("/{taskId}/resume")
    @Operation(summary = "恢复任务")
    public boolean resumeTask(@PathVariable("taskId") Long taskId) {
        log.info("恢复任务: {}", taskId);
        return taskService.resumeTask(taskId);
    }

    @PostMapping("/{taskId}/complete")
    @Operation(summary = "完成任务")
    public boolean completeTask(
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "完成说明") @RequestParam(required = false) String completionNote) {
        log.info("完成任务: taskId={}, completionNote={}", taskId, completionNote);
        return taskService.completeTask(taskId, completionNote);
    }

    @PostMapping("/{taskId}/cancel")
    @Operation(summary = "取消任务")
    public boolean cancelTask(
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        log.info("取消任务: taskId={}, reason={}", taskId, reason);
        return taskService.cancelTask(taskId, reason);
    }

    @PostMapping("/{taskId}/assign")
    @Operation(summary = "分配任务")
    public boolean assignTask(
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "受理人ID") @RequestParam Long assigneeId) {
        log.info("分配任务: taskId={}, assigneeId={}", taskId, assigneeId);
        return taskService.assignTask(taskId, assigneeId);
    }

    @PutMapping("/{taskId}/progress")
    @Operation(summary = "更新任务进度")
    public boolean updateTaskProgress(
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "进度（0-100）") @RequestParam Integer progress,
            @Parameter(description = "进度说明") @RequestParam(required = false) String progressNote) {
        log.info("更新任务进度: taskId={}, progress={}, progressNote={}", taskId, progress, progressNote);
        return taskService.updateTaskProgress(taskId, progress, progressNote);
    }

    @PostMapping("/{taskId}/comments")
    @Operation(summary = "添加任务评论")
    public boolean addTaskComment(
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "评论内容") @RequestParam String comment) {
        log.info("添加任务评论: taskId={}, comment={}", taskId, comment);
        return taskService.addTaskComment(taskId, comment);
    }

    @PutMapping("/{taskId}/priority")
    @Operation(summary = "设置任务优先级")
    public boolean setTaskPriority(
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "优先级") @RequestParam Integer priority) {
        log.info("设置任务优先级: taskId={}, priority={}", taskId, priority);
        return taskService.setTaskPriority(taskId, priority);
    }

    @GetMapping("/{taskId}/exists")
    @Operation(summary = "检查任务是否存在")
    public boolean checkTaskExists(@PathVariable("taskId") Long taskId) {
        log.info("检查任务是否存在: {}", taskId);
        return taskService.checkTaskExists(taskId);
    }

    @GetMapping("/cases/{caseId}/count")
    @Operation(summary = "统计案件任务数量")
    public int countTasks(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "任务类型") @RequestParam(required = false) Integer taskType,
            @Parameter(description = "任务状态") @RequestParam(required = false) Integer taskStatus) {
        log.info("统计案件任务数量: caseId={}, taskType={}, taskStatus={}", caseId, taskType, taskStatus);
        return taskService.countTasks(caseId, taskType, taskStatus);
    }
} 