package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.schedule.entity.MeetingRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议室Mapper接口
 */
@Mapper
public interface MeetingRoomMapper extends BaseMapper<MeetingRoom> {
    
    /**
     * 分页查询会议室列表
     *
     * @param page 分页参数
     * @param status 状态码，可为null
     * @return 分页结果
     */
    IPage<MeetingRoom> pageByStatus(Page<MeetingRoom> page, @Param("status") Integer status);
    
    /**
     * 查询指定时间段内可用的会议室
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param capacity 最小容量，可为null
     * @return 可用会议室列表
     */
    List<MeetingRoom> findAvailableRooms(@Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime,
                                        @Param("capacity") Integer capacity);
    
    /**
     * 根据管理员ID查询会议室
     *
     * @param managerId 管理员ID
     * @return 会议室列表
     */
    List<MeetingRoom> findByManagerId(@Param("managerId") Long managerId);
    
    /**
     * 更新会议室状态
     *
     * @param id 会议室ID
     * @param statusCode 状态码
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("statusCode") Integer statusCode);
} 