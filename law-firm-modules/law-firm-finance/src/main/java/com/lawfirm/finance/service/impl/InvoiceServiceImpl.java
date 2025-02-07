package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.finance.entity.Invoice;
import com.lawfirm.finance.mapper.InvoiceMapper;
import com.lawfirm.finance.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    @Override
    public IPage<Invoice> pageInvoices(Integer pageNum, Integer pageSize, String title, Integer type,
            String invoiceNo, Long matterId, String startDate, String endDate) {
        Page<Invoice> page = new Page<>(pageNum, pageSize);
        return lambdaQuery()
            .like(title != null, Invoice::getTitle, title)
            .eq(type != null, Invoice::getType, type)
            .eq(invoiceNo != null, Invoice::getInvoiceNo, invoiceNo)
            .eq(matterId != null, Invoice::getMatterId, matterId)
            .ge(startDate != null, Invoice::getCreateTime, startDate)
            .le(endDate != null, Invoice::getCreateTime, endDate)
            .orderByDesc(Invoice::getCreateTime)
            .page(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createInvoice(Invoice invoice) {
        // 设置初始状态
        invoice.setStatus(0); // 待开具
        save(invoice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInvoice(Invoice invoice) {
        updateById(invoice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInvoice(Long id) {
        // 检查发票状态
        Invoice invoice = getById(id);
        if (invoice != null && invoice.getStatus() != 0) {
            throw new RuntimeException("只能删除待开具状态的发票");
        }
        removeById(id);
    }

    @Override
    public Invoice getInvoiceDetail(Long id) {
        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void issueInvoice(Long id) {
        // 检查发票状态
        Invoice invoice = getById(id);
        if (invoice == null) {
            throw new RuntimeException("发票不存在");
        }
        if (invoice.getStatus() != 0) {
            throw new RuntimeException("只能开具待开具状态的发票");
        }
        
        // 更新状态
        invoice.setStatus(1); // 已开具
        updateById(invoice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void voidInvoice(Long id, String reason) {
        // 检查发票状态
        Invoice invoice = getById(id);
        if (invoice == null) {
            throw new RuntimeException("发票不存在");
        }
        if (invoice.getStatus() != 1) {
            throw new RuntimeException("只能作废已开具状态的发票");
        }
        
        // 更新状态
        invoice.setStatus(2); // 已作废
        invoice.setRemark(reason);
        updateById(invoice);
    }

    @Override
    public void exportInvoices(String title, Integer type, String invoiceNo, Long matterId,
            String startDate, String endDate) {
        // TODO: 实现导出逻辑
    }

    @Override
    public Map<String, Object> getInvoiceStats(String title, Integer type, String invoiceNo, Long matterId,
            String startDate, String endDate) {
        Map<String, Object> stats = new HashMap<>();
        
        // 查询符合条件的发票
        stats.put("totalCount", lambdaQuery()
            .like(title != null, Invoice::getTitle, title)
            .eq(type != null, Invoice::getType, type)
            .eq(invoiceNo != null, Invoice::getInvoiceNo, invoiceNo)
            .eq(matterId != null, Invoice::getMatterId, matterId)
            .ge(startDate != null, Invoice::getCreateTime, startDate)
            .le(endDate != null, Invoice::getCreateTime, endDate)
            .count());
            
        // 计算总金额
        stats.put("totalAmount", lambdaQuery()
            .like(title != null, Invoice::getTitle, title)
            .eq(type != null, Invoice::getType, type)
            .eq(invoiceNo != null, Invoice::getInvoiceNo, invoiceNo)
            .eq(matterId != null, Invoice::getMatterId, matterId)
            .ge(startDate != null, Invoice::getCreateTime, startDate)
            .le(endDate != null, Invoice::getCreateTime, endDate)
            .select(Invoice::getAmount)
            .list()
            .stream()
            .map(Invoice::getAmount)
            .reduce((a, b) -> a.add(b))
            .orElse(null));
            
        return stats;
    }
} 