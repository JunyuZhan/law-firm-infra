package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseEventDTO;
import com.lawfirm.model.cases.vo.business.CaseEventVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件事件服务接口
 */
public interface CaseEventService {

    /**
     * 创建事件
     *
     * @param eventDTO 事件信息
     * @return 事件ID
     */
    Long createEvent(CaseEventDTO eventDTO);

    /**
     * 批量创建事件
     *
     * @param eventDTOs 事件信息列表
     * @return 是否成功
     */
    boolean batchCreateEvents(List<CaseEventDTO> eventDTOs);

    /**
     * 更新事件
     *
     * @param eventDTO 事件信息
     * @return 是否成功
     */
    boolean updateEvent(CaseEventDTO eventDTO);

    /**
     * 删除事件
     *
     * @param eventId 事件ID
     * @return 是否成功
     */
    boolean deleteEvent(Long eventId);

    /**
     * 批量删除事件
     *
     * @param eventIds 事件ID列表
     * @return 是否成功
     */
    boolean batchDeleteEvents(List<Long> eventIds);

    /**
     * 获取事件详情
     *
     * @param eventId 事件ID
     * @return 事件详情
     */
    CaseEventVO getEventDetail(Long eventId);

    /**
     * 获取案件的所有事件
     *
     * @param caseId 案件ID
     * @return 事件列表
     */
    List<CaseEventVO> listCaseEvents(Long caseId);

    /**
     * 分页查询事件
     *
     * @param caseId 案件ID
     * @param eventType 事件类型
     * @param eventStatus 事件状态
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseEventVO> pageEvents(Long caseId, Integer eventType, Integer eventStatus, Integer pageNum, Integer pageSize);

    /**
     * 开始事件
     *
     * @param eventId 事件ID
     * @return 是否成功
     */
    boolean startEvent(Long eventId);

    /**
     * 完成事件
     *
     * @param eventId 事件ID
     * @param completionNote 完成说明
     * @return 是否成功
     */
    boolean completeEvent(Long eventId, String completionNote);

    /**
     * 取消事件
     *
     * @param eventId 事件ID
     * @param reason 取消原因
     * @return 是否成功
     */
    boolean cancelEvent(Long eventId, String reason);

    /**
     * 添加事件参与人
     *
     * @param eventId 事件ID
     * @param participantId 参与人ID
     * @return 是否成功
     */
    boolean addParticipant(Long eventId, Long participantId);

    /**
     * 移除事件参与人
     *
     * @param eventId 事件ID
     * @param participantId 参与人ID
     * @return 是否成功
     */
    boolean removeParticipant(Long eventId, Long participantId);

    /**
     * 添加事件附件
     *
     * @param eventId 事件ID
     * @param fileId 文件ID
     * @return 是否成功
     */
    boolean addAttachment(Long eventId, Long fileId);

    /**
     * 移除事件附件
     *
     * @param eventId 事件ID
     * @param fileId 文件ID
     * @return 是否成功
     */
    boolean removeAttachment(Long eventId, Long fileId);

    /**
     * 获取用户的事件列表
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 事件列表
     */
    List<CaseEventVO> listUserEvents(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取用户的待办事件
     *
     * @param userId 用户ID
     * @return 事件列表
     */
    List<CaseEventVO> listUserTodoEvents(Long userId);

    /**
     * 获取案件时间线
     *
     * @param caseId 案件ID
     * @return 事件列表
     */
    List<CaseEventVO> getCaseTimeline(Long caseId);

    /**
     * 检查事件是否存在
     *
     * @param eventId 事件ID
     * @return 是否存在
     */
    boolean checkEventExists(Long eventId);

    /**
     * 统计案件事件数量
     *
     * @param caseId 案件ID
     * @param eventType 事件类型
     * @param eventStatus 事件状态
     * @return 数量
     */
    int countEvents(Long caseId, Integer eventType, Integer eventStatus);
} 