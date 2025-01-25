package com.lawfirm.cases.service.impl;

import com.lawfirm.cases.repository.CaseRepository;
import com.lawfirm.cases.service.CaseStatusService;
import com.lawfirm.common.data.exception.BusinessException;
import com.lawfirm.common.data.exception.ResourceNotFoundException;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaseStatusServiceImpl implements CaseStatusService {

    private final CaseRepository caseRepository;

    @Override
    @Transactional
    public void transitStatus(Long caseId, CaseStatusEnum targetStatus, String operator, String reason) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("案件不存在: " + caseId));

        if (!canTransitTo(caseId, targetStatus)) {
            throw new BusinessException("当前状态不允许转换到目标状态");
        }

        caseInfo.setCaseStatus(targetStatus);
        caseRepository.save(caseInfo);
    }

    @Override
    public boolean canTransitTo(Long caseId, CaseStatusEnum targetStatus) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("案件不存在: " + caseId));

        CaseStatusEnum currentStatus = caseInfo.getCaseStatus();
        return isValidTransition(currentStatus, targetStatus);
    }

    @Override
    public CaseStatusEnum getCurrentStatus(Long caseId) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("案件不存在: " + caseId));
        return caseInfo.getCaseStatus();
    }

    @Override
    public List<CaseStatusVO> getCaseStatus() {
        return List.of(CaseStatusEnum.values()).stream()
                .map(status -> {
                    CaseStatusVO vo = new CaseStatusVO();
                    vo.setStatus(status);
                    vo.setDescription(status.getDescription());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    private boolean isValidTransition(CaseStatusEnum currentStatus, CaseStatusEnum targetStatus) {
        // 根据业务规则判断状态转换是否合法
        switch (currentStatus) {
            case DRAFT:
                return targetStatus == CaseStatusEnum.PENDING;
            case PENDING:
                return targetStatus == CaseStatusEnum.IN_PROGRESS || targetStatus == CaseStatusEnum.CLOSED;
            case IN_PROGRESS:
                return targetStatus == CaseStatusEnum.SUSPENDED || targetStatus == CaseStatusEnum.COMPLETED;
            case SUSPENDED:
                return targetStatus == CaseStatusEnum.IN_PROGRESS;
            case COMPLETED:
                return targetStatus == CaseStatusEnum.CLOSED;
            case CLOSED:
                return targetStatus == CaseStatusEnum.ARCHIVED;
            default:
                return false;
        }
    }
} 