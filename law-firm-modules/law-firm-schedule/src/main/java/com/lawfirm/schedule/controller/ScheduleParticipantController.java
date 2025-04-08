package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.ScheduleParticipantDTO;
import com.lawfirm.model.schedule.entity.ScheduleParticipant;
import com.lawfirm.model.schedule.service.ScheduleParticipantService;
import com.lawfirm.model.schedule.vo.ScheduleParticipantVO;
import com.lawfirm.schedule.constant.ScheduleConstants;
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
 * 日程参与人控制器
 */
@Tag(name = "日程参与人")
@RestController("scheduleParticipantController")
@RequestMapping(ScheduleConstants.API_PARTICIPANT_PREFIX)
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleParticipantController {
    
    private final ScheduleParticipantService participantService;
    
    @Operation(summary = "添加参与人")
    @PostMapping
    @PreAuthorize("hasAuthority('schedule:participant:add')")
    public CommonResult<Long> addParticipant(@Valid @RequestBody ScheduleParticipantDTO participantDTO) {
        log.info("添加日程参与人，日程ID：{}，参与人：{}", participantDTO.getScheduleId(), participantDTO.getParticipantId());
        
        // 按照接口定义调用方法
        boolean success = participantService.addParticipant(participantDTO.getScheduleId(), participantDTO);
        
        if (success) {
            return CommonResult.success(participantDTO.getId(), "添加参与人成功");
        } else {
            return CommonResult.error("添加参与人失败");
        }
    }
    
    @Operation(summary = "批量添加参与人")
    @PostMapping("/batch")
    @PreAuthorize("hasAuthority('schedule:participant:add')")
    public CommonResult<Integer> batchAddParticipants(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "参与人列表") @RequestBody List<Long> userIds) {
        log.info("批量添加日程参与人，日程ID：{}", scheduleId);
        int count = participantService.batchAddParticipants(scheduleId, userIds);
        return CommonResult.success(count, "已添加" + count + "个参与人");
    }
    
    @Operation(summary = "移除参与人")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:participant:remove')")
    public CommonResult<Boolean> removeParticipant(@Parameter(description = "参与人ID") @PathVariable Long id) {
        log.info("移除日程参与人，ID：{}", id);
        boolean success = participantService.removeParticipant(id);
        return success ? CommonResult.success(true, "移除参与人成功") : CommonResult.error("移除参与人失败");
    }
    
    @Operation(summary = "批量移除参与人")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('schedule:participant:remove')")
    public CommonResult<Integer> batchRemoveParticipants(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "参与人列表") @RequestBody List<Long> userIds) {
        log.info("批量移除日程参与人，日程ID：{}", scheduleId);
        int count = participantService.batchRemoveParticipants(scheduleId, userIds);
        return CommonResult.success(count, "已移除" + count + "个参与人");
    }
    
    @Operation(summary = "更新参与状态")
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('schedule:participant:update')")
    public CommonResult<Boolean> updateStatus(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "状态") @RequestParam Integer status) {
        log.info("更新日程参与状态，日程ID：{}，状态：{}", scheduleId, status);
        Long userId = SecurityUtils.getUserId();
        boolean success = participantService.updateStatus(scheduleId, userId, status);
        return success ? CommonResult.success(true, "更新状态成功") : CommonResult.error("更新状态失败");
    }
    
    @Operation(summary = "获取日程参与人列表")
    @GetMapping("/list/schedule/{scheduleId}")
    @PreAuthorize("hasAuthority('schedule:participant:view')")
    public CommonResult<List<ScheduleParticipantVO>> listByScheduleId(@Parameter(description = "日程ID") @PathVariable Long scheduleId) {
        log.info("获取日程参与人列表，日程ID：{}", scheduleId);
        List<ScheduleParticipantVO> participants = participantService.listByScheduleId(scheduleId);
        return CommonResult.success(participants);
    }
    
    @Operation(summary = "获取参与人详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:participant:view')")
    public CommonResult<ScheduleParticipantVO> getParticipantDetail(@Parameter(description = "参与人ID") @PathVariable Long id) {
        log.info("获取参与人详情，ID：{}", id);
        ScheduleParticipantVO participantVO = participantService.getDetail(id);
        return CommonResult.success(participantVO);
    }
    
    @Operation(summary = "分页查询参与人")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('schedule:participant:view')")
    public CommonResult<Page<ScheduleParticipantVO>> pageParticipants(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "日程ID") @RequestParam(required = false) Long scheduleId,
            @Parameter(description = "用户ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "角色") @RequestParam(required = false) Integer role,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        log.info("分页查询参与人，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<ScheduleParticipantVO> page = participantService.pageParticipants(
                pageNum, pageSize, scheduleId, userId, role, status);
        return CommonResult.success(page);
    }
    
    @Operation(summary = "检查是否参与日程")
    @GetMapping("/check")
    @PreAuthorize("hasAuthority('schedule:participant:view')")
    public CommonResult<Boolean> checkParticipation(
            @Parameter(description = "日程ID") @RequestParam Long scheduleId,
            @Parameter(description = "用户ID") @RequestParam(required = false) Long userId) {
        log.info("检查是否参与日程，日程ID：{}", scheduleId);
        if (userId == null) {
            userId = SecurityUtils.getUserId();
        }
        boolean participated = participantService.isParticipated(scheduleId, userId);
        return CommonResult.success(participated);
    }
    
    @Operation(summary = "获取我的参与日程")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('schedule:participant:view')")
    public CommonResult<Page<ScheduleParticipantVO>> getMyParticipations(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        log.info("获取我的参与日程，页码：{}，每页大小：{}", pageNum, pageSize);
        Long userId = SecurityUtils.getUserId();
        Page<ScheduleParticipantVO> page = participantService.pageParticipants(
                pageNum, pageSize, null, userId, null, status);
        return CommonResult.success(page);
    }
} 