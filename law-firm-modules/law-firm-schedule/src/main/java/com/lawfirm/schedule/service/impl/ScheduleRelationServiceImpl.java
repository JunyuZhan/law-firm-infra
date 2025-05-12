package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
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
import com.lawfirm.schedule.integration.CaseIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 日程关联服务实现类
 */
@Service("scheduleRelationService")
@Slf4j
public class ScheduleRelationServiceImpl extends BaseServiceImpl<ScheduleCaseRelationMapper, ScheduleCaseRelation> implements ScheduleRelationService {

    private final ScheduleCaseRelationMapper caseRelationMapper;
    private final ScheduleTaskRelationMapper taskRelationMapper;
    private final ScheduleEventService eventService;
    private final ScheduleCaseRelationConvert caseRelationConvert;
    private final ScheduleTaskRelationConvert taskRelationConvert;
    private final CaseIntegration caseIntegration;
    
    @Autowired
    private ScheduleService scheduleService;
    
    public ScheduleRelationServiceImpl(
            ScheduleCaseRelationMapper caseRelationMapper,
            ScheduleTaskRelationMapper taskRelationMapper,
            ScheduleEventService eventService,
            ScheduleCaseRelationConvert caseRelationConvert,
            ScheduleTaskRelationConvert taskRelationConvert,
            CaseIntegration caseIntegration) {
        this.caseRelationMapper = caseRelationMapper;
        this.taskRelationMapper = taskRelationMapper;
        this.eventService = eventService;
        this.caseRelationConvert = caseRelationConvert;
        this.taskRelationConvert = taskRelationConvert;
        this.caseIntegration = caseIntegration;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkCase(Long scheduleId, Long caseId, String description) {
        log.info("关联日程与案件，日程ID：{}，案件ID：{}", scheduleId, caseId);
        
        // 验证案件是否存在
        if (!caseIntegration.caseExists(caseId)) {
            log.error("关联日程与案件失败，案件不存在，案件ID：{}", caseId);
            throw new IllegalArgumentException("案件不存在");
        }
        
        LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleCaseRelation::getScheduleId, scheduleId)
                .eq(ScheduleCaseRelation::getCaseId, caseId);
        
        if (caseRelationMapper.selectCount(queryWrapper) > 0) {
            log.info("日程与案件已关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
            return true;
        }
        
        ScheduleCaseRelation relation = new ScheduleCaseRelation();
        relation.setScheduleId(scheduleId);
        relation.setCaseId(caseId);
        relation.setDescription(description);
        
        return caseRelationMapper.insert(relation) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkTask(Long scheduleId, Long taskId, String description) {
        log.info("关联日程与任务，日程ID：{}，任务ID：{}", scheduleId, taskId);
        
        // TODO: 验证任务是否存在
        
        LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleTaskRelation::getScheduleId, scheduleId)
                .eq(ScheduleTaskRelation::getTaskId, taskId);
        
        if (taskRelationMapper.selectCount(queryWrapper) > 0) {
            log.info("日程与任务已关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
            return true;
        }
        
        ScheduleTaskRelation relation = new ScheduleTaskRelation();
        relation.setScheduleId(scheduleId);
        relation.setTaskId(taskId);
        relation.setDescription(description);
        
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
        List<ScheduleCaseRelationVO> relationVOs = relations.stream()
                .map(caseRelationConvert::toVO)
                .collect(Collectors.toList());
        
        // 填充案件信息
        fillCaseInfo(relationVOs);
        
        return relationVOs;
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
        
        // 验证案件是否存在
        if (!caseIntegration.caseExists(caseId)) {
            log.error("获取案件关联的日程列表失败，案件不存在，案件ID：{}", caseId);
            return Collections.emptyList();
        }
        
        LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleCaseRelation::getCaseId, caseId);
        
        List<ScheduleCaseRelation> relations = caseRelationMapper.selectList(queryWrapper);
        List<ScheduleCaseRelationVO> relationVOs = relations.stream()
                .map(caseRelationConvert::toVO)
                .collect(Collectors.toList());
        
        // 填充案件信息
        fillCaseInfo(relationVOs);
        
        return relationVOs;
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
        log.info("批量关联日程与案件，关联数量：{}", relationDTOs.size());
        
        // 验证案件是否存在
        Set<Long> caseIds = relationDTOs.stream()
                .map(ScheduleCaseRelationDTO::getCaseId)
                .collect(Collectors.toSet());
        
        for (Long caseId : caseIds) {
            if (!caseIntegration.caseExists(caseId)) {
                log.error("批量关联日程与案件失败，案件不存在，案件ID：{}", caseId);
                throw new IllegalArgumentException("案件不存在：" + caseId);
            }
        }
        
        List<ScheduleCaseRelation> relations = relationDTOs.stream()
                .map(caseRelationConvert::toEntity)
                .collect(Collectors.toList());
        
        int insertCount = 0;
        for (ScheduleCaseRelation relation : relations) {
            // 检查关联是否已存在
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
        log.info("批量关联日程与任务，关联数量：{}", relationDTOs.size());
        
        // TODO: 验证任务是否存在
        
        List<ScheduleTaskRelation> relations = relationDTOs.stream()
                .map(taskRelationConvert::toEntity)
                .collect(Collectors.toList());
        
        int insertCount = 0;
        for (ScheduleTaskRelation relation : relations) {
            // 检查关联是否已存在
            LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ScheduleTaskRelation::getScheduleId, relation.getScheduleId())
                    .eq(ScheduleTaskRelation::getTaskId, relation.getTaskId());
            
            if (taskRelationMapper.selectCount(queryWrapper) == 0) {
                insertCount += taskRelationMapper.insert(relation);
            }
        }
        
        return insertCount > 0;
    }
    
    /**
     * 填充案件信息
     * 
     * @param relationVOs 案件关联VO列表
     */
    private void fillCaseInfo(List<ScheduleCaseRelationVO> relationVOs) {
        if (relationVOs == null || relationVOs.isEmpty()) {
            return;
        }
        
        Set<Long> caseIds = relationVOs.stream()
                .map(ScheduleCaseRelationVO::getCaseId)
                .collect(Collectors.toSet());
        
        Map<Long, CaseDetailVO> caseMap = caseIntegration.getCasesInfo(caseIds);
        
        relationVOs.forEach(vo -> {
            CaseDetailVO caseDetail = caseMap.get(vo.getCaseId());
            if (caseDetail != null) {
                // 将案件详情转为Map
                Map<String, Object> caseInfo = new java.util.HashMap<>();
                caseInfo.put("id", caseDetail.getId());
                caseInfo.put("caseNumber", caseDetail.getCaseNumber());
                caseInfo.put("caseName", caseDetail.getCaseName());
                caseInfo.put("status", caseDetail.getStatus());
                caseInfo.put("description", caseDetail.getDescription());
                caseInfo.put("clientId", caseDetail.getClientId());
                caseInfo.put("clientName", caseDetail.getClientName());
                
                vo.setCaseInfo(caseInfo);
                // 设置案件标题和编号
                vo.setCaseTitle(caseDetail.getCaseName());
                vo.setCaseNumber(caseDetail.getCaseNumber());
            }
        });
    }

    @Override
    public List<ScheduleCaseRelationVO> listCaseRelationsByCaseId(Long caseId) {
        log.info("获取案件关联的日程关系列表，案件ID：{}", caseId);
        
        if (caseId == null) {
            return Collections.emptyList();
        }
        
        LambdaQueryWrapper<ScheduleCaseRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleCaseRelation::getCaseId, caseId);
        
        List<ScheduleCaseRelation> relations = caseRelationMapper.selectList(queryWrapper);
        
        return relations.stream()
                .map(caseRelationConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleTaskRelationVO> listTaskRelationsByTaskId(Long taskId) {
        log.info("获取任务关联的日程关系列表，任务ID：{}", taskId);
        
        if (taskId == null) {
            return Collections.emptyList();
        }
        
        LambdaQueryWrapper<ScheduleTaskRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleTaskRelation::getTaskId, taskId);
        
        List<ScheduleTaskRelation> relations = taskRelationMapper.selectList(queryWrapper);
        
        return relations.stream()
                .map(taskRelationConvert::toVO)
                .collect(Collectors.toList());
    }
} 