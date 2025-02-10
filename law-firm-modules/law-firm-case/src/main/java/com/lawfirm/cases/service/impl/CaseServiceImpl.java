package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.model.cases.dto.CaseCreateDTO;
import com.lawfirm.model.cases.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.dto.CaseQueryDTO;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.cases.service.CaseService;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.exception.ResourceNotFoundException;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import com.lawfirm.cases.service.statistics.CaseStatisticsService;
import com.lawfirm.mapper.CaseConverter;
import com.lawfirm.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {

    private final CaseConverter caseConverter;
    private final CaseMapper caseMapper;
    private final CaseStatisticsService caseStatisticsService;

    @Override
    @Transactional
    public CaseDetailVO createCase(CaseCreateDTO dto) {
        Case entity = caseConverter.toEntity(dto);
        save(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    @Transactional
    public CaseDetailVO updateCase(Long id, CaseUpdateDTO dto) {
        Case entity = getById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        caseConverter.updateEntity(dto, entity);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    @Transactional
    public void deleteCase(Long id) {
        removeById(id);
    }

    @Override
    public CaseDetailVO getCaseById(Long id) {
        Case entity = getById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public CaseDetailVO getCaseByCaseNumber(String caseNumber) {
        Case entity = lambdaQuery()
            .eq(Case::getCaseNumber, caseNumber)
            .one();
        if (entity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public void validateCase(Long id) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        // 执行验证逻辑
    }

    @Override
    public Map<CaseStatusEnum, Long> countByStatus() {
        return caseStatisticsService.countByStatus();
    }

    @Override
    public CaseDetailVO updateProgress(Long id, CaseProgressEnum progress, String remark) {
        Case entity = getById(id);
        entity.setCaseProgress(progress);
        entity.setRemark(remark);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public CaseProgressEnum getCurrentProgress(Long id) {
        return getById(id).getCaseProgress();
    }

    @Override
    public CaseDetailVO updateImportance(Long id, CaseImportanceEnum importance, String remark) {
        Case entity = getById(id);
        entity.setCaseImportance(importance);
        entity.setRemark(remark);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public CaseDetailVO updateSource(Long id, CaseSourceEnum source, String remark) {
        Case entity = getById(id);
        entity.setCaseSource(source);
        entity.setRemark(remark);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public List<CaseDetailVO> findByClient(Long clientId, CaseQueryDTO queryDTO) {
        List<Case> cases = lambdaQuery()
            .eq(Case::getClientId, clientId)
            .eq(queryDTO.getCaseStatus() != null, Case::getCaseStatus, queryDTO.getCaseStatus())
            .eq(queryDTO.getCaseType() != null, Case::getCaseType, queryDTO.getCaseType())
            .eq(queryDTO.getCaseProgress() != null, Case::getCaseProgress, queryDTO.getCaseProgress())
            .eq(queryDTO.getCaseHandleType() != null, Case::getCaseHandleType, queryDTO.getCaseHandleType())
            .eq(queryDTO.getCaseDifficulty() != null, Case::getCaseDifficulty, queryDTO.getCaseDifficulty())
            .eq(queryDTO.getCaseImportance() != null, Case::getCaseImportance, queryDTO.getCaseImportance())
            .eq(queryDTO.getCasePriority() != null, Case::getCasePriority, queryDTO.getCasePriority())
            .eq(queryDTO.getCaseFeeType() != null, Case::getCaseFeeType, queryDTO.getCaseFeeType())
            .eq(queryDTO.getCaseSource() != null, Case::getCaseSource, queryDTO.getCaseSource())
            .eq(queryDTO.getLawyer() != null, Case::getLawyer, queryDTO.getLawyer())
            .list();
        return cases.stream()
            .map(caseConverter::toDetailVO)
            .toList();
    }

    @Override
    public List<CaseDetailVO> findByCurrentLawyer() {
        String currentLawyer = SecurityUtils.getUsername();
        List<Case> cases = lambdaQuery()
            .eq(Case::getLawyer, currentLawyer)
            .list();
        return cases.stream()
            .map(caseConverter::toDetailVO)
            .toList();
    }

    @Override
    public CaseStatusEnum getCurrentStatus(Long id) {
        return caseConverter.toStatusEnum(getById(id).getStatus());
    }

    @Override
    public Map<CaseImportanceEnum, Long> getImportanceStatistics() {
        return caseStatisticsService.getImportanceStatistics();
    }

    @Override
    public org.springframework.data.domain.Page<CaseDetailVO> findCases(CaseQueryDTO queryDTO, Pageable pageable) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Case> page = lambdaQuery()
            .eq(queryDTO.getCaseStatus() != null, Case::getCaseStatus, queryDTO.getCaseStatus())
            .eq(queryDTO.getCaseType() != null, Case::getCaseType, queryDTO.getCaseType())
            .eq(queryDTO.getCaseProgress() != null, Case::getCaseProgress, queryDTO.getCaseProgress())
            .eq(queryDTO.getCaseHandleType() != null, Case::getCaseHandleType, queryDTO.getCaseHandleType())
            .eq(queryDTO.getCaseDifficulty() != null, Case::getCaseDifficulty, queryDTO.getCaseDifficulty())
            .eq(queryDTO.getCaseImportance() != null, Case::getCaseImportance, queryDTO.getCaseImportance())
            .eq(queryDTO.getCasePriority() != null, Case::getCasePriority, queryDTO.getCasePriority())
            .eq(queryDTO.getCaseFeeType() != null, Case::getCaseFeeType, queryDTO.getCaseFeeType())
            .eq(queryDTO.getCaseSource() != null, Case::getCaseSource, queryDTO.getCaseSource())
            .eq(queryDTO.getLawyer() != null, Case::getLawyer, queryDTO.getLawyer())
            .eq(queryDTO.getClientId() != null, Case::getClientId, queryDTO.getClientId())
            .page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageable.getPageNumber() + 1, pageable.getPageSize()));

        List<CaseDetailVO> records = page.getRecords().stream()
            .map(caseConverter::toDetailVO)
            .collect(Collectors.toList());

        return new PageImpl<>(records, pageable, page.getTotal());
    }

    @Override
    public Map<CaseStatusEnum, Long> getCaseStatistics() {
        return caseStatisticsService.countByStatus();
    }

    @Override
    public List<CaseDetailVO> findByQuery(CaseQueryDTO queryDTO) {
        List<Case> cases = lambdaQuery()
            .eq(queryDTO.getCaseStatus() != null, Case::getCaseStatus, queryDTO.getCaseStatus())
            .eq(queryDTO.getCaseType() != null, Case::getCaseType, queryDTO.getCaseType())
            .eq(queryDTO.getCaseProgress() != null, Case::getCaseProgress, queryDTO.getCaseProgress())
            .eq(queryDTO.getCaseHandleType() != null, Case::getCaseHandleType, queryDTO.getCaseHandleType())
            .eq(queryDTO.getCaseDifficulty() != null, Case::getCaseDifficulty, queryDTO.getCaseDifficulty())
            .eq(queryDTO.getCaseImportance() != null, Case::getCaseImportance, queryDTO.getCaseImportance())
            .eq(queryDTO.getCasePriority() != null, Case::getCasePriority, queryDTO.getCasePriority())
            .eq(queryDTO.getCaseFeeType() != null, Case::getCaseFeeType, queryDTO.getCaseFeeType())
            .eq(queryDTO.getCaseSource() != null, Case::getCaseSource, queryDTO.getCaseSource())
            .eq(queryDTO.getLawyer() != null, Case::getLawyer, queryDTO.getLawyer())
            .eq(queryDTO.getClientId() != null, Case::getClientId, queryDTO.getClientId())
            .list();
        return cases.stream()
            .map(caseConverter::toDetailVO)
            .toList();
    }

    @Override
    public org.springframework.data.domain.Page<CaseDetailVO> findByQuery(CaseQueryDTO queryDTO, Pageable pageable) {
        return findCases(queryDTO, pageable);
    }

    @Override
    public List<CaseDetailVO> findRelatedCases(Long caseId) {
        Case currentCase = getById(caseId);
        if (currentCase == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        List<Case> cases = lambdaQuery()
            .eq(Case::getClientId, currentCase.getClientId())
            .ne(Case::getId, caseId)
            .list();
        return cases.stream()
            .map(caseConverter::toDetailVO)
            .toList();
    }

    @Override
    public List<CaseStatusVO> getStatusHistory(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        // TODO: 从状态历史表中查询
        return Collections.emptyList();
    }

    @Override
    public List<CaseStatusEnum> getAvailableStatus(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        CaseStatusEnum currentStatus = CaseStatusEnum.valueOf(caseEntity.getStatus().name());
        // TODO: 根据当前状态返回可用的下一个状态列表
        return Arrays.asList(CaseStatusEnum.values());
    }

    @Override
    public void updateStatus(Long caseId, String status, String reason, String remark) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        CaseStatusEnum newStatus = CaseStatusEnum.valueOf(status);
        caseEntity.setStatus(StatusEnum.valueOf(newStatus.name()));
        updateById(caseEntity);
        
        // TODO: 记录状态变更历史
    }

    @Override
    public List<CaseProgressEnum> getAvailableProgress(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        // TODO: 根据当前进展返回可用的下一个进展列表
        return Arrays.asList(CaseProgressEnum.values());
    }

    @Override
    public CaseDetailVO updateHandleType(Long id, CaseHandleTypeEnum handleType, String remark) {
        Case entity = getById(id);
        entity.setCaseHandleType(handleType);
        entity.setRemark(remark);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public CaseDetailVO updateDifficulty(Long id, CaseDifficultyEnum difficulty, String remark) {
        Case entity = getById(id);
        entity.setCaseDifficulty(difficulty);
        entity.setRemark(remark);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public CaseDetailVO updateFeeType(Long id, CaseFeeTypeEnum feeType, String remark) {
        Case entity = getById(id);
        entity.setCaseFeeType(feeType);
        entity.setRemark(remark);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public CaseDetailVO updatePriority(Long id, CasePriorityEnum priority, String remark) {
        Case entity = getById(id);
        entity.setCasePriority(priority);
        entity.setRemark(remark);
        updateById(entity);
        return caseConverter.toDetailVO(entity);
    }

    @Override
    public List<CaseDetailVO> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getCaseProgress, progress)
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getCaseHandleType, handleType)
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getCaseImportance, importance)
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getCaseDifficulty, difficulty)
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<CaseTypeEnum, Long> countByType() {
        List<Case> cases = caseMapper.selectList(null);
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseType, Collectors.counting()));
    }

    @Override
    public Map<String, Long> countByLawyer() {
        return baseMapper.selectList(null).stream()
                .collect(Collectors.groupingBy(Case::getLawyer, Collectors.counting()));
    }

    @Override
    public Map<String, Long> countByClient() {
        return baseMapper.selectList(null).stream()
                .collect(Collectors.groupingBy(c -> String.valueOf(c.getClientId()), Collectors.counting()));
    }

    @Override
    public Map<CaseProgressEnum, Long> countByProgress() {
        List<Case> cases = caseMapper.selectList(null);
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseProgress, Collectors.counting()));
    }

    @Override
    public Map<CaseHandleTypeEnum, Long> countByHandleType() {
        List<Case> cases = caseMapper.selectList(null);
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseHandleType, Collectors.counting()));
    }

    @Override
    public Map<CaseImportanceEnum, Long> countByImportance() {
        List<Case> cases = caseMapper.selectList(null);
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseImportance, Collectors.counting()));
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        return baseMapper.selectList(null).stream()
                .collect(Collectors.groupingBy(Case::getCaseProgress, Collectors.counting()));
    }

    @Override
    public Map<CaseHandleTypeEnum, Long> getHandleTypeStatistics() {
        return baseMapper.selectList(null).stream()
                .collect(Collectors.groupingBy(Case::getCaseHandleType, Collectors.counting()));
    }

    @Override
    public Map<CaseDifficultyEnum, Long> getDifficultyStatistics() {
        return baseMapper.selectList(null).stream()
                .collect(Collectors.groupingBy(Case::getCaseDifficulty, Collectors.counting()));
    }

    @Override
    public Map<CasePriorityEnum, Long> getPriorityStatistics() {
        return caseStatisticsService.getPriorityStatistics();
    }

    @Override
    public Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics() {
        return caseStatisticsService.getFeeTypeStatistics();
    }

    @Override
    public Map<CaseSourceEnum, Long> getSourceStatistics() {
        return caseStatisticsService.getSourceStatistics();
    }

    private Case getById(Long id) {
        Case entity = caseMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("案件不存在");
        }
        return entity;
    }
}
