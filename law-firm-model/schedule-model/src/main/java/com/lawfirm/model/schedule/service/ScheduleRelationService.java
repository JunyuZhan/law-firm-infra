package com.lawfirm.model.schedule.service;

import com.lawfirm.model.schedule.dto.ScheduleCaseRelationDTO;
import com.lawfirm.model.schedule.dto.ScheduleTaskRelationDTO;
import com.lawfirm.model.schedule.vo.ScheduleCaseRelationVO;
import com.lawfirm.model.schedule.vo.ScheduleTaskRelationVO;

import java.util.List;

/**
 * 日程关联服务接口
 */
public interface ScheduleRelationService {
    
    /**
     * 关联日程与案件
     *
     * @param scheduleId 日程ID
     * @param caseId 案件ID
     * @param description 关联描述
     * @return 是否成功
     */
    boolean linkCase(Long scheduleId, Long caseId, String description);
    
    /**
     * 关联日程与任务
     *
     * @param scheduleId 日程ID
     * @param taskId 任务ID
     * @param description 关联描述
     * @return 是否成功
     */
    boolean linkTask(Long scheduleId, Long taskId, String description);
    
    /**
     * 解除日程与案件的关联
     *
     * @param scheduleId 日程ID
     * @param caseId 案件ID
     * @return 是否成功
     */
    boolean unlinkCase(Long scheduleId, Long caseId);
    
    /**
     * 解除日程与任务的关联
     *
     * @param scheduleId 日程ID
     * @param taskId 任务ID
     * @return 是否成功
     */
    boolean unlinkTask(Long scheduleId, Long taskId);
    
    /**
     * 获取日程关联的案件列表
     *
     * @param scheduleId 日程ID
     * @return 案件关联VO列表
     */
    List<ScheduleCaseRelationVO> listCaseRelations(Long scheduleId);
    
    /**
     * 获取日程关联的任务列表
     *
     * @param scheduleId 日程ID
     * @return 任务关联VO列表
     */
    List<ScheduleTaskRelationVO> listTaskRelations(Long scheduleId);
    
    /**
     * 获取案件关联的日程列表
     *
     * @param caseId 案件ID
     * @return 案件关联VO列表
     */
    List<ScheduleCaseRelationVO> listSchedulesByCaseId(Long caseId);
    
    /**
     * 获取任务关联的日程列表
     *
     * @param taskId 任务ID
     * @return 任务关联VO列表
     */
    List<ScheduleTaskRelationVO> listSchedulesByTaskId(Long taskId);
    
    /**
     * 获取案件关联的日程关系列表
     *
     * @param caseId 案件ID
     * @return 案件关联VO列表
     */
    List<ScheduleCaseRelationVO> listCaseRelationsByCaseId(Long caseId);
    
    /**
     * 获取任务关联的日程关系列表
     *
     * @param taskId 任务ID
     * @return 任务关联VO列表
     */
    List<ScheduleTaskRelationVO> listTaskRelationsByTaskId(Long taskId);
    
    /**
     * 批量关联案件
     *
     * @param relationDTOs 关联DTO列表
     * @return 是否成功
     */
    boolean batchLinkCases(List<ScheduleCaseRelationDTO> relationDTOs);
    
    /**
     * 批量关联任务
     *
     * @param relationDTOs 关联DTO列表
     * @return 是否成功
     */
    boolean batchLinkTasks(List<ScheduleTaskRelationDTO> relationDTOs);
} 