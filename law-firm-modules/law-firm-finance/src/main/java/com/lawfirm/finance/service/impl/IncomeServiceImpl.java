package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.mapper.IncomeMapper;
import com.lawfirm.model.finance.service.ContractIncomeStat;
import com.lawfirm.model.finance.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收入服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeServiceImpl extends BaseServiceImpl<IncomeMapper, Income> implements IncomeService {

    private final SecurityContext securityContext;

    @Override
    @PreAuthorize("hasPermission('income', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "income", allEntries = true)
    public Long recordIncome(Income income) {
        log.info("记录收入: income={}", income);
        income.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        income.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        income.setCreateTime(LocalDateTime.now());
        income.setUpdateTime(LocalDateTime.now());
        save(income);
        return income.getId();
    }

    @Override
    @PreAuthorize("hasPermission('income', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "income", allEntries = true)
    public boolean updateIncome(Income income) {
        log.info("更新收入: income={}", income);
        income.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        income.setUpdateTime(LocalDateTime.now());
        return update(income);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "income", allEntries = true)
    public boolean deleteIncome(Long incomeId) {
        log.info("删除收入: incomeId={}", incomeId);
        return remove(incomeId);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "#incomeId")
    public Income getIncomeById(Long incomeId) {
        log.info("获取收入: incomeId={}", incomeId);
        return getById(incomeId);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'confirm')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "income", allEntries = true)
    public boolean confirmIncome(Long incomeId, Long confirmerId, String remark) {
        log.info("确认收入: incomeId={}, confirmerId={}, remark={}", incomeId, confirmerId, remark);
        Income income = getIncomeById(incomeId);
        if (income == null) {
            return false;
        }
        income.setConfirmStatus(1);
        income.setConfirmerId(confirmerId);
        income.setConfirmTime(LocalDateTime.now());
        income.setConfirmRemark(remark);
        income.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        income.setUpdateTime(LocalDateTime.now());
        return update(income);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'cancel')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "income", allEntries = true)
    public boolean cancelIncome(Long incomeId, String reason) {
        log.info("取消收入: incomeId={}, reason={}", incomeId, reason);
        Income income = getIncomeById(incomeId);
        if (income == null) {
            return false;
        }
        income.setConfirmStatus(2); // 假设2表示取消
        income.setConfirmRemark(reason);
        income.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        income.setUpdateTime(LocalDateTime.now());
        return update(income);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'list:' + #incomeType + ':' + #contractId + ':' + #startTime + ':' + #endTime")
    public List<Income> listIncomes(Integer incomeType, Long contractId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询收入列表: incomeType={}, contractId={}, startTime={}, endTime={}", 
                incomeType, contractId, startTime, endTime);
        LambdaQueryWrapper<Income> wrapper = new LambdaQueryWrapper<>();
        if (incomeType != null) {
            wrapper.eq(Income::getIncomeType, incomeType);
        }
        if (contractId != null) {
            wrapper.eq(Income::getCaseId, contractId);
        }
        if (startTime != null) {
            wrapper.ge(Income::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Income::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Income::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'page:' + #page.current + ':' + #page.size + ':' + #incomeType + ':' + #contractId + ':' + #startTime + ':' + #endTime")
    public IPage<Income> pageIncomes(IPage<Income> page, Integer incomeType, Long contractId, 
                                   LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询收入: page={}, incomeType={}, contractId={}, startTime={}, endTime={}", 
                page, incomeType, contractId, startTime, endTime);
        LambdaQueryWrapper<Income> wrapper = new LambdaQueryWrapper<>();
        if (incomeType != null) {
            wrapper.eq(Income::getIncomeType, incomeType);
        }
        if (contractId != null) {
            wrapper.eq(Income::getCaseId, contractId);
        }
        if (startTime != null) {
            wrapper.ge(Income::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Income::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Income::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'list_by_contract:' + #contractId")
    public List<Income> listIncomesByContract(Long contractId) {
        log.info("按合同查询收入: contractId={}", contractId);
        return list(new LambdaQueryWrapper<Income>()
                .eq(Income::getCaseId, contractId)
                .orderByDesc(Income::getCreateTime));
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'list_by_client:' + #clientId + ':' + #startTime + ':' + #endTime")
    public List<Income> listIncomesByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按客户查询收入: clientId={}, startTime={}, endTime={}", clientId, startTime, endTime);
        LambdaQueryWrapper<Income> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Income::getClientId, clientId);
        if (startTime != null) {
            wrapper.ge(Income::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Income::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Income::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'sum_amount:' + #incomeType + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumIncomeAmount(Integer incomeType, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计收入总额: incomeType={}, startTime={}, endTime={}", 
                incomeType, startTime, endTime);
        List<Income> incomes = listIncomes(incomeType, null, startTime, endTime);
        return incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'sum_contract_amount:' + #contractId")
    public BigDecimal sumContractIncomeAmount(Long contractId) {
        log.info("统计合同收入金额: contractId={}", contractId);
        List<Income> incomes = list(new LambdaQueryWrapper<Income>()
                .eq(Income::getCaseId, contractId));
        return incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'stat_by_month:' + #year + ':' + #month")
    public List<IncomeStat> statisticIncomeByMonth(Integer year, Integer month) {
        log.info("按月统计收入: year={}, month={}", year, month);
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (month != null) {
            YearMonth yearMonth = YearMonth.of(year, month);
            startTime = yearMonth.atDay(1).atStartOfDay();
            endTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        } else {
            startTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
            endTime = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        }
        
        List<Income> incomes = listIncomes(null, null, startTime, endTime);
        Map<YearMonth, List<Income>> groupedIncomes = incomes.stream()
                .collect(Collectors.groupingBy(income -> 
                        YearMonth.from(income.getCreateTime())));
        
        List<IncomeStat> stats = new ArrayList<>();
        groupedIncomes.forEach((yearMonth, monthIncomes) -> {
            IncomeStat stat = new IncomeStat() {
                @Override
                public String getDimension() {
                    return yearMonth.toString();
                }
                
                @Override
                public BigDecimal getAmount() {
                    return monthIncomes.stream()
                            .map(Income::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                
                @Override
                public Integer getCount() {
                    return monthIncomes.size();
                }
            };
            stats.add(stat);
        });
        
        return stats;
    }

    @Override
    @PreAuthorize("hasPermission('income', 'view')")
    @Cacheable(value = "income", key = "'group_by_contract'")
    public List<ContractIncomeStat> statisticIncomeByContract() {
        log.info("按合同统计收入");
        
        // 由于Income实体中没有contractId字段，我们使用caseId代替
        // 此处假设查询逻辑修改为按caseId分组统计
        List<Map<String, Object>> stats = baseMapper.statisticIncomeByCase();
        if (stats == null || stats.isEmpty()) {
            return new ArrayList<>();
        }
        
        return stats.stream()
                .map(map -> {
                    ContractIncomeStat stat = new ContractIncomeStat();
                    stat.setCaseId((Long) map.get("case_id"));
                    stat.setAmount((BigDecimal) map.get("amount"));
                    stat.setCount((Integer) map.get("count"));
                    return stat;
                })
                .collect(Collectors.toList());
    }
}