package com.lawfirm.cases.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.service.CaseStatisticsService;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseDifficultyEnum;
import com.lawfirm.model.cases.enums.CaseFeeTypeEnum;
import com.lawfirm.model.cases.enums.CaseHandleTypeEnum;
import com.lawfirm.model.cases.enums.CaseImportanceEnum;
import com.lawfirm.model.cases.enums.CasePriorityEnum;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseSourceEnum;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseStatisticsServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseStatisticsService {

    @Override
    public Map<CaseStatusEnum, Long> countByStatus() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseStatus, Collectors.counting()));
    }

    @Override
    public Map<CaseTypeEnum, Long> countByType() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseType, Collectors.counting()));
    }

    @Override
    public Map<String, Long> countByLawyer() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getLawyer, Collectors.counting()));
    }

    @Override
    public Map<Long, Long> countByClient() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getClientId, Collectors.counting()));
    }

    @Override
    public long countCasesByStatus(CaseStatusEnum status) {
        return count(new LambdaQueryWrapper<Case>()
                .eq(Case::getCaseStatus, status));
    }

    @Override
    public long countCasesByLawyer(String lawyer) {
        return count(new LambdaQueryWrapper<Case>()
                .eq(Case::getLawyer, lawyer));
    }

    @Override
    public long countCasesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return count(new LambdaQueryWrapper<Case>()
                .between(Case::getFilingTime, startDate, endDate));
    }

    @Override
    public Map<CaseProgressEnum, Long> getProgressStatistics() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseProgress, Collectors.counting()));
    }

    @Override
    public Map<CaseHandleTypeEnum, Long> getHandleTypeStatistics() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseHandleType, Collectors.counting()));
    }

    @Override
    public Map<CaseDifficultyEnum, Long> getDifficultyStatistics() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseDifficulty, Collectors.counting()));
    }

    @Override
    public Map<CaseImportanceEnum, Long> getImportanceStatistics() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseImportance, Collectors.counting()));
    }

    @Override
    public Map<CasePriorityEnum, Long> getPriorityStatistics() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCasePriority, Collectors.counting()));
    }

    @Override
    public Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseFeeType, Collectors.counting()));
    }

    @Override
    public Map<CaseSourceEnum, Long> getSourceStatistics() {
        return list().stream()
                .collect(Collectors.groupingBy(Case::getCaseSource, Collectors.counting()));
    }
} 