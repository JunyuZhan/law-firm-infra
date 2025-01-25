package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.entity.CaseReminder;
import com.lawfirm.cases.mapper.CaseReminderMapper;
import com.lawfirm.cases.service.CaseReminderService;
import com.lawfirm.cases.enums.ReminderStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件提醒Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CaseReminderServiceImpl extends ServiceImpl<CaseReminderMapper, CaseReminder> implements CaseReminderService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createReminder(CaseReminder reminder) {
        log.info("创建案件提醒: {}", reminder);
        reminder.setStatus(ReminderStatusEnum.PENDING);
        save(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReminder(CaseReminder reminder) {
        log.info("更新案件提醒: {}", reminder);
        updateById(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReminder(Long id) {
        log.info("删除案件提醒: {}", id);
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReminder(Long id, String confirmRemark) {
        log.info("确认案件提醒: {}, 确认备注: {}", id, confirmRemark);
        CaseReminder reminder = getById(id);
        if (reminder != null) {
            reminder.setStatus(ReminderStatusEnum.CONFIRMED);
            reminder.setConfirmTime(LocalDateTime.now());
            reminder.setConfirmRemark(confirmRemark);
            updateById(reminder);
        }
    }

    @Override
    public List<CaseReminder> listByCaseId(Long caseId) {
        return lambdaQuery()
                .eq(CaseReminder::getCaseId, caseId)
                .list();
    }

    @Override
    public List<CaseReminder> listByReceiverId(Long receiverId) {
        return lambdaQuery()
                .eq(CaseReminder::getReceiverId, receiverId)
                .list();
    }

    @Override
    public List<CaseReminder> listByType(Integer type) {
        return lambdaQuery()
                .eq(CaseReminder::getType, type)
                .list();
    }

    @Override
    public List<CaseReminder> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return lambdaQuery()
                .between(CaseReminder::getReminderTime, startTime, endTime)
                .list();
    }

    @Override
    public List<CaseReminder> listPendingReminders() {
        return lambdaQuery()
                .eq(CaseReminder::getStatus, ReminderStatusEnum.PENDING)
                .le(CaseReminder::getReminderTime, LocalDateTime.now())
                .list();
    }
} 