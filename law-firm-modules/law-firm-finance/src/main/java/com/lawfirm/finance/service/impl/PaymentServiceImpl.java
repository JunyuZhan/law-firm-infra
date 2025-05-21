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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.io.FileWriter;
import java.io.File;
import java.time.format.DateTimeFormatter;

import com.lawfirm.finance.exception.FinanceException;
import com.lawfirm.common.util.excel.ExcelWriter;

/**
 * 付款服务实现类
 */
@Slf4j
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    /**
     * 注入core层审计服务，便于后续记录付款操作日志
     */
    @Autowired(required = false)
    @Qualifier("financeAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续付款相关通知等
     */
    @Autowired(required = false)
    @Qualifier("financeMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续付款附件上传等
     */
    @Autowired(required = false)
    @Qualifier("financeFileService")
    private FileService fileService;

    /**
     * 注入core层存储桶服务，便于后续付款桶管理等
     */
    @Autowired(required = false)
    @Qualifier("financeBucketService")
    private BucketService bucketService;

    /**
     * 注入core层全文检索服务，便于后续付款检索等
     */
    @Autowired(required = false)
    @Qualifier("financeSearchService")
    private SearchService searchService;

    /**
     * 注入core层流程服务，便于后续付款审批流转等
     */
    @Autowired(required = false)
    @Qualifier("financeProcessService")
    private ProcessService processService;

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
        if (paymentIds == null || paymentIds.isEmpty()) {
            throw FinanceException.failed("付款数据导出", new IllegalArgumentException("付款ID列表不能为空"));
        }
        try {
            List<Payment> payments = listByIds(paymentIds);
            if (payments.isEmpty()) {
                throw FinanceException.failed("付款数据导出", new IllegalArgumentException("未找到对应的付款记录"));
            }
            // 导出目录
            String exportDir = "export/finance/payments";
            File dir = new File(exportDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = "payments_" + System.currentTimeMillis() + ".xlsx";
            String filePath = exportDir + "/" + fileName;
            // 使用通用ExcelWriter工具导出为Excel
            ExcelWriter.writeToFile(filePath, payments);
            log.info("付款数据导出成功，文件路径: {}", filePath);
            return filePath;
        } catch (Exception e) {
            log.error("付款数据导出失败", e);
            throw FinanceException.failed("付款数据导出", e);
        }
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