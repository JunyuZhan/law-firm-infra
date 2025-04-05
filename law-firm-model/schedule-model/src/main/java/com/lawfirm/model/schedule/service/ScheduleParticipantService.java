package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.schedule.dto.ScheduleParticipantDTO;
import com.lawfirm.model.schedule.entity.ScheduleParticipant;
import com.lawfirm.model.schedule.vo.ScheduleParticipantVO;

import java.util.List;

/**
 * 日程参与者服务接口
 */
public interface ScheduleParticipantService extends BaseService<ScheduleParticipant> {
    
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
     * 批量添加参与者
     *
     * @param scheduleId 日程ID
     * @param userIds 用户ID列表
     * @return 添加的参与者数量
     */
    int batchAddParticipants(Long scheduleId, List<Long> userIds);
    
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
    
    /**
     * 批量移除参与者
     *
     * @param scheduleId 日程ID
     * @param userIds 用户ID列表
     * @return 移除的参与者数量
     */
    int batchRemoveParticipants(Long scheduleId, List<Long> userIds);
    
    /**
     * 更新参与状态
     *
     * @param scheduleId 日程ID
     * @param userId 用户ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long scheduleId, Long userId, Integer status);
    
    /**
     * 获取参与者详情
     *
     * @param id 参与者ID
     * @return 参与者详情
     */
    ScheduleParticipantVO getDetail(Long id);
    
    /**
     * 分页查询参与者
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param scheduleId 日程ID
     * @param userId 用户ID
     * @param role 角色
     * @param status 状态
     * @return 分页结果
     */
    Page<ScheduleParticipantVO> pageParticipants(Integer pageNum, Integer pageSize, Long scheduleId, Long userId, Integer role, Integer status);
    
    /**
     * 检查用户是否参与日程
     *
     * @param scheduleId 日程ID
     * @param userId 用户ID
     * @return 是否参与
     */
    boolean isParticipated(Long scheduleId, Long userId);
} 