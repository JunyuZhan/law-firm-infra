package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.schedule.dto.ScheduleParticipantDTO;
import com.lawfirm.model.schedule.entity.ScheduleParticipant;
import com.lawfirm.model.schedule.vo.ScheduleParticipantVO;

import java.util.List;

/**
 * 日程参与者服务接口
 */
public interface ScheduleParticipantService extends IService<ScheduleParticipant> {
    
    /**
     * 添加参与者
     *
     * @param scheduleId 日程ID
     * @param participantDTO 参与者DTO
     * @return 是否成功
     */
    boolean addParticipant(Long scheduleId, ScheduleParticipantDTO participantDTO);
    
    /**
     * 批量添加参与者
     *
     * @param scheduleId 日程ID
     * @param participantDTOs 参与者DTO列表
     * @return 是否成功
     */
    boolean addParticipants(Long scheduleId, List<ScheduleParticipantDTO> participantDTOs);
    
    /**
     * 更新参与者
     *
     * @param id 参与者ID
     * @param participantDTO 参与者DTO
     * @return 是否成功
     */
    boolean updateParticipant(Long id, ScheduleParticipantDTO participantDTO);
    
    /**
     * 删除参与者
     *
     * @param id 参与者ID
     * @return 是否成功
     */
    boolean removeParticipant(Long id);
    
    /**
     * 根据日程ID删除所有参与者
     *
     * @param scheduleId 日程ID
     * @return 是否成功
     */
    boolean removeByScheduleId(Long scheduleId);
    
    /**
     * 获取日程的所有参与者
     *
     * @param scheduleId 日程ID
     * @return 参与者列表
     */
    List<ScheduleParticipantVO> listByScheduleId(Long scheduleId);
    
    /**
     * 获取参与者信息
     *
     * @param id 参与者ID
     * @return 参与者VO
     */
    ScheduleParticipantVO getParticipantDetail(Long id);
    
    /**
     * 查询用户参与的所有日程ID
     *
     * @param userId 用户ID
     * @return 日程ID列表
     */
    List<Long> getScheduleIdsByUserId(Long userId);
    
    /**
     * 响应日程邀请
     *
     * @param id 参与者ID
     * @param responseStatusCode 响应状态码
     * @param comments 回复意见
     * @return 是否成功
     */
    boolean respondToInvitation(Long id, Integer responseStatusCode, String comments);
} 