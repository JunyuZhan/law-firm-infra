package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.model.base.query.PageResult;
import com.lawfirm.model.base.response.ApiResponse;
import com.lawfirm.staff.model.request.clerk.meeting.*;
import com.lawfirm.staff.model.response.clerk.meeting.MeetingRoomResponse;
import com.lawfirm.staff.service.clerk.MeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 会议室管理
 */
@Tag(name = "文员-会议室管理")
@RestController
@RequestMapping("/api/v1/clerk/meeting-rooms")
@RequiredArgsConstructor
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @GetMapping("/page")
    @Operation(summary = "分页查询会议室")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:query')")
    @OperationLog(description = "分页查询会议室", operationType = "QUERY")
    public ApiResponse<PageResult<MeetingRoomResponse>> page(@Valid MeetingRoomPageRequest request) {
        return ApiResponse.ok(meetingRoomService.page(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取会议室详情")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:query')")
    @OperationLog(description = "获取会议室详情", operationType = "QUERY")
    public ApiResponse<MeetingRoomResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(meetingRoomService.getById(id));
    }

    @PostMapping
    @Operation(summary = "创建会议室")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:create')")
    @OperationLog(description = "创建会议室", operationType = "CREATE")
    public ApiResponse<Void> create(@Valid @RequestBody MeetingRoomCreateRequest request) {
        meetingRoomService.create(request);
        return ApiResponse.ok();
    }

    @PutMapping
    @Operation(summary = "更新会议室")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:update')")
    @OperationLog(description = "更新会议室", operationType = "UPDATE")
    public ApiResponse<Void> update(@Valid @RequestBody MeetingRoomUpdateRequest request) {
        meetingRoomService.update(request);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除会议室")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:delete')")
    @OperationLog(description = "删除会议室", operationType = "DELETE")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        meetingRoomService.delete(id);
        return ApiResponse.ok();
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新会议室状态")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:update')")
    @OperationLog(description = "更新会议室状态", operationType = "UPDATE")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @Valid @RequestBody MeetingRoomStatusUpdateRequest request) {
        meetingRoomService.updateStatus(id, request);
        return ApiResponse.ok();
    }

    @PutMapping("/{id}/maintenance")
    @Operation(summary = "更新会议室维护信息")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:update')")
    @OperationLog(description = "更新会议室维护信息", operationType = "UPDATE")
    public ApiResponse<Void> updateMaintenance(@PathVariable Long id, @Valid @RequestBody MeetingRoomMaintenanceUpdateRequest request) {
        meetingRoomService.updateMaintenance(id, request);
        return ApiResponse.ok();
    }

    @GetMapping("/{id}/usage-records")
    @Operation(summary = "查询会议室使用记录")
    @PreAuthorize("hasAnyAuthority('clerk:meeting:room:query')")
    @OperationLog(description = "查询会议室使用记录", operationType = "QUERY")
    public ApiResponse<PageResult<MeetingRoomResponse>> getUsageRecords(@PathVariable Long id, @Valid MeetingRoomUsageRecordPageRequest request) {
        return ApiResponse.ok(meetingRoomService.getUsageRecords(id, request));
    }
} 