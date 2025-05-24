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
 * 案例任务控制器
 */
@Slf4j
@Tag(name = "案例任务管理", description = "案例任务管理接口")
@RestController("caseTaskController")
@RequestMapping(TaskBusinessConstants.Controller.API_CASE_TASKS_PREFIX)
@RequiredArgsConstructor
public class CaseTaskController {

    private final WorkTaskService workTaskService;
    
    /**
     * 创建案例任务
     */
    @Operation(summary = "创建案例任务", description = "为指定案例创建新任务")
    @PreAuthorize("hasAuthority('" + TASK_CREATE + "')")
    @PostMapping
    public CommonResult<Long> createCaseTask(
            @Parameter(description = "案例ID") 
            @PathVariable Long caseId,
            @Parameter(description = "任务创建参数") 
            @RequestBody @Validated WorkTaskDTO taskDTO) {
        log.info("创建案例任务: caseId={}, {}", caseId, taskDTO);
        taskDTO.setCaseId(caseId);
        Long taskId = workTaskService.createTask(taskDTO);
        return CommonResult.success(taskId);
    }
    
    /**
     * 获取案例任务列表
     */
    @Operation(summary = "获取案例任务列表", description = "获取指定案例的所有任务")
    @PreAuthorize("hasAuthority('" + TASK_VIEW + "')")
    @GetMapping
    public CommonResult<List<WorkTaskDTO>> getCaseTasks(
            @Parameter(description = "案例ID") 
            @PathVariable Long caseId,
            @Parameter(description = "查询参数") 
            WorkTaskQuery query) {
        log.info("获取案例任务列表: caseId={}, query={}", caseId, query);
        if (query == null) {
            query = new WorkTaskQuery();
        }
        query.setCaseId(caseId);
        List<WorkTaskDTO> tasks = workTaskService.queryTaskList(query);
        return CommonResult.success(tasks);
    }
    
    /**
     * 获取案例任务统计
     */
    @Operation(summary = "获取案件任务统计", description = "获取指定案件的任务统计信息")
    @PreAuthorize("hasAuthority('" + TASK_VIEW + "')")
    @GetMapping("/statistics")
    public CommonResult<Map<String, Object>> getCaseTaskStatistics(
            @Parameter(description = "案件ID") 
            @PathVariable Long caseId) {
        log.info("获取案件任务统计: caseId={}", caseId);
        
        WorkTaskQuery query = new WorkTaskQuery();
        query.setCaseId(caseId);
        Map<String, Object> statistics = workTaskService.getTaskStatistics(query);
        
        return CommonResult.success(statistics);
    }
} 