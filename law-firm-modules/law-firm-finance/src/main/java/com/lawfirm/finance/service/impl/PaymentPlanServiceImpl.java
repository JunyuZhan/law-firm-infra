package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.enums.PaymentPlanStatusEnum;
import com.lawfirm.model.finance.mapper.PaymentPlanMapper;
import com.lawfirm.model.finance.service.PaymentPlanService;
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
 * 财务付款计划服务实现类
 */
@Slf4j
@Service("financePaymentPlanServiceImpl")
@RequiredArgsConstructor
public class PaymentPlanServiceImpl extends BaseServiceImpl<PaymentPlanMapper, PaymentPlan> implements PaymentPlanService {

    private final SecurityContext securityContext;

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "payment_plan", allEntries = true)
    public Long createPaymentPlan(PaymentPlan paymentPlan) {
        log.info("创建付款计划: paymentPlan={}", paymentPlan);
        paymentPlan.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        paymentPlan.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        paymentPlan.setCreateTime(LocalDateTime.now());
        paymentPlan.setUpdateTime(LocalDateTime.now());
        save(paymentPlan);
        return paymentPlan.getId();
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "payment_plan", allEntries = true)
    public boolean updatePaymentPlan(PaymentPlan paymentPlan) {
        log.info("更新付款计划: paymentPlan={}", paymentPlan);
        paymentPlan.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        paymentPlan.setUpdateTime(LocalDateTime.now());
        return update(paymentPlan);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "payment_plan", allEntries = true)
    public boolean deletePaymentPlan(Long paymentPlanId) {
        log.info("删除付款计划: paymentPlanId={}", paymentPlanId);
        return remove(paymentPlanId);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'view')")
    @Cacheable(value = "payment_plan", key = "#paymentPlanId")
    public PaymentPlan getPaymentPlanById(Long paymentPlanId) {
        log.info("获取付款计划: paymentPlanId={}", paymentPlanId);
        return getById(paymentPlanId);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "payment_plan", allEntries = true)
    public boolean updatePaymentPlanStatus(Long paymentPlanId, PaymentPlanStatusEnum status, String remark) {
        log.info("更新付款计划状态: paymentPlanId={}, status={}, remark={}", paymentPlanId, status, remark);
        PaymentPlan paymentPlan = getPaymentPlanById(paymentPlanId);
        if (paymentPlan == null) {
            return false;
        }
        paymentPlan.setStatus(status.getCode());
        paymentPlan.setRemark(remark);
        paymentPlan.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        paymentPlan.setUpdateTime(LocalDateTime.now());
        return update(paymentPlan);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'confirm')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "payment_plan", allEntries = true)
    public boolean confirmPayment(Long paymentPlanId, BigDecimal actualAmount, LocalDateTime paymentTime,
                                Long operatorId, String remark) {
        log.info("确认付款: paymentPlanId={}, actualAmount={}, paymentTime={}, operatorId={}, remark={}", 
                paymentPlanId, actualAmount, paymentTime, operatorId, remark);
        PaymentPlan paymentPlan = getPaymentPlanById(paymentPlanId);
        if (paymentPlan == null) {
            return false;
        }
        paymentPlan.setStatus(PaymentPlanStatusEnum.PAID.getCode());
        paymentPlan.setRemark(remark);
        paymentPlan.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        paymentPlan.setUpdateTime(LocalDateTime.now());
        return update(paymentPlan);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'cancel')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "payment_plan", allEntries = true)
    public boolean cancelPaymentPlan(Long paymentPlanId, String reason) {
        log.info("取消付款计划: paymentPlanId={}, reason={}", paymentPlanId, reason);
        PaymentPlan paymentPlan = getPaymentPlanById(paymentPlanId);
        if (paymentPlan == null) {
            return false;
        }
        paymentPlan.setStatus(PaymentPlanStatusEnum.CANCELLED.getCode());
        paymentPlan.setRemark(reason);
        paymentPlan.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        paymentPlan.setUpdateTime(LocalDateTime.now());
        return update(paymentPlan);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'view')")
    @Cacheable(value = "payment_plan", key = "'list:' + #status + ':' + #contractId + ':' + #startTime + ':' + #endTime")
    public List<PaymentPlan> listPaymentPlans(PaymentPlanStatusEnum status, Long contractId,
                                            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询付款计划列表: status={}, contractId={}, startTime={}, endTime={}", 
                status, contractId, startTime, endTime);
        LambdaQueryWrapper<PaymentPlan> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(PaymentPlan::getStatus, status.getCode());
        }
        if (contractId != null) {
            wrapper.eq(PaymentPlan::getCaseId, contractId);
        }
        if (startTime != null) {
            wrapper.ge(PaymentPlan::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(PaymentPlan::getCreateTime, endTime);
        }
        wrapper.orderByDesc(PaymentPlan::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'view')")
    @Cacheable(value = "payment_plan", key = "'page:' + #page.current + ':' + #page.size + ':' + #status + ':' + #contractId + ':' + #startTime + ':' + #endTime")
    public IPage<PaymentPlan> pagePaymentPlans(IPage<PaymentPlan> page, PaymentPlanStatusEnum status,
                                             Long contractId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询付款计划: page={}, status={}, contractId={}, startTime={}, endTime={}", 
                page, status, contractId, startTime, endTime);
        LambdaQueryWrapper<PaymentPlan> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(PaymentPlan::getStatus, status.getCode());
        }
        if (contractId != null) {
            wrapper.eq(PaymentPlan::getCaseId, contractId);
        }
        if (startTime != null) {
            wrapper.ge(PaymentPlan::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(PaymentPlan::getCreateTime, endTime);
        }
        wrapper.orderByDesc(PaymentPlan::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'view')")
    @Cacheable(value = "payment_plan", key = "'list_by_contract:' + #contractId")
    public List<PaymentPlan> listPaymentPlansByContract(Long contractId) {
        log.info("按合同查询付款计划: contractId={}", contractId);
        return list(new LambdaQueryWrapper<PaymentPlan>()
                .eq(PaymentPlan::getCaseId, contractId)
                .orderByDesc(PaymentPlan::getCreateTime));
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'view')")
    @Cacheable(value = "payment_plan", key = "'list_by_client:' + #clientId + ':' + #startTime + ':' + #endTime")
    public List<PaymentPlan> listPaymentPlansByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按客户查询付款计划: clientId={}, startTime={}, endTime={}", clientId, startTime, endTime);
        LambdaQueryWrapper<PaymentPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentPlan::getClientId, clientId);
        if (startTime != null) {
            wrapper.ge(PaymentPlan::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(PaymentPlan::getCreateTime, endTime);
        }
        wrapper.orderByDesc(PaymentPlan::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'view')")
    @Cacheable(value = "payment_plan", key = "'sum_amount:' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumPaymentPlanAmount(PaymentPlanStatusEnum status, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计付款计划金额: status={}, startTime={}, endTime={}", status, startTime, endTime);
        List<PaymentPlan> paymentPlans = listPaymentPlans(status, null, startTime, endTime);
        return paymentPlans.stream()
                .map(PaymentPlan::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'view')")
    @Cacheable(value = "payment_plan", key = "'sum_contract_amount:' + #contractId + ':' + #status")
    public BigDecimal sumContractPaymentPlanAmount(Long contractId, PaymentPlanStatusEnum status) {
        log.info("统计合同付款计划金额: contractId={}, status={}", contractId, status);
        List<PaymentPlan> paymentPlans = listPaymentPlans(status, contractId, null, null);
        return paymentPlans.stream()
                .map(PaymentPlan::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('payment_plan', 'export')")
    public String exportPaymentPlans(List<Long> paymentPlanIds) {
        log.info("导出付款计划数据: paymentPlanIds={}", paymentPlanIds);
        // TODO: 实现导出功能
        return null;
    }
}