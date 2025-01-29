package com.lawfirm.cases.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.mapper.CaseConverter;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.service.CaseStatusManagementService;
import com.lawfirm.cases.service.CaseStatusService;
import com.lawfirm.common.core.exception.ResourceNotFoundException;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.cases.model.vo.CaseStatusChangeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseStatusManagementServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseStatusManagementService {

    private final CaseConverter caseConverter;
    private final CaseStatusService caseStatusService;

    @Override
    public List<CaseStatusChangeVO> getStatusHistory(Long caseId) {
        return caseStatusService.getCaseStatus().stream()
            .map(status -> {
                CaseStatusChangeVO vo = new CaseStatusChangeVO();
                vo.setCaseId(caseId);
                vo.setFromStatus(null);
                vo.setToStatus(CaseStatusEnum.valueOf(status.getName()));
                return vo;
            })
            .collect(Collectors.toList());
    }

    @Override
    public CaseStatusChangeVO getCurrentStatus(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        CaseStatusEnum status = caseConverter.toStatusEnum(caseEntity.getStatus());
        CaseStatusChangeVO vo = new CaseStatusChangeVO();
        vo.setCaseId(caseId);
        vo.setToStatus(status);
        return vo;
    }

    @Override
    public List<String> getAvailableStatus(Long caseId) {
        return Arrays.stream(CaseStatusEnum.values())
                .filter(status -> caseStatusService.canTransitTo(caseId, status))
                .map(CaseStatusEnum::name)
                .collect(Collectors.toList());
    }

    @Override
    public CaseStatusChangeVO updateStatus(Long caseId, String status, String reason, String operator) {
        CaseStatusEnum targetStatus = CaseStatusEnum.valueOf(status);
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        
        CaseStatusChangeVO vo = new CaseStatusChangeVO();
        vo.setCaseId(caseId);
        vo.setFromStatus(caseConverter.toStatusEnum(caseEntity.getStatus()));
        vo.setToStatus(targetStatus);
        vo.setOperator(operator);
        vo.setReason(reason);
        
        caseEntity.setStatus(caseConverter.toStatusEnum(targetStatus));
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        
        return vo;
    }

    @Override
    public CaseDetailVO updateProgress(Long caseId, CaseProgressEnum progress, String operator) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        caseEntity.setCaseProgress(progress);
        caseEntity.setOperator(operator);
        updateById(caseEntity);
        return caseConverter.toDetailVO(caseEntity);
    }

    @Override
    public CaseProgressEnum getCurrentProgress(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        return caseEntity.getCaseProgress();
    }

    @Override
    public List<CaseProgressEnum> getAvailableProgress(Long caseId) {
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        CaseProgressEnum currentProgress = caseEntity.getCaseProgress();
        // TODO: 根据当前进展返回可用的下一个进展列表
        return Arrays.asList(CaseProgressEnum.values());
    }

    @Override
    public void archiveCase(Long id, String operator) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        caseEntity.setStatus(caseConverter.toStatusEnum(CaseStatusEnum.ARCHIVED));
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }

    @Override
    public void reopenCase(Long id, String operator, String reason) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        caseEntity.setStatus(caseConverter.toStatusEnum(CaseStatusEnum.IN_PROGRESS));
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }

    @Override
    public void suspendCase(Long id, String operator, String reason) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        caseEntity.setStatus(caseConverter.toStatusEnum(CaseStatusEnum.SUSPENDED));
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }

    @Override
    public void resumeCase(Long id, String operator) {
        Case caseEntity = getById(id);
        if (caseEntity == null) {
            throw new ResourceNotFoundException("案件不存在");
        }
        caseEntity.setStatus(caseConverter.toStatusEnum(CaseStatusEnum.IN_PROGRESS));
        caseEntity.setOperator(operator);
        updateById(caseEntity);
    }
} 