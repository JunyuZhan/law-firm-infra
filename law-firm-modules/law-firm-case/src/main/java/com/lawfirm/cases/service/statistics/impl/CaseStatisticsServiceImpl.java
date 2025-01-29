package com.lawfirm.cases.service.statistics.impl;

import com.lawfirm.cases.service.statistics.CaseStatisticsService;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.mapper.CaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaseStatisticsServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseStatisticsService {

    @Override
    public Map<CaseStatusEnum, Long> countByStatus() {
        List<Case> cases = list();
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseStatus, Collectors.counting()));
    }

    @Override
    public Map<CaseImportanceEnum, Long> getImportanceStatistics() {
        List<Case> cases = list();
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseImportance, Collectors.counting()));
    }

    @Override
    public Map<CasePriorityEnum, Long> getPriorityStatistics() {
        List<Case> cases = list();
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCasePriority, Collectors.counting()));
    }

    @Override
    public Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics() {
        List<Case> cases = list();
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseFeeType, Collectors.counting()));
    }

    @Override
    public Map<CaseSourceEnum, Long> getSourceStatistics() {
        List<Case> cases = list();
        return cases.stream()
                .collect(Collectors.groupingBy(Case::getCaseSource, Collectors.counting()));
    }
} 