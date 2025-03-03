package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseReminderDTO;
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
     * 批量创建提醒
     *
     * @param reminderDTOs 提醒信息列表
     * @return 是否成功
     */
    boolean batchCreateReminders(List<CaseReminderDTO> reminderDTOs);

    /**
     * 更新提醒
     *
     * @param reminderDTO 提醒信息
     * @return 是否成功
     */
    boolean updateReminder(CaseReminderDTO reminderDTO);

    /**
     * 删除提醒
     *
     * @param reminderId 提醒ID
     * @return 是否成功
     */
    boolean deleteReminder(Long reminderId);

    /**
     * 批量删除提醒
     *
     * @param reminderIds 提醒ID列表
     * @return 是否成功
     */
    boolean batchDeleteReminders(List<Long> reminderIds);

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
     * @param reminderType 提醒类型
     * @param reminderStatus 提醒状态
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseReminderVO> pageReminders(Long caseId, Integer reminderType, Integer reminderStatus, Integer pageNum, Integer pageSize);

    /**
     * 启用提醒
     *
     * @param reminderId 提醒ID
     * @return 是否成功
     */
    boolean enableReminder(Long reminderId);

    /**
     * 禁用提醒
     *
     * @param reminderId 提醒ID
     * @return 是否成功
     */
    boolean disableReminder(Long reminderId);

    /**
     * 确认提醒
     *
     * @param reminderId 提醒ID
     * @param confirmNote 确认说明
     * @return 是否成功
     */
    boolean confirmReminder(Long reminderId, String confirmNote);

    /**
     * 设置提醒时间
     *
     * @param reminderId 提醒ID
     * @param reminderTime 提醒时间
     * @return 是否成功
     */
    boolean setReminderTime(Long reminderId, LocalDateTime reminderTime);

    /**
     * 设置重复规则
     *
     * @param reminderId 提醒ID
     * @param repeatType 重复类型
     * @param repeatInterval 重复间隔
     * @param endTime 结束时间
     * @return 是否成功
     */
    boolean setRepeatRule(Long reminderId, Integer repeatType, Integer repeatInterval, LocalDateTime endTime);

    /**
     * 添加提醒接收人
     *
     * @param reminderId 提醒ID
     * @param receiverId 接收人ID
     * @return 是否成功
     */
    boolean addReceiver(Long reminderId, Long receiverId);

    /**
     * 移除提醒接收人
     *
     * @param reminderId 提醒ID
     * @param receiverId 接收人ID
     * @return 是否成功
     */
    boolean removeReceiver(Long reminderId, Long receiverId);

    /**
     * 获取用户的所有提醒
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 提醒列表
     */
    List<CaseReminderVO> listUserReminders(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取待处理的提醒
     *
     * @param userId 用户ID
     * @return 提醒列表
     */
    List<CaseReminderVO> listPendingReminders(Long userId);

    /**
     * 检查提醒是否存在
     *
     * @param reminderId 提醒ID
     * @return 是否存在
     */
    boolean checkReminderExists(Long reminderId);

    /**
     * 统计案件提醒数量
     *
     * @param caseId 案件ID
     * @param reminderType 提醒类型
     * @param reminderStatus 提醒状态
     * @return 数量
     */
    int countReminders(Long caseId, Integer reminderType, Integer reminderStatus);
} 