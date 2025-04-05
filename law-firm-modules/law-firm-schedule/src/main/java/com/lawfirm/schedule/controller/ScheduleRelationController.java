package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.ScheduleCaseRelationDTO;
import com.lawfirm.model.schedule.dto.ScheduleTaskRelationDTO;
import com.lawfirm.model.schedule.service.ScheduleRelationService;
import com.lawfirm.model.schedule.vo.ScheduleCaseRelationVO;
import com.lawfirm.model.schedule.vo.ScheduleTaskRelationVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 日程关联控制器
 */
@Tag(name = "日程关联")
@RestController("scheduleRelationController")
@RequestMapping("/schedule/relation")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleRelationController {
    
    private final ScheduleRelationService relationService;
    
    @Operation(summary = "创建案件关联")
    @PostMapping("/case")
    @PreAuthorize("hasAuthority('schedule:relation:create')")
    public CommonResult<Boolean> createCaseRelation(@Valid @RequestBody ScheduleCaseRelationDTO relationDTO) {
        log.info("创建日程与案件关联，日程ID：{}，案件ID：{}", relationDTO.getScheduleId(), relationDTO.getCaseId());
        boolean result = relationService.linkCase(
                relationDTO.getScheduleId(), 
                relationDTO.getCaseId(), 
                relationDTO.getDescription());
        return CommonResult.success(result, "创建日程与案件关联成功");
    }
    
    @Operation(summary = "创建任务关联")
    @PostMapping("/task")
    @PreAuthorize("hasAuthority('schedule:relation:create')")
    public CommonResult<Boolean> createTaskRelation(@Valid @RequestBody ScheduleTaskRelationDTO relationDTO) {
        log.info("创建日程与任务关联，日程ID：{}，任务ID：{}", relationDTO.getScheduleId(), relationDTO.getTaskId());
        boolean result = relationService.linkTask(
                relationDTO.getScheduleId(), 
                relationDTO.getTaskId(), 
                relationDTO.getDescription());
        return CommonResult.success(result, "创建日程与任务关联成功");
    }
    
    @Operation(summary = "删除案件关联")
    @DeleteMapping("/case")
    @PreAuthorize("hasAuthority('schedule:relation:delete')")
    public CommonResult<Boolean> deleteCaseRelation(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "案件ID") @RequestParam Long caseId) {
        log.info("删除日程与案件关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
        boolean success = relationService.unlinkCase(scheduleId, caseId);
        return success ? CommonResult.success(true, "删除日程与案件关联成功") : CommonResult.error("删除日程与案件关联失败");
    }
    
    @Operation(summary = "删除任务关联")
    @DeleteMapping("/task")
    @PreAuthorize("hasAuthority('schedule:relation:delete')")
    public CommonResult<Boolean> deleteTaskRelation(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "任务ID") @RequestParam Long taskId) {
        log.info("删除日程与任务关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
        boolean success = relationService.unlinkTask(scheduleId, taskId);
        return success ? CommonResult.success(true, "删除日程与任务关联成功") : CommonResult.error("删除日程与任务关联失败");
    }
    
    @Operation(summary = "获取日程关联的案件")
    @GetMapping("/case/list/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<List<ScheduleCaseRelationVO>> listCaseRelations(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程关联的案件，日程ID：{}", scheduleId);
        List<ScheduleCaseRelationVO> relations = relationService.listCaseRelations(scheduleId);
        return CommonResult.success(relations);
    }
    
    @Operation(summary = "获取日程关联的任务")
    @GetMapping("/task/list/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<List<ScheduleTaskRelationVO>> listTaskRelations(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程关联的任务，日程ID：{}", scheduleId);
        List<ScheduleTaskRelationVO> relations = relationService.listTaskRelations(scheduleId);
        return CommonResult.success(relations);
    }
    
    @Operation(summary = "获取案件关联的日程")
    @GetMapping("/case/schedules/{caseId}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<List<ScheduleCaseRelationVO>> listSchedulesByCaseId(@Parameter(description = "案件ID") @PathVariable Long caseId) {
        log.info("获取案件关联的日程，案件ID：{}", caseId);
        List<ScheduleCaseRelationVO> relations = relationService.listSchedulesByCaseId(caseId);
        return CommonResult.success(relations);
    }
    
    @Operation(summary = "获取任务关联的日程")
    @GetMapping("/task/schedules/{taskId}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<List<ScheduleTaskRelationVO>> listSchedulesByTaskId(@Parameter(description = "任务ID") @PathVariable Long taskId) {
        log.info("获取任务关联的日程，任务ID：{}", taskId);
        List<ScheduleTaskRelationVO> relations = relationService.listSchedulesByTaskId(taskId);
        return CommonResult.success(relations);
    }
    
    @Operation(summary = "批量关联案件")
    @PostMapping("/case/batch")
    @PreAuthorize("hasAuthority('schedule:relation:create')")
    public CommonResult<Boolean> batchLinkCases(@Valid @RequestBody List<ScheduleCaseRelationDTO> relationDTOs) {
        log.info("批量关联案件，数量：{}", relationDTOs.size());
        boolean success = relationService.batchLinkCases(relationDTOs);
        return success ? CommonResult.success(true, "批量关联案件成功") : CommonResult.error("批量关联案件失败");
    }
    
    @Operation(summary = "批量关联任务")
    @PostMapping("/task/batch")
    @PreAuthorize("hasAuthority('schedule:relation:create')")
    public CommonResult<Boolean> batchLinkTasks(@Valid @RequestBody List<ScheduleTaskRelationDTO> relationDTOs) {
        log.info("批量关联任务，数量：{}", relationDTOs.size());
        boolean success = relationService.batchLinkTasks(relationDTOs);
        return success ? CommonResult.success(true, "批量关联任务成功") : CommonResult.error("批量关联任务失败");
    }
} 