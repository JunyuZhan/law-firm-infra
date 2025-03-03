package com.lawfirm.core.workflow.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.service.TaskService;
import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.dto.task.TaskQueryDTO;
import com.lawfirm.model.workflow.vo.TaskVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 任务管理接口
 *
 * @author claude
 */
@Tag(name = "任务管理", description = "提供任务的增删改查等功能")
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    /**
     * 创建任务
     *
     * @param createDTO 创建参数
     * @return 任务ID
     */
    @PostMapping("/create")
    @Operation(summary = "创建任务")
    public ResponseEntity<TaskVO> createTask(
            @Parameter(description = "创建参数") @RequestBody @Valid TaskCreateDTO createDTO) {
        TaskVO task = taskService.createTask(createDTO);
        return ResponseEntity.ok(task);
    }

    /**
     * 获取任务详情
     *
     * @param id 任务ID
     * @return 任务详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取任务详情")
    public ResponseEntity<TaskVO> getTask(
            @Parameter(description = "任务ID") @PathVariable Long id) {
        TaskVO task = taskService.getTask(id);
        return ResponseEntity.ok(task);
    }

    /**
     * 分页查询任务
     *
     * @param queryDTO 查询条件
     * @param current 当前页
     * @param size 每页大小
     * @return 任务分页数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询任务列表")
    public ResponseEntity<List<TaskVO>> listTasks(
            @Parameter(description = "查询条件") TaskQueryDTO queryDTO) {
        List<TaskVO> tasks = taskService.listTasks(queryDTO);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 获取流程的任务列表
     *
     * @param processId 流程ID
     * @return 任务列表
     */
    @GetMapping("/process/{processId}")
    @Operation(summary = "获取流程的任务列表")
    public ResponseEntity<List<TaskVO>> getProcessTasks(
            @Parameter(description = "流程ID") @PathVariable Long processId) {
        List<TaskVO> tasks = taskService.listProcessTasks(processId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 获取我的待办任务
     *
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    @GetMapping("/my/todo")
    @Operation(summary = "获取我的待办任务")
    public ResponseEntity<List<TaskVO>> getMyTodoTasks(
            @Parameter(description = "处理人ID") @RequestParam Long handlerId) {
        List<TaskVO> tasks = taskService.listMyTodoTasks(handlerId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 获取我的已办任务
     *
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    @GetMapping("/my/done")
    @Operation(summary = "获取我的已办任务")
    public ResponseEntity<List<TaskVO>> getMyDoneTasks(
            @Parameter(description = "处理人ID") @RequestParam Long handlerId) {
        List<TaskVO> tasks = taskService.listMyDoneTasks(handlerId);
        return ResponseEntity.ok(tasks);
    }
} 