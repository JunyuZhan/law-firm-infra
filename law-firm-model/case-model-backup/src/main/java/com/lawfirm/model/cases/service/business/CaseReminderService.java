package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.dto.business.CaseReminderDTO;
import com.lawfirm.model.cases.enums.reminder.ReminderStatusEnum;
import com.lawfirm.model.cases.enums.reminder.ReminderTypeEnum;
import com.lawfirm.model.cases.vo.business.CaseReminderVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件提醒服务接口
 */
public interface CaseReminderService {

    /**
     * 创建提醒
     *
     * @param reminderDTO 提醒信息
     * @return 提醒ID
     */
    Long createReminder(CaseReminderDTO reminderDTO);

    /**
     * 更新提醒
     *
     * @param reminderDTO 提醒信息
     * @return 是否成功
     */
    Boolean updateReminder(CaseReminderDTO reminderDTO);

    /**
     * 删除提醒
     *
     * @param reminderId 提醒ID
     * @return 是否成功
     */
    Boolean deleteReminder(Long reminderId);

    /**
     * 批量删除提醒
     *
     * @param reminderIds 提醒ID列表
     * @return 是否成功
     */
    Boolean batchDeleteReminders(List<Long> reminderIds);

    /**
     * 更新提醒状态
     *
     * @param reminderId 提醒ID
     * @param status 提醒状态
     * @return 是否成功
     */
    Boolean updateReminderStatus(Long reminderId, ReminderStatusEnum status);

    /**
     * 获取提醒详情
     *
     * @param reminderId 提醒ID
     * @return 提醒详情
     */
    CaseReminderVO getReminderDetail(Long reminderId);

    /**
     * 获取案件的所有提醒
     *
     * @param caseId 案件ID
     * @return 提醒列表
     */
    List<CaseReminderVO> listCaseReminders(Long caseId);

    /**
     * 分页查询提醒
     *
     * @param caseId 案件ID
     * @param type 提醒类型
     * @param status 提醒状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CaseReminderVO> pageReminders(Long caseId, ReminderTypeEnum type, ReminderStatusEnum status,
            LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 获取用户的待处理提醒
     *
     * @param userId 用户ID
     * @return 提醒列表
     */
    List<CaseReminderVO> listPendingReminders(Long userId);

    /**
     * 获取即将到期的提醒
     *
     * @param userId 用户ID
     * @param days 天数
     * @return 提醒列表
     */
    List<CaseReminderVO> listUpcomingReminders(Long userId, Integer days);

    /**
     * 标记提醒为已读
     *
     * @param reminderId 提醒ID
     * @return 是否成功
     */
    Boolean markReminderAsRead(Long reminderId);

    /**
     * 设置提醒重复规则
     *
     * @param reminderId 提醒ID
     * @param repeatType 重复类型
     * @param repeatRule 重复规则
     * @return 是否成功
     */
    Boolean setReminderRepeatRule(Long reminderId, String repeatType, String repeatRule);

    /**
     * 发送提醒通知
     *
     * @param reminderId 提醒ID
     * @param notifyType 通知类型（邮件、短信等）
     * @return 是否成功
     */
    Boolean sendReminderNotification(Long reminderId, String notifyType);
} 