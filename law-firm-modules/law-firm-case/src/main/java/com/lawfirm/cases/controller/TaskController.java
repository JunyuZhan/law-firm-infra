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
 * 案件任务管理控制器
 */
@Slf4j
@RestController("caseTaskController")
@RequestMapping("/case/task")
@RequiredArgsConstructor
@Tag(name = "案件任务管理", description = "提供案件任务管理功能，包括任务的创建、分配、完成、跟进等操作")
public class TaskController {

    private final CaseTaskService taskService;

    @Operation(
        summary = "创建任务",
        description = "创建新的案件任务，包括任务基本信息、关联案件、负责人等"
    )
    @PostMapping
    public Long createTask(
            @Parameter(description = "任务信息，包括任务标题、内容、类型、开始时间、截止时间等") 
            @RequestBody @Validated CaseTaskDTO taskDTO) {
        log.info("创建任务: {}", taskDTO);
        return taskService.createTask(taskDTO);
    }

    @Operation(
        summary = "批量创建任务",
        description = "批量创建多个案件任务，支持同时创建多个相关联的任务"
    )
    @PostMapping("/batch")
    public boolean batchCreateTasks(
            @Parameter(description = "任务信息列表，每个任务包括标题、内容、类型等") 
            @RequestBody @Validated List<CaseTaskDTO> taskDTOs) {
        log.info("批量创建任务: {}", taskDTOs);
        return taskService.batchCreateTasks(taskDTOs);
    }

    @Operation(
        summary = "更新任务",
        description = "更新已有任务的信息，包括任务内容、时间、负责人等"
    )
    @PutMapping
    public boolean updateTask(
            @Parameter(description = "更新的任务信息，包括可修改的任务相关字段") 
            @RequestBody @Validated CaseTaskDTO taskDTO) {
        log.info("更新任务: {}", taskDTO);
        return taskService.updateTask(taskDTO);
    }

    @Operation(
        summary = "删除任务",
        description = "删除指定的任务，如果任务已开始或完成则不允许删除"
    )
    @DeleteMapping("/{taskId}")
    public boolean deleteTask(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId) {
        log.info("删除任务: {}", taskId);
        return taskService.deleteTask(taskId);
    }

    @Operation(
        summary = "批量删除任务",
        description = "批量删除多个任务，如果任一任务已开始或完成则不允许删除"
    )
    @DeleteMapping("/batch")
    public boolean batchDeleteTasks(
            @Parameter(description = "任务ID列表") 
            @RequestBody List<Long> taskIds) {
        log.info("批量删除任务: {}", taskIds);
        return taskService.batchDeleteTasks(taskIds);
    }

    @Operation(
        summary = "获取任务详情",
        description = "获取任务的详细信息，包括基本信息、进度、评论等"
    )
    @GetMapping("/{taskId}")
    public CaseTaskVO getTaskDetail(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId) {
        log.info("获取任务详情: {}", taskId);
        return taskService.getTaskDetail(taskId);
    }

    @Operation(
        summary = "获取案件的所有任务",
        description = "获取指定案件关联的所有任务列表"
    )
    @GetMapping("/cases/{caseId}")
    public List<CaseTaskVO> listCaseTasks(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有任务: caseId={}", caseId);
        return taskService.listCaseTasks(caseId);
    }

    @Operation(
        summary = "分页查询任务",
        description = "分页查询案件的任务列表，支持按任务类型和状态筛选"
    )
    @GetMapping("/cases/{caseId}/page")
    public IPage<CaseTaskVO> pageTasks(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "任务类型：1-文书撰写，2-证据收集，3-法律研究，4-客户沟通，5-案件汇报") 
            @RequestParam(required = false) Integer taskType,
            @Parameter(description = "任务状态：1-未开始，2-进行中，3-已暂停，4-已完成，5-已取消") 
            @RequestParam(required = false) Integer taskStatus,
            @Parameter(description = "页码，从1开始") 
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页显示记录数") 
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询任务: caseId={}, taskType={}, taskStatus={}, pageNum={}, pageSize={}", 
                caseId, taskType, taskStatus, pageNum, pageSize);
        return taskService.pageTasks(caseId, taskType, taskStatus, pageNum, pageSize);
    }

    @Operation(
        summary = "开始任务",
        description = "将任务状态更新为进行中，记录实际开始时间"
    )
    @PostMapping("/{taskId}/start")
    public boolean startTask(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId) {
        log.info("开始任务: {}", taskId);
        return taskService.startTask(taskId);
    }

    @Operation(
        summary = "暂停任务",
        description = "暂停正在进行的任务，需要提供暂停原因"
    )
    @PostMapping("/{taskId}/pause")
    public boolean pauseTask(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "暂停原因，说明暂停任务的具体原因") 
            @RequestParam String reason) {
        log.info("暂停任务: taskId={}, reason={}", taskId, reason);
        return taskService.pauseTask(taskId, reason);
    }

    @Operation(
        summary = "恢复任务",
        description = "恢复已暂停的任务，继续执行任务"
    )
    @PostMapping("/{taskId}/resume")
    public boolean resumeTask(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId) {
        log.info("恢复任务: {}", taskId);
        return taskService.resumeTask(taskId);
    }

    @Operation(
        summary = "完成任务",
        description = "将任务标记为已完成，可以添加完成说明"
    )
    @PostMapping("/{taskId}/complete")
    public boolean completeTask(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "完成说明，记录任务完成的具体情况") 
            @RequestParam(required = false) String completionNote) {
        log.info("完成任务: taskId={}, completionNote={}", taskId, completionNote);
        return taskService.completeTask(taskId, completionNote);
    }

    @Operation(
        summary = "取消任务",
        description = "取消未完成的任务，需要提供取消原因"
    )
    @PostMapping("/{taskId}/cancel")
    public boolean cancelTask(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "取消原因，说明取消任务的具体原因") 
            @RequestParam String reason) {
        log.info("取消任务: taskId={}, reason={}", taskId, reason);
        return taskService.cancelTask(taskId, reason);
    }

    @Operation(
        summary = "分配任务",
        description = "将任务分配给指定的负责人处理"
    )
    @PostMapping("/{taskId}/assign")
    public boolean assignTask(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "负责人用户ID") 
            @RequestParam Long assigneeId) {
        log.info("分配任务: taskId={}, assigneeId={}", taskId, assigneeId);
        return taskService.assignTask(taskId, assigneeId);
    }

    @Operation(
        summary = "更新任务进度",
        description = "更新任务的完成进度，可以添加进度说明"
    )
    @PutMapping("/{taskId}/progress")
    public boolean updateTaskProgress(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "完成进度，范围：0-100的整数") 
            @RequestParam Integer progress,
            @Parameter(description = "进度说明，记录当前进度的具体情况") 
            @RequestParam(required = false) String progressNote) {
        log.info("更新任务进度: taskId={}, progress={}, progressNote={}", taskId, progress, progressNote);
        return taskService.updateTaskProgress(taskId, progress, progressNote);
    }

    @Operation(
        summary = "添加任务评论",
        description = "为任务添加评论信息，用于记录任务相关的讨论和建议"
    )
    @PostMapping("/{taskId}/comments")
    public boolean addTaskComment(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "评论内容，记录对任务的意见和建议") 
            @RequestParam String comment) {
        log.info("添加任务评论: taskId={}, comment={}", taskId, comment);
        return taskService.addTaskComment(taskId, comment);
    }

    @Operation(
        summary = "设置任务优先级",
        description = "设置任务的优先级，用于任务的排序和重要程度标识"
    )
    @PutMapping("/{taskId}/priority")
    public boolean setTaskPriority(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId,
            @Parameter(description = "优先级：1-低，2-中，3-高，4-紧急") 
            @RequestParam Integer priority) {
        log.info("设置任务优先级: taskId={}, priority={}", taskId, priority);
        return taskService.setTaskPriority(taskId, priority);
    }

    @Operation(
        summary = "检查任务是否存在",
        description = "检查指定ID的任务是否存在"
    )
    @GetMapping("/{taskId}/exists")
    public boolean checkTaskExists(
            @Parameter(description = "任务ID") 
            @PathVariable("taskId") Long taskId) {
        log.info("检查任务是否存在: {}", taskId);
        return taskService.checkTaskExists(taskId);
    }

    @Operation(
        summary = "统计案件任务数量",
        description = "统计指定案件的任务数量，支持按任务类型和状态统计"
    )
    @GetMapping("/cases/{caseId}/count")
    public int countTasks(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "任务类型：1-文书撰写，2-证据收集，3-法律研究，4-客户沟通，5-案件汇报") 
            @RequestParam(required = false) Integer taskType,
            @Parameter(description = "任务状态：1-未开始，2-进行中，3-已暂停，4-已完成，5-已取消") 
            @RequestParam(required = false) Integer taskStatus) {
        log.info("统计案件任务数量: caseId={}, taskType={}, taskStatus={}", caseId, taskType, taskStatus);
        return taskService.countTasks(caseId, taskType, taskStatus);
    }
} 