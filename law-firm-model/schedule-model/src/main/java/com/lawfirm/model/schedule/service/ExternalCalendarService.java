package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.schedule.dto.ExternalCalendarDTO;
import com.lawfirm.model.schedule.entity.ExternalCalendar;
import com.lawfirm.model.schedule.vo.ExternalCalendarVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 外部日历服务接口
 */
public interface ExternalCalendarService {

    /**
     * 创建外部日历
     *
     * @param calendarDTO 外部日历DTO
     * @return 日历ID
     */
    Long createExternalCalendar(ExternalCalendarDTO calendarDTO);

    /**
     * 更新外部日历
     *
     * @param id          日历ID
     * @param calendarDTO 外部日历DTO
     * @return 是否成功
     */
    boolean updateExternalCalendar(Long id, ExternalCalendarDTO calendarDTO);

    /**
     * 获取外部日历详情
     *
     * @param id 日历ID
     * @return 外部日历VO
     */
    ExternalCalendarVO getExternalCalendarDetail(Long id);

    /**
     * 删除外部日历
     *
     * @param id 日历ID
     * @return 是否成功
     */
    boolean deleteExternalCalendar(Long id);

    /**
     * 获取用户的外部日历列表
     *
     * @param userId 用户ID，为null则获取当前用户
     * @return 外部日历VO列表
     */
    List<ExternalCalendarVO> listUserExternalCalendars(Long userId);

    /**
     * 分页查询外部日历
     *
     * @param page         分页参数
     * @param keyword      关键字
     * @param providerType 提供商类型
     * @param status       状态
     * @return 分页结果
     */
    Page<ExternalCalendarVO> page(Page<ExternalCalendar> page, String keyword, Integer providerType, Integer status);

    /**
     * 同步外部日历
     *
     * @param id        日历ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 同步的日程数量
     */
    int syncExternalCalendar(Long id, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取授权URL
     *
     * @param calendarDTO 外部日历DTO
     * @return 授权URL
     */
    String getAuthorizationUrl(ExternalCalendarDTO calendarDTO);

    /**
     * 处理授权回调
     *
     * @param providerType 提供商类型
     * @param code         授权码
     * @param state        状态
     * @return 是否成功
     */
    boolean handleAuthCallback(Integer providerType, String code, String state);

    /**
     * 更新外部日历状态
     *
     * @param id     日历ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateExternalCalendarStatus(Long id, Integer status);
} 