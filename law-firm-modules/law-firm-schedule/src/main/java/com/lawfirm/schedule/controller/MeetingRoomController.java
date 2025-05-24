package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.schedule.dto.MeetingRoomDTO;
import com.lawfirm.model.schedule.entity.MeetingRoom;
import com.lawfirm.model.schedule.service.MeetingRoomService;
import com.lawfirm.model.schedule.vo.MeetingRoomVO;
import com.lawfirm.schedule.converter.MeetingRoomConvert;
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
import java.util.stream.Collectors;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

/**
 * 会议室管理控制器
 */
@Tag(name = "会议室管理")
@RestController("meetingRoomController")
@RequestMapping(ScheduleConstants.API_MEETING_ROOM_PREFIX)
@RequiredArgsConstructor
@Validated
@Slf4j
public class MeetingRoomController {
    
    private final MeetingRoomService meetingRoomService;
    private final MeetingRoomConvert meetingRoomConvert;
    
    @Operation(summary = "创建会议室")
    @PostMapping
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_CREATE + "')")
    public CommonResult<Long> createMeetingRoom(@Valid @RequestBody MeetingRoomDTO roomDTO) {
        log.info("创建会议室：{}", roomDTO.getName());
        // 使用转换器将DTO转换为实体
        MeetingRoom meetingRoom = meetingRoomConvert.toEntity(roomDTO);
        
        Long id = meetingRoomService.createMeetingRoom(meetingRoom);
        return CommonResult.success(id, "创建会议室成功");
    }
    
    @Operation(summary = "更新会议室")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_EDIT + "')")
    public CommonResult<Boolean> updateMeetingRoom(
            @Parameter(description = "会议室ID") @PathVariable Long id,
            @Valid @RequestBody MeetingRoomDTO roomDTO) {
        log.info("更新会议室：{}", id);
        // 获取现有会议室
        MeetingRoom existingRoom = meetingRoomService.getById(id);
        if (existingRoom == null) {
            return CommonResult.error("会议室不存在");
        }
        
        // 使用转换器更新实体
        meetingRoomConvert.updateEntity(roomDTO, existingRoom);
        
        boolean success = meetingRoomService.updateMeetingRoom(id, existingRoom);
        return success ? CommonResult.success(true, "更新会议室成功") : CommonResult.error("更新会议室失败");
    }
    
    @Operation(summary = "获取会议室详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_VIEW + "')")
    public CommonResult<MeetingRoomVO> getMeetingRoomDetail(@Parameter(description = "会议室ID") @PathVariable Long id) {
        log.info("获取会议室详情：{}", id);
        MeetingRoomVO roomVO = meetingRoomService.getMeetingRoomDetail(id);
        return CommonResult.success(roomVO);
    }
    
    @Operation(summary = "删除会议室")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_DELETE + "')")
    public CommonResult<Boolean> deleteMeetingRoom(@Parameter(description = "会议室ID") @PathVariable Long id) {
        log.info("删除会议室：{}", id);
        boolean success = meetingRoomService.deleteMeetingRoom(id);
        return success ? CommonResult.success(true, "删除会议室成功") : CommonResult.error("删除会议室失败");
    }
    
    @Operation(summary = "获取所有会议室")
    @GetMapping("/list/all")
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_VIEW + "')")
    public CommonResult<List<MeetingRoomVO>> listAllMeetingRooms() {
        log.info("获取所有会议室");
        // 简单实现，获取所有会议室
        Page<MeetingRoom> page = new Page<>(1, 1000);
        IPage<MeetingRoomVO> iPage = meetingRoomService.pageMeetingRooms(page, null);
        return CommonResult.success(iPage.getRecords());
    }
    
    @Operation(summary = "分页查询会议室")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_VIEW + "')")
    public CommonResult<Page<MeetingRoomVO>> pageMeetingRooms(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "名称关键字") @RequestParam(required = false) String keyword,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        log.info("分页查询会议室，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<MeetingRoom> page = new Page<>(pageNum, pageSize);
        IPage<MeetingRoomVO> iPage = meetingRoomService.pageMeetingRooms(page, status);
        // 将IPage转换为Page
        Page<MeetingRoomVO> result = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        result.setRecords(iPage.getRecords());
        return CommonResult.success(result);
    }
    
    @Operation(summary = "修改会议室状态")
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_EDIT + "')")
    public CommonResult<Boolean> changeMeetingRoomStatus(
            @Parameter(description = "会议室ID") @PathVariable Long id,
            @Parameter(description = "状态") @PathVariable Integer status) {
        log.info("修改会议室状态，ID：{}，状态：{}", id, status);
        boolean success = meetingRoomService.updateStatus(id, status);
        return success ? CommonResult.success(true, "修改会议室状态成功") : CommonResult.error("修改会议室状态失败");
    }
    
    @Operation(summary = "获取可用会议室")
    @GetMapping("/list/available")
    @PreAuthorize("hasAuthority('" + MEETING_ROOM_VIEW + "')")
    public CommonResult<List<MeetingRoomVO>> listAvailableMeetingRooms() {
        log.info("获取可用会议室");
        // 简单实现，获取状态为可用的会议室
        Page<MeetingRoom> page = new Page<>(1, 1000);
        IPage<MeetingRoomVO> iPage = meetingRoomService.pageMeetingRooms(page, 1); // 假设 1 表示可用状态
        return CommonResult.success(iPage.getRecords());
    }
} 