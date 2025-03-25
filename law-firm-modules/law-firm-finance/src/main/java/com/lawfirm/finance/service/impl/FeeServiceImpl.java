package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Fee;
import com.lawfirm.model.finance.enums.FeeStatusEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import com.lawfirm.model.finance.mapper.FeeMapper;
import com.lawfirm.model.finance.service.FeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用服务实现类
 */
@Slf4j
@Service("financeServiceImpl")
@RequiredArgsConstructor
public class FeeServiceImpl extends BaseServiceImpl<FeeMapper, Fee> implements FeeService {

    private final SecurityContext securityContext;

    @Override
    @PreAuthorize("hasPermission('fee', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public Long createFee(Fee fee) {
        log.info("创建费用: fee={}", fee);
        fee.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setCreateTime(LocalDateTime.now());
        fee.setUpdateTime(LocalDateTime.now());
        save(fee);
        return fee.getId();
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean updateFee(Fee fee) {
        log.info("更新费用: fee={}", fee);
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean deleteFee(Long feeId) {
        log.info("删除费用: feeId={}", feeId);
        return remove(feeId);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "#feeId")
    public Fee getFeeById(Long feeId) {
        log.info("获取费用: feeId={}", feeId);
        return getById(feeId);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean updateFeeStatus(Long feeId, FeeStatusEnum status, String remark) {
        log.info("更新费用状态: feeId={}, status={}, remark={}", feeId, status, remark);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(remark);
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'approve')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean approveFee(Long feeId, boolean approved, Long approverId, String remark) {
        log.info("审批费用: feeId={}, approved={}, approverId={}, remark={}", feeId, approved, approverId, remark);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(remark + "(由ID:" + approverId + "审批)");
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'pay')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean payFee(Long feeId, Long accountId, LocalDateTime paymentTime, String remark) {
        log.info("支付费用: feeId={}, accountId={}, paymentTime={}, remark={}", feeId, accountId, paymentTime, remark);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(remark + "(支付时间:" + paymentTime + ",账户ID:" + accountId + ")");
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'cancel')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean cancelFee(Long feeId, String reason) {
        log.info("取消费用: feeId={}, reason={}", feeId, reason);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(reason + "(已取消)");
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list:' + #feeType + ':' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public List<Fee> listFees(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                             LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询费用列表: feeType={}, status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                feeType, status, departmentId, costCenterId, startTime, endTime);
        LambdaQueryWrapper<Fee> wrapper = new LambdaQueryWrapper<>();
        if (feeType != null) {
            wrapper.eq(Fee::getFeeType, feeType);
        }
        if (departmentId != null) {
            wrapper.eq(Fee::getDepartmentId, departmentId);
        }
        if (startTime != null) {
            wrapper.ge(Fee::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Fee::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Fee::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'page:' + #page.current + ':' + #page.size + ':' + #feeType + ':' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public IPage<Fee> pageFees(IPage<Fee> page, FeeTypeEnum feeType, FeeStatusEnum status,
                              Long departmentId, Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询费用: page={}, feeType={}, status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                page, feeType, status, departmentId, costCenterId, startTime, endTime);
        LambdaQueryWrapper<Fee> wrapper = new LambdaQueryWrapper<>();
        if (feeType != null) {
            wrapper.eq(Fee::getFeeType, feeType);
        }
        if (departmentId != null) {
            wrapper.eq(Fee::getDepartmentId, departmentId);
        }
        if (startTime != null) {
            wrapper.ge(Fee::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Fee::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Fee::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list_by_department:' + #departmentId + ':' + #startTime + ':' + #endTime")
    public List<Fee> listFeesByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按部门查询费用: departmentId={}, startTime={}, endTime={}", departmentId, startTime, endTime);
        return listFees(null, null, departmentId, null, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list_by_cost_center:' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public List<Fee> listFeesByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按成本中心查询费用: costCenterId={}, startTime={}, endTime={}", costCenterId, startTime, endTime);
        return java.util.Collections.emptyList();
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list_by_case:' + #caseId")
    public List<Fee> listFeesByCase(Long caseId) {
        log.info("按案件查询费用: caseId={}", caseId);
        return list(new LambdaQueryWrapper<Fee>()
                .eq(Fee::getCaseId, caseId)
                .orderByDesc(Fee::getCreateTime));
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_amount:' + #feeType + ':' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumFeeAmount(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                                 LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计费用金额: feeType={}, status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                feeType, status, departmentId, costCenterId, startTime, endTime);
        List<Fee> fees = listFees(feeType, status, departmentId, costCenterId, startTime, endTime);
        return fees.stream()
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_department_amount:' + #departmentId + ':' + #feeType + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumDepartmentFeeAmount(Long departmentId, FeeTypeEnum feeType, FeeStatusEnum status,
                                           LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计部门费用金额: departmentId={}, feeType={}, status={}, startTime={}, endTime={}", 
                departmentId, feeType, status, startTime, endTime);
        return sumFeeAmount(feeType, status, departmentId, null, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_cost_center_amount:' + #costCenterId + ':' + #feeType + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumCostCenterFeeAmount(Long costCenterId, FeeTypeEnum feeType, FeeStatusEnum status,
                                           LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计成本中心费用金额: costCenterId={}, feeType={}, status={}, startTime={}, endTime={}", 
                costCenterId, feeType, status, startTime, endTime);
        return BigDecimal.ZERO;
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_case_amount:' + #caseId + ':' + #feeType + ':' + #status")
    public BigDecimal sumCaseFeeAmount(Long caseId, FeeTypeEnum feeType, FeeStatusEnum status) {
        log.info("统计案件费用金额: caseId={}, feeType={}, status={}", caseId, feeType, status);
        
        // 构建查询条件
        LambdaQueryWrapper<Fee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Fee::getCaseId, caseId);
        if (feeType != null) {
            wrapper.eq(Fee::getFeeType, feeType);
        }
        // 由于Fee实体中没有status字段，这里忽略status条件
        
        // 执行查询并汇总金额
        List<Fee> fees = list(wrapper);
        return fees.stream()
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'export')")
    public String exportFees(List<Long> feeIds) {
        log.info("导出费用数据: feeIds={}", feeIds);
        // TODO: 实现导出功能
        return null;
    }
}