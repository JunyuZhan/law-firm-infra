package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.finance.entity.Payment;
import com.lawfirm.model.finance.enums.PaymentStatusEnum;
import com.lawfirm.model.finance.mapper.PaymentMapper;
import com.lawfirm.model.finance.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 付款服务实现类
 */
@Slf4j
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPayment(Payment payment) {
        log.info("创建付款记录: {}", payment.getPaymentNumber());
        if (payment.getPaymentNumber() == null) {
            payment.setPaymentNumber(generatePaymentNumber());
        }
        if (payment.getPaymentStatus() == null) {
            payment.setPaymentStatus(PaymentStatusEnum.PENDING);
        }
        save(payment);
        return payment.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePayment(Payment payment) {
        log.info("更新付款记录: {}", payment.getId());
        if (payment.getId() == null) {
            return false;
        }
        return updateById(payment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePayment(Long paymentId) {
        log.info("删除付款记录: {}", paymentId);
        return removeById(paymentId);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return getById(paymentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePaymentStatus(Long paymentId, PaymentStatusEnum status, String remark) {
        log.info("更新付款状态: {} -> {}", paymentId, status);
        Payment payment = getById(paymentId);
        if (payment == null) {
            log.error("付款记录不存在: {}", paymentId);
            return false;
        }
        payment.setPaymentStatus(status);
        payment.setDescription(remark);
        return updateById(payment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmPayment(Long paymentId, String remark) {
        log.info("确认付款: {}", paymentId);
        return updatePaymentStatus(paymentId, PaymentStatusEnum.PAID, remark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelPayment(Long paymentId, String reason) {
        log.info("取消付款: {}", paymentId);
        return updatePaymentStatus(paymentId, PaymentStatusEnum.CANCELLED, reason);
    }

    @Override
    public List<Payment> listPayments(PaymentStatusEnum status, Long contractId, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Payment::getPaymentStatus, status);
        }
        
        if (contractId != null) {
            wrapper.eq(Payment::getBillingId, contractId);
        }
        
        if (startTime != null) {
            wrapper.ge(Payment::getPaymentDate, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(Payment::getPaymentDate, endTime);
        }
        
        wrapper.orderByDesc(Payment::getPaymentDate);
        
        return list(wrapper);
    }

    @Override
    public Page<Payment> pagePayments(Page<Payment> page, PaymentStatusEnum status, Long contractId, Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Payment::getPaymentStatus, status);
        }
        
        if (contractId != null) {
            wrapper.eq(Payment::getBillingId, contractId);
        }
        
        if (clientId != null) {
            wrapper.eq(Payment::getClientId, clientId);
        }
        
        if (startTime != null) {
            wrapper.ge(Payment::getPaymentDate, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(Payment::getPaymentDate, endTime);
        }
        
        wrapper.orderByDesc(Payment::getPaymentDate);
        
        return page(page, wrapper);
    }

    @Override
    public Page<Payment> pagePaymentsByContract(Page<Payment> page, Long contractId) {
        if (contractId == null) {
            return new Page<>();
        }
        
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getBillingId, contractId);
        wrapper.orderByDesc(Payment::getPaymentDate);
        
        return page(page, wrapper);
    }

    @Override
    public Page<Payment> pagePaymentsByClient(Page<Payment> page, Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        if (clientId == null) {
            return new Page<>();
        }
        
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getClientId, clientId);
        
        if (startTime != null) {
            wrapper.ge(Payment::getPaymentDate, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(Payment::getPaymentDate, endTime);
        }
        
        wrapper.orderByDesc(Payment::getPaymentDate);
        
        return page(page, wrapper);
    }

    @Override
    public Page<Payment> pagePaymentsByCase(Page<Payment> page, Long caseId) {
        if (caseId == null) {
            return new Page<>();
        }
        
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getCaseId, caseId);
        wrapper.orderByDesc(Payment::getPaymentDate);
        
        return page(page, wrapper);
    }

    @Override
    public BigDecimal sumPaymentAmount(PaymentStatusEnum status, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Payment::getPaymentStatus, status);
        } else {
            wrapper.eq(Payment::getPaymentStatus, PaymentStatusEnum.PAID);
        }
        
        if (startTime != null) {
            wrapper.ge(Payment::getPaymentDate, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(Payment::getPaymentDate, endTime);
        }
        
        List<Payment> payments = list(wrapper);
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumContractPaymentAmount(Long contractId, PaymentStatusEnum status) {
        if (contractId == null) {
            return BigDecimal.ZERO;
        }
        
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getBillingId, contractId);
        
        if (status != null) {
            wrapper.eq(Payment::getPaymentStatus, status);
        } else {
            wrapper.eq(Payment::getPaymentStatus, PaymentStatusEnum.PAID);
        }
        
        List<Payment> payments = list(wrapper);
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumClientPaymentAmount(Long clientId, PaymentStatusEnum status, LocalDateTime startTime, LocalDateTime endTime) {
        if (clientId == null) {
            return BigDecimal.ZERO;
        }
        
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getClientId, clientId);
        
        if (status != null) {
            wrapper.eq(Payment::getPaymentStatus, status);
        } else {
            wrapper.eq(Payment::getPaymentStatus, PaymentStatusEnum.PAID);
        }
        
        if (startTime != null) {
            wrapper.ge(Payment::getPaymentDate, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(Payment::getPaymentDate, endTime);
        }
        
        List<Payment> payments = list(wrapper);
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String exportPayments(List<Long> paymentIds) {
        log.info("导出付款数据: {}", paymentIds);
        // TODO: 实现导出功能
        return "付款数据导出功能尚未实现";
    }
    
    /**
     * 生成付款编号
     * 
     * @return 付款编号
     */
    private String generatePaymentNumber() {
        return "PMT" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16).toUpperCase();
    }
} 