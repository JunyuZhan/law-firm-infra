package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import com.lawfirm.model.finance.mapper.TransactionMapper;
import com.lawfirm.model.finance.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 财务交易服务实现类
 */
@Slf4j
@Service("financeTransactionServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = true)
public class TransactionServiceImpl extends BaseServiceImpl<TransactionMapper, Transaction>
        implements TransactionService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTransaction(Transaction transaction) {
        log.info("创建交易记录: transaction={}", transaction);
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setUpdateTime(LocalDateTime.now());
        save(transaction);
        return transaction.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTransaction(Transaction transaction) {
        log.info("更新交易记录: transaction={}", transaction);
        transaction.setUpdateTime(LocalDateTime.now());
        return update(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTransaction(Long transactionId) {
        log.info("删除交易记录: transactionId={}", transactionId);
        return remove(transactionId);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        log.info("获取交易记录: transactionId={}", transactionId);
        return getById(transactionId);
    }

    @Override
    public List<Transaction> listTransactions(TransactionTypeEnum transactionType, Long accountId,
                                            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询交易记录列表: transactionType={}, accountId={}, startTime={}, endTime={}",
                transactionType, accountId, startTime, endTime);
        LambdaQueryWrapper<Transaction> wrapper = buildTransactionWrapper(transactionType, accountId, startTime, endTime);
        return list(wrapper);
    }

    @Override
    public IPage<Transaction> pageTransactions(IPage<Transaction> page, TransactionTypeEnum transactionType,
                                             Long accountId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询交易记录: page={}, transactionType={}, accountId={}, startTime={}, endTime={}",
                page, transactionType, accountId, startTime, endTime);
        LambdaQueryWrapper<Transaction> wrapper = buildTransactionWrapper(transactionType, accountId, startTime, endTime);
        return page(page, wrapper);
    }

    @Override
    public List<Transaction> listTransactionsByAccount(Long accountId, LocalDateTime startTime, LocalDateTime endTime) {
        return listTransactions(null, accountId, startTime, endTime);
    }

    @Override
    public IPage<Transaction> pageTransactionsByAccount(IPage<Transaction> page, Long accountId,
                                                      LocalDateTime startTime, LocalDateTime endTime) {
        return pageTransactions(page, null, accountId, startTime, endTime);
    }

    @Override
    public List<Transaction> listTransactionsByContract(Long contractId) {
        log.info("查询合同相关交易记录: contractId={}", contractId);
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Transaction::getBusinessId, contractId)
               .eq(Transaction::getBusinessType, "CONTRACT");
        return list(wrapper);
    }

    @Override
    public List<Transaction> listTransactionsByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询客户相关交易记录: clientId={}, startTime={}, endTime={}", clientId, startTime, endTime);
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Transaction::getBusinessId, clientId)
               .eq(Transaction::getBusinessType, "CLIENT");
        if (startTime != null) {
            wrapper.ge(Transaction::getTransactionTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Transaction::getTransactionTime, endTime);
        }
        return list(wrapper);
    }

    @Override
    public BigDecimal sumTransactionAmount(TransactionTypeEnum transactionType, Long accountId,
                                         LocalDateTime startTime, LocalDateTime endTime) {
        log.info("计算交易金额: transactionType={}, accountId={}, startTime={}, endTime={}",
                transactionType, accountId, startTime, endTime);
        List<Transaction> transactions = listTransactions(transactionType, accountId, startTime, endTime);
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumAccountTransactionAmount(Long accountId, TransactionTypeEnum transactionType,
                                                LocalDateTime startTime, LocalDateTime endTime) {
        return sumTransactionAmount(transactionType, accountId, startTime, endTime);
    }

    @Override
    public String exportTransactions(List<Long> transactionIds) {
        log.info("导出交易记录: transactionIds={}", transactionIds);
        if (CollectionUtils.isEmpty(transactionIds)) {
            log.warn("交易记录ID列表为空");
            return null;
        }
        // TODO: 实现交易数据导出功能
        return null;
    }

    /**
     * 构建交易查询条件
     */
    private LambdaQueryWrapper<Transaction> buildTransactionWrapper(TransactionTypeEnum transactionType,
                                                                  Long accountId,
                                                                  LocalDateTime startTime,
                                                                  LocalDateTime endTime) {
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        if (transactionType != null) {
            wrapper.eq(Transaction::getTransactionType, transactionType);
        }
        if (accountId != null) {
            wrapper.and(w -> w.eq(Transaction::getFromAccountId, accountId)
                            .or()
                            .eq(Transaction::getToAccountId, accountId));
        }
        if (startTime != null) {
            wrapper.ge(Transaction::getTransactionTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Transaction::getTransactionTime, endTime);
        }
        wrapper.orderByDesc(Transaction::getTransactionTime);
        return wrapper;
    }
}