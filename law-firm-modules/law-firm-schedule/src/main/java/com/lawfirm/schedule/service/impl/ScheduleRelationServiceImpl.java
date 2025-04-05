package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.schedule.dto.ScheduleCaseRelationDTO;
import com.lawfirm.model.schedule.dto.ScheduleTaskRelationDTO;
import com.lawfirm.model.schedule.entity.ScheduleCaseRelation;
import com.lawfirm.model.schedule.entity.ScheduleTaskRelation;
import com.lawfirm.model.schedule.mapper.ScheduleCaseRelationMapper;
import com.lawfirm.model.schedule.mapper.ScheduleTaskRelationMapper;
import com.lawfirm.model.schedule.service.ScheduleRelationService;
import com.lawfirm.model.schedule.service.ScheduleEventService;
import com.lawfirm.model.schedule.service.ScheduleService;
import com.lawfirm.model.schedule.vo.ScheduleCaseRelationVO;
import com.lawfirm.model.schedule.vo.ScheduleTaskRelationVO;
import com.lawfirm.schedule.converter.ScheduleCaseRelationConvert;
import com.lawfirm.schedule.converter.ScheduleTaskRelationConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日程关联服务实现类
 */
@Service("scheduleRelationService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleRelationServiceImpl implements ScheduleRelationService {

    private final ScheduleCaseRelationMapper caseRelationMapper;
    private final ScheduleTaskRelationMapper taskRelationMapper;
    private final ScheduleService scheduleService;
    private final ScheduleEventService eventService;
    private final ScheduleCaseRelationConvert caseRelationConvert;
    private final ScheduleTaskRelationConvert taskRelationConvert;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkCase(Long scheduleId, Long caseId, String description) {
        log.info("关联日程与案件，日程ID：{}，案件ID：{}", scheduleId, caseId);
        
        // 检查是否已关联
        LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleCaseRelation::getScheduleId, scheduleId)
                .eq(ScheduleCaseRelation::getCaseId, caseId);
        
        if (caseRelationMapper.selectCount(queryWrapper) > 0) {
            log.info("日程与案件已关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
            return true;
        }
        
        // 创建关联
        ScheduleCaseRelation relation = new ScheduleCaseRelation();
        relation.setScheduleId(scheduleId);
        relation.setCaseId(caseId);
        relation.setDescription(description);
        relation.setCreateTime(LocalDateTime.now());
        
        return caseRelationMapper.insert(relation) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkTask(Long scheduleId, Long taskId, String description) {
        log.info("关联日程与任务，日程ID：{}，任务ID：{}", scheduleId, taskId);
        
        // 检查是否已关联
        LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleTaskRelation::getScheduleId, scheduleId)
                .eq(ScheduleTaskRelation::getTaskId, taskId);
        
        if (taskRelationMapper.selectCount(queryWrapper) > 0) {
            log.info("日程与任务已关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
            return true;
        }
        
        // 创建关联
        ScheduleTaskRelation relation = new ScheduleTaskRelation();
        relation.setScheduleId(scheduleId);
        relation.setTaskId(taskId);
        relation.setDescription(description);
        relation.setCreateTime(LocalDateTime.now());
        
        return taskRelationMapper.insert(relation) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkCase(Long scheduleId, Long caseId) {
        log.info("解除日程与案件的关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
        
        LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleCaseRelation::getScheduleId, scheduleId)
                .eq(ScheduleCaseRelation::getCaseId, caseId);
        
        return caseRelationMapper.delete(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkTask(Long scheduleId, Long taskId) {
        log.info("解除日程与任务的关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
        
        LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleTaskRelation::getScheduleId, scheduleId)
                .eq(ScheduleTaskRelation::getTaskId, taskId);
        
        return taskRelationMapper.delete(queryWrapper) > 0;
    }

    @Override
    public List<ScheduleCaseRelationVO> listCaseRelations(Long scheduleId) {
        log.info("获取日程关联的案件列表，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleCaseRelation::getScheduleId, scheduleId);
        
        List<ScheduleCaseRelation> relations = caseRelationMapper.selectList(queryWrapper);
        return relations.stream()
                .map(caseRelationConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleTaskRelationVO> listTaskRelations(Long scheduleId) {
        log.info("获取日程关联的任务列表，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleTaskRelation::getScheduleId, scheduleId);
        
        List<ScheduleTaskRelation> relations = taskRelationMapper.selectList(queryWrapper);
        return relations.stream()
                .map(taskRelationConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleCaseRelationVO> listSchedulesByCaseId(Long caseId) {
        log.info("获取案件关联的日程列表，案件ID：{}", caseId);
        
        LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleCaseRelation::getCaseId, caseId);
        
        List<ScheduleCaseRelation> relations = caseRelationMapper.selectList(queryWrapper);
        return relations.stream()
                .map(caseRelationConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleTaskRelationVO> listSchedulesByTaskId(Long taskId) {
        log.info("获取任务关联的日程列表，任务ID：{}", taskId);
        
        LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleTaskRelation::getTaskId, taskId);
        
        List<ScheduleTaskRelation> relations = taskRelationMapper.selectList(queryWrapper);
        return relations.stream()
                .map(taskRelationConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchLinkCases(List<ScheduleCaseRelationDTO> relationDTOs) {
        log.info("批量关联案件，关联数量：{}", relationDTOs.size());
        
        List<ScheduleCaseRelation> relations = relationDTOs.stream()
                .map(caseRelationConvert::toEntity)
                .peek(relation -> relation.setCreateTime(LocalDateTime.now()))
                .collect(Collectors.toList());
        
        if (relations.isEmpty()) {
            return true;
        }
        
        int insertCount = 0;
        for (ScheduleCaseRelation relation : relations) {
            // 检查是否已存在关联
            LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ScheduleCaseRelation::getScheduleId, relation.getScheduleId())
                    .eq(ScheduleCaseRelation::getCaseId, relation.getCaseId());
            
            if (caseRelationMapper.selectCount(queryWrapper) == 0) {
                insertCount += caseRelationMapper.insert(relation);
            }
        }
        
        return insertCount > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchLinkTasks(List<ScheduleTaskRelationDTO> relationDTOs) {
        log.info("批量关联任务，关联数量：{}", relationDTOs.size());
        
        List<ScheduleTaskRelation> relations = relationDTOs.stream()
                .map(taskRelationConvert::toEntity)
                .peek(relation -> relation.setCreateTime(LocalDateTime.now()))
                .collect(Collectors.toList());
        
        if (relations.isEmpty()) {
            return true;
        }
        
        int insertCount = 0;
        for (ScheduleTaskRelation relation : relations) {
            // 检查是否已存在关联
            LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ScheduleTaskRelation::getScheduleId, relation.getScheduleId())
                    .eq(ScheduleTaskRelation::getTaskId, relation.getTaskId());
            
            if (taskRelationMapper.selectCount(queryWrapper) == 0) {
                insertCount += taskRelationMapper.insert(relation);
            }
        }
        
        return insertCount > 0;
    }
} 