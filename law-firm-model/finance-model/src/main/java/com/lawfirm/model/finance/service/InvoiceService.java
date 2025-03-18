package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 发票服务接口
 */
public interface InvoiceService {
    
    /**
     * 创建发票
     *
     * @param invoice 发票信息
     * @return 发票ID
     */
    Long createInvoice(Invoice invoice);
    
    /**
     * 更新发票信息
     *
     * @param invoice 发票信息
     * @return 是否更新成功
     */
    boolean updateInvoice(Invoice invoice);
    
    /**
     * 删除发票
     *
     * @param invoiceId 发票ID
     * @return 是否删除成功
     */
    boolean deleteInvoice(Long invoiceId);
    
    /**
     * 获取发票详情
     *
     * @param invoiceId 发票ID
     * @return 发票信息
     */
    Invoice getInvoiceById(Long invoiceId);
    
    /**
     * 更新发票状态
     *
     * @param invoiceId 发票ID
     * @param status 状态
     * @param remark 备注
     * @return 是否更新成功
     */
    boolean updateInvoiceStatus(Long invoiceId, InvoiceStatusEnum status, String remark);
    
    /**
     * 确认开票
     *
     * @param invoiceId 发票ID
     * @param invoiceNo 发票号码
     * @param invoiceDate 开票日期
     * @param operatorId 操作人ID
     * @param remark 备注
     * @return 是否确认成功
     */
    boolean confirmInvoice(Long invoiceId, String invoiceNo, LocalDateTime invoiceDate, 
                          Long operatorId, String remark);
    
    /**
     * 取消发票
     *
     * @param invoiceId 发票ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelInvoice(Long invoiceId, String reason);
    
    /**
     * 查询发票列表
     *
     * @param invoiceType 发票类型，可为null
     * @param status 发票状态，可为null
     * @param contractId 合同ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 发票列表
     */
    List<Invoice> listInvoices(InvoiceTypeEnum invoiceType, InvoiceStatusEnum status, 
                              Long contractId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询发票
     *
     * @param page 分页参数
     * @param invoiceType 发票类型，可为null
     * @param status 发票状态，可为null
     * @param contractId 合同ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页发票信息
     */
    IPage<Invoice> pageInvoices(IPage<Invoice> page, InvoiceTypeEnum invoiceType, 
                              InvoiceStatusEnum status, Long contractId, 
                              LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按合同查询发票
     *
     * @param contractId 合同ID
     * @return 发票列表
     */
    List<Invoice> listInvoicesByContract(Long contractId);
    
    /**
     * 按客户查询发票
     *
     * @param clientId 客户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 发票列表
     */
    List<Invoice> listInvoicesByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计发票金额
     *
     * @param invoiceType 发票类型，可为null
     * @param status 发票状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 发票总金额
     */
    BigDecimal sumInvoiceAmount(InvoiceTypeEnum invoiceType, InvoiceStatusEnum status, 
                               LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计合同发票金额
     *
     * @param contractId 合同ID
     * @param status 发票状态，可为null
     * @return 发票总金额
     */
    BigDecimal sumContractInvoiceAmount(Long contractId, InvoiceStatusEnum status);
    
    /**
     * 导出发票数据
     *
     * @param invoiceIds 发票ID列表
     * @return 导出文件路径
     */
    String exportInvoices(List<Long> invoiceIds);
} 