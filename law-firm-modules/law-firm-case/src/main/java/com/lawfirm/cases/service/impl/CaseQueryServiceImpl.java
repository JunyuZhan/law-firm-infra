package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.service.CaseQueryService;
import com.lawfirm.model.cases.dto.CaseQueryDTO;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.mapper.CaseConverter;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.lawfirm.model.base.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseQueryServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseQueryService {

    private final CaseConverter caseConverter;

    @Override
    public List<CaseDetailVO> findByLawyer(String lawyer, CaseQueryDTO query) {
        return lambdaQuery()
                .eq(Case::getLawyer, lawyer)
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findByClient(Long clientId, CaseQueryDTO query) {
        return lambdaQuery()
                .eq(Case::getClientId, clientId)
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CaseDetailVO> findRelatedCases(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new BusinessException("案件不存在");
        }
        return lambdaQuery()
                .eq(Case::getClientId, caseEntity.getClientId())
                .ne(Case::getId, caseId)
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public org.springframework.data.domain.Page<CaseDetailVO> findCases(CaseQueryDTO query, Pageable pageable) {
        LambdaQueryWrapper<Case> wrapper = new LambdaQueryWrapper<>();
        applyQueryConditions(wrapper, query);
        
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Case> mybatisPage = 
            baseMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
                pageable.getPageNumber() + 1, pageable.getPageSize()), wrapper);
        
        List<CaseDetailVO> records = mybatisPage.getRecords().stream()
            .map(caseConverter::toDetailVO)
            .collect(Collectors.toList());
            
        return new PageImpl<>(records, pageable, mybatisPage.getTotal());
    }

    @Override
    public List<CaseDetailVO> findByCurrentLawyer() {
        return lambdaQuery()
                .eq(Case::getLawyer, SecurityContextHolder.getContext().getAuthentication().getName())
                .list()
                .stream()
                .map(caseConverter::toDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Case> findCasesByStatus(CaseStatusEnum status) {
        return lambdaQuery().eq(Case::getCaseStatus, status).list();
    }

    @Override
    public List<Case> findCasesByType(String caseType) {
        return lambdaQuery().eq(Case::getCaseType, caseType).list();
    }

    @Override
    public List<Case> findMajorCases() {
        return lambdaQuery().eq(Case::getIsMajor, true).list();
    }

    @Override
    public List<Case> findConflictCases() {
        return lambdaQuery().eq(Case::getHasConflict, true).list();
    }

    @Override
    public List<Case> findCasesByProgress(CaseProgressEnum progress) {
        return lambdaQuery().eq(Case::getCaseProgress, progress).list();
    }

    @Override
    public List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType) {
        return lambdaQuery().eq(Case::getCaseHandleType, handleType).list();
    }

    @Override
    public List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty) {
        return lambdaQuery().eq(Case::getCaseDifficulty, difficulty).list();
    }

    @Override
    public List<Case> findCasesByImportance(CaseImportanceEnum importance) {
        return lambdaQuery().eq(Case::getCaseImportance, importance).list();
    }

    @Override
    public List<Case> findCasesByPriority(CasePriorityEnum priority) {
        return lambdaQuery().eq(Case::getCasePriority, priority).list();
    }

    @Override
    public List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType) {
        return lambdaQuery().eq(Case::getCaseFeeType, feeType).list();
    }

    @Override
    public List<Case> findCasesBySource(CaseSourceEnum source) {
        return lambdaQuery().eq(Case::getCaseSource, source).list();
    }

    private void applyQueryConditions(LambdaQueryWrapper<Case> wrapper, CaseQueryDTO query) {
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w.like(Case::getCaseName, query.getKeyword())
                    .or()
                    .like(Case::getCaseNumber, query.getKeyword()));
        }
        
        if (query.getCaseType() != null) {
            wrapper.eq(Case::getCaseType, query.getCaseType());
        }
        
        if (query.getCaseStatus() != null) {
            wrapper.eq(Case::getCaseStatus, query.getCaseStatus());
        }
        
        if (query.getCaseProgress() != null) {
            wrapper.eq(Case::getCaseProgress, query.getCaseProgress());
        }
        
        if (query.getCaseHandleType() != null) {
            wrapper.eq(Case::getCaseHandleType, query.getCaseHandleType());
        }
        
        if (query.getCaseDifficulty() != null) {
            wrapper.eq(Case::getCaseDifficulty, query.getCaseDifficulty());
        }
        
        if (query.getCaseImportance() != null) {
            wrapper.eq(Case::getCaseImportance, query.getCaseImportance());
        }
        
        if (query.getCasePriority() != null) {
            wrapper.eq(Case::getCasePriority, query.getCasePriority());
        }
        
        if (query.getCaseFeeType() != null) {
            wrapper.eq(Case::getCaseFeeType, query.getCaseFeeType());
        }
        
        if (query.getCaseSource() != null) {
            wrapper.eq(Case::getCaseSource, query.getCaseSource());
        }
        
        if (query.getLawyer() != null) {
            wrapper.eq(Case::getLawyer, query.getLawyer());
        }
        
        if (query.getClientId() != null) {
            wrapper.eq(Case::getClientId, query.getClientId());
        }
        
        if (query.getLawFirmId() != null) {
            wrapper.eq(Case::getLawFirmId, query.getLawFirmId());
        }
        
        if (query.getBranchId() != null) {
            wrapper.eq(Case::getBranchId, query.getBranchId());
        }
        
        if (query.getDepartmentId() != null) {
            wrapper.eq(Case::getDepartmentId, query.getDepartmentId());
        }
        
        if (query.getFilingTimeStart() != null) {
            wrapper.ge(Case::getFilingTime, query.getFilingTimeStart());
        }
        
        if (query.getFilingTimeEnd() != null) {
            wrapper.le(Case::getFilingTime, query.getFilingTimeEnd());
        }
        
        if (query.getCourtName() != null) {
            wrapper.eq(Case::getCourtName, query.getCourtName());
        }
        
        if (query.getJudgeName() != null) {
            wrapper.eq(Case::getJudgeName, query.getJudgeName());
        }
        
        if (query.getCourtCaseNumber() != null) {
            wrapper.eq(Case::getCourtCaseNumber, query.getCourtCaseNumber());
        }
        
        if (query.getIsMajor() != null) {
            wrapper.eq(Case::getIsMajor, query.getIsMajor());
        }
        
        if (query.getHasConflict() != null) {
            wrapper.eq(Case::getHasConflict, query.getHasConflict());
        }
    }
} 