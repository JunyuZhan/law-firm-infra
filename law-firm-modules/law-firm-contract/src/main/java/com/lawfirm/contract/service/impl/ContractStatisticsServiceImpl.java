package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.mapper.ContractMapper;
import com.lawfirm.contract.service.ContractStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 合同统计服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractStatisticsServiceImpl implements ContractStatisticsService {

    private final ContractMapper contractMapper;

    @Override
    @Cacheable(value = "contract:count", key = "#startTime + '_' + #endTime + '_' + #type + '_' + #status")
    public Long countContracts(LocalDateTime startTime, LocalDateTime endTime, Integer type, Integer status) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime)
                .eq(type != null, Contract::getType, type)
                .eq(status != null, Contract::getStatus, status);
        return contractMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(value = "contract:amount", key = "#startTime + '_' + #endTime + '_' + #type + '_' + #status")
    public BigDecimal sumContractAmount(LocalDateTime startTime, LocalDateTime endTime, Integer type, Integer status) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime)
                .eq(type != null, Contract::getType, type)
                .eq(status != null, Contract::getStatus, status);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .map(Contract::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Cacheable(value = "contract:type", key = "#startTime + '_' + #endTime")
    public Map<Integer, Long> countContractsByType(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getType, Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:status", key = "#startTime + '_' + #endTime")
    public Map<Integer, Long> countContractsByStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getStatus, Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:lawyer:count", key = "#startTime + '_' + #endTime")
    public Map<Long, Long> countContractsByLawyer(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getLawyerId, Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:lawyer:amount", key = "#startTime + '_' + #endTime")
    public Map<Long, BigDecimal> sumContractAmountByLawyer(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getLawyerId,
                        Collectors.mapping(Contract::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    @Cacheable(value = "contract:department:count", key = "#startTime + '_' + #endTime")
    public Map<Long, Long> countContractsByDepartment(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getDepartmentId, Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:department:amount", key = "#startTime + '_' + #endTime")
    public Map<Long, BigDecimal> sumContractAmountByDepartment(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getDepartmentId,
                        Collectors.mapping(Contract::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    @Cacheable(value = "contract:branch:count", key = "#startTime + '_' + #endTime")
    public Map<Long, Long> countContractsByBranch(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getBranchId, Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:branch:amount", key = "#startTime + '_' + #endTime")
    public Map<Long, BigDecimal> sumContractAmountByBranch(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getBranchId,
                        Collectors.mapping(Contract::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    @Cacheable(value = "contract:month:count", key = "#year + '_' + #type")
    public Map<Integer, Long> countContractsByMonth(Integer year, Integer type) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Contract::getCreateTime, startTime)
                .le(Contract::getCreateTime, endTime)
                .eq(type != null, Contract::getType, type);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(contract -> contract.getCreateTime().getMonthValue(),
                        Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:month:amount", key = "#year + '_' + #type")
    public Map<Integer, BigDecimal> sumContractAmountByMonth(Integer year, Integer type) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Contract::getCreateTime, startTime)
                .le(Contract::getCreateTime, endTime)
                .eq(type != null, Contract::getType, type);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(contract -> contract.getCreateTime().getMonthValue(),
                        Collectors.mapping(Contract::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    @Cacheable(value = "contract:client:count", key = "#startTime + '_' + #endTime")
    public Map<Long, Long> countContractsByClient(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getClientId, Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:client:amount", key = "#startTime + '_' + #endTime")
    public Map<Long, BigDecimal> sumContractAmountByClient(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getCreateTime, startTime)
                .le(endTime != null, Contract::getCreateTime, endTime);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        return contracts.stream()
                .collect(Collectors.groupingBy(Contract::getClientId,
                        Collectors.mapping(Contract::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    @Cacheable(value = "contract:signdate:count", key = "#startTime + '_' + #endTime + '_' + #interval")
    public Map<LocalDateTime, Long> countContractsBySignDate(LocalDateTime startTime, LocalDateTime endTime, Integer interval) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getSignDate, startTime)
                .le(endTime != null, Contract::getSignDate, endTime)
                .orderByAsc(Contract::getSignDate);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        
        // 按时间间隔分组
        return contracts.stream()
                .collect(Collectors.groupingBy(contract -> {
                    LocalDateTime signDate = contract.getSignDate();
                    return signDate.minusNanos(signDate.getNano())
                            .minusSeconds(signDate.getSecond())
                            .minusMinutes(signDate.getMinute())
                            .minusHours(signDate.getHour())
                            .minusDays(signDate.getDayOfMonth() % interval);
                }, Collectors.counting()));
    }

    @Override
    @Cacheable(value = "contract:signdate:amount", key = "#startTime + '_' + #endTime + '_' + #interval")
    public Map<LocalDateTime, BigDecimal> sumContractAmountBySignDate(LocalDateTime startTime, LocalDateTime endTime, Integer interval) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(startTime != null, Contract::getSignDate, startTime)
                .le(endTime != null, Contract::getSignDate, endTime)
                .orderByAsc(Contract::getSignDate);
        List<Contract> contracts = contractMapper.selectList(wrapper);
        
        // 按时间间隔分组
        return contracts.stream()
                .collect(Collectors.groupingBy(contract -> {
                    LocalDateTime signDate = contract.getSignDate();
                    return signDate.minusNanos(signDate.getNano())
                            .minusSeconds(signDate.getSecond())
                            .minusMinutes(signDate.getMinute())
                            .minusHours(signDate.getHour())
                            .minusDays(signDate.getDayOfMonth() % interval);
                }, Collectors.mapping(Contract::getAmount,
                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }
} 