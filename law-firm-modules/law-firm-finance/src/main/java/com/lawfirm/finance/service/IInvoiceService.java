package com.lawfirm.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.finance.entity.Invoice;
import com.lawfirm.finance.dto.request.InvoiceAddRequest;
import com.lawfirm.finance.dto.request.InvoiceUpdateRequest;
import com.lawfirm.finance.dto.request.InvoiceQueryRequest;
import com.lawfirm.finance.dto.response.InvoiceResponse;

import java.util.List;

/**
 * 发票Service接口
 */
public interface IInvoiceService extends IService<Invoice> {

    /**
     * 添加发票
     *
     * @param request 添加请求
     * @return 发票ID
     */
    Long addInvoice(InvoiceAddRequest request);

    /**
     * 更新发票
     *
     * @param request 更新请求
     */
    void updateInvoice(InvoiceUpdateRequest request);

    /**
     * 删除发票
     *
     * @param id 发票ID
     */
    void deleteInvoice(Long id);

    /**
     * 分页查询发票
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<InvoiceResponse> pageInvoices(InvoiceQueryRequest request);

    /**
     * 获取发票详情
     *
     * @param id 发票ID
     * @return 发票详情
     */
    InvoiceResponse getInvoiceById(Long id);

    /**
     * 作废发票
     *
     * @param id 发票ID
     * @param reason 作废原因
     */
    void invalidateInvoice(Long id, String reason);

    /**
     * 获取发票统计
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 发票统计列表
     */
    List<InvoiceResponse> getInvoiceStatistics(String startTime, String endTime);
} 