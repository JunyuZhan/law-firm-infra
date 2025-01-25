package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.converter.CaseConverter;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.model.dto.CaseCreateDTO;
import com.lawfirm.cases.model.dto.CaseQueryDTO;
import com.lawfirm.cases.model.dto.CaseUpdateDTO;
import com.lawfirm.cases.service.CaseService;
import com.lawfirm.cases.service.CaseStatusService;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.exception.ResourceNotFoundException;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {

    private final CaseConverter caseConverter;
    private final CaseStatusService caseStatusService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO createCase(CaseCreateDTO createDTO) {
        // 检查案件编号是否已存在
        if (lambdaQuery().eq(Case::getCaseNumber, createDTO.getCaseNumber()).exists()) {
            throw new BusinessException("案件编号已存在");
        }

        Case caseEntity = caseConverter.toEntity(createDTO);
        caseEntity.setCaseStatus(CaseStatusEnum.DRAFT);
        caseEntity.setStatusEnum(StatusEnum.ENABLED);
        caseEntity.setFilingTime(LocalDateTime.now());
        
        save(caseEntity);
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updateCase(Long id, CaseUpdateDTO updateDTO) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }

        caseConverter.updateEntity(caseEntity, updateDTO);
        updateById(caseEntity);
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCase(Long id) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        removeById(id);
    }

    @Override
    public CaseDetailVO getCaseById(Long id) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public Page<CaseDetailVO> findCases(CaseQueryDTO queryDTO, Pageable pageable) {
        LambdaQueryWrapper<Case> wrapper = new LambdaQueryWrapper<>();
        
        // 基本信息查询
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            wrapper.and(w -> w
                    .like(Case::getCaseNumber, queryDTO.getKeyword())
                    .or()
                    .like(Case::getCaseName, queryDTO.getKeyword())
            );
        }
        
        // 状态查询
        if (queryDTO.getCaseStatus() != null) {
            wrapper.eq(Case::getCaseStatus, queryDTO.getCaseStatus());
        }
        
        // 类型查询
        if (queryDTO.getCaseType() != null) {
            wrapper.eq(Case::getCaseType, queryDTO.getCaseType());
        }
        
        // 时间范围查询
        if (queryDTO.getStartTime() != null) {
            wrapper.ge(Case::getFilingTime, queryDTO.getStartTime());
        }
        if (queryDTO.getEndTime() != null) {
            wrapper.le(Case::getFilingTime, queryDTO.getEndTime());
        }
        
        // 执行分页查询
        Page<Case> casePage = page(new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize()), wrapper);
        
        // 转换为VO
        List<CaseDetailVO> voList = casePage.getRecords().stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(voList, pageable, casePage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validateCase(Long id) {
        caseStatusService.transitStatus(id, CaseStatusEnum.IN_PROGRESS, "系统", "案件验证通过");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void archiveCase(Long id, String operator) {
        caseStatusService.transitStatus(id, CaseStatusEnum.ARCHIVED, operator, "案件归档");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reopenCase(Long id, String operator, String reason) {
        caseStatusService.transitStatus(id, CaseStatusEnum.IN_PROGRESS, operator, reason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspendCase(Long id, String operator, String reason) {
        caseStatusService.transitStatus(id, CaseStatusEnum.SUSPENDED, operator, reason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeCase(Long id, String operator) {
        caseStatusService.transitStatus(id, CaseStatusEnum.IN_PROGRESS, operator, "案件恢复");
    }

    @Override
    public long countCasesByStatus(CaseStatusEnum status) {
        return lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .count();
    }

    @Override
    public long countCasesByLawyer(String lawyer) {
        return lambdaQuery()
                .eq(Case::getLawyer, lawyer)
                .count();
    }

    @Override
    public long countCasesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return lambdaQuery()
                .between(Case::getFilingTime, startDate, endDate)
                .count();
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public CaseDetailVO getCaseByCaseNumber(String caseNumber) {
        Case caseEntity = lambdaQuery().eq(Case::getCaseNumber, caseNumber).one();
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "caseNumber", caseNumber);
        }
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public List<Case> findCasesByClient(Long clientId) {
        return lambdaQuery()
                .eq(Case::getClientId, clientId)
                .list();
    }

    @Override
    public List<Case> findCasesByLawyer(Long lawyerId) {
        return lambdaQuery()
                .eq(Case::getLawyerId, lawyerId)
                .list();
    }

    @Override
    public List<Case> findCasesByStatus(CaseStatusEnum status) {
        return lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .list();
    }

    @Override
    public List<Case> findCasesByType(CaseTypeEnum caseType) {
        return lambdaQuery()
                .eq(Case::getCaseType, caseType)
                .list();
    }

    @Override
    public List<Case> findCasesByLawyer(String lawyer) {
        return lambdaQuery()
                .eq(Case::getLawyer, lawyer)
                .list();
    }

    @Override
    public List<Case> findCasesByClient(String client) {
        return lambdaQuery()
                .eq(Case::getClientId, client)
                .list();
    }

    @Override
    public List<Case> findCasesByType(String caseType) {
        return lambdaQuery()
                .eq(Case::getCaseType, CaseTypeEnum.valueOf(caseType))
                .list();
    }

    @Override
    public List<Case> findCasesByStatus(CaseStatusEnum status) {
        return lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .list();
    }

    @Override
    public List<Case> findMajorCases() {
        return lambdaQuery()
                .eq(Case::getIsMajor, true)
                .list();
    }

    @Override
    public List<Case> findConflictCases() {
        return lambdaQuery()
                .eq(Case::getHasConflict, true)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public Map<CaseStatusEnum, Long> getCaseStatistics() {
        List<Case> cases = list();
        return cases.stream()
                .collect(Collectors.groupingBy(
                        Case::getCaseStatus,
                        Collectors.counting()
                ));
    }

    @Override
    public List<CaseDetailVO> findCasesByClient(Long clientId) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getClientId, clientId)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByLawyer(Long lawyerId) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getLawyerId, lawyerId)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByStatus(CaseStatusEnum status) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByType(CaseTypeEnum caseType) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getCaseType, caseType)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Case> findCasesByType(String caseType) {
        return lambdaQuery()
                .eq(Case::getCaseType, CaseTypeEnum.valueOf(caseType))
                .list();
    }

    @Override
    public List<Case> findCasesByStatus(CaseStatusEnum status) {
        return lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .list();
    }

    @Override
    public List<Case> findMajorCases() {
        return lambdaQuery()
                .eq(Case::getIsMajor, true)
                .list();
    }

    @Override
    public List<Case> findConflictCases() {
        return lambdaQuery()
                .eq(Case::getHasConflict, true)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public Map<CaseHandleTypeEnum, Long> getHandleTypeStatistics() {
        List<Map<String, Object>> statistics = lambdaQuery()
                .select(Case::getHandleType)
                .groupBy(Case::getHandleType)
                .listMaps(new LambdaQueryWrapper<>());
        
        Map<CaseHandleTypeEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseHandleTypeEnum handleType = (CaseHandleTypeEnum) map.get("handle_type");
            Long count = (Long) map.get("count");
            result.put(handleType, count);
        });
        return result;
    }

    @Override
    public Map<CaseDifficultyEnum, Long> getDifficultyStatistics() {
        List<Map<String, Object>> statistics = lambdaQuery()
                .select(Case::getDifficulty)
                .groupBy(Case::getDifficulty)
                .listMaps(new LambdaQueryWrapper<>());
        
        Map<CaseDifficultyEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseDifficultyEnum difficulty = (CaseDifficultyEnum) map.get("difficulty");
            Long count = (Long) map.get("count");
            result.put(difficulty, count);
        });
        return result;
    }

    @Override
    public Map<CaseImportanceEnum, Long> getImportanceStatistics() {
        List<Map<String, Object>> statistics = lambdaQuery()
                .select(Case::getImportance)
                .groupBy(Case::getImportance)
                .listMaps(new LambdaQueryWrapper<>());
        
        Map<CaseImportanceEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseImportanceEnum importance = (CaseImportanceEnum) map.get("importance");
            Long count = (Long) map.get("count");
            result.put(importance, count);
        });
        return result;
    }

    @Override
    public Map<CasePriorityEnum, Long> getPriorityStatistics() {
        List<Map<String, Object>> statistics = lambdaQuery()
                .select(Case::getPriority)
                .groupBy(Case::getPriority)
                .listMaps(new LambdaQueryWrapper<>());
        
        Map<CasePriorityEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CasePriorityEnum priority = (CasePriorityEnum) map.get("priority");
            Long count = (Long) map.get("count");
            result.put(priority, count);
        });
        return result;
    }

    @Override
    public Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics() {
        List<Map<String, Object>> statistics = lambdaQuery()
                .select(Case::getFeeType)
                .groupBy(Case::getFeeType)
                .listMaps(new LambdaQueryWrapper<>());
        
        Map<CaseFeeTypeEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseFeeTypeEnum feeType = (CaseFeeTypeEnum) map.get("fee_type");
            Long count = (Long) map.get("count");
            result.put(feeType, count);
        });
        return result;
    }

    @Override
    public Map<CaseSourceEnum, Long> getSourceStatistics() {
        List<Map<String, Object>> statistics = lambdaQuery()
                .select(Case::getSource)
                .groupBy(Case::getSource)
                .listMaps(new LambdaQueryWrapper<>());
        
        Map<CaseSourceEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseSourceEnum source = (CaseSourceEnum) map.get("source");
            Long count = (Long) map.get("count");
            result.put(source, count);
        });
        return result;
    }

    @Override
    public CaseProgressEnum getCurrentProgress(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        return caseEntity.getProgress();
    }

    @Override
    public List<CaseProgressEnum> getAvailableProgress(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        // 根据案件类型和当前进展返回可用的进展状态
        List<CaseProgressEnum> availableProgress = new ArrayList<>();
        CaseProgressEnum currentProgress = caseEntity.getProgress();
        
        // TODO: 根据业务规则实现进展状态转换逻辑
        
        return availableProgress;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updateProgress(Long caseId, CaseProgressEnum progress, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        caseEntity.setProgress(progress);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updateHandleType(Long caseId, CaseHandleTypeEnum handleType, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        caseEntity.setHandleType(handleType);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updateDifficulty(Long caseId, CaseDifficultyEnum difficulty, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        caseEntity.setDifficulty(difficulty);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updateImportance(Long caseId, CaseImportanceEnum importance, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        caseEntity.setImportance(importance);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updatePriority(Long caseId, CasePriorityEnum priority, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        caseEntity.setPriority(priority);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updateFeeType(Long caseId, CaseFeeTypeEnum feeType, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        caseEntity.setFeeType(feeType);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseDetailVO updateSource(Long caseId, CaseSourceEnum source, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        caseEntity.setSource(source);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery()
                .eq(Case::getPriority, priority)
                .list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery()
                .eq(Case::getFeeType, feeType)
                .list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery()
                .eq(Case::getSource, source)
                .list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Case> allCases = list();
        return allCases.stream()
                .collect(Collectors.groupingBy(
                        Case::getProgress,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery()
                .eq(Case::getProgress, progress)
                .list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery()
                .eq(Case::getHandleType, handleType)
                .list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery()
                .eq(Case::getDifficulty, difficulty)
                .list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery()
                .eq(Case::getImportance, importance)
                .list();
package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.converter.CaseConverter;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.model.dto.CaseCreateDTO;
import com.lawfirm.cases.model.dto.CaseQueryDTO;
import com.lawfirm.cases.model.dto.CaseUpdateDTO;
import com.lawfirm.cases.repository.CaseRepository;
import com.lawfirm.cases.service.CaseService;
import com.lawfirm.common.data.exception.BusinessException;
import com.lawfirm.common.data.exception.ResourceNotFoundException;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.entity.CaseFile;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.data.mapper.BaseMapper;
import com.lawfirm.cases.query.CaseQuery;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {

    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;
    private final CaseConverter caseConverter;

    @Override
    @Transactional
    public CaseDetailVO createCase(CaseCreateDTO createDTO) {
        // 检查案件编号是否已存在
        if (lambdaQuery().eq(Case::getCaseNumber, createDTO.getCaseNumber()).exists()) {
            throw new IllegalArgumentException("案件编号已存在");
        }

        Case caseEntity = caseConverter.toEntity(createDTO);
        caseEntity.setCaseStatus(CaseStatusEnum.DRAFT);
        caseEntity.setStatusEnum(StatusEnum.ENABLED);
        caseEntity.setFilingTime(LocalDateTime.now());
        
        save(caseEntity);
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional
    public CaseDetailVO updateCase(Long id, CaseUpdateDTO updateDTO) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", id);
        }

        caseConverter.updateEntity(caseEntity, updateDTO);
        updateById(caseEntity);
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional
    public void deleteCase(Long id) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", id);
        }
        
        removeById(id);
    }

    @Override
    public CaseDetailVO getCaseById(Long id) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", id);
        }
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public CaseDetailVO getCaseByCaseNumber(String caseNumber) {
        Case caseEntity = lambdaQuery().eq(Case::getCaseNumber, caseNumber).one();
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "caseNumber", caseNumber);
        }
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public Page<CaseDetailVO> findCases(CaseQueryDTO queryDTO, Pageable pageable) {
        LambdaQueryWrapper<Case> wrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (queryDTO.getCaseNumber() != null) {
            wrapper.like(Case::getCaseNumber, queryDTO.getCaseNumber());
        }
        if (queryDTO.getCaseName() != null) {
            wrapper.like(Case::getCaseName, queryDTO.getCaseName());
        }
        if (queryDTO.getCaseType() != null) {
            wrapper.eq(Case::getCaseType, queryDTO.getCaseType());
        }
        if (queryDTO.getCaseStatus() != null) {
            wrapper.eq(Case::getCaseStatus, queryDTO.getCaseStatus());
        }
        if (queryDTO.getProgress() != null) {
            wrapper.eq(Case::getProgress, queryDTO.getProgress());
        }
        if (queryDTO.getHandleType() != null) {
            wrapper.eq(Case::getHandleType, queryDTO.getHandleType());
        }
        if (queryDTO.getDifficulty() != null) {
            wrapper.eq(Case::getDifficulty, queryDTO.getDifficulty());
        }
        if (queryDTO.getImportance() != null) {
            wrapper.eq(Case::getImportance, queryDTO.getImportance());
        }
        if (queryDTO.getPriority() != null) {
            wrapper.eq(Case::getPriority, queryDTO.getPriority());
        }
        if (queryDTO.getFeeType() != null) {
            wrapper.eq(Case::getFeeType, queryDTO.getFeeType());
        }
        if (queryDTO.getSource() != null) {
            wrapper.eq(Case::getSource, queryDTO.getSource());
        }
        if (queryDTO.getLawyer() != null) {
            wrapper.like(Case::getLawyer, queryDTO.getLawyer());
        }
        if (queryDTO.getClientId() != null) {
            wrapper.eq(Case::getClientId, queryDTO.getClientId());
        }
        if (queryDTO.getLawFirmId() != null) {
            wrapper.eq(Case::getLawFirmId, queryDTO.getLawFirmId());
        }
        if (queryDTO.getBranchId() != null) {
            wrapper.eq(Case::getBranchId, queryDTO.getBranchId());
        }
        if (queryDTO.getDepartmentId() != null) {
            wrapper.eq(Case::getDepartmentId, queryDTO.getDepartmentId());
        }
        
        // 时间范围查询
        if (queryDTO.getFilingTimeStart() != null) {
            wrapper.ge(Case::getFilingTime, queryDTO.getFilingTimeStart());
        }
        if (queryDTO.getFilingTimeEnd() != null) {
            wrapper.le(Case::getFilingTime, queryDTO.getFilingTimeEnd());
        }
        if (queryDTO.getClosingTimeStart() != null) {
            wrapper.ge(Case::getClosingTime, queryDTO.getClosingTimeStart());
        }
        if (queryDTO.getClosingTimeEnd() != null) {
            wrapper.le(Case::getClosingTime, queryDTO.getClosingTimeEnd());
        }
        
        // 费用范围查询
        if (queryDTO.getFeeMin() != null) {
            wrapper.ge(Case::getFee, queryDTO.getFeeMin());
        }
        if (queryDTO.getFeeMax() != null) {
            wrapper.le(Case::getFee, queryDTO.getFeeMax());
        }
        
        // 法院信息查询
        if (queryDTO.getCourtName() != null) {
            wrapper.like(Case::getCourtName, queryDTO.getCourtName());
        }
        if (queryDTO.getJudgeName() != null) {
            wrapper.like(Case::getJudgeName, queryDTO.getJudgeName());
        }
        if (queryDTO.getCourtCaseNumber() != null) {
            wrapper.like(Case::getCourtCaseNumber, queryDTO.getCourtCaseNumber());
        }
        
        // 特殊标记查询
        if (queryDTO.getIsMajor() != null) {
            wrapper.eq(Case::getIsMajor, queryDTO.getIsMajor());
        }
        if (queryDTO.getHasConflict() != null) {
            wrapper.eq(Case::getHasConflict, queryDTO.getHasConflict());
        }

        // 执行分页查询
        Page<Case> casePage = page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
                pageable.getPageNumber() + 1,
                pageable.getPageSize()
        ), wrapper);

        // 转换为VO
        List<CaseDetailVO> voList = casePage.getRecords().stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());

        return new PageImpl<>(voList, pageable, casePage.getTotal());
    }

    @Override
    @Transactional
    public CaseDetailVO updateProgress(Long caseId, CaseProgressEnum progress, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        caseEntity.setProgress(progress);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public CaseProgressEnum getCurrentProgress(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        return caseEntity.getProgress();
    }

    @Override
    public List<CaseProgressEnum> getAvailableProgress(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        // 根据案件类型和当前进展返回可用的进展状态
        List<CaseProgressEnum> availableProgress = new ArrayList<>();
        CaseProgressEnum currentProgress = caseEntity.getProgress();
        
        // TODO: 根据业务规则实现进展状态转换逻辑
        
        return availableProgress;
    }

    @Override
    @Transactional
    public CaseDetailVO updateHandleType(Long caseId, CaseHandleTypeEnum handleType, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        caseEntity.setHandleType(handleType);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional
    public CaseDetailVO updateDifficulty(Long caseId, CaseDifficultyEnum difficulty, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        caseEntity.setDifficulty(difficulty);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional
    public CaseDetailVO updateImportance(Long caseId, CaseImportanceEnum importance, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        caseEntity.setImportance(importance);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional
    public CaseDetailVO updatePriority(Long caseId, CasePriorityEnum priority, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        caseEntity.setPriority(priority);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional
    public CaseDetailVO updateFeeType(Long caseId, CaseFeeTypeEnum feeType, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        caseEntity.setFeeType(feeType);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    @Transactional
    public CaseDetailVO updateSource(Long caseId, CaseSourceEnum source, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件", "id", caseId);
        }
        
        caseEntity.setSource(source);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery().eq(Case::getProgress, progress).list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery().eq(Case::getHandleType, handleType).list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery().eq(Case::getDifficulty, difficulty).list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery().eq(Case::getImportance, importance).list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery().eq(Case::getPriority, priority).list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery().eq(Case::getFeeType, feeType).list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery().eq(Case::getSource, source).list();
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        List<Map<String, Object>> statistics = caseMapper.selectMaps(
                new LambdaQueryWrapper<Case>()
                        .select(Case::getProgress)
                        .groupBy(Case::getProgress)
        );
        
        Map<CaseProgressEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseProgressEnum progress = (CaseProgressEnum) map.get("progress");
            Long count = (Long) map.get("count");
            result.put(progress, count);
        });
        return result;
    }

    @Override
    public Map<CaseHandleTypeEnum, Long> getHandleTypeStatistics() {
        List<Map<String, Object>> statistics = caseMapper.selectMaps(
                new LambdaQueryWrapper<Case>()
                        .select(Case::getHandleType)
                        .groupBy(Case::getHandleType)
        );
        
        Map<CaseHandleTypeEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseHandleTypeEnum handleType = (CaseHandleTypeEnum) map.get("handle_type");
            Long count = (Long) map.get("count");
            result.put(handleType, count);
        });
        return result;
    }

    @Override
    public Map<CaseDifficultyEnum, Long> getDifficultyStatistics() {
        List<Map<String, Object>> statistics = caseMapper.selectMaps(
                new LambdaQueryWrapper<Case>()
                        .select(Case::getDifficulty)
                        .groupBy(Case::getDifficulty)
        );
        
        Map<CaseDifficultyEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseDifficultyEnum difficulty = (CaseDifficultyEnum) map.get("difficulty");
            Long count = (Long) map.get("count");
            result.put(difficulty, count);
        });
        return result;
    }

    @Override
    public Map<CaseImportanceEnum, Long> getImportanceStatistics() {
        List<Map<String, Object>> statistics = caseMapper.selectMaps(
                new LambdaQueryWrapper<Case>()
                        .select(Case::getImportance)
                        .groupBy(Case::getImportance)
        );
        
        Map<CaseImportanceEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseImportanceEnum importance = (CaseImportanceEnum) map.get("importance");
            Long count = (Long) map.get("count");
            result.put(importance, count);
        });
        return result;
    }

    @Override
    public Map<CasePriorityEnum, Long> getPriorityStatistics() {
        List<Map<String, Object>> statistics = caseMapper.selectMaps(
                new LambdaQueryWrapper<Case>()
                        .select(Case::getPriority)
                        .groupBy(Case::getPriority)
        );
        
        Map<CasePriorityEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CasePriorityEnum priority = (CasePriorityEnum) map.get("priority");
            Long count = (Long) map.get("count");
            result.put(priority, count);
        });
        return result;
    }

    @Override
    public Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics() {
        List<Map<String, Object>> statistics = caseMapper.selectMaps(
                new LambdaQueryWrapper<Case>()
                        .select(Case::getFeeType)
                        .groupBy(Case::getFeeType)
        );
        
        Map<CaseFeeTypeEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseFeeTypeEnum feeType = (CaseFeeTypeEnum) map.get("fee_type");
            Long count = (Long) map.get("count");
            result.put(feeType, count);
        });
        return result;
    }

    @Override
    public Map<CaseSourceEnum, Long> getSourceStatistics() {
        List<Map<String, Object>> statistics = caseMapper.selectMaps(
                new LambdaQueryWrapper<Case>()
                        .select(Case::getSource)
                        .groupBy(Case::getSource)
        );
        
        Map<CaseSourceEnum, Long> result = new HashMap<>();
        statistics.forEach(map -> {
            CaseSourceEnum source = (CaseSourceEnum) map.get("source");
            Long count = (Long) map.get("count");
            result.put(source, count);
        });
        return result;
    }

    @Override
    public Map<CaseStatusEnum, Long> getCaseStatistics() {
        List<Case> cases = list();
        return cases.stream()
                .collect(Collectors.groupingBy(
                        Case::getCaseStatus,
                        Collectors.counting()
                ));
    }

    @Override
    public List<CaseDetailVO> findCasesByClient(Long clientId) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getClientId, clientId)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByLawyer(Long lawyerId) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getLawyerId, lawyerId)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByStatus(CaseStatusEnum status) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findCasesByType(CaseTypeEnum caseType) {
        List<Case> cases = lambdaQuery()
                .eq(Case::getCaseType, caseType)
                .list();
        return cases.stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Case> findCasesByLawyer(String lawyer) {
        return lambdaQuery()
                .eq(Case::getLawyer, lawyer)
                .list();
    }

    @Override
    public List<Case> findCasesByClient(String client) {
        return lambdaQuery()
                .eq(Case::getClientId, client)
                .list();
    }

    @Override
    public List<Case> findCasesByType(String caseType) {
        return lambdaQuery()
                .eq(Case::getCaseType, CaseTypeEnum.valueOf(caseType))
                .list();
    }

    @Override
    public List<Case> findCasesByStatus(CaseStatusEnum status) {
        return lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .list();
    }

    @Override
    public List<Case> findMajorCases() {
        return lambdaQuery()
                .eq(Case::getIsMajor, true)
                .list();
    }

    @Override
    public List<Case> findConflictCases() {
        return lambdaQuery()
                .eq(Case::getHasConflict, true)
                .list();
    }

    @Override
    public void validateCase(Long id) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new BusinessException("案件不存在");
        }
        
        caseEntity.setCaseStatus(CaseStatusEnum.IN_PROGRESS);
        caseEntity.setStatus(CaseStatusEnum.IN_PROGRESS.toStatusEnum());
        updateById(caseEntity);
    }

    @Override
    public void archiveCase(Long id, String operator) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new BusinessException("案件不存在");
        }
        
        caseEntity.setCaseStatus(CaseStatusEnum.ARCHIVED);
        caseEntity.setStatus(CaseStatusEnum.ARCHIVED.toStatusEnum());
        caseEntity.setClosingTime(LocalDateTime.now());
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }

    @Override
    public void reopenCase(Long id, String operator, String reason) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new BusinessException("案件不存在");
        }
        
        caseEntity.setCaseStatus(CaseStatusEnum.IN_PROGRESS);
        caseEntity.setStatus(CaseStatusEnum.IN_PROGRESS.toStatusEnum());
        caseEntity.setClosingTime(null);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }

    @Override
    public void suspendCase(Long id, String operator, String reason) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new BusinessException("案件不存在");
        }
        
        caseEntity.setCaseStatus(CaseStatusEnum.SUSPENDED);
        caseEntity.setStatus(CaseStatusEnum.SUSPENDED.toStatusEnum());
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }

    @Override
    public void resumeCase(Long id, String operator) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new BusinessException("案件不存在");
        }
        
        caseEntity.setCaseStatus(CaseStatusEnum.IN_PROGRESS);
        caseEntity.setStatus(CaseStatusEnum.IN_PROGRESS.toStatusEnum());
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }

    @Override
    public long countCasesByStatus(CaseStatusEnum status) {
        return lambdaQuery()
                .eq(Case::getCaseStatus, status)
                .count();
    }

    @Override
    public long countCasesByLawyer(String lawyer) {
        return lambdaQuery()
                .eq(Case::getLawyer, lawyer)
                .count();
    }

    @Override
    public long countCasesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return lambdaQuery()
                .between(Case::getFilingTime, startDate, endDate)
                .count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaseStatusVO> getStatusHistory(Long caseId) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Case not found with id: %d", caseId)));
        
        List<CaseStatusVO> history = new ArrayList<>();
        CaseStatusVO current = new CaseStatusVO();
        current.setCaseId(caseId);
        current.setStatus(caseInfo.getCaseStatus().getValue());
        current.setOperator(caseInfo.getOperator());
        current.setCreateTime(LocalDateTime.now());
        history.add(current);
        
        return history;
    }

    @Override
    @Transactional(readOnly = true)
    public CaseStatusVO getCurrentStatus(Long caseId) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Case not found with id: %d", caseId)));
        
        CaseStatusVO status = new CaseStatusVO();
        status.setCaseId(caseId);
        status.setStatus(caseInfo.getCaseStatus().getValue());
        status.setOperator(caseInfo.getOperator());
        status.setCreateTime(LocalDateTime.now());
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAvailableStatus(Long caseId) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Case not found with id: %d", caseId)));
        
        List<String> availableStatus = new ArrayList<>();
        availableStatus.add(CaseStatusEnum.IN_PROGRESS.getValue());
        availableStatus.add(CaseStatusEnum.COMPLETED.getValue());
        availableStatus.add(CaseStatusEnum.CANCELLED.getValue());
        return availableStatus;
    }

    @Override
    @Transactional
    public CaseStatusVO updateStatus(Long caseId, String status, String reason, String operator) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Case not found with id: %d", caseId)));
        
        if (!getAvailableStatus(caseId).contains(status)) {
            throw new BusinessException(String.format("Invalid status transition: %s", status));
        }
        
        caseInfo.setCaseStatus(CaseStatusEnum.valueOf(status));
        caseInfo.setStatus(CaseStatusEnum.valueOf(status).toStatusEnum());
        caseInfo.setOperator(operator);
        caseRepository.save(caseInfo);
        
        CaseStatusVO statusVO = new CaseStatusVO();
        statusVO.setCaseId(caseId);
        statusVO.setStatus(status);
        statusVO.setOperator(operator);
        statusVO.setReason(reason);
        statusVO.setCreateTime(LocalDateTime.now());
        return statusVO;
    }

    @Override
    public Optional<Case> getCaseByCaseNo(String caseNo) {
        return caseRepository.findByCaseNo(caseNo);
    }
} 