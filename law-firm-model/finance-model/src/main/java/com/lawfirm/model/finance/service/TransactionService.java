package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易服务接口
 */
public interface TransactionService {
    
    /**
     * 创建交易记录
     *
     * @param transaction 交易信息
     * @return 交易ID
     */
    Long createTransaction(Transaction transaction);
    
    /**
     * 更新交易记录
     *
     * @param transaction 交易信息
     * @return 是否更新成功
     */
    boolean updateTransaction(Transaction transaction);
    
    /**
     * 删除交易记录
     *
     * @param transactionId 交易ID
     * @return 是否删除成功
     */
    boolean deleteTransaction(Long transactionId);
    
    /**
     * 获取交易详情
     *
     * @param transactionId 交易ID
     * @return 交易信息
     */
    Transaction getTransactionById(Long transactionId);
    
    /**
     * 查询交易列表
     *
     * @param transactionType 交易类型，可为null
     * @param accountId 账户ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 交易列表
     */
    List<Transaction> listTransactions(TransactionTypeEnum transactionType, Long accountId,
                                     LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询交易
     *
     * @param page 分页参数
     * @param transactionType 交易类型，可为null
     * @param accountId 账户ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页交易信息
     */
    IPage<Transaction> pageTransactions(IPage<Transaction> page, TransactionTypeEnum transactionType,
                                      Long accountId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按账户查询交易
     *
     * @param accountId 账户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 交易列表
     */
    List<Transaction> listTransactionsByAccount(Long accountId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按账户分页查询交易
     *
     * @param page 分页参数
     * @param accountId 账户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页交易信息
     */
    IPage<Transaction> pageTransactionsByAccount(IPage<Transaction> page, Long accountId,
                                               LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按合同查询交易
     *
     * @param contractId 合同ID
     * @return 交易列表
     */
    List<Transaction> listTransactionsByContract(Long contractId);
    
    /**
     * 按客户查询交易
     *
     * @param clientId 客户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 交易列表
     */
    List<Transaction> listTransactionsByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计交易金额
     *
     * @param transactionType 交易类型，可为null
     * @param accountId 账户ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 交易总金额
     */
    BigDecimal sumTransactionAmount(TransactionTypeEnum transactionType, Long accountId,
                                  LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计账户交易金额
     *
     * @param accountId 账户ID
     * @param transactionType 交易类型，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 交易总金额
     */
    BigDecimal sumAccountTransactionAmount(Long accountId, TransactionTypeEnum transactionType,
                                         LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 导出交易数据
     *
     * @param transactionIds 交易ID列表
     * @return 导出文件路径
     */
    String exportTransactions(List<Long> transactionIds);
} 