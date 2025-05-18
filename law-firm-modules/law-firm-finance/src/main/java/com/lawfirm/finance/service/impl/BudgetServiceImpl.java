package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.common.util.excel.ExcelWriter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Budget;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.mapper.BudgetMapper;
import com.lawfirm.model.finance.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务预算服务实现类
 */
@Slf4j
@Service("financeBudgetServiceImpl")
@RequiredArgsConstructor
public class BudgetServiceImpl extends BaseServiceImpl<BudgetMapper, Budget> implements BudgetService {

    private final SecurityContext securityContext;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @PreAuthorize("hasPermission('budget', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "budget", allEntries = true)
    public Long createBudget(Budget budget) {
        log.info("创建预算: budget={}", budget);
        budget.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        budget.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        budget.setCreateTime(LocalDateTime.now());
        budget.setUpdateTime(LocalDateTime.now());
        save(budget);
        return budget.getId();
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "budget", allEntries = true)
    public boolean updateBudget(Budget budget) {
        log.info("更新预算: budget={}", budget);
        budget.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        budget.setUpdateTime(LocalDateTime.now());
        return update(budget);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "budget", allEntries = true)
    public boolean deleteBudget(Long budgetId) {
        log.info("删除预算: budgetId={}", budgetId);
        return remove(budgetId);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "#budgetId")
    public Budget getBudgetById(Long budgetId) {
        log.info("获取预算: budgetId={}", budgetId);
        return getById(budgetId);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "budget", allEntries = true)
    public boolean updateBudgetStatus(Long budgetId, BudgetStatusEnum status, String remark) {
        log.info("更新预算状态: budgetId={}, status={}, remark={}", budgetId, status, remark);
        Budget budget = getBudgetById(budgetId);
        if (budget == null) {
            return false;
        }
        budget.setBudgetStatus(status);
        budget.setDescription(remark);
        budget.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        budget.setUpdateTime(LocalDateTime.now());
        return update(budget);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'approve')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "budget", allEntries = true)
    public boolean approveBudget(Long budgetId, boolean approved, Long approverId, String remark) {
        log.info("审批预算: budgetId={}, approved={}, approverId={}, remark={}", budgetId, approved, approverId, remark);
        Budget budget = getBudgetById(budgetId);
        if (budget == null) {
            return false;
        }
        budget.setBudgetStatus(approved ? BudgetStatusEnum.APPROVED : BudgetStatusEnum.REJECTED);
        budget.setDescription(remark);
        budget.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        budget.setUpdateTime(LocalDateTime.now());
        return update(budget);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'adjust')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "budget", allEntries = true)
    public boolean adjustBudget(Long budgetId, BigDecimal amount, Long operatorId, String remark) {
        log.info("调整预算: budgetId={}, amount={}, operatorId={}, remark={}", budgetId, amount, operatorId, remark);
        Budget budget = getBudgetById(budgetId);
        if (budget == null) {
            return false;
        }
        budget.setAmount(budget.getAmount().add(amount));
        budget.setDescription(remark);
        budget.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        budget.setUpdateTime(LocalDateTime.now());
        return update(budget);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "'list:' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public List<Budget> listBudgets(BudgetStatusEnum status, Long departmentId, Long costCenterId,
                                  LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询预算列表: status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                status, departmentId, costCenterId, startTime, endTime);
        LambdaQueryWrapper<Budget> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Budget::getBudgetStatus, status);
        }
        if (departmentId != null) {
            wrapper.eq(Budget::getDepartmentId, departmentId);
        }
        if (costCenterId != null) {
            wrapper.eq(Budget::getCostCenterId, costCenterId);
        }
        if (startTime != null) {
            wrapper.ge(Budget::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Budget::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Budget::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "'page:' + #page.current + ':' + #page.size + ':' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public IPage<Budget> pageBudgets(IPage<Budget> page, BudgetStatusEnum status, Long departmentId,
                                   Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询预算: page={}, status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                page, status, departmentId, costCenterId, startTime, endTime);
        LambdaQueryWrapper<Budget> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Budget::getBudgetStatus, status);
        }
        if (departmentId != null) {
            wrapper.eq(Budget::getDepartmentId, departmentId);
        }
        if (costCenterId != null) {
            wrapper.eq(Budget::getCostCenterId, costCenterId);
        }
        if (startTime != null) {
            wrapper.ge(Budget::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Budget::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Budget::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "'list_by_department:' + #departmentId + ':' + #startTime + ':' + #endTime")
    public List<Budget> listBudgetsByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按部门查询预算: departmentId={}, startTime={}, endTime={}", departmentId, startTime, endTime);
        return listBudgets(null, departmentId, null, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "'list_by_cost_center:' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public List<Budget> listBudgetsByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按成本中心查询预算: costCenterId={}, startTime={}, endTime={}", costCenterId, startTime, endTime);
        return listBudgets(null, null, costCenterId, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "'sum_amount:' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumBudgetAmount(BudgetStatusEnum status, Long departmentId, Long costCenterId,
                                    LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计预算金额: status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                status, departmentId, costCenterId, startTime, endTime);
        List<Budget> budgets = listBudgets(status, departmentId, costCenterId, startTime, endTime);
        return budgets.stream()
                .map(Budget::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "'sum_department_amount:' + #departmentId + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumDepartmentBudgetAmount(Long departmentId, BudgetStatusEnum status,
                                              LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计部门预算金额: departmentId={}, status={}, startTime={}, endTime={}", 
                departmentId, status, startTime, endTime);
        return sumBudgetAmount(status, departmentId, null, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'view')")
    @Cacheable(value = "budget", key = "'sum_cost_center_amount:' + #costCenterId + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumCostCenterBudgetAmount(Long costCenterId, BudgetStatusEnum status,
                                              LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计成本中心预算金额: costCenterId={}, status={}, startTime={}, endTime={}", 
                costCenterId, status, startTime, endTime);
        return sumBudgetAmount(status, null, costCenterId, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('budget', 'export')")
    public String exportBudgets(List<Long> budgetIds) {
        log.info("导出预算数据: budgetIds={}", budgetIds);
        
        // 查询预算记录
        List<Budget> budgets;
        if (budgetIds != null && !budgetIds.isEmpty()) {
            budgets = listByIds(budgetIds);
        } else {
            // 如果没有指定ID，则获取所有记录
            budgets = list();
        }
        
        if (budgets.isEmpty()) {
            log.warn("没有找到要导出的预算记录");
            return null;
        }
        
        // 准备Excel数据
        List<List<String>> excelData = new ArrayList<>();
        
        // 添加表头
        List<String> header = new ArrayList<>();
        header.add("预算ID");
        header.add("预算编号");
        header.add("预算名称");
        header.add("预算类型");
        header.add("预算状态");
        header.add("预算金额");
        header.add("已使用金额");
        header.add("剩余金额");
        header.add("币种");
        header.add("开始时间");
        header.add("结束时间");
        header.add("部门ID");
        header.add("成本中心ID");
        header.add("预算说明");
        header.add("创建时间");
        header.add("更新时间");
        excelData.add(header);
        
        // 添加数据行
        for (Budget budget : budgets) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(budget.getId()));
            row.add(budget.getBudgetNumber());
            row.add(budget.getBudgetName());
            row.add(budget.getBudgetType() != null ? budget.getBudgetType().toString() : "");
            row.add(budget.getBudgetStatus() != null ? budget.getBudgetStatus().toString() : "");
            row.add(budget.getAmount() != null ? budget.getAmount().toString() : "0");
            row.add(budget.getUsedAmount() != null ? budget.getUsedAmount().toString() : "0");
            row.add(budget.getRemainingAmount() != null ? budget.getRemainingAmount().toString() : "0");
            row.add(budget.getCurrency() != null ? budget.getCurrency().toString() : "");
            row.add(budget.getStartTime() != null ? budget.getStartTime().format(DATE_FORMATTER) : "");
            row.add(budget.getEndTime() != null ? budget.getEndTime().format(DATE_FORMATTER) : "");
            row.add(budget.getDepartmentId() != null ? String.valueOf(budget.getDepartmentId()) : "");
            row.add(budget.getCostCenterId() != null ? String.valueOf(budget.getCostCenterId()) : "");
            row.add(budget.getDescription() != null ? budget.getDescription() : "");
            row.add(budget.getCreateTime() != null ? budget.getCreateTime().format(DATE_FORMATTER) : "");
            row.add(budget.getUpdateTime() != null ? budget.getUpdateTime().format(DATE_FORMATTER) : "");
            excelData.add(row);
        }
        
        // 生成临时文件名
        String fileName = "budgets_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        
        try {
            // 将数据写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ExcelWriter.write(excelData, outputStream);
            outputStream.close();
            
            // 返回文件路径
            return filePath;
        } catch (IOException e) {
            log.error("导出预算记录失败", e);
            return null;
        }
    }
} 