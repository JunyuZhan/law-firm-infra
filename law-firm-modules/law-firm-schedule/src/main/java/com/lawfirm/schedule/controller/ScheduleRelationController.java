package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.ScheduleRelationDTO;
import com.lawfirm.model.schedule.entity.ScheduleRelation;
import com.lawfirm.model.schedule.service.ScheduleRelationService;
import com.lawfirm.model.schedule.vo.ScheduleRelationVO;
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
    
    @Operation(summary = "创建日程关联")
    @PostMapping
    @PreAuthorize("hasAuthority('schedule:relation:create')")
    public CommonResult<Long> createRelation(@Valid @RequestBody ScheduleRelationDTO relationDTO) {
        log.info("创建日程关联，源日程ID：{}，目标日程ID：{}", relationDTO.getSourceId(), relationDTO.getTargetId());
        ScheduleRelation relation = new ScheduleRelation();
        relation.setSourceId(relationDTO.getSourceId());
        relation.setTargetId(relationDTO.getTargetId());
        relation.setRelationType(relationDTO.getRelationType());
        relation.setCreatedBy(SecurityUtils.getUserId());
        
        Long id = relationService.createRelation(relation);
        return CommonResult.success(id, "创建日程关联成功");
    }
    
    @Operation(summary = "删除日程关联")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:relation:delete')")
    public CommonResult<Boolean> deleteRelation(@Parameter(description = "关联ID") @PathVariable Long id) {
        log.info("删除日程关联：{}", id);
        boolean success = relationService.deleteRelation(id);
        return success ? CommonResult.success(true, "删除日程关联成功") : CommonResult.error("删除日程关联失败");
    }
    
    @Operation(summary = "获取关联详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<ScheduleRelationVO> getRelationDetail(@Parameter(description = "关联ID") @PathVariable Long id) {
        log.info("获取日程关联详情：{}", id);
        ScheduleRelationVO relationVO = relationService.getRelationDetail(id);
        return CommonResult.success(relationVO);
    }
    
    @Operation(summary = "获取日程的所有关联")
    @GetMapping("/list/schedule/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<List<ScheduleRelationVO>> listByScheduleId(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程的所有关联，日程ID：{}", scheduleId);
        List<ScheduleRelationVO> relations = relationService.listByScheduleId(scheduleId);
        return CommonResult.success(relations);
    }
    
    @Operation(summary = "获取日程的上级关联")
    @GetMapping("/list/parents/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<List<ScheduleRelationVO>> listParentRelations(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程的上级关联，日程ID：{}", scheduleId);
        List<ScheduleRelationVO> relations = relationService.listParentRelations(scheduleId);
        return CommonResult.success(relations);
    }
    
    @Operation(summary = "获取日程的下级关联")
    @GetMapping("/list/children/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<List<ScheduleRelationVO>> listChildRelations(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程的下级关联，日程ID：{}", scheduleId);
        List<ScheduleRelationVO> relations = relationService.listChildRelations(scheduleId);
        return CommonResult.success(relations);
    }
    
    @Operation(summary = "分页查询日程关联")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<Page<ScheduleRelationVO>> pageRelations(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "源日程ID") @RequestParam(required = false) Long sourceId,
            @Parameter(description = "目标日程ID") @RequestParam(required = false) Long targetId,
            @Parameter(description = "关联类型") @RequestParam(required = false) Integer relationType) {
        log.info("分页查询日程关联，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<ScheduleRelation> page = new Page<>(pageNum, pageSize);
        Page<ScheduleRelationVO> result = relationService.pageRelations(page, sourceId, targetId, relationType);
        return CommonResult.success(result);
    }
    
    @Operation(summary = "检查日程关联")
    @GetMapping("/check")
    @PreAuthorize("hasAuthority('schedule:relation:view')")
    public CommonResult<Boolean> checkRelation(
            @Parameter(description = "源日程ID") @RequestParam Long sourceId,
            @Parameter(description = "目标日程ID") @RequestParam Long targetId,
            @Parameter(description = "关联类型") @RequestParam(required = false) Integer relationType) {
        log.info("检查日程关联，源日程ID：{}，目标日程ID：{}", sourceId, targetId);
        boolean exists = relationService.checkRelation(sourceId, targetId, relationType);
        return CommonResult.success(exists);
    }
} 