package com.lawfirm.contract.task;

import com.lawfirm.contract.service.ContractStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 合同统计缓存预热任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContractStatisticsCacheTask {

    private final ContractStatisticsService contractStatisticsService;

    /**
     * 每天凌晨2点执行缓存预热
     */
    @Async
    @Scheduled(cron = "0 0 2 * * ?")
    public void warmupCache() {
        log.info("开始执行合同统计缓存预热任务");
        try {
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusYears(1);

            // 预热各维度的统计数据
            contractStatisticsService.countContracts(startTime, endTime, null, null);
            contractStatisticsService.sumContractAmount(startTime, endTime, null, null);
            contractStatisticsService.countContractsByType(startTime, endTime);
            contractStatisticsService.countContractsByStatus(startTime, endTime);
            contractStatisticsService.countContractsByLawyer(startTime, endTime);
            contractStatisticsService.sumContractAmountByLawyer(startTime, endTime);
            contractStatisticsService.countContractsByDepartment(startTime, endTime);
            contractStatisticsService.sumContractAmountByDepartment(startTime, endTime);
            contractStatisticsService.countContractsByBranch(startTime, endTime);
            contractStatisticsService.sumContractAmountByBranch(startTime, endTime);
            contractStatisticsService.countContractsByClient(startTime, endTime);
            contractStatisticsService.sumContractAmountByClient(startTime, endTime);
            
            // 预热按月统计数据
            int currentYear = endTime.getYear();
            contractStatisticsService.countContractsByMonth(currentYear, null);
            contractStatisticsService.sumContractAmountByMonth(currentYear, null);

            // 预热按签订时间统计数据
            contractStatisticsService.countContractsBySignDate(startTime, endTime, 7);
            contractStatisticsService.sumContractAmountBySignDate(startTime, endTime, 7);

            log.info("合同统计缓存预热任务执行完成");
        } catch (Exception e) {
            log.error("合同统计缓存预热任务执行失败", e);
        }
    }
} 