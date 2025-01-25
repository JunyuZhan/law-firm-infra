package com.lawfirm.cases.service.impl;

import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.entity.CaseWorkload;
import com.lawfirm.cases.repository.CaseRepository;
import com.lawfirm.cases.repository.CaseWorkloadRepository;
import com.lawfirm.cases.service.CaseWorkloadService;
import com.lawfirm.common.data.exception.ResourceNotFoundException;
import com.lawfirm.common.data.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseWorkloadServiceImpl implements CaseWorkloadService {

    private final CaseRepository caseRepository;
    private final CaseWorkloadRepository workloadRepository;

    @Override
    @Transactional
    public CaseWorkload recordWorkload(Long caseId, String lawyer, String workType, String workContent,
                                     LocalDateTime startTime, LocalDateTime endTime, String location,
                                     Boolean needReimbursement, BigDecimal reimbursementAmount,
                                     String reimbursementDesc, String remarks) {
        // 验证案件存在
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Case not found with id: %d", caseId)));

        // 验证时间合法性
        if (endTime.isBefore(startTime)) {
            throw new BusinessException("End time cannot be before start time");
        }

        // 计算工作时长
        Duration duration = Duration.between(startTime, endTime);
        BigDecimal hours = BigDecimal.valueOf(duration.toMinutes())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        CaseWorkload workload = new CaseWorkload();
        workload.setCaseId(caseId);
        workload.setLawyer(lawyer);
        workload.setWorkType(workType);
        workload.setWorkContent(workContent);
        workload.setStartTime(startTime);
        workload.setEndTime(endTime);
        workload.setDuration(hours);
        workload.setLocation(location);
        workload.setNeedReimbursement(needReimbursement);
        workload.setReimbursementAmount(reimbursementAmount);
        workload.setReimbursementDesc(reimbursementDesc);
        workload.setRemarks(remarks);

        workloadRepository.save(workload);
        log.info("Workload recorded for case: {}, lawyer: {}, duration: {} hours", 
                caseId, lawyer, hours);
        return workload;
    }

    @Override
    @Transactional
    public void deleteWorkload(Long workloadId) {
        CaseWorkload workload = workloadRepository.findById(workloadId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Workload not found with id: %d", workloadId)));
        workloadRepository.delete(workload);
        log.info("Workload deleted: {}", workloadId);
    }

    @Override
    public List<CaseWorkload> getCaseWorkloads(Long caseId) {
        return workloadRepository.findByCaseIdOrderByStartTimeDesc(caseId);
    }

    @Override
    public List<CaseWorkload> getLawyerWorkloads(String lawyer, LocalDateTime startDate, LocalDateTime endDate) {
        return workloadRepository.findByLawyerAndStartTimeBetweenOrderByStartTimeDesc(
                lawyer, startDate, endDate);
    }

    @Override
    public Map<String, BigDecimal> getLawyerWorkloadStats(String lawyer, LocalDateTime startDate, LocalDateTime endDate) {
        List<CaseWorkload> workloads = getLawyerWorkloads(lawyer, startDate, endDate);
        Map<String, BigDecimal> stats = new HashMap<>();
        
        // 按工作类型统计工时
        workloads.forEach(workload -> {
            stats.merge(workload.getWorkType(), workload.getDuration(), BigDecimal::add);
        });
        
        return stats;
    }

    @Override
    public List<CaseWorkload> getPendingReimbursements(String lawyer) {
        return workloadRepository.findByLawyerAndNeedReimbursementTrueOrderByStartTimeDesc(lawyer);
    }

    @Override
    public BigDecimal getTotalReimbursementAmount(String lawyer, LocalDateTime startDate, LocalDateTime endDate) {
        return workloadRepository.findByLawyerAndStartTimeBetweenAndNeedReimbursementTrue(
                lawyer, startDate, endDate)
                .stream()
                .map(CaseWorkload::getReimbursementAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 