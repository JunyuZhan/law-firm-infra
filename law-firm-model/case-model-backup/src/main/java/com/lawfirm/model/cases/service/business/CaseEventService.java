package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.enums.event.EventStatusEnum;
import com.lawfirm.model.cases.enums.event.EventTypeEnum;
import com.lawfirm.model.cases.vo.business.CaseEventVO;
import com.lawfirm.model.cases.vo.business.CaseTimelineVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件事件服务接口
 */
public interface CaseEventService {

    /**
     * 创建事件
     *
     * @param caseId 案件ID
     * @param type 事件类型
     * @param title 事件标题
     * @param content 事件内容
     * @param eventTime 事件时间
     * @param participants 参与人ID列表
     * @return 事件ID
     */
    Long createEvent(Long caseId, EventTypeEnum type, String title, String content, 
            LocalDateTime eventTime, List<Long> participants);

    /**
     * 更新事件
     *
     * @param eventId 事件ID
     * @param type 事件类型
     * @param title 事件标题
     * @param content 事件内容
     * @param eventTime 事件时间
     * @param participants 参与人ID列表
     * @return 是否成功
     */
    Boolean updateEvent(Long eventId, EventTypeEnum type, String title, String content,
            LocalDateTime eventTime, List<Long> participants);

    /**
     * 删除事件
     *
     * @param eventId 事件ID
     * @return 是否成功
     */
    Boolean deleteEvent(Long eventId);

    /**
     * 更新事件状态
     *
     * @param eventId 事件ID
     * @param status 事件状态
     * @param comment 状态变更说明
     * @return 是否成功
     */
    Boolean updateEventStatus(Long eventId, EventStatusEnum status, String comment);

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
     * @param type 事件类型
     * @param status 事件状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CaseEventVO> pageEvents(Long caseId, EventTypeEnum type, EventStatusEnum status,
            LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 获取案件时间线
     *
     * @param caseId 案件ID
     * @return 时间线数据
     */
    CaseTimelineVO getCaseTimeline(Long caseId);

    /**
     * 添加事件附件
     *
     * @param eventId 事件ID
     * @param fileIds 文件ID列表
     * @return 是否成功
     */
    Boolean addEventAttachments(Long eventId, List<Long> fileIds);

    /**
     * 移除事件附件
     *
     * @param eventId 事件ID
     * @param fileIds 文件ID列表
     * @return 是否成功
     */
    Boolean removeEventAttachments(Long eventId, List<Long> fileIds);

    /**
     * 添加事件评论
     *
     * @param eventId 事件ID
     * @param comment 评论内容
     * @return 是否成功
     */
    Boolean addEventComment(Long eventId, String comment);

    /**
     * 获取用户相关的待处理事件
     *
     * @param userId 用户ID
     * @return 事件列表
     */
    List<CaseEventVO> listUserPendingEvents(Long userId);
} 