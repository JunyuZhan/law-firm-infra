package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.schedule.entity.MeetingRoom;
import com.lawfirm.model.schedule.vo.MeetingRoomVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议室服务接口
 */
public interface MeetingRoomService extends BaseService<MeetingRoom> {
    
    /**
     * 创建会议室
     *
     * @param meetingRoom 会议室实体
     * @return 创建的会议室ID
     */
    Long createMeetingRoom(MeetingRoom meetingRoom);
    
    /**
     * 更新会议室
     *
     * @param id 会议室ID
     * @param meetingRoom 会议室实体
     * @return 是否成功
     */
    boolean updateMeetingRoom(Long id, MeetingRoom meetingRoom);
    
    /**
     * 删除会议室
     *
     * @param id 会议室ID
     * @return 是否成功
     */
    boolean deleteMeetingRoom(Long id);
    
    /**
     * 获取会议室详情
     *
     * @param id 会议室ID
     * @return 会议室VO
     */
    MeetingRoomVO getMeetingRoomDetail(Long id);
    
    /**
     * 分页查询会议室
     *
     * @param page 分页参数
     * @param statusCode 状态码，可为null
     * @return 分页结果
     */
    IPage<MeetingRoomVO> pageMeetingRooms(Page<MeetingRoom> page, Integer statusCode);
    
    /**
     * 查询指定时间段内可用的会议室
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param capacity 最小容量，可为null
     * @return 可用会议室列表
     */
    List<MeetingRoomVO> listAvailableRooms(LocalDateTime startTime, LocalDateTime endTime, Integer capacity);
    
    /**
     * 查询由指定用户管理的会议室
     *
     * @param managerId 管理员ID
     * @return 会议室列表
     */
    List<MeetingRoomVO> listByManagerId(Long managerId);
    
    /**
     * 更新会议室状态
     *
     * @param id 会议室ID
     * @param statusCode 状态码
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer statusCode);
    
    /**
     * 检查会议室在指定时间段是否可用
     *
     * @param id 会议室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否可用
     */
    boolean checkAvailability(Long id, LocalDateTime startTime, LocalDateTime endTime);
} 