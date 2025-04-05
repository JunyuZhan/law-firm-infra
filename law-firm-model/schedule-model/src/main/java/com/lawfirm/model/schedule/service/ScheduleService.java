package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.vo.ScheduleVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程服务接口
 */
public interface ScheduleService extends BaseService<Schedule> {
    
    /**
     * 创建日程
     *
     * @param scheduleDTO 日程DTO
     * @return 创建的日程ID
     */
    Long createSchedule(ScheduleDTO scheduleDTO);
    
    /**
     * 更新日程
     *
     * @param id 日程ID
     * @param scheduleDTO 日程DTO
     * @return 是否成功
     */
    boolean updateSchedule(Long id, ScheduleDTO scheduleDTO);
    
    /**
     * 删除日程
     *
     * @param id 日程ID
     * @return 是否成功
     */
    boolean deleteSchedule(Long id);
    
    /**
     * 获取日程详情
     *
     * @param id 日程ID
     * @return 日程VO
     */
    ScheduleVO getScheduleDetail(Long id);
    
    /**
     * 分页查询用户的日程
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param status 状态，可为null
     * @return 分页结果
     */
    Page<ScheduleVO> pageByUser(Page<Schedule> page, Long userId, Integer status);
    
    /**
     * 查询指定日期的日程
     *
     * @param userId 用户ID
     * @param date 日期
     * @return 日程列表
     */
    List<ScheduleVO> listByDate(Long userId, LocalDate date);
    
    /**
     * 查询指定时间范围的日程
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日程列表
     */
    List<ScheduleVO> listByTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询与案件关联的日程
     *
     * @param caseId 案件ID
     * @return 日程列表
     */
    List<ScheduleVO> listByCaseId(Long caseId);
    
    /**
     * 查询与任务关联的日程
     *
     * @param taskId 任务ID
     * @return 日程列表
     */
    List<ScheduleVO> listByTaskId(Long taskId);
    
    /**
     * 检查日程时间冲突
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeScheduleId 排除的日程ID，用于更新操作
     * @return 冲突的日程列表
     */
    List<ScheduleVO> checkConflicts(Long userId, LocalDateTime startTime, LocalDateTime endTime, Long excludeScheduleId);
    
    /**
     * 将日程与案件关联
     *
     * @param scheduleId 日程ID
     * @param caseId 案件ID
     * @param description 关联说明
     * @return 是否成功
     */
    boolean linkCase(Long scheduleId, Long caseId, String description);
    
    /**
     * 将日程与任务关联
     *
     * @param scheduleId 日程ID
     * @param taskId 任务ID
     * @param description 关联说明
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
     * 更新日程状态
     *
     * @param id 日程ID
     * @param statusCode 状态码
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer statusCode);
} 