package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.schedule.entity.MeetingRoomBooking;
import com.lawfirm.model.schedule.vo.MeetingRoomBookingVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议室预订服务接口
 */
public interface MeetingRoomBookingService extends IService<MeetingRoomBooking> {
    
    /**
     * 创建会议室预订
     *
     * @param meetingRoomId 会议室ID
     * @param userId        用户ID
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param title         标题
     * @param description   描述
     * @param bookingType   预订类型
     * @return 预订ID
     */
    Long createBooking(Long meetingRoomId, Long userId, LocalDateTime startTime, LocalDateTime endTime, String title, String description, Integer bookingType);
    
    /**
     * 更新会议室预订
     *
     * @param id      预订ID
     * @param booking 预订信息
     * @return 是否成功
     */
    boolean updateBooking(Long id, MeetingRoomBooking booking);
    
    /**
     * 获取预订详情
     *
     * @param id 预订ID
     * @return 预订详情
     */
    MeetingRoomBookingVO getBookingDetail(Long id);
    
    /**
     * 取消预订
     *
     * @param id     预订ID
     * @param userId 用户ID，null表示管理员操作
     * @return 是否成功
     */
    boolean cancelBooking(Long id, Long userId);
    
    /**
     * 确认预订
     *
     * @param id 预订ID
     * @return 是否成功
     */
    boolean confirmBooking(Long id);
    
    /**
     * 获取会议室在指定时间段的预订
     *
     * @param roomId    会议室ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 预订列表
     */
    List<MeetingRoomBookingVO> listRoomBookings(Long roomId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取用户在指定时间段的预订
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 预订列表
     */
    List<MeetingRoomBookingVO> listUserBookings(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询预订
     *
     * @param page   分页参数
     * @param roomId 会议室ID
     * @param userId 用户ID
     * @param status 状态
     * @return 分页结果
     */
    IPage<MeetingRoomBookingVO> pageBookings(Page<MeetingRoomBooking> page, 
                                            Long roomId, Long userId, Integer statusCode);
    
    /**
     * 查询指定日期的所有预订
     *
     * @param date 日期
     * @return 预订列表
     */
    List<MeetingRoomBookingVO> listBookingsByDate(LocalDateTime date);
    
    /**
     * 检查时间冲突
     *
     * @param roomId           会议室ID
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @param currentBookingId 当前预订ID（更新时使用，新建时为null）
     * @return 是否存在冲突
     */
    boolean checkTimeConflict(Long roomId, LocalDateTime startTime, LocalDateTime endTime, Long currentBookingId);
} 