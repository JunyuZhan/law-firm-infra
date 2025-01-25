package com.lawfirm.cases.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.cases.entity.CaseReminder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件提醒Service接口
 */
public interface CaseReminderService extends IService<CaseReminder> {

    /**
     * 创建提醒
     */
    void createReminder(CaseReminder reminder);

    /**
     * 更新提醒
     */
    void updateReminder(CaseReminder reminder);

    /**
     * 删除提醒
     */
    void deleteReminder(Long id);

    /**
     * 确认提醒
     */
    void confirmReminder(Long id, String confirmRemark);

    /**
     * 根据案件ID查询提醒列表
     */
    List<CaseReminder> listByCaseId(Long caseId);

    /**
     * 根据接收人ID查询提醒列表
     */
    List<CaseReminder> listByReceiverId(Long receiverId);

    /**
     * 根据提醒类型查询提醒列表
     */
    List<CaseReminder> listByType(Integer type);

    /**
     * 查询指定时间范围内的提醒列表
     */
    List<CaseReminder> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询待处理的提醒列表
     */
    List<CaseReminder> listPendingReminders();
} 