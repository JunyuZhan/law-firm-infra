package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import com.lawfirm.model.finance.mapper.InvoiceMapper;
import com.lawfirm.model.finance.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.finance.exception.FinanceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 财务发票服务实现类
 */
@Slf4j
@Service("financeInvoiceServiceImpl")
@RequiredArgsConstructor
public class InvoiceServiceImpl extends BaseServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    private final SecurityContext securityContext;

    @Override
    @PreAuthorize("hasPermission('invoice', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "invoice", allEntries = true)
    public Long createInvoice(Invoice invoice) {
        log.info("创建发票: invoice={}", invoice);
        invoice.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setCreateTime(LocalDateTime.now());
        invoice.setUpdateTime(LocalDateTime.now());
        save(invoice);
        return invoice.getId();
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "invoice", allEntries = true)
    public boolean updateInvoice(Invoice invoice) {
        log.info("更新发票: invoice={}", invoice);
        invoice.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setUpdateTime(LocalDateTime.now());
        return update(invoice);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "invoice", allEntries = true)
    public boolean deleteInvoice(Long invoiceId) {
        log.info("删除发票: invoiceId={}", invoiceId);
        return remove(invoiceId);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'view')")
    @Cacheable(value = "invoice", key = "#invoiceId")
    public Invoice getInvoiceById(Long invoiceId) {
        log.info("获取发票: invoiceId={}", invoiceId);
        return getById(invoiceId);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "invoice", allEntries = true)
    public boolean updateInvoiceStatus(Long invoiceId, InvoiceStatusEnum status, String remark) {
        log.info("更新发票状态: invoiceId={}, status={}, remark={}", invoiceId, status, remark);
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice == null) {
            return false;
        }
        invoice.setInvoiceStatus(status);
        invoice.setRemark(remark);
        invoice.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setUpdateTime(LocalDateTime.now());
        return update(invoice);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'confirm')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "invoice", allEntries = true)
    public boolean confirmInvoice(Long invoiceId, String invoiceNo, LocalDateTime invoiceDate, Long operatorId, String remark) {
        log.info("确认发票: invoiceId={}, invoiceNo={}, invoiceDate={}, operatorId={}, remark={}", 
                invoiceId, invoiceNo, invoiceDate, operatorId, remark);
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice == null) {
            return false;
        }
        invoice.setInvoiceStatus(InvoiceStatusEnum.ISSUED);
        invoice.setRemark(remark);
        invoice.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setUpdateTime(LocalDateTime.now());
        return update(invoice);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'cancel')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "invoice", allEntries = true)
    public boolean cancelInvoice(Long invoiceId, String reason) {
        log.info("取消发票: invoiceId={}, reason={}", invoiceId, reason);
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice == null) {
            return false;
        }
        invoice.setInvoiceStatus(InvoiceStatusEnum.CANCELLED);
        invoice.setRemark(reason);
        invoice.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setUpdateTime(LocalDateTime.now());
        return update(invoice);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'view')")
    @Cacheable(value = "invoice", key = "'list:' + #invoiceType + ':' + #status + ':' + #contractId + ':' + #startTime + ':' + #endTime")
    public List<Invoice> listInvoices(InvoiceTypeEnum invoiceType, InvoiceStatusEnum status, Long contractId,
                                    LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询发票列表: invoiceType={}, status={}, contractId={}, startTime={}, endTime={}", 
                invoiceType, status, contractId, startTime, endTime);
        LambdaQueryWrapper<Invoice> wrapper = new LambdaQueryWrapper<>();
        if (invoiceType != null) {
            wrapper.eq(Invoice::getInvoiceType, invoiceType);
        }
        if (status != null) {
            wrapper.eq(Invoice::getInvoiceStatus, status);
        }
        if (contractId != null) {
            wrapper.eq(Invoice::getCaseId, contractId);
        }
        if (startTime != null) {
            wrapper.ge(Invoice::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Invoice::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Invoice::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'view')")
    @Cacheable(value = "invoice", key = "'page:' + #page.current + ':' + #page.size + ':' + #invoiceType + ':' + #status + ':' + #contractId + ':' + #startTime + ':' + #endTime")
    public IPage<Invoice> pageInvoices(IPage<Invoice> page, InvoiceTypeEnum invoiceType, InvoiceStatusEnum status,
                                     Long contractId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询发票: page={}, invoiceType={}, status={}, contractId={}, startTime={}, endTime={}", 
                page, invoiceType, status, contractId, startTime, endTime);
        LambdaQueryWrapper<Invoice> wrapper = new LambdaQueryWrapper<>();
        if (invoiceType != null) {
            wrapper.eq(Invoice::getInvoiceType, invoiceType);
        }
        if (status != null) {
            wrapper.eq(Invoice::getInvoiceStatus, status);
        }
        if (contractId != null) {
            wrapper.eq(Invoice::getCaseId, contractId);
        }
        if (startTime != null) {
            wrapper.ge(Invoice::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Invoice::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Invoice::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'view')")
    @Cacheable(value = "invoice", key = "'list_by_contract:' + #contractId")
    public List<Invoice> listInvoicesByContract(Long contractId) {
        log.info("按合同查询发票: contractId={}", contractId);
        return list(new LambdaQueryWrapper<Invoice>()
                .eq(Invoice::getCaseId, contractId)
                .orderByDesc(Invoice::getCreateTime));
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'view')")
    @Cacheable(value = "invoice", key = "'list_by_client:' + #clientId + ':' + #startTime + ':' + #endTime")
    public List<Invoice> listInvoicesByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按客户查询发票: clientId={}, startTime={}, endTime={}", clientId, startTime, endTime);
        LambdaQueryWrapper<Invoice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Invoice::getClientId, clientId);
        if (startTime != null) {
            wrapper.ge(Invoice::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Invoice::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Invoice::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'view')")
    @Cacheable(value = "invoice", key = "'sum_amount:' + #invoiceType + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumInvoiceAmount(InvoiceTypeEnum invoiceType, InvoiceStatusEnum status, 
                                     LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计发票金额: invoiceType={}, status={}, startTime={}, endTime={}", 
                invoiceType, status, startTime, endTime);
        List<Invoice> invoices = listInvoices(invoiceType, status, null, startTime, endTime);
        return invoices.stream()
                .map(Invoice::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'view')")
    @Cacheable(value = "invoice", key = "'sum_contract_amount:' + #contractId + ':' + #status")
    public BigDecimal sumContractInvoiceAmount(Long contractId, InvoiceStatusEnum status) {
        log.info("统计合同发票金额: contractId={}, status={}", contractId, status);
        LambdaQueryWrapper<Invoice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Invoice::getCaseId, contractId);
        if (status != null) {
            wrapper.eq(Invoice::getInvoiceStatus, status);
        }
        List<Invoice> invoices = list(wrapper);
        return invoices.stream()
                .map(Invoice::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('invoice', 'export')")
    public String exportInvoices(List<Long> invoiceIds) {
        log.info("导出发票数据: invoiceIds={}", invoiceIds);
        // TODO: 实现导出功能
        return null;
    }
} 