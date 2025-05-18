package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.common.util.excel.ExcelWriter;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务付款计划服务实现类
 */
@Slf4j
@Service("financePaymentPlanServiceImpl")
@RequiredArgsConstructor
public class PaymentPlanServiceImpl extends BaseServiceImpl<PaymentPlanMapper, PaymentPlan> implements PaymentPlanService {

    private final SecurityContext securityContext;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        
        // 查询付款计划记录
        List<PaymentPlan> plans;
        if (paymentPlanIds != null && !paymentPlanIds.isEmpty()) {
            plans = listByIds(paymentPlanIds);
        } else {
            // 如果没有指定ID，则获取所有记录
            plans = list();
        }
        
        if (plans.isEmpty()) {
            log.warn("没有找到要导出的付款计划");
            return null;
        }
        
        // 准备Excel数据
        List<List<String>> excelData = new ArrayList<>();
        
        // 添加表头
        List<String> header = new ArrayList<>();
        header.add("计划ID");
        header.add("计划编号");
        header.add("计划名称");
        header.add("总金额");
        header.add("币种");
        header.add("已付金额");
        header.add("未付金额");
        header.add("状态");
        header.add("付款期数");
        header.add("已付期数");
        header.add("开始日期");
        header.add("结束日期");
        header.add("付款周期");
        header.add("付款日");
        header.add("客户ID");
        header.add("案件ID");
        header.add("创建时间");
        header.add("更新时间");
        header.add("说明");
        excelData.add(header);
        
        // 添加数据行
        for (PaymentPlan plan : plans) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(plan.getId()));
            row.add(plan.getPlanNumber());
            row.add(plan.getPlanName());
            row.add(plan.getTotalAmount() != null ? plan.getTotalAmount().toString() : "0");
            row.add(plan.getCurrency() != null ? plan.getCurrency().toString() : "");
            row.add(plan.getPaidAmount() != null ? plan.getPaidAmount().toString() : "0");
            row.add(plan.getUnpaidAmount() != null ? plan.getUnpaidAmount().toString() : "0");
            row.add(PaymentPlanStatusEnum.getByCode(plan.getStatus()).getDesc());
            row.add(String.valueOf(plan.getInstallments()));
            row.add(String.valueOf(plan.getPaidInstallments()));
            row.add(plan.getStartDate() != null ? plan.getStartDate().format(DATE_FORMATTER) : "");
            row.add(plan.getEndDate() != null ? plan.getEndDate().format(DATE_FORMATTER) : "");
            
            // 付款周期格式化
            String paymentCycle = "";
            if (plan.getPaymentCycle() != null) {
                switch (plan.getPaymentCycle()) {
                    case 1: paymentCycle = "按月"; break;
                    case 2: paymentCycle = "按季度"; break;
                    case 3: paymentCycle = "按年"; break;
                    case 4: paymentCycle = "自定义"; break;
                    default: paymentCycle = String.valueOf(plan.getPaymentCycle());
                }
            }
            row.add(paymentCycle);
            
            row.add(plan.getPaymentDay() != null ? String.valueOf(plan.getPaymentDay()) : "");
            row.add(String.valueOf(plan.getClientId()));
            row.add(plan.getCaseId() != null ? String.valueOf(plan.getCaseId()) : "");
            row.add(plan.getCreateTime() != null ? plan.getCreateTime().format(DATE_FORMATTER) : "");
            row.add(plan.getUpdateTime() != null ? plan.getUpdateTime().format(DATE_FORMATTER) : "");
            row.add(plan.getDescription() != null ? plan.getDescription() : "");
            excelData.add(row);
        }
        
        // 生成临时文件名
        String fileName = "payment_plans_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        
        try {
            // 将数据写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ExcelWriter.write(excelData, outputStream);
            outputStream.close();
            
            // 返回文件路径
            return filePath;
        } catch (IOException e) {
            log.error("导出付款计划失败", e);
            return null;
        }
    }
}