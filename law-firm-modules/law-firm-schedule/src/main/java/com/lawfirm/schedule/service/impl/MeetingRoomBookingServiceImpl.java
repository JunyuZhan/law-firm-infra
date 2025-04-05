package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.schedule.entity.MeetingRoomBooking;
import com.lawfirm.model.schedule.entity.enums.BookingStatus;
import com.lawfirm.model.schedule.mapper.MeetingRoomBookingMapper;
import com.lawfirm.model.schedule.service.MeetingRoomBookingService;
import com.lawfirm.model.schedule.service.MeetingRoomService;
import com.lawfirm.model.schedule.service.ScheduleEventService;
import com.lawfirm.model.schedule.vo.MeetingRoomBookingVO;
import com.lawfirm.schedule.converter.MeetingRoomBookingConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 会议室预订服务实现类
 */
@Service("meetingRoomBookingService")
@RequiredArgsConstructor
@Slf4j
public class MeetingRoomBookingServiceImpl extends BaseServiceImpl<MeetingRoomBookingMapper, MeetingRoomBooking> implements MeetingRoomBookingService {

    private final MeetingRoomBookingConvert bookingConvert;
    private final MeetingRoomService meetingRoomService;
    private final ScheduleEventService scheduleEventService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBooking(Long meetingRoomId, Long userId, LocalDateTime startTime, 
                             LocalDateTime endTime, String title, String description, Integer bookingType) {
        log.info("创建会议室预订，会议室ID：{}，用户ID：{}，开始时间：{}，结束时间：{}", 
                meetingRoomId, userId, startTime, endTime);
        
        // 检查会议室是否存在并可用
        if (!meetingRoomService.checkAvailability(meetingRoomId, startTime, endTime)) {
            log.error("会议室不可用，会议室ID：{}", meetingRoomId);
            throw new RuntimeException("会议室不可用");
        }
        
        // 检查时间冲突
        if (checkTimeConflict(meetingRoomId, startTime, endTime, null)) {
            log.error("预订时间冲突，会议室ID：{}，开始时间：{}，结束时间：{}", 
                    meetingRoomId, startTime, endTime);
            throw new RuntimeException("预订时间冲突");
        }
        
        MeetingRoomBooking booking = new MeetingRoomBooking();
        booking.setMeetingRoomId(meetingRoomId);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTitle(title);
        booking.setDescription(description);
        booking.setBookingType(bookingType);
        booking.setUserId(userId);
        booking.setStatus(0); // 待审核
        booking.setCreateTime(LocalDateTime.now());
        
        save(booking);
        
        // 发布预订事件
        scheduleEventService.publishMeetingRoomBookedEvent(booking);
        
        return booking.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBooking(Long id, MeetingRoomBooking booking) {
        log.info("更新会议室预订，ID：{}", id);
        
        MeetingRoomBooking existingBooking = getById(id);
        if (existingBooking == null) {
            log.error("更新会议室预订失败，预订不存在，ID：{}", id);
            return false;
        }
        
        // 如果会议室ID或时间有变更，需要检查冲突
        if (!Objects.equals(existingBooking.getMeetingRoomId(), booking.getMeetingRoomId()) ||
            !Objects.equals(existingBooking.getStartTime(), booking.getStartTime()) ||
            !Objects.equals(existingBooking.getEndTime(), booking.getEndTime())) {
            
            // 检查会议室是否存在并可用
            if (!meetingRoomService.checkAvailability(booking.getMeetingRoomId(), 
                                                    booking.getStartTime(), 
                                                    booking.getEndTime())) {
                log.error("会议室不可用，会议室ID：{}", booking.getMeetingRoomId());
                return false;
            }
            
            // 检查时间冲突
            if (checkTimeConflict(booking.getMeetingRoomId(), 
                                 booking.getStartTime(), 
                                 booking.getEndTime(), 
                                 id)) {
                log.error("预订时间冲突，会议室ID：{}，开始时间：{}，结束时间：{}", 
                        booking.getMeetingRoomId(), booking.getStartTime(), booking.getEndTime());
                return false;
            }
        }
        
        booking.setId(id);
        booking.setUpdateTime(LocalDateTime.now());
        
        return updateById(booking);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelBooking(Long id, Long userId) {
        log.info("取消会议室预订，ID：{}，用户ID：{}", id, userId);
        
        MeetingRoomBooking booking = getById(id);
        if (booking == null) {
            log.error("取消会议室预订失败，预订不存在，ID：{}", id);
            return false;
        }
        
        // 检查权限
        if (!Objects.equals(booking.getUserId(), userId)) {
            log.error("取消会议室预订失败，没有权限，用户ID：{}", userId);
            return false;
        }
        
        booking.setStatus(3); // 已取消
        booking.setUpdateTime(LocalDateTime.now());
        
        boolean result = updateById(booking);
        
        if (result) {
            // 发布取消事件
            scheduleEventService.publishBookingCancelledEvent(booking, "用户取消");
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmBooking(Long id) {
        log.info("确认会议室预订，ID：{}", id);
        
        MeetingRoomBooking booking = getById(id);
        if (booking == null) {
            log.error("确认会议室预订失败，预订不存在，ID：{}", id);
            return false;
        }
        
        if (booking.getStatus() != 0) {
            log.error("确认会议室预订失败，当前状态不是待审核，ID：{}，状态：{}", id, booking.getStatus());
            return false;
        }
        
        booking.setStatus(1); // 已确认
        booking.setUpdateTime(LocalDateTime.now());
        
        boolean result = updateById(booking);
        
        if (result) {
            // 发布确认事件
            scheduleEventService.publishBookingConfirmedEvent(booking);
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeBooking(Long id) {
        log.info("完成会议室预订，ID：{}", id);
        
        MeetingRoomBooking booking = getById(id);
        if (booking == null) {
            log.error("完成会议室预订失败，预订不存在，ID：{}", id);
            return false;
        }
        
        if (booking.getStatus() != 1) {
            log.error("完成会议室预订失败，当前状态不是已确认，ID：{}，状态：{}", id, booking.getStatus());
            return false;
        }
        
        booking.setStatus(2); // 已完成
        booking.setUpdateTime(LocalDateTime.now());
        
        return updateById(booking);
    }

    @Override
    public MeetingRoomBookingVO getBookingDetail(Long id) {
        log.info("获取会议室预订详情，ID：{}", id);
        
        MeetingRoomBooking booking = getById(id);
        if (booking == null) {
            return null;
        }
        
        return bookingConvert.toVO(booking);
    }

    @Override
    public IPage<MeetingRoomBookingVO> pageBookings(Page<MeetingRoomBooking> page, 
                                                   Long roomId, Long userId, Integer statusCode) {
        log.info("分页查询会议室预订，会议室ID：{}，用户ID：{}，状态：{}", roomId, userId, statusCode);
        
        LambdaQueryWrapper<MeetingRoomBooking> queryWrapper = new LambdaQueryWrapper<>();
        
        if (roomId != null) {
            queryWrapper.eq(MeetingRoomBooking::getMeetingRoomId, roomId);
        }
        
        if (userId != null) {
            queryWrapper.eq(MeetingRoomBooking::getUserId, userId);
        }
        
        if (statusCode != null) {
            queryWrapper.eq(MeetingRoomBooking::getStatus, statusCode);
        }
        
        queryWrapper.orderByDesc(MeetingRoomBooking::getCreateTime);
        
        IPage<MeetingRoomBooking> result = page(page, queryWrapper);
        
        return result.convert(bookingConvert::toVO);
    }

    @Override
    public List<MeetingRoomBookingVO> listUserBookings(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("查询用户的会议室预订记录，用户ID：{}，开始日期：{}，结束日期：{}", userId, startDate, endDate);
        
        if (startDate == null) {
            startDate = LocalDateTime.now().minusDays(30);
        }
        
        if (endDate == null) {
            endDate = LocalDateTime.now().plusDays(30);
        }
        
        List<MeetingRoomBooking> bookings = baseMapper.findUserBookings(userId, startDate, endDate);
        return bookings.stream()
                .map(bookingConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoomBookingVO> listRoomBookings(Long roomId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("查询会议室的预订记录，会议室ID：{}，开始日期：{}，结束日期：{}", roomId, startDate, endDate);
        
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        
        if (endDate == null) {
            endDate = startDate.plusDays(7);
        }
        
        List<MeetingRoomBooking> bookings = baseMapper.findRoomBookings(roomId, startDate, endDate);
        return bookings.stream()
                .map(bookingConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoomBookingVO> listBookingsByDate(LocalDateTime date) {
        log.info("查询指定日期的所有预订，日期：{}", date);
        
        if (date == null) {
            date = LocalDateTime.now();
        }
        
        List<MeetingRoomBooking> bookings = baseMapper.findBookingsByDate(date);
        return bookings.stream()
                .map(bookingConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkTimeConflict(Long roomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId) {
        log.info("检查时间冲突，会议室ID：{}，开始时间：{}，结束时间：{}，排除的预订ID：{}", 
                roomId, startTime, endTime, excludeBookingId);
        
        List<MeetingRoomBooking> conflicts = baseMapper.findConflicts(roomId, startTime, endTime, excludeBookingId);
        return !conflicts.isEmpty();
    }

    /**
     * 检查会议室预定冲突
     *
     * @param meetingRoomId 会议室ID
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param excludeId     排除的预定ID
     * @return true表示有冲突，false表示无冲突
     */
    @Override
    public boolean checkConflict(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeId) {
        log.info("检查会议室预定冲突，会议室ID：{}，时间范围：{} - {}", meetingRoomId, startTime, endTime);
        
        // 验证参数
        if (meetingRoomId == null || startTime == null || endTime == null) {
            return false;
        }
        
        // 构建查询条件
        LambdaQueryWrapper<MeetingRoomBooking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeetingRoomBooking::getMeetingRoomId, meetingRoomId)
                .eq(MeetingRoomBooking::getStatus, BookingStatus.CONFIRMED.getCode());
        
        // 排除指定ID
        if (excludeId != null) {
            queryWrapper.ne(MeetingRoomBooking::getId, excludeId);
        }
        
        // 查询时间冲突的预定
        // 1. 新预定开始时间在已有预定时间范围内
        // 2. 新预定结束时间在已有预定时间范围内
        // 3. 新预定时间范围完全包含已有预定
        queryWrapper.and(w -> w
                .between(MeetingRoomBooking::getStartTime, startTime, endTime)
                .or()
                .between(MeetingRoomBooking::getEndTime, startTime, endTime)
                .or()
                .nested(n -> n
                        .le(MeetingRoomBooking::getStartTime, startTime)
                        .ge(MeetingRoomBooking::getEndTime, endTime)
                )
        );
        
        return count(queryWrapper) > 0;
    }
} 