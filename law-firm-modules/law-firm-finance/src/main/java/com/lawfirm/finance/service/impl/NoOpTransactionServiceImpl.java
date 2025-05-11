package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import com.lawfirm.model.finance.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 财务交易服务空实现类，当存储功能禁用时使用
 */
@Slf4j
@Service("financeTransactionServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpTransactionServiceImpl extends BaseServiceImpl<BaseMapper<Transaction>, Transaction>
        implements TransactionService {

    @Override
    public Long createTransaction(Transaction transaction) {
        log.warn("存储功能已禁用，忽略创建交易记录操作");
        return null;
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        log.warn("存储功能已禁用，忽略更新交易记录操作");
        return true;
    }

    @Override
    public boolean deleteTransaction(Long transactionId) {
        log.warn("存储功能已禁用，忽略删除交易记录操作: {}", transactionId);
        return true;
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        log.warn("存储功能已禁用，忽略获取交易记录操作: {}", transactionId);
        return null;
    }

    @Override
    public List<Transaction> listTransactions(TransactionTypeEnum transactionType, Long accountId,
                                            LocalDateTime startTime, LocalDateTime endTime) {
        log.warn("存储功能已禁用，忽略查询交易记录列表操作");
        return Collections.emptyList();
    }

    @Override
    public IPage<Transaction> pageTransactions(IPage<Transaction> page, TransactionTypeEnum transactionType,
                                             Long accountId, LocalDateTime startTime, LocalDateTime endTime) {
        log.warn("存储功能已禁用，忽略分页查询交易记录操作");
        return page;
    }

    @Override
    public List<Transaction> listTransactionsByAccount(Long accountId, LocalDateTime startTime, LocalDateTime endTime) {
        log.warn("存储功能已禁用，忽略按账户查询交易记录操作: {}", accountId);
        return Collections.emptyList();
    }

    @Override
    public IPage<Transaction> pageTransactionsByAccount(IPage<Transaction> page, Long accountId,
                                                      LocalDateTime startTime, LocalDateTime endTime) {
        log.warn("存储功能已禁用，忽略按账户分页查询交易记录操作: {}", accountId);
        return page;
    }

    @Override
    public List<Transaction> listTransactionsByContract(Long contractId) {
        log.warn("存储功能已禁用，忽略按合同查询交易记录操作: {}", contractId);
        return Collections.emptyList();
    }

    @Override
    public List<Transaction> listTransactionsByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        log.warn("存储功能已禁用，忽略按客户查询交易记录操作: {}", clientId);
        return Collections.emptyList();
    }

    @Override
    public BigDecimal sumTransactionAmount(TransactionTypeEnum transactionType, Long accountId,
                                         LocalDateTime startTime, LocalDateTime endTime) {
        log.warn("存储功能已禁用，忽略计算交易金额操作");
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumAccountTransactionAmount(Long accountId, TransactionTypeEnum transactionType,
                                                LocalDateTime startTime, LocalDateTime endTime) {
        log.warn("存储功能已禁用，忽略计算账户交易金额操作: {}", accountId);
        return BigDecimal.ZERO;
    }

    @Override
    public String exportTransactions(List<Long> transactionIds) {
        log.warn("存储功能已禁用，忽略导出交易记录操作: {}", transactionIds);
        return null;
    }
} 