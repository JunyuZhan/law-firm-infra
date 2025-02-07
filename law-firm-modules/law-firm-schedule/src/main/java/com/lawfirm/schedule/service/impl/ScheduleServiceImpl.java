package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.schedule.entity.Schedule;
import com.lawfirm.schedule.mapper.ScheduleMapper;
import com.lawfirm.schedule.model.dto.ScheduleDTO;
import com.lawfirm.schedule.model.query.ScheduleQuery;
import com.lawfirm.schedule.model.vo.ScheduleVO;
import com.lawfirm.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日程管理服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Schedule create(Schedule schedule) {
        // 设置创建人信息
        schedule.setCreatorId(SecurityUtils.getUserId());
        schedule.setCreatorName(SecurityUtils.getUsername());
        // 设置初始状态
        schedule.setStatus(0);
        save(schedule);
        return schedule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Schedule update(Schedule schedule) {
        updateById(schedule);
        return schedule;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public ScheduleVO get(Long id) {
        Schedule schedule = getById(id);
        return toVO(schedule);
    }

    @Override
    public List<ScheduleVO> getMySchedules() {
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Schedule::getCreatorId, SecurityUtils.getUserId())
                .ge(Schedule::getEndTime, LocalDateTime.now())
                .orderByAsc(Schedule::getStartTime);
        return toVOList(list(wrapper));
    }

    @Override
    public List<ScheduleVO> getReminders() {
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Schedule::getCreatorId, SecurityUtils.getUserId())
                .eq(Schedule::getStatus, "PENDING")
                .isNotNull(Schedule::getReminderTime)
                .ge(Schedule::getStartTime, LocalDateTime.now())
                .orderByAsc(Schedule::getStartTime);
        return toVOList(list(wrapper));
    }

    @Override
    public List<ScheduleVO> list(ScheduleQuery query) {
        LambdaQueryWrapper<Schedule> wrapper = buildQueryWrapper(query);
        return toVOList(list(wrapper));
    }

    @Override
    public PageResult<ScheduleVO> page(ScheduleQuery query) {
        LambdaQueryWrapper<Schedule> wrapper = buildQueryWrapper(query);
        Page<Schedule> page = new Page<>(query.getPageNum(), query.getPageSize());
        page(page, wrapper);
        return PageResult.of(toVOList(page.getRecords()), page.getTotal());
    }

    @Override
    @Transactional
    public void setReminder(Long id, Integer reminderTime) {
        Schedule schedule = getById(id);
        schedule.setReminderTime(reminderTime);
        updateById(schedule);
    }

    @Override
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        Long userId = SecurityUtils.getUserId();

        // 待开始的日程数
        LambdaQueryWrapper<Schedule> pendingWrapper = new LambdaQueryWrapper<>();
        pendingWrapper.eq(Schedule::getCreatorId, userId)
                .eq(Schedule::getStatus, "PENDING");
        stats.put("pendingCount", count(pendingWrapper));

        // 进行中的日程数
        LambdaQueryWrapper<Schedule> inProgressWrapper = new LambdaQueryWrapper<>();
        inProgressWrapper.eq(Schedule::getCreatorId, userId)
                .eq(Schedule::getStatus, "IN_PROGRESS");
        stats.put("inProgressCount", count(inProgressWrapper));

        // 已完成的日程数
        LambdaQueryWrapper<Schedule> completedWrapper = new LambdaQueryWrapper<>();
        completedWrapper.eq(Schedule::getCreatorId, userId)
                .eq(Schedule::getStatus, "COMPLETED");
        stats.put("completedCount", count(completedWrapper));

        return stats;
    }

    @Override
    public void export(ScheduleQuery query) {
        // TODO: 实现导出功能
    }

    private LambdaQueryWrapper<Schedule> buildQueryWrapper(ScheduleQuery query) {
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getCreatorId() != null, Schedule::getCreatorId, query.getCreatorId())
                .eq(query.getStatus() != null, Schedule::getStatus, query.getStatus())
                .eq(query.getDeptId() != null, Schedule::getDeptId, query.getDeptId())
                .like(query.getTitle() != null, Schedule::getTitle, query.getTitle())
                .eq(query.getType() != null, Schedule::getType, query.getType())
                .ge(query.getStartTimeBegin() != null, Schedule::getStartTime, query.getStartTimeBegin())
                .le(query.getStartTimeEnd() != null, Schedule::getStartTime, query.getStartTimeEnd())
                .orderByDesc(Schedule::getCreateTime);
        return wrapper;
    }

    private ScheduleVO toVO(Schedule schedule) {
        if (schedule == null) {
            return null;
        }
        ScheduleVO vo = new ScheduleVO();
        vo.setId(schedule.getId());
        vo.setTitle(schedule.getTitle());
        vo.setContent(schedule.getContent());
        vo.setType(schedule.getType());
        vo.setStartTime(schedule.getStartTime());
        vo.setEndTime(schedule.getEndTime());
        vo.setLocation(schedule.getLocation());
        vo.setParticipants(schedule.getParticipants());
        vo.setReminderTime(schedule.getReminderTime());
        vo.setCreatorId(schedule.getCreatorId());
        vo.setCreatorName(schedule.getCreatorName());
        vo.setDeptId(schedule.getDeptId());
        vo.setDeptName(schedule.getDeptName());
        vo.setStatus(schedule.getStatus());
        vo.setRemark(schedule.getRemark());
        vo.setParticipantIds(schedule.getParticipantIds());
        vo.setCreateTime(schedule.getCreateTime());
        vo.setUpdateTime(schedule.getUpdateTime());
        return vo;
    }

    private List<ScheduleVO> toVOList(List<Schedule> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::toVO).toList();
    }
} 