package com.lawfirm.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.schedule.model.dto.ScheduleDTO;
import com.lawfirm.schedule.model.query.ScheduleQuery;
import com.lawfirm.schedule.model.vo.ScheduleVO;
import com.lawfirm.schedule.entity.Schedule;

import java.util.List;
import java.util.Map;

/**
 * 日程管理服务接口
 */
public interface ScheduleService extends IService<Schedule> {

    /**
     * 创建日程
     */
    Schedule create(Schedule schedule);

    /**
     * 更新日程
     */
    Schedule update(Schedule schedule);

    /**
     * 删除日程
     */
    void delete(Long id);

    /**
     * 获取日程详情
     */
    ScheduleVO get(Long id);

    /**
     * 获取我的日程
     */
    List<ScheduleVO> getMySchedules();

    /**
     * 获取日程提醒
     */
    List<ScheduleVO> getReminders();

    /**
     * 根据条件查询日程列表
     */
    List<ScheduleVO> list(ScheduleQuery query);

    /**
     * 分页查询日程
     */
    PageResult<ScheduleVO> page(ScheduleQuery query);

    /**
     * 设置日程提醒
     */
    void setReminder(Long id, Integer reminderTime);

    /**
     * 获取日程统计
     */
    Map<String, Object> getStats();

    /**
     * 导出日程
     */
    void export(ScheduleQuery query);
} 