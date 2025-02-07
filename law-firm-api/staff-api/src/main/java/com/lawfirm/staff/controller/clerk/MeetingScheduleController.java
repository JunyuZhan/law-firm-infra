package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.base.query.PageResult;
import com.lawfirm.model.base.response.ApiResponse;
import com.lawfirm.staff.model.request.clerk.meeting.*;
import com.lawfirm.staff.model.response.clerk.meeting.MeetingScheduleResponse;
import com.lawfirm.staff.service.clerk.MeetingScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 会议日程管理
 */
@Tag(name = "文员-会议日程管理")
@RestController
@RequestMapping("/api/v1/clerk/meeting-schedules")
@RequiredArgsConstructor
public class MeetingScheduleController {

    private final MeetingScheduleService meetingScheduleService;

    @GetMapping("/page")
    @Operation(summary = "分页查询会议日程")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:query')")
    @OperationLog(description = "分页查询会议日程", operationType = "QUERY")
    public ApiResponse<PageResult<MeetingScheduleResponse>> page(@Valid MeetingSchedulePageRequest request) {
        return ApiResponse.ok(meetingScheduleService.page(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取会议日程详情")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:query')")
    @OperationLog(description = "获取会议日程详情", operationType = "QUERY")
    public ApiResponse<MeetingScheduleResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(meetingScheduleService.getById(id));
    }

    @PostMapping
    @Operation(summary = "创建会议日程")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:create')")
    @OperationLog(description = "创建会议日程", operationType = "CREATE")
    public ApiResponse<Void> create(@Valid @RequestBody MeetingScheduleCreateRequest request) {
        meetingScheduleService.create(request);
        return ApiResponse.ok();
    }

    @PutMapping
    @Operation(summary = "更新会议日程")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:update')")
    @OperationLog(description = "更新会议日程", operationType = "UPDATE")
    public ApiResponse<Void> update(@Valid @RequestBody MeetingScheduleUpdateRequest request) {
        meetingScheduleService.update(request);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除会议日程")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:delete')")
    @OperationLog(description = "删除会议日程", operationType = "DELETE")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        meetingScheduleService.delete(id);
        return ApiResponse.ok();
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新会议日程状态")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:update')")
    @OperationLog(description = "更新会议日程状态", operationType = "UPDATE")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @Valid @RequestBody MeetingScheduleStatusUpdateRequest request) {
        meetingScheduleService.updateStatus(id, request);
        return ApiResponse.ok();
    }

    @GetMapping("/check-conflict")
    @Operation(summary = "检查会议日程冲突")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:query')")
    @OperationLog(description = "检查会议日程冲突", operationType = "QUERY")
    public ApiResponse<Boolean> checkConflict(@Valid MeetingScheduleCheckConflictRequest request) {
        return ApiResponse.ok(meetingScheduleService.checkConflict(request));
    }

    @GetMapping("/room/{roomId}/available-time")
    @Operation(summary = "获取会议室可用时间")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:schedule:query')")
    @OperationLog(description = "获取会议室可用时间", operationType = "QUERY")
    public ApiResponse<List<MeetingRoomAvailableTimeResponse>> getAvailableTime(@PathVariable Long roomId, @Valid MeetingRoomAvailableTimeRequest request) {
        return ApiResponse.ok(meetingScheduleService.getAvailableTime(roomId, request));
    }
} 