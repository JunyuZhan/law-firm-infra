package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.MeetingRoomBookingDTO;
import com.lawfirm.model.schedule.entity.MeetingRoomBooking;
import com.lawfirm.model.schedule.service.MeetingRoomBookingService;
import com.lawfirm.model.schedule.vo.MeetingRoomBookingVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议室预订管理控制器
 */
@Tag(name = "会议室预订管理")
@RestController("meetingRoomBookingController")
@RequestMapping("/schedule/meeting-booking")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MeetingRoomBookingController {
    
    private final MeetingRoomBookingService bookingService;
    
    @Operation(summary = "创建预订")
    @PostMapping
    @PreAuthorize("hasAuthority('schedule:meeting:create')")
    public CommonResult<Long> createBooking(@Valid @RequestBody MeetingRoomBookingDTO bookingDTO) {
        log.info("创建会议室预订：{}", bookingDTO.getTitle());
        Long id = bookingService.createBooking(
            bookingDTO.getMeetingRoomId(),
            SecurityUtils.getUserId(),
            bookingDTO.getStartTime(),
            bookingDTO.getEndTime(),
            bookingDTO.getTitle(),
            bookingDTO.getDescription(),
            bookingDTO.getBookingType()
        );
        return CommonResult.success(id, "创建预订成功");
    }
    
    @Operation(summary = "更新预订")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:meeting:update')")
    public CommonResult<Boolean> updateBooking(
            @Parameter(description = "预订ID") @PathVariable Long id,
            @Valid @RequestBody MeetingRoomBookingDTO bookingDTO) {
        log.info("更新会议室预订：{}", id);
        // 将DTO转换为实体
        MeetingRoomBooking booking = new MeetingRoomBooking();
        booking.setMeetingRoomId(bookingDTO.getMeetingRoomId());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setTitle(bookingDTO.getTitle());
        booking.setDescription(bookingDTO.getDescription());
        booking.setBookingType(bookingDTO.getBookingType());
        
        boolean success = bookingService.updateBooking(id, booking);
        return success ? CommonResult.success(true, "更新预订成功") : CommonResult.error("更新预订失败");
    }
    
    @Operation(summary = "获取预订详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:meeting:view')")
    public CommonResult<MeetingRoomBookingVO> getBookingDetail(@Parameter(description = "预订ID") @PathVariable Long id) {
        log.info("获取会议室预订详情：{}", id);
        MeetingRoomBookingVO bookingVO = bookingService.getBookingDetail(id);
        return CommonResult.success(bookingVO);
    }
    
    @Operation(summary = "取消预订")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:meeting:delete')")
    public CommonResult<Boolean> cancelBooking(@Parameter(description = "预订ID") @PathVariable Long id) {
        log.info("取消会议室预订：{}", id);
        boolean success = bookingService.cancelBooking(id, SecurityUtils.getUserId());
        return success ? CommonResult.success(true, "取消预订成功") : CommonResult.error("取消预订失败");
    }
    
    @Operation(summary = "获取会议室的预订列表")
    @GetMapping("/list/room/{roomId}")
    @PreAuthorize("hasAuthority('schedule:meeting:view')")
    public CommonResult<List<MeetingRoomBookingVO>> listByRoomId(
            @Parameter(description = "会议室ID") @PathVariable Long roomId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("获取会议室的预订列表，会议室ID：{}，时间段：{} - {}", roomId, startTime, endTime);
        List<MeetingRoomBookingVO> bookings = bookingService.listRoomBookings(roomId, startTime, endTime);
        return CommonResult.success(bookings);
    }
    
    @Operation(summary = "获取用户的预订列表")
    @GetMapping("/list/user/{userId}")
    @PreAuthorize("hasAuthority('schedule:meeting:view')")
    public CommonResult<List<MeetingRoomBookingVO>> listByUserId(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("获取用户的会议室预订列表，用户ID：{}，时间段：{} - {}", userId, startTime, endTime);
        List<MeetingRoomBookingVO> bookings = bookingService.listUserBookings(userId, startTime, endTime);
        return CommonResult.success(bookings);
    }
    
    @Operation(summary = "分页查询预订")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('schedule:meeting:view')")
    public CommonResult<Page<MeetingRoomBookingVO>> pageBookings(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "会议室ID") @RequestParam(required = false) Long roomId,
            @Parameter(description = "预订人ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        log.info("分页查询会议室预订，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<MeetingRoomBooking> page = new Page<>(pageNum, pageSize);
        IPage<MeetingRoomBookingVO> result = bookingService.pageBookings(page, roomId, userId, status);
        // 将IPage转换为Page
        Page<MeetingRoomBookingVO> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(result.getRecords());
        return CommonResult.success(resultPage);
    }
    
    @Operation(summary = "审核预订")
    @PutMapping("/{id}/review")
    @PreAuthorize("hasAuthority('schedule:meeting:admin')")
    public CommonResult<Boolean> reviewBooking(
            @Parameter(description = "预订ID") @PathVariable Long id,
            @Parameter(description = "审核状态") @RequestParam Integer status,
            @Parameter(description = "审核备注") @RequestParam(required = false) String remarks) {
        log.info("审核会议室预订，预订ID：{}，审核状态：{}", id, status);
        boolean success = status == 1 ? bookingService.confirmBooking(id) : bookingService.cancelBooking(id, null);
        return success ? CommonResult.success(true, "审核预订成功") : CommonResult.error("审核预订失败");
    }
    
    @Operation(summary = "检查会议室是否可用")
    @GetMapping("/check-availability")
    @PreAuthorize("hasAuthority('schedule:meeting:view')")
    public CommonResult<Boolean> checkRoomAvailability(
            @Parameter(description = "会议室ID") @RequestParam Long roomId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @Parameter(description = "当前预订ID") @RequestParam(required = false) Long currentBookingId) {
        log.info("检查会议室是否可用，会议室ID：{}，时间段：{} - {}", roomId, startTime, endTime);
        boolean available = !bookingService.checkTimeConflict(roomId, startTime, endTime, currentBookingId);
        return CommonResult.success(available, available ? "会议室可用" : "会议室已被预订");
    }
    
    @Operation(summary = "获取我的预订列表")
    @GetMapping("/list/my")
    @PreAuthorize("hasAuthority('schedule:meeting:view')")
    public CommonResult<List<MeetingRoomBookingVO>> listMyBookings(
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("获取我的会议室预订列表，时间段：{} - {}", startTime, endTime);
        Long userId = SecurityUtils.getUserId();
        List<MeetingRoomBookingVO> bookings = bookingService.listUserBookings(userId, startTime, endTime);
        return CommonResult.success(bookings);
    }
} 