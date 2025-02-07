package com.lawfirm.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.finance.entity.Invoice;

import java.util.Map;

/**
 * 发票服务接口
 */
public interface InvoiceService extends IService<Invoice> {

    /**
     * 分页查询发票
     */
    IPage<Invoice> pageInvoices(Integer pageNum, Integer pageSize, String title, Integer type, 
        String invoiceNo, Long matterId, String startDate, String endDate);

    /**
     * 创建发票
     */
    void createInvoice(Invoice invoice);

    /**
     * 更新发票
     */
    void updateInvoice(Invoice invoice);

    /**
     * 删除发票
     */
    void deleteInvoice(Long id);

    /**
     * 获取发票详情
     */
    Invoice getInvoiceDetail(Long id);

    /**
     * 开具发票
     */
    void issueInvoice(Long id);

    /**
     * 作废发票
     */
    void voidInvoice(Long id, String reason);

    /**
     * 导出发票
     */
    void exportInvoices(String title, Integer type, String invoiceNo, Long matterId, 
        String startDate, String endDate);

    /**
     * 统计发票
     */
    Map<String, Object> getInvoiceStats(String title, Integer type, String invoiceNo, Long matterId,
        String startDate, String endDate);
} 