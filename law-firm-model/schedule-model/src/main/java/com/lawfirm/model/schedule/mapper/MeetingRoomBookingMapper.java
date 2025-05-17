package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.MeetingRoomBooking;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议室预订Mapper接口
 */
@Mapper
public interface MeetingRoomBookingMapper extends BaseMapper<MeetingRoomBooking> {
    
    /**
     * 查询用户的会议室预订
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 预订列表
     */
    @Select("SELECT * FROM schedule_meeting_room_booking "
            + "WHERE user_id = #{userId} "
            + "AND ((start_time BETWEEN #{startTime} AND #{endTime}) "
            + "OR (end_time BETWEEN #{startTime} AND #{endTime}) "
            + "OR (start_time <= #{startTime} AND end_time >= #{endTime})) "
            + "ORDER BY start_time")
    List<MeetingRoomBooking> findUserBookings(@Param("userId") Long userId, 
                                             @Param("startTime") LocalDateTime startTime, 
                                             @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询会议室的预订
     *
     * @param roomId 会议室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 预订列表
     */
    @Select("SELECT * FROM schedule_meeting_room_booking "
            + "WHERE meeting_room_id = #{roomId} "
            + "AND ((start_time BETWEEN #{startTime} AND #{endTime}) "
            + "OR (end_time BETWEEN #{startTime} AND #{endTime}) "
            + "OR (start_time <= #{startTime} AND end_time >= #{endTime})) "
            + "ORDER BY start_time")
    List<MeetingRoomBooking> findRoomBookings(@Param("roomId") Long roomId, 
                                             @Param("startTime") LocalDateTime startTime, 
                                             @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询指定日期的所有预订
     *
     * @param date 日期
     * @return 预订列表
     */
    @Select("SELECT * FROM schedule_meeting_room_booking "
            + "WHERE DATE(start_time) = DATE(#{date}) "
            + "ORDER BY start_time")
    List<MeetingRoomBooking> findBookingsByDate(@Param("date") LocalDateTime date);
    
    /**
     * 检查时间冲突
     *
     * @param roomId 会议室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeBookingId 排除的预订ID
     * @return 冲突的预订列表
     */
    @Select({"<script>",
             "SELECT * FROM schedule_meeting_room_booking ",
             "WHERE meeting_room_id = #{roomId} ",
             "AND booking_status != 3 ", // 不包括已取消的预订
             "AND (",
             "  (start_time &lt;= #{endTime} AND end_time &gt;= #{startTime})",
             ") ",
             "<if test='excludeBookingId != null'>",
             "AND id != #{excludeBookingId} ",
             "</if>",
             "ORDER BY start_time",
             "</script>"})
    List<MeetingRoomBooking> findConflicts(@Param("roomId") Long roomId, 
                                          @Param("startTime") LocalDateTime startTime, 
                                          @Param("endTime") LocalDateTime endTime,
                                          @Param("excludeBookingId") Long excludeBookingId);
} 