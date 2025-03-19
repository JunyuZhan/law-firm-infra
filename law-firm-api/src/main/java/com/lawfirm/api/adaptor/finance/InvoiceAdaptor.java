package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.invoice.InvoiceCreateDTO;
import com.lawfirm.model.finance.dto.invoice.InvoiceUpdateDTO;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.service.InvoiceService;
import com.lawfirm.model.finance.vo.invoice.InvoiceVO;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票管理适配器
 */
@Component
public class InvoiceAdaptor extends BaseAdaptor {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * 创建发票
     */
    public InvoiceVO createInvoice(InvoiceCreateDTO dto) {
        Invoice invoice = invoiceService.createInvoice(dto);
        return convert(invoice, InvoiceVO.class);
    }

    /**
     * 更新发票
     */
    public InvoiceVO updateInvoice(Long id, InvoiceUpdateDTO dto) {
        Invoice invoice = invoiceService.updateInvoice(id, dto);
        return convert(invoice, InvoiceVO.class);
    }

    /**
     * 获取发票详情
     */
    public InvoiceVO getInvoice(Long id) {
        Invoice invoice = invoiceService.getInvoice(id);
        return convert(invoice, InvoiceVO.class);
    }

    /**
     * 删除发票
     */
    public void deleteInvoice(Long id) {
        invoiceService.deleteInvoice(id);
    }

    /**
     * 获取所有发票
     */
    public List<InvoiceVO> listInvoices() {
        List<Invoice> invoices = invoiceService.listInvoices();
        return invoices.stream()
                .map(invoice -> convert(invoice, InvoiceVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新发票状态
     */
    public void updateInvoiceStatus(Long id, InvoiceStatusEnum status) {
        invoiceService.updateInvoiceStatus(id, status);
    }

    /**
     * 根据发票类型查询发票
     */
    public List<InvoiceVO> getInvoicesByType(InvoiceTypeEnum type) {
        List<Invoice> invoices = invoiceService.getInvoicesByType(type);
        return invoices.stream()
                .map(invoice -> convert(invoice, InvoiceVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据账户ID查询发票
     */
    public List<InvoiceVO> getInvoicesByAccountId(Long accountId) {
        List<Invoice> invoices = invoiceService.getInvoicesByAccountId(accountId);
        return invoices.stream()
                .map(invoice -> convert(invoice, InvoiceVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查发票是否存在
     */
    public boolean existsInvoice(Long id) {
        return invoiceService.existsInvoice(id);
    }

    /**
     * 获取发票数量
     */
    public long countInvoices() {
        return invoiceService.countInvoices();
    }
} 