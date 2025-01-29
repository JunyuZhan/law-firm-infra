package com.lawfirm.cases.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawfirm.mapper.CaseConverter;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.service.CaseStatusService;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.vo.CaseStatusEnumVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseStatusServiceImpl implements CaseStatusService {

    private final CaseMapper caseMapper;
    private final CaseConverter caseConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transitStatus(Long caseId, CaseStatusEnum targetStatus, String operator, String reason) {
        Case caseEntity = caseMapper.selectById(caseId);
        if (caseEntity == null) {
            throw new IllegalArgumentException("案件不存在");
        }

        CaseStatusEnum currentStatus = caseConverter.toStatusEnum(caseEntity.getStatus());
        if (!canTransitTo(caseId, targetStatus)) {
            throw new IllegalStateException("不允许变更到目标状态");
        }

        caseEntity.setStatus(caseConverter.toStatusEnum(targetStatus));
        caseEntity.setOperator(operator);
        caseEntity.setUpdateTime(LocalDateTime.now());
        caseEntity.setUpdateBy(operator);
        caseMapper.updateById(caseEntity);
    }

    @Override
    public boolean canTransitTo(Long caseId, CaseStatusEnum targetStatus) {
        // 根据业务规则判断是否可以转换到目标状态
        return true;
    }

    @Override
    public CaseStatusEnum getCurrentStatus(Long caseId) {
        Case caseEntity = caseMapper.selectById(caseId);
        if (caseEntity == null) {
            return null;
        }
        return caseConverter.toStatusEnum(caseEntity.getStatus());
    }

    @Override
    public List<CaseStatusEnumVO> getCaseStatus() {
        return Arrays.stream(CaseStatusEnum.values())
                .map(status -> {
                    CaseStatusEnumVO vo = new CaseStatusEnumVO();
                    vo.setCode(String.valueOf(status.getCode()));
                    vo.setName(status.name());
                    vo.setDescription(status.getDescription());
                    return vo;
                })
                .collect(Collectors.toList());
    }
} 