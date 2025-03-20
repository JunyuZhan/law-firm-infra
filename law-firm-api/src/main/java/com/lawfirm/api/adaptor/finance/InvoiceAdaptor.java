package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.invoice.InvoiceCreateDTO;
import com.lawfirm.model.finance.dto.invoice.InvoiceUpdateDTO;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.service.InvoiceService;
import com.lawfirm.model.finance.vo.invoice.InvoiceDetailVO;
import com.lawfirm.model.finance.vo.invoice.InvoiceListVO;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public Long createInvoice(InvoiceCreateDTO dto) {
        Invoice invoice = convert(dto, Invoice.class);
        return invoiceService.createInvoice(invoice);
    }

    /**
     * 更新发票
     */
    public boolean updateInvoice(Long id, InvoiceUpdateDTO dto) {
        Invoice invoice = convert(dto, Invoice.class);
        invoice.setId(id);
        return invoiceService.updateInvoice(invoice);
    }

    /**
     * 获取发票详情
     */
    public InvoiceDetailVO getInvoice(Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return convert(invoice, InvoiceDetailVO.class);
    }

    /**
     * 删除发票
     */
    public boolean deleteInvoice(Long id) {
        return invoiceService.deleteInvoice(id);
    }

    /**
     * 获取所有发票
     */
    public List<InvoiceListVO> listInvoices(InvoiceTypeEnum type, InvoiceStatusEnum status, 
                                           Long contractId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Invoice> invoices = invoiceService.listInvoices(type, status, contractId, startTime, endTime);
        return invoices.stream()
                .map(invoice -> convert(invoice, InvoiceListVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新发票状态
     */
    public boolean updateInvoiceStatus(Long id, InvoiceStatusEnum status, String remark) {
        return invoiceService.updateInvoiceStatus(id, status, remark);
    }

    /**
     * 确认开票
     */
    public boolean confirmInvoice(Long id, String invoiceNo, LocalDateTime invoiceDate, 
                                Long operatorId, String remark) {
        return invoiceService.confirmInvoice(id, invoiceNo, invoiceDate, operatorId, remark);
    }

    /**
     * 取消发票
     */
    public boolean cancelInvoice(Long id, String reason) {
        return invoiceService.cancelInvoice(id, reason);
    }

    /**
     * 分页查询发票
     */
    public IPage<InvoiceListVO> pageInvoices(IPage<Invoice> page, InvoiceTypeEnum type, 
                                            InvoiceStatusEnum status, Long contractId, 
                                            LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Invoice> invoicePage = invoiceService.pageInvoices(page, type, status, contractId, startTime, endTime);
        return invoicePage.convert(invoice -> convert(invoice, InvoiceListVO.class));
    }

    /**
     * 根据合同查询发票
     */
    public List<InvoiceListVO> listInvoicesByContract(Long contractId) {
        List<Invoice> invoices = invoiceService.listInvoicesByContract(contractId);
        return invoices.stream()
                .map(invoice -> convert(invoice, InvoiceListVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据客户查询发票
     */
    public List<InvoiceListVO> listInvoicesByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Invoice> invoices = invoiceService.listInvoicesByClient(clientId, startTime, endTime);
        return invoices.stream()
                .map(invoice -> convert(invoice, InvoiceListVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 统计发票金额
     */
    public BigDecimal sumInvoiceAmount(InvoiceTypeEnum type, InvoiceStatusEnum status, 
                                     LocalDateTime startTime, LocalDateTime endTime) {
        return invoiceService.sumInvoiceAmount(type, status, startTime, endTime);
    }

    /**
     * 统计合同发票金额
     */
    public BigDecimal sumContractInvoiceAmount(Long contractId, InvoiceStatusEnum status) {
        return invoiceService.sumContractInvoiceAmount(contractId, status);
    }

    /**
     * 导出发票数据
     */
    public String exportInvoices(List<Long> invoiceIds) {
        return invoiceService.exportInvoices(invoiceIds);
    }
} 