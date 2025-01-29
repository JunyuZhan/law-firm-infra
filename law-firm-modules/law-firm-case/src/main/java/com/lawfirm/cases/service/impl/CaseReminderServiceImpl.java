package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.cases.mapper.CaseReminderMapper;
import com.lawfirm.cases.service.CaseReminderService;
import com.lawfirm.model.cases.entity.CaseReminder;
import com.lawfirm.model.cases.enums.ReminderStatusEnum;
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
public class CaseReminderServiceImpl implements CaseReminderService {

    private final CaseReminderMapper caseReminderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createReminder(CaseReminder reminder) {
        reminder.setStatus(ReminderStatusEnum.PENDING.getCode());
        reminder.setCreateTime(LocalDateTime.now());
        caseReminderMapper.insert(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReminder(CaseReminder reminder) {
        caseReminderMapper.updateById(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReminder(Long id) {
        caseReminderMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReminder(Long id, String confirmRemark) {
        CaseReminder reminder = caseReminderMapper.selectById(id);
        reminder.setStatus(ReminderStatusEnum.CONFIRMED.getCode());
        reminder.setRemark(confirmRemark);
        caseReminderMapper.updateById(reminder);
    }

    @Override
    public List<CaseReminder> listByCaseId(Long caseId) {
        LambdaQueryWrapper<CaseReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseReminder::getCaseId, caseId);
        return caseReminderMapper.selectList(wrapper);
    }

    @Override
    public List<CaseReminder> listByReceiverId(Long receiverId) {
        LambdaQueryWrapper<CaseReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseReminder::getReceiverId, receiverId);
        return caseReminderMapper.selectList(wrapper);
    }

    @Override
    public List<CaseReminder> listByType(Integer type) {
        LambdaQueryWrapper<CaseReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseReminder::getType, type);
        return caseReminderMapper.selectList(wrapper);
    }

    @Override
    public List<CaseReminder> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CaseReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(CaseReminder::getReminderTime, startTime, endTime);
        return caseReminderMapper.selectList(wrapper);
    }

    @Override
    public List<CaseReminder> listPendingReminders() {
        LambdaQueryWrapper<CaseReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseReminder::getStatus, ReminderStatusEnum.PENDING.getCode());
        return caseReminderMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeReminder(Long id, String remark) {
        CaseReminder reminder = caseReminderMapper.selectById(id);
        reminder.setStatus(ReminderStatusEnum.COMPLETED.getCode());
        reminder.setRemark(remark);
        caseReminderMapper.updateById(reminder);
    }

    @Override
    public List<CaseReminder> getCaseReminders(Long caseId) {
        return listByCaseId(caseId);
    }

    @Override
    public List<CaseReminder> getLawyerReminders(String lawyerId) {
        return listByReceiverId(Long.valueOf(lawyerId));
    }

    @Override
    public List<CaseReminder> getPendingReminders() {
        return listPendingReminders();
    }

    @Override
    public List<CaseReminder> getUpcomingReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusDays(7);
        LambdaQueryWrapper<CaseReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseReminder::getStatus, ReminderStatusEnum.PENDING.getCode())
                .between(CaseReminder::getReminderTime, now, future);
        return caseReminderMapper.selectList(wrapper);
    }

    @Override
    public List<CaseReminder> getOverdueReminders() {
        LambdaQueryWrapper<CaseReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseReminder::getStatus, ReminderStatusEnum.PENDING.getCode())
                .lt(CaseReminder::getReminderTime, LocalDateTime.now());
        return caseReminderMapper.selectList(wrapper);
    }
} 