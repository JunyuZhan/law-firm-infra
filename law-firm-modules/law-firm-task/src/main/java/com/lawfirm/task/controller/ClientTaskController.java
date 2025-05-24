package com.lawfirm.task.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.task.dto.WorkTaskDTO;
import com.lawfirm.model.task.query.WorkTaskQuery;
import com.lawfirm.model.task.service.WorkTaskService;
import com.lawfirm.task.constant.TaskBusinessConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 客户任务控制器
 */
@Slf4j
@Tag(name = "客户任务管理", description = "客户任务管理接口")
@RestController("clientTaskController")
@RequestMapping(TaskBusinessConstants.Controller.API_CLIENT_TASKS_PREFIX)
@RequiredArgsConstructor
public class ClientTaskController {

    private final WorkTaskService workTaskService;
    
    /**
     * 创建客户任务
     */
    @Operation(summary = "创建客户任务", description = "为指定客户创建新任务")
    @PreAuthorize("hasAuthority('" + TASK_CREATE + "')")
    @PostMapping
    public CommonResult<Long> createClientTask(
            @Parameter(description = "客户ID") 
            @PathVariable Long clientId,
            @Parameter(description = "任务创建参数") 
            @RequestBody @Validated WorkTaskDTO taskDTO) {
        log.info("创建客户任务: clientId={}, {}", clientId, taskDTO);
        taskDTO.setClientId(clientId);
        Long taskId = workTaskService.createTask(taskDTO);
        return CommonResult.success(taskId);
    }
    
    /**
     * 获取客户任务列表
     */
    @Operation(summary = "获取客户任务列表", description = "获取指定客户的所有任务")
    @PreAuthorize("hasAuthority('" + TASK_VIEW + "')")
    @GetMapping
    public CommonResult<List<WorkTaskDTO>> getClientTasks(
            @Parameter(description = "客户ID") 
            @PathVariable Long clientId,
            @Parameter(description = "查询参数") 
            WorkTaskQuery query) {
        log.info("获取客户任务列表: clientId={}, query={}", clientId, query);
        if (query == null) {
            query = new WorkTaskQuery();
        }
        query.setClientId(clientId);
        List<WorkTaskDTO> tasks = workTaskService.queryTaskList(query);
        return CommonResult.success(tasks);
    }
    
    /**
     * 获取客户任务统计
     */
    @Operation(summary = "获取客户任务统计", description = "获取指定客户的任务统计信息")
    @PreAuthorize("hasAuthority('" + TASK_VIEW + "')")
    @GetMapping("/statistics")
    public CommonResult<Map<String, Object>> getClientTaskStatistics(
            @Parameter(description = "客户ID") 
            @PathVariable Long clientId) {
        log.info("获取客户任务统计: clientId={}", clientId);
        
        WorkTaskQuery query = new WorkTaskQuery();
        query.setClientId(clientId);
        Map<String, Object> statistics = workTaskService.getTaskStatistics(query);
        
        return CommonResult.success(statistics);
    }
} 